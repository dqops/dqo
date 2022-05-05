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
package ai.dqo.cli.exceptions;

import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.core.configuration.DqoCoreConfigurationProperties;
import org.apache.parquet.Strings;
import org.jline.reader.UserInterruptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;

/**
 * Command error handler.
 */
public class CommandExecutionErrorHandler implements CommandLine.IExecutionExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CommandExecutionErrorHandler.class);

    private final TerminalWriter terminalWriter;
    private final DqoCoreConfigurationProperties coreConfigurationProperties;

    /**
     * Command error handler.
     * @param terminalWriter Terminal writer.
     * @param coreConfigurationProperties core configuration properties.
     */
    @Autowired
    public CommandExecutionErrorHandler(TerminalWriter terminalWriter,
										DqoCoreConfigurationProperties coreConfigurationProperties) {
        this.terminalWriter = terminalWriter;
        this.coreConfigurationProperties = coreConfigurationProperties;
    }

    /**
     * Handles an error.
     * @param e Exception.
     * @param commandLine Command line.
     * @param parseResult Command parse result.
     * @return Command line.
     * @throws Exception Exception.
     */
    @Override
    public int handleExecutionException(Exception e, CommandLine commandLine, CommandLine.ParseResult parseResult) throws Exception {
        if (e instanceof CliRequiredParameterMissingException) {
            CliRequiredParameterMissingException parameterMissingException = (CliRequiredParameterMissingException)e;
			this.terminalWriter.writeLine("Missing required parameter: " + parameterMissingException.getParameterName());
            String usageMessage = commandLine.getUsageMessage();
			this.terminalWriter.writeLine(usageMessage);

            return -2;
        }

        if (e instanceof UserInterruptException) {
            return -1; // ignore, user clicked "esc"
        }

        String exceptionMessage = e.getMessage();
        if (Strings.isNullOrEmpty(exceptionMessage)) {
			this.terminalWriter.writeLine("Command failed");
        }
        else {
			this.terminalWriter.writeLine("Command failed, error message: " + exceptionMessage);
            if (this.coreConfigurationProperties.isPrintStackTrace()) {
                e.printStackTrace();
            }
        }

		LOG.debug("Command " + parseResult.toString() + " failed", e);
        return -1;
    }
}
