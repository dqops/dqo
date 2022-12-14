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
package ai.dqo.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for dqo.ai. Properties are mapped to the "dqo.cloud." prefix that are responsible for the configuration of the DQO Cloud.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.cloud")
@EqualsAndHashCode(callSuper = false)
public class DqoCloudConfigurationProperties implements Cloneable {
    private String apiKey;
    private String apiKeyRequestUrl;
    private String uiBaseUrl;
    private String restApiBaseUrl;
    private int apiKeyPickupTimeoutSeconds = 10 * 60;
    private int apiKeyPickupRetryDelayMillis = 1000;

    /**
     * Returns the DQO Cloud API Key that was configured in an environment variable or in a configuration file.
     * When the key is missing, then an API key from the settings is used.
     * @return DQO Api key.
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Sets the DQO Cloud api key.
     * @param apiKey API key.
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * DQO Cloud login url used to obtain the API Key.
     * @return Cloud login url.
     */
    public String getApiKeyRequestUrl() {
        return apiKeyRequestUrl;
    }

    /**
     * Sets the cloud login url.
     * @param apiKeyRequestUrl New cloud login url.
     */
    public void setApiKeyRequestUrl(String apiKeyRequestUrl) {
        this.apiKeyRequestUrl = apiKeyRequestUrl;
    }

    /**
     * Returns the api key pickup timeout that the console is waiting, given in seconds.
     * @return Api key pickup timeout.
     */
    public int getApiKeyPickupTimeoutSeconds() {
        return apiKeyPickupTimeoutSeconds;
    }

    /**
     * Sets the api key pickup timeout, in seconds.
     * @param apiKeyPickupTimeoutSeconds Api key pickup timeout.
     */
    public void setApiKeyPickupTimeoutSeconds(int apiKeyPickupTimeoutSeconds) {
        this.apiKeyPickupTimeoutSeconds = apiKeyPickupTimeoutSeconds;
    }

    /**
     * Returns the number of milliseconds between retries to pick up a new api key.
     * @return Retry wait time.
     */
    public int getApiKeyPickupRetryDelayMillis() {
        return apiKeyPickupRetryDelayMillis;
    }

    /**
     * Sets the retry wait time between calls to pick up a new api key.
     * @param apiKeyPickupRetryDelayMillis Api key wait time between rest api calls to the cloud dqo.
     */
    public void setApiKeyPickupRetryDelayMillis(int apiKeyPickupRetryDelayMillis) {
        this.apiKeyPickupRetryDelayMillis = apiKeyPickupRetryDelayMillis;
    }

    /**
     * Returns the base url for the DQO Cloud UI.
     * @return Base url for the DQO Cloud UI.
     */
    public String getUiBaseUrl() {
        return uiBaseUrl;
    }

    /**
     * Sets the base url for the DQO Cloud UI.
     * @param uiBaseUrl UI base url.
     */
    public void setUiBaseUrl(String uiBaseUrl) {
        this.uiBaseUrl = uiBaseUrl;
    }

    /**
     * Returns the base url for the DQO Cloud REST API.
     * @return REST Api base url.
     */
    public String getRestApiBaseUrl() {
        return restApiBaseUrl;
    }

    /**
     * Sets the rest api base url.
     * @param apiBaseUrl Rest api base url.
     */
    public void setRestApiBaseUrl(String apiBaseUrl) {
        this.restApiBaseUrl = apiBaseUrl;
    }

    /**
     * Clones the current object.
     * @return
     */
    @Override
    public DqoCloudConfigurationProperties clone() {
        try {
            return (DqoCloudConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
