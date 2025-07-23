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

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for DQOps that configures the default cron schedules applied to new connections. Properties are mapped to the "dqo.scheduler.default-schedules." prefix.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.scheduler.default-schedules")
@EqualsAndHashCode(callSuper = false)
@Data
public class DqoSchedulerDefaultSchedulesConfigurationProperties implements Cloneable {
    /**
     * The default cron scheduling expression for running profiling checks.
     */
    private String profiling;

    /**
     * The default cron scheduling expression for running daily monitoring checks.
     */
    private String monitoringDaily;

    /**
     * The default cron scheduling expression for running monthly monitoring checks.
     */
    private String monitoringMonthly;

    /**
     * The default cron scheduling expression for running daily partitioned checks.
     */
    private String partitionedDaily;

    /**
     * The default cron scheduling expression for running monthly partitioned checks.
     */
    private String partitionedMonthly;


    @Override
    public DqoSchedulerDefaultSchedulesConfigurationProperties clone() {
        try {
            return (DqoSchedulerDefaultSchedulesConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
