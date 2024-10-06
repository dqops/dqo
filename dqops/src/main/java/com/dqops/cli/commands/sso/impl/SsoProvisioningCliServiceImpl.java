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

package com.dqops.cli.commands.sso.impl;

import com.dqops.cli.commands.CliOperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Service that performs provisioning of a realm to support SSO authentication. The implementation provided in the
 * open-source version of DQOps does not support SSO authentication using on-premise identity providers.
 */
@Component
public class SsoProvisioningCliServiceImpl implements SsoProvisioningCliService {
    @Autowired
    public SsoProvisioningCliServiceImpl() {
    }

    /**
     * Performs provisioning of a realm using the configuration that was present in the configuration files.
     * @return CLI operation status.
     */
    @Override
    public CliOperationStatus provisionRealm() {
        return new CliOperationStatus(false, "Single sign-on authentication is not supported in an open-source version of DQOps. Please contact DQOps sales for details.", null);
    }
}
