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

import java.time.LocalDateTime;

/**
 * File name for an error samples CSV file creator.
 */
public interface ErrorSamplesFileNameCreator {

    /**
     * Creates a file name for an error samples CSV file.
     * @param details ErrorSamplesFileNameDetails with names used when creating a file name.
     * @param dateTime Date time added to the end of the file name.
     * @return The file name.
     */
    String createFileName(ErrorSamplesFileNameDetails details, LocalDateTime dateTime);
}
