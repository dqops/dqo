/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for DQOps related to calling external services.
 * Properties are mapped to the "dqo.notifications.rest." prefix that are responsible for the configuration of the notification queue (REST API caller).
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.notifications.rest")
@EqualsAndHashCode(callSuper = false)
public class DqoNotificationsRestConfigurationProperties implements Cloneable {
    private int publishBusyLoopingDurationSeconds = 30;
    private int maxRetries = 10;
    private int maxParallelCalls = 4;
    private long responseTimeoutSeconds = 120;

    /**
     * Returns the duration of busy looping to wait until the sink can accept a notification.
     * @return Return the duration of busy looping to wait until the sink can accept a notification.
     */
    public int getPublishBusyLoopingDurationSeconds() {
        return publishBusyLoopingDurationSeconds;
    }

    /**
     * Sets the duration of busy looping to wait until the sink can accept a notification.
     * @param publishBusyLoopingDurationSeconds The duration of busy looping to wait until the sink can accept a notification.
     */
    public void setPublishBusyLoopingDurationSeconds(int publishBusyLoopingDurationSeconds) {
        this.publishBusyLoopingDurationSeconds = publishBusyLoopingDurationSeconds;
    }

    /**
     * The maximum number of retries when the call fails.
     * @return The limit of retries.
     */
    public int getMaxRetries() {
        return maxRetries;
    }

    /**
     * The maximum number of retries.
     * @param maxRetries Maximum number of retries.
     */
    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    /**
     * Returns the maximum number of parallel rest api calls.
     * @return Maximum number of parallel calls.
     */
    public int getMaxParallelCalls() {
        return maxParallelCalls;
    }

    /**
     * Sets the maximum number of parallel calls.
     * @param maxParallelCalls New maximum number of parallel calls.
     */
    public void setMaxParallelCalls(int maxParallelCalls) {
        this.maxParallelCalls = maxParallelCalls;
    }

    /**
     * Returns the target's server response timeout in seconds.
     * @return Response timeout in seconds.
     */
    public long getResponseTimeoutSeconds() {
        return responseTimeoutSeconds;
    }

    /**
     * Sets the target server response timeout in seconds.
     * @param responseTimeoutSeconds New response timeout.
     */
    public void setResponseTimeoutSeconds(long responseTimeoutSeconds) {
        this.responseTimeoutSeconds = responseTimeoutSeconds;
    }

    /**
     * Clones the current object.
     * @return
     */
    @Override
    public DqoNotificationsRestConfigurationProperties clone() {
        try {
            return (DqoNotificationsRestConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
