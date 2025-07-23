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

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Creates a file name for error samples CSV file.
 */
@Component
public class ErrorSamplesFileNameCreatorImpl implements ErrorSamplesFileNameCreator {

    /**
     * Creates a file name for an error samples CSV file.
     * @param details ErrorSamplesFileNameDetails with names used when creating a file name.
     * @param dateTime Date time added to the end of the file name.
     * @return The file name.
     */
    public String createFileName(ErrorSamplesFileNameDetails details, LocalDateTime dateTime) {
        String dateTimeFormatted = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));

        String resourceName = details.getColumnName() != null
                ? String.join(".", details.getConnectionName(), details.getSchemaName(), details.getTableName(), details.getColumnName())
                : String.join(".", details.getConnectionName(), details.getSchemaName(), details.getTableName());

        String fileName = "Error_samples" + "_" +
                resourceName + "_" +
                (details.getCheckCategory() != null ? details.getCheckCategory() + "_" : "")  +
                (details.getCheckName() != null ? details.getCheckName() + "_" : "")  +
                dateTimeFormatted + ".csv";
        return fileName;
    }

}
