/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
