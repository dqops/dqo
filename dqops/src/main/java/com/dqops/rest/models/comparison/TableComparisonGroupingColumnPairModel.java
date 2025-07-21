/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.models.comparison;

import com.dqops.metadata.comparisons.TableComparisonGroupingColumnsPairSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Model that identifies a pair of column names used for grouping the data on both the compared table and the reference table. The groups are then matched (joined) by DQOps to compare aggregated results.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableComparisonGroupingColumnPairModel", description = "Model that identifies a pair of column names used for grouping the data on both the compared table and the reference table. " +
        "The groups are then matched (joined) by DQOps to compare aggregated results.")
@Data
public class TableComparisonGroupingColumnPairModel {
    /**
     * The name of the column on the compared table (the parent table) that is used in the GROUP BY clause to group rows before compared aggregates (row counts, sums, etc.) are calculated.
     * This column is also used to join (match) results to the reference table.
     */
    @JsonPropertyDescription("The name of the column on the compared table (the parent table) that is used in the GROUP BY clause to group rows before compared " +
            "aggregates (row counts, sums, etc.) are calculated. This column is also used to join (match) results to the reference table.")
    private String comparedTableColumnName;

    /**
     * The name of the column on the reference table (the source of truth) that is used in the GROUP BY clause to group rows before compared " +
     * "aggregates (row counts, sums, etc.) are calculated. This column is also used to join (match) results to the compared table.
     */
    @JsonPropertyDescription("The name of the column on the reference table (the source of truth) that is used in the GROUP BY clause to group rows before compared " +
            "aggregates (row counts, sums, etc.) are calculated. This column is also used to join (match) results to the compared table.")
    private String referenceTableColumnName;

    /**
     * Creates the compared columns column model from the specification.
     * @param columnsPairSpec Source compared columns pair specification.
     * @return Model for the object.
     */
    public static TableComparisonGroupingColumnPairModel fromColumnPairSpec(TableComparisonGroupingColumnsPairSpec columnsPairSpec) {
        return new TableComparisonGroupingColumnPairModel() {{
            setComparedTableColumnName(columnsPairSpec.getComparedTableColumnName());
            setReferenceTableColumnName(columnsPairSpec.getReferenceTableColumnName());
        }};
    }

    /**
     * Creates a grouping column pair specification object from this model.
     * @return Grouping column pair specification.
     */
    public TableComparisonGroupingColumnsPairSpec createColumnsPairSpec() {
        TableComparisonGroupingColumnsPairSpec result = new TableComparisonGroupingColumnsPairSpec();
        result.setComparedTableColumnName(this.getComparedTableColumnName());
        result.setReferenceTableColumnName(this.getReferenceTableColumnName());
        return result;
    }
}
