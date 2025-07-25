/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.scheduler;

import com.dqops.core.configuration.DqoSchedulerConfigurationProperties;
import com.dqops.core.domains.LocalDataDomainRegistry;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.dqops.core.dqocloud.apikey.DqoCloudLicenseType;
import com.dqops.core.jobqueue.DqoJobQueue;
import com.dqops.core.jobqueue.ParentDqoJobQueue;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.scheduler.collectstatistics.CollectScheduledStatisticsSchedulerJob;
import com.dqops.core.scheduler.importtables.AutoImportTablesSchedulerJob;
import com.dqops.core.scheduler.quartz.*;
import com.dqops.core.scheduler.runcheck.RunScheduledChecksSchedulerJob;
import com.dqops.core.scheduler.synchronize.JobSchedulesDelta;
import com.dqops.core.scheduler.synchronize.SynchronizeMetadataSchedulerJob;
import com.dqops.core.scheduler.schedules.UniqueSchedulesCollection;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
import com.dqops.execution.checks.progress.CheckRunReportingMode;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionReportingMode;
import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.dqops.metadata.settings.domains.LocalDataDomainSpec;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.quartz.JobBuilder.newJob;


/**
 * Job scheduling root class that manages an instance of a Quartz scheduler.
 */
@Component
@Slf4j
public class JobSchedulerServiceImpl implements JobSchedulerService {
    private boolean started;
    private boolean shutdownInitiated;
    private Scheduler scheduler;
    private DqoSchedulerConfigurationProperties schedulerConfigurationProperties;
    private SchedulerFactoryBean schedulerFactory;
    private SpringIoCJobFactory jobFactory;
    private TriggerFactory triggerFactory;
    private JobDataMapAdapter jobDataMapAdapter;
    private ScheduledJobListener scheduledJobListener;
    private DqoJobQueue dqoJobQueue;
    private ParentDqoJobQueue parentDqoJobQueue;
    private DefaultTimeZoneProvider defaultTimeZoneProvider;
    private DqoCloudApiKeyProvider dqoCloudApiKeyProvider;
    private DqoUserPrincipalProvider principalProvider;
    private LocalDataDomainRegistry localDataDomainRegistry;
    private JobDetail runChecksJob;
    private JobDetail synchronizeMetadataJob;
    private JobDetail collectStatisticsJob;
    private JobDetail importTablesJob;
    private FileSystemSynchronizationReportingMode synchronizationMode = FileSystemSynchronizationReportingMode.silent;
    private CheckRunReportingMode checkRunReportingMode = CheckRunReportingMode.silent;
    private StatisticsCollectorExecutionReportingMode collectStatisticsReportingMode = StatisticsCollectorExecutionReportingMode.silent;
    private Set<String> scheduledDataDomains = ConcurrentHashMap.newKeySet();

    /**
     * Job scheduler service constructor.
     * @param schedulerConfigurationProperties Scheduler configuration properties.
     * @param schedulerFactory Quartz scheduler factory (new).
     * @param jobFactory Custom job factory that uses Spring IoC for instantiating job instances.
     * @param triggerFactory Trigger factory that creates quartz triggers for the schedules defined in the metadata.
     * @param jobDataMapAdapter Job data adapter that can retrieve the original schedule from the trigger arguments.
     * @param scheduledJobListener  Job listener that is notified when a job starts or finishes.
     * @param dqoJobQueue Standard job queue, used to ensure that the job queue starts before the job scheduler.
     * @param parentDqoJobQueue Parent job queue, used to ensure that the job queue starts before the job scheduler.
     * @param defaultTimeZoneProvider Default time zone provider.
     * @param dqoCloudApiKeyProvider DQOps Cloud api key provider.
     * @param principalProvider Local user principal provider.
     * @param localDataDomainRegistry Local data domain registry.
     */
    @Autowired
    public JobSchedulerServiceImpl(DqoSchedulerConfigurationProperties schedulerConfigurationProperties,
                                   SchedulerFactoryBean schedulerFactory,
                                   SpringIoCJobFactory jobFactory,
                                   TriggerFactory triggerFactory,
                                   JobDataMapAdapter jobDataMapAdapter,
                                   ScheduledJobListener scheduledJobListener,
                                   DqoJobQueue dqoJobQueue,
                                   ParentDqoJobQueue parentDqoJobQueue,
                                   DefaultTimeZoneProvider defaultTimeZoneProvider,
                                   DqoCloudApiKeyProvider dqoCloudApiKeyProvider,
                                   DqoUserPrincipalProvider principalProvider,
                                   LocalDataDomainRegistry localDataDomainRegistry) {
        this.schedulerConfigurationProperties = schedulerConfigurationProperties;
        this.schedulerFactory = schedulerFactory;
        this.jobFactory = jobFactory;
        this.triggerFactory = triggerFactory;
        this.jobDataMapAdapter = jobDataMapAdapter;
        this.scheduledJobListener = scheduledJobListener;
        this.dqoJobQueue = dqoJobQueue;
        this.parentDqoJobQueue = parentDqoJobQueue;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
        this.principalProvider = principalProvider;
        this.localDataDomainRegistry = localDataDomainRegistry;
    }

    /**
     * Returns the file synchronization mode that was configured for the scheduler.
     * @return File synchronization reporting mode.
     */
    public FileSystemSynchronizationReportingMode getSynchronizationMode() {
        return this.synchronizationMode;
    }

    /**
     * Returns the reporting mode for running the checks by the scheduler.
     * @return Reporting mode during the check execution.
     */
    public CheckRunReportingMode getCheckRunReportingMode() {
        return this.checkRunReportingMode;
    }

    /**
     * Returns the reporting mode for running the checks by the scheduler.
     *
     * @return Reporting mode used when running the checks.
     */
    @Override
    public StatisticsCollectorExecutionReportingMode getCollectStatisticsReportingMode() {
        return this.collectStatisticsReportingMode;
    }

    /**
     * Checks if the job scheduler is started.
     *
     * @return True - the job scheduler is running, false - it is not started.
     */
    @Override
    public boolean isStarted() {
        return this.started;
    }

    /**
     * Initializes and starts the scheduler.
     * @param synchronizationMode Cloud sync reporting mode.
     * @param checkRunReportingMode Check execution reporting mode.
     */
    @Override
    public void start(FileSystemSynchronizationReportingMode synchronizationMode, CheckRunReportingMode checkRunReportingMode) {
        this.synchronizationMode = synchronizationMode;
        this.checkRunReportingMode = checkRunReportingMode;

        if (this.started) {
            return;
        }

        if (log.isInfoEnabled()) {
            log.info(String.format("Starting the job scheduler, synchronization mode: %s, check run mode: %s, using the time zone: %s",
                    synchronizationMode, checkRunReportingMode, this.defaultTimeZoneProvider.getDefaultTimeZoneId()));
        }

        boolean schedulerWasPreviouslyRunning = this.scheduler != null;

        createAndStartScheduler();
        if (!schedulerWasPreviouslyRunning) {
            defineDefaultJobs();
        }
       
        this.started = true;

        reconcileScheduledDomains();
    }

    /**
     * Creates and starts a scheduler, but without any jobs.
     */
    public void createAndStartScheduler() {
        this.dqoJobQueue.start(); // ensure that the job queues are working
        this.parentDqoJobQueue.start();

        try {
            if (this.scheduler != null) {
                this.scheduler.start();
                return; // scheduler was started, stopped, and we are now starting it again
            }

            this.scheduler = schedulerFactory.getScheduler();
            this.scheduler.setJobFactory(this.jobFactory);
            if (this.scheduler.getListenerManager().getJobListeners().stream()
                    .allMatch(l -> l != this.scheduledJobListener)) {
                this.scheduler.getListenerManager().addJobListener(this.scheduledJobListener);
            }
            this.schedulerFactory.start();
        }
        catch (Exception ex) {
            log.error("Failed to start the job scheduler because " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }
    }

    /**
     * Defines the default jobs like running checks or synchronizing the metadata.
     */
    public void defineDefaultJobs() {
        try {
            if (!this.scheduler.checkExists(JobKeys.RUN_CHECKS)) {
                this.runChecksJob = newJob(RunScheduledChecksSchedulerJob.class)
                        .withIdentity(JobKeys.RUN_CHECKS)
                        .storeDurably()
                        .build();
                this.scheduler.addJob(this.runChecksJob, true);
            }

            if (!this.scheduler.checkExists(JobKeys.SYNCHRONIZE_METADATA)) {
                this.synchronizeMetadataJob = newJob(SynchronizeMetadataSchedulerJob.class)
                        .withIdentity(JobKeys.SYNCHRONIZE_METADATA)
                        .storeDurably()
                        .build();
                this.scheduler.addJob(this.synchronizeMetadataJob, true);
            }

            if (!this.scheduler.checkExists(JobKeys.COLLECT_STATISTICS)) {
                this.collectStatisticsJob = newJob(CollectScheduledStatisticsSchedulerJob.class)
                        .withIdentity(JobKeys.COLLECT_STATISTICS)
                        .storeDurably()
                        .build();
                this.scheduler.addJob(this.collectStatisticsJob, true);
            }

            if (!this.scheduler.checkExists(JobKeys.IMPORT_TABLES)) {
                this.importTablesJob = newJob(AutoImportTablesSchedulerJob.class)
                        .withIdentity(JobKeys.IMPORT_TABLES)
                        .storeDurably()
                        .build();
                this.scheduler.addJob(this.importTablesJob, true);
            }

            String scanMetadataCronSchedule = this.schedulerConfigurationProperties.getSynchronizeCronSchedule();
            DqoUserPrincipal userPrincipalForAdministrator = this.principalProvider.createLocalInstanceAdminPrincipal();
            DqoCloudApiKey dqoCloudApiKey = this.dqoCloudApiKeyProvider.getApiKey(userPrincipalForAdministrator.getDataDomainIdentity());
            if (dqoCloudApiKey != null) {
                DqoCloudApiKeyPayload apiKeyPayload = dqoCloudApiKey.getApiKeyPayload();
                if (apiKeyPayload.getLicenseType() == DqoCloudLicenseType.FREE ||
                        (apiKeyPayload.getLicenseType() == DqoCloudLicenseType.PERSONAL && apiKeyPayload.getExpiresAt() != null)) {
                    int minuteToStart = (Instant.now().atOffset(ZoneOffset.UTC).getMinute() + 5) % 60;
                    scanMetadataCronSchedule = minuteToStart + " * * * *";
                }
            }

            String mountedRootDomainName = userPrincipalForAdministrator.getDataDomainIdentity().getDataDomainCloud();
            this.scheduledDataDomains.add(mountedRootDomainName);
            CronScheduleSpec scanMetadataCronScheduleSpec = new CronScheduleSpec(scanMetadataCronSchedule);
            Trigger scanMetadataTrigger = this.triggerFactory.createTrigger(scanMetadataCronScheduleSpec, JobKeys.SYNCHRONIZE_METADATA,
                    mountedRootDomainName);
            this.scheduler.scheduleJob(scanMetadataTrigger);
        } catch (SchedulerException ex) {
            log.error("Failed to define the default jobs because " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }
    }

    /**
     * Analyzes the list of active data domains for which the job scheduler is scheduling jobs.
     * Adds or removes jobs for all nested data domains.
     */
    @Override
    public void reconcileScheduledDomains() {
        if (!this.started || this.scheduler == null) {
            return;
        }

        Set<String> allNewDataDomains = new LinkedHashSet<>();
        DqoUserPrincipal userPrincipalForAdministrator = this.principalProvider.createLocalInstanceAdminPrincipal();
        allNewDataDomains.add(userPrincipalForAdministrator.getDataDomainIdentity().getDataDomainCloud());
        Collection<LocalDataDomainSpec> nestedDataDomains = this.localDataDomainRegistry.getNestedDataDomains();
        if (nestedDataDomains == null) {
            return;
        }

        Set<String> nestedDataDomainNames = nestedDataDomains
                .stream()
                .filter(dataDomainSpec -> dataDomainSpec.isEnableScheduler())
                .map(dataDomainSpec -> dataDomainSpec.getDataDomainName())
                .collect(Collectors.toSet());
        allNewDataDomains.addAll(nestedDataDomainNames);

        for (String newDomainName : allNewDataDomains) {
            if (this.scheduledDataDomains.contains(newDomainName)) {
                continue; // already scheduled
            }

            String scanMetadataCronSchedule = this.schedulerConfigurationProperties.getSynchronizeCronSchedule();
            CronScheduleSpec scanMetadataCronScheduleSpec = new CronScheduleSpec(scanMetadataCronSchedule);
            Trigger scanMetadataTrigger = this.triggerFactory.createTrigger(scanMetadataCronScheduleSpec,
                    JobKeys.SYNCHRONIZE_METADATA, newDomainName);
            try {
                this.scheduler.scheduleJob(scanMetadataTrigger);
                this.scheduledDataDomains.add(newDomainName);

                JobDataMap jobDataMap = new JobDataMap();
                this.jobDataMapAdapter.setDataDomain(jobDataMap, newDomainName);
                this.scheduler.triggerJob(JobKeys.SYNCHRONIZE_METADATA, jobDataMap); // scan for new cron jobs
            }
            catch (SchedulerException ex) {
                log.error("Failed to schedule metadata scan for a new data domain because " + ex.getMessage(), ex);
                throw new JobSchedulerException(ex);
            }
        }

        for (String existingDomainName : List.of(this.scheduledDataDomains.toArray(new String[this.scheduledDataDomains.size()]))) {
            if (allNewDataDomains.contains(existingDomainName)) {
                continue; // nothing to change
            }

            try {
                List<? extends Trigger> triggersOfRunChecks = this.scheduler.getTriggersOfJob(JobKeys.RUN_CHECKS);
                if (triggersOfRunChecks != null) {
                    for (Trigger trigger : triggersOfRunChecks) {
                        String dataDomainInJob = this.jobDataMapAdapter.getDataDomain(trigger.getJobDataMap());
                        if (Objects.equals(dataDomainInJob, existingDomainName)) {
                            this.scheduler.unscheduleJob(trigger.getKey());
                        }
                    }
                }

                List<? extends Trigger> triggersOfSynchronizeMetadata = this.scheduler.getTriggersOfJob(JobKeys.SYNCHRONIZE_METADATA);
                if (triggersOfSynchronizeMetadata != null) {
                    for (Trigger trigger : triggersOfSynchronizeMetadata) {
                        String dataDomainInJob = this.jobDataMapAdapter.getDataDomain(trigger.getJobDataMap());
                        if (Objects.equals(dataDomainInJob, existingDomainName)) {
                            this.scheduler.unscheduleJob(trigger.getKey());
                        }
                    }
                }

                List<? extends Trigger> triggersOfCollectStatistics = this.scheduler.getTriggersOfJob(JobKeys.COLLECT_STATISTICS);
                if (triggersOfCollectStatistics != null) {
                    for (Trigger trigger : triggersOfCollectStatistics) {
                        String dataDomainInJob = this.jobDataMapAdapter.getDataDomain(trigger.getJobDataMap());
                        if (Objects.equals(dataDomainInJob, existingDomainName)) {
                            this.scheduler.unscheduleJob(trigger.getKey());
                        }
                    }
                }

                List<? extends Trigger> triggersOfImportTables = this.scheduler.getTriggersOfJob(JobKeys.IMPORT_TABLES);
                if (triggersOfImportTables != null) {
                    for (Trigger trigger : triggersOfImportTables) {
                        String dataDomainInJob = this.jobDataMapAdapter.getDataDomain(trigger.getJobDataMap());
                        if (Objects.equals(dataDomainInJob, existingDomainName)) {
                            this.scheduler.unscheduleJob(trigger.getKey());
                        }
                    }
                }
            }
            catch (SchedulerException ex) {
                log.error("Failed to unschedule jobs for an unloaded data domain because " + ex.getMessage(), ex);
                throw new JobSchedulerException(ex);
            }

            this.scheduledDataDomains.remove(existingDomainName);
        }
    }

    /**
     * Stops the job scheduler, but with an option to start it again.
     */
    @Override
    public void stop() {
        if (!this.started || this.scheduler == null) {
            return;
        }

        try {
            this.scheduler.standby();
        } catch (SchedulerException ex) {
            log.error("Failed to pause the scheduler because " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }

        this.started = false;
    }

    /**
     * Stops the scheduler.
     */
    @Override
    @PreDestroy
    public void shutdown() {
        if (!this.started) {
            return;
        }

        this.shutdownInitiated = true;

        log.debug("Shutting down the job scheduler");

        try {
            if (this.scheduler != null) {
                List<? extends Trigger> triggersOfRunChecks = this.scheduler.getTriggersOfJob(JobKeys.RUN_CHECKS);
                if (triggersOfRunChecks != null) {
                    for (Trigger trigger : triggersOfRunChecks) {
                        this.scheduler.unscheduleJob(trigger.getKey());
                    }
                }

                List<? extends Trigger> triggersOfSynchronizeMeta = this.scheduler.getTriggersOfJob(JobKeys.SYNCHRONIZE_METADATA);
                if (triggersOfSynchronizeMeta != null) {
                    for (Trigger trigger : triggersOfSynchronizeMeta) {
                        this.scheduler.unscheduleJob(trigger.getKey());
                    }
                }

                List<? extends Trigger> triggersOfCollectStatistics = this.scheduler.getTriggersOfJob(JobKeys.COLLECT_STATISTICS);
                if (triggersOfCollectStatistics != null) {
                    for (Trigger trigger : triggersOfCollectStatistics) {
                        this.scheduler.unscheduleJob(trigger.getKey());
                    }
                }

                List<? extends Trigger> triggersOfImportTables = this.scheduler.getTriggersOfJob(JobKeys.IMPORT_TABLES);
                if (triggersOfImportTables != null) {
                    for (Trigger trigger : triggersOfImportTables) {
                        this.scheduler.unscheduleJob(trigger.getKey());
                    }
                }

                this.scheduler.shutdown();
                this.schedulerFactory.stop();
            }
            this.scheduler = null;
        }
        catch (Exception ex) {
            log.error("Failed to stop the job scheduler because " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }
        finally {
            this.started = false;
            this.shutdownInitiated = false;
        }
    }

    /**
     * Triggers the metadata synchronization job on the job scheduler. Calls the cloud sync and reload the metadata to detect new schedules.
     * @param dataDomain Data domain name. Pass null to refresh all data domains.
     */
    @Override
    public void triggerMetadataSynchronization(String dataDomain) {
        if (!this.started || this.shutdownInitiated) {
            return;
        }

        log.debug("Triggering the SYNCHRONIZE_METADATA job on the job scheduler.");

        try {
            if (dataDomain == null) {
                for (String scheduledDataDomainName : new ArrayList<>(this.scheduledDataDomains)) {
                    JobDataMap jobDataMap = new JobDataMap();
                    this.jobDataMapAdapter.setDataDomain(jobDataMap, scheduledDataDomainName);
                    this.scheduler.triggerJob(JobKeys.SYNCHRONIZE_METADATA, jobDataMap);
                }
            } else {
                if (this.scheduledDataDomains.contains(dataDomain)) {
                    JobDataMap jobDataMap = new JobDataMap();
                    this.jobDataMapAdapter.setDataDomain(jobDataMap, dataDomain);
                    this.scheduler.triggerJob(JobKeys.SYNCHRONIZE_METADATA, jobDataMap);
                }
            }
        }
        catch (Exception ex) {
            log.error("Failed to trigger the metadata synchronization in the job scheduler because " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }
    }

    /**
     * Returns the default job scheduler. The scheduler must be started first.
     * @return Quartz scheduler instance.
     */
    @Override
    public Scheduler getScheduler() {
        assert this.scheduler != null : "The scheduler must be first started";
        return this.scheduler;
    }

    /**
     * Retrieves all schedules configured on triggers for a given job.
     * @param jobKey Job key for one of the known jobs like the run checks or scan metadata.
     * @param dataDomainName Data domain name.
     * @return Returns a list of active schedules.
     */
    @Override
    public UniqueSchedulesCollection getActiveSchedules(JobKey jobKey, String dataDomainName) {
        try {
            UniqueSchedulesCollection schedulesCollection = new UniqueSchedulesCollection();
            if (this.scheduler == null) {
                return schedulesCollection;
            }

            List<? extends Trigger> triggersOfJob = this.scheduler.getTriggersOfJob(jobKey);

            for (Trigger trigger : triggersOfJob) {
                JobDataMap jobDataMap = trigger.getJobDataMap();
                String dataDomainInJob = this.jobDataMapAdapter.getDataDomain(jobDataMap);

                if (Objects.equals(dataDomainInJob, dataDomainName)) {
                    CronScheduleSpec schedule = this.jobDataMapAdapter.getSchedule(jobDataMap);
                    schedulesCollection.add(schedule);
                }
            }

            return schedulesCollection;
        }
        catch (Exception ex) {
            log.error("Failed to list active schedules in the job scheduler " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }
    }

    /**
     * Applies changes for a new list of schedules, new triggers are added, unused triggers are removed.
     * @param schedulesDelta Schedule delta with schedules to add and schedules to remove.
     * @param jobKey Target job to configure. Jobs are specified in the {@link JobKeys}
     * @param dataDomainName Data domain name.
     */
    @Override
    public void applyScheduleDeltaToJob(JobSchedulesDelta schedulesDelta, JobKey jobKey, String dataDomainName) {
        Exception firstException = null;
        List<? extends Trigger> triggersOfJob = null;

        if (this.scheduler == null) {
            return; // the scheduler is not started yet
        }

        try {
            triggersOfJob = this.scheduler.getTriggersOfJob(jobKey);
        }
        catch (Exception ex) {
            log.error("Failed to list scheduled jobs because " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }

        for (Trigger trigger : triggersOfJob) {
            CronScheduleSpec scheduleOnTrigger = this.jobDataMapAdapter.getSchedule(trigger.getJobDataMap());

            if (schedulesDelta.getSchedulesToDelete().contains(scheduleOnTrigger)) {
                try {
                    this.scheduler.unscheduleJob(trigger.getKey());
                }
                catch (Exception ex) {
                    log.error("Failed to unschedule a job because " + ex.getMessage(), ex);
                    if (firstException != null) {
                        firstException = ex;
                    }
                }
            }
        }

        for (CronScheduleSpec scheduleToAdd : schedulesDelta.getSchedulesToAdd().getUniqueSchedules()) {
            try {
                Trigger triggerToAdd = this.triggerFactory.createTrigger(scheduleToAdd, jobKey, dataDomainName);

                TriggerKey triggerKey = triggerToAdd.getKey();
                if (!this.scheduler.checkExists(triggerKey)) {
                    this.scheduler.scheduleJob(triggerToAdd);
                }
            }
            catch (Exception ex) {
                log.error("Failed to schedule a job because " + ex.getMessage(), ex);
                if (firstException != null) {
                    firstException = ex;
                }
            }
        }

        if (firstException != null) {
            log.error("Failed to activate new schedules the job scheduler because " + firstException.getMessage(), firstException);
            throw new JobSchedulerException(firstException);
        }
    }
}
