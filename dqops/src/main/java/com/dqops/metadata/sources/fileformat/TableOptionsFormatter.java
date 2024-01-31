package com.dqops.metadata.sources.fileformat;

import com.google.common.base.CaseFormat;

import java.util.List;
import java.util.Map;

public class TableOptionsFormatter {

    private final StringBuilder sourceTable;
    private final String commaNewLine = ",\n  ";

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
            sourceTable.append(commaNewLine)
                    .append(makeSnakeCase(fieldName)).append(" = ").append(value);
        }
    }

    public void formatStringWhenSet(String fieldName, String value){
        if(value != null){
            sourceTable.append(commaNewLine)
                    .append(makeSnakeCase(fieldName)).append(" = '").append(value).append("'");
        }
    }

    public void formatMapWhenSet(String fieldName, Map<String, String> value){
        if(value != null && !value.isEmpty()){
            sourceTable.append(",");
            sourceTable.append(makeSnakeCase(fieldName)).append(" = {");
            value.forEach((k, v) -> {
                sourceTable.append("'").append(k).append("': '").append(v).append("',");
            });
            sourceTable.append("}");
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
