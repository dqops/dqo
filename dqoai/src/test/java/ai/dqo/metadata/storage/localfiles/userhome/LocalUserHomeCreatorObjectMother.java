/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.metadata.storage.localfiles.userhome;

import ai.dqo.utils.BeanFactoryObjectMother;

/**
 * Object mother for {@link LocalUserHomeCreator}
 */
public class LocalUserHomeCreatorObjectMother {
    private static boolean isInitialized;

    /**
     * Returns the default (singleton) implementation of the local dqo user home creator.
     * @return Default dqo user home creator.
     */
    public static LocalUserHomeCreator getDefault() {
        return BeanFactoryObjectMother.getBeanFactory().getBean(LocalUserHomeCreator.class);
    }

    /**
     * Initialized a DQO User home at a given location.
     * @param pathToUserHome Path to a dqo user home to initialize.
     */
    public static void initializeDqoUserHomeAt(String pathToUserHome) {
        LocalUserHomeCreator localUserHomeCreator = getDefault();
        localUserHomeCreator.initializeDqoUserHome(pathToUserHome);
    }

    /**
     * Initializes silently the default dqo user home.
     */
    public static void initializeDefaultDqoUserHomeSilentlyOnce() {
        if (isInitialized) {
            return;
        }

        getDefault().ensureDefaultUserHomeIsInitialized(true);

        isInitialized = true;
    }
}
