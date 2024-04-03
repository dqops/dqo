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
