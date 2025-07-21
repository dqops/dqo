/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.python;

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
