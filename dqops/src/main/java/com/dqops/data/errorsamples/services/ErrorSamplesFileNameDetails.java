package com.dqops.data.errorsamples.services;

import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

/**
 * File name details containing fields which are used when creating a file name.
 */
@Builder
@Data
public class ErrorSamplesFileNameDetails {
    private String connectionName;
    private String schemaName;
    private String tableName;
    private String columnName;
    private String checkCategory;
    private String checkName;
    private DateTime datetime;
}
