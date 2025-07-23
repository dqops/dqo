/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.settings;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.settings.domains.LocalDataDomainSpecMap;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;
import java.util.Set;

/**
 * Local settings specification.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class LocalSettingsSpec extends AbstractSpec implements InvalidYamlStatusHolder {
	private static final ChildHierarchyNodeFieldMapImpl<LocalSettingsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
		{
			put("smtp_server_configuration", o -> o.smtpServerConfiguration);
			put("data_domains", o -> o.dataDomains);
			put("data_catalog_urls", o -> o.dataCatalogUrls);
		}
	};

	@JsonPropertyDescription("Editor name spec (VSC, Eclipse, Intellij)")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String editorName;

	@JsonPropertyDescription("Editor path on user's computer")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String editorPath;

	@JsonPropertyDescription("DQOps Cloud API Key")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String apiKey;

	@JsonPropertyDescription("Disable synchronization with DQOps Cloud")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean disableCloudSync;

	@JsonPropertyDescription("DQOps instance signature key used to sign keys. This should be a Base64 encoded binary key at a 32 bytes length.")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String instanceSignatureKey;

	@JsonPropertyDescription("Default IANA time zone name of the server. This time zone is used to convert the time of UTC timestamps values returned " +
			"from databases to a uniform local date and time. The default value is the local time zone of the DQOps server instance.")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String timeZone;

	@JsonPropertyDescription("DQOps instance name assigned to this server instance. DQOps supports scheduling data quality checks only on selected instances. If this parameter is not set, DQOps will use a name passed as a parameter or environment variable. The fallback value is the host name.")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String instanceName;

	@JsonPropertyDescription("SMTP server configuration for incident notifications.")
	private SmtpServerConfigurationSpec smtpServerConfiguration;

	@JsonPropertyDescription("A dictionary containing the configuration of local data domains managed by this instance.")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
	private LocalDataDomainSpecMap dataDomains = new LocalDataDomainSpecMap();

	@JsonPropertyDescription("A list of urls of special REST API services that will transform DQOps data quality health models to a format supported by a target data catalog platform. These services are called to push the health status of tables.")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
	private DataCatalogUrlsSetSpec dataCatalogUrls = new DataCatalogUrlsSetSpec();

	@JsonIgnore
	private String yamlParsingError;

	/**
	 * Default constructor.
	 */
	public LocalSettingsSpec() {
	}

	/**
	 * Editor name constructor.
	 */
	public LocalSettingsSpec(String editorName) {
		this.editorName = editorName;
	}

	/**
	 * Sets a value that indicates that the YAML file deserialized into this object has a parsing error.
	 *
	 * @param yamlParsingError YAML parsing error.
	 */
	@Override
	public void setYamlParsingError(String yamlParsingError) {
		this.yamlParsingError = yamlParsingError;
	}

	/**
	 * Returns the YAML parsing error that was captured.
	 *
	 * @return YAML parsing error.
	 */
	@Override
	public String getYamlParsingError() {
		return this.yamlParsingError;
	}

	/**
	 * Returns an editor name.
	 * @return Editor name.
	 */
	public String getEditorName() {
		return this.editorName;
	}

	/**
	 * Sets an editor name.
	 * @param editorName Editor name.
	 */
	public void setEditorName(String editorName) {
		setDirtyIf(!Objects.equals(this.editorName, editorName));
		this.editorName = editorName;
	}

	/**
	 * Returns an editor path.
	 * @return Editor path.
	 */
	public String getEditorPath() {
		return this.editorPath;
	}

	/**
	 * Sets an editor path.
	 * @param editorPath Editor path.
	 */
	public void setEditorPath(String editorPath) {
		setDirtyIf(!Objects.equals(this.editorPath, editorPath));
		this.editorPath = editorPath;
	}

	/**
	 * Returns an api key.
	 * @return Api key.
	 */
	public String getApiKey() {
		return this.apiKey;
	}

	/**
	 * Sets an api key.
	 * @param apiKey Api key.
	 */
	public void setApiKey(String apiKey) {
		setDirtyIf(!Objects.equals(this.apiKey, apiKey));
		this.apiKey = apiKey;
	}

	/**
	 * Retrieves the flag in synchronization with DQOps cloud is disabled.
	 * @return True when synchronization with DQOps cloud is disabled.
	 */
	public boolean isDisableCloudSync() {
		return disableCloudSync;
	}

	/**
	 * Sets a flag to disable synchronization with DQOps cloud.
	 * @param disableCloudSync Disable synchronization with DQOps cloud.
	 */
	public void setDisableCloudSync(boolean disableCloudSync) {
		setDirtyIf(this.disableCloudSync != disableCloudSync);
		this.disableCloudSync = disableCloudSync;
	}

	/**
	 * Returns the DQOps instance signature key as Base64 encoded byte array.
	 * @return Instance signature key, base64.
	 */
	public String getInstanceSignatureKey() {
		return instanceSignatureKey;
	}

	/**
	 * Sets the instance signature key as a base64 encoded string.
	 * @param instanceSignatureKey Instance signature key.
	 */
	public void setInstanceSignatureKey(String instanceSignatureKey) {
		setDirtyIf(!Objects.equals(this.instanceSignatureKey, instanceSignatureKey));
		this.instanceSignatureKey = instanceSignatureKey;
	}

	/**
	 * Returns the default server timezone. It should be a valid IANA time zone name.
	 * @return The default time zone as a well-known IANA time zone code.
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * Sets the default server time zone.
	 * @param timeZone IANA time zone name.
	 */
	public void setTimeZone(String timeZone) {
		setDirtyIf(!Objects.equals(this.timeZone, timeZone));
		this.timeZone = timeZone;
	}

	/**
	 * Returns an instance name assigned to this DQOps instance.
	 * @return Instance name.
	 */
	public String getInstanceName() {
		return instanceName;
	}

	/**
	 * Sets an instance name to identify this DQOps instance.
	 * @param instanceName Instance name.
	 */
	public void setInstanceName(String instanceName) {
		setDirtyIf(!Objects.equals(this.instanceName, instanceName));
		this.instanceName = instanceName;
	}

	/**
	 * Returns the SMTP server configuration for the incident notifications.
	 * @return The SMTP server configuration for the incident notifications.
	 */
	public SmtpServerConfigurationSpec getSmtpServerConfiguration() {
		return smtpServerConfiguration;
	}

	/**
	 * Sets the SMTP server configuration for the incident notifications.
	 * @param smtpServerConfiguration SMTP server configuration for the incident notifications.
	 */
	public void setSmtpServerConfiguration(SmtpServerConfigurationSpec smtpServerConfiguration) {
		setDirtyIf(!Objects.equals(this.smtpServerConfiguration, smtpServerConfiguration));
		this.smtpServerConfiguration = smtpServerConfiguration;
		propagateHierarchyIdToField(dataDomains, "smtp_server_configuration");
	}

	/**
	 * Returns the dictionary of local data domains.
	 * @return Dictioary of local data domains.
	 */
	public LocalDataDomainSpecMap getDataDomains() {
		return dataDomains;
	}

	/**
	 * Stores the dictionary of local data domains.
	 * @param dataDomains The new dictionary of local data domains.
	 */
	public void setDataDomains(LocalDataDomainSpecMap dataDomains) {
		setDirtyIf(!Objects.equals(this.dataDomains, dataDomains));
		this.dataDomains = dataDomains;
		propagateHierarchyIdToField(dataDomains, "data_domains");
	}

	/**
	 * Returns a list of data catalog URLs of rest api services that are pushing data quality health statuses.
	 * @return List of data quality urls.
	 */
	public DataCatalogUrlsSetSpec getDataCatalogUrls() {
		return dataCatalogUrls;
	}

	/**
	 * Changes the collection of data quality urls.
	 * @param dataCatalogUrls New list of data quality urls.
	 */
	public void setDataCatalogUrls(DataCatalogUrlsSetSpec dataCatalogUrls) {
		setDirtyIf(!Objects.equals(this.dataCatalogUrls, dataCatalogUrls));
		this.dataCatalogUrls = dataCatalogUrls;
		propagateHierarchyIdToField(dataCatalogUrls, "data_catalog_urls");
	}

	/**
	 * Returns the child map on the spec class with all fields.
	 *
	 * @return Return the field map.
	 */
	@Override
	protected ChildHierarchyNodeFieldMap getChildMap() {
		return FIELDS;
	}

	/**
	 * Creates and returns a deep copy of this object.
	 */
	@Override
	public LocalSettingsSpec deepClone() {
		return (LocalSettingsSpec) super.deepClone();
	}

	/**
	 * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
	 * @param secretValueProvider Secret value provider to use.
	 * @param lookupContext Secret lookup context.
	 * @return Trimmed and expanded version of this object.
	 */
	public LocalSettingsSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
		LocalSettingsSpec cloned = (LocalSettingsSpec) super.deepClone();
		cloned.apiKey = secretValueProvider.expandValue(this.apiKey, lookupContext);
		cloned.instanceSignatureKey = secretValueProvider.expandValue(this.instanceSignatureKey, lookupContext);
		cloned.editorPath = secretValueProvider.expandValue(this.editorPath, lookupContext);
		cloned.editorName = secretValueProvider.expandValue(this.editorName, lookupContext);
		cloned.timeZone = secretValueProvider.expandValue(this.timeZone, lookupContext);
		cloned.instanceName = secretValueProvider.expandValue(this.instanceName, lookupContext);
		if (this.smtpServerConfiguration != null) {
			cloned.smtpServerConfiguration = this.smtpServerConfiguration.expandAndTrim(secretValueProvider, lookupContext);
		}
        return cloned;
	}

	/**
	 * Calls the visitor.
	 * @param visitor Visitor instance.
	 * @param parameter Additional parameter that will be passed back to the visitor.
	 * @return Visitor's result.
	 */
	@Override
	public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
		return visitor.accept(this, parameter);
	}
}
