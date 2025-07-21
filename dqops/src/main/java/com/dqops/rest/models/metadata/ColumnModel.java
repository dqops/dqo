/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.models.metadata;

import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Table model that returns the specification of a single column in the REST Api.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ColumnModel", description = "Full column model")
public class ColumnModel {
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Physical table name including the schema and table names.")
    private PhysicalTableName table;

    @JsonPropertyDescription("Column name.")
    private String columnName;

    @JsonPropertyDescription("Column hash that identifies the column using a unique hash code.")
    private Long columnHash;

    @JsonPropertyDescription("Full column specification.")
    private ColumnSpec spec;

    /**
     * Boolean flag that decides if the current user can update or delete this object.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete this object.")
    private boolean canEdit;

    /**
     * Optional parsing error that was captured when parsing the YAML file.
     * This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.
     */
    @JsonPropertyDescription("Optional parsing error that was captured when parsing the YAML file. " +
            "This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.")
    private String yamlParsingError;

    public ColumnModel() {
    }

    public static class ColumnModelSampleFactory implements SampleValueFactory<ColumnModel> {
        @Override
        public ColumnModel createSample() {
            ColumnSpec columnSpec = new ColumnSpec.ColumnSpecSampleFactory().createSample();
            return new ColumnModel() {{
                setConnectionName(SampleStringsRegistry.getConnectionName());
                setTable(PhysicalTableName.fromSchemaTableFilter(SampleStringsRegistry.getSchemaTableName()));
                setColumnName(SampleStringsRegistry.getColumnName());
                setColumnHash(columnSpec.getHierarchyId() != null ? columnSpec.getHierarchyId().hashCode64() : null);
                setSpec(columnSpec);
                setCanEdit(true);
            }};
        }
    }
}
