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
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Check execution context factory. Opens the dqo home context and the user home context.
 */
@Component
public class ExecutionContextFactoryImpl implements ExecutionContextFactory {
    private final UserHomeContextFactory userHomeContextFactory;
    private final DqoHomeContextFactory dqoHomeContextFactory;

    /**
     * Dependency injection constructor.
     * @param userHomeContextFactory User home context factory.
     * @param dqoHomeContextFactory Dqo home context factory.
     */
    @Autowired
    public ExecutionContextFactoryImpl(UserHomeContextFactory userHomeContextFactory,
                                       DqoHomeContextFactory dqoHomeContextFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.dqoHomeContextFactory = dqoHomeContextFactory;
    }

    /**
     * Creates a new execution context by opening the user home context and the dqo system home context.
     * @param userDomainIdentity Calling user identity, with the name of the data domain whose user home is opened.
     * @return Execution context.
     */
    @Override
    public ExecutionContext create(UserDomainIdentity userDomainIdentity) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userDomainIdentity, true);
        return new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome());
    }

    /**
     * Creates a new execution context by opening the user home context and the dqo system home context.
     *
     * @param userDomainIdentity Calling user identity, with the name of the data domain whose user home is opened.
     * @param readOnly           Configures the read-only status of the user home.
     * @return Check execution context.
     */
    @Override
    public ExecutionContext create(UserDomainIdentity userDomainIdentity, boolean readOnly) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userDomainIdentity, readOnly);
        return new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome());
    }

    /**
     * Creates a new execution context by using a given user context.
     * @param userHomeContext User home context.
     * @return Execution context.
     */
    public ExecutionContext create(UserHomeContext userHomeContext) {
        return new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome());
    }
}
