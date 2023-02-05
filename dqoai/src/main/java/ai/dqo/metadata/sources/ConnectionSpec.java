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

import ai.dqo.connectors.ConnectionProviderSpecificParameters;
import ai.dqo.connectors.ProviderType;
import ai.dqo.connectors.bigquery.BigQueryParametersSpec;
import ai.dqo.connectors.postgresql.PostgresqlParametersSpec;
import ai.dqo.connectors.snowflake.SnowflakeParametersSpec;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.comments.CommentsListSpec;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.id.*;
import ai.dqo.metadata.notifications.NotificationSettingsSpec;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.scheduling.RecurringSchedulesSpec;
import ai.dqo.utils.datetime.TimeZoneUtility;
import ai.dqo.utils.exceptions.DqoRuntimeException;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import picocli.CommandLine;

import java.time.ZoneId;
import java.util.Objects;

/**
 * Connection specification.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class ConnectionSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<ConnectionSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
			put("comments", o -> o.comments);
			put("default_data_stream_mapping", o -> o.defaultDataStreamMapping);
			put("bigquery", o -> o.bigquery);
			put("snowflake", o -> o.snowflake);
            put("postgresql", o -> o.postgresql);
            put("labels", o -> o.labels);
            put("schedule", o -> o.schedule);
            put("schedules", o -> o.schedules);
            put("notifications", o -> o.notifications);
        }
    };

    @JsonPropertyDescription("Database provider type (required). Accepts: bigquery, snowflake.")
    private ProviderType providerType;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("BigQuery connection parameters. Specify parameters in the bigquery section.")
    private BigQueryParametersSpec bigquery;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("Snowflake connection parameters. Specify parameters in the snowflake section or set the url (which is the Snowflake JDBC url).")
    private SnowflakeParametersSpec snowflake;

    @CommandLine.Mixin // fill properties from CLI command line arguments
    @JsonPropertyDescription("PostgreSQL connection parameters. Specify parameters in the postgresql section or set the url (which is the Snowflake JDBC url).")
    private PostgresqlParametersSpec postgresql;

    @JsonPropertyDescription("Timezone name for the time period timestamps. This should be the timezone of the monitored database. Use valid Java ZoneId name, the list of possible timezones is listed as 'TZ database name' on https://en.wikipedia.org/wiki/List_of_tz_database_time_zones")
    private String timeZone = "UTC";

    @JsonPropertyDescription("The concurrency limit for the maximum number of parallel executions of checks on this connection.")
    private Integer parallelRunsLimit;

    @JsonPropertyDescription("Default data streams configuration for all tables. The configuration may be overridden on table, column and check level. Data streams are configured in two cases: (1) a static dimension is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). (2) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a 'country' column used for partitioning.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DataStreamMappingSpec defaultDataStreamMapping;

    @JsonPropertyDescription("Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private RecurringScheduleSpec schedule;

    @JsonPropertyDescription("Configuration of the job scheduler that runs data quality checks. The scheduler configuration is divided into types of checks that have different schedules.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private RecurringSchedulesSpec schedules;

    @JsonPropertyDescription("Configuration of the notifications settings. Notifications are published when new data quality issues are detected.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private NotificationSettingsSpec notifications;

    @JsonPropertyDescription("Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CommentsListSpec comments;

    @JsonPropertyDescription("Custom labels that were assigned to the connection. Labels are used for searching for tables when filtered data quality checks are executed.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private LabelSetSpec labels;

    /**
     * Default constructor.
     */
    public ConnectionSpec() {
    }

    /**
     * Constructor for creating a connection for a selected provider.
     * @param providerType Provider type.
     */
    public ConnectionSpec(ProviderType providerType) {
        this.providerType = providerType;
    }

    /**
     * Returns a database provider type to be used.
     * @return Connection provider type.
     */
    public ProviderType getProviderType() {
        return providerType;
    }

    /**
     * Sets a database provider type.
     * @param providerType Database provider type.
     */
    public void setProviderType(ProviderType providerType) {
		setDirtyIf(!Objects.equals(this.providerType, providerType));
        this.providerType = providerType;
    }

    /**
     * Big query connection settings.
     * @return Big query connection settings.
     */
    public BigQueryParametersSpec getBigquery() {
        return bigquery;
    }

    /**
     * Sets the bigquery connection parameters.
     * @param bigquery Bigquery connection parameters.
     */
    public void setBigquery(BigQueryParametersSpec bigquery) {
		setDirtyIf(!Objects.equals(this.bigquery, bigquery));
        this.bigquery = bigquery;
		propagateHierarchyIdToField(bigquery, "bigquery");
    }

    /**
     * Returns snowflake connection parameters.
     * @return Snowflake connection parameters.
     */
    public SnowflakeParametersSpec getSnowflake() {
        return snowflake;
    }

    /**
     * Sets the snowflake connection parameters.
     * @param snowflake Snowflake connection parameters.
     */
    public void setSnowflake(SnowflakeParametersSpec snowflake) {
		setDirtyIf(!Objects.equals(this.snowflake, snowflake));
        this.snowflake = snowflake;
		propagateHierarchyIdToField(snowflake, "snowflake");
    }

    /**
     * Returns the connection parameters for PostgreSQL.
     * @return PostgreSQL connection parameters.
     */
    public PostgresqlParametersSpec getPostgresql() {
        return postgresql;
    }

    /**
     * Sets the PostgreSQL connection parameters.
     * @param postgresql New PostgreSQL connection parameters.
     */
    public void setPostgresql(PostgresqlParametersSpec postgresql) {
        setDirtyIf(!Objects.equals(this.postgresql, postgresql));
        this.postgresql = postgresql;
        propagateHierarchyIdToField(postgresql, "postgresql");
    }

    /**
     * Returns the schedule configuration for running the checks automatically.
     * @return Schedule configuration.
     */
    public RecurringScheduleSpec getSchedule() {
        return schedule;
    }

    /**
     * Stores a new schedule configuration.
     * @param schedule New schedule configuration.
     */
    public void setSchedule(RecurringScheduleSpec schedule) {
        setDirtyIf(!Objects.equals(this.schedule, schedule));
        this.schedule = schedule;
        propagateHierarchyIdToField(schedule, "schedule");
    }

    /**
     * Returns the configuration of schedules for each type of check.
     * @return Configuration of schedules for each type of checks.
     */
    public RecurringSchedulesSpec getSchedules() {
        return schedules;
    }

    /**
     * Sets the configuration of schedules for running each type of checks.
     * @param schedules Configuration of schedules.
     */
    public void setSchedules(RecurringSchedulesSpec schedules) {
        setDirtyIf(!Objects.equals(this.schedules, schedules));
        this.schedules = schedules;
        propagateHierarchyIdToField(schedules, "schedules");
    }

    /**
     * Get the target database timezone name. Should match one of available {@link java.time.ZoneId} time zone.
     * @return Time zone name.
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * Sets a time zone name. Zone names are not validated on set.
     * @param timeZone Time zone name.
     */
    public void setTimeZone(String timeZone) {
		setDirtyIf(!Objects.equals(this.timeZone, timeZone));
        this.timeZone = timeZone;
    }

    /**
     * Parses the time zone. Returns a Java zoneId. Returns UTC if the time zone name is invalid.
     * @return Time zone object with the zone rules.
     */
    @JsonIgnore
    public ZoneId getJavaTimeZoneId() {
        try {
            ZoneId zoneId = TimeZoneUtility.parseZoneId(this.timeZone);
            return zoneId;
        }
        catch (Exception ex) {
            // ignore exceptions here, we will use UTC as a fallback
        }

        return ZoneId.of("UTC");
    }

    /**
     * Returns the limit of parallel data quality checks that could be started at the same time on the connection.
     * @return Concurrency limit (number of parallel jobs) that are executing checks or null when no limits are enforced.
     */
    public Integer getParallelRunsLimit() {
        return parallelRunsLimit;
    }

    /**
     * Sets the concurrency limit of the number of checks that can run in parallel on this connection.
     * @param parallelRunsLimit New concurrency limit or null when no limit is applied.
     */
    public void setParallelRunsLimit(Integer parallelRunsLimit) {
        this.setDirtyIf(!Objects.equals(this.parallelRunsLimit, parallelRunsLimit));
        this.parallelRunsLimit = parallelRunsLimit;
    }

    /**
     * Returns the notification settings.
     * @return Notification settings.
     */
    public NotificationSettingsSpec getNotifications() {
        return notifications;
    }

    /**
     * Sets a new configuration of notifications.
     * @param notifications New notification settings.
     */
    public void setNotifications(NotificationSettingsSpec notifications) {
        setDirtyIf(!Objects.equals(this.notifications, notifications));
        this.notifications = notifications;
        propagateHierarchyIdToField(notifications, "notifications");
    }

    /**
     * Returns a collection of comments for this connection.
     * @return List of comments (or null).
     */
    public CommentsListSpec getComments() {
        return comments;
    }

    /**
     * Sets a list of comments for this connection.
     * @param comments Comments list.
     */
    public void setComments(CommentsListSpec comments) {
		setDirtyIf(!Objects.equals(this.comments, comments));
        this.comments = comments;
		propagateHierarchyIdToField(comments, "comments");
    }

    /**
     * Returns the default data streams configuration for all tables on the connection.
     * @return Default data streams configuration.
     */
    public DataStreamMappingSpec getDefaultDataStreamMapping() {
        return defaultDataStreamMapping;
    }

    /**
     * Returns the default data streams configuration for all tables on this connection.
     * @param defaultDataStreamMapping Data streams configuration.
     */
    public void setDefaultDataStreamMapping(DataStreamMappingSpec defaultDataStreamMapping) {
		setDirtyIf(!Objects.equals(this.defaultDataStreamMapping, defaultDataStreamMapping));
        this.defaultDataStreamMapping = defaultDataStreamMapping;
		propagateHierarchyIdToField(defaultDataStreamMapping, "default_data_stream_mapping");
    }

    /**
     * List of labels assigned to a table. Labels are used for targeting the execution of tests.
     * @return Labels collection.
     */
    public LabelSetSpec getLabels() {
        return labels;
    }

    /**
     * Changes a list of labels.
     * @param labels Labels collection.
     */
    public void setLabels(LabelSetSpec labels) {
        setDirtyIf(!Objects.equals(this.labels, labels));
        this.labels = labels;
        propagateHierarchyIdToField(labels, "labels");
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
     * Creates and returns a deep copy of this object.
     */
    @Override
    public ConnectionSpec deepClone() {
        ConnectionSpec cloned = (ConnectionSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @return Trimmed and expanded version of this object.
     */
    public ConnectionSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        try {
            ConnectionSpec cloned = (ConnectionSpec) super.clone();
            if (cloned.defaultDataStreamMapping != null) {
                cloned.defaultDataStreamMapping = cloned.defaultDataStreamMapping.expandAndTrim(secretValueProvider);
            }
            if (cloned.bigquery != null) {
                cloned.bigquery = cloned.bigquery.expandAndTrim(secretValueProvider);
            }
            if (cloned.snowflake != null) {
                cloned.snowflake = cloned.snowflake.expandAndTrim(secretValueProvider);
            }
            if (cloned.postgresql != null) {
                cloned.postgresql = cloned.postgresql.expandAndTrim(secretValueProvider);
            }
            if (cloned.notifications != null) {
                cloned.notifications = cloned.notifications.expandAndTrim(secretValueProvider);
            }
            cloned.comments = null;
            cloned.schedule = null; // we probably don't need it here
            cloned.schedules = null;
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned", ex);
        }
    }

    /**
     * Creates a trimmed version of the object without unwanted properties.
     * A trimmed version is passed to a Jinja2 sql template as a context parameter.
     * @return Trimmed version of this object.
     */
    public ConnectionSpec trim() {
        try {
            ConnectionSpec cloned = (ConnectionSpec) super.clone();
            cloned.defaultDataStreamMapping = null;
            cloned.comments = null;
            cloned.schedule = null;
            cloned.schedules = null;
            cloned.notifications = null;
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned", ex);
        }
    }

    /**
     * Retrieves the connection name from the hierarchy.
     * @return Connection name or null for a standalone connection spec object.
     */
    @JsonIgnore
    public String getConnectionName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }
        return hierarchyId.get(hierarchyId.size() - 2).toString();
    }

    /**
     * Returns the provider specific configuration object. Takes the name of the provider and returns the child object of that name (postgresql, snowflake, bigquery).
     * @return Provider specific configuration.
     */
    @JsonIgnore
    public ConnectionProviderSpecificParameters getProviderSpecificConfiguration() {
        if (this.providerType == null) {
            throw new DqoRuntimeException("Missing provider type in the connection");
        }

        HierarchyNode providerConfigChild = this.getChild(this.providerType.name());
        ConnectionProviderSpecificParameters providerConfiguration =
                (ConnectionProviderSpecificParameters) providerConfigChild;

        return providerConfiguration;
    }
}
