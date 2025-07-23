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

import java.nio.file.Path;

/**
 * Object mother for {@link LocalDqoUserHomePathProvider}.
 */
public class LocalDqoUserHomePathProviderObjectMother {
    /**
     * Creates a local user home physical path provider that determines the home location from the configuration properties.
     * @param dqoUserConfigurationProperties DQOps user configuration properties.
     * @return DQOps Configuration properties.
     */
    public static LocalDqoUserHomePathProvider createLocalUserHomeProviderStub(DqoUserConfigurationProperties dqoUserConfigurationProperties) {
        Path absolutePathToLocalUserHome = Path.of(dqoUserConfigurationProperties.getHome()).toAbsolutePath();
        return new LocalDqoUserHomePathProviderStub(absolutePathToLocalUserHome);
    }
}
