/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.execution.checks;

import com.dqops.checks.defaults.DefaultObservabilityConfigurationServiceImpl;
import com.dqops.connectors.ConnectionProviderRegistry;
import com.dqops.connectors.ConnectionProviderRegistryObjectMother;
import com.dqops.core.configuration.*;
import com.dqops.core.incidents.IncidentImportQueueServiceStub;
import com.dqops.core.jobqueue.concurrency.ParallelJobLimitProviderStub;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.core.secrets.SecretValueProviderObjectMother;
import com.dqops.data.checkresults.factory.CheckResultsTableFactoryImpl;
import com.dqops.data.checkresults.snapshot.CheckResultsSnapshotFactoryImpl;
import com.dqops.data.errors.factory.ErrorsTableFactoryImpl;
import com.dqops.data.errors.normalization.ErrorsNormalizationServiceImpl;
import com.dqops.data.errors.snapshot.ErrorsSnapshotFactoryImpl;
import com.dqops.data.errorsamples.factory.ErrorSamplesTableFactoryImpl;
import com.dqops.data.errorsamples.normalization.ErrorSamplesNormalizationServiceImpl;
import com.dqops.data.errorsamples.snapshot.ErrorSamplesSnapshotFactoryImpl;
import com.dqops.data.normalization.CommonTableNormalizationServiceImpl;
import com.dqops.data.readouts.factory.SensorReadoutsTableFactoryImpl;
import com.dqops.data.readouts.normalization.SensorReadoutsNormalizationServiceImpl;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshotFactoryImpl;
import com.dqops.data.storage.ParquetPartitionStorageServiceImpl;
import com.dqops.data.storage.ParquetPartitionStorageServiceObjectMother;
import com.dqops.execution.checks.ruleeval.RuleEvaluationSchedulerProvider;
import com.dqops.execution.checks.ruleeval.RuleEvaluationSchedulerProviderObjectMother;
import com.dqops.execution.checks.ruleeval.RuleEvaluationServiceImpl;
import com.dqops.execution.errorsampling.TableErrorSamplerExecutionServiceImpl;
import com.dqops.execution.rules.DataQualityRuleRunnerImpl;
import com.dqops.execution.rules.finder.RuleDefinitionFindService;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceObjectMother;
import com.dqops.execution.rules.runners.RuleRunnerFactoryObjectMother;
import com.dqops.execution.rules.training.RuleModelTrainingQueueImpl;
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
        DqoErrorSamplingConfigurationProperties errorSamplingConfigurationProperties = DqoErrorSamplingConfigurationPropertiesObjectMother.getDefault();
        SensorExecutionRunParametersFactoryImpl sensorExecutionRunParametersFactory = new SensorExecutionRunParametersFactoryImpl(
                secretValueProvider, sensorLimitsConfigurationProperties, errorSamplingConfigurationProperties);
        SensorDefinitionFindService sensorDefinitionFindService = SensorDefinitionFindServiceObjectMother.getSensorDefinitionFindService();
        DataQualitySensorRunnerImpl dataQualitySensorRunner = new DataQualitySensorRunnerImpl(sensorDefinitionFindService,
                SensorRunnerFactoryObjectMother.create(), new SqlQueryFragmentsParserImpl());
        DefaultTimeZoneProvider defaultTimeZoneProvider = DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider();
        CommonTableNormalizationServiceImpl commonNormalizationService = new CommonTableNormalizationServiceImpl();
        SensorReadoutsNormalizationServiceImpl sensorReadoutsNormalizationService = new SensorReadoutsNormalizationServiceImpl(
                commonNormalizationService, defaultTimeZoneProvider);

        RuleDefinitionFindService ruleDefinitionFindService = RuleDefinitionFindServiceObjectMother.getRuleDefinitionFindService();
        DataQualityRuleRunnerImpl ruleRunner = new DataQualityRuleRunnerImpl(ruleDefinitionFindService, RuleRunnerFactoryObjectMother.createDefault());
        RuleEvaluationServiceImpl ruleEvaluationService = new RuleEvaluationServiceImpl(ruleRunner, ruleDefinitionFindService, defaultTimeZoneProvider,
                new RuleModelTrainingQueueImpl(), new DqoPythonConfigurationProperties());

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

        ConnectionProviderRegistry connectionProviderRegistry = ConnectionProviderRegistryObjectMother.getInstance();
        DefaultObservabilityConfigurationServiceImpl defaultObservabilityConfigurationService =
                new DefaultObservabilityConfigurationServiceImpl(connectionProviderRegistry);
        UserErrorLoggerImpl userErrorLogger = new UserErrorLoggerImpl(new DqoLoggingUserErrorsConfigurationProperties());
        ErrorSamplesNormalizationServiceImpl errorSamplesNormalizationService =
                new ErrorSamplesNormalizationServiceImpl(commonNormalizationService, new DqoErrorSamplingConfigurationProperties());
        ErrorSamplesSnapshotFactoryImpl errorSamplesSnapshotFactory =
                new ErrorSamplesSnapshotFactoryImpl(parquetPartitionStorageService, new ErrorSamplesTableFactoryImpl());

        TableErrorSamplerExecutionServiceImpl tableErrorSamplerExecutionService = new TableErrorSamplerExecutionServiceImpl(
                hierarchyNodeTreeSearcher, sensorExecutionRunParametersFactory, dataQualitySensorRunner,
                connectionProviderRegistry, errorSamplesNormalizationService, errorSamplesSnapshotFactory,
                sensorLimitsConfigurationProperties, userErrorLogger, defaultObservabilityConfigurationService);

        TableCheckExecutionServiceImpl tableCheckExecutionService = new TableCheckExecutionServiceImpl(
                hierarchyNodeTreeSearcher,
                sensorExecutionRunParametersFactory,
                dataQualitySensorRunner,
                connectionProviderRegistry,
                sensorReadoutsNormalizationService,
                ruleEvaluationService,
                sensorReadoutsSnapshotFactory,
                checkResultsSnapshotFactory,
                errorsNormalizationService,
                errorsSnapshotFactory,
                ruleDefinitionFindService,
                new IncidentImportQueueServiceStub(),
                sensorLimitsConfigurationProperties,
                userErrorLogger,
                defaultObservabilityConfigurationService,
                tableErrorSamplerExecutionService,
                DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider(),
                new ParallelJobLimitProviderStub(2),
                RuleEvaluationSchedulerProviderObjectMother.getDefault());

        return tableCheckExecutionService;
    }
}
