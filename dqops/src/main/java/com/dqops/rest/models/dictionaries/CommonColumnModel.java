/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.models.dictionaries;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Dictionary model used for combo boxes to select a column. Returns a column name that exists in any table within a connection (source)
 * and a count of the column occurrence. It is used to find the most common columns.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CommonColumnModel", description = "Common column model that describes a column name that is frequently used in tables within a connection")
public class CommonColumnModel implements Comparable<CommonColumnModel> {
    @JsonPropertyDescription("Column name.")
    private String columnName;

    @JsonPropertyDescription("Count of tables that are have a column with this name.")
    private int tablesCount;

    public CommonColumnModel() {
    }

    public CommonColumnModel(String columnName, int tablesCount) {
        this.columnName = columnName;
        this.tablesCount = tablesCount;
    }

    @Override
    public int compareTo(CommonColumnModel other) {
        int columnCountCompare = Integer.compare(this.tablesCount, other.tablesCount) * -1; // reverse order
        if (columnCountCompare == 0) {
            return this.columnName.compareTo(other.columnName);
        }

        return columnCountCompare;
    }
}
