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
package ai.dqo.cli.commands;

import ai.dqo.cli.commands.check.CheckCliCommand;
import ai.dqo.cli.commands.cloud.CloudCliCommand;
import ai.dqo.cli.commands.column.ColumnCliCommand;
import ai.dqo.cli.commands.connection.ConnectionCliCommand;
import ai.dqo.cli.commands.impl.DqoShellRunnerService;
import ai.dqo.cli.commands.run.RunCliCommand;
import ai.dqo.cli.commands.scheduler.SchedulerCliCommand;
import ai.dqo.cli.commands.sensor.SensorCliCommand;
import ai.dqo.cli.commands.settings.SettingsCliCommand;
import ai.dqo.cli.commands.table.TableCliCommand;
import ai.dqo.cli.commands.utility.ClearScreenCliCommand;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * DQO root CLI command.
 */
@Component
@CommandLine.Command(
        name = "",
        description = {
                "DQO Interactive Shell",
                "Hit @|magenta <TAB>|@ to see available commands.",
                "Hit @|magenta ALT-S|@ to toggle tailtips.",
                ""},
        footer = {"", "Press Ctrl-D to exit."},
        subcommands = {
            ClearScreenCliCommand.class,
            ConnectionCliCommand.class,
            TableCliCommand.class,
            CheckCliCommand.class,
            ColumnCliCommand.class,
            SettingsCliCommand.class,
            CloudCliCommand.class,
            SensorCliCommand.class,
            SchedulerCliCommand.class,
            RunCliCommand.class
        }
)
public class DqoRootCliCommand extends BaseCommand implements ICommand {
    private final BeanFactory beanFactory;

    @Autowired
    public DqoRootCliCommand(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        DqoShellRunnerService shellRunnerService = this.beanFactory.getBean(DqoShellRunnerService.class);
        return shellRunnerService.call();
    }
}
