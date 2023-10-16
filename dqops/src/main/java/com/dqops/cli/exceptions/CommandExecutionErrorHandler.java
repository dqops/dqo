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
package com.dqops.cli.exceptions;

import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.core.configuration.DqoCoreConfigurationProperties;
import com.dqops.utils.exceptions.DqoErrorUserMessage;
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

    private final TerminalFactory terminalFactory;
    private final DqoCoreConfigurationProperties coreConfigurationProperties;

    /**
     * Command error handler.
     * @param terminalFactory Terminal writer factory.
     * @param coreConfigurationProperties DQOps Core configuration properties.
     */
    @Autowired
    public CommandExecutionErrorHandler(TerminalFactory terminalFactory, DqoCoreConfigurationProperties coreConfigurationProperties) {
        this.terminalFactory = terminalFactory;
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
    public int handleExecutionException(Exception e, CommandLine commandLine, CommandLine.ParseResult parseResult) {
        if (e instanceof CliRequiredParameterMissingException) {
            CliRequiredParameterMissingException parameterMissingException = (CliRequiredParameterMissingException)e;
			this.terminalFactory.getWriter().writeLine("Missing required parameter: " + parameterMissingException.getParameterName());
            String usageMessage = commandLine.getUsageMessage();
			this.terminalFactory.getWriter().writeLine(usageMessage);

            return -2;
        }

        if (e instanceof UserInterruptException) {
            return -1; // ignore, user clicked "esc"
        }

        String exceptionMessage = e.getMessage();
        if (Strings.isNullOrEmpty(exceptionMessage)) {
			this.terminalFactory.getWriter().writeLine("Command failed");
        }
        else {
			this.terminalFactory.getWriter().writeLine("Command failed, error message: " + exceptionMessage);
        }

        if (this.coreConfigurationProperties.isPrintStackTrace()) {
            e.printStackTrace();
        } else {
            Throwable innerCause = e;
            while (innerCause != null && !(innerCause instanceof DqoErrorUserMessage)) {
                innerCause = innerCause.getCause();
            }
            if (innerCause != null) {
                System.err.println(((DqoErrorUserMessage) innerCause).getUserFriendlyMessage());
            }
        }

        LOG.debug("Command " + parseResult.toString() + " failed", e);
        return -1;
    }
}
