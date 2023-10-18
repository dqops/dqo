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
package com.dqops.cli.commands;

import com.dqops.cli.completion.completers.OutputFormatCompleter;
import com.dqops.cli.exceptions.CliRequiredParameterMissingException;
import picocli.CommandLine;

/**
 * Base class for all commands. Exposes default parameters for all commands.
 */
public abstract class BaseCommand {
    /**
     * Default parameter to enable a headless (no user input allowed) mode. The default behavior of the command line is to prompt the user for all required parameters. When the --headless mode is enabled, user prompting is disabled and a lack of required parameter generates an error
     */
    @CommandLine.Option(names = {"--headless", "-hl"},
            description = "Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start " +
                    "because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to " +
                    "approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.", defaultValue = "false")
    private boolean headless;

    @CommandLine.Option(names = {"-h", "--help"}, description = "Show the help for the command and parameters", required = false, usageHelp = true)
    private boolean help;

    @CommandLine.Option(names = {"-of", "--output-format"}, description = "Output format for tabular responses", required = false,
            completionCandidates = OutputFormatCompleter.class)
    private TabularOutputFormat outputFormat = TabularOutputFormat.TABLE;

    @CommandLine.Option(names = {"-fw", "--file-write"}, description = "Write command response to a file", required = false)
    private boolean writeToFile = false;

    /**
     * Throws a {@link CliRequiredParameterMissingException} exception because a <code>parameterName</code> is missing. Otherwise, the method silently passes through
     * and the command code can prompt the user to provide a parameter value.
     * @param parameterName Parameter name that is missing.
     */
    public void throwRequiredParameterMissingIfHeadless(String parameterName) {
        if (this.isHeadless()) {
            throw new CliRequiredParameterMissingException(parameterName);
        }
    }

    /**
     * Get the headless mode switch.
     * @return True when the command should be executed in a headless mode.
     */
    public boolean isHeadless() {
        return headless;
    }

    /**
     * Changes the value of the headless mode.
     * @param headless Headless mode value.
     */
    public void setHeadless(boolean headless) {
        this.headless = headless;
    }

    /**
     * True when a help was requested (but the command is not actually executed and the picocli engine automatically shows the help).
     * @return Help was requested.
     */
    public boolean isHelp() {
        return help;
    }

    /**
     * Sets the help parameter value.
     * @param help Help parameter value.
     */
    public void setHelp(boolean help) {
        this.help = help;
    }

    /**
     * Returns the output format for tabular results.
     * @return Tabular output format.
     */
    public TabularOutputFormat getOutputFormat() {
        return outputFormat;
    }

    /**
     * Sets the output format for a tabular data.
     * @param outputFormat New tabular output format.
     */
    public void setOutputFormat(TabularOutputFormat outputFormat) {
        this.outputFormat = outputFormat;
    }

    /**
     * True when a cli command response will be written to a file.
     * @return Write to file boolean value.
     */
    public boolean isWriteToFile() {
        return writeToFile;
    }

    /**
     * Sets the write to file parameter value.
     * @param writeToFile Write to file parameter value.
     */
    public void setWriteToFile(boolean writeToFile) {
        this.writeToFile = writeToFile;
    }
}
