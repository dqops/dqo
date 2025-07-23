/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.models.remote;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Connection test status result model returned from REST API. Describes the status of testing a connection
 * (opening a connection to verify if it usable, credentials are approved and the access was granted by the tested data source).
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "ConnectionTestModel", description = "Connection test status result")
public class ConnectionTestModel {
    /**
     * Connection test result.
     */
    @JsonPropertyDescription("Connection test result")
    private ConnectionTestStatus connectionTestResult;

    /**
     * Optional error message when the status is not "SUCCESS".
     */
    @JsonPropertyDescription("Optional error message when the status is not \"SUCCESS\"")
    private String errorMessage;
}