package ai.dqo.data.ruleresults.services;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.data.errors.snapshot.ErrorsSnapshotFactory;
import ai.dqo.data.ruleresults.services.models.CheckResultsOverviewDataModel;
import ai.dqo.data.ruleresults.snapshot.RuleResultsSnapshotFactory;
import ai.dqo.metadata.id.HierarchyNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service that returns data from the check results.
 */
@Service
public class RuleResultsDataServiceImpl implements RuleResultsDataService {
    private RuleResultsSnapshotFactory ruleResultsSnapshotFactory;
    private ErrorsSnapshotFactory errorsSnapshotFactory;

    @Autowired
    public RuleResultsDataServiceImpl(RuleResultsSnapshotFactory ruleResultsSnapshotFactory,
                                      ErrorsSnapshotFactory errorsSnapshotFactory) {
        this.ruleResultsSnapshotFactory = ruleResultsSnapshotFactory;
        this.errorsSnapshotFactory = errorsSnapshotFactory;
    }

    /**
     * Retrieves the overall status of the recent check executions for the given root checks container (group of checks).
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters Load parameters.
     * @return Overview of the check recent results.
     */
    @Override
    public Collection<CheckResultsOverviewDataModel> readMostRecentCheckStatuses(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                                                 CheckResultsOverviewParameters loadParameters) {
        Map<Long, CheckResultsOverviewDataModel> emptyCheckModels = makeEmptyCheckModels(rootChecksContainerSpec);

        // TODO: load both check results and errors, then find the most recent values

        return null;
    }

    /**
     * Identifies all checks in the check container, creates empty models with the check name and the check category name, identified (hashed) by the check_hash.
     * @param rootChecksContainerSpec Root check container.
     * @return Map of empty check results for the checks configured in the container.
     */
    public Map<Long, CheckResultsOverviewDataModel> makeEmptyCheckModels(AbstractRootChecksContainerSpec rootChecksContainerSpec) {
        Map<Long, CheckResultsOverviewDataModel> resultModelsMap = new LinkedHashMap<>();

        for (HierarchyNode categoryHierarchyNode : rootChecksContainerSpec.children()) {
            if (!(categoryHierarchyNode instanceof AbstractCheckCategorySpec)) {
                continue;
            }

            AbstractCheckCategorySpec checkCategorySpec = (AbstractCheckCategorySpec)categoryHierarchyNode;

            for (HierarchyNode checkHierarchyNode : checkCategorySpec.children()) {
                if (!(checkHierarchyNode instanceof AbstractCheckSpec)) {
                    continue;
                }

                AbstractCheckSpec<?,?,?,?> checkSpec = (AbstractCheckSpec<?,?,?,?>)checkHierarchyNode;
                long checkHash = checkSpec.getHierarchyId().hashCode64();

                CheckResultsOverviewDataModel checkResultsOverviewDataModel = new CheckResultsOverviewDataModel() {{
                    setCheckCategory(checkSpec.getCategoryName());
                    setCheckName(checkSpec.getCheckName());
                }};
                resultModelsMap.put(checkHash, checkResultsOverviewDataModel);
            }
        }

        return resultModelsMap;
    }
}
