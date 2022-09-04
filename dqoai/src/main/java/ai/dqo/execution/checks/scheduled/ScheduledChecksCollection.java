package ai.dqo.execution.checks.scheduled;

import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.sources.TableWrapper;

import java.util.*;

/**
 * Collection of tables and their checks that will be executed for a single schedule.
 * This is a collection of objects that are grouping checks by a target table.
 */
public class ScheduledChecksCollection {
    private Map<HierarchyId, ScheduledTableChecksCollection> tablesWithChecks = new LinkedHashMap();

    /**
     * Adds a table with checks to the collection.
     * @param scheduledTableChecksCollection Table with nested checks.
     */
    public void addTableChecks(final ScheduledTableChecksCollection scheduledTableChecksCollection) {
        this.tablesWithChecks.put(scheduledTableChecksCollection.getTargetTable().getHierarchyId(), scheduledTableChecksCollection);
    }

    /**
     * Returns a collection of target tables and their checks.
     * @return Collection of target tables.
     */
    public Collection<ScheduledTableChecksCollection> getTablesWithChecks() {
        return Collections.unmodifiableCollection(this.tablesWithChecks.values());
    }

    /**
     * Retrieves a table check collection when a table was already added or created and returns a new empty table's check collection.
     * @param targetTableWrapper Target table wrapper.
     * @return Collection of scheduled checks within the given table.
     */
    public ScheduledTableChecksCollection getOrAddTableChecks(TableWrapper targetTableWrapper) {
        ScheduledTableChecksCollection scheduledTableChecksCollection = this.tablesWithChecks.get(targetTableWrapper.getHierarchyId());
        if (scheduledTableChecksCollection == null) {
            scheduledTableChecksCollection = new ScheduledTableChecksCollection(targetTableWrapper);
            addTableChecks(scheduledTableChecksCollection);
        }

        return scheduledTableChecksCollection;
    }
}
