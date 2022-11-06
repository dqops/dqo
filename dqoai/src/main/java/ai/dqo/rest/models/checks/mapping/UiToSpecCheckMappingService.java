package ai.dqo.rest.models.checks.mapping;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.rest.models.checks.UIAllChecksModel;

/**
 * Service that updates the check specification from the UI model that was filled with updates.
 */
public interface UiToSpecCheckMappingService {
    /**
     * Updates the <code>checkCategoriesSpec</code> with the updates received from the UI in the <code>model</code>.
     *
     * @param model               Data quality check UI model with the updates.
     * @param checkCategoriesSpec The target check categories spec object that will be updated.
     */
    void updateAllChecksSpecs(UIAllChecksModel model, AbstractRootChecksContainerSpec checkCategoriesSpec);
}
