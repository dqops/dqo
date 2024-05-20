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

package com.dqops.execution.sensors.grouping;

import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.execution.sensors.SensorPrepareResult;
import com.dqops.execution.sqltemplates.grouping.FragmentedSqlQuery;
import com.dqops.execution.sqltemplates.grouping.SqlQueryFragment;
import com.dqops.execution.sqltemplates.grouping.SqlQueryFragmentType;
import com.dqops.utils.exceptions.DqoRuntimeException;
import org.apache.parquet.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A group of similar sensors that are grouped together. Their SQLs will be merged together in order to execute ony one query that can analyze multiple metrics from a table,
 * or even from multiple columns.
 */
public class PreparedSensorsGroup {
    private final List<SensorPrepareResult> preparedSensors = new ArrayList<>();
    private String mergedSql;

    /**
     * Returns a collection of prepared sensors that are part of this group.
     * @return Collection of prepared sensors.
     */
    public List<SensorPrepareResult> getPreparedSensors() {
        return preparedSensors;
    }

    /**
     * Adds a prepared sensor to the group.
     * @param sensorPrepareResult Prepared sensor result.
     */
    public void add(SensorPrepareResult sensorPrepareResult) {
        this.preparedSensors.add(sensorPrepareResult);
    }

    /**
     * Retrieves the first sensor's prepared result. This sensor is used as a template for other operations.
     * @return The prepare results of the first sensor that was added to the group.
     */
    public SensorPrepareResult getFirstSensorPrepareResult() {
        return this.preparedSensors.get(0);
    }

    /**
     * Returns the count of prepared sensors that are aggregated into this similar sensor group.
     * @return Count of similar sensors that are aggregated.
     */
    public int size() {
        return this.preparedSensors.size();
    }

    /**
     * Returns true if this check could be split further.
     * @return True when this check could be split further.
     */
    public boolean isSplittable() {
        return this.preparedSensors.size() > 1 && !Strings.isNullOrEmpty(this.getFirstSensorPrepareResult().getRenderedSensorSql());
    }

    /**
     * Merges queries into a single SQL that calculates all queries at once.
     * The merged query could be returned by {@link PreparedSensorsGroup#getMergedSql()}.
     */
    public void mergeQueries() {
        if (this.preparedSensors.size() == 0) {
            throw new DqoRuntimeException("No prepared SQLs were added");
        }

        SensorPrepareResult firstPreparedStatement = this.preparedSensors.get(0);
        FragmentedSqlQuery firstQuerySqlFragments = firstPreparedStatement.getFragmentedSqlQuery();

        if (firstQuerySqlFragments == null || firstPreparedStatement.getRenderedSensorSql() == null) {
            this.mergedSql = null; // these sensors are not SQL sensors
            return;
        }

        if (this.preparedSensors.size() == 1) {
            this.mergedSql = firstPreparedStatement.getRenderedSensorSql();
            firstPreparedStatement.setActualValueAlias(firstQuerySqlFragments.getActualValueAlias());
            firstPreparedStatement.setExpectedValueAlias(firstQuerySqlFragments.getExpectedValueAlias());
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();

        int sqlFragmentsCount = firstQuerySqlFragments.getComponents().size();
        for (int fragmentIndex = 0; fragmentIndex < sqlFragmentsCount; fragmentIndex++) {
            SqlQueryFragment firstQueryFragment = firstQuerySqlFragments.getComponents().get(fragmentIndex);
            if (firstQueryFragment.getFragmentType() == SqlQueryFragmentType.STATIC_FRAGMENT) {
                stringBuilder.append(firstQueryFragment.getText());
            } else {
                for (int sensorIndex = 0; sensorIndex < this.preparedSensors.size(); sensorIndex++) {
                    SensorPrepareResult sensorPrepareResult = this.preparedSensors.get(sensorIndex);
                    sensorPrepareResult.setActualValueAlias(sensorPrepareResult.getSensorRunParameters().getActualValueAlias());
                    sensorPrepareResult.setExpectedValueAlias(sensorPrepareResult.getSensorRunParameters().getExpectedValueAlias());

                    FragmentedSqlQuery fragmentedSqlQuery = sensorPrepareResult.getFragmentedSqlQuery();
                    SqlQueryFragment sensorSqlFragment = fragmentedSqlQuery.getComponents().get(fragmentIndex);
                    String sqlFragmentText = sensorSqlFragment.getText();
                    if (fragmentedSqlQuery.getActualValueAlias() != null &&
                            Objects.equals(fragmentedSqlQuery.getActualValueAlias(), SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME) &&
                            !Objects.equals(fragmentedSqlQuery.getActualValueAlias(), sensorPrepareResult.getSensorRunParameters().getActualValueAlias())) {
                        // need to fix the column name
                        sqlFragmentText = sqlFragmentText.replace(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME,
                                sensorPrepareResult.getSensorRunParameters().getActualValueAlias());
                    }

                    if (fragmentedSqlQuery.getExpectedValueAlias() != null &&
                            Objects.equals(fragmentedSqlQuery.getExpectedValueAlias(), SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME) &&
                            !Objects.equals(fragmentedSqlQuery.getExpectedValueAlias(), sensorPrepareResult.getSensorRunParameters().getExpectedValueAlias())) {
                        // need to fix the column name
                        sqlFragmentText = sqlFragmentText.replace(SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME,
                                sensorPrepareResult.getSensorRunParameters().getExpectedValueAlias());
                    }

                    stringBuilder.append(sqlFragmentText);

                    SqlQueryFragment nextQueryFragment = firstQuerySqlFragments.getComponents().size() > fragmentIndex + 1 ?
                            firstQuerySqlFragments.getComponents().get(fragmentIndex + 1) : null;
                    if (sensorIndex < this.preparedSensors.size() - 1 ||  // not the last merged sensor
                            (nextQueryFragment != null && (nextQueryFragment.getFragmentType() != SqlQueryFragmentType.STATIC_FRAGMENT ||
                                    (nextQueryFragment.getFragmentType() == SqlQueryFragmentType.STATIC_FRAGMENT &&
                                    nextQueryFragment.getText().trim().startsWith(","))))) {
                        if (!sqlFragmentText.trim().endsWith(",")) {
                            stringBuilder.append(',');
                        }
                    }
                }
            }
        }


        if (stringBuilder.length() > 0) {
            this.mergedSql = stringBuilder.toString();
        }
    }

    /**
     * Splits the list into two separate lists of equal (or almost equal) size.
     * This method should be called to generate SQLs that aggregate less sensors, in case that one of the sensor cannot be executed due to some issues in the sensor's query or a missing column.
     * @return Two groups with sensors extracted from this group.
     * @throws DqoRuntimeException When this group has less than 2 sensors and cannot be divided further.
     */
    public PreparedSensorsGroup[] split() {
        if (this.preparedSensors.size() == 1) {
            throw new DqoRuntimeException("Cannot split the group that has only one sensor");
        }

        int firstGroupSize = this.preparedSensors.size() / 2;
        PreparedSensorsGroup firstGroup = new PreparedSensorsGroup();
        firstGroup.preparedSensors.addAll(this.preparedSensors.subList(0, firstGroupSize));

        PreparedSensorsGroup secondGroup = new PreparedSensorsGroup();
        secondGroup.preparedSensors.addAll(this.preparedSensors.subList(firstGroupSize, this.preparedSensors.size()));

        return new PreparedSensorsGroup[] { firstGroup, secondGroup };
    }

    /**
     * Returns a merged SQL query that is combined from multiple sensors.
     * @return Merged sql.
     */
    public String getMergedSql() {
        if (this.mergedSql != null) {
            return this.mergedSql;
        }

        this.mergeQueries();
        return this.mergedSql;
    }
}
