/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.execution.checks;

import ai.dqo.metadata.search.CheckSearchFilters;
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
            if (failedAt.getConnectionName() != null) {
                messageBuilder
                        .append("\tConnection:\t")
                        .append(failedAt.getConnectionName())
                        .append("\n");
            }
            if (failedAt.getSchemaTableName() != null) {
                messageBuilder
                        .append("\tSchema.Table:\t")
                        .append(failedAt.getSchemaTableName())
                        .append("\n");
            }
            if (failedAt.getColumnName() != null) {
                messageBuilder
                        .append("\tColumn:\t")
                        .append(failedAt.getColumnName())
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
