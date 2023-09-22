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

import com.dqops.checks.defaults.DefaultObservabilityCheckSettingsSpec;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.scheduling.MonitoringSchedulesSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Settings specification.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class SettingsSpec extends AbstractSpec {
	private static final ChildHierarchyNodeFieldMapImpl<SettingsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
		{
		}
	};

	@JsonPropertyDescription("Editor name spec (VSC, Eclipse, Intellij)")
	private String editorName;

	@JsonPropertyDescription("Editor path on user's computer")
	private String editorPath;

	@JsonPropertyDescription("Api key")
	private String apiKey;

	@JsonPropertyDescription("DQO instance signature key used to sign keys. This should be a Base64 encoded binary key at a 32 bytes length.")
	private String instanceSignatureKey;

	@JsonPropertyDescription("Default IANA time zone name of the server. This time zone is used to convert the time of UTC timestamps values returned from databases to a uniform local date and time. The default value is the local time zone of the DQO server instance.")
	private String timeZone;

	/**
	 * Default constructor.
	 */
	public SettingsSpec() {
	}

	/**
	 * Editor name constructor.
	 */
	public SettingsSpec(String editorName) {
		this.editorName = editorName;
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
	 * Returns the DQO instance signature key as Base64 encoded byte array.
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
	public SettingsSpec deepClone() {
		return (SettingsSpec) super.deepClone();
	}

	/**
	 * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
	 * @param secretValueProvider Secret value provider to use.
	 * @param lookupContext Secret lookup context.
	 * @return Trimmed and expanded version of this object.
	 */
	public SettingsSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
		SettingsSpec cloned = (SettingsSpec) super.deepClone();
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
