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
package ai.dqo.cli.commands.settings;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.settings.apikey.SettingsApiKeyRemoveCliCommand;
import ai.dqo.cli.commands.settings.apikey.SettingsApiKeySetCliCommand;
import ai.dqo.cli.commands.settings.apikey.SettingsApiKeyShowCliCommand;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli settings Api key base command.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "apikey", description = "Set or show api key", subcommands = {
		SettingsApiKeySetCliCommand.class,
		SettingsApiKeyRemoveCliCommand.class,
		SettingsApiKeyShowCliCommand.class,
})
public class SettingsApiKeyCliCommand extends BaseCommand {
}
