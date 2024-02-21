package com.dqops.metadata.sources.fileformat;

import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ColumnSpecMap;
import com.dqops.metadata.sources.TableSpec;
import com.google.common.base.CaseFormat;

import java.util.List;
import java.util.Map;

/**
 * The formatter for duckdb table that is read from files with given filepath, column names and file format specific options.
 */
public class TableOptionsFormatter {

    private final StringBuilder sourceTable;
    private final String commaNewLineIdentTwo  = ",\n  ";

    /**
     * Opens the string builder describing the method name used for reading data and filling paths to the files with data.
     * @param methodName Method name used for reading data.
     * @param filePathList List of paths to files with data.
     */
    public TableOptionsFormatter(String methodName, List<String> filePathList) {
        this.sourceTable = new StringBuilder();
        sourceTable.append(methodName);
        sourceTable.append("(\n");
        sourceTable.append("  ").append(formatFilePaths(filePathList));
    }

    /**
     * Formats a single or multiple filepaths.
     * @param filePaths The list of file paths.
     * @return The formatted string.
     */
    private String formatFilePaths(List<String> filePaths){
        if(filePaths.size() == 1){
            return "'" + filePaths.get(0) + "'";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("'").append(filePaths.get(0)).append("'");
        filePaths.stream().skip(1).forEach(filePath -> {
            sb.append(", '").append(filePath).append("'");
        });
        sb.append("]");
        return sb.toString();
    }

    /**
     * Formats entry to a "key = value" string when value is present.
     * @param fieldName Name of the field used as a key
     * @param value The value which is read via the toString()
     * @param <T> A generic type of the field
     */
    public <T> void formatValueWhenSet(String fieldName, T value){
        if(value != null){
            sourceTable.append(commaNewLineIdentTwo)
                    .append(makeSnakeCase(fieldName)).append(" = ").append(value);
        }
    }

    /**
     * Formats entry to a "key = 'value'" string when value is present.
     * @param fieldName Name of the field used as a key
     * @param value The value which is read via the toString(). The value is surrounded with apostrophes.
     * @param <T> A generic type of the field
     */
    public <T> void formatStringWhenSet(String fieldName, T value){
        if(value != null){
            sourceTable.append(commaNewLineIdentTwo)
                    .append(makeSnakeCase(fieldName)).append(" = '").append(value).append("'");
        }
    }

    /**
     * Formats columns to the key value like string, where keys are the names of columns, and values are types of them.
     * @param fieldName The name of the field that will become before the = sign. Commonly "columns".
     * @param tableSpec The table spec object with columns names that are used for formatting the columns string.
     */
    public void formatColumns(String fieldName, TableSpec tableSpec){

        if(tableSpec != null && tableSpec.getColumns() != null && !tableSpec.getColumns().isEmpty()){
            ColumnSpecMap columnSpecMap = tableSpec.getColumns();

            sourceTable.append(commaNewLineIdentTwo);
            sourceTable.append(makeSnakeCase(fieldName)).append(" = {");

            Map.Entry<String, ColumnSpec> firstEntry = columnSpecMap.entrySet().stream().findFirst().get();
            String firstColumnType = firstEntry.getValue().getTypeSnapshot().getColumnType();
            sourceTable.append("\n    '").append(firstEntry.getKey()).append("': '").append(firstColumnType).append("'");

            columnSpecMap.entrySet().stream().skip(1).forEachOrdered(columnSpecEntry -> {
                String columnType = columnSpecEntry.getValue().getTypeSnapshot().getColumnType();
                sourceTable.append(",\n    '").append(columnSpecEntry.getKey()).append("': '").append(columnType).append("'");
            });
            sourceTable.append("\n  }");
        }
    }

    /**
     * Reformats string from the camelCase to lower_underscore format.
     * @param fieldName The camelCase field that will be reformatted.
     * @return Name of the field with lower_underscore format.
     */
    private String makeSnakeCase(String fieldName){
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
    }

    /**
     * The method stringifies the StringBuilder adding the close bracket to the end of the built string.
     * @return
     */
    @Override
    public String toString() {
        sourceTable.append("\n)");
        return sourceTable.toString();
    }
}
