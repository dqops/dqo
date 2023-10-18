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

import com.dqops.BaseTest;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.defaults.DefaultProfilingObservabilityCheckSettingsSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.connectors.bigquery.BigQueryConnectionSpecObjectMother;
import com.dqops.core.scheduler.quartz.*;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableSpecObjectMother;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mapping.models.QualityCategoryModel;
import com.dqops.services.check.matching.SimilarCheckCacheImpl;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.services.timezone.DefaultTimeZoneProviderObjectMother;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.dqops.utils.serialization.JsonSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModelToSpecCheckMappingServiceImplTests extends BaseTest {
    private ModelToSpecCheckMappingServiceImpl sut;
    private SpecToModelCheckMappingServiceImpl specToUiMapper;
    private TableSpec tableSpec;
    private ConnectionSpec bigQueryConnectionSpec;

    @BeforeEach
    void setUp() {
        DefaultTimeZoneProvider defaultTimeZoneProvider = DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider();
        TriggerFactory triggerFactory = new TriggerFactoryImpl(
                new JobDataMapAdapterImpl(JsonSerializerObjectMother.getDefault()),
                defaultTimeZoneProvider);

        SchedulesUtilityService schedulesUtilityService = new SchedulesUtilityServiceImpl(
                triggerFactory,
                defaultTimeZoneProvider);

        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        SensorDefinitionFindServiceImpl sensorDefinitionFindService = new SensorDefinitionFindServiceImpl();
        RuleDefinitionFindServiceImpl ruleDefinitionFindService = new RuleDefinitionFindServiceImpl();
        SimilarCheckCacheImpl similarCheckCache = new SimilarCheckCacheImpl(reflectionService, sensorDefinitionFindService,
                ruleDefinitionFindService, dqoHomeContextFactory);
        this.specToUiMapper = new SpecToModelCheckMappingServiceImpl(
                reflectionService, sensorDefinitionFindService, ruleDefinitionFindService, schedulesUtilityService, similarCheckCache);

        this.bigQueryConnectionSpec = BigQueryConnectionSpecObjectMother.create();
        
        
        this.sut = new ModelToSpecCheckMappingServiceImpl(reflectionService);
        this.tableSpec = TableSpecObjectMother.create("public", "tab1");
        this.tableSpec.setDefaultDataGroupingConfiguration(new DataGroupingConfigurationSpec());
    }

    @Test
    void updateAllChecksSpecs_whenEmptyTableChecksModelGivenJustCreated_thenExecutesWithoutErrors() {
        TableProfilingCheckCategoriesSpec tableCheckCategoriesSpec = new TableProfilingCheckCategoriesSpec();
        CheckContainerModel uiModel = this.specToUiMapper.createModel(tableCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, null, null, true);

        this.sut.updateCheckContainerSpec(uiModel, tableCheckCategoriesSpec, this.tableSpec);
    }

    @Test
    void updateAllChecksSpecs_whenEmptyColumnChecksModelGivenJustCreated_thenExecutesWithoutErrors() {
        ColumnProfilingCheckCategoriesSpec columnCheckCategoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        CheckContainerModel uiModel = this.specToUiMapper.createModel(columnCheckCategoriesSpec, new CheckSearchFilters(),
                this.bigQueryConnectionSpec, this.tableSpec, null, null, true);

        this.sut.updateCheckContainerSpec(uiModel, columnCheckCategoriesSpec, this.tableSpec);
    }

    @Test
    void updateAllChecksSpec_whenChangesAppliedToDefaultProfilingObservabilityChecks_thenEnablesCheck() {
        DefaultProfilingObservabilityCheckSettingsSpec profilingObservabilityChecksSpec = new DefaultProfilingObservabilityCheckSettingsSpec();
        CheckContainerModel uiModel = this.specToUiMapper.createModel(profilingObservabilityChecksSpec.getTable(), null,
                null, null, null, null, true);

        QualityCategoryModel tableVolumeCategoryModel = uiModel.getCategories().stream()
                .filter(cm -> cm.getCategory().equals("volume")).findFirst().get();
        CheckModel profileRowCountModel = tableVolumeCategoryModel.getChecks().stream()
                .filter(cm -> cm.getCheckName().equals("profile_row_count")).findFirst().get();

        profileRowCountModel.setConfigured(true);
        profileRowCountModel.getRule().getWarning().setConfigured(true);


        this.sut.updateCheckContainerSpec(uiModel, profilingObservabilityChecksSpec.getTable(), this.tableSpec);

        Assertions.assertNotNull(profilingObservabilityChecksSpec.getTable().getVolume());
        TableRowCountCheckSpec profileRowCountCheck = profilingObservabilityChecksSpec.getTable().getVolume().getProfileRowCount();
        Assertions.assertNotNull(profileRowCountCheck);
        Assertions.assertNotNull(profileRowCountCheck.getWarning());
    }
}
