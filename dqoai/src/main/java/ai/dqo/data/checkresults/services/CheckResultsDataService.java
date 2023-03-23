package ai.dqo.data.checkresults.services;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.data.checkresults.services.models.CheckResultsDetailedDataModel;
import ai.dqo.data.checkresults.services.models.CheckResultsOverviewDataModel;

/**
 * Service that returns data from the check results.
 */
public interface CheckResultsDataService {
    /**
     * Retrieves the overall status of the recent check executions for the given root checks container (group of checks).
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @return Overview of the check's recent results.
     */
    CheckResultsOverviewDataModel[] readMostRecentCheckStatuses(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                                CheckResultsOverviewParameters loadParameters);

    /**
     * Retrieves complete model of the results of check executions for the given root checks container (group of checks).
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @return Complete model of the check results.
     */
    CheckResultsDetailedDataModel[] readCheckStatusesDetailed(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                              CheckResultsDetailedParameters loadParameters);
}
