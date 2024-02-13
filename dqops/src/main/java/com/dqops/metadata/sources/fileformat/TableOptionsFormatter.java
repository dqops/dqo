package com.dqops.metadata.sources.fileformat;

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

    public void formatMapWhenSet(String fieldName, Map<String, String> value){
        if(value != null && !value.isEmpty()){
            sourceTable.append(commaNewLineIdentTwo);
            sourceTable.append(makeSnakeCase(fieldName)).append(" = {");

            Map.Entry<String, String> firstEntry = value.entrySet().stream().findFirst().get();
            sourceTable.append("\n    '").append(firstEntry.getKey()).append("': '").append(firstEntry.getValue()).append("'");
            value.entrySet().stream().skip(1).forEach(stringStringEntry -> {
                sourceTable.append(",\n    '").append(stringStringEntry.getKey()).append("': '").append(stringStringEntry.getValue()).append("'");
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
