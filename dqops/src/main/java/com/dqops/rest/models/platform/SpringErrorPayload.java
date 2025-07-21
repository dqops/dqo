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

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Object mapped to the default spring error payload (key/values).
 */
@Data
@ApiModel(value = "SpringErrorPayload", description = "Spring error payload that identifies the fields in the error returned by the REST API in case of unexpected errors (exceptions).")
public class SpringErrorPayload {
    @JsonPropertyDescription("Error timestamp as an epoch timestamp.")
    public Long timestamp;

    @JsonPropertyDescription("Optional status code.")
    public Integer status;

    @JsonPropertyDescription("Error name.")
    public String error;

    @JsonPropertyDescription("Optional exception.")
    public String exception;

    @JsonPropertyDescription("Exception's message.")
    public String message;

    @JsonPropertyDescription("Exception's stack trace (optional).")
    public String path;
}
