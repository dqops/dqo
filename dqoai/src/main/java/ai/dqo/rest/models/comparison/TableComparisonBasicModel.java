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
package ai.dqo.rest.models.comparison;

import ai.dqo.metadata.sources.PhysicalTableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Model that contains the basic information about a table-to-table comparison defined on a compared table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableComparisonBasicModel", description = "Model that contains the basic information about a table-to-table comparison defined on a compared table.")
@Data
public class TableComparisonBasicModel {
    @JsonPropertyDescription("The name of the table comparison that is defined in the 'comparisons' node on the table specification.")
    private String comparisonName;

    /**
     * Compared connection name - the connection name to the data source that is compared (verified).
     */
    @JsonPropertyDescription("Compared connection name - the connection name to the data source that is compared (verified).")
    private String comparedConnection;

    /**
     * The schema and table name of the compared table that is verified.
     */
    @JsonPropertyDescription("The schema and table name of the compared table that is verified.")
    private PhysicalTableName comparedTable;

    /**
     * Reference connection name - the connection name to the data source that has the reference data to compare to.
     */
    @JsonPropertyDescription("Reference connection name - the connection name to the data source that has the reference data to compare to.")
    private String referenceConnection;

    /**
     * The schema and table name of the reference table that has the expected data.
     */
    @JsonPropertyDescription("The schema and table name of the reference table that has the expected data.")
    private PhysicalTableName referenceTable;

    @JsonPropertyDescription("The name of the data grouping configuration on the parent table that will be used for comparison. " +
            "When the parent table has no data grouping configurations, compares the whole table without grouping.")
    private String comparedTableGroupingName;

    @JsonPropertyDescription("The name of the data grouping configuration on the referenced name that will be used for comparison. " +
            "When the reference table has no data grouping configurations, compares the whole table without grouping. " +
            "The data grouping configurations on the compared table and the reference table must have the same grouping dimension levels configured, but the configuration (the names of the columns) could be different.")
    private String referenceTableGroupingName;
}
