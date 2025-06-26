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

import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoPythonConfigurationProperties;
import com.dqops.core.jobqueue.concurrency.ParallelJobLimitProvider;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.serialization.JsonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;

/**
 * Service that starts python to execute a givens script.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class PythonCallerServiceImpl implements PythonCallerService, DisposableBean {
    private final DqoConfigurationProperties configurationProperties;
    private final DqoPythonConfigurationProperties pythonConfigurationProperties;
    private final JsonSerializer jsonSerializer;
    private final PythonVirtualEnvService pythonVirtualEnvService;
    private final ParallelJobLimitProvider parallelJobLimitProvider;
    private Map<String, PythonScriptProcesses> pythonModuleProcesses = new LinkedHashMap<>();
    private final Object processDictionaryLock = new Object();
    private Integer maxProcessesPerScript;

    /**
     * Default injection constructor.
     * @param configurationProperties Configuration properties with the DQOps Home path.
     * @param pythonConfigurationProperties DQOps python configuration properties.
     * @param jsonSerializer Json serializer.
     * @param pythonVirtualEnvService Python virtual environment management service.
     */
    @Autowired
    public PythonCallerServiceImpl(DqoConfigurationProperties configurationProperties,
                                   DqoPythonConfigurationProperties pythonConfigurationProperties,
                                   JsonSerializer jsonSerializer,
                                   PythonVirtualEnvService pythonVirtualEnvService,
                                   ParallelJobLimitProvider parallelJobLimitProvider){
        this.configurationProperties = configurationProperties;
        this.pythonConfigurationProperties = pythonConfigurationProperties;
        this.jsonSerializer = jsonSerializer;
        this.pythonVirtualEnvService = pythonVirtualEnvService;
        this.parallelJobLimitProvider = parallelJobLimitProvider;
    }

    /**
     * Returns a maximum degree of parallelism, which is the number of scripts of one type that can be started at a time.
     * It is never higher than the number of CPU cores.
     * @return The maximum number of processes of one type to run.
     */
    public int getMaxProcessesPerScript() {
        if (this.maxProcessesPerScript != null) {
            return this.maxProcessesPerScript;
        }

        if (this.parallelJobLimitProvider != null) {
            int maxDegreeOfParallelism = this.parallelJobLimitProvider.getMaxDegreeOfParallelism();
            this.maxProcessesPerScript = Math.min(maxDegreeOfParallelism, Runtime.getRuntime().availableProcessors());
        } else {
            this.maxProcessesPerScript = 1;
        }
        
        return this.maxProcessesPerScript;
    }

    /**
     * Serializes a collection of input objects to a multi-json (EOL separated) file content.
     * @param inputs Collection of objects to be serialized.
     * @param <I> Input object type.
     * @return Combined file to be returned.
     */
    protected <I> String serializeInputs(Collection<I> inputs) {
        StringBuilder sb = new StringBuilder();
        for (I element : inputs) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            String serializedObject = this.jsonSerializer.serialize(element);
            sb.append(serializedObject);
        }

        return sb.toString();
    }

    /**
     * Resolves an absolute file path to a file inside the DQO_HOME folder.
     * @param relativeFilePath Relative file path.
     * @return Absolute file path.
     */
    protected String resolveAbsolutePathToHomeFile(String relativeFilePath) {
        String homeDir = this.configurationProperties.getHome();
        Path absoluteFilePath = Path.of(homeDir).resolve(relativeFilePath).toAbsolutePath();
        return absoluteFilePath.toString();
    }

    /**
     * Executes a python script in the DQO_HOME folder.
     *
     * @param input                Input objects to serialize to JSON and feed to the standard input of the python script.
     * @param pythonFilePathInHome Path to the python module (.py file) that is relative to the DQO_HOME folder.
     * @param outputType           Output type to parse the returned json objects.
     * @return Output object that were received for each input object.
     */
    @Override
    public <I, O> O executePythonHomeScript(I input, String pythonFilePathInHome, Class<O> outputType) {
        PythonScriptProcesses availableProcesses = null;
        StreamingPythonProcess streamingPythonProcess = null;

        synchronized (this.processDictionaryLock) {
            for (int retry = 0; retry < 10000000; retry++) {
                if (this.pythonModuleProcesses == null) {
                    throw new PythonExecutionException("Python script cannot be called, because Python processes were already closed and the application is shutting down."); // closing
                }

                availableProcesses = this.pythonModuleProcesses.get(pythonFilePathInHome);
                if (availableProcesses == null) {
                    availableProcesses = new PythonScriptProcesses();
                    this.pythonModuleProcesses.put(pythonFilePathInHome, availableProcesses);
                }

                int maxDoP = getMaxProcessesPerScript();
                if (!availableProcesses.incrementRunningProcesses(maxDoP)) {
                    try {
                        this.processDictionaryLock.wait();
                        continue; // try again
                    } catch (InterruptedException e) {
                        throw new DqoRuntimeException(e);
                    }
                }

                if (availableProcesses.getAvailableProcesses().isEmpty()) {
                    String absolutePythonPath = resolveAbsolutePathToHomeFile(pythonFilePathInHome);
                    PythonVirtualEnv virtualEnv = this.pythonVirtualEnvService.getVirtualEnv();
                    String commandLineText = String.format(
                            "\"%s\" -u \"%s\"",
                            virtualEnv.getPythonInterpreterPath(),
                            absolutePythonPath
                    );
                    streamingPythonProcess = new StreamingPythonProcess(this.jsonSerializer, commandLineText, this.pythonConfigurationProperties.getPythonScriptTimeoutSeconds());
                    streamingPythonProcess.startProcess(virtualEnv);
                } else {
                    streamingPythonProcess = availableProcesses.getAvailableProcesses().pop();
                }

                if (streamingPythonProcess.isClosed()) {
                    availableProcesses.decrementRunningProcesses();
                    continue; // try again, because this process has exited (killed?)
                } else {
                    break; // we can use this process
                }
            }
        }

        if (streamingPythonProcess == null) {
            // no way to get a process, memory issues?
            throw new PythonExecutionException("Cannot start another Python process to run " + pythonFilePathInHome + ", processes are killed or the MaxDOP is too low");
        }

        try {
            O receiveMessage = streamingPythonProcess.sendReceiveMessage(input, outputType);
            synchronized (this.processDictionaryLock) {
                availableProcesses.getAvailableProcesses().add(streamingPythonProcess); // put it back for the next call
                availableProcesses.decrementRunningProcesses();
                this.processDictionaryLock.notify();
            }
            return receiveMessage;
        }
        catch (Exception ex) {
            // when the process fails, we want to start a new process, we do not add it back
            synchronized (this.processDictionaryLock) {
                availableProcesses.decrementRunningProcesses();
                this.processDictionaryLock.notify();
            }

            try {
                streamingPythonProcess.close();
            }
            catch (Exception exc) {
                log.error("Python process cannot be stopped: " + exc.getMessage() + " when running " + pythonFilePathInHome + " Python file", ex);
            }

            log.error("Python process failed: " + ex.getMessage() + " when running " + pythonFilePathInHome + " Python file", ex);
            throw new PythonExecutionException("Python process failed: " + ex.getMessage() + " when running " + pythonFilePathInHome + " Python file", ex);
        }
    }

    /**
     * Executes a python script in the DQO_HOME folder in an profiling mode. A new process is started, inputs sent, outputs received and the process finishes.
     * @param inputs List of input objects to serialize to JSON and feed to the standard input of the python script.
     * @param pythonFilePathInHome Path to the python module (.py file) that is relative to the DQO_HOME folder.
     * @param outputType Output type to parse the returned json objects.
     * @param <I> Input type.
     * @param <O> Output type.
     * @return List of output objects that were received for each input object.
     */
    public <I, O> List<O> executePythonHomeScriptAndFinish(Collection<I> inputs, String pythonFilePathInHome, Class<O> outputType) {
        try {
            String fullInputText = serializeInputs(inputs);
            String absolutePythonPath = resolveAbsolutePathToHomeFile(pythonFilePathInHome);
            PythonVirtualEnv virtualEnv = this.pythonVirtualEnvService.getVirtualEnv();
            String commandLineText = String.format(
                    "\"%s\" -u \"%s\"",
                    virtualEnv.getPythonInterpreterPath(),
                    absolutePythonPath
            );
            CommandLine cmdLine = CommandLine.parse(commandLineText);

            ByteArrayInputStream inputStream = new ByteArrayInputStream(fullInputText.getBytes(StandardCharsets.UTF_8));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errStream = new ByteArrayOutputStream();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errStream, inputStream);
            DefaultExecutor executor = new DefaultExecutor();
            executor.setStreamHandler(streamHandler);

            try {
                int exitCode = executor.execute(cmdLine);
                if (exitCode != 0) {
                    throw new PythonExecutionException("Python script returned an error code " + exitCode);
                }
            }
            catch (Exception ex) {
                String outputStreamText = outputStream.toString(StandardCharsets.UTF_8);
                String errStreamText = errStream.toString(StandardCharsets.UTF_8);
                throw new PythonExecutionException("Python script " + absolutePythonPath + " failed, stderr: " + errStreamText +
                        ", stdout: " + outputStreamText);
            }

            byte[] receivedByteOutput = outputStream.toByteArray();
            try (InputStreamReader receivedInputReader = new InputStreamReader(
                    new ByteArrayInputStream(receivedByteOutput), StandardCharsets.UTF_8)) {
                List<O> results = this.jsonSerializer.deserializeMultiple(receivedInputReader, outputType);
                return results;
            }
        } catch (IOException e) {
            log.error("Python process failed: " + e.getMessage(), e);
            throw new PythonExecutionException("Failed to execute python script " + pythonFilePathInHome, e);
        }
    }

    /**
     * Called when DQOps stops. Stops all python processes.
     * @throws Exception Exception thrown when any process failed to stop.
     */
    @Override
    public void destroy() throws Exception {
        Throwable firstException = null;

        ArrayList<StreamingPythonProcess> allProcesses = new ArrayList<>();

        synchronized (this.processDictionaryLock) {
            for (PythonScriptProcesses processesStack : this.pythonModuleProcesses.values()) {
                allProcesses.addAll(processesStack.getAvailableProcesses());
            }
            this.pythonModuleProcesses = null;
        }

        for (StreamingPythonProcess pythonProcess : allProcesses) {
            try {
                pythonProcess.announceClose();
            }
            catch (Throwable t) {
                if (firstException == null) {
                    firstException = t;
                }
            }
        }

        for (StreamingPythonProcess pythonProcess : allProcesses) {
            try {
                pythonProcess.close();
            }
            catch (Throwable t) {
                if (firstException == null) {
                    firstException = t;
                }
            }
        }

        if (firstException != null) {
            throw new PythonExecutionException("Cannot stop Python process, error: " + firstException.getMessage(), firstException);
        }
    }
}
