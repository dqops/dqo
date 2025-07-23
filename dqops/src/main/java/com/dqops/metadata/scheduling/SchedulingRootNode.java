/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.scheduling;

import com.dqops.metadata.id.HierarchyNode;

/**
 * Marker interface for nodes that contain its own configuration of CRON schedules and are used as a target for scheduling: connection wrapper, table, check.
 */
public interface SchedulingRootNode extends HierarchyNode {
}
