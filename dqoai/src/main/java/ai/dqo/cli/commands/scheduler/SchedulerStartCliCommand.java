/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.cli.commands.scheduler;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.core.scheduler.JobSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "scheduler start" 2nd level CLI command - starts a scheduler that will run checks.
 */
@Component
@Scope("prototype")
@CommandLine.Command(name = "start", description = "Starts a background job scheduler. This operation should be called only from the shell mode. When the dqo is started as 'dqo scheduler start' from the operating system, it will stop immediately.")
public class SchedulerStartCliCommand extends BaseCommand implements ICommand {
    private JobSchedulerService jobSchedulerService;

    /**
     * Creates a cli command given the dependencies.
     * @param jobSchedulerService Job scheduler dependency.
     */
    @Autowired
    public SchedulerStartCliCommand(JobSchedulerService jobSchedulerService) {
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
        this.jobSchedulerService.start();
        return 0;
    }
}
