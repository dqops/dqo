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
package com.dqops.checks.column.checkspecs.customsql;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.DefaultDataQualityDimensions;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.rules.comparison.ImportSeverityRuleParametersSpec;
import com.dqops.sensors.column.customsql.ColumnSqlImportCustomResultSensorParametersSpec;
import com.dqops.sensors.table.customsql.TableSqlImportCustomResultSensorParametersSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Column level check that uses a custom SQL SELECT statement to retrieve a result of running a custom data quality check on a column by a custom
 * data quality check, hardcoded in the data pipeline. The result is retrieved by querying a separate **logging table**, whose schema is not fixed.
 * The logging table should have columns that identify a table and a column for which they store custom data quality check results, and a *severity* column of the data quality issue.
 * The SQL query that is configured in this external data quality results importer must be
 * a complete SELECT statement that queries a dedicated logging table, created by the data engineering team.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnSqlImportCustomResultCheckSpec extends AbstractCheckSpec<ColumnSqlImportCustomResultSensorParametersSpec,
        ImportSeverityRuleParametersSpec, ImportSeverityRuleParametersSpec, ImportSeverityRuleParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSqlImportCustomResultCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Sensor parameters with the custom SQL SELECT statement that queries a log table to get a result of a custom query that retrieves results from other data quality libraries.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSqlImportCustomResultSensorParametersSpec parameters = new ColumnSqlImportCustomResultSensorParametersSpec();

    @JsonPropertyDescription("Warning severity import rule. Activate the rule with no parameters to import custom data quality results when the custom query returns a value **1** in the *severity* result column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ImportSeverityRuleParametersSpec warning;

    @JsonPropertyDescription("Error severity import rule. Activate the rule with no parameters to import custom data quality results when the custom query returns a value **2** in the *severity* result column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ImportSeverityRuleParametersSpec error;

    @JsonPropertyDescription("Fatal severity import rule. Activate the rule with no parameters to import custom data quality results when the custom query returns a value **3** in the *severity* result column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ImportSeverityRuleParametersSpec fatal;

    /**
     * Returns the parameters of the sensor.
     * @return Sensor parameters.
     */
    @Override
    public ColumnSqlImportCustomResultSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets a new row count sensor parameter object.
     * @param parameters Row count parameters.
     */
    public void setParameters(ColumnSqlImportCustomResultSensorParametersSpec parameters) {
		this.setDirtyIf(!Objects.equals(this.parameters, parameters));
        this.parameters = parameters;
		this.propagateHierarchyIdToField(parameters, "parameters");
    }

    /**
     * Alerting threshold configuration that raise a "WARNING" severity alerts for unsatisfied rules.
     *
     * @return Warning severity rule parameters.
     */
    @Override
    public ImportSeverityRuleParametersSpec getWarning() {
        return this.warning;
    }

    /**
     * Sets a new warning level alerting threshold.
     * @param warning Warning alerting threshold to set.
     */
    public void setWarning(ImportSeverityRuleParametersSpec warning) {
        this.setDirtyIf(!Objects.equals(this.warning, warning));
        this.warning = warning;
        this.propagateHierarchyIdToField(warning, "warning");
    }

    /**
     * Alerting threshold configuration that raise a regular "ERROR" severity alerts for unsatisfied rules.
     *
     * @return Default "ERROR" alerting thresholds.
     */
    @Override
    public ImportSeverityRuleParametersSpec getError() {
        return this.error;
    }

    /**
     * Sets a new error level alerting threshold.
     * @param error Error alerting threshold to set.
     */
    public void setError(ImportSeverityRuleParametersSpec error) {
        this.setDirtyIf(!Objects.equals(this.error, error));
        this.error = error;
        this.propagateHierarchyIdToField(error, "error");
    }

    /**
     * Alerting threshold configuration that raise a "FATAL" severity alerts for unsatisfied rules.
     *
     * @return Fatal severity rule parameters.
     */
    @Override
    public ImportSeverityRuleParametersSpec getFatal() {
        return this.fatal;
    }

    /**
     * Sets a new fatal level alerting threshold.
     * @param fatal Fatal alerting threshold to set.
     */
    public void setFatal(ImportSeverityRuleParametersSpec fatal) {
        this.setDirtyIf(!Objects.equals(this.fatal, fatal));
        this.fatal = fatal;
        this.propagateHierarchyIdToField(fatal, "fatal");
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Returns an alternative check's friendly name that is shown on the check editor.
     *
     * @return An alternative name, or null when the check has no alternative name to show.
     */
    @Override
    @JsonIgnore
    public String getFriendlyName() {
        return "Import custom data quality results on column";
    }

    /**
     * Returns the default data quality dimension name used when an overwritten data quality dimension name was not assigned.
     *
     * @return Default data quality dimension name.
     */
    @Override
    public DefaultDataQualityDimensions getDefaultDataQualityDimension() {
        return DefaultDataQualityDimensions.Validity;
    }
}
