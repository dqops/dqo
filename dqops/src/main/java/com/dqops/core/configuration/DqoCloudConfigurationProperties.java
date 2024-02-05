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
package com.dqops.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for cloud.dqops.com. Properties are mapped to the "dqo.cloud." prefix that are responsible for the configuration of the DQOps Cloud.
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
    private int parallelFileUploads = 500;
    private int parallelFileDownloads = 500;
    private int fileSynchronizationTimeLimitSeconds = 1200;
    private int maxRetries = 5;
    private long retryBackoffMillis = 10;
    private boolean startWithoutApiKey;
    private boolean authenticateWithDqoCloud;
    private long idleTimeoutSeconds = 30;

    /**
     * Returns the DQOps Cloud API Key that was configured in an environment variable or in a configuration file.
     * When the key is missing, then an API key from the settings is used.
     * @return DQOps Cloud Api key.
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Sets the DQOps Cloud api key.
     * @param apiKey API key.
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * DQOps Cloud login url used to obtain the API Key.
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
     * Returns the base url for the DQOps Cloud UI.
     * @return Base url for the DQOps Cloud UI.
     */
    public String getUiBaseUrl() {
        return uiBaseUrl;
    }

    /**
     * Sets the base url for the DQOps Cloud UI.
     * @param uiBaseUrl UI base url.
     */
    public void setUiBaseUrl(String uiBaseUrl) {
        this.uiBaseUrl = uiBaseUrl;
    }

    /**
     * Returns the base url for the DQOps Cloud REST API.
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
     * Returns the number of files that are uploaded to DQOps Cloud in parallel using HTTP/2 multiplexing.
     * @return Number of files that are uploaded to DQOps Cloud in parallel.
     */
    public int getParallelFileUploads() {
        return parallelFileUploads;
    }

    /**
     * Sets the number of files that are uploaded to DQOps Cloud in parallel using HTTP/2 multiplexing.
     * @param parallelFileUploads New number of files that are uploaded to DQOps Cloud in parallel.
     */
    public void setParallelFileUploads(int parallelFileUploads) {
        this.parallelFileUploads = parallelFileUploads;
    }

    /**
     * Returns the number of files that are downloaded from DQOps Cloud in parallel using HTTP/2 multiplexing.
     * @return Number of files that are downloaded from DQOps Cloud in parallel.
     */
    public int getParallelFileDownloads() {
        return parallelFileDownloads;
    }

    /**
     * Sets the number of files that are downloaded from DQOps Cloud in parallel using HTTP/2 multiplexing.
     * @param parallelFileDownloads New number of files that are downloaded from DQOps Cloud in parallel.
     */
    public void setParallelFileDownloads(int parallelFileDownloads) {
        this.parallelFileDownloads = parallelFileDownloads;
    }

    /**
     * Returns the time limit in seconds for a file synchronization operation (upload of a folder or download a folder).
     * @return Time limit in seconds.
     */
    public int getFileSynchronizationTimeLimitSeconds() {
        return fileSynchronizationTimeLimitSeconds;
    }

    /**
     * Sets the time limit in seconds for a file synchronization (download one folder or upload one folder).
     * @param fileSynchronizationTimeLimitSeconds Time limit in seconds.
     */
    public void setFileSynchronizationTimeLimitSeconds(int fileSynchronizationTimeLimitSeconds) {
        this.fileSynchronizationTimeLimitSeconds = fileSynchronizationTimeLimitSeconds;
    }

    /**
     * Returns the number of retries during file upload or download to the DQOps cloud.
     * @return Max number of retries.
     */
    public int getMaxRetries() {
        return maxRetries;
    }

    /**
     * Sets the number of retries during file upload or download to the DQOps cloud.
     * @param maxRetries Maximum number of retries.
     */
    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    /**
     * Returns the initial delay for the first retry of uploading or downloading files to the DQOps Cloud, specified in milliseconds.
     * @return The initial delay in milliseconds for the first retry.
     */
    public long getRetryBackoffMillis() {
        return retryBackoffMillis;
    }

    /**
     * Sets the initial delay for the first retry of uploading or downloading files to the DQOps Cloud, specified in milliseconds.
     * @param retryBackoffMillis The initial delay in milliseconds for the first retry.
     */
    public void setRetryBackoffMillis(long retryBackoffMillis) {
        this.retryBackoffMillis = retryBackoffMillis;
    }

    /**
     * True when it is allowed to start DQOps without a DQOps Cloud api key.
     * @return True when dqo can start without an api key.
     */
    public boolean isStartWithoutApiKey() {
        return startWithoutApiKey;
    }

    /**
     * Sets a flag to enable starting DQOps without an API key.
     * @param startWithoutApiKey Start DQOps without an api key.
     */
    public void setStartWithoutApiKey(boolean startWithoutApiKey) {
        this.startWithoutApiKey = startWithoutApiKey;
    }

    /**
     * Enables user authentication using DQOps Cloud credentials.
     * @return Authenticate users with DQOps Cloud.
     */
    public boolean isAuthenticateWithDqoCloud() {
        return authenticateWithDqoCloud;
    }

    /**
     * Sets the flag to enable user authentication with DQOps Cloud credentials.
     * @param authenticateWithDqoCloud Authenticate users with DQOps Cloud credentials.
     */
    public void setAuthenticateWithDqoCloud(boolean authenticateWithDqoCloud) {
        this.authenticateWithDqoCloud = authenticateWithDqoCloud;
    }

    /**
     * Clones the current object.
     * @return Cloned instance.
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

    /**
     * Returns the timeout of idle connections to DQOps cloud that are closed.
     * @return Idle connection timeout in seconds.
     */
    public long getIdleTimeoutSeconds() {
        return idleTimeoutSeconds;
    }

    /**
     * Sets the timeout when idle connections are closed.
     * @param idleTimeoutSeconds Idle connections timeout.
     */
    public void setIdleTimeoutSeconds(long idleTimeoutSeconds) {
        this.idleTimeoutSeconds = idleTimeoutSeconds;
    }
}
