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
package ai.dqo.cli.commands.start;

import ai.dqo.cli.commands.BaseCommand;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "start" 1st level CLI command - a grouping command for starting a server mode (scheduler).
 */
@Component
@Scope("prototype")
@CommandLine.Command(name = "start", description = "Starts DQO in a server mode, select the mode to activate",
        subcommands = StartSchedulerCliCommand.class)
public class StartCliCommand extends BaseCommand {
}
