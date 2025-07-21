/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.exceptions;

import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.core.configuration.DqoCoreConfigurationProperties;
import com.dqops.utils.exceptions.DqoErrorUserMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.parquet.Strings;
import org.jline.reader.UserInterruptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;

/**
 * Command error handler.
 */
@Slf4j
public class CommandExecutionErrorHandler implements CommandLine.IExecutionExceptionHandler {
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

        log.error("Command " + parseResult.toString() + " failed", e);
        return -1;
    }
}
