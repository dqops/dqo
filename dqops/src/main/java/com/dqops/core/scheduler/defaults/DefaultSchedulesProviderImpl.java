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

package com.dqops.core.scheduler.defaults;

import com.dqops.core.configuration.DqoSchedulerDefaultSchedulesConfigurationProperties;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.dqops.metadata.scheduling.DefaultSchedulesSpec;
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
    public DefaultSchedulesSpec createDefaultSchedules() {
        DefaultSchedulesSpec schedules = new DefaultSchedulesSpec();

        if (!Strings.isNullOrEmpty(this.defaultSchedulesConfigurationProperties.getProfiling())) {
            schedules.setProfiling(new MonitoringScheduleSpec(this.defaultSchedulesConfigurationProperties.getProfiling()));
        }

        if (!Strings.isNullOrEmpty(this.defaultSchedulesConfigurationProperties.getMonitoringDaily())) {
            schedules.setMonitoringDaily(new MonitoringScheduleSpec(this.defaultSchedulesConfigurationProperties.getMonitoringDaily()));
        }

        if (!Strings.isNullOrEmpty(this.defaultSchedulesConfigurationProperties.getMonitoringMonthly())) {
            schedules.setMonitoringMonthly(new MonitoringScheduleSpec(this.defaultSchedulesConfigurationProperties.getMonitoringMonthly()));
        }

        if (!Strings.isNullOrEmpty(this.defaultSchedulesConfigurationProperties.getPartitionedDaily())) {
            schedules.setPartitionedDaily(new MonitoringScheduleSpec(this.defaultSchedulesConfigurationProperties.getPartitionedDaily()));
        }

        if (!Strings.isNullOrEmpty(this.defaultSchedulesConfigurationProperties.getPartitionedMonthly())) {
            schedules.setPartitionedMonthly(new MonitoringScheduleSpec(this.defaultSchedulesConfigurationProperties.getPartitionedMonthly()));
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
    public DefaultSchedulesSpec createMonitoringSchedulesSpecForNewConnection(UserHome userHome) {
        MonitoringSchedulesWrapper schedulesWrapper = userHome.getDefaultSchedules();
        if (schedulesWrapper == null || schedulesWrapper.getSpec() == null) {
            return createDefaultSchedules();
        }

        if (schedulesWrapper.getSpec() == null) {
            return null;
        }

        DefaultSchedulesSpec defaultSchedules = schedulesWrapper.getSpec();
        DefaultSchedulesSpec clonedSchedules = defaultSchedules.deepClone();

        return clonedSchedules;
    }
}
