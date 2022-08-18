package ai.dqo.metadata.search;

/**
 * Search filters used to search for the recurring schedule specifications.
 */
public class RecurringScheduleSearchFilters {
    private Boolean enabled = true;

    /**
     * Returns the filter for enabled only schedules.
     * @return Filter for the enabled or disabled status.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets the filter for only enabled or disabled schedules.
     * @param enabled Not null value for a filter.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public RecurringScheduleSearchFiltersVisitor createSearchFilterVisitor() {
        return new RecurringScheduleSearchFiltersVisitor(this);
    }
}
