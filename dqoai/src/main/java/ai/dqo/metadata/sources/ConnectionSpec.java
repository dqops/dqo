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

import ai.dqo.connectors.ProviderType;
import ai.dqo.connectors.bigquery.BigQueryParametersSpec;
import ai.dqo.connectors.snowflake.SnowflakeParametersSpec;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.comments.CommentsListSpec;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionSpec;
import ai.dqo.metadata.groupings.DimensionsConfigurationSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.utils.datetime.TimeZoneUtility;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * Connection specification.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class ConnectionSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<ConnectionSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
			put("comments", o -> o.comments);
			put("default_time_series", o -> o.defaultTimeSeries);
			put("default_dimensions", o -> o.defaultDimensions);
			put("bigquery", o -> o.bigquery);
			put("snowflake", o -> o.snowflake);
            put("schedule", o -> o.schedule);
        }
    };

    @JsonPropertyDescription("Database name (for those sources that have a database/schema/table separation).")
    private String databaseName;

    @JsonPropertyDescription("Database provider type (required). Accepts: bigquery, snowflake.")
    private ProviderType providerType;

    @JsonPropertyDescription("JDBC driver url (overrides custom configuration for the provider and uses a hardcoded JDBC url).")
    private String url;

    @JsonPropertyDescription("Database user name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.")
    private String user;

    @JsonPropertyDescription("Database password. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.")
    private String password;

    @JsonPropertyDescription("BigQuery connection parameters. Specify parameters in the bigquery section.")
    private BigQueryParametersSpec bigquery;

    @JsonPropertyDescription("Snowflake connection parameters. Specify parameters in the snowflake section or set the url (which is the Snowflake JDBC url).")
    private SnowflakeParametersSpec snowflake;

    @JsonPropertyDescription("Timezone name for the time period timestamps. This should be the timezone of the monitored database. Use valid Java ZoneId name, the list of possible timezones is listed as 'TZ database name' on https://en.wikipedia.org/wiki/List_of_tz_database_time_zones")
    private String timeZone = "UTC";

    @JsonPropertyDescription("Default time series source configuration for all tables. Chooses the source for the time series for all tables. The configuration may be overridden on table, column and check levels. Time series of data quality sensor readings may be calculated from a timestamp column or a current time may be used. Also the time gradient (day, week) may be configured to analyse the data behavior at a correct scale.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TimeSeriesConfigurationSpec defaultTimeSeries;

    @JsonPropertyDescription("Default data quality dimensions configuration for all tables. The configuration may be overridden on table, column and check level. Dimensions are configured in two cases: (1) a static dimension is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). (2) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a 'country' column used for partitioning.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DimensionsConfigurationSpec defaultDimensions;

    @JsonPropertyDescription("Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private RecurringScheduleSpec schedule;

    @JsonPropertyDescription("Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CommentsListSpec comments;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LinkedHashMap<String, String> properties = new LinkedHashMap<>();

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private LinkedHashMap<String, String> originalProperties = new LinkedHashMap<>(); // used to perform comparison in the isDirty check

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
     * Returns a physical database name.
     * @return Physical database name.
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * Sets a physical database name.
     * @param databaseName Physical database name.
     */
    public void setDatabaseName(String databaseName) {
		setDirtyIf(!Objects.equals(this.databaseName, databaseName));
        this.databaseName = databaseName;
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
     * Returns a JDBC database connection url.
     * @return JDBC database connection url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets a JDBC database connection url.
     * @param url JDBC connection url.
     */
    public void setUrl(String url) {
		setDirtyIf(!Objects.equals(this.url, url));
        this.url = url;
    }

    /**
     * Returns the user that is used to log in to the data source (JDBC user or similar).
     * @return User name.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets a user name.
     * @param user User name.
     */
    public void setUser(String user) {
		setDirtyIf(!Objects.equals(this.user, user));
        this.user = user;
    }

    /**
     * Returns a password used to authenticate to the server.
     * @return Password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets a password that is used to connect to the database.
     * @param password Password.
     */
    public void setPassword(String password) {
        // TODO: support storing passwords in a credentials file,
        // we can support a special format like "$secret"
        // which means that the password must be stored locally or taken from an env variable $secret,
        // the format could be a name of an ENV variable or a variable in .credentials folder,
        // we can also support notation @filename which could be used for special files like private keys (GCP),
        // those files would be stored in the .credentials folder only
		setDirtyIf(!Objects.equals(this.password, password));
        this.password = password;
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
     * Returns a key/value map of additional provider specific properties.
     * @return Key/value dictionary of additional properties.
     */
    public LinkedHashMap<String, String> getProperties() {
        return properties;
    }

    /**
     * Sets a dictionary of additional connection parameters.
     * @param properties Key/value dictionary with extra parameters.
     */
    public void setProperties(LinkedHashMap<String, String> properties) {
		setDirtyIf(!Objects.equals(this.properties, properties));
        this.properties = properties;
		this.originalProperties = (LinkedHashMap<String, String>) properties.clone();
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
     * Returns the time series configuration for all tables on the connection.
     * @return Time series configuration.
     */
    public TimeSeriesConfigurationSpec getDefaultTimeSeries() {
        return defaultTimeSeries;
    }

    /**
     * Sets a new time series configuration for all tables on the connection.
     * @param defaultTimeSeries New time series configuration.
     */
    public void setDefaultTimeSeries(TimeSeriesConfigurationSpec defaultTimeSeries) {
		setDirtyIf(!Objects.equals(this.defaultTimeSeries, defaultTimeSeries));
        this.defaultTimeSeries = defaultTimeSeries;
		propagateHierarchyIdToField(defaultTimeSeries, "default_time_series");
    }

    /**
     * Returns the data quality measure dimensions configuration for all tables on the connection.
     * @return Dimension configuration.
     */
    public DimensionsConfigurationSpec getDefaultDimensions() {
        return defaultDimensions;
    }

    /**
     * Returns the dimension configuration for all tables on this connection.
     * @param defaultDimensions Dimension configuration.
     */
    public void setDefaultDimensions(DimensionsConfigurationSpec defaultDimensions) {
		setDirtyIf(!Objects.equals(this.defaultDimensions, defaultDimensions));
        this.defaultDimensions = defaultDimensions;
		propagateHierarchyIdToField(defaultDimensions, "default_dimensions");
    }

    /**
     * Check if the object is dirty (has changes).
     *
     * @return True when the object is dirty and has modifications.
     */
    @Override
    public boolean isDirty() {
        return super.isDirty() || !Objects.equals(this.properties, this.originalProperties);
    }

    /**
     * Clears the dirty flag (sets the dirty to false). Called after flushing or when changes should be considered as unimportant.
     * @param propagateToChildren When true, clears also the dirty status of child objects.
     */
    @Override
    public void clearDirty(boolean propagateToChildren) {
        super.clearDirty(propagateToChildren);
		this.originalProperties = (LinkedHashMap<String, String>) this.properties.clone();
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
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a deep copy of this object.
     */
    @Override
    public ConnectionSpec clone() {
        try {
            ConnectionSpec cloned = (ConnectionSpec)super.clone();
            if (cloned.bigquery != null) {
                cloned.bigquery = cloned.bigquery.clone();
            }
            if (cloned.snowflake != null) {
                cloned.snowflake = cloned.snowflake.clone();
            }
            if (cloned.defaultDimensions != null) {
                cloned.defaultDimensions = cloned.defaultDimensions.clone();
            }
            if (cloned.defaultTimeSeries != null) {
                cloned.defaultTimeSeries = cloned.defaultTimeSeries.clone();
            }
            if (cloned.comments != null) {
                cloned.comments = cloned.comments.clone();
            }
            if (cloned.schedule != null) {
                cloned.schedule = cloned.schedule.clone();
            }
            if (cloned.properties != null) {
                cloned.properties = (LinkedHashMap<String, String>) cloned.properties.clone();
            }
            if (cloned.originalProperties != null) {
                cloned.originalProperties = (LinkedHashMap<String, String>) cloned.originalProperties.clone();
            }

            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned", ex);
        }
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @return Trimmed and expanded version of this object.
     */
    public ConnectionSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        try {
            ConnectionSpec cloned = (ConnectionSpec) super.clone();
            cloned.databaseName = secretValueProvider.expandValue(cloned.databaseName);
            cloned.url = secretValueProvider.expandValue(cloned.url);
            cloned.user = secretValueProvider.expandValue(cloned.user);
            cloned.password = secretValueProvider.expandValue(cloned.password);
            cloned.properties = secretValueProvider.expandProperties(cloned.properties);
            if (cloned.defaultTimeSeries != null) {
                cloned.defaultTimeSeries = cloned.defaultTimeSeries.expandAndTrim(secretValueProvider);
            }
            if (cloned.defaultDimensions != null) {
                cloned.defaultDimensions = cloned.defaultDimensions.expandAndTrim(secretValueProvider);
            }
            if (cloned.bigquery != null) {
                cloned.bigquery = cloned.bigquery.expandAndTrim(secretValueProvider);
            }
            if (cloned.snowflake != null) {
                cloned.snowflake = cloned.snowflake.expandAndTrim(secretValueProvider);
            }
            cloned.comments = null;
            cloned.schedule = null; // we probably don't need it here
            cloned.originalProperties = null;
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
            cloned.defaultTimeSeries = null;
            cloned.defaultDimensions = null;
            cloned.comments = null;
            cloned.schedule = null;
            cloned.originalProperties = null;
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
}
