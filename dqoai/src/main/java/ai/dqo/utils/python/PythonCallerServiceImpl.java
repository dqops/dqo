/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.utils.python;

import ai.dqo.core.configuration.DqoConfigurationProperties;
import ai.dqo.utils.serialization.JsonSerializer;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service that starts python to execute a givens script.
 */
@Component
public class PythonCallerServiceImpl implements PythonCallerService {
    private final DqoConfigurationProperties configurationProperties;
    private final JsonSerializer jsonSerializer;
    private final PythonVirtualEnvService pythonVirtualEnvService;
    private final Map<String, StreamingPythonProcess> pythonModuleProcesses = new HashMap<>();
    private final Object processStartLock = new Object();

    /**
     * Default injection constructor.
     * @param configurationProperties DQO configuration properties.
     * @param jsonSerializer Json serializer.
     * @param pythonVirtualEnvService Python virtual environment management service.
     */
    @Autowired
    public PythonCallerServiceImpl(DqoConfigurationProperties configurationProperties,
								   JsonSerializer jsonSerializer,
								   PythonVirtualEnvService pythonVirtualEnvService){
        this.configurationProperties = configurationProperties;
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
        StreamingPythonProcess streamingPythonProcess = null;

        synchronized (this.processStartLock) {
            streamingPythonProcess = this.pythonModuleProcesses.get(pythonFilePathInHome);
            if (streamingPythonProcess == null) {
                String absolutePythonPath = resolveAbsolutePathToHomeFile(pythonFilePathInHome);
                PythonVirtualEnv virtualEnv = this.pythonVirtualEnvService.getVirtualEnv();
                String commandLineText = virtualEnv.getPythonInterpreterPath() + " -u \"" + absolutePythonPath + "\"";
                streamingPythonProcess = new StreamingPythonProcess(this.jsonSerializer, commandLineText, this.configurationProperties.getPython().getPythonScriptTimeoutSeconds());
                streamingPythonProcess.startProcess(virtualEnv);
				this.pythonModuleProcesses.put(pythonFilePathInHome, streamingPythonProcess);
            }
        }

        try {
            O receiveMessage = streamingPythonProcess.sendReceiveMessage(input, outputType);
            return receiveMessage;
        }
        catch (Exception ex) {
            // when the process fails, we want to stat a new process
            synchronized (this.processStartLock) {
				this.pythonModuleProcesses.remove(pythonFilePathInHome);
                streamingPythonProcess.close();
            }

            throw new PythonExecutionException("Python process failed: " + ex.getMessage(), ex);
        }
    }

    /**
     * Executes a python script in the DQO_HOME folder in an ad-hoc mode. A new process is started, inputs sent, outputs received and the process finishes.
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
            String commandLineText = virtualEnv.getPythonInterpreterPath() + " \"" + absolutePythonPath + "\"";
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
}
