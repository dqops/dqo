/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.settings;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.metadata.settings.LocalSettingsSpec;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.reflection.DefaultFieldValue;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * DQOps local settings that are stored in the *$DQO_USER_HOME/.localsettings.dqosettings.yaml* file in the user's DQOps home folder.
 * The local settings contain the current DQOps Cloud API Key and other settings. The local settings take precedence over parameters
 * passed when starting DQOps.
 */
public class LocalSettingsYaml implements InvalidYamlStatusHolder {
	@JsonPropertyDescription("DQOps YAML schema version")
	@DefaultFieldValue(ApiVersion.CURRENT_API_VERSION)
	private String apiVersion = ApiVersion.CURRENT_API_VERSION;

	@JsonPropertyDescription("File type")
	@DefaultFieldValue("settings")
	private SpecificationKind kind = SpecificationKind.settings;

	@JsonPropertyDescription("The object that stores the configuration settings of a local DQOps instance")
	private LocalSettingsSpec spec = new LocalSettingsSpec();

	@JsonIgnore
	private String yamlParsingError;

	public LocalSettingsYaml() {
	}

	public LocalSettingsYaml(LocalSettingsSpec spec) {
		this.spec = spec;
	}

	/**
	 * Current api version. The current version is dqo/v1
	 * @return Current api version.
	 */
	public String getApiVersion() {
		return apiVersion;
	}

	/**
	 * Sets the api version for the serialized file.
	 * @param apiVersion Api version.
	 */
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	/**
	 * Returns the YAML file kind.
	 * @return Yaml file kind.
	 */
	public SpecificationKind getKind() {
		return kind;
	}

	/**
	 * Sets YAML file kind.
	 * @param kind YAML file kind.
	 */
	public void setKind(SpecificationKind kind) {
		this.kind = kind;
	}

	/**
	 * Returns a data source specification.
	 * @return Data source specification.
	 */
	public LocalSettingsSpec getSpec() {
		return spec;
	}

	/**
	 * Sets a data source specification.
	 * @param spec Data source specification.
	 */
	public void setSpec(LocalSettingsSpec spec) {
		this.spec = spec;
	}

	/**
	 * Sets a value that indicates that the YAML file deserialized into this object has a parsing error.
	 *
	 * @param yamlParsingError YAML parsing error.
	 */
	@Override
	public void setYamlParsingError(String yamlParsingError) {
		if (this.spec != null) {
			this.spec.setYamlParsingError(yamlParsingError);
		}
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
}
