/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.completion.completers.cache;

import com.dqops.utils.exceptions.DqoRuntimeException;

/**
 * Exception thrown when the CLI completer cache failed.
 */
public class CliCompleterCacheException extends DqoRuntimeException {
    public CliCompleterCacheException(String message, Throwable cause) {
        super(message, cause);
    }
}
