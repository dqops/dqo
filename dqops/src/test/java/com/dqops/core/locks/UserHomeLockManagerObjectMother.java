/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.locks;

import com.dqops.core.configuration.DqoCoreConfigurationProperties;
import com.dqops.core.configuration.DqoCoreConfigurationPropertiesObjectMother;
import com.dqops.utils.BeanFactoryObjectMother;

/**
 * Object mother for {@link UserHomeLockManager}
 */
public final class UserHomeLockManagerObjectMother {
    /**
     * Creates a new instance of a user home lock manager.
     * @return User home lock manager.
     */
    public static UserHomeLockManager createNewLockManager() {
        DqoCoreConfigurationProperties coreConfigurationProperties = DqoCoreConfigurationPropertiesObjectMother.getCoreConfigurationProperties();
        ThreadLocksCounterImpl threadLocksCounter = new ThreadLocksCounterImpl(coreConfigurationProperties);
        return new UserHomeLockManagerImpl(coreConfigurationProperties, threadLocksCounter);
    }

    /**
     * Returns the real shared global lock manager.
     * @return Global lock manager that is protecting access to the user home.
     */
    public static UserHomeLockManager getDefaultGlobalLockManager() {
        return BeanFactoryObjectMother.getBeanFactory().getBean(UserHomeLockManager.class);
    }
}
