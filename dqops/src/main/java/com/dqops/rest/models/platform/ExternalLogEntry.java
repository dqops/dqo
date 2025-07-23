/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.models.platform;

import com.dqops.utils.docs.generators.SampleValueFactory;
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
