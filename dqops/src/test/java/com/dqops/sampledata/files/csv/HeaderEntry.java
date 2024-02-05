package com.dqops.sampledata.files.csv;

import lombok.AllArgsConstructor;
import lombok.Data;
import tech.tablesaw.api.ColumnType;

@Data
@AllArgsConstructor
public class HeaderEntry {
    private String columnName;
    private ColumnType columnType;
}
