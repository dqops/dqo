package ai.dqo.data.errors.services;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.data.errors.services.models.ErrorsDetailedDataModel;

/**
 * Service that returns data from sensor readouts.
 */
public interface ErrorsDataService {
    /**
     * Retrieves the complete model of the errors related to check executions for the given root checks container (group of checks).
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @return Complete model of the errors.
     */
    ErrorsDetailedDataModel[] readErrorsDetailed(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                 ErrorsDetailedParameters loadParameters);
}
