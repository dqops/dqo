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
package com.dqops.cli.commands.settings;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.settings.instancename.SettingsInstanceNameRemoveCliCommand;
import com.dqops.cli.commands.settings.instancename.SettingsInstanceNameSetCliCommand;
import com.dqops.cli.commands.settings.instancename.SettingsInstanceNameShowCliCommand;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli settings instance name base command.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "instancename", description = "Set or show the DQOps instance name", subcommands = {
		SettingsInstanceNameSetCliCommand.class,
		SettingsInstanceNameRemoveCliCommand.class,
		SettingsInstanceNameShowCliCommand.class,
})
public class SettingsInstanceNameCliCommand extends BaseCommand {
}
