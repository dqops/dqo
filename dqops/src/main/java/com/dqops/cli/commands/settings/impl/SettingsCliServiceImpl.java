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
package com.dqops.cli.commands.settings.impl;

import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.core.catalogsync.DataCatalogHealthSendService;
import com.dqops.core.dqocloud.accesskey.DqoCloudAccessTokenCache;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.dqops.core.dqocloud.apikey.DqoCloudLimit;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.settings.DataCatalogUrlsSetSpec;
import com.dqops.metadata.settings.LocalSettingsSpec;
import com.dqops.metadata.settings.SettingsWrapper;
import com.dqops.metadata.settings.SmtpServerConfigurationSpec;
import com.dqops.metadata.settings.instancename.InstanceNameProvider;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Map;

/**
 * Settings management service.
 */
@Component
public class SettingsCliServiceImpl implements SettingsCliService {
	private final UserHomeContextFactory userHomeContextFactory;
	private final DefaultTimeZoneProvider defaultTimeZoneProvider;
	private final DqoCloudAccessTokenCache dqoCloudAccessTokenCache;
	private final DqoCloudApiKeyProvider dqoCloudApiKeyProvider;
	private final DqoUserPrincipalProvider principalProvider;
	private final DataCatalogHealthSendService dataCatalogHealthSendService;
	private final InstanceNameProvider instanceNameProvider;

	@Autowired
	public SettingsCliServiceImpl(UserHomeContextFactory userHomeContextFactory,
								  DefaultTimeZoneProvider defaultTimeZoneProvider,
								  DqoCloudAccessTokenCache dqoCloudAccessTokenCache,
								  DqoCloudApiKeyProvider dqoCloudApiKeyProvider,
								  DqoUserPrincipalProvider principalProvider,
								  DataCatalogHealthSendService dataCatalogHealthSendService,
								  InstanceNameProvider instanceNameProvider) {
		this.userHomeContextFactory = userHomeContextFactory;
		this.defaultTimeZoneProvider = defaultTimeZoneProvider;
		this.dqoCloudAccessTokenCache = dqoCloudAccessTokenCache;
		this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
		this.principalProvider = principalProvider;
		this.dataCatalogHealthSendService = dataCatalogHealthSendService;
		this.instanceNameProvider = instanceNameProvider;
	}

	/**
	 * Creates and returns a new settings wrapper object in a given DQOps user home.
	 * @param userHome Target user home to update.
	 * @return Created local settings wrapper.
	 */
	private SettingsWrapper createEmptySettingFile(UserHome userHome) {
		LocalSettingsSpec localSettingsSpec = new LocalSettingsSpec();
		SettingsWrapper settings = userHome.getSettings();
		settings.setSpec(localSettingsSpec);
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

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
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

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
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

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
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
		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);

		LocalSettingsSpec localSettingsSpec = new LocalSettingsSpec();
		localSettingsSpec.setEditorName(editorName);
		localSettingsSpec.setEditorPath(editorPath);
		SettingsWrapper settings = userHomeContext.getUserHome().getSettings();
		if (settings != null && settings.getSpec() != null) {
			cliOperationStatus.setFailedMessage("Settings file has been already initialized");
			return cliOperationStatus;
		}
		settings.setSpec(localSettingsSpec);
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
		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);

		SettingsWrapper settings = userHomeContext.getUserHome().getSettings();
		if (settings == null || settings.getSpec() == null) {
			cliOperationStatus.setFailedMessage("Settings file does not exist");
			return cliOperationStatus;
		}
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

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		settings.setApiKey(key);
		userHomeContext.flush();
		this.dqoCloudApiKeyProvider.invalidate();
		this.dqoCloudAccessTokenCache.invalidate();
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

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
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
		this.dqoCloudApiKeyProvider.invalidate();
		this.dqoCloudAccessTokenCache.invalidate();
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

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		String apiKeyString = settings.getApiKey();
		if (Strings.isNullOrEmpty(apiKeyString)) {
			cliOperationStatus.setFailedMessage(String.format("DQOps Cloud API Key is not set"));
			return cliOperationStatus;
		}

		DqoCloudApiKey cloudApiKey = this.dqoCloudApiKeyProvider.decodeApiKey(apiKeyString);
		DqoCloudApiKeyPayload apiKeyPayload = cloudApiKey.getApiKeyPayload();

		StringBuilder textBuilder = new StringBuilder();
		textBuilder.append(String.format("DQOps Cloud API key is: %s\n", apiKeyString));
		textBuilder.append(String.format("Tenant id: %s/%d\n", apiKeyPayload.getTenantId(), apiKeyPayload.getTenantGroup()));
		textBuilder.append(String.format("License type: %s\n", apiKeyPayload.getLicenseType()));
		textBuilder.append(String.format("Access to the data quality data lake and dashboards: %s\n",
				apiKeyPayload.getDataQualityDataWarehouse() == null || apiKeyPayload.getDataQualityDataWarehouse() == true));

		for (Map.Entry<DqoCloudLimit, Integer> limitEntry : apiKeyPayload.getLimits().entrySet()) {
			String limitEntryName = "";
			switch (limitEntry.getKey()) {
				case CONNECTIONS_LIMIT:
					limitEntryName = "Maximum number of synchronized connections";
					break;
				case USERS_LIMIT:
					limitEntryName = "Maximum number of DQOps Cloud users";
					break;
				case TABLES_LIMIT:
					limitEntryName = "Maximum number of synchronized tables";
					break;
				case CONNECTION_TABLES_LIMIT:
					limitEntryName = "Maximum number of synchronized tables inside a single connection";
					break;
				case MONTHS_LIMIT:
					limitEntryName = "Maximum number of synchronized months with data quality results, including the current month";
					break;
				case JOBS_LIMIT:
					limitEntryName = "Maximum number of parallel data quality jobs";
					break;
				case DATA_DOMAINS_LIMIT:
					limitEntryName = "Maximum number of data domains";
					break;
				default:
					limitEntryName = limitEntry.getKey().toString();
					break;
			}

			textBuilder.append(String.format("%s: %d\n", limitEntryName, limitEntry.getValue()));
		}

		cliOperationStatus.setSuccessMessage(textBuilder.toString());
		return cliOperationStatus;
	}

	/**
	 * Sets a new DQOps instance name
	 *
	 * @param instanceName New instance name
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus setInstanceName(String instanceName) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		settings.setInstanceName(instanceName);
		userHomeContext.flush();
		this.instanceNameProvider.invalidate();
		cliOperationStatus.setSuccessMessage("Successfully set the DQOps instance name");

		return cliOperationStatus;
	}

	/**
	 * Removes an instance name.
	 *
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus removeInstanceName() {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		String key = settings.getApiKey();
		if (Strings.isNullOrEmpty(key)) {
			cliOperationStatus.setFailedMessage(String.format("DQOps instance name is not set"));
			return cliOperationStatus;
		}

		settings.setInstanceName(null);
		userHomeContext.flush();
		this.instanceNameProvider.invalidate();
		cliOperationStatus.setSuccessMessage("Successfully removed DQOps instance name");

		return cliOperationStatus;
	}

	/**
	 * Shows the instance name.
	 *
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus showInstanceName() {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		String instanceNameString = settings.getInstanceName();
		if (Strings.isNullOrEmpty(instanceNameString)) {
			cliOperationStatus.setFailedMessage(String.format("DQOps instance name is not set"));
			return cliOperationStatus;
		}

		StringBuilder textBuilder = new StringBuilder();
		textBuilder.append(String.format("DQOps instance name is: %s\n", instanceNameString));

		cliOperationStatus.setSuccessMessage(textBuilder.toString());
		return cliOperationStatus;
	}

	/**
	 * Sets a new SMTP server configuration.
	 * @param host SMTP server host.
	 * @param port SMTP server port.
	 * @param useSsl SMTP server useSSL.
	 * @param username SMTP server username.
	 * @param password SMTP server password.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus setSmtpServerConfiguration(String host,
														 String port,
														 Boolean useSsl,
														 String username,
														 String password){
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		SmtpServerConfigurationSpec smtpServerConfigurationSpec = new SmtpServerConfigurationSpec() {{
				setHost(host);
				setPort(port);
				setUseSsl(useSsl);
				setUsername(username);
				setPassword(password);
		}};
		settings.setSmtpServerConfiguration(smtpServerConfigurationSpec);

		userHomeContext.flush();
		this.dqoCloudApiKeyProvider.invalidate();
		this.dqoCloudAccessTokenCache.invalidate();
		cliOperationStatus.setSuccessMessage("Successfully set SMTP server configuration");

		return cliOperationStatus;
	}

	/**
	 * Removes a new SMTP server configuration.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus removeSmtpServerConfiguration(){
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		SmtpServerConfigurationSpec smtpServerConfiguration = settings.getSmtpServerConfiguration();
		if (smtpServerConfiguration == null) {
			cliOperationStatus.setFailedMessage(String.format("SMTP server configuration is not set"));
			return cliOperationStatus;
		}

		settings.setSmtpServerConfiguration(null);
		userHomeContext.flush();
		this.dqoCloudApiKeyProvider.invalidate();
		this.dqoCloudAccessTokenCache.invalidate();
		cliOperationStatus.setSuccessMessage("Successfully removed SMTP server configuration");

		return cliOperationStatus;
	}

	/**
	 * Shows a new SMTP server configuration.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus showSmtpServerConfiguration(){
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		SmtpServerConfigurationSpec smtpServerConfiguration = settings.getSmtpServerConfiguration();
		if (smtpServerConfiguration == null) {
			cliOperationStatus.setFailedMessage(String.format("SMTP server configuration is not set."));
			return cliOperationStatus;
		}

		StringBuilder textBuilder = new StringBuilder();
		textBuilder.append(String.format("SMTP server host: %s\n", smtpServerConfiguration.getHost()));
		textBuilder.append(String.format("SMTP server port: %s/%d\n", smtpServerConfiguration.getPort()));
		textBuilder.append(String.format("SMTP server use SSL: %s\n", smtpServerConfiguration.getUseSsl()));
		textBuilder.append(String.format("SMTP server user name: %s\n", smtpServerConfiguration.getUsername()));
		textBuilder.append(String.format("SMTP server password: %s\n", smtpServerConfiguration.getPassword()));

		cliOperationStatus.setSuccessMessage(textBuilder.toString());
		return cliOperationStatus;
	}


	/**
	 * Sets a new IANA time zone.
	 * @param timeZone Time zone name.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus setTimeZone(String timeZone) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		if (timeZone != null) {
			try {
				ZoneId validatedTimeZoneId = ZoneId.of(timeZone);
			}
			catch (Exception ex) {
				cliOperationStatus.setFailedMessage("Invalid time zone, use IANA time zone names");
				return cliOperationStatus;
			}
		}

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		settings.setTimeZone(timeZone);
		userHomeContext.flush();
		this.defaultTimeZoneProvider.invalidate();

		cliOperationStatus.setSuccessMessage("Successfully set the time zone");

		return cliOperationStatus;
	}

	/**
	 * Removes a time zone.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus removeTimeZone() {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		String timeZone = settings.getTimeZone();
		if (Strings.isNullOrEmpty(timeZone)) {
			cliOperationStatus.setFailedMessage(String.format("Time zone is not set"));
			return cliOperationStatus;
		}

		settings.setApiKey(null);
		userHomeContext.flush();
		this.defaultTimeZoneProvider.invalidate();

		cliOperationStatus.setSuccessMessage("Successfully removed the time zone");

		return cliOperationStatus;
	}

	/**
	 * Shows a time zone.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus showTimeZone() {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		String timeZone = settings.getTimeZone();
		if (Strings.isNullOrEmpty(timeZone)) {
			cliOperationStatus.setFailedMessage(String.format("Time zone is not set"));
			return cliOperationStatus;
		}
		cliOperationStatus.setSuccessMessage(String.format("The default time zone is: %s", timeZone));
		return cliOperationStatus;
	}

	/**
	 * Shows a list of data catalog urls.
	 *
	 * @return CLI operation status.
	 */
	@Override
	public CliOperationStatus showDataCatalogUrls() {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();
		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		StringBuilder stringBuilder = new StringBuilder();
		if (settings.getDataCatalogUrls() != null) {
			for (String dataCatalogUrl : settings.getDataCatalogUrls()) {
				stringBuilder.append(dataCatalogUrl);
				stringBuilder.append("\n");
			}
		}

		cliOperationStatus.setSuccessMessage(stringBuilder.toString());
		return cliOperationStatus;
	}

	/**
	 * Adds an url to a data catalog wrapper that sends the health data to the data catalog.
	 *
	 * @param dataCatalogUrl Url to the data catalog wrapper.
	 * @return CLI operation status.
	 */
	@Override
	public CliOperationStatus addDataCatalogUrl(String dataCatalogUrl) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		if (Strings.isNullOrEmpty(dataCatalogUrl)) {
			cliOperationStatus.setFailedMessage("Wrong data catalog URL");
			return cliOperationStatus;
		}

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		if (settings.getDataCatalogUrls() == null) {
			settings.setDataCatalogUrls(new DataCatalogUrlsSetSpec());
		}

		settings.getDataCatalogUrls().add(dataCatalogUrl);
		userHomeContext.flush();

		this.dataCatalogHealthSendService.invalidateUrlList();
		cliOperationStatus.setSuccessMessage("Url to a data catalog wrapper added");

		return cliOperationStatus;
	}

	/**
	 * Removes a url to a data catalog wrapper that stores data quality health statuses.
	 *
	 * @param dataCatalogUrl Data catalog url to remove.
	 * @return CLI operation status.
	 */
	@Override
	public CliOperationStatus removeDataCatalogUrl(String dataCatalogUrl) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		if (Strings.isNullOrEmpty(dataCatalogUrl)) {
			cliOperationStatus.setFailedMessage("Wrong data catalog URL");
			return cliOperationStatus;
		}

		DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();
		LocalSettingsSpec settings;
		if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null) {
			settings = createEmptySettingFile(userHome).getSpec();
		}
		else {
			settings = userHome.getSettings().getSpec();
		}

		if (settings.getDataCatalogUrls() == null) {
			settings.setDataCatalogUrls(new DataCatalogUrlsSetSpec());
		}

		settings.getDataCatalogUrls().remove(dataCatalogUrl);
		userHomeContext.flush();

		this.dataCatalogHealthSendService.invalidateUrlList();
		cliOperationStatus.setSuccessMessage("Url to a data catalog wrapper removed");

		return cliOperationStatus;
	}
}
