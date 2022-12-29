/*
 * Copyright © 2022 DQO.ai (support@dqo.ai)
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

package ai.dqo.data.ruleresults.services;

import ai.dqo.data.ruleresults.services.models.RuleResultsFragmentFilter;
import ai.dqo.data.ruleresults.snapshot.RuleResultsSnapshot;
import ai.dqo.data.storage.*;
import ai.dqo.metadata.sources.PhysicalTableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.Table;
import tech.tablesaw.selection.Selection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RuleResultsDeleteServiceImpl implements RuleResultsDeleteService {

    private final String ID_COLUMN_NAME = "id";
    private final String TIME_SERIES_COLUMN_NAME = "time_period";

    private ParquetPartitionStorageService parquetPartitionStorageService;

    @Autowired
    public RuleResultsDeleteServiceImpl(ParquetPartitionStorageService parquetPartitionStorageService) {
        this.parquetPartitionStorageService = parquetPartitionStorageService;
    }

    /**
     * Deletes the results from a table, applying specific filters to get the fragment.
     * @param filter Filter for the result fragment that is of interest.
     */
    @Override
    public void deleteSelectedRuleResultsFragment(RuleResultsFragmentFilter filter) {
        this.validateRuleResultsFragmentFilter(filter);

        Map<String, String> conditions = filter.getColumnConditions();
        List<String> columnNames = new ArrayList<>(conditions.keySet());
        columnNames.add(ID_COLUMN_NAME);
        if (!filter.isIgnoreDateDay()) {
            columnNames.add(TIME_SERIES_COLUMN_NAME);
        }

        FileStorageSettings fileStorageSettings = RuleResultsSnapshot.createRuleResultsStorageSettings();

        Map<ParquetPartitionId, LoadedMonthlyPartition> presentData =
                parquetPartitionStorageService.loadPartitionsForMonthsRange(
                        filter.getTableSearchFilters().getConnectionName(),
                        PhysicalTableName.fromSchemaTableFilter(
                                filter.getTableSearchFilters().getSchemaTableName()),
                        filter.getDateStart(),
                        filter.getDateEnd(),
                        fileStorageSettings,
                        columnNames.toArray(new String[0])
                );

        for (LoadedMonthlyPartition loadedMonthlyPartition : presentData.values()) {
            Table monthlyPartitionTable = loadedMonthlyPartition.getData();
            Selection toDelete = Selection.withRange(0, monthlyPartitionTable.rowCount());

            // Filter by accurate date.
            if (!filter.isIgnoreDateDay()) {
                toDelete = toDelete.and(
                        monthlyPartitionTable.dateColumn(TIME_SERIES_COLUMN_NAME)
                                .isBetweenIncluding(filter.getDateStart(), filter.getDateEnd()));
            }

            // Filter by string columns' conditions.
            for (Map.Entry<String, String> conditionEntry : conditions.entrySet()) {
                String colName = conditionEntry.getKey();
                String colValue = conditionEntry.getValue();
                toDelete = toDelete.and(
                        monthlyPartitionTable.stringColumn(colName)
                                .isEqualTo(colValue));
            }

            List<String> idsToDelete = monthlyPartitionTable.stringColumn(ID_COLUMN_NAME).where(toDelete).asList();

            if (!idsToDelete.isEmpty()) {
                TableDataChanges changes = new TableDataChanges(null);
                changes.getDeletedIds().addAll(idsToDelete);
                this.parquetPartitionStorageService.savePartition(loadedMonthlyPartition, changes, fileStorageSettings);
            }
        }
    }

    /**
     * Validation method for RuleResultsFragmentFilter objects. Throws if something is not ok.
     * @param target The RuleResultsFragmentFilter object to validate.
     */
    protected void validateRuleResultsFragmentFilter(RuleResultsFragmentFilter target) {
        if (target == null) {
            throw new NullPointerException("RuleResultsFragmentFilter cannot be null");
        }

        if (target.getTableSearchFilters() == null) {
            throw new NullPointerException("TableSearchFilters cannot be null");
        }

        if (target.getDateStart() == null || target.getDateEnd() == null) {
            throw new NullPointerException("dateStart and dateEnd cannot be null");
        }
    }
}
