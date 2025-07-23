/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
import com.dqops.metadata.dictionaries.DictionaryWrapper;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Set;

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
     * @param importStatistics Import statistics to be used by the rule miner. Without the statistics, the miner can only configure current checks or copy profiling checks.
     * @param importDefaultChecks Imports the results of default checks. When we disable it, the rule miner will not see their results and will not propose configuring them. It is important when configuring the monitoring and partition checks to not copy them.
     * @return All loaded results for a table.
     */
    @Override
    public TableProfilingResults loadTableProfilingResults(ExecutionContext executionContext,
                                                           ConnectionSpec connectionSpec,
                                                           TableSpec tableSpec,
                                                           boolean importStatistics,
                                                           boolean importDefaultChecks) {
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
        UserDomainIdentity userDomainIdentity = userHomeContext.getUserIdentity();
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
        if (!importDefaultChecks) {
            tableAssetProfilingResults.removeChecksAppliedByPatterns();
        }

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
            if (!importDefaultChecks) {
                columnAssetProfilingResultsContainer.removeChecksAppliedByPatterns();
            }
        }

        ZoneId defaultTimeZoneId = this.defaultTimeZoneProvider.getDefaultTimeZoneId(userHomeContext);
        tableProfilingResults.setTimeZoneId(defaultTimeZoneId);

        if (importStatistics) {
            StatisticsResultsForTableModel mostRecentStatisticsForTable = this.statisticsDataService.getMostRecentStatisticsForTable(connectionSpec.getConnectionName(),
                    tableSpec.getPhysicalTableName(), CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME, true, userDomainIdentity);
            tableProfilingResults.importStatistics(mostRecentStatisticsForTable);
        }

        tableProfilingResults.calculateMissingNotNullCounts();

        for (DictionaryWrapper dictionaryWrapper : userHomeContext.getUserHome().getDictionaries()) {
            String dictionaryName = dictionaryWrapper.getDictionaryName();
            Set<String> dictionaryEntries = dictionaryWrapper.getDictionaryEntries();
            tableProfilingResults.getDictionaries().put(dictionaryName, dictionaryEntries);
        }

        return tableProfilingResults;
    }
}
