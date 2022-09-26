package ai.dqo.rest.models.metadata;

import ai.dqo.connectors.ProviderType;
import ai.dqo.connectors.bigquery.BigQueryParametersSpec;
import ai.dqo.connectors.snowflake.SnowflakeParametersSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Connection model returned by the rest api that is limited only to the basic fields, excluding nested nodes.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ConnectionBasicModel", description = "Basic connection model with a subset of parameters, excluding all nested objects.")
public class ConnectionBasicModel {
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Connection hash that identifies the connection using a unique hash code.")
    private Long connectionHash;

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

    /**
     * Creates a basic connection model from a connection specification by cherry-picking relevant fields.
     * @param connectionName Connection name to store in the model.
     * @param connectionSpec Source connection specification.
     * @return Basic connection model.
     */
    public static ConnectionBasicModel fromConnectionSpecification(String connectionName, ConnectionSpec connectionSpec) {
        return new ConnectionBasicModel() {{
            setConnectionName(connectionName);
            setConnectionHash(connectionSpec.getHierarchyId() != null ? connectionSpec.getHierarchyId().hashCode64() : null);
            setDatabaseName(connectionSpec.getDatabaseName());
            setProviderType(connectionSpec.getProviderType());
            setUrl(connectionSpec.getUrl());
            setUser(connectionSpec.getUser());
            setPassword(connectionSpec.getPassword());  // TODO: to verify if we want to return it
            setTimeZone(connectionSpec.getTimeZone());
            setBigquery(connectionSpec.getBigquery());
            setSnowflake(connectionSpec.getSnowflake());
        }};
    }

    /**
     * Updates a connection specification by copying all fields.
     * @param targetConnectionSpec Target connection specification to update.
     */
    public void copyToConnectionSpecification(ConnectionSpec targetConnectionSpec) {
        targetConnectionSpec.setDatabaseName(this.getDatabaseName());
        targetConnectionSpec.setProviderType(this.getProviderType());
        targetConnectionSpec.setUrl(this.getUrl());
        targetConnectionSpec.setUser(this.getUser());
        targetConnectionSpec.setPassword(this.getPassword());
        targetConnectionSpec.setTimeZone(this.getTimeZone());
        targetConnectionSpec.setBigquery(this.getBigquery());
        targetConnectionSpec.setSnowflake(this.getSnowflake());
    }
}
