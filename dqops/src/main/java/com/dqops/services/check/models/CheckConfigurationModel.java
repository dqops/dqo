/*
 * Copyright © 2023 DQOps (support@dqops.com)
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

package com.dqops.services.check.models;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.services.check.mapping.models.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Model containing fundamental configuration of a single data quality check.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckConfigurationModel", description = "Model containing fundamental configuration of a single data quality check.")
public class CheckConfigurationModel {

    /**
     * Connection name.
     */
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    /**
     * Schema name.
     */
    @JsonPropertyDescription("Schema name.")
    private String schemaName;

    /**
     * Table name.
     */
    @JsonPropertyDescription("Table name.")
    private String tableName;

    /**
     * Column name, if the check is set up on a column.
     */
    @JsonPropertyDescription("Column name, if the check is set up on a column.")
    private String columnName;

    /**
     * Check target (table or column).
     */
    @JsonPropertyDescription("Check target (table or column).")
    private CheckTarget checkTarget;

    /**
     * Check type (profiling, monitoring, partitioned).
     */
    @JsonPropertyDescription("Check type (profiling, monitoring, partitioned).")
    private CheckType checkType;

    /**
     * Check timescale (for monitoring and partitioned checks).
     */
    @JsonPropertyDescription("Check timescale (for monitoring and partitioned checks).")
    private CheckTimeScale checkTimeScale;

    /**
     * Category to which this check belongs.
     */
    @JsonPropertyDescription("Category to which this check belongs.")
    private String categoryName;

    /**
     * Check name that is used in YAML file.
     */
    @JsonPropertyDescription("Check name that is used in YAML file.")
    private String checkName;

    /**
     * List of fields for editing the sensor parameters.
     */
    @JsonPropertyDescription("List of fields for editing the sensor parameters.")
    private List<FieldModel> sensorParameters = new ArrayList<>();

    /**
     * SQL WHERE clause added to the sensor query for every check on this table.
     */
    @JsonPropertyDescription("SQL WHERE clause added to the sensor query for every check on this table.")
    private String tableLevelFilter;

    /**
     * SQL WHERE clause added to the sensor query for this check.
     */
    @JsonPropertyDescription("SQL WHERE clause added to the sensor query for this check.")
    private String sensorLevelFilter;

    /**
     * Rule parameters for the warning severity rule.
     */
    @JsonPropertyDescription("Rule parameters for the warning severity rule.")
    private RuleParametersModel warning;

    /**
     * Rule parameters for the error severity rule.
     */
    @JsonPropertyDescription("Rule parameters for the error severity rule.")
    private RuleParametersModel error;

    /**
     * Rule parameters for the fatal severity rule.
     */
    @JsonPropertyDescription("Rule parameters for the fatal severity rule.")
    private RuleParametersModel fatal;

    /**
     * Whether the check has been disabled.
     */
    @JsonPropertyDescription("Whether the check has been disabled.")
    private boolean disabled;

    /**
     * Whether the check is configured (not null).
     */
    @JsonPropertyDescription("Whether the check is configured (not null).")
    private boolean configured;

    /**
     * Sensor parameters, returned only for reference and for tools such as the documentation generator.
     */
    @JsonIgnore
    private AbstractSensorParametersSpec sensorParametersSpec;

    /**
     * Check specification object, returned only for reference and for tools such as the documentation generator.
     */
    @JsonIgnore
    private AbstractCheckSpec<?, ?, ?, ?> checkSpec;
}
