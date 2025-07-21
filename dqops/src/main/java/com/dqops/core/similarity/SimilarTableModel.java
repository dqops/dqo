/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.similarity;

import com.dqops.metadata.sources.PhysicalTableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Model that describes a table that is similar to a reference table. Similar tables are used to build the data lineage graph.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode
@ApiModel(value = "SimilarTableModel", description = "Model that describes a table that is similar to a reference table. Similar tables are used to build the data lineage graph.")
public class SimilarTableModel {
    /**
     * Table similarity score. Lower numbers indicate higher similarity.
     */
    @JsonPropertyDescription("Table similarity score. Lower numbers indicate higher similarity.")
    private int difference;

    /**
     * A similarity score as a percentage. A value 100.0 means that the tables are probably equal.
     */
    @JsonPropertyDescription("A similarity score as a percentage. A value 100.0 means that the tables are probably equal.")
    private double similarityPct;

    /**
     * Connection name
     */
    @JsonPropertyDescription("Connection name")
    private String connectionName;

    /**
     * Schema name
     */
    @JsonPropertyDescription("Schema name")
    private String schemaName;

    /**
     * Table name
     */
    @JsonPropertyDescription("Table name")
    private String tableName;

    public SimilarTableModel() {
    }

    public SimilarTableModel(int difference, String connectionName, PhysicalTableName physicalTableName) {
        this.difference = difference;
        this.similarityPct = ((DataSimilarityFormula.WORD_COUNT * 64) - difference) * 100.0 / (DataSimilarityFormula.WORD_COUNT * 64);
        this.connectionName = connectionName;
        this.schemaName = physicalTableName.getSchemaName();
        this.tableName = physicalTableName.getTableName();
    }
}
