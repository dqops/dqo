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
import com.dqops.utils.exceptions.DqoRuntimeException;
import org.apache.parquet.Strings;

import java.util.*;

/**
 * Object that stores similar dashboards identified by the looker studio url.
 */
public class SimilarDashboardsContainer {
    private String firstDashboardName;
    private List<String> firstDashboardFolderPath;
    private String url;
    private Integer width;
    private Integer height;
    private Map<String, LinkedHashSet<String>> parameters = new LinkedHashMap<>();
    private List<DashboardSpec> dashboards = new ArrayList<>();

    /**
     * Creates a similar dashboard container given the first dashboard that was found.
     * @param dashboardSpec The dashboard specification of the first dashboard.
     * @param rootNode Root node, used to resolve folder names.
     * @return Similar dashboards container.
     */
    public static SimilarDashboardsContainer fromDashboardSpec(DashboardSpec dashboardSpec, HierarchyNode rootNode) {
        SimilarDashboardsContainer similarDashboardsContainer = new SimilarDashboardsContainer();
        similarDashboardsContainer.firstDashboardName = dashboardSpec.getDashboardName();
        if (Strings.isNullOrEmpty(dashboardSpec.getUrl())) {
            throw new DqoRuntimeException("Dashboard " + dashboardSpec.getDashboardName() + ", hierarchy id: " + dashboardSpec.getHierarchyId().toString() + " has no url");
        }
        similarDashboardsContainer.url = dashboardSpec.getUrl().replace("https://datastudio.google.com/", "https://lookerstudio.google.com/");
        similarDashboardsContainer.width = dashboardSpec.getWidth();
        similarDashboardsContainer.height = dashboardSpec.getHeight();
        similarDashboardsContainer.firstDashboardFolderPath = dashboardSpec.getFolderPath(rootNode);
        similarDashboardsContainer.dashboards.add(dashboardSpec);
        similarDashboardsContainer.addDashboardParameters(dashboardSpec);

        return similarDashboardsContainer;
    }

    /**
     * Add the parameters extracted from the dashboard to the dictionary of all known parameters with all known values.
     * @param dashboardSpec Dashboard specification to analyze.
     */
    protected void addDashboardParameters(DashboardSpec dashboardSpec) {
        if (dashboardSpec.getParameters() == null || dashboardSpec.getParameters().size() == 0) {
            return;
        }

        for (Map.Entry<String, String> parameterEntry : dashboardSpec.getParameters().entrySet()) {
            String parameterName = parameterEntry.getKey();
            String parameterValue = parameterEntry.getValue();
            LinkedHashSet<String> allParameterValuesList = this.parameters.get(parameterName);
            if (allParameterValuesList == null) {
                allParameterValuesList = new LinkedHashSet<>();
                this.parameters.put(parameterName, allParameterValuesList);
            }
            allParameterValuesList.add(parameterValue);
        }
    }

    /**
     * Returns the name of the first dashboard that was found.
     * @return The name of the first dashboard.
     */
    public String getFirstDashboardName() {
        return firstDashboardName;
    }

    /**
     * Returns the folder path (a list of folder names) to the first dashboard.
     * @return The folder path to the first dashboard.
     */
    public List<String> getFirstDashboardFolderPath() {
        return firstDashboardFolderPath;
    }

    /**
     * The url of all dashboards.
     * @return The looker studio dashboard url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns the width of the first dashboard.
     * @return The width of the first dashboard.
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * Returns the height of the first dashboard.
     * @return The height of the first dashboard.
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * Returns the dictionary of parameter names (keys) and a list of all parameter values that were found.
     * @return Dictionary of all parameters with all parameter values that were found.
     */
    public Map<String, LinkedHashSet<String>> getParameters() {
        return parameters;
    }

    /**
     * Returns the list of dashboards that were found.
     * @return List of dashboards.
     */
    public List<DashboardSpec> getDashboards() {
        return dashboards;
    }

    /**
     * Adds a dashboard and all its parameters to the container.
     * @param dashboardSpec Dashboard to be added.
     */
    public void addDashboard(DashboardSpec dashboardSpec) {
        this.dashboards.add(dashboardSpec);
        this.addDashboardParameters(dashboardSpec);
    }

    /**
     * Creates a single dashboard that is templated - contains all detected parameter values as a special value used for the parameter expansion and generating nested dashboards.
     * @return Templated dashboard.
     */
    public DashboardSpec createTemplatedDashboardSpec() {
        DashboardSpec dashboardSpec = new DashboardSpec();
        dashboardSpec.setDashboardName(this.getFirstDashboardName());
        dashboardSpec.setUrl(this.getUrl());
        dashboardSpec.setWidth(this.getWidth());
        dashboardSpec.setHeight(this.getHeight());
        LinkedHashMap<String, String> dashboardParameters = new LinkedHashMap<>();

        for (Map.Entry<String, LinkedHashSet<String>> parameterEntry : this.getParameters().entrySet()) {
            String parameterName = parameterEntry.getKey();
            LinkedHashSet<String> parameterValues = parameterEntry.getValue();
            if (parameterValues.size() == 1) {
                dashboardParameters.put(parameterName, parameterValues.stream().findFirst().get());
            } else {
                StringBuilder parameterValuesBuilder = new StringBuilder();
                parameterValuesBuilder.append('$');
                boolean isFirst = true;
                for (String parameterValue : parameterValues) {
                    if (!isFirst) {
                        parameterValuesBuilder.append(',');
                    }
                    isFirst = false;
                    parameterValuesBuilder.append(parameterValue);
                }
                parameterValuesBuilder.append('$');
                dashboardParameters.put(parameterName, parameterValuesBuilder.toString());
            }
        }

        dashboardSpec.setParameters(dashboardParameters);
        return dashboardSpec;
    }
}
