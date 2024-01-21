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
