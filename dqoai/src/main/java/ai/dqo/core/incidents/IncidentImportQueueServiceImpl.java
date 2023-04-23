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

package ai.dqo.core.incidents;

import ai.dqo.data.checkresults.factory.CheckResultsColumnNames;
import ai.dqo.data.incidents.factory.IncidentStatus;
import ai.dqo.data.incidents.factory.IncidentsColumnNames;
import ai.dqo.data.incidents.snapshot.IncidentsSnapshot;
import ai.dqo.data.incidents.snapshot.IncidentsSnapshotFactory;
import ai.dqo.metadata.incidents.IncidentGroupingLevel;
import ai.dqo.metadata.incidents.IncidentGroupingSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.PhysicalTableName;
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
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    private final Map<String, ConnectionIncidentLoader> connectionIncidentLoaders = new LinkedHashMap<>();

    /**
     * Creates an incident import queue service.
     * @param incidentsSnapshotFactory Incident snapshot factory.
     * @param incidentNotificationService Incident notification service. Sends notifications to webhooks.
     */
    @Autowired
    public IncidentImportQueueServiceImpl(IncidentsSnapshotFactory incidentsSnapshotFactory,
                                          IncidentNotificationService incidentNotificationService) {
        this.incidentsSnapshotFactory = incidentsSnapshotFactory;
        this.incidentNotificationService = incidentNotificationService;
    }

    /**
     * Imports incidents detected on a single table to a connection level incidents table.
     * @param tableIncidentImportBatch Issues (failed data quality check results) detected on a single table that should be loaded to the incidents table.
     */
    @Override
    public void importTableIncidents(TableIncidentImportBatch tableIncidentImportBatch) {
        String connectionName = tableIncidentImportBatch.getConnection().getConnectionName();

        synchronized (this.connectionsLock) {
            ConnectionIncidentLoader connectionIncidentLoader = this.connectionIncidentLoaders.get(connectionName);
            if (connectionIncidentLoader == null) {
                // create and start a new connection incident loader
                connectionIncidentLoader = new ConnectionIncidentLoader(connectionName, tableIncidentImportBatch);
                connectionIncidentLoaders.put(connectionName, connectionIncidentLoader);
                connectionIncidentLoader.start();
            }
            else {
                // append a new table to be loaded
                connectionIncidentLoader.queueTableForImport(tableIncidentImportBatch);
            }
        }
    }

    /**
     * Class that manages a queue of loading incidents on a single connection level.
     */
    public class ConnectionIncidentLoader {
        private final Queue<TableIncidentImportBatch> tableIncidentsQueue = new LinkedList<>(); // access protected by the "lock" object from the parent class
        private final Object loaderLock = new Object();
        private String connectionName;
        private IncidentsSnapshot incidentsSnapshot;
        private Map<Long, IntArrayList> existingIncidentByHashRowIndexes;
        private Table allExistingIncidentRows;
        private Map<Long, Integer> newIncidentByHashRowIndexes;
        private Table allNewIncidentRows;

        /**
         * Creates an incident loader queue for a named connection.
         * @param connectionName Target connection name.
         */
        public ConnectionIncidentLoader(String connectionName,
                                        TableIncidentImportBatch tableIncidentImportBatch) {
            this.connectionName = connectionName;
            this.tableIncidentsQueue.add(tableIncidentImportBatch);
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
                        if (this.tableIncidentsQueue.isEmpty()) {
                            connectionIncidentLoaders.remove(connectionName);
                            return;
                        }
                    }

                    while (true) { // processing multiple tables as a bigger multi-table batch, before flushing
                        TableIncidentImportBatch nextTableImportBatch;

                        synchronized (loaderLock) {
                            nextTableImportBatch = this.tableIncidentsQueue.poll();
                            if (nextTableImportBatch == null) {
                                break; // flushing
                            }
                        }

                        List<NewIncidentNotificationMessage> newIncidentsNotificationMessages = importBatch(nextTableImportBatch);
                        if (newIncidentsNotificationMessages != null) {
                            // sending notifications
                            incidentNotificationService.sendNotifications(newIncidentsNotificationMessages,
                                    nextTableImportBatch.getConnection().getIncidentGrouping());
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
                    connectionIncidentLoaders.remove(connectionName);
                }
                log.error("Failed to load incidents to the incidents table", ex);
            }
        }

        /**
         * Imports the new batch of failed data quality checks to a connection level table.
         * NOTE: This method assumes that the new check results are sorted by the executedAt, so it is a new batch of recently
         * executed checks, not just an old list of checks for which we are creating incidents.
         * @param nextTableImportBatch Next table incident batch that should be loaded.
         */
        public List<NewIncidentNotificationMessage> importBatch(TableIncidentImportBatch nextTableImportBatch) {
            ConnectionSpec connection = nextTableImportBatch.getConnection();
            IncidentGroupingSpec incidentGrouping = connection.getIncidentGrouping();
            if (incidentGrouping == null || incidentGrouping.isDisabled()) {
                return null;
            }

            Table newCheckResults = nextTableImportBatch.getNewCheckResults();
            int minimumSeverityLevel = incidentGrouping.getMinimumSeverity().getSeverityLevel();
            IntColumn severityColumn = newCheckResults.intColumn(CheckResultsColumnNames.SEVERITY_COLUMN_NAME);
            InstantColumn executedAtColumn = newCheckResults.instantColumn(CheckResultsColumnNames.EXECUTED_AT_COLUMN_NAME);
            LongColumn checkResultIncidentHashColumn = newCheckResults.longColumn(CheckResultsColumnNames.INCIDENT_HASH_COLUMN_NAME);
            Selection selectionOfSeverityAlerts = severityColumn.isGreaterThanOrEqualTo(minimumSeverityLevel);
            List<NewIncidentNotificationMessage> newIncidentNotificationMessages = new ArrayList<>();

            if (selectionOfSeverityAlerts.isEmpty()) {
                return null; // no alerts with a severity at the threshold when we create incidents
            }

            if (this.incidentsSnapshot == null) {
                this.incidentsSnapshot = incidentsSnapshotFactory.createSnapshot(this.connectionName); // load previous snapshots
                this.allNewIncidentRows = this.incidentsSnapshot.getTableDataChanges().getNewOrChangedRows();
            }

            if (this.newIncidentByHashRowIndexes == null) {
                this.newIncidentByHashRowIndexes = new LinkedHashMap<>();
            }

            IntColumn failedChecksNewIncidentsColumn = this.allNewIncidentRows.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME);
            IntColumn highestSeverityNewIncidentsColumn = this.allNewIncidentRows.intColumn(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME);
            InstantColumn lastSeenNewIncidentsColumn = this.allNewIncidentRows.instantColumn(IncidentsColumnNames.LAST_SEEN_COLUMN_NAME);
            InstantColumn incidentUntilNewIncidentsColumn = this.allNewIncidentRows.instantColumn(IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME);

            int[] issuesRowIndexes = selectionOfSeverityAlerts.toArray();
            for (int i = 0; i < issuesRowIndexes.length; i++) {
                int checkResultRowIndex = issuesRowIndexes[i];
                Integer severity = severityColumn.get(checkResultRowIndex);

                if (severity < incidentGrouping.getMinimumSeverity().getSeverityLevel()) {
                    continue; // skipping this incident
                }

                Instant executedAt = executedAtColumn.get(checkResultRowIndex);
                Long incidentHash = checkResultIncidentHashColumn.get(checkResultRowIndex);

                int maxDaysToLoad = Math.max(incidentGrouping.getMaxIncidentLengthDays(), incidentGrouping.getMuteForDays());
                Instant earliestDateToLoad = executedAt.minus(maxDaysToLoad + 1, ChronoUnit.DAYS);

                boolean newMonthsLoaded = this.incidentsSnapshot.ensureMonthsAreLoaded(earliestDateToLoad.atOffset(ZoneOffset.UTC).toLocalDate(),
                        executedAt.atOffset(ZoneOffset.UTC).toLocalDate());
                if (newMonthsLoaded) {
                    this.allExistingIncidentRows = this.incidentsSnapshot.getAllData();
                    if (this.allExistingIncidentRows != null) {
                        this.existingIncidentByHashRowIndexes = findIncidentRowIndexes(this.allExistingIncidentRows);
                    }
                    else {
                        this.existingIncidentByHashRowIndexes = new LinkedHashMap<>();
                    }
                }

                Integer newOrUpdatedIncidentRowIndex = this.newIncidentByHashRowIndexes.get(incidentHash);
                if (newOrUpdatedIncidentRowIndex != null) {
                    // update the incident
                    int rowIndexInt = newOrUpdatedIncidentRowIndex;

                    if (!executedAt.isAfter(incidentUntilNewIncidentsColumn.get(rowIndexInt))) {
                        failedChecksNewIncidentsColumn.set(rowIndexInt, failedChecksNewIncidentsColumn.get(newOrUpdatedIncidentRowIndex) + 1);
                        Integer highestSeenSeverity = highestSeverityNewIncidentsColumn.get(newOrUpdatedIncidentRowIndex);
                        if (highestSeenSeverity < severity) {
                            highestSeverityNewIncidentsColumn.set(rowIndexInt, severity);
                        }
                        Instant lastSeen = lastSeenNewIncidentsColumn.get(rowIndexInt);
                        if (lastSeen.isBefore(executedAt)) {
                            lastSeenNewIncidentsColumn.set(rowIndexInt, executedAt);
                        }

                        continue;
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
                    Row newIncidentRow = this.allNewIncidentRows.appendRow();
                    int newIncidentRowIndex = newIncidentRow.getRowNumber();
                    this.newIncidentByHashRowIndexes.put(incidentHash, newIncidentRowIndex);
                    NewIncidentNotificationMessage newIncidentNotificationMessage = new NewIncidentNotificationMessage();
                    newIncidentNotificationMessages.add(newIncidentNotificationMessage);
                    newIncidentNotificationMessage.setNewIncidentRowIndex(newIncidentRowIndex);

                    UUID incidentIdUuid = new UUID(incidentHash, Hashing.combineOrdered(
                            new ArrayList<>() {{
                                add(Hashing.farmHashFingerprint64().hashString(connectionName, StandardCharsets.UTF_8));
                                add(Hashing.farmHashFingerprint64().hashLong(executedAt.toEpochMilli()));
                                add(HashCode.fromLong(incidentHash));
                            }}).asLong());

                    String incidentId = incidentIdUuid.toString();
                    newIncidentNotificationMessage.setIncidentId(incidentId);
                    newIncidentRow.setString(IncidentsColumnNames.ID_COLUMN_NAME, incidentId);

                    newIncidentNotificationMessage.setIncidentHash(incidentHash);
                    newIncidentRow.setLong(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME, incidentHash);

                    PhysicalTableName physicalTableName = nextTableImportBatch.getTable().getPhysicalTableName();
                    newIncidentNotificationMessage.setConnection(connectionName);
                    newIncidentNotificationMessage.setSchema(physicalTableName.getSchemaName());
                    newIncidentRow.setString(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME, physicalTableName.getSchemaName());
                    newIncidentNotificationMessage.setTable(physicalTableName.getTableName());
                    newIncidentRow.setString(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME, physicalTableName.getTableName());

                    Integer tablePriority = nextTableImportBatch.getTable().getPriority();
                    if (tablePriority != null) {
                        newIncidentRow.setInt(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME, tablePriority);
                        newIncidentNotificationMessage.setTablePriority(tablePriority);
                    }
                    if (incidentGrouping.isDivideByDataStream()) {
                        String dataStreamName = newCheckResults.getString(checkResultRowIndex, CheckResultsColumnNames.DATA_STREAM_NAME_COLUMN_NAME);
                        newIncidentRow.setString(IncidentsColumnNames.DATA_STREAM_NAME_COLUMN_NAME, dataStreamName);
                        newIncidentNotificationMessage.setDataStreamName(dataStreamName);
                    }

                    newIncidentNotificationMessage.setHighestSeverity(severity);
                    newIncidentRow.setInt(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME, severity);
                    newIncidentNotificationMessage.setFirstSeen(executedAt);
                    newIncidentRow.setInstant(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME, executedAt);
                    newIncidentRow.setInstant(IncidentsColumnNames.LAST_SEEN_COLUMN_NAME, executedAt);
                    newIncidentRow.setInstant(IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME,
                            executedAt.plus(incidentGrouping.getMaxIncidentLengthDays(), ChronoUnit.DAYS));
                    newIncidentRow.setInt(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME, 1);
                    newIncidentRow.setString(IncidentsColumnNames.STATUS_COLUMN_NAME, IncidentStatus.open.name());

                    IncidentGroupingLevel incidentGroupingLevel = incidentGrouping.getGroupingLevel();
                    if (incidentGroupingLevel.groupByDimension()) {
                        String qualityDimension = newCheckResults.getString(checkResultRowIndex, CheckResultsColumnNames.QUALITY_DIMENSION_COLUMN_NAME);
                        newIncidentRow.setString(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME, qualityDimension);
                        newIncidentNotificationMessage.setQualityDimension(qualityDimension);
                    }

                    if (incidentGroupingLevel.groupByCheckCategory()) {
                        String checkCategory = newCheckResults.getString(checkResultRowIndex, CheckResultsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
                        newIncidentRow.setString(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME, checkCategory);
                        newIncidentNotificationMessage.setCheckCategory(checkCategory);
                    }

                    if (incidentGroupingLevel.groupByCheckType()) {
                        String checkType = newCheckResults.getString(checkResultRowIndex, CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME);
                        newIncidentRow.setString(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME, checkType);
                        newIncidentNotificationMessage.setCheckType(checkType);
                    }

                    if (incidentGroupingLevel.groupByCheckName()) {
                        String checkName = newCheckResults.getString(checkResultRowIndex, CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME);
                        newIncidentRow.setString(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME, checkName);
                        newIncidentNotificationMessage.setCheckName(checkName);
                    }
                } else {
                    // copy the row for update and increment values...
                    int updatedIncidentRowIndex = this.allExistingIncidentRows.rowCount();
                    this.allExistingIncidentRows.copyRowsToTable(new int[] { existingOpenIncidentRowIndex }, this.allNewIncidentRows);
                    this.newIncidentByHashRowIndexes.put(incidentHash, updatedIncidentRowIndex);

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

            for (NewIncidentNotificationMessage newIncidentNotificationMessage : newIncidentNotificationMessages) {
                Integer failedChecksCount = this.allNewIncidentRows.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME)
                        .get(newIncidentNotificationMessage.getNewIncidentRowIndex());
                newIncidentNotificationMessage.setFailedChecksCount(failedChecksCount);
            }

            return newIncidentNotificationMessages;
        }

        /**
         * Finds row indexes of all existing incidents identified by an incident hash.
         * @param incidentTable Incident table with all existing incidents.
         * @return Map indexed by the incident hash, which contains an integer list of row indexes in the <code>incidentTable</code> table with that hash.
         */
        public Map<Long, IntArrayList> findIncidentRowIndexes(Table incidentTable) {
            Map<Long, IntArrayList> resultMap = new LinkedHashMap<>();

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
    }
}
