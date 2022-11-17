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
package ai.dqo.cli.commands.settings.impl;

import ai.dqo.cli.commands.CliOperationStatus;

/**
 * Settings management service.
 */
public interface SettingsService {

	/**
	 * Sets a new editor name and path.
	 * @param editorName editor name.
	 * @param editorPath editor path.
	 * @return Cli operation status.
	 */
	CliOperationStatus setSettingsEditor(String editorName, String editorPath);

	/**
	 * Removes a new editor name and path.
	 * @return Cli operation status.
	 */
	CliOperationStatus removeSettingsEditor();

	/**
	 * Shows a new editor name and path.
	 * @return Cli operation status.
	 */
	CliOperationStatus showSettingsEditor();

	/**
	 * Inits an empty settings file with new editor name and path.
	 * @param editorName editor name.
	 * @param editorPath editor path.
	 * @return Cli operation status.
	 */
	CliOperationStatus initSettings(String editorName, String editorPath);

	/**
	 * Removes settings file.
	 * @return Cli operation status.
	 */
	CliOperationStatus removeSettings();

	/**
	 * Sets a new api key.
	 * @param key api key.
	 * @return Cli operation status.
	 */
	CliOperationStatus setApiKey(String key);

	/**
	 * Removes a new api key.
	 * @return Cli operation status.
	 */
	CliOperationStatus removeApiKey();

	/**
	 * Shows a new api key.
	 * @return Cli operation status.
	 */
	CliOperationStatus showApiKey();
}
