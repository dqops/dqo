package ai.dqo.execution.checks.scheduled;

import ai.dqo.checks.AbstractCheckDeprecatedSpec;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.sources.TableWrapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Collection of checks inside a single table that should be executed as part of a schedule.
 */
public class ScheduledTableChecksCollection {
    private TableWrapper targetTable;
    private HashSet<HierarchyId> checks = new LinkedHashSet<>();

    /**
     * Creates an object for a given target table.
     * @param targetTable Target table.
     */
    public ScheduledTableChecksCollection(TableWrapper targetTable) {
        this.targetTable = targetTable;
    }

    /**
     * Returns a target table wrapper.
     * @return Target table.
     */
    public TableWrapper getTargetTable() {
        return targetTable;
    }

    /**
     * Returns a collection of identifiers of checks that are nested inside the target table and should be executed.
     * @return Collection of check's hierarchy ids that identify checks to be executed.
     */
    public Set<HierarchyId> getChecks() {
        return Collections.unmodifiableSet(checks);
    }

    /**
     * Adds a check to the collection of unique checks within the table.
     * A check can be added multiple times, secondary calls are just ignored and the check is added only once.
     * @param checkSpec Check to be added.
     */
    public void addCheck(AbstractCheckDeprecatedSpec checkSpec) {
        assert this.targetTable.getHierarchyId().isMyDescendant(checkSpec.getHierarchyId());

        this.checks.add(checkSpec.getHierarchyId());
    }
}
