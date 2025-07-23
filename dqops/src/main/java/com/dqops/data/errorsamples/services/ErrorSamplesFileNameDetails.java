/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

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
