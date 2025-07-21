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
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;

/**
 * Check execution context object mother.
 */
public class CheckExecutionContextObjectMother {
    /**
     * Create a check execution context with an empty, in-memory home context and a real dqo context.
     * @return Check execution context.
     */
    public static ExecutionContext createWithInMemoryUserContext() {
        UserHomeContext userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        ExecutionContext executionContext = new ExecutionContext(userHomeContext, dqoHomeContext);
        return executionContext;
    }

    /**
     * Create a check execution context with a testable user home context and a real dqo context.
     * @param recreateTemporaryHomeFolder Recreate (clean) the testable user home context.
     * @return Check execution context.
     */
    public static ExecutionContext createTestableUserContext(boolean recreateTemporaryHomeFolder) {
        UserHomeContext userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(recreateTemporaryHomeFolder);
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        ExecutionContext executionContext = new ExecutionContext(userHomeContext, dqoHomeContext);
        return executionContext;
    }
}
