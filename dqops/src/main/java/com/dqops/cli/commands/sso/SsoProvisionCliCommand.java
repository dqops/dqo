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

package com.dqops.cli.commands.sso;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.sso.impl.SsoProvisioningCliService;
import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.cli.terminal.TerminalWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * CLI command that provisions a realm to support on-premise SSO.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "provision", header = "Provisions a realm in Keycloak", description = "Creates and configures a new realm in Keycloak, configuring all required settings. This command is supported only in paid versions of DQOps. Please contact DQOps sales for details.")
public class SsoProvisionCliCommand extends BaseCommand implements ICommand {
    private TerminalFactory terminalFactory;
    private SsoProvisioningCliService ssoProvisioningCliService;

    public SsoProvisionCliCommand() {
    }

    @Autowired
    public SsoProvisionCliCommand(TerminalFactory terminalFactory,
                                  SsoProvisioningCliService ssoProvisioningCliService) {
        this.terminalFactory = terminalFactory;
        this.ssoProvisioningCliService = ssoProvisioningCliService;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        TerminalWriter terminalWriter = this.terminalFactory.getWriter();

        CliOperationStatus cliOperationStatus = this.ssoProvisioningCliService.provisionRealm();
        terminalWriter.writeLine(cliOperationStatus.getMessage());
        return cliOperationStatus.isSuccess() ? 0 : -1;
    }
}
