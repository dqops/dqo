package ai.dqo.core.dqocloud.dashboards;

import ai.dqo.metadata.dashboards.DashboardSpec;

/**
 * Service that creates authenticated URLS for Looker studio dashboards.
 */
public interface LookerStudioUrlService {
    /**
     * Contacts the Cloud dqo server and issues a short-lived refresh token scoped to access data quality dashboards using Looker Studio.
     *
     * @return API key scoped for accessing dashboards for the client's credentials.
     */
    String issueLookerStudioQueryApiKey();

    /**
     * Creates an authenticated URL for a looker studio dashboard.
     *
     * @param dashboardSpec Dashboard specification.
     * @return Authenticated url to the dashboard with an appended short-lived refresh token.
     */
    String makeAuthenticatedDashboardUrl(DashboardSpec dashboardSpec);
}
