/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
