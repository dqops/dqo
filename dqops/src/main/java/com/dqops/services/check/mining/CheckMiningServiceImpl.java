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

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.ExecutionContext;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.StringPatternComparer;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mapping.models.QualityCategoryModel;
import com.dqops.services.check.mapping.models.RuleParametersModel;
import com.dqops.services.check.matching.SimilarCheckModel;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.serialization.JsonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service that proposes a configuration of data quality checks for a table and a target check type. This is a data quality rule mining engine.
 */
@Service
@Slf4j
public class CheckMiningServiceImpl implements CheckMiningService {
    private final SpecToModelCheckMappingService specToModelCheckMappingService;
    private final TableProfilingResultsReadService tableProfilingResultsReadService;
    private final JsonSerializer jsonSerializer;

    /**
     * Dependency injection constructor that receives dependencies of the service.
     * @param specToModelCheckMappingService Specification to model mapping service.
     * @param tableProfilingResultsReadService Service that loads statistics and profiling check results.
     * @param jsonSerializer Json serializer, used to convert sensor parameters and rule parameters to the target type by serializing and deserializing.
     */
    @Autowired
    public CheckMiningServiceImpl(SpecToModelCheckMappingService specToModelCheckMappingService,
                                  TableProfilingResultsReadService tableProfilingResultsReadService,
                                  JsonSerializer jsonSerializer) {
        this.specToModelCheckMappingService = specToModelCheckMappingService;
        this.tableProfilingResultsReadService = tableProfilingResultsReadService;
        this.jsonSerializer = jsonSerializer;
    }

    /**
     * Proposes a configuration of data quality checks for the given table and the target check type and time scale.
     * @param connectionSpec Connection specification of the connection to the data source. Also identifies the provider type to propose checks supported by that provider.
     * @param tableSpec Table specification for which a list of checks is suggested.
     * @param executionContext Execution context to provide access to the user home and DQOps home.
     * @param checkType Target check type to propose.
     * @param checkTimeScale Target check time scale.
     * @param miningParameters Check mining parameters.
     * @return Check mining proposal.
     */
    @Override
    public CheckMiningProposalModel proposeChecks(
            ConnectionSpec connectionSpec,
            TableSpec tableSpec,
            ExecutionContext executionContext,
            CheckType checkType,
            CheckTimeScale checkTimeScale,
            CheckMiningParametersModel miningParameters) {
        CheckMiningProposalModel checkProposalModel = new CheckMiningProposalModel();
        TableSpec clonedTableSpec = tableSpec.deepClone();

        TableProfilingResults tableProfilingResults = this.tableProfilingResultsReadService.loadTableProfilingResults(
                executionContext, connectionSpec, clonedTableSpec);

        AbstractRootChecksContainerSpec tableCheckRootContainer = clonedTableSpec.getTableCheckRootContainer(
                checkType, checkTimeScale, false, true);

        if (Strings.isNullOrEmpty(miningParameters.getColumnNameFilter())) {
            CheckContainerModel proposedTableChecks = this.proposeChecksForContainer(tableCheckRootContainer, tableProfilingResults.getTableProfilingResults(),
                    tableProfilingResults, connectionSpec, clonedTableSpec, executionContext, miningParameters);
            checkProposalModel.setTableChecks(proposedTableChecks);
        } else {
            checkProposalModel.setTableChecks(new CheckContainerModel()); // a column filter given, no table level checks proposed
        }

        for (ColumnSpec columnSpec : clonedTableSpec.getColumns().values()) {
            AbstractRootChecksContainerSpec columnCheckRootContainer = columnSpec.getColumnCheckRootContainer(
                    checkType, checkTimeScale, false, true);
            String columnName = columnSpec.getColumnName();

            if (!Strings.isNullOrEmpty(miningParameters.getColumnNameFilter())) {
                if (!StringPatternComparer.matchSearchPattern(columnName, miningParameters.getColumnNameFilter())) {
                    continue;
                }
            }

            DataAssetProfilingResults columnProfilingResults = tableProfilingResults.getColumnProfilingResults(columnName);
            CheckContainerModel proposedColumnChecks = this.proposeChecksForContainer(columnCheckRootContainer, columnProfilingResults,
                    tableProfilingResults, connectionSpec, clonedTableSpec, executionContext, miningParameters);
            checkProposalModel.getColumnChecks().put(columnName, proposedColumnChecks);
        }

        return checkProposalModel;
    }

    /**
     * Proposes data quality checks for a given target check container. It can be a table level check container or a column level check container.
     * @param targetCheckRootContainer Target check container for which we are generating checks. Already configured checks are excluded from the proposal.
     * @param dataAssetProfilingResults Previous data asset profiling results from basic profiling results and profiling checks. Their sensor values are used to propose check parameters.
     * @param tableProfilingResults All table profiling results (in case that the row count is required or some other measures).
     * @param connectionSpec Parent connection specification.
     * @param tableSpec Table specification.
     * @param executionContext Execution context to get access to the whole user home.
     * @param miningParameters Check mining parameters.
     * @return Check container model, limited only to proposed checks.
     */
    public CheckContainerModel proposeChecksForContainer(
            AbstractRootChecksContainerSpec targetCheckRootContainer,
            DataAssetProfilingResults dataAssetProfilingResults,
            TableProfilingResults tableProfilingResults,
            ConnectionSpec connectionSpec,
            TableSpec tableSpec,
            ExecutionContext executionContext,
            CheckMiningParametersModel miningParameters) {
        CheckContainerModel targetModel = this.specToModelCheckMappingService.createModel(
                targetCheckRootContainer, new CheckSearchFilters(), connectionSpec, tableSpec, executionContext,
                connectionSpec.getProviderType(), true);
        targetModel.dropConfiguredChecks();

        for (QualityCategoryModel categoryModel : new ArrayList<>(targetModel.getCategories())) {
            if (!Strings.isNullOrEmpty(miningParameters.getCategoryFilter())) {
                if (!StringPatternComparer.matchSearchPattern(categoryModel.getCategory(), miningParameters.getCategoryFilter())) {
                    targetModel.getCategories().remove(categoryModel);
                    continue;
                }
            }

            List<CheckModel> listOfChecksInCategory = categoryModel.getChecks();
            for (CheckModel checkModel : new ArrayList<>(listOfChecksInCategory)) {
                if (!Strings.isNullOrEmpty(miningParameters.getCheckNameFilter())) {
                    if (!StringPatternComparer.matchSearchPattern(checkModel.getCheckName(), miningParameters.getCheckNameFilter())) {
                        listOfChecksInCategory.remove(checkModel);
                        continue;
                    }
                }

                AbstractCheckSpec<?, ?, ?, ?> checkSpec = checkModel.getCheckSpec();
                SimilarCheckModel similarProfilingCheck = checkModel.getSimilarProfilingCheck();
                ProfilingCheckResult profilingCheckByCheckName = similarProfilingCheck != null ?
                        dataAssetProfilingResults.getProfilingCheckByCheckName(similarProfilingCheck.getCheckName(), true) : null;
                boolean checkWasConfigured = false;

                try {
                    // let the check configure itself
                    checkWasConfigured = checkSpec.proposeCheckConfiguration(
                            profilingCheckByCheckName, dataAssetProfilingResults, tableProfilingResults, tableSpec,
                            targetCheckRootContainer, checkModel, miningParameters, this.jsonSerializer);
                }
                catch (Exception ex) {
                    checkWasConfigured = false;
                    log.error("The rule mining engine failed to configure the check '" + checkModel.getCheckName() + "', error: " + ex.getMessage(), ex);
                }

                if (checkWasConfigured) {
                    CheckModel updatedCheckModel = this.specToModelCheckMappingService.createCheckModel(
                            checkModel.getCheckFieldInfo(), checkModel.getCustomCheckDefinitionSpec(),
                            checkSpec, checkModel.getScheduleGroup(), checkModel.getRunChecksJobTemplate(),
                            tableSpec, executionContext, connectionSpec.getProviderType(), targetCheckRootContainer.getCheckTarget(),
                            targetCheckRootContainer.getCheckType(), targetCheckRootContainer.getCheckTimeScale(), checkModel.isCanEdit());
                    updatedCheckModel.setConfigured(true);

                    listOfChecksInCategory.set(listOfChecksInCategory.indexOf(checkModel), updatedCheckModel);
                } else {
                    listOfChecksInCategory.remove(checkModel); // this check was not configured by the rule mining engine
                }
            }
        }

        targetModel.dropEmptyCategories();

        return targetModel;
    }
}
