package ai.dqo.data.ruleresults.services;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.data.ruleresults.services.models.CheckResultsOverviewDataModel;

/**
 * Service that returns data from the check results.
 */
public interface RuleResultsDataService {
    /**
     * Retrieves the overall status of the recent check executions for the given root checks container (group of checks).
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @return Overview of the check recent results.
     */
    CheckResultsOverviewDataModel[] readMostRecentCheckStatuses(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                                CheckResultsOverviewParameters loadParameters);
}
