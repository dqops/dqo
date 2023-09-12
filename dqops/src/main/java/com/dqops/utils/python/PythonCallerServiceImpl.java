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
import com.dqops.utils.serialization.JsonSerializer;
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
public class PythonCallerServiceImpl implements PythonCallerService, DisposableBean {
    private final DqoConfigurationProperties configurationProperties;
    private final DqoPythonConfigurationProperties pythonConfigurationProperties;
    private final JsonSerializer jsonSerializer;
    private final PythonVirtualEnvService pythonVirtualEnvService;
    private Map<String, Stack<StreamingPythonProcess>> pythonModuleProcesses = new HashMap<>();
    private final Object processDictionaryLock = new Object();

    /**
     * Default injection constructor.
     * @param configurationProperties Configuration properties with the DQO Home path.
     * @param pythonConfigurationProperties DQO python configuration properties.
     * @param jsonSerializer Json serializer.
     * @param pythonVirtualEnvService Python virtual environment management service.
     */
    @Autowired
    public PythonCallerServiceImpl(DqoConfigurationProperties configurationProperties,
                                   DqoPythonConfigurationProperties pythonConfigurationProperties,
                                   JsonSerializer jsonSerializer,
                                   PythonVirtualEnvService pythonVirtualEnvService){
        this.configurationProperties = configurationProperties;
        this.pythonConfigurationProperties = pythonConfigurationProperties;
        this.jsonSerializer = jsonSerializer;
        this.pythonVirtualEnvService = pythonVirtualEnvService;
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
        Stack<StreamingPythonProcess> availableProcessesStack = null;
        StreamingPythonProcess streamingPythonProcess = null;

        synchronized (this.processDictionaryLock) {
            availableProcessesStack = this.pythonModuleProcesses.get(pythonFilePathInHome);
            if (availableProcessesStack == null) {
                availableProcessesStack = new Stack<>();
                this.pythonModuleProcesses.put(pythonFilePathInHome, availableProcessesStack);
            }

            if (availableProcessesStack.isEmpty()) {
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
                streamingPythonProcess = availableProcessesStack.pop();
            }
        }

        try {
            O receiveMessage = streamingPythonProcess.sendReceiveMessage(input, outputType);
            synchronized (this.processDictionaryLock) {
                availableProcessesStack.add(streamingPythonProcess); // put it back for the next call
            }
            return receiveMessage;
        }
        catch (Exception ex) {
            // when the process fails, we want to stat a new process
            streamingPythonProcess.close();
            throw new PythonExecutionException("Python process failed: " + ex.getMessage(), ex);
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
            throw new PythonExecutionException("Failed to execute python script " + pythonFilePathInHome, e);
        }
    }

    /**
     * Called when DQO stops. Stops all python processes.
     * @throws Exception Exception thrown when any process failed to stop.
     */
    @Override
    public void destroy() throws Exception {
        Throwable firstException = null;

        synchronized (this.processDictionaryLock) {
            for (Stack<StreamingPythonProcess> processesStack : this.pythonModuleProcesses.values()) {
                StreamingPythonProcess[] processesToStop = processesStack.toArray(StreamingPythonProcess[]::new);
                for (StreamingPythonProcess pythonProcess : processesToStop) {
                    try {
                        pythonProcess.close();
                    }
                    catch (Throwable t) {
                        if (firstException == null) {
                            firstException = t;
                        }
                    }
                }
            }

            this.pythonModuleProcesses = null;
        }

        if (firstException != null) {
            throw new PythonExecutionException("Cannot stop Python process, error: " + firstException.getMessage(), firstException);
        }
    }
}
