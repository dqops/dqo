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
import com.dqops.metadata.dashboards.DashboardsFolderSpec;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Service that builds an expanded tree of dashboards. Builds an expanded dashboard tree only once.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DashboardsProviderImpl implements DashboardsProvider {
    private DqoHomeContextFactory dqoHomeContextFactory;
    private DashboardsFolderListSpec dashboardFolderTree;
    private final Object lock = new Object();

    /**
     * Default injection constructor.
     * @param dqoHomeContextFactory DQOps Home context factory, returns the default implementation of the DQOps home.
     */
    @Autowired
    public DashboardsProviderImpl(DqoHomeContextFactory dqoHomeContextFactory) {
        this.dqoHomeContextFactory = dqoHomeContextFactory;
    }

    /**
     * Returns a cached dashboard folder tree that is expanded. All templated (multi parameter valued) dashboards are expanded into multiple dashboards.
     * @return Expanded dashboard tree.
     */
    @Override
    public DashboardsFolderListSpec getDashboardTree() {
        synchronized (this.lock) {
            if (this.dashboardFolderTree == null) {
                DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
                DashboardsFolderListSpec defaultTemplatedFolderList = dqoHomeContext.getDqoHome().getDashboards().getSpec();
                DashboardsFolderListSpec expandedDashboardTree = defaultTemplatedFolderList.createExpandedDashboardTree();

                // disable sorting, we will show the original order
//                for (DashboardsFolderSpec rootDashboardFolder : expandedDashboardTree) {
//                    rootDashboardFolder.sort();
//                }
                this.dashboardFolderTree = expandedDashboardTree;
            }

            return this.dashboardFolderTree;
        }
    }
}
