/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.mapping.models;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.connectors.bigquery.BigQueryConnectionSpecObjectMother;
import com.dqops.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import com.dqops.core.scheduler.quartz.*;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableSpecObjectMother;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.services.check.matching.SimilarCheckCacheImpl;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.services.timezone.DefaultTimeZoneProviderObjectMother;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.dqops.utils.serialization.JsonSerializerObjectMother;

/**
 * Object factory for creating a {@link CheckModel}
 */
public class CheckModelObjectMother {
    /**
     * Create a check model for a check spec.
     * @param checkSpec Source check specification.
     * @param checkCategoriesSpec Check categories container that contains the check.
     * @param connectionSpec Parent connection name.
     * @param tableSpec Parent table specification.
     * @return Check model.
     */
    public static CheckModel createCheckModel(
            AbstractCheckSpec<?,?,?,?> checkSpec,
            AbstractRootChecksContainerSpec checkCategoriesSpec,
            ConnectionSpec connectionSpec,
            TableSpec tableSpec) {
        DefaultTimeZoneProvider defaultTimeZoneProvider = DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider();
        TriggerFactory triggerFactory = new TriggerFactoryImpl(
                new JobDataMapAdapterImpl(JsonSerializerObjectMother.getDefault(), DqoUserConfigurationPropertiesObjectMother.createDefaultUserConfiguration()),
                defaultTimeZoneProvider);

        SchedulesUtilityService schedulesUtilityService = new SchedulesUtilityServiceImpl(
                triggerFactory,
                defaultTimeZoneProvider);

        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        SensorDefinitionFindServiceImpl sensorDefinitionFindService = new SensorDefinitionFindServiceImpl();
        RuleDefinitionFindServiceImpl ruleDefinitionFindService = new RuleDefinitionFindServiceImpl();
        SimilarCheckCacheImpl similarCheckCache = new SimilarCheckCacheImpl(reflectionService,
                sensorDefinitionFindService, ruleDefinitionFindService, dqoHomeContextFactory);
        SpecToModelCheckMappingServiceImpl specToModelCheckMappingService = new SpecToModelCheckMappingServiceImpl(
                reflectionService,
                sensorDefinitionFindService,
                ruleDefinitionFindService,
                schedulesUtilityService,
                similarCheckCache);

        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        ExecutionContext executionContext = new ExecutionContext(inMemoryFileHomeContext, DqoHomeContextObjectMother.getRealDqoHomeContext());


        CheckModel checkModel = specToModelCheckMappingService.createCheckModel(null, null, checkSpec, checkCategoriesSpec.getSchedulingGroup(),
                new CheckSearchFilters(), tableSpec, executionContext, connectionSpec.getProviderType(),
                checkCategoriesSpec.getCheckTarget(), checkCategoriesSpec.getCheckType(), checkCategoriesSpec.getCheckTimeScale(), true);
        checkModel.setConfigured(true);

        return checkModel;
    }
}
