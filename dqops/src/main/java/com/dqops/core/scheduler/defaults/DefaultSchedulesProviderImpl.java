/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.scheduler.defaults;

import com.dqops.core.configuration.DqoSchedulerDefaultSchedulesConfigurationProperties;
import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.dqops.metadata.scheduling.CronSchedulesSpec;
import com.dqops.metadata.scheduling.MonitoringSchedulesWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Retrieves and creates the default configuration of schedules for new connections.
 */
@Component
public class DefaultSchedulesProviderImpl implements DefaultSchedulesProvider {
    private DqoSchedulerDefaultSchedulesConfigurationProperties defaultSchedulesConfigurationProperties;
    private UserHomeContextFactory userHomeContextFactory;

    /**
     * Dependency injection container.
     * @param defaultSchedulesConfigurationProperties Default schedules configuration.
     * @param userHomeContextFactory User home context factory, used to read the current configuration.
     */
    @Autowired
    public DefaultSchedulesProviderImpl(DqoSchedulerDefaultSchedulesConfigurationProperties defaultSchedulesConfigurationProperties,
                                        UserHomeContextFactory userHomeContextFactory) {
        this.defaultSchedulesConfigurationProperties = defaultSchedulesConfigurationProperties;
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Creates the schedules specification object with the default values of schedules. Returns null when no schedules are configured.
     * @return Schedules configuration with the default schedules (only configured ones) or null when no schedules are configured.
     */
    @Override
    public CronSchedulesSpec createDefaultSchedules() {
        CronSchedulesSpec schedules = new CronSchedulesSpec();

        if (!Strings.isNullOrEmpty(this.defaultSchedulesConfigurationProperties.getProfiling())) {
            schedules.setProfiling(new CronScheduleSpec(this.defaultSchedulesConfigurationProperties.getProfiling()));
        }

        if (!Strings.isNullOrEmpty(this.defaultSchedulesConfigurationProperties.getMonitoringDaily())) {
            schedules.setMonitoringDaily(new CronScheduleSpec(this.defaultSchedulesConfigurationProperties.getMonitoringDaily()));
        }

        if (!Strings.isNullOrEmpty(this.defaultSchedulesConfigurationProperties.getMonitoringMonthly())) {
            schedules.setMonitoringMonthly(new CronScheduleSpec(this.defaultSchedulesConfigurationProperties.getMonitoringMonthly()));
        }

        if (!Strings.isNullOrEmpty(this.defaultSchedulesConfigurationProperties.getPartitionedDaily())) {
            schedules.setPartitionedDaily(new CronScheduleSpec(this.defaultSchedulesConfigurationProperties.getPartitionedDaily()));
        }

        if (!Strings.isNullOrEmpty(this.defaultSchedulesConfigurationProperties.getPartitionedMonthly())) {
            schedules.setPartitionedMonthly(new CronScheduleSpec(this.defaultSchedulesConfigurationProperties.getPartitionedMonthly()));
        }

        if (schedules.isDefault()) {
            return null;
        }

        return schedules;
    }

    /**
     * Creates a new monitoring schedule configuration for a new connection.
     * @return New monitoring schedule configuration for a new connection.
     */
    @Override
    public CronSchedulesSpec createMonitoringSchedulesSpecForNewConnection(UserHome userHome) {
        MonitoringSchedulesWrapper schedulesWrapper = userHome.getDefaultSchedules();
        if (schedulesWrapper == null || schedulesWrapper.getSpec() == null) {
            return createDefaultSchedules();
        }

        if (schedulesWrapper.getSpec() == null) {
            return null;
        }

        CronSchedulesSpec defaultSchedules = schedulesWrapper.getSpec();
        CronSchedulesSpec clonedSchedules = defaultSchedules.deepClone();

        return clonedSchedules;
    }
}
