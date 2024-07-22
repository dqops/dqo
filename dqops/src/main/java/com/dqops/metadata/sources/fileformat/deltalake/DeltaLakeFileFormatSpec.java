package com.dqops.metadata.sources.fileformat.deltalake;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.fileformat.TableOptionsFormatter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * DeltaLake file format specification for querying data in the csv format files.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class DeltaLakeFileFormatSpec extends AbstractSpec {

    private static final ChildHierarchyNodeFieldMapImpl<DeltaLakeFileFormatSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    /**
     * Formats the table options to be used in SQL query. The set (non null) options are added only.
     *
     * @param filePathList The names of files with data.
     * @return The formatted source table with the options.
     */
    public String buildSourceTableOptionsString(List<String> filePathList, TableSpec tableSpec) {
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("delta_scan", filePathList);
        return tableOptionsFormatter.build();
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
    public DeltaLakeFileFormatSpec deepClone() {
        return (DeltaLakeFileFormatSpec)super.deepClone();
    }

}
