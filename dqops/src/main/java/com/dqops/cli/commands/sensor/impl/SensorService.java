/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.sensor.impl;

import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.SensorFileExtension;
import com.dqops.connectors.ProviderType;

/**
 * Service called from the "sensor" cli commands to edit a template.
 */
public interface SensorService {
	CliOperationStatus editTemplate(String sensorName, ProviderType provider, SensorFileExtension ext);

}
