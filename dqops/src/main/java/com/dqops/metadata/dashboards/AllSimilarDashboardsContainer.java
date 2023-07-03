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
package com.dqops.metadata.dashboards;

import com.dqops.metadata.id.HierarchyNode;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Object that stores all identified unique dashboards, grouped by their urls, but with possible parameter values.
 */
public class AllSimilarDashboardsContainer {
    private Map<String, SimilarDashboardsContainer> similarDashboards = new LinkedHashMap<>();

    /**
     * Returns a collection of similar dashboards, in the order how they were found.
     * @return List of similar dashboards in the order how they were found.
     */
    public Collection<SimilarDashboardsContainer> getSimilarDashboards() {
        return this.similarDashboards.values();
    }

    /**
     * Adds a dashboard to the container of similar dashboards. Finds a group of similar dashboards that use the same looker studio url and adds the dashboard.
     * @param dashboardSpec Dashboard specification to be added.
     * @param rootNode Root node, used to find and resolve folder names.
     */
    public void addDashboard(DashboardSpec dashboardSpec, HierarchyNode rootNode) {
        String url = dashboardSpec.getUrl();
        SimilarDashboardsContainer similarDashboardsContainer = this.similarDashboards.get(url);
        if (similarDashboardsContainer == null) {
            similarDashboardsContainer = SimilarDashboardsContainer.fromDashboardSpec(dashboardSpec, rootNode);
            this.similarDashboards.put(url, similarDashboardsContainer);
            return;
        }

        similarDashboardsContainer.addDashboard(dashboardSpec);
    }

    /**
     * Creates a new dashboard folder list from scratch, adding only the top-most dashboards and building aggregated dashboard parameters.
     * @return List of root level folders with all dashboards added as templated (with parameter values that require further expansion).
     */
    public DashboardsFolderListSpec createDashboardFolderList() {
        DashboardsFolderListSpec resultFolderList = new DashboardsFolderListSpec();

        for (SimilarDashboardsContainer similarDashboardsContainer : this.similarDashboards.values()) {
            DashboardsFolderSpec targetDashboardFolder = resultFolderList.getOrCreateFolderPath(similarDashboardsContainer.getFirstDashboardFolderPath());
            DashboardSpec templatedDashboardSpec = similarDashboardsContainer.createTemplatedDashboardSpec();
            targetDashboardFolder.getDashboards().add(templatedDashboardSpec);
        }

        return resultFolderList;
    }
}
