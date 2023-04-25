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

package ai.dqo.metadata.incidents;

import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Configuration of data quality incident grouping on a connection level. Defines how similar data quality issues are grouped into incidents.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class IncidentGroupingSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<IncidentGroupingSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("webhooks", o -> o.webhooks);
        }
    };

    @JsonPropertyDescription("Minimum severity level of data quality issues that are grouped into incidents. The default minimum severity level is 'warning'. Other supported severity levels are 'error' and 'fatal'.")
    private MinimumGroupingSeverityLevel minimumSeverity = MinimumGroupingSeverityLevel.warning;

    @JsonPropertyDescription("Grouping level of failed data quality checks for creating higher level data quality incidents. The default grouping level is by a table, a data quality dimension and a check category (i.e. a consistency data quality incident detected on a table X in the numeric checks category).")
    private IncidentGroupingLevel groupingLevel = IncidentGroupingLevel.table_dimension_category;

    @JsonPropertyDescription("Create separate data quality incidents for each data stream, creating different incidents for different data streams. By default, data streams are ignored for grouping data quality issues into data quality incidents.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean divideByDataStream;

    @JsonPropertyDescription("The maximum length of a data quality incident in days. When a new data quality issue is detected after max_incident_length_days days since a similar data quality was first seen, a new data quality incident is created that will capture all following data quality issues for the next max_incident_length_days days. The default value is 60 days.")
    private int maxIncidentLengthDays = 60;

    @JsonPropertyDescription("The number of days that all similar data quality issues are muted when a a data quality incident is closed in the 'mute' status.")
    private int muteForDays = 60;

    @JsonPropertyDescription("Disables data quality incident creation for failed data quality checks on the data source.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled;

    @JsonPropertyDescription("Configuration of Webhook URLs for new or updated incident notifications.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private IncidentWebhookNotificationsSpec webhooks;

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
     * Returns a flat if the data stream is also included in the data quality issue grouping.
     * @return True when incidents are created for data streams.
     */
    public boolean isDivideByDataStream() {
        return divideByDataStream;
    }

    /**
     * Sets a flag that enables creating separate data quality incidents for each data stream.
     * @param divideByDataStream True when each data stream has a different incident.
     */
    public void setDivideByDataStream(boolean divideByDataStream) {
        this.setDirtyIf(this.divideByDataStream != divideByDataStream);
        this.divideByDataStream = divideByDataStream;
    }

    /**
     * Returns the maximum length of an incident.
     * @return Maximum length of an incident in days.
     */
    public int getMaxIncidentLengthDays() {
        return maxIncidentLengthDays;
    }

    /**
     * Sets the maximum length of a data quality incident in days.
     * @param maxIncidentLengthDays New maximum data quality incident length in days.
     */
    public void setMaxIncidentLengthDays(int maxIncidentLengthDays) {
        this.setDirtyIf(this.maxIncidentLengthDays != maxIncidentLengthDays);
        this.maxIncidentLengthDays = maxIncidentLengthDays;
    }

    /**
     * Returns the number of days how long data quality incidents are muted and similar data quality issues will not result in creating a new data quality incident.
     * @return The number of days when a previously muted data quality incident will not be reopened.
     */
    public int getMuteForDays() {
        return muteForDays;
    }

    /**
     * Sets the number of days when new data quality incidents are not created after a similar incident was muted.
     * @param muteForDays Number of days to mute an incident.
     */
    public void setMuteForDays(int muteForDays) {
        this.setDirtyIf(this.muteForDays != muteForDays);
        this.muteForDays = muteForDays;
    }

    /**
     * Returns true if incident creation is disabled. When false (the default value), then data quality incidents are created.
     * @return True when data quality incidents are disabled on the table.
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Sets the flag to disable data quality incidents on the connection.
     * @param disabled Disable data quality incidents on the connection.
     */
    public void setDisabled(boolean disabled) {
        this.setDirtyIf(this.disabled != disabled);
        this.disabled = disabled;
    }

    /**
     * Returns the configuration of webhooks used for incident notifications.
     * @return Webhooks configuration for incidents.
     */
    public IncidentWebhookNotificationsSpec getWebhooks() {
        return webhooks;
    }

    /**
     * Sets a new configuration of incident notification webhooks.
     * @param webhooks New configuration of incident notification webhooks.
     */
    public void setWebhooks(IncidentWebhookNotificationsSpec webhooks) {
        setDirtyIf(!Objects.equals(this.webhooks, webhooks));
        this.webhooks = webhooks;
        propagateHierarchyIdToField(webhooks, "webhooks");
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
    public IncidentGroupingSpec deepClone() {
        IncidentGroupingSpec cloned = (IncidentGroupingSpec) super.deepClone();
        return cloned;
    }

    /**
     * Creates a cloned and expanded version of the objects. All parameters are changed to the values expanded from variables like ${ENV_VAR}.
     * @param secretValueProvider Secret value provider.
     * @return Cloned and expanded copy of the object.
     */
    public IncidentGroupingSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        IncidentGroupingSpec cloned = this.deepClone();
        if (cloned.webhooks != null) {
            cloned.webhooks = cloned.webhooks.expandAndTrim(secretValueProvider);
        }
        return cloned;
    }

    /**
     * Calculates an incident hash that is used for grouping similar data quality issues into data quality incidents.
     * This method will decide which values are used for the data quality issue calculation picking the required parameters into a hash calculation.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name affected by a data quality issue.
     * @param dataStreamName Data stream name.
     * @param dataQualityDimension Data quality dimension name.
     * @param checkCategory Data quality check category (group of checks).
     * @param checkType Data quality check type (profiling, recurring, partitioned).
     * @param checkName Data quality check name.
     * @return Hash of the data quality incident.
     */
    public long calculateIncidentHash(String connectionName,
                                      PhysicalTableName physicalTableName,
                                      String dataStreamName,
                                      String dataQualityDimension,
                                      String checkCategory,
                                      String checkType,
                                      String checkName) {
        ArrayList<String> hashedComponents = new ArrayList<>();
        hashedComponents.add(connectionName);
        hashedComponents.add(physicalTableName.getSchemaName());
        hashedComponents.add(physicalTableName.getTableName());
        if (this.divideByDataStream && dataStreamName != null) {
            hashedComponents.add(dataStreamName);
        }
        if (this.groupingLevel != IncidentGroupingLevel.table) {
            hashedComponents.add(dataQualityDimension);
            if (this.groupingLevel != IncidentGroupingLevel.table_dimension) {
                hashedComponents.add(checkCategory);
                if (this.groupingLevel != IncidentGroupingLevel.table_dimension_category) {
                    hashedComponents.add(checkType);
                    if (this.groupingLevel != IncidentGroupingLevel.table_dimension_category_check_type) {
                        hashedComponents.add(checkName);
                    }
                }
            }
        }

        List<HashCode> elementHashes = hashedComponents.stream()
                .filter(element -> element != null)
                .map(element -> Hashing.farmHashFingerprint64().hashString(element, StandardCharsets.UTF_8))
                .collect(Collectors.toList());
        return Math.abs(Hashing.combineOrdered(elementHashes).asLong()); // we return only positive hashes which limits the hash space to 2^63, but positive hashes are easier for users
    }
}
