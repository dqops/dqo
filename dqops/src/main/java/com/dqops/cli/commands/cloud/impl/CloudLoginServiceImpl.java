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
package com.dqops.cli.commands.cloud.impl;

import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.cloud.rest.api.ApiKeyRequestApi;
import com.dqops.cloud.rest.handler.ApiClient;
import com.dqops.core.configuration.DqoCloudConfigurationProperties;
import com.dqops.core.dqocloud.accesskey.DqoCloudAccessTokenCache;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.dqops.core.dqocloud.client.DqoCloudApiClientFactory;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.metadata.settings.LocalSettingsSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.utils.browser.OpenBrowserFailedException;
import com.dqops.utils.browser.OpenBrowserService;
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
 * Service that will open a browser and log in to the DQOps cloud.
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
    private DqoCloudApiKeyProvider dqoCloudApiKeyProvider;
    private DqoUserPrincipalProvider userPrincipalProvider;

    /**
     * Injection constructor.
     * @param userHomeContextFactory User home context factory - to store the api key in the settings.
     * @param openBrowserService Open browser service.
     * @param terminalFactory Terminal factory.
     * @param dqoCloudConfigurationProperties Configuration properties.
     * @param dqoCloudApiClientFactory DQOps Cloud API client factory.
     * @param dqoCloudAccessTokenCache DQOps Cloud access key cache which must be invalidated when the api key changes.
     * @param dqoCloudApiKeyProvider DQOps Cloud API key provider (cache) that must be invalidated.
     * @param userPrincipalProvider User principal provider for the local user.
     */
    @Autowired
    public CloudLoginServiceImpl(UserHomeContextFactory userHomeContextFactory,
                                 OpenBrowserService openBrowserService,
                                 TerminalFactory terminalFactory,
                                 DqoCloudConfigurationProperties dqoCloudConfigurationProperties,
                                 DqoCloudApiClientFactory dqoCloudApiClientFactory,
                                 DqoCloudAccessTokenCache dqoCloudAccessTokenCache,
                                 DqoCloudApiKeyProvider dqoCloudApiKeyProvider,
                                 DqoUserPrincipalProvider userPrincipalProvider) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.openBrowserService = openBrowserService;
        this.terminalFactory = terminalFactory;
        this.dqoCloudConfigurationProperties = dqoCloudConfigurationProperties;
        this.dqoCloudApiClientFactory = dqoCloudApiClientFactory;
        this.dqoCloudAccessTokenCache = dqoCloudAccessTokenCache;
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
        this.userPrincipalProvider = userPrincipalProvider;
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
            terminalWriter.writeLine("Opening the DQOps Cloud API Key request, please log in or create your DQOps Cloud account.");
            terminalWriter.writeLine("DQOps Cloud API Key request may be opened manually by navigating to: " + apiKeyRequestUrl);
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
            CompletableFuture<Boolean> waitForConsoleInputMono = this.terminalFactory.getReader().waitForConsoleInput(waitDuration, false);

            // now waiting for the api key...
            while (Instant.now().isBefore(timeoutTime) && !waitForConsoleInputMono.isDone()) {
                try {
                    String apiKey = apiKeyRequestApi.pickApiKey(apiKeyRequest);

                    saveApiKeyInUserSettings(apiKey);

                    terminalWriter.writeLine("API Key: " + apiKey);
                    terminalWriter.writeLine("DQOps Cloud API Key was retrieved and stored in the settings.");
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
        DqoUserPrincipal userPrincipal = this.userPrincipalProvider.getLocalUserPrincipal();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity());
        UserHome userHome = userHomeContext.getUserHome();
        LocalSettingsSpec localSettingsSpec = userHome.getSettings().getSpec();
        if (localSettingsSpec == null) {
            localSettingsSpec = new LocalSettingsSpec();
            userHome.getSettings().setSpec(localSettingsSpec);
        }
        localSettingsSpec.setApiKey(apiKey);
        userHomeContext.flush();

        this.dqoCloudApiKeyProvider.invalidate();
        this.dqoCloudAccessTokenCache.invalidate();
    }
}
