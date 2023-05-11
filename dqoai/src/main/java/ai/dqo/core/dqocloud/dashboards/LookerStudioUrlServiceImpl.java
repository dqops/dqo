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
package ai.dqo.core.dqocloud.dashboards;

import ai.dqo.cloud.rest.api.LookerStudioKeyRequestApi;
import ai.dqo.cloud.rest.handler.ApiClient;
import ai.dqo.core.dqocloud.client.DqoCloudApiClientFactory;
import ai.dqo.metadata.dashboards.DashboardSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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
     * @param dashboardSpec Dashboard specification.
     * @return Authenticated url to the dashboard with an appended short-lived refresh token.
     */
    @Override
    public String makeAuthenticatedDashboardUrl(DashboardSpec dashboardSpec) {
        String refreshToken = this.issueLookerStudioQueryApiKey();

        StringBuilder stringBuilder = new StringBuilder();
        String jsonParameters = formatDashboardParameters(refreshToken, dashboardSpec.getParameters());
        String urlEncodedLookerStudioParameters = URLEncoder.encode(jsonParameters, StandardCharsets.UTF_8);
        stringBuilder.append(dashboardSpec.getUrl());
        stringBuilder.append("?params=");
        stringBuilder.append(urlEncodedLookerStudioParameters);
        String authenticatedUrl = stringBuilder.toString();

        return authenticatedUrl;
    }

    /**
     * Generates the Looker Studio JSON parameter string.
     * @param refreshToken Refresh token.
     * @param parameters Optional dictionary of key/value parameter pairs.
     * @return JSON string to be submitted as a parameter.
     */
    public String formatDashboardParameters(String refreshToken, Map<String, String> parameters) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append('"');
        stringBuilder.append("ds0.token");
        stringBuilder.append('"');
        stringBuilder.append(':');
        stringBuilder.append('"');
        stringBuilder.append(refreshToken);
        stringBuilder.append('"');

        if (parameters != null && parameters.size() > 0) {
            for (Map.Entry<String, String> paramValuePair : parameters.entrySet()) {
                stringBuilder.append(',');
                stringBuilder.append('"');
                stringBuilder.append(paramValuePair.getKey());
                stringBuilder.append('"');
                stringBuilder.append(':');
                stringBuilder.append('"');
                stringBuilder.append(paramValuePair.getValue());
                stringBuilder.append('"');
            }
        }
        
        stringBuilder.append("}");

        return stringBuilder.toString();
    }
}
