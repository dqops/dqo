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

package com.dqops.metadata.incidents;

import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Configuration of data quality incident grouping on a table level. Defines how similar data quality issues are grouped into incidents.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class TableIncidentGroupingSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<TableIncidentGroupingSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Grouping level of failed data quality checks for creating higher level data quality incidents. The default grouping level is by a table, a data quality dimension and a check category (i.e. a datatype data quality incident detected on a table X in the numeric checks category).")
    private IncidentGroupingLevel groupingLevel;

    @JsonPropertyDescription("Minimum severity level of data quality issues that are grouped into incidents. The default minimum severity level is 'warning'. Other supported severity levels are 'error' and 'fatal'.")
    private MinimumGroupingSeverityLevel minimumSeverity;

    @JsonPropertyDescription("Create separate data quality incidents for each data group, creating different incidents for different groups of rows. " +
            "By default, data groups are ignored for grouping data quality issues into data quality incidents.")
    private Boolean divideByDataGroup;

    @JsonPropertyDescription("Disables data quality incident creation for failed data quality checks on the table.")
    private Boolean disabled;

    /**
     * Returns the data quality issue grouping level used to group similar issues into incidents.
     * @return Data quality issue grouping level.
     */
    public IncidentGroupingLevel getGroupingLevel() {
        return groupingLevel;
    }

    /**
     * Sets the data quality issue grouping level used to group issues into incidents.
     * @param groupingLevel Grouping level.
     */
    public void setGroupingLevel(IncidentGroupingLevel groupingLevel) {
        this.setDirtyIf(!Objects.equals(this.groupingLevel, groupingLevel));
        this.groupingLevel = groupingLevel;
    }

    /**
     * Returns the minimum severity level of failed data quality checks that a grouped into incidents.
     * @return Minimum severity level for grouping.
     */
    public MinimumGroupingSeverityLevel getMinimumSeverity() {
        return minimumSeverity;
    }

    /**
     * Sets the minimum severity level of failed data quality checks that are grouped into incidents.
     * @param minimumSeverity Minimum severity level.
     */
    public void setMinimumSeverity(MinimumGroupingSeverityLevel minimumSeverity) {
        this.setDirtyIf(!Objects.equals(this.minimumSeverity, minimumSeverity));
        this.minimumSeverity = minimumSeverity;
    }

    /**
     * Returns a flat if the data stream is also included in the data quality issue grouping.
     * @return True when incidents are created for data streams.
     */
    public Boolean getDivideByDataGroup() {
        return divideByDataGroup;
    }

    /**
     * Sets a flag that enables creating separate data quality incidents for each data stream.
     * @param divideByDataGroup True when each data stream has a different incident.
     */
    public void setDivideByDataGroup(Boolean divideByDataGroup) {
        this.setDirtyIf(!Objects.equals(this.divideByDataGroup, divideByDataGroup));
        this.divideByDataGroup = divideByDataGroup;
    }
    /**
     * Returns true if incident creation is disabled. When false (the default value), then data quality incidents are created.
     * @return True when data quality incidents are disabled on the table.
     */
    public Boolean getDisabled() {
        return disabled;
    }

    /**
     * Sets the flag to disable data quality incidents on the connection.
     * @param disabled Disable data quality incidents on the connection.
     */
    public void setDisabled(Boolean disabled) {
        this.setDirtyIf(!Objects.equals(this.disabled, disabled));
        this.disabled = disabled;
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
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public TableIncidentGroupingSpec deepClone() {
        TableIncidentGroupingSpec cloned = (TableIncidentGroupingSpec) super.deepClone();
        return cloned;
    }

    /**
     * Creates a cloned and expanded version of the objects. All parameters are changed to the values expanded from variables like ${ENV_VAR}.
     * @param secretValueProvider Secret value provider.
     * @return Cloned and expanded copy of the object.
     */
    public TableIncidentGroupingSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        TableIncidentGroupingSpec cloned = this.deepClone();
        return cloned;
    }

    public static class TableIncidentGroupingSpecSampleFactory implements SampleValueFactory<TableIncidentGroupingSpec> {
        @Override
        public TableIncidentGroupingSpec createSample() {
            return new TableIncidentGroupingSpec() {{
                setGroupingLevel(IncidentGroupingLevel.table_dimension);
                setMinimumSeverity(MinimumGroupingSeverityLevel.warning);
                setDivideByDataGroup(true);
                setDisabled(false);
            }};
        }
    }
}
