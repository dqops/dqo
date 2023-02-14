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
package ai.dqo.cli.commands.cloud.impl;

import ai.dqo.cli.terminal.TerminalFactory;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.cloud.rest.api.ApiKeyRequestApi;
import ai.dqo.cloud.rest.handler.ApiClient;
import ai.dqo.core.configuration.DqoCloudConfigurationProperties;
import ai.dqo.core.dqocloud.accesskey.DqoCloudAccessTokenCache;
import ai.dqo.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import ai.dqo.core.dqocloud.client.DqoCloudApiClientFactory;
import ai.dqo.metadata.settings.SettingsSpec;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.utils.browser.OpenBrowserFailedException;
import ai.dqo.utils.browser.OpenBrowserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * Service that will open a browser and log in to the DQO cloud.
 */
@Component
@Slf4j
public class CloudLoginServiceImpl implements CloudLoginService {
    private UserHomeContextFactory userHomeContextFactory;
    private OpenBrowserService openBrowserService;
    private TerminalFactory terminalFactory;
    private DqoCloudConfigurationProperties dqoCloudConfigurationProperties;
    private DqoCloudApiClientFactory dqoCloudApiClientFactory;
    private DqoCloudAccessTokenCache dqoCloudAccessTokenCache;

    /**
     * Injection constructor.
     * @param userHomeContextFactory User home context factory - to store the api key in the settings.
     * @param openBrowserService Open browser service.
     * @param terminalFactory Terminal factory.
     * @param dqoCloudConfigurationProperties Configuration properties.
     * @param dqoCloudApiClientFactory DQO Cloud API client factory.
     * @param dqoCloudAccessTokenCache DQO Cloud access key cache which must be invalidated when the api key changes.
     */
    @Autowired
    public CloudLoginServiceImpl(UserHomeContextFactory userHomeContextFactory,
                                 OpenBrowserService openBrowserService,
                                 TerminalFactory terminalFactory,
                                 DqoCloudConfigurationProperties dqoCloudConfigurationProperties,
                                 DqoCloudApiClientFactory dqoCloudApiClientFactory,
                                 DqoCloudAccessTokenCache dqoCloudAccessTokenCache) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.openBrowserService = openBrowserService;
        this.terminalFactory = terminalFactory;
        this.dqoCloudConfigurationProperties = dqoCloudConfigurationProperties;
        this.dqoCloudApiClientFactory = dqoCloudApiClientFactory;
        this.dqoCloudAccessTokenCache = dqoCloudAccessTokenCache;
    }

    /**
     * Requests an API key generation, opens a browser and waits for the API key.
     * @return True when the api key was retrieved, false when there was an error.
     */
    public boolean logInToDqoCloud() {
        try {
            ApiClient apiClient = this.dqoCloudApiClientFactory.createUnauthenticatedClient();
            ApiKeyRequestApi apiKeyRequestApi = new ApiKeyRequestApi(apiClient);
            Random random = new Random();
            String challenge = Integer.toString(Math.abs(random.nextInt()));
            String apiKeyRequest = apiKeyRequestApi.requestApiKeyVersion(DqoCloudApiKeyPayload.CURRENT_API_KEY_VERSION, challenge);
            String apiKeyRequestUrl = this.dqoCloudConfigurationProperties.getApiKeyRequestUrl() + apiKeyRequest;

            TerminalWriter terminalWriter = this.terminalFactory.getWriter();
            terminalWriter.writeLine("Opening the DQO Cloud API Key request, please log in or create your DQO Cloud account.");
            terminalWriter.writeLine("DQO Cloud API Key request may be opened manually by navigating to: " + apiKeyRequestUrl);
            terminalWriter.writeLine("Please wait up to 30 seconds after signup/login or press any key to cancel");

            try {
                this.openBrowserService.openUrlInBrowser(apiKeyRequestUrl);
            }
            catch (OpenBrowserFailedException oex) {
                // show the message to the user, they will know that they should open the browser
                terminalWriter.writeLine("The login url cannot be opened in your browser, message: " + oex.getMessage());
            }

            Duration waitDuration = Duration.of(this.dqoCloudConfigurationProperties.getApiKeyPickupTimeoutSeconds(), ChronoUnit.SECONDS);
            Instant startTime = Instant.now();
            Instant timeoutTime = startTime.plus(waitDuration);
            CompletableFuture<Boolean> waitForConsoleInputMono = this.terminalFactory.getReader().waitForConsoleInput(waitDuration);

            // now waiting for the api key...
            while (Instant.now().isBefore(timeoutTime) && !waitForConsoleInputMono.isDone()) {
                try {
                    String apiKey = apiKeyRequestApi.pickApiKey(apiKeyRequest);

                    saveApiKeyInUserSettings(apiKey);

                    terminalWriter.writeLine("API Key: " + apiKey);
                    terminalWriter.writeLine("DQO Cloud API Key was retrieved and stored in the settings.");
                    waitForConsoleInputMono.cancel(true);
                    return true;
                }
                catch (RestClientException ex) {
                    // ignore... it is probably the not found error, because the api key was not yet issued
                    log.debug("API key pickup error: " + ex.getMessage(), ex);
                }

                Thread.sleep(this.dqoCloudConfigurationProperties.getApiKeyPickupRetryDelayMillis());
            }

            if (waitForConsoleInputMono.isDone() && Objects.equals(true, waitForConsoleInputMono.get())) {
                this.terminalFactory.getReader().tryReadChar(0); // read that character that was typed
                terminalWriter.writeLine("API Key retrieval cancelled, run the \"cloud login\" command again from the shell.");
            }
        }
        catch (Exception ex) {
            this.terminalFactory.getWriter().writeLine("Failed to retrieve the API key. " + ex.getMessage());
        }

        return false;
    }

    /**
     * Saves the api key in the user settings.
     * @param apiKey API key to store.
     */
    public void saveApiKeyInUserSettings(String apiKey) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        SettingsSpec settingsSpec = userHome.getSettings().getSpec();
        if (settingsSpec == null) {
            settingsSpec = new SettingsSpec();
            userHome.getSettings().setSpec(settingsSpec);
        }
        settingsSpec.setApiKey(apiKey);
        userHomeContext.flush();

        this.dqoCloudAccessTokenCache.invalidate();
    }
}
