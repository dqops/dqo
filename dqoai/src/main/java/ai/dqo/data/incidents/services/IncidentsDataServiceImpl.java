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
package ai.dqo.data.incidents.services;

import ai.dqo.data.incidents.factory.IncidentStatus;
import ai.dqo.data.incidents.factory.IncidentsColumnNames;
import ai.dqo.data.incidents.services.models.IncidentModel;
import ai.dqo.data.incidents.snapshot.IncidentsSnapshot;
import ai.dqo.data.incidents.snapshot.IncidentsSnapshotFactory;
import ai.dqo.data.storage.LoadedMonthlyPartition;
import ai.dqo.data.storage.ParquetPartitionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;
import tech.tablesaw.selection.Selection;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Data quality incident management service. Supports reading incidents from parquet tables.
 */
@Service
public class IncidentsDataServiceImpl implements IncidentsDataService {
    private IncidentsSnapshotFactory incidentsSnapshotFactory;

    /**
     * Creates a new incident data service, given all required dependencies.
     * @param incidentsSnapshotFactory Incident snapshot factory.
     */
    @Autowired
    public IncidentsDataServiceImpl(IncidentsSnapshotFactory incidentsSnapshotFactory) {
        this.incidentsSnapshotFactory = incidentsSnapshotFactory;
    }

    /**
     * Loads recent incidents on a connection.
     * @param connectionName Connection name.
     * @param filterParameters Incident filter parameters.
     * @return Collection of recent incidents.
     */
    @Override
    public Collection<IncidentModel> loadRecentIncidentsOnConnection(
            String connectionName, IncidentListFilterParameters filterParameters) {
        IncidentsSnapshot incidentsSnapshot = this.incidentsSnapshotFactory.createSnapshot(connectionName);
        LocalDate endDate = Instant.now().atOffset(ZoneOffset.UTC).toLocalDate();
        LocalDate startDate = endDate.minusMonths(filterParameters.getRecentMonths() - 1);
        if (!incidentsSnapshot.ensureMonthsAreLoaded(startDate, endDate)) {
            return new ArrayList<>(); // no results
        }

        int currentRowIndex = 0;
        int startRowIndexInPage = (filterParameters.getPage() - 1) + filterParameters.getLimit();
        int untilRowIndexInPage = filterParameters.getPage() + filterParameters.getLimit();

        ArrayList<IncidentModel> incidentModels = new ArrayList<>();
        Map<ParquetPartitionId, LoadedMonthlyPartition> loadedMonthlyPartitions = incidentsSnapshot.getLoadedMonthlyPartitions();
        for (Map.Entry<ParquetPartitionId, LoadedMonthlyPartition> partitionEntry : loadedMonthlyPartitions.entrySet()) {
            Table partitionTable = partitionEntry.getValue().getData();
            if (partitionTable == null) {
                // empty partition
                continue;
            }

            StringColumn incidentIdColumn = partitionTable.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME);
            StringColumn schemaColumn = partitionTable.stringColumn(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME);
            StringColumn tableColumn = partitionTable.stringColumn(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME);
            IntColumn tablePriorityColumn = partitionTable.intColumn(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME);
            LongColumn incidentHashColumn = partitionTable.longColumn(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME);
            InstantColumn firstSeenColumn = partitionTable.instantColumn(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME);
            InstantColumn lastSeenColumn = partitionTable.instantColumn(IncidentsColumnNames.LAST_SEEN_COLUMN_NAME);
            InstantColumn incidentUntilColumn = partitionTable.instantColumn(IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME);
            StringColumn dataStreamNameColumn = partitionTable.stringColumn(IncidentsColumnNames.DATA_STREAM_NAME_COLUMN_NAME);
            StringColumn qualityDimensionColumn = partitionTable.stringColumn(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME);
            StringColumn checkCategoryColumn = partitionTable.stringColumn(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
            StringColumn checkTypeColumn = partitionTable.stringColumn(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME);
            StringColumn checkNameColumn = partitionTable.stringColumn(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME);
            IntColumn highestSeverityColumn = partitionTable.intColumn(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME);
            IntColumn failedChecksCountColumn = partitionTable.intColumn(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME);
            StringColumn statusColumn = partitionTable.stringColumn(IncidentsColumnNames.STATUS_COLUMN_NAME);

            int partitionYear = partitionEntry.getKey().getMonth().getYear();
            int partitionMonth = partitionEntry.getKey().getMonth().getMonthValue();

            int rowCount = partitionTable.rowCount();
            for (int rowIndex = rowCount - 1; rowIndex >= 0; rowIndex--) {
                String status = statusColumn.get(rowIndex);
                IncidentStatus incidentStatus = IncidentStatus.valueOf(status);

                if (!filterParameters.isLoadResolvedAndMutedIncidents() &&
                        (incidentStatus == IncidentStatus.resolved || incidentStatus == IncidentStatus.muted)) {
                    continue; // skipping
                }

                currentRowIndex++;
                if (currentRowIndex < startRowIndexInPage) {
                    continue;
                }

                if (currentRowIndex > untilRowIndexInPage) {
                    break;
                }

                IncidentModel incidentModel = new IncidentModel();
                incidentModel.setIncidentId(incidentIdColumn.get(rowIndex));
                incidentModel.setYear(partitionYear);
                incidentModel.setMonth(partitionMonth);
                incidentModel.setConnection(connectionName);
                incidentModel.setSchema(schemaColumn.get(rowIndex));
                incidentModel.setTable(tableColumn.get(rowIndex));
                if (!tablePriorityColumn.isMissing(rowIndex)) {
                    incidentModel.setTablePriority(tablePriorityColumn.get(rowIndex));
                }
                incidentModel.setIncidentHash(incidentHashColumn.get(rowIndex));
                Instant firstSeen = firstSeenColumn.get(rowIndex);
                incidentModel.setFirstSeen(firstSeen);
                incidentModel.setLastSeen(lastSeenColumn.get(rowIndex));
                incidentModel.setIncidentUntil(incidentUntilColumn.get(rowIndex));
                if (!dataStreamNameColumn.isMissing(rowIndex)) {
                    incidentModel.setDataStreamName(dataStreamNameColumn.get(rowIndex));
                }
                if (!qualityDimensionColumn.isMissing(rowIndex)) {
                    incidentModel.setQualityDimension(qualityDimensionColumn.get(rowIndex));
                }
                if (!checkCategoryColumn.isMissing(rowIndex)) {
                    incidentModel.setCheckCategory(checkCategoryColumn.get(rowIndex));
                }
                if (!checkTypeColumn.isMissing(rowIndex)) {
                    incidentModel.setCheckType(checkTypeColumn.get(rowIndex));
                }
                if (!checkNameColumn.isMissing(rowIndex)) {
                    incidentModel.setCheckName(checkNameColumn.get(rowIndex));
                }
                incidentModel.setHighestSeverity(highestSeverityColumn.get(rowIndex));
                incidentModel.setFailedChecksCount(failedChecksCountColumn.get(rowIndex));
                incidentModel.setStatus(incidentStatus);
                incidentModels.add(incidentModel);
            }

            if (currentRowIndex >= untilRowIndexInPage) {
                break; // no need to scan another partition file
            }
        }

        return incidentModels;
    }

    /**
     * Loads one incident.
     *
     * @param connectionName Connection name on which the incident was raised.
     * @param year           Year when the incident was first seen.
     * @param month          Month of year when the incident was first seen.
     * @param incidentId     Incident id.
     * @return Incident model when the incident was found or null when the incident is not found.
     */
    @Override
    public IncidentModel loadIncident(String connectionName, int year, int month, String incidentId) {
        IncidentsSnapshot incidentsSnapshot = this.incidentsSnapshotFactory.createSnapshot(connectionName);
        LocalDate incidentMonth = LocalDate.of(year, month, 1);
        LoadedMonthlyPartition monthPartition = incidentsSnapshot.getMonthPartition(incidentMonth, true);
        if (monthPartition == null || monthPartition.getData() == null || monthPartition.getData().rowCount() == 0) {
            return null; // no data
        }

        Table incidentMonthData = monthPartition.getData();
        StringColumn incidentIdColumn = incidentMonthData.stringColumn(IncidentsColumnNames.ID_COLUMN_NAME);
        Selection incidentIdSelection = incidentIdColumn.isEqualTo(incidentId);
        if (incidentIdSelection.isEmpty()) {
            return null;
        }

        int rowIndex = incidentIdSelection.get(0);
        IncidentModel incidentModel = IncidentModel.fromIncidentRow(incidentMonthData.row(rowIndex), connectionName);
        return incidentModel;
    }
}
