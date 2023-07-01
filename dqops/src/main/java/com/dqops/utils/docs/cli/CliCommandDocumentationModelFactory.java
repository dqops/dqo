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
package com.dqops.utils.docs.cli;

import picocli.CommandLine;

/**
 * Documentation factory that creates documentation of a single CLI command.
 */
public interface CliCommandDocumentationModelFactory {
    /**
     * Generates a documentation for a single command. May return null for commands that are just intermediate nodes in the command tree
     * and are not callable.
     *
     * @param commandLine Command line for the command.
     * @return Command documentation or null if the command is not callable.
     */
    CliCommandDocumentationModel makeDocumentationForCommand(CommandLine commandLine);
}
