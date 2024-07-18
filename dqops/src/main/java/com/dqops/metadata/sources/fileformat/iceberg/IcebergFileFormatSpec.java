package com.dqops.metadata.sources.fileformat.iceberg;

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
 * Iceberg file format specification for querying data in the csv format files.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class IcebergFileFormatSpec extends AbstractSpec {

    private static final ChildHierarchyNodeFieldMapImpl<IcebergFileFormatSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("The option ensures that some path resolution is performed, which allows scanning Iceberg tables that are moved.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean allowMovedPaths = true;

    /**
     * Formats the table options to be used in SQL query. The set (non null) options are added only.
     *
     * @param filePathList The names of files with data.
     * @return The formatted source table with the options.
     */
    public String buildSourceTableOptionsString(List<String> filePathList, TableSpec tableSpec) {
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("iceberg_scan", filePathList);
        tableOptionsFormatter.formatValueWhenSet(Fields.allowMovedPaths, allowMovedPaths);
        return tableOptionsFormatter.build();
    }

    /**
     * Returns option that ensures that some path resolution is performed, which allows scanning Iceberg tables that are moved.
     *
     * @return Allow moved paths option state.
     */
    public Boolean getAllowMovedPaths() {
        return allowMovedPaths;
    }

    /**
     * Sets option that ensures that some path resolution is performed, which allows scanning Iceberg tables that are moved.
     *
     * @param allowMovedPaths Allow moved paths option state.
     */
    public void setAllowMovedPaths(Boolean allowMovedPaths) {
        setDirtyIf(!Objects.equals(this.allowMovedPaths, allowMovedPaths));
        this.allowMovedPaths = allowMovedPaths;
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
    public IcebergFileFormatSpec deepClone() {
        return (IcebergFileFormatSpec)super.deepClone();
    }

}
