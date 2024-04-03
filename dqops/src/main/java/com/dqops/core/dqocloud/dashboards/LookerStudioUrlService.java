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
package com.dqops.core.dqocloud.dashboards;

import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.dashboards.DashboardSpec;

/**
 * Service that creates authenticated URLS for Looker studio dashboards.
 */
public interface LookerStudioUrlService {
    /**
     * Contacts the Cloud dqo server and issues a short-lived refresh token scoped to access data quality dashboards using Looker Studio.
     *
     * @param userPrincipal Calling user principal, identifies the data domain.
     * @return API key scoped for accessing dashboards for the client's credentials.
     */
    String getLookerStudioQueryApiKey(DqoUserPrincipal userPrincipal);

    /**
     * Creates an authenticated URL for a looker studio dashboard.
     *
     * @param dashboardSpec Dashboard specification.
     * @param dqoWindowLocationOrigin URL to the DQOps instance (the window.location.origin value).
     * @param userPrincipal Calling user principal, identifies the data domain.
     * @return Authenticated url to the dashboard with an appended short-lived refresh token.
     */
    String makeAuthenticatedDashboardUrl(DashboardSpec dashboardSpec, String dqoWindowLocationOrigin, DqoUserPrincipal userPrincipal);
}
