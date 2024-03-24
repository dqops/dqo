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

package com.dqops.metadata.scheduling;

import com.dqops.metadata.id.HierarchyNode;

/**
 * Marker interface for nodes that contain its own configuration of CRON schedules and are used as a target for scheduling: connection wrapper, table, check.
 */
public interface SchedulingRootNode extends HierarchyNode {
}
