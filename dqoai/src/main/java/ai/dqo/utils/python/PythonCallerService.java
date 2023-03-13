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

import java.util.Collection;
import java.util.List;

/**
 * Service that starts python to execute a givens script.
 */
public interface PythonCallerService {
    /**
     * Executes a python script in the DQO_HOME folder.
     * @param input Input objects to serialize to JSON and feed to the standard input of the python script.
     * @param pythonFilePathInHome Path to the python module (.py file) that is relative to the DQO_HOME folder.
     * @param outputType Output type to parse the returned json objects.
     * @param <I> Input type.
     * @param <O> Output type.
     * @return Output object that were received for each input object.
     */
    <I, O> O executePythonHomeScript(I input, String pythonFilePathInHome, Class<O> outputType);

    /**
     * Executes a python script in the DQO_HOME folder in a profiling mode. A new process is started, inputs sent, outputs received and the process finishes.
     * @param inputs List of input objects to serialize to JSON and feed to the standard input of the python script.
     * @param pythonFilePathInHome Path to the python module (.py file) that is relative to the DQO_HOME folder.
     * @param outputType Output type to parse the returned json objects.
     * @param <I> Input type.
     * @param <O> Output type.
     * @return List of output objects that were received for each input object.
     */
    <I, O> List<O> executePythonHomeScriptAndFinish(Collection<I> inputs, String pythonFilePathInHome, Class<O> outputType);
}
