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

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;

/**
 * Execution context factory. Opens the dqo home context and the user home context.
 */
public interface ExecutionContextFactory {
    /**
     * Creates a new execution context by opening the user home context and the dqo system home context. Creates the user home in read-only mode.
     * @param userDomainIdentity Calling user identity, with the name of the data domain whose user home is opened.
     * @return Check execution context.
     */
    ExecutionContext create(UserDomainIdentity userDomainIdentity);

    /**
     * Creates a new execution context by opening the user home context and the dqo system home context.
     * @param userDomainIdentity Calling user identity, with the name of the data domain whose user home is opened.
     * @param readOnly Configures the read-only status of the user home.
     * @return Check execution context.
     */
    ExecutionContext create(UserDomainIdentity userDomainIdentity, boolean readOnly);

    /**
     * Creates a new execution context by using a given user context.
     * @param userHomeContext User home context.
     * @return A new execution context.
     */
    ExecutionContext create(UserHomeContext userHomeContext);
}
