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

import com.dqops.metadata.basespecs.AbstractDirtyTrackingSpecList;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;

import java.util.Comparator;
import java.util.Objects;

/**
 * List of dashboards.
 */
public class DashboardListSpec extends AbstractDirtyTrackingSpecList<DashboardSpec> implements Cloneable {
    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public DashboardListSpec deepClone() {
        DashboardListSpec cloned = new DashboardListSpec();
        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(this.getHierarchyId().clone());
        }

        for (DashboardSpec dashboard : this) {
            cloned.add(dashboard.deepClone());
        }

        cloned.clearDirty(false);
        return cloned;
    }

    /**
     * Adds a DQOps Cloud dashboard using a fluent interface.
     * @param dashboardName Dashboard name.
     * @param url Looker studio dashboard url.
     * @param width Width in pixels.
     * @param height Height in pixels.
     * @return Self.
     */
    public DashboardListSpec withDqoCloudDashboard(String dashboardName, String url, Integer width, Integer height) {
        DashboardSpec dashboardSpec = new DashboardSpec() {{
            setDashboardName(dashboardName);
            setUrl(url);
            setWidth(width);
            setHeight(height);
        }};
        this.add(dashboardSpec);

        return this;
    }

    /**
     * Finds a dashboard by name.
     * @param dashboardName Dashboard specification when the dashboard was found or null when the dashboard is missing.
     * @return Dashboard specification or null.
     */
    public DashboardSpec getDashboardByName(String dashboardName) {
        for (DashboardSpec dashboard : this) {
            if (Objects.equals(dashboardName, dashboard.getDashboardName())) {
                return dashboard;
            }
        }

        return null;
    }

    /**
     * Sorts the list of folders per folder name. Sorts also nested dashboards and folders inside nested dashboards.
     */
    public void sort() {
        this.sort(Comparator.comparing(DashboardSpec::getDashboardName));
    }

    /**
     * Merges the list of dashboards with another (user's custom dashboards), returning a combined list.
     * @param otherDashboards List of custom dashboards.
     * @return Combined list of dashboards, overriding own dashboards by dashboards from the other list.
     */
    public DashboardListSpec merge(DashboardListSpec otherDashboards) {
        if (otherDashboards == null || otherDashboards.size() == 0) {
            return this;
        }

        DashboardListSpec cloned = new DashboardListSpec();
        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(this.getHierarchyId());
        }

        for (DashboardSpec dashboardSpec : this) {
            DashboardSpec otherDashboard = otherDashboards.getDashboardByName(dashboardSpec.getDashboardName());
            if (otherDashboard != null) {
                cloned.add(otherDashboard);
            } else {
                cloned.add(dashboardSpec);
            }
        }

        for (DashboardSpec otherDashboard : otherDashboards) {
            if (cloned.getDashboardByName(otherDashboard.getDashboardName()) != null) {
                continue; // dashboard already merged
            }

            cloned.add(otherDashboard);
        }

        return cloned;
    }
}
