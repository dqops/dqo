/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.incidents;

import com.dqops.checks.CheckType;
import com.dqops.core.configuration.DqoIncidentsConfigurationProperties;
import com.dqops.core.incidents.message.IncidentNotificationMessage;
import com.dqops.core.incidents.message.IncidentNotificationMessageParameters;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityFactory;
import com.dqops.data.checkresults.factory.CheckResultsColumnNames;
import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.dqops.data.incidents.snapshot.IncidentsSnapshot;
import com.dqops.data.incidents.snapshot.IncidentsSnapshotFactory;
import com.dqops.metadata.incidents.ConnectionIncidentGroupingSpec;
import com.dqops.metadata.incidents.FilteredNotificationSpec;
import com.dqops.metadata.incidents.IncidentGroupingLevel;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.google.common.collect.Lists;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;
import tech.tablesaw.api.*;
import tech.tablesaw.selection.Selection;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Data quality incident import service. Works in the background and imports new data quality incidents.
 * It is used because we can run checks on multiple tables in parallel, but incidents are stored on a whole
 * collection (data source) level, so we don't want to load and write parquet files too often, after processing each table.
 * We want a background incident processing service that can load new incidents in batches to the incidents table.
 */
@Component
@Slf4j
public class IncidentImportQueueServiceImpl implements IncidentImportQueueService {
    private IncidentsSnapshotFactory incidentsSnapshotFactory;
    private IncidentNotificationService incidentNotificationService;
    private final Object connectionsLock = new Object();
    private final Map<DataDomainConnectionKey, ConnectionIncidentTableUpdater> connectionIncidentLoaders = new LinkedHashMap<>();
    private final UserDomainIdentityFactory userDomainIdentityFactory;
    private final DqoIncidentsConfigurationProperties incidentsConfigurationProperties;
    private final DefaultTimeZoneProvider defaultTimeZoneProvider;

    private final IncidentNotificationsConfigurationLoader incidentNotificationsConfigurationLoader;

    /**
     * Creates an incident import queue service.
     *
     * @param incidentsSnapshotFactory                 Incident snapshot factory.
     * @param incidentNotificationService              Incident notification service. Sends notifications to addresses.
     * @param userDomainIdentityFactory                User data domain identity factory.
     * @param incidentsConfigurationProperties         Incidents configuration parameters.
     * @param defaultTimeZoneProvider                  Default time zone provider.
     * @param incidentNotificationsConfigurationLoader Incident configuration configuration loader.
     */
    @Autowired
    public IncidentImportQueueServiceImpl(IncidentsSnapshotFactory incidentsSnapshotFactory,
                                          IncidentNotificationService incidentNotificationService,
                                          UserDomainIdentityFactory userDomainIdentityFactory,
                                          DqoIncidentsConfigurationProperties incidentsConfigurationProperties,
                                          DefaultTimeZoneProvider defaultTimeZoneProvider,
                                          IncidentNotificationsConfigurationLoader incidentNotificationsConfigurationLoader) {
        this.incidentsSnapshotFactory = incidentsSnapshotFactory;
        this.incidentNotificationService = incidentNotificationService;
        this.userDomainIdentityFactory = userDomainIdentityFactory;
        this.incidentsConfigurationProperties = incidentsConfigurationProperties;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
        this.incidentNotificationsConfigurationLoader = incidentNotificationsConfigurationLoader;
    }

    /**
     * Imports incidents detected on a single table to a connection level incidents table.
     * @param tableIncidentImportBatch Issues (failed data quality check results) detected on a single table that should be loaded to the incidents table.
     * @param userDomainIdentity User and data domain identity that identifies the data domain.
     */
    @Override
    public void importTableIncidents(TableIncidentImportBatch tableIncidentImportBatch,
                                     UserDomainIdentity userDomainIdentity) {
        String connectionName = tableIncidentImportBatch.getConnection().getConnectionName();
        DataDomainConnectionKey loaderKey = new DataDomainConnectionKey(userDomainIdentity.getDataDomainFolder(), connectionName);

        synchronized (this.connectionsLock) {
            ConnectionIncidentTableUpdater connectionIncidentTableUpdater = this.connectionIncidentLoaders.get(loaderKey);
            if (connectionIncidentTableUpdater == null) {
                // create and start a new connection incident loader
                UserDomainIdentity domainIdentity = this.userDomainIdentityFactory.createDataDomainAdminIdentityForCloudDomain(userDomainIdentity.getDataDomainCloud());
                connectionIncidentTableUpdater = new ConnectionIncidentTableUpdater(loaderKey, domainIdentity, connectionName,
                        tableIncidentImportBatch, null, null);
                connectionIncidentLoaders.put(loaderKey, connectionIncidentTableUpdater);
                connectionIncidentTableUpdater.start();
            }
            else {
                // append a new table to be loaded
                connectionIncidentTableUpdater.queueTableForImport(tableIncidentImportBatch);
            }
        }
    }

    /**
     * Sets a new incident status on an incident.
     *
     * @param incidentStatusChangeParameters Parameters of the incident whose status will be updated.
     * @param userDomainIdentity             User identity that also specifies the data domain.
     */
    @Override
    public void setIncidentStatus(IncidentStatusChangeParameters incidentStatusChangeParameters,
                                  UserDomainIdentity userDomainIdentity) {
        String connectionName = incidentStatusChangeParameters.getConnectionName();
        DataDomainConnectionKey loaderKey = new DataDomainConnectionKey(userDomainIdentity.getDataDomainFolder(), connectionName);

        synchronized (this.connectionsLock) {
            ConnectionIncidentTableUpdater connectionIncidentTableUpdater = this.connectionIncidentLoaders.get(loaderKey);
            if (connectionIncidentTableUpdater == null) {
                // create and start a new connection incident loader
                UserDomainIdentity domainIdentity = this.userDomainIdentityFactory.createDataDomainAdminIdentityForCloudDomain(userDomainIdentity.getDataDomainCloud());
                connectionIncidentTableUpdater = new ConnectionIncidentTableUpdater(loaderKey, domainIdentity, connectionName,
                        null, incidentStatusChangeParameters, null);
                connectionIncidentLoaders.put(loaderKey, connectionIncidentTableUpdater);
                connectionIncidentTableUpdater.start();
            }
            else {
                // append a new status update to the queue
                connectionIncidentTableUpdater.queueIncidentStatusChange(incidentStatusChangeParameters);
            }
        }
    }

    /**
     * Sets a new incident issueUrl on an incident.
     *
     * @param incidentIssueUrlChangeParameters Parameters of the incident whose issueUrl will be updated.
     * @param userDomainIdentity               User identity that also specifies the data domain.
     */
    @Override
    public void setIncidentIssueUrl(IncidentIssueUrlChangeParameters incidentIssueUrlChangeParameters,
                                    UserDomainIdentity userDomainIdentity) {
        String connectionName = incidentIssueUrlChangeParameters.getConnectionName();
        DataDomainConnectionKey loaderKey = new DataDomainConnectionKey(userDomainIdentity.getDataDomainFolder(), connectionName);

        synchronized (this.connectionsLock) {
            ConnectionIncidentTableUpdater connectionIncidentTableUpdater = this.connectionIncidentLoaders.get(loaderKey);
            if (connectionIncidentTableUpdater == null) {
                // create and start a new connection incident loader
                UserDomainIdentity domainIdentity = this.userDomainIdentityFactory.createDataDomainAdminIdentityForCloudDomain(userDomainIdentity.getDataDomainCloud());
                connectionIncidentTableUpdater = new ConnectionIncidentTableUpdater(loaderKey, domainIdentity, connectionName,
                        null, null, incidentIssueUrlChangeParameters);
                connectionIncidentLoaders.put(loaderKey, connectionIncidentTableUpdater);
                connectionIncidentTableUpdater.start();
            }
            else {
                // append a new status update to the queue
                connectionIncidentTableUpdater.queueIncidentIssueUrlChange(incidentIssueUrlChangeParameters);
            }
        }
    }

    /**
     * Class that manages a queue of loading incidents on a single connection level.
     */
    public class ConnectionIncidentTableUpdater {
        private final Queue<TableIncidentImportBatch> tableIncidentsQueue = new LinkedList<>();
        private final Queue<IncidentStatusChangeParameters> statusChangeQueue = new LinkedList<>();
        private final Queue<IncidentIssueUrlChangeParameters> issueUrlChangeQueue = new LinkedList<>();
        private final Object loaderLock = new Object();
        private final DataDomainConnectionKey loaderKey;
        private UserDomainIdentity domainIdentity;
        private String connectionName;
        private IncidentsSnapshot incidentsSnapshot;
        private Map<Long, IntArrayList> existingIncidentByHashRowIndexes;
        private Table allExistingIncidentRows;
        private Map<Long, IntArrayList> newIncidentByHashRowIndexes;
        private Table allNewIncidentRows;

        /**
         * Creates an incident loader queue for a named connection.
         * @param loaderKey Loader key under which this object is stored.
         * @param domainIdentity Domain admin identity, identifies the data domain.
         * @param connectionName Target connection name.
         * @param tableIncidentImportBatch Optional table incident import batch to be queued.
         * @param incidentStatusChangeParameters Optional incident status change parameter to be queued.
         * @param incidentIssueUrlChangeParameters Optional incident issueUrl change parameter to be queued.
         */
        public ConnectionIncidentTableUpdater(DataDomainConnectionKey loaderKey,
                                              UserDomainIdentity domainIdentity,
                                              String connectionName,
                                              TableIncidentImportBatch tableIncidentImportBatch,
                                              IncidentStatusChangeParameters incidentStatusChangeParameters,
                                              IncidentIssueUrlChangeParameters incidentIssueUrlChangeParameters) {
            this.loaderKey = loaderKey;
            this.domainIdentity = domainIdentity;
            this.connectionName = connectionName;
            if (tableIncidentImportBatch != null) {
                this.tableIncidentsQueue.add(tableIncidentImportBatch);
            }
            if (incidentStatusChangeParameters != null) {
                this.statusChangeQueue.add(incidentStatusChangeParameters);
            }
            if (incidentIssueUrlChangeParameters != null) {
                this.issueUrlChangeQueue.add(incidentIssueUrlChangeParameters);
            }
        }

        /**
         * Queues a table to be loaded.
         * NOTE: This method must be called when the thread is synchronized on the lock.
         * @param tableIncidentImportBatch Incidents detected on a table.
         */
        public void queueTableForImport(TableIncidentImportBatch tableIncidentImportBatch) {
            synchronized (this.loaderLock) {
                this.tableIncidentsQueue.add(tableIncidentImportBatch);
            }
        }

        /**
         * Queues an incident status change operation.
         * @param incidentStatusChangeParameters Incident status change operation to be executed synchronously.
         */
        public void queueIncidentStatusChange(IncidentStatusChangeParameters incidentStatusChangeParameters) {
            synchronized (this.loaderLock) {
                this.statusChangeQueue.add(incidentStatusChangeParameters);
            }
        }

        /**
         * Queues an incident issueUrl change operation.
         * @param incidentIssueUrlChangeParameters Incident status change operation to be executed synchronously.
         */
        public void queueIncidentIssueUrlChange(IncidentIssueUrlChangeParameters incidentIssueUrlChangeParameters) {
            synchronized (this.loaderLock) {
                this.issueUrlChangeQueue.add(incidentIssueUrlChangeParameters);
            }
        }

        /**
         * Starts a background incident loader on a connection level.
         */
        public void start() {
            Schedulers.boundedElastic().schedule(this::run);
        }

        /**
         * Internal method that runs the incident loader on the bounded elastic thread queue.
         */
        public void run() {
            try {
                while (true) {
                    synchronized (connectionsLock) {
                        if (this.tableIncidentsQueue.isEmpty() && this.statusChangeQueue.isEmpty() && this.issueUrlChangeQueue.isEmpty()) {
                            connectionIncidentLoaders.remove(this.loaderKey);
                            return;
                        }
                    }

                    while (true) { // processing multiple tables as a bigger multi-table batch, before flushing
                        TableIncidentImportBatch nextTableImportBatch;
                        IncidentStatusChangeParameters incidentStatusChangeParameters = null;
                        IncidentIssueUrlChangeParameters incidentIssueUrlChangeParameters = null;

                        synchronized (loaderLock) {
                            nextTableImportBatch = this.tableIncidentsQueue.poll();
                            if (nextTableImportBatch == null) {
                                incidentStatusChangeParameters = this.statusChangeQueue.poll();
                                if (incidentStatusChangeParameters == null) {
                                    incidentIssueUrlChangeParameters = this.issueUrlChangeQueue.poll();
                                    if (incidentIssueUrlChangeParameters == null) {
                                        break; // flushing
                                    }
                                }
                            }
                        }

                        if (nextTableImportBatch != null) {
                            List<IncidentNotificationMessage> newIncidentsNotificationMessages = importBatch(
                                    nextTableImportBatch, this.domainIdentity);
                            if (newIncidentsNotificationMessages != null) {
                                // sending notifications
                                incidentNotificationService.sendNotifications(
                                        newIncidentsNotificationMessages,
                                        nextTableImportBatch.getConnection().getIncidentGrouping(),
                                        this.domainIdentity);
                            }
                        }

                        if (incidentStatusChangeParameters != null) {
                            IncidentNotificationMessage changedIncidentsNotificationMessage = changeIncidentStatus(incidentStatusChangeParameters);
                            if (changedIncidentsNotificationMessage != null) {
                                // sending notifications
                                ArrayList<IncidentNotificationMessage> statusChangeNotificationMessages = Lists.newArrayList(changedIncidentsNotificationMessage);
                                incidentNotificationService.sendNotifications(
                                        statusChangeNotificationMessages,
                                        incidentStatusChangeParameters.getIncidentGrouping(),
                                        this.domainIdentity);
                            }
                        }

                        if (incidentIssueUrlChangeParameters != null) {
                            changeIncidentIssueUrl(incidentIssueUrlChangeParameters);
                            // no notification
                        }
                    }

                    if (this.incidentsSnapshot != null) {
                        this.incidentsSnapshot.save();
                        this.incidentsSnapshot = null;
                        this.existingIncidentByHashRowIndexes = null;
                        this.allExistingIncidentRows = null;
                        this.newIncidentByHashRowIndexes = null;
                        this.allNewIncidentRows = null;
                    }
                }
            }
            catch (Throwable ex) {
                synchronized (connectionsLock) {
                    connectionIncidentLoaders.remove(this.loaderKey);
                }
                log.error("Failed to load incidents to the incidents table", ex);
            }
        }

        /**
         * Imports the new batch of failed data quality checks to a connection level table.
         * NOTE: This method assumes that the new check results are sorted by the executedAt, so it is a new batch of recently
         * executed checks, not just an old list of checks for which we are creating incidents.
         * @param nextTableImportBatch Next table incident batch that should be loaded.
         * @param userIdentity  User identity that specifies the data domain where the notification addresses are defined.
         */
        public List<IncidentNotificationMessage> importBatch(TableIncidentImportBatch nextTableImportBatch,
                                                             UserDomainIdentity userIdentity) {
            ConnectionSpec connection = nextTableImportBatch.getConnection();
            ConnectionIncidentGroupingSpec incidentGroupingAtConnection = connection.getIncidentGrouping();
            if (incidentGroupingAtConnection == null || incidentGroupingAtConnection.isDisabled()) {
                return null;
            }

            Table newCheckResults = nextTableImportBatch.getNewCheckResults();
            int minimumSeverityLevel = incidentGroupingAtConnection.getMinimumSeverity().getSeverityLevel();
            IntColumn severityColumn = newCheckResults.intColumn(CheckResultsColumnNames.SEVERITY_COLUMN_NAME);
            InstantColumn executedAtColumn = newCheckResults.instantColumn(CheckResultsColumnNames.EXECUTED_AT_COLUMN_NAME);
            DateTimeColumn timePeriodColumn = newCheckResults.dateTimeColumn(CheckResultsColumnNames.TIME_PERIOD_COLUMN_NAME);
            LongColumn checkResultIncidentHashColumn = newCheckResults.longColumn(CheckResultsColumnNames.INCIDENT_HASH_COLUMN_NAME);
            StringColumn checkTypeColumn = newCheckResults.stringColumn(CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME);
            Selection selectionOfMonitoringCheckResults = checkTypeColumn.isEqualTo(CheckType.monitoring.getDisplayName());
            Selection selectionOfPartitionedCheckResults = checkTypeColumn.isEqualTo(CheckType.partitioned.getDisplayName());
            Selection selectionOfIssuesAboveMinSeverity = severityColumn.isGreaterThanOrEqualTo(minimumSeverityLevel);

            Selection selectionOfMonitoringAlerts = selectionOfMonitoringCheckResults // only monitoring (full table scan) check results
                    .and(selectionOfIssuesAboveMinSeverity); // incidents generated only for severity above a threshold

            ZoneId defaultTimeZoneId = defaultTimeZoneProvider.getDefaultTimeZoneId();
            int partitionedChecksTimeWindowDays = incidentsConfigurationProperties.getPartitionedChecksTimeWindowDays();
            LocalDateTime oldestIncludedPartitionedCheck = Instant.now().atZone(defaultTimeZoneId).truncatedTo(ChronoUnit.DAYS)
                    .minus(partitionedChecksTimeWindowDays, ChronoUnit.DAYS)
                    .toLocalDateTime();
            Selection selectionOfPartitionChecksInTimeWindowCheckResults = selectionOfPartitionedCheckResults
                    .and(selectionOfIssuesAboveMinSeverity)
                    .and(timePeriodColumn.isOnOrAfter(oldestIncludedPartitionedCheck));

            Selection selectionOfAllNewAlerts = selectionOfMonitoringAlerts // only monitoring issues, independent of the time period
                    .or(selectionOfPartitionChecksInTimeWindowCheckResults); // incidents for issues related to old partitioned checks cover results only for a specified time window
                    // profiling check results are not used to generate incidents, that is why there is no third OR
            List<Integer> newIncidentsRowIndexes = new ArrayList<>();

            if (selectionOfAllNewAlerts.isEmpty()) {
                return null; // no alerts with a severity at the threshold when we create incidents
            }

            if (this.incidentsSnapshot == null) {
                this.incidentsSnapshot = incidentsSnapshotFactory.createSnapshot(this.connectionName, this.domainIdentity); // load previous snapshots
                this.allNewIncidentRows = this.incidentsSnapshot.getTableDataChanges().getNewOrChangedRows();
            }

            if (this.newIncidentByHashRowIndexes == null) {
                this.newIncidentByHashRowIndexes = new LinkedHashMap<>();
            }

            IntColumn failedChecksNewIncidentsColumn = this.allNewIncidentRows.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME);
            IntColumn highestSeverityNewIncidentsColumn = this.allNewIncidentRows.intColumn(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME);
            InstantColumn lastSeenNewIncidentsColumn = this.allNewIncidentRows.instantColumn(IncidentsColumnNames.LAST_SEEN_COLUMN_NAME);
            InstantColumn incidentUntilNewIncidentsColumn = this.allNewIncidentRows.instantColumn(IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME);
            StringColumn statusNewIncidentsColumn = this.allNewIncidentRows.stringColumn(IncidentsColumnNames.STATUS_COLUMN_NAME);

            int[] issuesRowIndexes = selectionOfAllNewAlerts.toArray();
            for (int i = 0; i < issuesRowIndexes.length; i++) {
                int checkResultRowIndex = issuesRowIndexes[i];
                Integer severity = severityColumn.get(checkResultRowIndex);

                if (severity < incidentGroupingAtConnection.getMinimumSeverity().getSeverityLevel()) {
                    continue; // skipping this incident
                }

                Instant executedAt = executedAtColumn.get(checkResultRowIndex);
                Long incidentHash = checkResultIncidentHashColumn.get(checkResultRowIndex);

                int maxDaysToLoad = Math.max(incidentGroupingAtConnection.getMaxIncidentLengthDays(), incidentGroupingAtConnection.getMuteForDays());
                Instant earliestDateToLoad = executedAt.minus(maxDaysToLoad + 1, ChronoUnit.DAYS);

                boolean newMonthsLoaded = this.incidentsSnapshot.ensureMonthsAreLoaded(earliestDateToLoad.atOffset(ZoneOffset.UTC).toLocalDate(),
                        executedAt.atOffset(ZoneOffset.UTC).toLocalDate());
                if (newMonthsLoaded) {
                    this.allExistingIncidentRows = this.incidentsSnapshot.getAllData();
                    this.existingIncidentByHashRowIndexes = findIncidentRowIndexes(this.allExistingIncidentRows);
                }

                IntArrayList newOrUpdatedIncidentRowIndexes = this.newIncidentByHashRowIndexes.get(incidentHash);
                if (newOrUpdatedIncidentRowIndexes != null) {
                    // update the active incident

                    boolean incidentUpdated = false;
                    for (Integer newRowIndex : newOrUpdatedIncidentRowIndexes) {
                        if (!Objects.equals(statusNewIncidentsColumn.get(newRowIndex), IncidentStatus.resolved) &&
                                !executedAt.isAfter(incidentUntilNewIncidentsColumn.get(newRowIndex))) {
                            failedChecksNewIncidentsColumn.set(newRowIndex.intValue(), failedChecksNewIncidentsColumn.get(newRowIndex) + 1);
                            Integer highestSeenSeverity = highestSeverityNewIncidentsColumn.get(newRowIndex);
                            if (highestSeenSeverity < severity) {
                                highestSeverityNewIncidentsColumn.set(newRowIndex.intValue(), severity);
                            }
                            Instant lastSeen = lastSeenNewIncidentsColumn.get(newRowIndex);
                            if (lastSeen.isBefore(executedAt)) {
                                lastSeenNewIncidentsColumn.set(newRowIndex, executedAt);
                            }

                            incidentUpdated = true;
                            break;
                        }
                    }

                    if (incidentUpdated) {
                        continue;  // skipping the next part, no need to copy an incident from existing incidents
                    }
                }

                IntArrayList rowIndexesOfOldIncidents = this.existingIncidentByHashRowIndexes.get(incidentHash);
                Integer existingOpenIncidentRowIndex = null;
                if (rowIndexesOfOldIncidents != null) {
                    for (int ri = rowIndexesOfOldIncidents.size() - 1; ri >= 0; ri--) {
                        int existingIncidentRowIndex = rowIndexesOfOldIncidents.getInt(ri);
                        InstantColumn existingIncidentsFirstSeenColumn = this.allExistingIncidentRows.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME);
                        InstantColumn existingIncidentsIncidentUntilColumn = this.allExistingIncidentRows.instantColumn(IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME);
                        StringColumn existingIncidentsStatusColumn = this.allExistingIncidentRows.stringColumn(IncidentsColumnNames.STATUS_COLUMN_NAME);

                        if (!executedAt.isBefore(existingIncidentsFirstSeenColumn.get(existingIncidentRowIndex)) &&
                            !executedAt.isAfter(existingIncidentsIncidentUntilColumn.get(existingIncidentRowIndex)) &&
                            !Objects.equals(existingIncidentsStatusColumn.get(existingIncidentRowIndex), IncidentStatus.resolved.name())) {
                            existingOpenIncidentRowIndex = existingIncidentRowIndex;
                            break; // we found an incident where we could add the issue
                        }
                    }
                }

                if (existingOpenIncidentRowIndex == null) {
                    // this is a new incident, we don't know anything about it

                    PhysicalTableName physicalTableName = nextTableImportBatch.getTable().getPhysicalTableName();
                    Integer tablePriority = nextTableImportBatch.getTable().getPriority();

                    IncidentNotificationConfigurations notificationConfigurations = incidentNotificationsConfigurationLoader
                            .loadConfiguration(incidentGroupingAtConnection, userIdentity);
                    IncidentNotificationMessage notificationMessageForFilterCheck = new IncidentNotificationMessage(){{
                        setConnection(connectionName);
                        setSchema(physicalTableName.getSchemaName());
                        setTable(physicalTableName.getTableName());
                        setTablePriority(tablePriority);
                        setDataGroupName(newCheckResults.getString(checkResultRowIndex, CheckResultsColumnNames.DATA_GROUP_NAME_COLUMN_NAME));
                        setQualityDimension(newCheckResults.getString(checkResultRowIndex, CheckResultsColumnNames.QUALITY_DIMENSION_COLUMN_NAME));
                        setCheckCategory(newCheckResults.getString(checkResultRowIndex, CheckResultsColumnNames.CHECK_CATEGORY_COLUMN_NAME));
                        setCheckType(newCheckResults.getString(checkResultRowIndex, CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME));
                        setCheckName(newCheckResults.getString(checkResultRowIndex, CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME));
                        setHighestSeverity(severity);
                    }};

                    FilteredNotificationSpec firstMatchingNotification = notificationConfigurations.findFirstMatchingNotification(notificationMessageForFilterCheck);
                    boolean shouldExcludeIncident = firstMatchingNotification != null && firstMatchingNotification.isDoNotCreateIncidents();
                    if (shouldExcludeIncident) {
                        continue;
                    }

                    Row newIncidentRow = this.allNewIncidentRows.appendRow();
                    int newIncidentRowIndex = newIncidentRow.getRowNumber();
                    if (newOrUpdatedIncidentRowIndexes != null) {
                        newOrUpdatedIncidentRowIndexes.add(newIncidentRowIndex);
                    } else {
                        this.newIncidentByHashRowIndexes.put(incidentHash, new IntArrayList(new int[] { newIncidentRowIndex }));
                    }
                    newIncidentsRowIndexes.add(newIncidentRowIndex);
                    UUID incidentIdUuid = new UUID(incidentHash, Hashing.combineOrdered(
                            new ArrayList<>() {{
                                add(Hashing.farmHashFingerprint64().hashString(connectionName, StandardCharsets.UTF_8));
                                add(Hashing.farmHashFingerprint64().hashLong(executedAt.toEpochMilli()));
                                add(HashCode.fromLong(incidentHash));
                            }}).asLong());

                    String incidentId = incidentIdUuid.toString();
                    newIncidentRow.setString(IncidentsColumnNames.ID_COLUMN_NAME, incidentId);
                    newIncidentRow.setLong(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME, incidentHash);
                    newIncidentRow.setInt(IncidentsColumnNames.MINIMUM_SEVERITY_COLUMN_NAME, minimumSeverityLevel);

                    newIncidentRow.setString(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME, physicalTableName.getSchemaName());
                    newIncidentRow.setString(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME, physicalTableName.getTableName());

                    if (tablePriority != null) {
                        newIncidentRow.setInt(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME, tablePriority);
                    }
                    if (incidentGroupingAtConnection.isDivideByDataGroups()) {
                        String dataGroupName = newCheckResults.getString(checkResultRowIndex, CheckResultsColumnNames.DATA_GROUP_NAME_COLUMN_NAME);
                        newIncidentRow.setString(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME, dataGroupName);
                    }

                    newIncidentRow.setInt(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME, severity);
                    newIncidentRow.setInstant(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME, executedAt);
                    newIncidentRow.setInstant(IncidentsColumnNames.LAST_SEEN_COLUMN_NAME, executedAt);
                    newIncidentRow.setInstant(IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME,
                            executedAt.plus(incidentGroupingAtConnection.getMaxIncidentLengthDays(), ChronoUnit.DAYS));
                    newIncidentRow.setInt(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME, 1);
                    newIncidentRow.setString(IncidentsColumnNames.STATUS_COLUMN_NAME, IncidentStatus.open.name());

                    IncidentGroupingLevel incidentGroupingLevel = incidentGroupingAtConnection.getGroupingLevel();
                    if (incidentGroupingLevel.groupByDimension()) {
                        String qualityDimension = newCheckResults.getString(checkResultRowIndex, CheckResultsColumnNames.QUALITY_DIMENSION_COLUMN_NAME);
                        newIncidentRow.setString(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME, qualityDimension);
                    }

                    if (incidentGroupingLevel.groupByCheckCategory()) {
                        String checkCategory = newCheckResults.getString(checkResultRowIndex, CheckResultsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
                        newIncidentRow.setString(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME, checkCategory);
                    }

                    if (incidentGroupingLevel.groupByCheckType()) {
                        String checkType = newCheckResults.getString(checkResultRowIndex, CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME);
                        newIncidentRow.setString(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME, checkType);
                    }

                    if (incidentGroupingLevel.groupByCheckName()) {
                        String checkName = newCheckResults.getString(checkResultRowIndex, CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME);
                        newIncidentRow.setString(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME, checkName);
                    }
                } else {
                    // copy the row for update and increment values...
                    int updatedIncidentRowIndex = this.allNewIncidentRows.rowCount();
                    this.allNewIncidentRows.addRow(existingOpenIncidentRowIndex, this.allExistingIncidentRows);
                    if (newOrUpdatedIncidentRowIndexes != null) {
                        newOrUpdatedIncidentRowIndexes.add(updatedIncidentRowIndex);
                    } else {
                        this.newIncidentByHashRowIndexes.put(incidentHash, new IntArrayList(new int[] { updatedIncidentRowIndex }));
                    }

                    failedChecksNewIncidentsColumn.set(updatedIncidentRowIndex, failedChecksNewIncidentsColumn.get(updatedIncidentRowIndex) + 1);
                    Integer highestSeenSeverity = highestSeverityNewIncidentsColumn.get(updatedIncidentRowIndex);
                    if (highestSeenSeverity < severity) {
                        highestSeverityNewIncidentsColumn.set(updatedIncidentRowIndex, severity);
                    }
                    Instant lastSeen = lastSeenNewIncidentsColumn.get(updatedIncidentRowIndex);
                    if (lastSeen.isBefore(executedAt)) {
                        lastSeenNewIncidentsColumn.set(updatedIncidentRowIndex, executedAt);
                    }
                }
            }

            List<IncidentNotificationMessage> incidentNotificationMessages = newIncidentsRowIndexes.stream()
                    .map(newIncidentRowIndex -> {
                        IncidentNotificationMessageParameters messageParameters = IncidentNotificationMessageParameters
                                .builder()
                                .incidentRow(this.allNewIncidentRows.row(newIncidentRowIndex))
                                .connectionName(connectionName)
                                .build();

                        return IncidentNotificationMessage.fromIncidentRow(messageParameters);
                    })
                    .collect(Collectors.toList());

            return incidentNotificationMessages;
        }

        /**
         * Finds row indexes of all existing incidents identified by an incident hash.
         * @param incidentTable Incident table with all existing incidents.
         * @return Map indexed by the incident hash, which contains an integer list of row indexes in the <code>incidentTable</code> table with that hash.
         */
        public Map<Long, IntArrayList> findIncidentRowIndexes(Table incidentTable) {
            Map<Long, IntArrayList> resultMap = new LinkedHashMap<>();

            if (incidentTable == null) {
                return resultMap;
            }

            LongColumn incidentHashColumn = incidentTable.longColumn(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME);
            int rowCount = incidentHashColumn.size();

            for (int i = 0; i < rowCount; i++) {
                Long hash = incidentHashColumn.get(i);
                IntArrayList hashRowIndexes = resultMap.get(hash);
                if (hashRowIndexes == null) {
                    hashRowIndexes = new IntArrayList();
                    resultMap.put(hash, hashRowIndexes);
                }
                hashRowIndexes.add(i);
            }

            return resultMap;
        }

        /**
         * Updates an incident status. Sends notifications if the status is changed.
         * @param incidentStatusChangeParameters Incident notification parameters with a new incident status.
         * @return Incident notification message for an updated incident.
         */
        public IncidentNotificationMessage changeIncidentStatus(IncidentStatusChangeParameters incidentStatusChangeParameters) {
            if (this.incidentsSnapshot == null) {
                this.incidentsSnapshot = incidentsSnapshotFactory.createSnapshot(this.connectionName, this.domainIdentity); // load previous snapshots
                this.allNewIncidentRows = this.incidentsSnapshot.getTableDataChanges().getNewOrChangedRows();
            }

            if (this.newIncidentByHashRowIndexes == null) {
                this.newIncidentByHashRowIndexes = new LinkedHashMap<>();
            }

            LocalDate monthOfIncidentFirstSeen = LocalDate.of(incidentStatusChangeParameters.getFirstSeenYear(),
                    incidentStatusChangeParameters.getFirstSeenMonth(), 1);
            boolean newMonthsLoaded = this.incidentsSnapshot.ensureMonthsAreLoaded(monthOfIncidentFirstSeen, monthOfIncidentFirstSeen);;
            if (newMonthsLoaded) {
                this.allExistingIncidentRows = this.incidentsSnapshot.getAllData();
                this.existingIncidentByHashRowIndexes = findIncidentRowIndexes(this.allExistingIncidentRows);
            }

            String newStatusString = incidentStatusChangeParameters.getNewIncidentStatus().name();
            Selection newRowsIncidentIdSelection = this.allNewIncidentRows.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME)
                    .isEqualTo(incidentStatusChangeParameters.getIncidentId());
            if (!newRowsIncidentIdSelection.isEmpty()) {
                // the updated row has other updates awaiting
                int newRowIndex = newRowsIncidentIdSelection.get(0);
                StringColumn newRowStatusColumn = this.allNewIncidentRows.stringColumn(IncidentsColumnNames.STATUS_COLUMN_NAME);
                String currentStatus = newRowStatusColumn.get(newRowIndex);
                if (!Objects.equals(currentStatus, newStatusString)) {
                    newRowStatusColumn.set(newRowIndex, newStatusString);

                    IncidentNotificationMessageParameters messageParameters = IncidentNotificationMessageParameters
                            .builder()
                            .incidentRow(this.allNewIncidentRows.row(newRowIndex))
                            .connectionName(incidentStatusChangeParameters.getConnectionName())
                            .build();
                    return IncidentNotificationMessage.fromIncidentRow(messageParameters);
                }
            } else if (this.allExistingIncidentRows != null) {
                Selection existingRowsIncidentIdSelection = this.allExistingIncidentRows.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME)
                        .isEqualTo(incidentStatusChangeParameters.getIncidentId());
                if (existingRowsIncidentIdSelection.isEmpty()) {
                    // incident not found, skipping because changing the status is a background async operation that is not returning the result
                    return null;
                }

                int[] existingIncidentRowIndexes = existingRowsIncidentIdSelection.toArray();
                assert existingIncidentRowIndexes.length == 1;
                int targetNewIncidentsRowIndex = this.allNewIncidentRows.rowCount();
                this.allNewIncidentRows.addRow(existingIncidentRowIndexes[0], this.allExistingIncidentRows);
                Long incidentHash = this.allNewIncidentRows.longColumn(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME)
                        .get(targetNewIncidentsRowIndex);

                IntArrayList newRowIndexes = this.newIncidentByHashRowIndexes.get(incidentHash);
                if (newRowIndexes != null) {
                    newRowIndexes.add(targetNewIncidentsRowIndex);
                }
                else {
                    this.newIncidentByHashRowIndexes.put(incidentHash, new IntArrayList(new int[] { targetNewIncidentsRowIndex }));
                }

                StringColumn newRowStatusColumn = this.allNewIncidentRows.stringColumn(IncidentsColumnNames.STATUS_COLUMN_NAME);
                String currentStatus = newRowStatusColumn.get(targetNewIncidentsRowIndex);
                if (!Objects.equals(currentStatus, newStatusString)) {
                    newRowStatusColumn.set(targetNewIncidentsRowIndex, newStatusString);

                    IncidentNotificationMessageParameters messageParameters = IncidentNotificationMessageParameters
                            .builder()
                            .incidentRow(this.allNewIncidentRows.row(targetNewIncidentsRowIndex))
                            .connectionName(incidentStatusChangeParameters.getConnectionName())
                            .build();
                    return IncidentNotificationMessage.fromIncidentRow(messageParameters);
                }
            }

            return null;
        }

        /**
         * Updates an incident issueUrl.
         * @param incidentIssueUrlChangeParameters Incident change parameters with a new incident issueUrl.
         */
        public void changeIncidentIssueUrl(IncidentIssueUrlChangeParameters incidentIssueUrlChangeParameters) {
            if (this.incidentsSnapshot == null) {
                this.incidentsSnapshot = incidentsSnapshotFactory.createSnapshot(this.connectionName, this.domainIdentity); // load previous snapshots
                this.allNewIncidentRows = this.incidentsSnapshot.getTableDataChanges().getNewOrChangedRows();
            }

            if (this.newIncidentByHashRowIndexes == null) {
                this.newIncidentByHashRowIndexes = new LinkedHashMap<>();
            }

            LocalDate monthOfIncidentFirstSeen = LocalDate.of(incidentIssueUrlChangeParameters.getFirstSeenYear(),
                    incidentIssueUrlChangeParameters.getFirstSeenMonth(), 1);
            boolean newMonthsLoaded = this.incidentsSnapshot.ensureMonthsAreLoaded(monthOfIncidentFirstSeen, monthOfIncidentFirstSeen);;
            if (newMonthsLoaded) {
                this.allExistingIncidentRows = this.incidentsSnapshot.getAllData();
                this.existingIncidentByHashRowIndexes = findIncidentRowIndexes(this.allExistingIncidentRows);
            }

            Selection newRowsIncidentIdSelection = this.allNewIncidentRows.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME)
                    .isEqualTo(incidentIssueUrlChangeParameters.getIncidentId());
            if (!newRowsIncidentIdSelection.isEmpty()) {
                // the updated row has other updates awaiting
                int newRowIndex = newRowsIncidentIdSelection.get(0);
                StringColumn newRowIssueUrlColumn = this.allNewIncidentRows.stringColumn(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME);
                newRowIssueUrlColumn.set(newRowIndex, incidentIssueUrlChangeParameters.getNewIssueUrl());
            } else if (this.allExistingIncidentRows != null) {
                Selection existingRowsIncidentIdSelection = this.allExistingIncidentRows.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME)
                        .isEqualTo(incidentIssueUrlChangeParameters.getIncidentId());
                if (existingRowsIncidentIdSelection.isEmpty()) {
                    // incident not found, skipping because changing the status is a background async operation that is not returning the result
                    return;
                }

                int[] existingIncidentRowIndexes = existingRowsIncidentIdSelection.toArray();
                assert existingIncidentRowIndexes.length == 1;
                int targetNewIncidentsRowIndex = this.allNewIncidentRows.rowCount();
                this.allNewIncidentRows.addRow(existingIncidentRowIndexes[0], this.allExistingIncidentRows);
                Long incidentHash = this.allNewIncidentRows.longColumn(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME)
                        .get(targetNewIncidentsRowIndex);

                IntArrayList newRowIndexes = this.newIncidentByHashRowIndexes.get(incidentHash);
                if (newRowIndexes != null) {
                    newRowIndexes.add(targetNewIncidentsRowIndex);
                }
                else {
                    this.newIncidentByHashRowIndexes.put(incidentHash, new IntArrayList(new int[] { targetNewIncidentsRowIndex }));
                }

                StringColumn newRowIssueUrlColumn = this.allNewIncidentRows.stringColumn(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME);
                newRowIssueUrlColumn.set(targetNewIncidentsRowIndex, incidentIssueUrlChangeParameters.getNewIssueUrl());
            }
        }
    }
}
