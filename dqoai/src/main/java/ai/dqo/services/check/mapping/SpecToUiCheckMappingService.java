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
package ai.dqo.services.check.mapping;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.services.check.mapping.models.UICheckContainerModel;
import ai.dqo.services.check.mapping.models.UIFieldModel;
import ai.dqo.services.check.mapping.basicmodels.UICheckContainerBasicModel;
import ai.dqo.rules.AbstractRuleParametersSpec;
import ai.dqo.sensors.AbstractSensorParametersSpec;

import java.util.List;

/**
 * Service that creates a UI friendly model from the data quality check specifications,
 * enabling transformation from the storage model (YAML compliant) to a UI friendly model.
 */
public interface SpecToUiCheckMappingService {
    /**
     * Creates a UI friendly model of the whole checks container of table level or column level data quality checks, divided into categories.
     * @param checkCategoriesSpec Table or column level data quality checks container of type profiling, checkpoint or partitioned check (for a specific timescale).
     * @param runChecksTemplate Check search filter for the parent table or column that is used as a template to create more fine-grained "run checks" job configurations. Also determines which checks will be included in the ui model.
     * @param connectionSpec Connection specification for the connection to which the table belongs to.
     * @param tableSpec Table specification with the configuration of the parent table.
     * @param executionContext Execution context with a reference to both the DQO Home (with default sensor implementation) and DQO User (with user specific sensors).
     * @param providerType Provider type from the parent connection.
     * @return UI friendly model of data quality checks' container.
     */
    UICheckContainerModel createUiModel(AbstractRootChecksContainerSpec checkCategoriesSpec,
                                        CheckSearchFilters runChecksTemplate,
                                        ConnectionSpec connectionSpec,
                                        TableSpec tableSpec,
                                        ExecutionContext executionContext,
                                        ProviderType providerType);

    /**
     * Creates a simplistic UI friendly model of every data quality check on table level or column level, divided into categories.
     *
     * @param checkCategoriesSpec Table or column level data quality checks container of type profiling, checkpoint or partitioned check (for a specific timescale).
     * @param executionContext Execution context with a reference to both the DQO Home (with default sensor implementation) and DQO User (with user specific sensors).
     * @param providerType Provider type from the parent connection.
     * @return Simplistic UI friendly model of data quality checks' container.
     */
    UICheckContainerBasicModel createUiBasicModel(AbstractRootChecksContainerSpec checkCategoriesSpec,
                                             ExecutionContext executionContext,
                                             ProviderType providerType);

    /**
     * Creates a list of fields to edit all values in the sensor parameters specification.
     * @param parametersSpec Sensor parameters specification.
     * @return List of UI fields for all sensor parameter fields.
     */
    List<UIFieldModel> createFieldsForSensorParameters(AbstractSensorParametersSpec parametersSpec);

    /**
     * Creates a list of fields to edit all values in the rule parameters specification.
     * @param ruleParametersSpec Rule parameters specification.
     * @return List of UI fields for all rule parameter fields.
     */
    List<UIFieldModel> createFieldsForRuleParameters(AbstractRuleParametersSpec ruleParametersSpec);
}
