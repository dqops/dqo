package com.dqops.metadata.sources.fileformat;

import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ColumnSpecMap;
import com.dqops.metadata.sources.TableSpec;
import com.google.common.base.CaseFormat;

import java.util.List;
import java.util.Map;

public class TableOptionsFormatter {

    private final StringBuilder sourceTable;
    private final String commaNewLineIdentTwo  = ",\n  ";

    public TableOptionsFormatter(String methodName, List<String> filePathList) {
        this.sourceTable = new StringBuilder();
        sourceTable.append(methodName);
        sourceTable.append("(\n");
        sourceTable.append("  ").append(formatFilePaths(filePathList));
    }

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

    public <T> void formatValueWhenSet(String fieldName, T value){
        if(value != null){
            sourceTable.append(commaNewLineIdentTwo)
                    .append(makeSnakeCase(fieldName)).append(" = ").append(value);
        }
    }

    public <T> void formatStringWhenSet(String fieldName, T value){
        if(value != null){
            sourceTable.append(commaNewLineIdentTwo)
                    .append(makeSnakeCase(fieldName)).append(" = '").append(value).append("'");
        }
    }

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

    private String makeSnakeCase(String fieldName){
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
    }

    @Override
    public String toString() {
        sourceTable.append("\n)");
        return sourceTable.toString();
    }
}
