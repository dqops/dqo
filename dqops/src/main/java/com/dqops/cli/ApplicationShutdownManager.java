/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli;

/**
 * Spring boot shutdown manager - allows the application to request the shutdown, because the web server must be stopped.
 */
public interface ApplicationShutdownManager {
    /**
     * Initializes an application shutdown, given the application return code to return.
     *
     * @param returnCode Return code.
     */
    void initiateShutdown(int returnCode);
}
