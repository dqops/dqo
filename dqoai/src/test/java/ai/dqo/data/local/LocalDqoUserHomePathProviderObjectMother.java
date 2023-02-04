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
package ai.dqo.data.local;

import ai.dqo.core.configuration.DqoUserConfigurationProperties;

import java.nio.file.Path;

/**
 * Object mother for {@link LocalDqoUserHomePathProvider}.
 */
public class LocalDqoUserHomePathProviderObjectMother {
    /**
     * Creates a local user home physical path provider that determines the home location from the configuration properties.
     * @param dqoUserConfigurationProperties DQO user configuration properties.
     * @return DQO Configuration properties.
     */
    public static LocalDqoUserHomePathProvider createLocalUserHomeProviderStub(DqoUserConfigurationProperties dqoUserConfigurationProperties) {
        Path absolutePathToLocalUserHome = Path.of(dqoUserConfigurationProperties.getHome()).toAbsolutePath();
        return new LocalDqoUserHomePathProviderStub(absolutePathToLocalUserHome);
    }
}
