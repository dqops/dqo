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
package ai.dqo.rest.models.checks;

import ai.dqo.metadata.fields.ParameterDefinitionSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * Model of a single field that is used to edit a parameter value for a sensor or a rule. Describes the type of the field and the current value.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UIFieldModel", description = "Model of a single field that is used to edit a parameter value for a sensor or a rule. Describes the type of the field and the current value.")
public class UIFieldModel {
    @JsonPropertyDescription("Field name that matches the field name (snake_case) used in the YAML specification.")
    private ParameterDefinitionSpec definition;

    @JsonPropertyDescription("Field value for a string field.")
    private String stringValue;

    @JsonPropertyDescription("Field value for a boolean field.")
    private Boolean booleanValue;

    @JsonPropertyDescription("Field value for an integer (32-bit) field.")
    private Integer integerValue;

    @JsonPropertyDescription("Field value for a long (64-bit) field.")
    private Long longValue;

    @JsonPropertyDescription("Field value for a double field.")
    private Double doubleValue;

    @JsonPropertyDescription("Field value for a datetime field.")
    private Instant dateTimeValue;

    @JsonPropertyDescription("Field value for a column name field.")
    private String columnNameValue;

    @JsonPropertyDescription("Field value for an enum (choice) field.")
    private String enumValue;

    @JsonPropertyDescription("Field value for an array (list) of strings.")
    private List<String> stringListValue;
}
