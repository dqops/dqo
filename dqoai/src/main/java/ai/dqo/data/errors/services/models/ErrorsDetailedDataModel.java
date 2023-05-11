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
package ai.dqo.data.errors.services.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

/**
 * Error detailed statuses. Returned in the context of a single data-stream, with a supplied list of other data-streams.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class ErrorsDetailedDataModel {
    @JsonPropertyDescription("Check name.")
    private String checkName;
    @JsonPropertyDescription("Check display name.")
    private String checkDisplayName;
    @JsonPropertyDescription("Check type.")
    private String checkType;
    @JsonPropertyDescription("Check hash.")
    private Long checkHash;
    @JsonPropertyDescription("Check category name.")
    private String checkCategory;

    @JsonPropertyDescription("Data stream list.")
    private List<String> dataStreamNames;
    @JsonPropertyDescription("Selected data-stream.")
    private String dataStream;

    @JsonPropertyDescription("Single error statuses")
    private List<ErrorDetailedSingleModel> singleErrors;
}
