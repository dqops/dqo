/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.exceptions;

/**
 * Interface for DQOps exceptions with the ability to provide user-friendly messages.
 */
public interface DqoErrorUserMessage {
    /**
     * Get an informative explanation for throwing the exception.
     * @return User-friendly message with the cause of this exception.
     */
    String getUserFriendlyMessage();
}
