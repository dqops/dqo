package ai.dqo.cli.commands.sensor.impl;

import ai.dqo.cli.commands.SensorFileExtension;
import ai.dqo.cli.commands.status.CliOperationStatus;
import ai.dqo.connectors.ProviderType;

/**
 * Service called from the "sensor" cli commands to edit a template.
 */
public interface SensorService {
	CliOperationStatus editTemplate(String sensorName, ProviderType provider, SensorFileExtension ext);

}
