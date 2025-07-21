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
 * Helper method that runs a given runnable silently, without throwing any exceptions.
 */
public final class RunSilently {
    /**
     * Silently runs a runnable that could throw an exception.
     * @param runnable Runnable to call.
     *
     * @return Exception that was throw or null when no exception was raised.
     */
    public static Throwable run(RunnableWithException runnable) {
        try {
            runnable.run();
            return null;
        }
        catch (Throwable t) {
            // does nothing, eats the exception
            return t;
        }
    }
}
