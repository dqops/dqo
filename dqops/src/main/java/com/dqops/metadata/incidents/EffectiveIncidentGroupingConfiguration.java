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

import com.dqops.metadata.sources.PhysicalTableName;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Effective incident grouping configuration, combined from the configuration on a connection level and a table level.
 */
public class EffectiveIncidentGroupingConfiguration {
    private ConnectionIncidentGroupingSpec connectionIncidentGrouping;
    private TableIncidentGroupingSpec tableIncidentGrouping;
    private IncidentGroupingLevel groupingLevel;
    private MinimumGroupingSeverityLevel minimumSeverity;
    private int maxIncidentLengthDays;
    private int muteForDays;
    private boolean divideByDataStream;
    private boolean disabled;
    private IncidentWebhookNotificationsSpec webhooks;

    /**
     * Creates an effective incident grouping configuration by taking the configuration from the connection level
     * and then overriding the values that were configured (overridden) at a table level.
     * @param connectionIncidentGrouping Connection level incident grouping configuration.
     * @param tableIncidentGrouping Table level incident grouping configuration.
     */
    public EffectiveIncidentGroupingConfiguration(ConnectionIncidentGroupingSpec connectionIncidentGrouping,
                                                  TableIncidentGroupingSpec tableIncidentGrouping) {

        this.connectionIncidentGrouping = connectionIncidentGrouping;
        this.tableIncidentGrouping = tableIncidentGrouping;

        this.groupingLevel = connectionIncidentGrouping.getGroupingLevel();
        this.minimumSeverity = connectionIncidentGrouping.getMinimumSeverity();
        this.divideByDataStream = connectionIncidentGrouping.isDivideByDataGroups();
        this.disabled = connectionIncidentGrouping.isDisabled();
        this.maxIncidentLengthDays = connectionIncidentGrouping.getMaxIncidentLengthDays();
        this.muteForDays = connectionIncidentGrouping.getMuteForDays();
        this.webhooks = connectionIncidentGrouping.getWebhooks();

        if (tableIncidentGrouping != null) {
            if (tableIncidentGrouping.getGroupingLevel() != null) {
                this.groupingLevel = tableIncidentGrouping.getGroupingLevel();
            }

            if (tableIncidentGrouping.getMinimumSeverity() != null) {
                this.minimumSeverity = tableIncidentGrouping.getMinimumSeverity();
            }

            if (tableIncidentGrouping.getDivideByDataGroup() != null) {
                this.divideByDataStream = tableIncidentGrouping.getDivideByDataGroup();
            }

            if (tableIncidentGrouping.getDisabled() != null) {
                this.disabled = tableIncidentGrouping.getDisabled();
            }
        }
    }

    /**
     * Returns the data quality issue grouping level used to group similar issues into incidents.
     * @return Data quality issue grouping level.
     */
    public IncidentGroupingLevel getGroupingLevel() {
        return groupingLevel;
    }

    /**
     * Returns the minimum severity.
     * @return Minimum severity.
     */
    public MinimumGroupingSeverityLevel getMinimumSeverity() {
        return minimumSeverity;
    }

    /**
     * Returns the flag if the incidents should be created for each data stream.
     * @return true when incidents are created for each data stream.
     */
    public boolean isDivideByDataStream() {
        return divideByDataStream;
    }

    /**
     * Returns true if incident creation is disabled.
     * @return True when the incident creation is disabled.
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Returns the maximum length of an incident.
     * @return Maximum length of an incident in days.
     */
    public int getMaxIncidentLengthDays() {
        return maxIncidentLengthDays;
    }

    /**
     * Returns the number of days how long data quality incidents are muted and similar data quality issues will not result in creating a new data quality incident.
     * @return The number of days when a previously muted data quality incident will not be reopened.
     */
    public int getMuteForDays() {
        return muteForDays;
    }

    /**
     * Returns the configuration of webhooks used for incident notifications.
     * @return Webhooks configuration for incidents.
     */
    public IncidentWebhookNotificationsSpec getWebhooks() {
        return webhooks;
    }


    /**
     * Calculates an incident hash that is used for grouping similar data quality issues into data quality incidents.
     * This method will decide which values are used for the data quality issue calculation picking the required parameters into a hash calculation.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name affected by a data quality issue.
     * @param dataStreamName Data stream name.
     * @param dataQualityDimension Data quality dimension name.
     * @param checkCategory Data quality check category (group of checks).
     * @param checkType Data quality check type (profiling, monitoring, partitioned).
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
