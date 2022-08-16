package ai.dqo.metadata.search;

/**
 * Search filters used to search for the recurring schedule specifications.
 */
public class RecurringScheduleSearchFilters {
    /**
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public RecurringScheduleSearchFiltersVisitor createSearchFilterVisitor() {
        return new RecurringScheduleSearchFiltersVisitor(this);
    }
}
