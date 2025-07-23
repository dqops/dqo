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

import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableWrapper;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

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
     * @param targetTableSpec Target table specification.
     * @return Collection of scheduled checks within the given table.
     */
    public ScheduledTableChecksCollection getOrAddTableChecks(TableSpec targetTableSpec) {
        ScheduledTableChecksCollection scheduledTableChecksCollection = this.tablesWithChecks.get(targetTableSpec.getHierarchyId());
        if (scheduledTableChecksCollection == null) {
            scheduledTableChecksCollection = new ScheduledTableChecksCollection(targetTableSpec);
            addTableChecks(scheduledTableChecksCollection);
        }

        return scheduledTableChecksCollection;
    }
}
