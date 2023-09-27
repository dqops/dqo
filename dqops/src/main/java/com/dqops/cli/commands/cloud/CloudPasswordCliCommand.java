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
package com.dqops.cli.commands.cloud;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.cloud.impl.CloudLoginService;
import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.core.dqocloud.users.UserManagementService;
import com.dqops.core.principal.DqoCloudApiKeyPrincipalProvider;
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
 * 2st level CLI command "cloud password" for changing the DQO Cloud password used to log in to the DQO Cloud account.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "password", header = "Changes the user's password in DQO Cloud", description = "Allows the user to change the password that is used to log in to DQO Cloud account using the email and password.")
@Slf4j
public class CloudPasswordCliCommand extends BaseCommand implements ICommand {
    private UserManagementService userManagementService;
    private DqoCloudApiKeyPrincipalProvider principalProvider;
    private TerminalFactory terminalFactory;

    public CloudPasswordCliCommand() {
    }

    @Autowired
    public CloudPasswordCliCommand(UserManagementService userManagementService,
                                   DqoCloudApiKeyPrincipalProvider principalProvider,
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
        DqoUserPrincipal userPrincipal = this.principalProvider.createUserPrincipal();
        TerminalWriter terminalWriter = this.terminalFactory.getWriter();

        if (userPrincipal.getApiKeyPayload() == null) {
            terminalWriter.writeLine("This instance is not authenticated to DQO Cloud. Please run 'cloud login' command first.");
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
            this.userManagementService.changePassword(userPrincipal, userPrincipal.getName(), password);
        }
        catch (Exception ex) {
            terminalWriter.writeLine("Cannot change the password, error: " + ex.getMessage());
            log.error("Cannot change the password, error: " + ex.getMessage(), ex);
            return -1;
        }

        return 0;
    }
}
