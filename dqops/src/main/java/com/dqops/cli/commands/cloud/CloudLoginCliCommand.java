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
import com.dqops.cli.commands.cloud.impl.CloudLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * 2st level CLI command "cloud login" to log in to the DQOps Cloud.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "login", header = "Log in or register an account at the DQOps Cloud", description = "Allow user to provide login credentials if the user already has an account.")
public class CloudLoginCliCommand extends BaseCommand implements ICommand {
    private CloudLoginService cloudLoginService;

    public CloudLoginCliCommand() {
    }

    @Autowired
    public CloudLoginCliCommand(CloudLoginService cloudLoginService) {
        this.cloudLoginService = cloudLoginService;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        boolean success = this.cloudLoginService.logInToDqoCloud();
        return success ? 0 : -1;
    }
}
