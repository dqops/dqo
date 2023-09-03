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
package com.dqops.services.check.mapping;

import com.dqops.checks.*;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.ExecutionContext;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.definitions.checks.CheckDefinitionSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.services.check.mapping.basicmodels.CheckContainerBasicModel;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mapping.models.FieldModel;
import com.dqops.utils.reflection.FieldInfo;

import java.util.List;

/**
 * Service that creates a model from the data quality check specifications,
 * enabling transformation from the storage model (YAML compliant) to a UI friendly model.
 */
public interface SpecToModelCheckMappingService {
    /**
     * Creates a model of the whole checks container of table level or column level data quality checks, divided into categories.
     * @param checkCategoriesSpec Table or column level data quality checks container of type profiling, monitoring or partitioned check (for a specific timescale).
     * @param runChecksTemplate Check search filter for the parent table or column that is used as a template to create more fine-grained "run checks" job configurations. Also determines which checks will be included in the model.
     * @param connectionSpec Connection specification for the connection to which the table belongs to.
     * @param tableSpec Table specification with the configuration of the parent table.
     * @param executionContext Execution context with a reference to both the DQO Home (with default sensor implementation) and DQO User (with user specific sensors).
     * @param providerType Provider type from the parent connection.
     * @param canManageChecks The user is an operator and can run any operation.
     * @return Model of data quality checks' container.
     */
    CheckContainerModel createModel(AbstractRootChecksContainerSpec checkCategoriesSpec,
                                    CheckSearchFilters runChecksTemplate,
                                    ConnectionSpec connectionSpec,
                                    TableSpec tableSpec,
                                    ExecutionContext executionContext,
                                    ProviderType providerType,
                                    boolean canManageChecks);

    /**
     * Creates a simplistic model of every data quality check on table level or column level, divided into categories.
     *
     * @param checkCategoriesSpec Table or column level data quality checks container of type profiling, monitoring or partitioned check (for a specific timescale).
     * @param executionContext Execution context with a reference to both the DQO Home (with default sensor implementation) and DQO User (with user specific sensors).
     * @param providerType Provider type from the parent connection.
     * @param canManageChecks The user is an operator and can run any operation.
     * @return Simplistic model of data quality checks' container.
     */
    CheckContainerBasicModel createBasicModel(AbstractRootChecksContainerSpec checkCategoriesSpec,
                                              ExecutionContext executionContext,
                                              ProviderType providerType,
                                              boolean canManageChecks);

    /**
     * Creates a list of fields to edit all values in the sensor parameters specification.
     * @param parametersSpec Sensor parameters specification.
     * @return List of fields for all sensor parameter fields.
     */
    List<FieldModel> createFieldsForSensorParameters(AbstractSensorParametersSpec parametersSpec);

    /**
     * Creates a model for a single data quality check.
     * @param checkFieldInfo            Reflection info of the field in the parent object that stores the check specification field value.
     * @param customCheckDefinitionSpec Check definition specification for custom checks. When it is given, the <code>checkFieldInfo</code> parameter is null and the check specification is used instead.
     * @param checkSpec                 Check specification instance retrieved from the object.
     * @param scheduleGroup             Scheduling group relevant to this check.
     * @param runChecksCategoryTemplate "run check" job configuration for the parent category, used to create templates for each check.
     * @param tableSpec                 Table specification with the configuration of the parent table.
     * @param executionContext          Execution context with a reference to both the DQO Home (with default sensor implementation) and DQO User (with user specific sensors).
     * @param providerType              Provider type from the parent connection.
     * @param checkTarget               Check target.
     * @param checkType                 Check type (profiling, recurring, ...).
     * @param checkTimeScale            Check time scale: null for profiling, daily/monthly for others that apply the date truncation.
     * @param canManageChecks           The user is an operator and can run any operation.
     * @return Check model.
     */
    CheckModel createCheckModel(FieldInfo checkFieldInfo,
                                CheckDefinitionSpec customCheckDefinitionSpec,
                                AbstractCheckSpec<?, ?, ?, ?> checkSpec,
                                CheckRunScheduleGroup scheduleGroup,
                                CheckSearchFilters runChecksCategoryTemplate,
                                TableSpec tableSpec,
                                ExecutionContext executionContext,
                                ProviderType providerType,
                                CheckTarget checkTarget,
                                CheckType checkType,
                                CheckTimeScale checkTimeScale,
                                boolean canManageChecks);

    /**
     * Creates a list of fields to edit all values in the rule parameters specification.
     * @param ruleParametersSpec Rule parameters specification.
     * @return List of fields for all rule parameter fields.
     */
    List<FieldModel> createFieldsForRuleParameters(AbstractRuleParametersSpec ruleParametersSpec);
}
