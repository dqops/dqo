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
package com.dqops.cli.commands.collect;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.check.CheckActivateCliCommand;
import com.dqops.cli.commands.check.CheckDeactivateCliCommand;
import com.dqops.cli.commands.check.CheckRunCliCommand;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "collect" 1st level CLI command - a grouping command for performing actions that collect some information, for example - error samples, etc.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "collect", description = "Commands related to collecting statistics and samples", subcommands = {
        CollectErrorSamplesCliCommand.class,
})
public class CollectCliCommand extends BaseCommand {
}
