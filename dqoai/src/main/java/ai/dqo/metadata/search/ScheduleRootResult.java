/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.metadata.search;

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.HierarchyNode;
import ai.dqo.metadata.scheduling.CheckRunRecurringScheduleGroup;

/**
 * Result object returned by the schedule root search operation (to find nodes that have a cron schedule that is applicable to all nodes inside).
 * This object is a pair of the node ({@link ai.dqo.metadata.sources.ConnectionSpec}, {@link ai.dqo.metadata.sources.TableSpec} or {@link ai.dqo.checks.AbstractCheckSpec})
 * and a scheduling group type (profiling, daily, monthly, etc).
 */
public class ScheduleRootResult {
    private CheckRunRecurringScheduleGroup scheduleGroup;
    private HierarchyNode scheduleRootNode;

    /**
     * Creates a schedule root find result.
     * @param scheduleGroup Scheduling group that identifies the type of schedule to use.
     * @param scheduleRootNode Metadata root node ({@link ai.dqo.metadata.sources.ConnectionSpec}, {@link ai.dqo.metadata.sources.TableSpec} or {@link ai.dqo.checks.AbstractCheckSpec}) that has a schedule defined.
     */
    public ScheduleRootResult(CheckRunRecurringScheduleGroup scheduleGroup, HierarchyNode scheduleRootNode) {
        this.scheduleGroup = scheduleGroup;
        this.scheduleRootNode = scheduleRootNode;
    }

    /**
     * Returns the scheduling group - the name of the schedule to use and filter the check types.
     * @return Scheduling group.
     */
    public CheckRunRecurringScheduleGroup getScheduleGroup() {
        return scheduleGroup;
    }

    /**
     * Returns the metadata node do be scheduled.
     * @return Metadata root node ({@link ai.dqo.metadata.sources.ConnectionSpec}, {@link ai.dqo.metadata.sources.TableSpec} or {@link ai.dqo.checks.AbstractCheckSpec}) that has a schedule defined.
     */
    public HierarchyNode getScheduleRootNode() {
        return scheduleRootNode;
    }
}
