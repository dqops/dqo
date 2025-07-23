/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
