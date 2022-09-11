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
package ai.dqo.cli.commands.run;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.scheduler.SchedulerStartCliCommand;
import ai.dqo.cli.commands.scheduler.SchedulerStopCliCommand;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.core.scheduler.JobSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "run" 1st level CLI command - starts DQO in a server mode.
 */
@Component
@Scope("prototype")
@CommandLine.Command(name = "run", description = "Starts DQO in a server mode, continuously running a job scheduler that runs the data quality checks.")
public class RunCliCommand extends BaseCommand implements ICommand {
    private JobSchedulerService jobSchedulerService;
    private TerminalReader terminalReader;

    /**
     * Creates a cli command given the dependencies.
     *
     * @param jobSchedulerService Job scheduler dependency.
     * @param terminalReader      Terminal reader - used to wait for an exit signal.
     */
    @Autowired
    public RunCliCommand(JobSchedulerService jobSchedulerService, TerminalReader terminalReader) {
        this.jobSchedulerService = jobSchedulerService;
        this.terminalReader = terminalReader;
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
        this.jobSchedulerService.triggerMetadataSynchronization();
        this.terminalReader.waitForExit("DQO was started in a server mode.");
        this.jobSchedulerService.shutdown();
        return 0;
    }
}
