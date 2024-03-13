/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.utils.python;

import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.serialization.JsonSerializer;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * Instance of a python process that started a python module that works in a streaming mode.
 */
@Slf4j
public class StreamingPythonProcess implements Closeable, ExecuteResultHandler {
    private static final Logger LOG = LoggerFactory.getLogger(StreamingPythonProcess.class);
    private static final int PYTHON_BUFFER_SIZE = 1024; // buffer size used in the python streaming process in a call to TextIO.read(buffer_size)
    private static final int PYTHON_RECEIVE_RESPONSE_BUFFER_SIZE = 1024;
    private static final byte[] PYTHON_BUFFER_SPACE = StringUtils.repeat(' ', PYTHON_BUFFER_SIZE + 10).getBytes(StandardCharsets.UTF_8);

    private PipedOutputStream writeToProcessStream;
    private PipedInputStream writeToProcessStreamProcessSide;
    private PipedInputStream readFromProcessStream;
    private PipedOutputStream readFromProcessStreamProcessSide;
    private InputStreamReader readFromProcessStreamReader;
    private volatile PumpStreamHandler streamHandler;
    private ByteArrayOutputStream errorStream;
    private DefaultExecutor executor;
    private CompletableFuture<Object> processFinishedErrorFuture;
    private CompletableFuture<Object> processFinishedSuccessFuture;
    private CompletableFuture<OutputStream> outputDetectedOnStderrFuture;
    private final JsonSerializer jsonSerializer;
    private final String commandLineText;
    private final int timeoutSeconds;
    private JsonFactory jsonFactory;
    private JsonParser jsonParser;
    private Thread processingThread;
    private BlockingQueue<PythonRequestReplyMessage<?,?>> requestReplyMessages = new LinkedBlockingDeque<>();
    private boolean closed;
    private final CountDownLatch waitForClose = new CountDownLatch(1);

    /**
     * Streaming python process wrapper. Keeps a reference to a python process that was started in the background.
     * @param jsonSerializer Json serializer.
     * @param commandLineText Command line text that will be started.
     * @param timeoutSeconds Timeout in seconds.
     */
    public StreamingPythonProcess(JsonSerializer jsonSerializer, String commandLineText, int timeoutSeconds) {
        this.jsonSerializer = jsonSerializer;
        this.commandLineText = commandLineText;
        this.timeoutSeconds = timeoutSeconds;
    }

    /**
     * Returns true if the python process was closed, probably due to a timeout.
     * @return true - the process was closed.
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Sends (streams) a JSON request to the process, waits for the response and returns the response.
     * @param outputType Output type to parse the returned json objects.
     * @param <I> Input type.
     * @param <O> Output type.
     * @return Response received from the process.
     */
    public <I, O> O sendReceiveMessage(I input, Class<O> outputType) {
        try {
            PythonRequestReplyMessage<I, O> sendReceiveMessage = new PythonRequestReplyMessage<>(input, outputType);
            this.requestReplyMessages.put(sendReceiveMessage);

            return sendReceiveMessage.getResponseFuture().get();
        } catch (ExecutionException | InterruptedException ex) {
            throw new PythonExecutionException("Python process failed with a message: " + ex.getMessage(), ex);
        }
    }

    /**
     * Starts a process in the background.
     * @param pythonVirtualEnv Python virtual environment.
     */
    public void startProcess(PythonVirtualEnv pythonVirtualEnv) {
        CompletableFuture<Void> processStartedFuture = new CompletableFuture<>();

        this.processingThread = new Thread(() -> {
            try {
                startProcessCore(pythonVirtualEnv);
                processStartedFuture.complete(null);
            }
            catch (Throwable ex) {
                processStartedFuture.completeExceptionally(ex);
                return;
            }

            while (true) {
                try {
                    PythonRequestReplyMessage requestReplyMessage = this.requestReplyMessages.take();
                    if (requestReplyMessage.isEmpty()) {
                        return;
                    }

                    try {
                        Object response = sendReceiveMessageCore(requestReplyMessage.getRequest(), requestReplyMessage.getResponseType());
                        requestReplyMessage.getResponseFuture().complete(response);
                    }
                    catch (Throwable ex) {
                        requestReplyMessage.getResponseFuture().completeExceptionally(ex);
                    }
                }
                catch (InterruptedException ex) {
                    return;
                }
            }
        });
        this.processingThread.setDaemon(true);
        this.processingThread.start();

        try {
            processStartedFuture.get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new DqoRuntimeException("Failed to start a Python process, error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Sends (streams) a JSON request to the process, waits for the response and returns the response.
     * @param outputType Output type to parse the returned json objects.
     * @return Response received from the process.
     */
    public Object sendReceiveMessageCore(Object input, Class<?> outputType) {
        try {
            String inputText = this.jsonSerializer.serialize(input);

// NOTE: uncomment the following line if you are testing, and you want to see the input json that was sent to the python process
//            System.out.println(inputText);

            byte[] inputBytes = inputText.getBytes(StandardCharsets.UTF_8);
            this.writeToProcessStream.write(inputBytes);
            this.writeToProcessStream.flush();
            this.writeToProcessStream.write(PYTHON_BUFFER_SPACE);  // python buffer flush overflow to avoid blocking...
            this.writeToProcessStream.flush();

            CompletableFuture<Boolean> timeoutCompletableFuture = new CompletableFuture<>();
            timeoutCompletableFuture.completeOnTimeout(false, this.timeoutSeconds, TimeUnit.SECONDS);
            CompletableFuture<Object> completedFuture = CompletableFuture.anyOf(timeoutCompletableFuture,
                    this.outputDetectedOnStderrFuture, this.processFinishedErrorFuture, this.processFinishedSuccessFuture);
            CompletableFuture<Object> finishedFuture = completedFuture.handleAsync((Object result, Throwable ex) -> {
                if (timeoutCompletableFuture.isCancelled()) {
                    // result received
                    return null;
                }

                if (Objects.equals(result,false)) {
                    // time out
                    this.close();
                    return null;
                }

                if (ex != null) {
                    this.close();
                    String message = "Waiting for a response from Python failed with an error: " + ex.getMessage();
                    log.error(message, ex);
                    throw new PythonExecutionException(message, ex);
                }

                if (this.outputDetectedOnStderrFuture.isDone() || processFinishedErrorFuture.isDone() || processFinishedSuccessFuture.isDone()) {
                    this.close();
                    String errStreamText = this.errorStream.toString(StandardCharsets.UTF_8);
                    throw new PythonExecutionException("Python command " + this.commandLineText + " failed, stderr:\n" + errStreamText);
                }

                return null;
            });

            try {
                ObjectMapper objectMapper = this.jsonSerializer.getMapper();
                Object deserializedResponse = objectMapper.readValue(this.jsonParser, outputType);
                timeoutCompletableFuture.cancel(true);
                finishedFuture.cancel(true);

                return deserializedResponse;
            } catch (Exception ex) {
                this.close();
                timeoutCompletableFuture.cancel(true);
                finishedFuture.cancel(true);

                if (!this.outputDetectedOnStderrFuture.isDone()) {
                    throw new PythonExecutionException("Failed to parse a response from a python process, message: " + ex.getMessage(), ex);
                }
                else {
                    String errStreamText = this.errorStream.toString(StandardCharsets.UTF_8);
                    throw new PythonExecutionException("Python command " + this.commandLineText + " failed, stderr:\n" + errStreamText, ex);
                }
            }
        } catch (PythonExecutionException ex) {
            throw ex;
        } catch (Exception e) {
            throw new PythonExecutionException("Python process failed with a message: " + e.getMessage(), e);
        }
    }

    /**
     * Starts a process in the background. This method runs as the thread's method.
     * @param pythonVirtualEnv Python virtual environment.
     */
    public void startProcessCore(PythonVirtualEnv pythonVirtualEnv) {
        try {
            CommandLine cmdLine = CommandLine.parse(this.commandLineText);

            this.processFinishedErrorFuture = new CompletableFuture<>();
            this.processFinishedSuccessFuture = new CompletableFuture<>();
            this.writeToProcessStream = new PipedOutputStream();
            this.writeToProcessStreamProcessSide = new PipedInputStream(this.writeToProcessStream);
            this.readFromProcessStreamProcessSide = new PipedOutputStream();
            this.readFromProcessStream = new PipedInputStream(this.readFromProcessStreamProcessSide);

            this.readFromProcessStreamReader = new InputStreamReader(
                    new BufferedInputStream(readFromProcessStream, PYTHON_RECEIVE_RESPONSE_BUFFER_SIZE), StandardCharsets.UTF_8);
            this.errorStream = new ByteArrayOutputStream();
            this.jsonFactory = new JsonFactory();
            this.jsonParser = jsonFactory.createParser(this.readFromProcessStreamReader);

            ActivityDetectionOutputStream errorOutputStream = new ActivityDetectionOutputStream(new FlushingOutputStream(this.errorStream));
            this.outputDetectedOnStderrFuture = errorOutputStream.getOutputDetectedFuture();
            this.outputDetectedOnStderrFuture
                    .thenRun(() -> {
                        try {
                            // we detected that some output was written to the stderr of the python process, it is an error and we will terminate...
                            Thread.sleep(100); // we need to wait for the remaining output
                            this.waitForClose.countDown();
                            this.close();

                            String errStreamText = this.errorStream.toString(StandardCharsets.UTF_8);
                            log.error("Python process failed with an error, the error captured from the stderr: " + errStreamText);
                        }
                        catch (Exception ioe) {
                            log.error("Python process failed with an error and we cannot close the stream: " + ioe.getMessage(), ioe);
                        }
                    });

            this.streamHandler = new FlushingPumpStreamHandler(
                    new FlushingOutputStream(this.readFromProcessStreamProcessSide),
                    errorOutputStream, // we can use the System.stderr instead to push the errors directly to our error stream
                    //System.err,
                    this.writeToProcessStreamProcessSide);
            this.executor = new DefaultExecutor();
            this.executor.setStreamHandler(streamHandler);

            Map<String, String> systemEnvVariables = System.getenv();
            HashMap<String, String> processEnvVariables = new LinkedHashMap<>(systemEnvVariables);
            processEnvVariables.put("PYTHONUNBUFFERED", "1");
            processEnvVariables.put("PYTHONIOENCODING", "UTF-8");
            if (pythonVirtualEnv.isEnableDebugging()) {
                processEnvVariables.put("PYDEVD_USE_CYTHON", "NO"); // to enable debugging the python process
            }
            processEnvVariables.remove("PYTHONHOME");
            for (Map.Entry<String, String> venvEnvironmentVarKeyValPair : pythonVirtualEnv.getEnvironmentVariables().entrySet()) {
                processEnvVariables.put(venvEnvironmentVarKeyValPair.getKey(), venvEnvironmentVarKeyValPair.getValue());
            }

            this.executor.execute(cmdLine, processEnvVariables, this);
        }
        catch (Exception ex) {
            this.waitForClose.countDown();
            this.streamHandler = null;
            String errStreamText = this.errorStream.toString(StandardCharsets.UTF_8);
            log.error("Python process failed with an error, the error captured from the stderr: " + errStreamText +
                    ", exception message: " + ex.getMessage(), ex);
            throw new PythonExecutionException("Command line " + commandLineText + " failed, stderr:\n" + errStreamText);
        }
    }

    /**
     * Command line text that was started.
     * @return Command line text.
     */
    public String getCommandLineText() {
        return this.commandLineText;
    }

    /**
     * Process has finished with a success.
     * @param i Error code.
     */
    @Override
    public void onProcessComplete(int i) {
		this.processFinishedSuccessFuture.complete(i);
        this.streamHandler = null;
        this.waitForClose.countDown();
        this.close();
    }

    /**
     * Process has failed.
     * @param e Exception from the process.
     */
    @Override
    public void onProcessFailed(ExecuteException e) {
        log.error("Python process failed, error: " + e.getMessage(), e);
		this.processFinishedErrorFuture.complete(e);
        this.streamHandler = null;
        this.waitForClose.countDown();
        this.close();
    }

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     */
    @Override
    public void close() {
        synchronized (this) {
            if (this.closed) {
                return;
            }

            this.closed = true;
        }

        try {
            this.requestReplyMessages.put(PythonRequestReplyMessage.createEmpty()); // poison pill message
        }
        catch (InterruptedException ex) {
        }

        try {
            this.waitForClose.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
        }

        if (this.streamHandler != null) {
            try {
                this.streamHandler.setStopTimeout(100); // there is an additional 2000ms timeout inside the stop function, making it 2100ms
                this.streamHandler.stop();
            }
            catch (Exception ex) {
            }
            finally {
                this.streamHandler = null;
            }
        }

        try {
            if (this.writeToProcessStream != null) {
				this.writeToProcessStream.close();
				this.writeToProcessStreamProcessSide.close();
			}
        }
        catch (IOException ex) {
        }
        finally {
			this.writeToProcessStream = null;
			this.writeToProcessStreamProcessSide = null;
        }

        try {
            if (this.readFromProcessStream != null) {
				this.readFromProcessStream.close();
				this.readFromProcessStreamProcessSide.close();
            }
        }
        catch (IOException ex) {
        }
        finally {
			this.readFromProcessStream = null;
			this.readFromProcessStreamProcessSide = null;
        }

        this.processingThread.interrupt();
    }
}
