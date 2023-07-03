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

import lombok.Data;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Documentation model about one single CLI command.
 */
@Data
public class CliCommandDocumentationModel {
    /**
     * Full command name. For example: "connection list".
     */
    private String qualifiedName;

    /**
     * Command header lines.
     */
    private String[] header;

    /**
     * Command description lines.
     */
    private String[] description;

    /**
     * Command synopsis (command structure).
     */
    private String synopsis;

    /**
     * Custom usage structure.
     */
    private String[] customSynopsis;

    /**
     * Picocli command line object, for reference.
     */
    private CommandLine commandLine;

    /**
     * Command specification.
     */
    private CommandLine.Model.CommandSpec commandSpec;

    /**
     * Command help.
     */
    private CommandLine.Help help;

    /**
     * Usage message.
     */
    private CommandLine.Model.UsageMessageSpec usageMessageSpec;

    /**
     * List of command options.
     */
    private List<CliOptionDocumentationModel> options = new ArrayList<>();
}
