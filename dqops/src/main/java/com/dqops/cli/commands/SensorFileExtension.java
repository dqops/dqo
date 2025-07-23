/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Tabular output format for printing the tabular results.
 */
public enum SensorFileExtension {
	/**
	 * The jinja2 file extension.
	 */
	@JsonProperty("jinja2")
	JINJA2,
	/**
	 * The yaml file extension.
	 */
	@JsonProperty("yaml")
	YAML,
}
