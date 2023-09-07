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