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
import com.dqops.cli.commands.settings.catalog.SettingsCatalogAddCliCommand;
import com.dqops.cli.commands.settings.catalog.SettingsCatalogListCliCommand;
import com.dqops.cli.commands.settings.catalog.SettingsCatalogRemoveCliCommand;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli settings data catalog integration base command.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "catalog", description = "Lists, adds and removes URLs to the data catalog REST API endpoints that receive updates of data quality of tables.", subcommands = {
		SettingsCatalogListCliCommand.class,
		SettingsCatalogAddCliCommand.class,
		SettingsCatalogRemoveCliCommand.class,
})
public class SettingsCatalogCliCommand extends BaseCommand {
}
