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
package ai.dqo.metadata.sources;

import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.fields.ControlType;
import ai.dqo.metadata.fields.ParameterDataType;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Configuration of timestamp related columns on a table level.
 * Timestamp columns are used for timeliness data quality checks and for date/time partitioned checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TimestampColumnsSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<TimestampColumnsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Column name that identifies an event timestamp (date/time), such as a transaction timestamp, impression timestamp, event timestamp.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ControlType(ParameterDataType.column_name_type)
    private String eventTimestampColumn;

    @JsonPropertyDescription("Column name that contains the timestamp (or date/time) when the row was ingested (loaded, inserted) into the table. Use a column that is filled by the data pipeline or ETL process at the time of the data loading.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ControlType(ParameterDataType.column_name_type)
    private String ingestionTimestampColumn;

    @JsonPropertyDescription("Column name that contains the date, datetime or timestamp column for date/time partitioned data. Partition checks (daily partition checks and monthly partition checks) use this column in a GROUP BY clause in order to detect data quality issues in each partition separately. It should be a DATE type, DATETIME type (using a local server time zone) or a TIMESTAMP type (a UTC absolute time).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ControlType(ParameterDataType.column_name_type)
    private String partitionByColumn;

    @JsonPropertyDescription("Date/time partitioned checks timestamp source. Decides if the date/time partitioned data quality checks are calculated for rounded time periods (days, weeks) for rows grouped by event timestamp (when the action happened) or the ingestion timestamp (when the data was loaded). Additionally, date range filtering uses this column when the data for a given time period is analyzed.")
    private PartitionedChecksTimestampSource partitionedChecksTimestampSource = PartitionedChecksTimestampSource.event_timestamp;

    /**
     * Returns the name of the column that identifies the event timestamp, it could be the transaction timestamp.
     * @return Event timestamp column name.
     */
    public String getEventTimestampColumn() {
        return eventTimestampColumn;
    }

    /**
     * Sets the name of the event timestamp column.
     * @param eventTimestampColumn Event timestamp column name.
     */
    public void setEventTimestampColumn(String eventTimestampColumn) {
        this.setDirtyIf(!Objects.equals(this.eventTimestampColumn, eventTimestampColumn));
        this.eventTimestampColumn = eventTimestampColumn;
    }

    /**
     * Returns the name of the column that identifies the ingestion timestamp (when the row was loaded).
     * @return Ingestion timestamp column name.
     */
    public String getIngestionTimestampColumn() {
        return ingestionTimestampColumn;
    }

    /**
     * Sets the name of the column where the data pipeline stores the ingestion timestamp (when the row was loaded/inserted).
     * @param ingestionTimestampColumn Ingestion timestamp column name.
     */
    public void setIngestionTimestampColumn(String ingestionTimestampColumn) {
        this.setDirtyIf(!Objects.equals(this.ingestionTimestampColumn, ingestionTimestampColumn));
        this.ingestionTimestampColumn = ingestionTimestampColumn;
    }

    /**
     * Returns the name of the date/datetime/timestamp column used for partition checks.
     * @return Column name used for partition checks.
     */
    public String getPartitionByColumn() {
        return partitionByColumn;
    }

    /**
     * Sets the name of a column used for partition checks.
     * @param partitionByColumn A new column name used for partitioning.
     */
    public void setPartitionByColumn(String partitionByColumn) {
        this.setDirtyIf(!Objects.equals(this.partitionByColumn, partitionByColumn));
        this.partitionByColumn = partitionByColumn;
    }

    /**
     * Returns the type of the timestamp column used to group rows into time periods for date/time partitioned data quality checks.
     * @return Partitioned checks grouping column.
     */
    public PartitionedChecksTimestampSource getPartitionedChecksTimestampSource() {
        return partitionedChecksTimestampSource;
    }

    /**
     * Sets the type of column used for date/time partitioned checks.
     * @param partitionedChecksTimestampSource Partitioned data quality checks column source.
     */
    public void setPartitionedChecksTimestampSource(PartitionedChecksTimestampSource partitionedChecksTimestampSource) {
        this.setDirtyIf(this.partitionedChecksTimestampSource != partitionedChecksTimestampSource);
        this.partitionedChecksTimestampSource = partitionedChecksTimestampSource;
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
    public TimestampColumnsSpec deepClone() {
        TimestampColumnsSpec cloned = (TimestampColumnsSpec) super.deepClone();
        return cloned;
    }

    /**
     * Creates a clone of the object, expanding variables in parameters.
     * @param secretValueProvider Secret value provider.
     * @return Cloned and expanded instance of the object.
     */
    public TimestampColumnsSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        TimestampColumnsSpec cloned = this.deepClone();
        cloned.eventTimestampColumn = secretValueProvider.expandValue(cloned.eventTimestampColumn);
        cloned.ingestionTimestampColumn = secretValueProvider.expandValue(cloned.ingestionTimestampColumn);
        cloned.partitionByColumn = secretValueProvider.expandValue(cloned.partitionByColumn);
        return cloned;
    }

    /**
     * Returns the effective name of a column used for time partitioned checks.
     * @return Column name used for grouping rows for date/time partitioned checks.
     */
    @JsonIgnore
    @Deprecated  // this is just a fallback version
    public String getEffectivePartitioningColumn() {
        if (!Strings.isNullOrEmpty(this.partitionByColumn)) {
            return this.partitionByColumn;
        }

        switch (this.partitionedChecksTimestampSource) {
            case event_timestamp:
                return this.eventTimestampColumn;

            case ingestion_timestamp:
                return this.ingestionTimestampColumn;

            default:
                throw new IllegalArgumentException("Effective column used for time window grouping for date/time partitioned data quality checks is not correctly configured.");
        }
    }
}
