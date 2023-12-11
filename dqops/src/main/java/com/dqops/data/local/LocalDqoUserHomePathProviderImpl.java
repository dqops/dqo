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
package com.dqops.data.local;

import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.principal.UserDomainIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Objects;

/**
 * Service that returns an absolute path to the local user home, used to access data files.
 */
@Component
public class LocalDqoUserHomePathProviderImpl implements LocalDqoUserHomePathProvider {
    private DqoUserConfigurationProperties dqoUserConfigurationProperties;

    /**
     * Default injection constructor.
     * @param dqoUserConfigurationProperties  User home configuration properties.
     */
    @Autowired
    public LocalDqoUserHomePathProviderImpl(DqoUserConfigurationProperties dqoUserConfigurationProperties) {
        this.dqoUserConfigurationProperties = dqoUserConfigurationProperties;
    }

    /**
     * Returns the absolute path to the DQO_USER_HOME folder.
     * @param userIdentity User identity that identifies the target data domain.
     * @return Absolute path to the DQOps user home folder.
     */
    @Override
    public Path getLocalUserHomePath(UserDomainIdentity userIdentity) {
        Path absolutePathToLocalUserHomeRoot = Path.of(this.dqoUserConfigurationProperties.getHome()).toAbsolutePath();

        if (!Objects.equals(userIdentity.getDataDomainFolder(), this.dqoUserConfigurationProperties.getDefaultDataDomain())) {
            absolutePathToLocalUserHomeRoot = absolutePathToLocalUserHomeRoot
                    .resolve(BuiltInFolderNames.DATA_DOMAINS)
                    .resolve(userIdentity.getDataDomainFolder());
        }

        return absolutePathToLocalUserHomeRoot;
    }
}
