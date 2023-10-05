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

package com.dqops.execution.checks;

import com.dqops.connectors.ConnectionProviderRegistryObjectMother;
import com.dqops.core.configuration.DqoLoggingExecutionConfigurationProperties;
import com.dqops.core.configuration.DqoSensorLimitsConfigurationProperties;
import com.dqops.core.configuration.DqoSensorLimitsConfigurationPropertiesObjectMother;
import com.dqops.core.incidents.IncidentImportQueueServiceStub;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.core.secrets.SecretValueProviderObjectMother;
import com.dqops.data.checkresults.factory.CheckResultsTableFactoryImpl;
import com.dqops.data.checkresults.snapshot.CheckResultsSnapshotFactoryImpl;
import com.dqops.data.errors.factory.ErrorsTableFactoryImpl;
import com.dqops.data.errors.normalization.ErrorsNormalizationServiceImpl;
import com.dqops.data.errors.snapshot.ErrorsSnapshotFactoryImpl;
import com.dqops.data.normalization.CommonTableNormalizationServiceImpl;
import com.dqops.data.readouts.factory.SensorReadoutsTableFactoryImpl;
import com.dqops.data.readouts.normalization.SensorReadoutsNormalizationServiceImpl;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshotFactoryImpl;
import com.dqops.data.storage.ParquetPartitionStorageServiceImpl;
import com.dqops.data.storage.ParquetPartitionStorageServiceObjectMother;
import com.dqops.execution.checks.ruleeval.RuleEvaluationServiceImpl;
import com.dqops.execution.rules.DataQualityRuleRunnerImpl;
import com.dqops.execution.rules.finder.RuleDefinitionFindService;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceObjectMother;
import com.dqops.execution.rules.runners.RuleRunnerFactoryObjectMother;
import com.dqops.execution.sensors.DataQualitySensorRunnerImpl;
import com.dqops.execution.sensors.SensorExecutionRunParametersFactoryImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindService;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceObjectMother;
import com.dqops.execution.sensors.runners.SensorRunnerFactoryObjectMother;
import com.dqops.execution.sqltemplates.grouping.SqlQueryFragmentsParserImpl;
import com.dqops.metadata.search.HierarchyNodeTreeSearcherImpl;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.services.timezone.DefaultTimeZoneProviderObjectMother;
import com.dqops.utils.logging.UserErrorLoggerImpl;

/**
 * Object mother for {@link TableCheckExecutionService}
 */
public class TableCheckExecutionServiceObjectMother {
    /**
     * Creates a new table check execution service that operates on a given user home context, saving parquet files
     * in the user home. This instance is almost fully functional, only incidents are not saved.
     * @param userHomeContext User home context.
     * @return Functional table check execution service.
     */
    public static TableCheckExecutionServiceImpl createCheckExecutionServiceOnUserHomeContext(UserHomeContext userHomeContext) {
        HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(new HierarchyNodeTreeWalkerImpl());
        SecretValueProvider secretValueProvider = SecretValueProviderObjectMother.getInstance();
        DqoSensorLimitsConfigurationProperties sensorLimitsConfigurationProperties = DqoSensorLimitsConfigurationPropertiesObjectMother.getDefault();
        SensorExecutionRunParametersFactoryImpl sensorExecutionRunParametersFactory = new SensorExecutionRunParametersFactoryImpl(
                secretValueProvider, sensorLimitsConfigurationProperties);
        SensorDefinitionFindService sensorDefinitionFindService = SensorDefinitionFindServiceObjectMother.getSensorDefinitionFindService();
        DataQualitySensorRunnerImpl dataQualitySensorRunner = new DataQualitySensorRunnerImpl(sensorDefinitionFindService,
                SensorRunnerFactoryObjectMother.create(), new SqlQueryFragmentsParserImpl());
        DefaultTimeZoneProvider defaultTimeZoneProvider = DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider();
        CommonTableNormalizationServiceImpl commonNormalizationService = new CommonTableNormalizationServiceImpl();
        SensorReadoutsNormalizationServiceImpl sensorReadoutsNormalizationService = new SensorReadoutsNormalizationServiceImpl(
                commonNormalizationService, defaultTimeZoneProvider);

        RuleDefinitionFindService ruleDefinitionFindService = RuleDefinitionFindServiceObjectMother.getRuleDefinitionFindService();
        DataQualityRuleRunnerImpl ruleRunner = new DataQualityRuleRunnerImpl(ruleDefinitionFindService, RuleRunnerFactoryObjectMother.createDefault());
        RuleEvaluationServiceImpl ruleEvaluationService = new RuleEvaluationServiceImpl(ruleRunner, ruleDefinitionFindService, defaultTimeZoneProvider);

        ParquetPartitionStorageServiceImpl parquetPartitionStorageService =
                ParquetPartitionStorageServiceObjectMother.create(userHomeContext);
        SensorReadoutsTableFactoryImpl sensorReadoutsTableFactory = new SensorReadoutsTableFactoryImpl();
        SensorReadoutsSnapshotFactoryImpl sensorReadoutsSnapshotFactory = new SensorReadoutsSnapshotFactoryImpl(
                parquetPartitionStorageService, sensorReadoutsTableFactory);
        CheckResultsSnapshotFactoryImpl checkResultsSnapshotFactory = new CheckResultsSnapshotFactoryImpl(
                parquetPartitionStorageService, new CheckResultsTableFactoryImpl(sensorReadoutsTableFactory));
        ErrorsNormalizationServiceImpl errorsNormalizationService = new ErrorsNormalizationServiceImpl(
                sensorReadoutsNormalizationService, commonNormalizationService, defaultTimeZoneProvider);
        ErrorsSnapshotFactoryImpl errorsSnapshotFactory = new ErrorsSnapshotFactoryImpl(
                parquetPartitionStorageService, new ErrorsTableFactoryImpl(sensorReadoutsTableFactory));

        TableCheckExecutionServiceImpl tableCheckExecutionService = new TableCheckExecutionServiceImpl(
                hierarchyNodeTreeSearcher,
                sensorExecutionRunParametersFactory,
                dataQualitySensorRunner,
                ConnectionProviderRegistryObjectMother.getInstance(),
                sensorReadoutsNormalizationService,
                ruleEvaluationService,
                sensorReadoutsSnapshotFactory,
                checkResultsSnapshotFactory,
                errorsNormalizationService,
                errorsSnapshotFactory,
                ruleDefinitionFindService,
                new IncidentImportQueueServiceStub(),
                sensorLimitsConfigurationProperties,
                new UserErrorLoggerImpl(new DqoLoggingExecutionConfigurationProperties()));

        return tableCheckExecutionService;
    }
}
