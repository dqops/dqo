package ai.dqo.core.dqocloud.dashboards;

import ai.dqo.cloud.rest.api.LookerStudioKeyRequestApi;
import ai.dqo.cloud.rest.handler.ApiClient;
import ai.dqo.core.dqocloud.client.DqoCloudApiClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Service that creates authenticated URLS for Looker studio dashboards.
 */
@Service
public class LookerStudioUrlServiceImpl implements LookerStudioUrlService {
    private final DqoCloudApiClientFactory dqoCloudApiClientFactory;

    /**
     * Dependency injection constructor that receives all required dependencies.
     * @param dqoCloudApiClientFactory Creates a rest api client for contacting the cloud.dqo.ai using the API key.
     */
    @Autowired
    public LookerStudioUrlServiceImpl(DqoCloudApiClientFactory dqoCloudApiClientFactory) {
        this.dqoCloudApiClientFactory = dqoCloudApiClientFactory;
    }

    /**
     * Contacts the Cloud dqo server and issues a short-lived refresh token scoped to access data quality dashboards using Looker Studio.
     * @return API key scoped for accessing dashboards for the client's credentials.
     */
    @Override
    public String issueLookerStudioQueryApiKey() {
        ApiClient authenticatedClient = this.dqoCloudApiClientFactory.createAuthenticatedClient();
        LookerStudioKeyRequestApi lookerStudioKeyRequestApi = new LookerStudioKeyRequestApi(authenticatedClient);
        String queryApiKey = lookerStudioKeyRequestApi.issueLookerStudioApiKey();

        return queryApiKey;
    }

    /**
     * Creates an authenticated URL for a looker studio dashboard.
     * @param unauthenticatedDashboardUrl URL to the Looker Studio dashboard.
     * @return Authenticated url to the dashboard with an appended short-lived refresh token.
     */
    @Override
    public String makeAuthenticatedDashboardUrl(String unauthenticatedDashboardUrl) {
        String refreshToken = this.issueLookerStudioQueryApiKey();

        StringBuilder stringBuilder = new StringBuilder();
        String jsonParameters = String.format("{\"ds0.token\":\"%s\"}", refreshToken);
        String urlEncodedLookerStudioParameters = URLEncoder.encode(jsonParameters, StandardCharsets.UTF_8);
        stringBuilder.append(unauthenticatedDashboardUrl);
        stringBuilder.append("?params=");
        stringBuilder.append(urlEncodedLookerStudioParameters);
        String authenticatedUrl = stringBuilder.toString();

        return authenticatedUrl;
    }
}
