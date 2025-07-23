/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.dqohome;

import com.dqops.metadata.basespecs.Flushable;
import com.dqops.metadata.dashboards.DashboardFolderListSpecWrapper;
import com.dqops.metadata.definitions.checks.CheckDefinitionList;
import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.sensors.SensorDefinitionList;
import com.dqops.metadata.id.HierarchyNode;

/**
 * Dqo.io home model to access the content of the DQO_HOME folder. Provides access to the data in the application home, like built-in rules, sensors, checks and dashboards definitions.
 * The actual implementation can use a local file system, a virtual file system or a database.
 */
public interface DqoHome extends Flushable, HierarchyNode {
    /**
     * Returns a list of sensor definitions.
     * @return Collection of sensor definitions.
     */
    SensorDefinitionList getSensors();

    /**
     * Returns a list of rules definitions.
     * @return Collection of sensor definitions.
     */
    RuleDefinitionList getRules();

    /**
     * Returns a list of check definitions.
     * @return Collection of check definitions.
     */
    CheckDefinitionList getChecks();

    /**
     * Returns a list of dashboards definitions.
     * @return Collection of dashboards definitions.
     */
    DashboardFolderListSpecWrapper getDashboards();
}
