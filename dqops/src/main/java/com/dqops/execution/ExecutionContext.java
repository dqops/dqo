/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution;

import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;

/**
 * Object that stores a current reference to the dqo come and user home to look up definitions.
 * It is provided as a context to run checks and profilers.
 */
public class ExecutionContext {
    private final UserHomeContext userHomeContext;
    private final DqoHomeContext dqoHomeContext;

    /**
     * Creates a check/profiler/.. execution context with references to the user home and dqo come.
     * @param userHomeContext User home context.
     * @param dqoHomeContext Dqo application home context.
     */
    public ExecutionContext(UserHomeContext userHomeContext, DqoHomeContext dqoHomeContext) {
        this.userHomeContext = userHomeContext;
        this.dqoHomeContext = dqoHomeContext;
    }

    /**
     * User home context.
     * @return User home context.
     */
    public UserHomeContext getUserHomeContext() {
        return userHomeContext;
    }

    /**
     * Dqo application home context (with built-in rule and sensor definitions).
     * @return Dqo home context.
     */
    public DqoHomeContext getDqoHomeContext() {
        return dqoHomeContext;
    }
}
