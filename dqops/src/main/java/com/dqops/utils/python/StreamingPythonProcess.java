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
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Instance of a python process that started a python module that works in a streaming mode.
 */
@Slf4j
public class StreamingPythonProcess implements Closeable, ExecuteResultHandler {
    private static final Logger LOG = LoggerFactory.getLogger(StreamingPythonProcess.class);
    private static final int PYTHON_BUFFER_SIZE = 1024; // buffer size used in the python streaming process in a call to TextIO.read(buffer_size)
    private static final byte[] PYTHON_BUFFER_SPACE = StringUtils.repeat(' ', PYTHON_BUFFER_SIZE + 10).getBytes(StandardCharsets.UTF_8);

    private PipedOutputStream writeToProcessStream;
    private PipedInputStream writeToProcessStreamProcessSide;
    private PipedInputStream readFromProcessStream;
    private PipedOutputStream readFromProcessStreamProcessSide;
    private InputStreamReader readFromProcessStreamReader;
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
     * Sends (streams) a JSON request to the process, waits for the response and returns the response.
     * @param outputType Output type to parse the returned json objects.
     * @param <I> Input type.
     * @param <O> Output type.
     * @return Response received from the process.
     */
    public synchronized <I, O> O sendReceiveMessage(I input, Class<O> outputType) {
        try {
            String inputText = this.jsonSerializer.serialize(input);

// NOTE: uncomment the following line if you are testing, and you want to see the input json that was sent to the python process
//            System.out.println(inputText);

            byte[] inputBytes = inputText.getBytes(StandardCharsets.UTF_8);
			this.writeToProcessStream.write(inputBytes);
            this.writeToProcessStream.flush();
            this.writeToProcessStream.write(PYTHON_BUFFER_SPACE);  // python buffer flush overflow to avoid blocking...
			this.writeToProcessStream.flush();

            CompletableFuture<O> receiveResponseFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    ObjectMapper objectMapper = this.jsonSerializer.getMapper();
                    O deserializedResponse = objectMapper.readValue(this.jsonParser, outputType);

                    return deserializedResponse;
                } catch (Exception ex) {
                    if (!this.outputDetectedOnStderrFuture.isDone()) {
                        throw new PythonExecutionException("Failed to parse a response from a python process", ex);
                    }
                    else {
                        return null;
                    }
                }
            });

            CompletableFuture<Object> completedFuture = CompletableFuture.anyOf(receiveResponseFuture, this.processFinishedErrorFuture, this.processFinishedSuccessFuture);
            try {
                Object futureResult = completedFuture.get(this.timeoutSeconds, TimeUnit.SECONDS);
            }
            catch (TimeoutException tex) {
                if (!this.outputDetectedOnStderrFuture.isDone()) {
                    String errStreamText = this.errorStream.toString(StandardCharsets.UTF_8);
                    throw new PythonExecutionException("Python command " + this.commandLineText + " failed with a timeout, stderr:\n" + errStreamText);
                }
            }

            if (this.outputDetectedOnStderrFuture.isDone() || processFinishedErrorFuture.isDone() || processFinishedSuccessFuture.isDone()) {
                String errStreamText = this.errorStream.toString(StandardCharsets.UTF_8);
                throw new PythonExecutionException("Python command " + this.commandLineText + " failed, stderr:\n" + errStreamText);
            }

            O result = receiveResponseFuture.get(1, TimeUnit.MILLISECONDS);
            return result;
        } catch (PythonExecutionException ex) {
            throw ex;
        } catch (Exception e) {
            throw new PythonExecutionException("Failed to a process", e);
        }
    }

    /**
     * Starts a process in the background.
     * @param pythonVirtualEnv Python virtual environment.
     */
    public void startProcess(PythonVirtualEnv pythonVirtualEnv) {
        try {
            CommandLine cmdLine = CommandLine.parse(this.commandLineText);

			this.processFinishedErrorFuture = new CompletableFuture<>();
			this.processFinishedSuccessFuture = new CompletableFuture<>();
			this.writeToProcessStream = new PipedOutputStream();
			this.writeToProcessStreamProcessSide = new PipedInputStream(this.writeToProcessStream);
			this.readFromProcessStreamProcessSide = new PipedOutputStream();
			this.readFromProcessStream = new PipedInputStream(this.readFromProcessStreamProcessSide);

			this.readFromProcessStreamReader = new InputStreamReader(new BufferedInputStream(readFromProcessStream, PYTHON_BUFFER_SIZE));
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
							this.writeToProcessStream.close();
							this.writeToProcessStreamProcessSide.close();
							this.readFromProcessStream.close();
							this.readFromProcessStreamProcessSide.close();

                            String errStreamText = this.errorStream.toString(StandardCharsets.UTF_8);
                            log.error("Python process failed with an error, the error captured from the stderr: " + errStreamText);
                        }
                        catch (Exception ioe) {
                            log.error("Python process failed with an error and we cannot close the stream: " + ioe.getMessage(), ioe);
                        }
                    });

            PumpStreamHandler streamHandler = new FlushingPumpStreamHandler(
                    new FlushingOutputStream(this.readFromProcessStreamProcessSide),
                    //errorOutputStream, // we can use the System.stderr instead to push the errors directly to our error stream
                    System.err,
					this.writeToProcessStreamProcessSide);
			this.executor = new DefaultExecutor();
			this.executor.setStreamHandler(streamHandler);

            Map<String, String> systemEnvVariables = System.getenv();
            HashMap<String, String> processEnvVariables = new HashMap<>(systemEnvVariables);
            processEnvVariables.put("PYTHONUNBUFFERED", "1");
            processEnvVariables.put("PYTHONIOENCODING", "utf-8");
            processEnvVariables.remove("PYTHONHOME");
            for (Map.Entry<String, String> venvEnvironmentVarKeyValPair : pythonVirtualEnv.getEnvironmentVariables().entrySet()) {
                processEnvVariables.put(venvEnvironmentVarKeyValPair.getKey(), venvEnvironmentVarKeyValPair.getValue());
            }

			this.executor.execute(cmdLine, processEnvVariables, this);
        }
        catch (Exception ex) {
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
    }

    /**
     * Process has failed.
     * @param e Exception from the process.
     */
    @Override
    public void onProcessFailed(ExecuteException e) {
        log.error("Python process failed, error: " + e.getMessage(), e);
		this.processFinishedErrorFuture.complete(e);
    }

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     */
    @Override
    public void close() {
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
    }
}
