/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.rest.models.remote;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Connection Status Model returned from REST API. Describes a connection status for the source database with specified connection.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "ConnectionRemoteModel", description = "Connection status remote management")
public class ConnectionTestModel {

    @JsonPropertyDescription("Connection status")
    private ConnectionStatusRemote connectionStatus;

    @JsonPropertyDescription("Error message")
    private String errorMessage;
}