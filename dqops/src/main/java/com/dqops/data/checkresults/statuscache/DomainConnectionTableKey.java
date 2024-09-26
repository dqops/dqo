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

package com.dqops.data.checkresults.statuscache;

import com.dqops.metadata.sources.PhysicalTableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * A key object that identifies every table. These keys are used in a cache to store the most recent
 * table quality status for each table or a data lineage cache.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "DomainConnectionTableKey", description = "Table key that identifies a table in the data quality cache or a data lineage cache.")
@Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.ToString()
public final class DomainConnectionTableKey {
    /**
     * Data domain name.
     */
    @JsonPropertyDescription("Data domain name.")
    private final String dataDomain;

    /**
     * Connection name.
     */
    @JsonPropertyDescription("Connection name.")
    private final String connectionName;

    /**
     * Physical table name.
     */
    @JsonPropertyDescription("Full table name, including the schema and the table names.")
    private final PhysicalTableName physicalTableName;

    /**
     * Creates a table status key.
     * @param dataDomain Data domain name.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     */
    public DomainConnectionTableKey(String dataDomain, String connectionName, PhysicalTableName physicalTableName) {
        this.dataDomain = dataDomain;
        this.connectionName = connectionName;
        this.physicalTableName = physicalTableName;
    }

    /**
     * Returns a table key within the data domain that identifies the table. It is based on the connection, schema and table names.
     * @return The table key a single string.
     */
    @JsonPropertyDescription("A string key that identifies the table within the data domain. It is based on the connection, schema and table names.")
    public String getCompactKey() {
        return this.connectionName + "." + this.physicalTableName.getSchemaName() + "." + this.physicalTableName.getTableName();
    }
}
