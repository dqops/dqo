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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "scheduler" 1st level CLI command - a grouping command for controlling the job scheduler.
 */
@Component
@Scope("prototype")
@CommandLine.Command(name = "scheduler", description = "Controls the repeating task scheduler by starting, stopping or running a foreground job scheduler.",
        subcommands = {
            SchedulerStartCliCommand.class,
            SchedulerStopCliCommand.class
        })
public class SchedulerCliCommand extends BaseCommand {
}
