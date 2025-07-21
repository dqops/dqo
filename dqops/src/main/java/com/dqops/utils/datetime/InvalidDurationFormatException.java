/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.datetime;

import com.dqops.utils.exceptions.DqoRuntimeException;

/**
 * Exception thrown when the given text was not a supported duration text, such as 10s, 10m, 5h
 */
public class InvalidDurationFormatException extends DqoRuntimeException {
    public InvalidDurationFormatException(String message) {
        super(message);
    }

    public InvalidDurationFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
