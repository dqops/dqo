/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userDomainIdentity);
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
