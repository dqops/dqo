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
package com.dqops.metadata.settings;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

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
		}
	};

	@JsonPropertyDescription("Editor name spec (VSC, Eclipse, Intellij)")
	private String editorName;

	@JsonPropertyDescription("Editor path on user's computer")
	private String editorPath;

	@JsonPropertyDescription("Api key")
	private String apiKey;

	@JsonPropertyDescription("Disable synchronization with DQOps cloud")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean disableCloudSync;

	@JsonPropertyDescription("DQOps instance signature key used to sign keys. This should be a Base64 encoded binary key at a 32 bytes length.")
	private String instanceSignatureKey;

	@JsonPropertyDescription("Default IANA time zone name of the server. This time zone is used to convert the time of UTC timestamps values returned " +
			"from databases to a uniform local date and time. The default value is the local time zone of the DQOps server instance.")
	private String timeZone;

	@JsonPropertyDescription("SMTP server configuration for incident notifications.")
	private SmtpServerConfigurationSpec smtpServerConfiguration;

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
        return cloned;
	}

	@Override
	public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
		return visitor.accept(this, parameter);
	}
}
