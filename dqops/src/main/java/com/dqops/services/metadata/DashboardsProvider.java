/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.metadata;

import com.dqops.metadata.dashboards.DashboardsFolderListSpec;

/**
 * Service that builds an expanded tree of dashboards. Builds an expanded dashboard tree only once.
 */
public interface DashboardsProvider {
    /**
     * Returns a cached dashboard folder tree that is expanded. All templated (multi parameter valued) dashboards are expanded into multiple dashboards.
     *
     * @return Expanded dashboard tree.
     */
    DashboardsFolderListSpec getDashboardTree();
}
