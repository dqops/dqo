/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.scheduler;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.ICommand;
import com.dqops.core.scheduler.JobSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "scheduler stop" 2nd level CLI command - stops a scheduler that was previously started.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "stop", header = "Stops a background job scheduler", description = "This operation should be called only from the shell mode after the scheduler was started.")
public class SchedulerStopCliCommand extends BaseCommand implements ICommand {
    private JobSchedulerService jobSchedulerService;

    public SchedulerStopCliCommand() {
    }

    /**
     * Creates a cli command given the dependencies.
     * @param jobSchedulerService Job scheduler dependency.
     */
    @Autowired
    public SchedulerStopCliCommand(JobSchedulerService jobSchedulerService) {
        this.jobSchedulerService = jobSchedulerService;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        this.jobSchedulerService.stop();
        return 0;
    }
}
