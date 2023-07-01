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
