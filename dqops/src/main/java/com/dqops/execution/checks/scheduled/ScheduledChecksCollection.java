/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
