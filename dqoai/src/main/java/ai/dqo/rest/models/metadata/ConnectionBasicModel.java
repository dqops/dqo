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
package ai.dqo.rest.models.metadata;

import ai.dqo.connectors.ProviderType;
import ai.dqo.connectors.bigquery.BigQueryParametersSpec;
import ai.dqo.connectors.postgresql.PostgresqlParametersSpec;
import ai.dqo.connectors.snowflake.SnowflakeParametersSpec;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.search.StatisticsCollectorSearchFilters;
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

    @JsonPropertyDescription("Database provider type (required). Accepts: bigquery, snowflake.")
    private ProviderType providerType;

    @JsonPropertyDescription("BigQuery connection parameters. Specify parameters in the bigquery section.")
    private BigQueryParametersSpec bigquery;

    @JsonPropertyDescription("Snowflake connection parameters.")
    private SnowflakeParametersSpec snowflake;

    @JsonPropertyDescription("PostgreSQL connection parameters.")
    private PostgresqlParametersSpec postgresql;

    @JsonPropertyDescription("Timezone name for the time period timestamps. This should be the timezone of the monitored database. Use valid Java ZoneId name, the list of possible timezones is listed as 'TZ database name' on https://en.wikipedia.org/wiki/List_of_tz_database_time_zones")
    private String timeZone = "UTC";

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run all checks within this connection.")
    private CheckSearchFilters runChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"collect statistics\" job that should be pushed to the job queue in order to run all statistics collectors within this connection.")
    private StatisticsCollectorSearchFilters collectStatisticsJobTemplate;

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
            setProviderType(connectionSpec.getProviderType());
            setTimeZone(connectionSpec.getTimeZone());
            setBigquery(connectionSpec.getBigquery());
            setSnowflake(connectionSpec.getSnowflake());
            setPostgresql(connectionSpec.getPostgresql());
            setRunChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setEnabled(true);
            }});
            setCollectStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
            {{
                setConnectionName(connectionName);
                setEnabled(true);
            }});
        }};
    }

    /**
     * Updates a connection specification by copying all fields.
     * @param targetConnectionSpec Target connection specification to update.
     */
    public void copyToConnectionSpecification(ConnectionSpec targetConnectionSpec) {
        targetConnectionSpec.setProviderType(this.getProviderType());
        targetConnectionSpec.setTimeZone(this.getTimeZone());
        targetConnectionSpec.setBigquery(this.getBigquery());
        targetConnectionSpec.setSnowflake(this.getSnowflake());
        targetConnectionSpec.setPostgresql(this.getPostgresql());
    }
}
