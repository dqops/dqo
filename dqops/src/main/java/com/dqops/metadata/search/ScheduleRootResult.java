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
package com.dqops.metadata.search;

import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;

/**
 * Result object returned by the schedule root search operation (to find nodes that have a cron schedule that is applicable to all nodes inside).
 * This object is a pair of the node ({@link com.dqops.metadata.sources.ConnectionSpec}, {@link com.dqops.metadata.sources.TableSpec} or {@link com.dqops.checks.AbstractCheckSpec})
 * and a scheduling group type (profiling, daily, monthly, etc).
 */
public class ScheduleRootResult {
    private CheckRunScheduleGroup scheduleGroup;
    private HierarchyNode scheduleRootNode;

    /**
     * Creates a schedule root find result.
     * @param scheduleGroup Scheduling group that identifies the type of schedule to use.
     * @param scheduleRootNode Metadata root node ({@link com.dqops.metadata.sources.ConnectionSpec}, {@link com.dqops.metadata.sources.TableSpec} or {@link com.dqops.checks.AbstractCheckSpec}) that has a schedule defined.
     */
    public ScheduleRootResult(CheckRunScheduleGroup scheduleGroup, HierarchyNode scheduleRootNode) {
        this.scheduleGroup = scheduleGroup;
        this.scheduleRootNode = scheduleRootNode;
    }

    /**
     * Returns the scheduling group - the name of the schedule to use and filter the check types.
     * @return Scheduling group.
     */
    public CheckRunScheduleGroup getScheduleGroup() {
        return scheduleGroup;
    }

    /**
     * Returns the metadata node do be scheduled.
     * @return Metadata root node ({@link com.dqops.metadata.sources.ConnectionSpec}, {@link com.dqops.metadata.sources.TableSpec} or {@link com.dqops.checks.AbstractCheckSpec}) that has a schedule defined.
     */
    public HierarchyNode getScheduleRootNode() {
        return scheduleRootNode;
    }
}
