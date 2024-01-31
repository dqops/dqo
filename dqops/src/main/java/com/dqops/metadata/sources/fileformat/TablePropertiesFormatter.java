package com.dqops.metadata.sources.fileformat;

import com.google.common.base.CaseFormat;

import java.util.List;
import java.util.Map;

public class TablePropertiesFormatter {

    StringBuilder sourceTable;

    public TablePropertiesFormatter(String methodName, List<String> filePathList) {
        this.sourceTable = new StringBuilder();
        sourceTable.append(methodName);
        sourceTable.append("(");
        sourceTable.append(formatFilePaths(filePathList));
    }

    private String formatFilePaths(List<String> filePaths){
        if(filePaths.size() == 1){
            return "'" + filePaths.get(0) + "'";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        filePaths.forEach(tableName -> {
            sb.append("'").append(tableName).append("',");
        });
        sb.append("]");
        return sb.toString();
    }

    public void formatBooleanWhenSet(String fieldName, Boolean value){
        if(value != null){
            sourceTable.append(",").append(makeSnakeCase(fieldName)).append(" = ").append(value);
        }
    }

    public void formatStringWhenSet(String fieldName, String value){
        if(value != null){
            sourceTable.append(",").append(makeSnakeCase(fieldName)).append(" = '").append(value).append("'");
        }
    }

    public void formatLongWhenSet(String fieldName, Long value){
        if(value != null){
            sourceTable.append(",").append(makeSnakeCase(fieldName)).append(" = ").append(value);
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
        sourceTable.append(")");
        return sourceTable.toString();
    }
}
