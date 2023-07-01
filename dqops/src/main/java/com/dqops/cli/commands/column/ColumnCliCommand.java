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
package com.dqops.cli.commands.column;

import com.dqops.cli.commands.BaseCommand;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "column" 1st level cli command.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "column", description = "Modify and list columns",subcommands = {
		ColumnAddCliCommand.class,
		ColumnRemoveCliCommand.class,
		ColumnUpdateCliCommand.class,
		ColumnListCliCommand.class,
		ColumnEnableCliCommand.class,
		ColumnDisableCliCommand.class,
		ColumnRenameCliCommand.class,
})
public class ColumnCliCommand extends BaseCommand {
}
