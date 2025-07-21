/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.sources.fileformat.avro;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.fileformat.TableOptionsFormatter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.util.List;
import java.util.Objects;

/**
 * Csv file format specification for querying data in the Avro format files.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class AvroFileFormatSpec extends AbstractSpec {

    private static final ChildHierarchyNodeFieldMapImpl<AvroFileFormatSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Whether or not an extra filename column should be included in the result.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean filename;

    /**
     * Formats the table options to be used in SQL query. The set (non null) options are added only.
     *
     * @param filePathList The names of files with data.
     * @return The formatted source table with the options.
     */
    public String buildSourceTableOptionsString(List<String> filePathList, TableSpec tableSpec) {
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("read_avro", filePathList);
        tableOptionsFormatter.formatValueWhenSet(Fields.filename, filename);
        return tableOptionsFormatter.build();
    }

    /**
     * Returns whether or not an extra filename column should be included in the result.
     *
     * @return The filename option state.
     */
    public Boolean getFilename() {
        return filename;
    }

    /**
     * Sets that an extra filename column should be included in the result.
     *
     * @param filename The filename option state.
     */
    public void setFilename(Boolean filename) {
        setDirtyIf(!Objects.equals(this.filename, filename));
        this.filename = filename;
    }
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a deep clone (copy) of this object.
     */
    @Override
    public AvroFileFormatSpec deepClone() {
        return (AvroFileFormatSpec)super.deepClone();
    }

    /**
     * Creates an expanded and trimmed deep copy of the spec.
     * Configurable properties will be expanded if they contain environment variables or secrets.
     *
     * @param secretValueProvider Secret value provider.
     * @param lookupContext       Secret value lookup context used to access shared credentials.
     * @return Cloned, trimmed and expanded table specification.
     */
    public AvroFileFormatSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        AvroFileFormatSpec cloned = this.deepClone();
        return cloned;
    }
}
