/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.data.statistics.services;

import ai.dqo.data.statistics.factory.StatisticsColumnNames;
import ai.dqo.data.statistics.models.StatisticsResultsFragmentFilter;
import ai.dqo.data.statistics.snapshot.StatisticsSnapshot;
import ai.dqo.data.statistics.snapshot.StatisticsSnapshotFactory;
import ai.dqo.metadata.sources.PhysicalTableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Service that deletes statistics data from parquet files.
 */
@Service
public class StatisticsDeleteServiceImpl implements StatisticsDeleteService {
    private StatisticsSnapshotFactory statisticsSnapshotFactory;

    @Autowired
    public StatisticsDeleteServiceImpl(StatisticsSnapshotFactory statisticsSnapshotFactory) {
        this.statisticsSnapshotFactory = statisticsSnapshotFactory;
    }

    /**
     * Deletes the statistics results from a table, applying specific filters to get the fragment (if necessary).
     * @param filter Filter for the statistics results fragment that is of interest.
     */
    @Override
    public void deleteSelectedStatisticsResultsFragment(StatisticsResultsFragmentFilter filter) {
        Map<String, String> simpleConditions = filter.getColumnConditions();
        Map<String, Set<String>> conditions = new HashMap<>();
        for (Map.Entry<String, String> kv: simpleConditions.entrySet()) {
            String columnName = kv.getKey();
            String columnValue = kv.getValue();
            Set<String> wrappedValue = new HashSet<>(){{add(columnValue);}};
            conditions.put(columnName, wrappedValue);
        }
        if (filter.getColumnNames() != null && !filter.getColumnNames().isEmpty()) {
            conditions.put(StatisticsColumnNames.COLUMN_NAME_COLUMN_NAME, new HashSet<>(filter.getColumnNames()));
        }

        StatisticsSnapshot currentSnapshot = this.statisticsSnapshotFactory.createSnapshot(
                filter.getTableSearchFilters().getConnectionName(),
                PhysicalTableName.fromSchemaTableFilter(filter.getTableSearchFilters().getSchemaTableName())
        );

        LocalDate startDeletionRange = filter.getDateStart();
        LocalDate endDeletionRange = filter.getDateEnd();

        currentSnapshot.markSelectedForDeletion(startDeletionRange, endDeletionRange, conditions);
        currentSnapshot.save();
    }
}
