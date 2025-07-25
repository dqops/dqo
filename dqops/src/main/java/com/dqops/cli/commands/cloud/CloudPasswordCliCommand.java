/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.cloud;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.core.dqocloud.users.UserManagementService;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.principal.DqoUserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.Objects;

/**
 * 2st level CLI command "cloud password" for changing the DQOps Cloud password used to log in to the DQOps Cloud account.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "password", header = "Changes the user's password in DQOps Cloud",
        description = "Allows the user to change the password that is used to log in to DQOps Cloud account using the email and password.")
@Slf4j
public class CloudPasswordCliCommand extends BaseCommand implements ICommand {
    private UserManagementService userManagementService;
    private DqoUserPrincipalProvider principalProvider;
    private TerminalFactory terminalFactory;

    public CloudPasswordCliCommand() {
    }

    @Autowired
    public CloudPasswordCliCommand(UserManagementService userManagementService,
                                   DqoUserPrincipalProvider principalProvider,
                                   TerminalFactory terminalFactory) {
        this.userManagementService = userManagementService;
        this.principalProvider = principalProvider;
        this.terminalFactory = terminalFactory;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
        TerminalWriter terminalWriter = this.terminalFactory.getWriter();

        if (userPrincipal.getApiKeyPayload() == null) {
            terminalWriter.writeLine("This instance is not authenticated to DQOps Cloud. Please run 'cloud login' command first.");
            return -1;
        }

        TerminalReader terminalReader = terminalFactory.getReader();
        String password = terminalReader.promptPassword("Please enter the new password:", false);
        if (Strings.isNullOrEmpty(password)) {
            return 0;
        }

        String password2 = terminalReader.promptPassword("Please enter the new password again:", false);
        if (Strings.isNullOrEmpty(password2)) {
            return 0;
        }

        if (!Objects.equals(password, password2)) {
            terminalWriter.writeLine("Passwords do not match");
        }

        try {
            this.userManagementService.changePassword(userPrincipal, userPrincipal.getDataDomainIdentity().getUserName(), password);
        }
        catch (Exception ex) {
            terminalWriter.writeLine("Cannot change the password, error: " + ex.getMessage());
            log.error("Cannot change the password, error: " + ex.getMessage(), ex);
            return -1;
        }

        return 0;
    }
}
