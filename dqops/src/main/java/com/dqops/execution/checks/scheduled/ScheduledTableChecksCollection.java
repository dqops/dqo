/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.checks.scheduled;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableWrapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Collection of checks inside a single table that should be executed as part of a schedule.
 */
public class ScheduledTableChecksCollection {
    private TableSpec targetTable;
    private Set<HierarchyId> checks = new LinkedHashSet<>();

    /**
     * Creates an object for a given target table.
     * @param targetTable Target table.
     */
    public ScheduledTableChecksCollection(TableSpec targetTable) {
        this.targetTable = targetTable;
    }

    /**
     * Returns a target table wrapper.
     * @return Target table.
     */
    public TableSpec getTargetTable() {
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
    public void addCheck(AbstractCheckSpec checkSpec) {
        assert this.targetTable.getHierarchyId().isMyDescendant(checkSpec.getHierarchyId());

        this.checks.add(checkSpec.getHierarchyId());
    }
}
