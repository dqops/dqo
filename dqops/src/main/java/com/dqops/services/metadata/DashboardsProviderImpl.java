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
