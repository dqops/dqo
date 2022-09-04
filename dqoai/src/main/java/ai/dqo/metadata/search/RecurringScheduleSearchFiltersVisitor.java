package ai.dqo.metadata.search;

import ai.dqo.metadata.id.HierarchyNode;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.traversal.TreeNodeTraversalResult;

import java.util.List;

/**
 * Metadata node search visitor that is searching for all unique recurring schedules.
 */
public class RecurringScheduleSearchFiltersVisitor extends AbstractSearchVisitor {
    private RecurringScheduleSearchFilters filters;

    /**
     * Creates a visitor given the search filters.
     * @param filters Search filters.
     */
    public RecurringScheduleSearchFiltersVisitor(RecurringScheduleSearchFilters filters) {
        this.filters = filters;
    }

    /**
     * Accepts a recurring schedule specification, it is the cron expression how to schedule the job.
     *
     * @param recurringScheduleSpec Recurring schedule.
     * @param parameter             Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(RecurringScheduleSpec recurringScheduleSpec, List<HierarchyNode> parameter) {
        Boolean enabledFilter = this.filters.getScheduleEnabled();
        if (enabledFilter != null) {
            boolean mustBeDisabled = !enabledFilter;
            if (recurringScheduleSpec.isDisable() != mustBeDisabled) {
                return TreeNodeTraversalResult.STOP_TRAVERSAL; // no children possible
            }
        }

        parameter.add(recurringScheduleSpec);
        return TreeNodeTraversalResult.STOP_TRAVERSAL; // no children possible
    }
}
