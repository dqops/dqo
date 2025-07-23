/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.table.profiling;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.timeliness.TableDataFreshnessAnomalyCheckSpec;
import com.dqops.checks.table.checkspecs.timeliness.TableDataFreshnessCheckSpec;
import com.dqops.checks.table.checkspecs.timeliness.TableDataIngestionDelayCheckSpec;
import com.dqops.checks.table.checkspecs.timeliness.TableDataStalenessCheckSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of timeliness data quality checks on a table level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTimelinessProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_data_freshness", o -> o.profileDataFreshness);
            put("profile_data_freshness_anomaly", o -> o.profileDataFreshnessAnomaly);
            put("profile_data_staleness", o -> o.profileDataStaleness);
            put("profile_data_ingestion_delay", o -> o.profileDataIngestionDelay);
        }
    };

    @JsonPropertyDescription("Calculates the number of days since the most recent event timestamp (freshness)")
    private TableDataFreshnessCheckSpec profileDataFreshness;

    @JsonPropertyDescription("Verifies that the number of days since the most recent event timestamp (freshness) changes in a rate within a percentile boundary during the last 90 days.")
    private TableDataFreshnessAnomalyCheckSpec profileDataFreshnessAnomaly;

    @JsonPropertyDescription("Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)")
    private TableDataStalenessCheckSpec profileDataStaleness;

    @JsonPropertyDescription("Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp")
    private TableDataIngestionDelayCheckSpec profileDataIngestionDelay;

    /**
     * Returns the number of days since the most recent event check configuration.
     * @return A number of days since the most recent event check configuration.
     */
    public TableDataFreshnessCheckSpec getProfileDataFreshness() {
        return profileDataFreshness;
    }

    /**
     * Sets the number of days since the most recent event check configuration.
     * @param profileDataFreshness Maximum days since the most recent event check configuration.
     */
    public void setProfileDataFreshness(TableDataFreshnessCheckSpec profileDataFreshness) {
        this.setDirtyIf(!Objects.equals(this.profileDataFreshness, profileDataFreshness));
        this.profileDataFreshness = profileDataFreshness;
        propagateHierarchyIdToField(profileDataFreshness, "profile_data_freshness");
    }

    /**
     * Returns the number of days since the most recent event value anomaly 90 days check specification.
     * @return A number of days since the most recent event value anomaly 90 days check specification.
     */
    public TableDataFreshnessAnomalyCheckSpec getProfileDataFreshnessAnomaly() {
        return profileDataFreshnessAnomaly;
    }

    /**
     * Sets the number of days since the most recent event value anomaly 90 days check specification.
     * @param profileDataFreshnessAnomaly freshness value anomaly 90 days check specification.
     */
    public void setProfileDataFreshnessAnomaly(TableDataFreshnessAnomalyCheckSpec profileDataFreshnessAnomaly) {
        this.setDirtyIf(!Objects.equals(this.profileDataFreshnessAnomaly, profileDataFreshnessAnomaly));
        this.profileDataFreshnessAnomaly = profileDataFreshnessAnomaly;
        this.propagateHierarchyIdToField(profileDataFreshnessAnomaly, "profile_data_freshness_anomaly");
    }

    /**
     * Returns a number of days since the last data ingestion check configuration.
     * @return A number of days since the last data ingestion check configuration.
     */
    public TableDataStalenessCheckSpec getProfileDataStaleness() {
        return profileDataStaleness;
    }

    /**
     * Sets a number of days since the last data ingestion check configuration.
     * @param profileDataStaleness  A number of days since the last load check configuration.
     */
    public void setProfileDataStaleness(TableDataStalenessCheckSpec profileDataStaleness) {
        this.setDirtyIf(!Objects.equals(this.profileDataStaleness, profileDataStaleness));
        this.profileDataStaleness = profileDataStaleness;
        propagateHierarchyIdToField(profileDataStaleness, "profile_data_staleness");
    }

    /**
     * Returns a data ingestion delay check configuration.
     * @return A data ingestion delay check configuration.
     */
    public TableDataIngestionDelayCheckSpec getProfileDataIngestionDelay() {
        return profileDataIngestionDelay;
    }

    /**
     * Sets a data ingestion delay check configuration.
     * @param profileDataIngestionDelay A data ingestion delay check configuration.
     */
    public void setProfileDataIngestionDelay(TableDataIngestionDelayCheckSpec profileDataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.profileDataIngestionDelay, profileDataIngestionDelay));
        this.profileDataIngestionDelay = profileDataIngestionDelay;
        propagateHierarchyIdToField(profileDataIngestionDelay, "profile_data_ingestion_delay");
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
     * Gets the check target appropriate for all checks in this category.
     *
     * @return Corresponding check target.
     */
    @Override
    @JsonIgnore
    public CheckTarget getCheckTarget() {
        return CheckTarget.table;
    }

    /**
     * Gets the check type appropriate for all checks in this category.
     *
     * @return Corresponding check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.profiling;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return null;
    }

    /**
     * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
     *
     * @return Array of supported data type categories.
     */
    @Override
    @JsonIgnore
    public DataTypeCategory[] getSupportedDataTypeCategories() {
        return DataTypeCategory.ANY;
    }
}
