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

import java.io.Closeable;

/**
 * Closes a closeable object, ignoring exceptions.
 */
public class CloseableHelper {
    /**
     * Closes a {@link Closeable} object silently, ignoring all exceptions.
     * @param closeable Closeable object (like file input stream) to close.
     */
    public static void closeSilently(Closeable closeable) {
        try {
            closeable.close();
        }
        catch (Exception ex) {
            // ignore
        }
    }
}
