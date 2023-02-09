package ai.dqo.data.readouts.services;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.data.readouts.services.models.SensorReadoutsDetailedDataModel;

/**
 * Service that returns data from sensor readouts.
 */
public interface SensorReadoutsDataService {
    /**
     * Retrieves the complete model of the readouts of sensor activations for the given root checks container (group of checks).
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @return Complete model of the sensor readouts.
     */
    SensorReadoutsDetailedDataModel[] readSensorReadoutsDetailed(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                                SensorReadoutsDetailedParameters loadParameters);
}
