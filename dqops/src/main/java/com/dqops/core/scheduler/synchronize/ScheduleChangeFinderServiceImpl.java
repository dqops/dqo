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
package com.dqops.core.scheduler.synchronize;

import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.scheduler.schedules.UniqueSchedulesCollection;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.search.CronScheduleSearchFilters;
import com.dqops.metadata.settings.instancename.InstanceNameProvider;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Schedule detection service that scans the metadata and compares the current list of schedules used by the scheduler
 * with the current schedules. Removes outdated schedules.
 */
@Component
public class ScheduleChangeFinderServiceImpl implements ScheduleChangeFinderService {
    private HierarchyNodeTreeSearcher nodeTreeSearcher;
    private UserHomeContextFactory userHomeContextFactory;
    private SecretValueProvider secretValueProvider;
    private DqoUserPrincipalProvider dqoUserPrincipalProvider;
    private InstanceNameProvider instanceNameProvider;

    @Autowired
    public ScheduleChangeFinderServiceImpl(
            HierarchyNodeTreeSearcher nodeTreeSearcher,
            UserHomeContextFactory userHomeContextFactory,
            SecretValueProvider secretValueProvider,
            DqoUserPrincipalProvider dqoUserPrincipalProvider,
            InstanceNameProvider instanceNameProvider) {
        this.nodeTreeSearcher = nodeTreeSearcher;
        this.userHomeContextFactory = userHomeContextFactory;
        this.secretValueProvider = secretValueProvider;
        this.dqoUserPrincipalProvider = dqoUserPrincipalProvider;
        this.instanceNameProvider = instanceNameProvider;
    }

    /**
     * Loads (finds) all current schedules that are specified in the metadata.
     * @param dataDomainName Data domain name.
     * @param ignoreChecks Ignore schedules on checks.
     * @param scheduleGroup Schedule group.
     * @return All unique schedules to run data quality checks.
     */
    public UniqueSchedulesCollection loadCurrentSchedules(
            String dataDomainName,
            boolean ignoreChecks,
            CheckRunScheduleGroup scheduleGroup) {
        DqoUserPrincipal userPrincipal = this.dqoUserPrincipalProvider.createLocalDomainAdminPrincipal(dataDomainName);
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), true);
        UserHome userHome = userHomeContext.getUserHome();
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(userHome);

        CronScheduleSearchFilters cronScheduleSearchFilters = new CronScheduleSearchFilters();
        cronScheduleSearchFilters.setScheduleEnabled(true);
        cronScheduleSearchFilters.setIgnoreChecks(ignoreChecks);
        cronScheduleSearchFilters.setScheduleGroup(scheduleGroup);
        cronScheduleSearchFilters.setLocalInstanceName(this.instanceNameProvider.getInstanceName());
        // NOTE 1: we can add additional filters if this instance should only process schedules in one connection or matching a connection name pattern
        // NOTE 2: this code will not detect that a default observability check has a custom schedule, because the default checks are not applied
        Collection<CronScheduleSpec> schedules = this.nodeTreeSearcher.findSchedules(userHome, cronScheduleSearchFilters);

        UniqueSchedulesCollection uniqueSchedulesCollection = new UniqueSchedulesCollection();
        for (CronScheduleSpec monitoringSchedule : schedules) {
            if (Strings.isNullOrEmpty(monitoringSchedule.getCronExpression())) {
                continue;
            }

            ConnectionWrapper parentConnectionWrapper = userHome.findConnectionFor(monitoringSchedule.getHierarchyId());
            assert parentConnectionWrapper != null;

            CronScheduleSpec clonedMonitoringSchedule = monitoringSchedule.expandAndTrim(
                    this.secretValueProvider, secretValueLookupContext);
            clonedMonitoringSchedule.setHierarchyId(null);

            uniqueSchedulesCollection.add(clonedMonitoringSchedule);
        }

        return uniqueSchedulesCollection;
    }

    /**
     * Loads all schedules configured in the metadata and compares the list with the current running schedules.
     * Returns two list of schedules, those new schedules to add and outdated schedules to remove.
     * @param currentRunningSchedules Current running schedules.
     * @param dataDomainName Data domain name.
     * @return The delta - two lists of schedules, to add and to remove from the scheduler.
     */
    @Override
    public JobSchedulesDelta findRunChecksSchedulesToAddOrRemove(UniqueSchedulesCollection currentRunningSchedules, String dataDomainName) {
        assert currentRunningSchedules != null;

        UniqueSchedulesCollection currentMetadataSchedules = loadCurrentSchedules(dataDomainName, false, null);

        UniqueSchedulesCollection schedulesToAdd = currentMetadataSchedules.minus(currentRunningSchedules);
        UniqueSchedulesCollection schedulesToRemove = currentRunningSchedules.minus(currentMetadataSchedules);

        return new JobSchedulesDelta(schedulesToAdd, schedulesToRemove);
    }

    /**
     * Loads all CRON schedules for running profiling checks and compares the list with the current running schedules.
     * Returns two list of schedules, those new schedules to add and outdated schedules to remove.
     *
     * @param currentRunningSchedules Current running schedules for profiling.
     * @param dataDomainName          Data domain name.
     * @return The delta - two lists of schedules, to add and to remove from the scheduler.
     */
    @Override
    public JobSchedulesDelta findProfilingSchedulesToAddOrRemove(UniqueSchedulesCollection currentRunningSchedules, String dataDomainName) {
        assert currentRunningSchedules != null;

        UniqueSchedulesCollection currentMetadataSchedules = loadCurrentSchedules(dataDomainName, true, CheckRunScheduleGroup.profiling);

        UniqueSchedulesCollection schedulesToAdd = currentMetadataSchedules.minus(currentRunningSchedules);
        UniqueSchedulesCollection schedulesToRemove = currentRunningSchedules.minus(currentMetadataSchedules);

        return new JobSchedulesDelta(schedulesToAdd, schedulesToRemove);
    }

    /**
     * Loads all CRON schedules for importing tables (they are configured on connections) and compares the list with the current running schedules.
     * Returns two list of schedules, those new schedules to add and outdated schedules to remove.
     *
     * @param currentRunningSchedules Current running schedules for profiling.
     * @param dataDomainName          Data domain name.
     * @return The delta - two lists of schedules, to add and to remove from the scheduler.
     */
    @Override
    public JobSchedulesDelta findImportTablesSchedulesToAddOrRemove(UniqueSchedulesCollection currentRunningSchedules, String dataDomainName) {
        DqoUserPrincipal userPrincipal = this.dqoUserPrincipalProvider.createLocalDomainAdminPrincipal(dataDomainName);
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), true);
        UserHome userHome = userHomeContext.getUserHome();
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(userHome);

        UniqueSchedulesCollection currentMetadataSchedules = new UniqueSchedulesCollection();

        ConnectionList connectionList = userHome.getConnections();
        for (ConnectionWrapper connectionWrapper : connectionList) {
            ConnectionSpec connectionSpec = connectionWrapper.getSpec();
            if (connectionSpec == null || connectionSpec.getAutoImportTables() == null ||
                    connectionSpec.getAutoImportTables().getSchedule() == null ||
                    connectionSpec.getAutoImportTables().getSchedule().isDisabled()) {
                continue;
            }

            CronScheduleSpec schedule = connectionSpec.getAutoImportTables().getSchedule();
            CronScheduleSpec clonedImportSchedule = schedule.expandAndTrim(
                    this.secretValueProvider, secretValueLookupContext);
            clonedImportSchedule.setHierarchyId(null);

            if (Strings.isNullOrEmpty(clonedImportSchedule.getCronExpression())) {
                continue;
            }

            currentMetadataSchedules.add(clonedImportSchedule);
        }

        UniqueSchedulesCollection schedulesToAdd = currentMetadataSchedules.minus(currentRunningSchedules);
        UniqueSchedulesCollection schedulesToRemove = currentRunningSchedules.minus(currentMetadataSchedules);

        return new JobSchedulesDelta(schedulesToAdd, schedulesToRemove);
    }
}
