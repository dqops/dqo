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

import ai.dqo.data.statistics.models.StatisticsResultsFragmentFilter;
import ai.dqo.data.statistics.snapshot.StatisticsSnapshot;
import ai.dqo.data.statistics.snapshot.StatisticsSnapshotFactory;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.utils.datetime.LocalDateTimeTruncateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

/**
 * Service that deletes statistics data from parquet files.
 */
@Service
public class StatisticsResultsDeleteServiceImpl implements StatisticsResultsDeleteService {
    private StatisticsSnapshotFactory statisticsResultsSnapshotFactory;

    @Autowired
    public StatisticsResultsDeleteServiceImpl(StatisticsSnapshotFactory statisticsResultsSnapshotFactory) {
        this.statisticsResultsSnapshotFactory = statisticsResultsSnapshotFactory;
    }

    /**
     * Deletes the statistics results from a table, applying specific filters to get the fragment (if necessary).
     * @param filter Filter for the statistics results fragment that is of interest.
     */
    @Override
    public void deleteSelectedStatisticsResultsFragment(StatisticsResultsFragmentFilter filter) {
        Map<String, String> conditions = filter.getColumnConditions();
        StatisticsSnapshot currentSnapshot = this.statisticsResultsSnapshotFactory.createSnapshot(
                filter.getTableSearchFilters().getConnectionName(),
                PhysicalTableName.fromSchemaTableFilter(filter.getTableSearchFilters().getSchemaTableName())
        );

        LocalDate startDeletionRange = filter.getDateStart();
        LocalDate endDeletionRange = filter.getDateEnd();

        currentSnapshot.markSelectedForDeletion(startDeletionRange, endDeletionRange, conditions);
        currentSnapshot.save();
    }
}
