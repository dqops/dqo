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

package com.dqops.services.check.mining;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckType;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.checkresults.services.CheckResultsDataService;
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.data.statistics.models.StatisticsResultsForTableModel;
import com.dqops.data.statistics.services.StatisticsDataService;
import com.dqops.execution.ExecutionContext;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

/**
 * Service that loads the results from profiling checks and statistics, to be used for data quality rule mining (suggesting the rule thresholds for checks).
 */
@Service
public class TableProfilingResultsReadServiceImpl implements TableProfilingResultsReadService {
    private final SpecToModelCheckMappingService specToModelCheckMappingService;
    private final StatisticsDataService statisticsDataService;
    private final CheckResultsDataService checkResultsDataService;
    private final DefaultTimeZoneProvider defaultTimeZoneProvider;

    /**
     * DI constructor.
     * @param specToModelCheckMappingService Specification to check model mapping service.
     * @param statisticsDataService Statistics loading service.
     * @param checkResultsDataService Check results loading service.
     * @param defaultTimeZoneProvider Default time zone provider.
     */
    @Autowired
    public TableProfilingResultsReadServiceImpl(
            SpecToModelCheckMappingService specToModelCheckMappingService,
            StatisticsDataService statisticsDataService,
            CheckResultsDataService checkResultsDataService,
            DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.specToModelCheckMappingService = specToModelCheckMappingService;
        this.statisticsDataService = statisticsDataService;
        this.checkResultsDataService = checkResultsDataService;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    /**
     * Loads all profiling results and statistics for a table. The data is organized by check names.
     * @param executionContext Execution context with access to the user home.
     * @param connectionSpec Connection specification.
     * @param tableSpec Table specification of the table that is analyzed.
     * @return All loaded results for a table.
     */
    @Override
    public TableProfilingResults loadTableProfilingResults(ExecutionContext executionContext,
                                                           ConnectionSpec connectionSpec,
                                                           TableSpec tableSpec) {
        UserDomainIdentity userDomainIdentity = executionContext.getUserHomeContext().getUserIdentity();
        TableProfilingResults tableProfilingResults = this.checkResultsDataService.loadProfilingChecksResultsForTable(
                tableSpec, userDomainIdentity);
        tableProfilingResults.setMissingProfilingChecksResults(true); // we will change it to false when we find any profiling results

        CheckSearchFilters checkSearchFilters = new CheckSearchFilters();
        AbstractRootChecksContainerSpec tableProfilingChecksContainer = tableSpec.getTableCheckRootContainer(
                CheckType.profiling, null, false, true);
        CheckContainerModel tableChecksModel = this.specToModelCheckMappingService.createModel(tableProfilingChecksContainer, checkSearchFilters,
                connectionSpec, tableSpec, executionContext, connectionSpec.getProviderType(), true);
        tableChecksModel.removeComparisonCategory();
        DataAssetProfilingResults tableAssetProfilingResults = tableProfilingResults.getTableProfilingResults();
        if (tableAssetProfilingResults.hasAnyProfilingChecksResults()) {
            tableProfilingResults.setMissingProfilingChecksResults(false);
        }
        tableAssetProfilingResults.importChecksModels(tableChecksModel);

        for (ColumnSpec columnSpec : tableSpec.getColumns().values()) {
            AbstractRootChecksContainerSpec columnProfilingChecksContainer = columnSpec.getColumnCheckRootContainer(
                    CheckType.profiling, null, false, true);

            CheckContainerModel columnChecksModel = this.specToModelCheckMappingService.createModel(columnProfilingChecksContainer, checkSearchFilters,
                    connectionSpec, tableSpec, executionContext, connectionSpec.getProviderType(), true);
            columnChecksModel.removeComparisonCategory();

            DataAssetProfilingResults columnAssetProfilingResultsContainer = tableProfilingResults.getColumnProfilingResults(columnSpec.getColumnName());
            if (columnAssetProfilingResultsContainer.hasAnyProfilingChecksResults()) {
                tableProfilingResults.setMissingProfilingChecksResults(false);
            }
            columnAssetProfilingResultsContainer.importChecksModels(columnChecksModel);
        }

        StatisticsResultsForTableModel mostRecentStatisticsForTable = this.statisticsDataService.getMostRecentStatisticsForTable(connectionSpec.getConnectionName(),
                tableSpec.getPhysicalTableName(), CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME, true, userDomainIdentity);

        ZoneId defaultTimeZoneId = this.defaultTimeZoneProvider.getDefaultTimeZoneId(executionContext.getUserHomeContext());
        tableProfilingResults.importStatistics(mostRecentStatisticsForTable, defaultTimeZoneId);
        tableProfilingResults.calculateMissingNotNullCounts();

        return tableProfilingResults;
    }
}
