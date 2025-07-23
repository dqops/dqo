/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.execution.checks;

import com.dqops.metadata.search.CheckSearchFilters;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Container for throwables that appeared during check execution, along with the location of the event.
 */
public class CheckExecutionErrorSummary {
    private final Throwable cause;
    private final CheckSearchFilters failedAt;

    public CheckExecutionErrorSummary(Throwable cause, CheckSearchFilters failedAt) {
        this.cause = cause;
        this.failedAt = failedAt;
    }

    public Throwable getCause() {
        return cause;
    }

    public CheckSearchFilters getFailedAt() {
        return failedAt;
    }
    
    public String getSummaryMessage() {
        StringBuilder messageBuilder = new StringBuilder("Check execution error");
        if (failedAt != null) {
            messageBuilder.append(" at\n");
            if (failedAt.getConnection() != null) {
                messageBuilder
                        .append("\tConnection:\t")
                        .append(failedAt.getConnection())
                        .append("\n");
            }
            if (failedAt.getFullTableName() != null) {
                messageBuilder
                        .append("\tSchema.Table:\t")
                        .append(failedAt.getFullTableName())
                        .append("\n");
            }
            if (failedAt.getColumn() != null) {
                messageBuilder
                        .append("\tColumn:\t")
                        .append(failedAt.getColumn())
                        .append("\n");
            }
            if (failedAt.getCheckName() != null) {
                messageBuilder
                        .append("\tCheck:\t")
                        .append(failedAt.getCheckName())
                        .append("\n");
            }
            if (failedAt.getSensorName() != null) {
                messageBuilder
                        .append("\tSensor:\t")
                        .append(failedAt.getSensorName())
                        .append("\n");
            }
        }
        else if (cause != null) {
            messageBuilder.append(" ");
        }

        if (cause != null) {
            messageBuilder
                    .append("with message: ")
                    .append(cause.getMessage());
        }
        return messageBuilder.toString();
    }

    public String getDebugMessage() {
        StringBuilder messageBuilder = new StringBuilder(getSummaryMessage());
        if (cause != null) {
            messageBuilder.append("\nTrace:\n").append(ExceptionUtils.getStackTrace(cause));
        }
        return messageBuilder.toString();
    }
}
