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
import ai.dqo.metadata.basespecs.InstanceStatus;
import ai.dqo.metadata.sources.SettingsSpec;
import ai.dqo.metadata.sources.SettingsWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Settings management service.
 */
@Component
public class SettingsServiceImpl implements SettingsService {
	private final UserHomeContextFactory userHomeContextFactory;

	@Autowired
	public SettingsServiceImpl(UserHomeContextFactory userHomeContextFactory) {
		this.userHomeContextFactory = userHomeContextFactory;
	}

	private SettingsWrapper createEmptySettingFile(UserHome userHome) {

		SettingsSpec settingsSpec = new SettingsSpec();
		SettingsWrapper settings = userHome.getSettings();
		settings.setSpec(settingsSpec);
		settings.setStatus(InstanceStatus.ADDED);

		return settings;
	}

	/**
	 * Sets a new editor name and path.
	 * @param editorName editor name.
	 * @param editorPath editor path.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus setSettingsEditor(String editorName, String editorPath) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
		UserHome userHome = userHomeContext.getUserHome();
		SettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		System.out.println(settings);

		settings.setEditorName(editorName);
		settings.setEditorPath(editorPath);
		userHomeContext.flush();
		cliOperationStatus.setSuccessMessage("Successfully set editor name");

		return cliOperationStatus;
	}

	/**
	 * Removes a new editor name and path.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus removeSettingsEditor() {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
		UserHome userHome = userHomeContext.getUserHome();
		SettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		if (Strings.isNullOrEmpty(settings.getEditorName())) {
			cliOperationStatus.setFailedMessage(String.format("Editor name is not set"));
			return cliOperationStatus;
		}

		settings.setEditorName(null);
		settings.setEditorPath(null);
		userHomeContext.flush();
		cliOperationStatus.setSuccessMessage("Successfully removed editor");

		return cliOperationStatus;
	}

	/**
	 * Shows a new editor name and path.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus showSettingsEditor() {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
		UserHome userHome = userHomeContext.getUserHome();
		SettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		String editorName = settings.getEditorName();
		String editorPath = settings.getEditorPath();
		if (Strings.isNullOrEmpty(editorName)) {
			cliOperationStatus.setFailedMessage(String.format("Editor name is not set"));
			return cliOperationStatus;
		}
		cliOperationStatus.setSuccessMessage(String.format("Set editor is: %s\nEditor path is: %s", editorName, editorPath));

		return cliOperationStatus;
	}

	/**
	 * Inits an empty settings file with new editor name and path.
	 * @param editorName editor name.
	 * @param editorPath editor path.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus initSettings(String editorName, String editorPath) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();
		UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome();

		SettingsSpec settingsSpec = new SettingsSpec();
		settingsSpec.setEditorName(editorName);
		settingsSpec.setEditorPath(editorPath);
		SettingsWrapper settings = userHomeContext.getUserHome().getSettings();
		if (settings != null && settings.getSpec() != null) {
			cliOperationStatus.setFailedMessage("Settings file has been already initialized");
			return cliOperationStatus;
		}
		settings.setSpec(settingsSpec);
		settings.setStatus(InstanceStatus.ADDED);
		userHomeContext.flush();

		cliOperationStatus.setSuccessMessage("Successfully initialized settings file");
		return cliOperationStatus;
	}

	/**
	 * Removes settings file.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus removeSettings() {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();
		UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome();

		SettingsWrapper settings = userHomeContext.getUserHome().getSettings();
		if (settings == null || settings.getSpec() == null) {
			cliOperationStatus.setFailedMessage("Settings file does not exist");
			return cliOperationStatus;
		}
		settings.setStatus(InstanceStatus.TO_BE_DELETED);
		settings.markForDeletion();
		userHomeContext.flush();

		cliOperationStatus.setSuccessMessage("Successfully removed settings file");
		return cliOperationStatus;
	}

	/**
	 * Sets a new api key.
	 * @param key api key.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus setApiKey(String key) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
		UserHome userHome = userHomeContext.getUserHome();
		SettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		settings.setApiKey(key);
		userHomeContext.flush();
		cliOperationStatus.setSuccessMessage("Successfully set api key");

		return cliOperationStatus;
	}

	/**
	 * Removes a new api key.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus removeApiKey() {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
		UserHome userHome = userHomeContext.getUserHome();
		SettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		String key = settings.getApiKey();
		if (Strings.isNullOrEmpty(key)) {
			cliOperationStatus.setFailedMessage(String.format("Api key is not set"));
			return cliOperationStatus;
		}

		settings.setApiKey(null);
		userHomeContext.flush();
		cliOperationStatus.setSuccessMessage("Successfully removed api key");

		return cliOperationStatus;
	}

	/**
	 * Shows a new api key.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus showApiKey() {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
		UserHome userHome = userHomeContext.getUserHome();
		SettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		String key = settings.getApiKey();
		if (Strings.isNullOrEmpty(key)) {
			cliOperationStatus.setFailedMessage(String.format("Api key is not set"));
			return cliOperationStatus;
		}
		cliOperationStatus.setSuccessMessage(String.format("Set api key is: %s", key));
		return cliOperationStatus;
	}

}
