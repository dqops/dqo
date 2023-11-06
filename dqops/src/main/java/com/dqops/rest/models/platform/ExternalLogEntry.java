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
package com.dqops.rest.models.platform;

import com.dqops.utils.docs.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * External log entry that would be logged on the server.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ExternalLogEntry", description = "External log entry")
public class ExternalLogEntry {
    /**
     * window.location value at the time when the log entry was reported.
     */
    @JsonPropertyDescription("window.location value at the time when the log entry was reported.")
    private String windowLocation;

    /**
     * Log message that should be logged.
     */
    @JsonPropertyDescription("Log message that should be logged.")
    private String message;

    public static class ExternalLogEntrySampleFactory implements SampleValueFactory<ExternalLogEntry> {
        @Override
        public ExternalLogEntry createSample() {
            ExternalLogEntry externalLogEntry = new ExternalLogEntry();
            externalLogEntry.setWindowLocation("window.location");
            externalLogEntry.setMessage("Sample log message.");

            return externalLogEntry;
        }
    }
}
