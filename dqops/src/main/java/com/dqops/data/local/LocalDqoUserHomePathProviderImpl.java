/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
