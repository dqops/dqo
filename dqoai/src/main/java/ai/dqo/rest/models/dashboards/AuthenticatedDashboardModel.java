/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.rest.models.dashboards;

import ai.dqo.metadata.dashboards.DashboardSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;

/**
 * Model that describes a single authenticated dashboard.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "AuthenticatedDashboardModel", description = "Describes a single authenticated dashboard.")
public class AuthenticatedDashboardModel {
    @JsonPropertyDescription("Folder path")
    private String folderPath;
    @JsonPropertyDescription("Dashboard model with an unauthenticated url")
    private DashboardSpec dashboard;
    @JsonPropertyDescription("Dashboard authenticated url with a short lived refresh token")
    private String authenticatedDashboardUrl;

    public AuthenticatedDashboardModel() {
    }

    /**
     * Creates an authenticated dashboard model.
     * @param folderPath Folder path in the form: folder1/folder2/folder3
     * @param dashboard Dashboard specification.
     * @param authenticatedDashboardUrl Authenticated dashboard url.
     */
    public AuthenticatedDashboardModel(String folderPath, DashboardSpec dashboard, String authenticatedDashboardUrl) {
        this.folderPath = folderPath;
        this.dashboard = dashboard;
        this.authenticatedDashboardUrl = authenticatedDashboardUrl;
    }

    /**
     * Returns the folder path.
     * @return Folder path.
     */
    public String getFolderPath() {
        return folderPath;
    }

    /**
     * Set the folder path.
     * @param folderPath Folder path.
     */
    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    /**
     * Returns the dashboard description.
     * @return Dashboard specification.
     */
    public DashboardSpec getDashboard() {
        return dashboard;
    }

    /**
     * Sets the dashboard specification.
     * @param dashboard Dashboard specification.
     */
    public void setDashboard(DashboardSpec dashboard) {
        this.dashboard = dashboard;
    }

    /**
     * Returns an authenticated dashboard url.
     * @return Authenticated dashboard url.
     */
    public String getAuthenticatedDashboardUrl() {
        return authenticatedDashboardUrl;
    }

    /**
     * Sets an authenticated dashboard url.
     * @param authenticatedDashboardUrl Authenticated dashboard url.
     */
    public void setAuthenticatedDashboardUrl(String authenticatedDashboardUrl) {
        this.authenticatedDashboardUrl = authenticatedDashboardUrl;
    }
}
