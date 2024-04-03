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
