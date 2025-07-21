/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.cli.commands.sso.impl;

import com.dqops.cli.commands.CliOperationStatus;

/**
 * Service that performs provisioning of a realm to support SSO authentication. The implementation provided in the
 * open-source version of DQOps does not support SSO authentication using on-premise identity providers.
 */
public interface SsoProvisioningCliService {
    /**
     * Performs provisioning of a realm using the configuration that was present in the configuration files.
     *
     * @return CLI operation status.
     */
    CliOperationStatus provisionRealm();
}
