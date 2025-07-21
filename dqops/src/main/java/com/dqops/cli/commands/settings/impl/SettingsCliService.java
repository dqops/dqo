/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.settings.impl;

import com.dqops.cli.commands.CliOperationStatus;

/**
 * Settings management service for CLI.
 */
public interface SettingsCliService {

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

	/**
	 * Sets a new SMTP server configuration.
	 * @param host SMTP server host.
	 * @param port SMTP server port.
	 * @param useSSL SMTP server useSSL.
	 * @param username SMTP server username.
	 * @param password SMTP server password.
	 * @return Cli operation status.
	 */
	CliOperationStatus setSmtpServerConfiguration(String host,
												  String port,
												  Boolean useSSL,
												  String username,
												  String password);

	/**
	 * Removes a new SMTP server configuration.
	 * @return Cli operation status.
	 */
	CliOperationStatus removeSmtpServerConfiguration();

	/**
	 * Shows a new SMTP server configuration.
	 * @return Cli operation status.
	 */
	CliOperationStatus showSmtpServerConfiguration();

	/**
	 * Sets a new IANA time zone name.
	 * @param timeZone Time zone name.
	 * @return Cli operation status.
	 */
	CliOperationStatus setTimeZone(String timeZone);

	/**
	 * Removes a time zone name.
	 * @return Cli operation status.
	 */
	CliOperationStatus removeTimeZone();

	/**
	 * Shows a time zone.
	 * @return Cli operation status.
	 */
	CliOperationStatus showTimeZone();

	/**
	 * Shows a list of data catalog urls.
	 * @return CLI operation status.
	 */
	CliOperationStatus showDataCatalogUrls();

	/**
	 * Adds an url to a data catalog wrapper that sends the health data to the data catalog.
	 * @param dataCatalogUrl Url to the data catalog wrapper.
	 * @return CLI operation status.
	 */
	CliOperationStatus addDataCatalogUrl(String dataCatalogUrl);

	/**
	 * Removes a url to a data catalog wrapper that stores data quality health statuses.
	 * @param dataCatalogUrl Data catalog url to remove.
	 * @return CLI operation status.
	 */
	CliOperationStatus removeDataCatalogUrl(String dataCatalogUrl);

	/**
	 * Sets a new DQOps instance name
	 * @param instanceName New instance name
	 * @return Cli operation status.
	 */
	CliOperationStatus setInstanceName(String instanceName);

	/**
	 * Removes an instance name.
	 * @return Cli operation status.
	 */
	CliOperationStatus removeInstanceName();

	/**
	 * Shows the instance name.
	 * @return Cli operation status.
	 */
	CliOperationStatus showInstanceName();
}
