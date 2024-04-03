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
