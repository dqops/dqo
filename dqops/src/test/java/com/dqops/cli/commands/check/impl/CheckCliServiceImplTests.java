/*
 * Copyright © 2023 DQOps (support@dqops.com)
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

package com.dqops.cli.commands.check.impl;

import com.dqops.BaseTest;
import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.column.checkspecs.numeric.ColumnNegativeCountCheckSpec;
import com.dqops.checks.column.checkspecs.strings.ColumnStringLengthAboveMaxLengthCountCheckSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.column.profiling.ColumnStringsProfilingChecksSpec;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringChecksRootSpec;
import com.dqops.checks.column.monitoring.numeric.ColumnNumericDailyMonitoringChecksSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.cli.commands.check.impl.models.AllChecksModelCliPatchParameters;
import com.dqops.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import com.dqops.core.jobqueue.*;
import com.dqops.core.principal.DqoDqoUserPrincipalProviderStub;
import com.dqops.core.principal.DqoUserPrincipalObjectMother;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityObjectMother;
import com.dqops.core.scheduler.quartz.*;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.execution.ExecutionContextFactoryImpl;
import com.dqops.execution.rules.finder.RuleDefinitionFindService;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceObjectMother;
import com.dqops.execution.sensors.finder.SensorDefinitionFindService;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceObjectMother;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.search.HierarchyNodeTreeSearcherImpl;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rules.comparison.*;
import com.dqops.services.check.CheckService;
import com.dqops.services.check.CheckServiceImpl;
import com.dqops.services.check.mapping.*;
import com.dqops.services.check.matching.SimilarCheckCacheImpl;
import com.dqops.services.timezone.DefaultTimeZoneProviderObjectMother;
import com.dqops.utils.BeanFactoryObjectMother;
import com.dqops.utils.reflection.ReflectionService;
import com.dqops.utils.reflection.ReflectionServiceSingleton;
import com.dqops.utils.serialization.JsonSerializerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class CheckCliServiceImplTests extends BaseTest {
    private CheckCliServiceImpl sut;

    private UserHomeContextFactory userHomeContextFactory;
    private HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private UserDomainIdentity userDomainIdentity;

    @BeforeEach
    public void setUp() {
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();
        this.userDomainIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        ExecutionContextFactory executionContextFactory = new ExecutionContextFactoryImpl(userHomeContextFactory, dqoHomeContextFactory);
        this.hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(new HierarchyNodeTreeWalkerImpl());
        ReflectionService reflectionService = ReflectionServiceSingleton.getInstance();

        SensorDefinitionFindService sensorDefinitionFindService = SensorDefinitionFindServiceObjectMother.getSensorDefinitionFindService();
        RuleDefinitionFindService ruleDefinitionFindService = RuleDefinitionFindServiceObjectMother.getRuleDefinitionFindService();
        JobDataMapAdapter jobDataMapAdapter = new JobDataMapAdapterImpl(new JsonSerializerImpl(), DqoUserConfigurationPropertiesObjectMother.createDefaultUserConfiguration());
        TriggerFactory triggerFactory = new TriggerFactoryImpl(jobDataMapAdapter, DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider());
        SchedulesUtilityService schedulesUtilityService = new SchedulesUtilityServiceImpl(triggerFactory, DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider());
        SimilarCheckCacheImpl similarCheckCache = new SimilarCheckCacheImpl(reflectionService, sensorDefinitionFindService, ruleDefinitionFindService, dqoHomeContextFactory);
        SpecToModelCheckMappingService specToModelCheckMappingService = new SpecToModelCheckMappingServiceImpl(reflectionService,
                sensorDefinitionFindService, ruleDefinitionFindService, schedulesUtilityService, similarCheckCache);
        AllChecksModelFactory allChecksModelFactory = new AllChecksModelFactoryImpl(executionContextFactory, hierarchyNodeTreeSearcher, specToModelCheckMappingService);

        ModelToSpecCheckMappingService modelToSpecCheckMappingService = new ModelToSpecCheckMappingServiceImpl(reflectionService);
        AllChecksPatchApplier allChecksPatchApplier = new AllChecksPatchApplierImpl(modelToSpecCheckMappingService);

        DqoQueueJobFactory dqoQueueJobFactory = new DqoQueueJobFactoryImpl(BeanFactoryObjectMother.getBeanFactory());
        ParentDqoJobQueue parentDqoJobQueue = DqoJobQueueObjectMother.getDefaultParentJobQueue();

        CheckService checkService = new CheckServiceImpl(
                allChecksModelFactory,
                allChecksPatchApplier,
                dqoQueueJobFactory,
                parentDqoJobQueue,
                userHomeContextFactory);

        DqoDqoUserPrincipalProviderStub principalProviderStub = new DqoDqoUserPrincipalProviderStub(
                DqoUserPrincipalObjectMother.createStandaloneAdmin());

        this.sut = new CheckCliServiceImpl(
                checkService,
                allChecksModelFactory,
                principalProviderStub);
    }

    private ColumnSpec createColumn(String type, boolean nullable) {
        ColumnSpec col = new ColumnSpec();
        ColumnTypeSnapshotSpec columnTypeSnapshotSpec = new ColumnTypeSnapshotSpec();
        columnTypeSnapshotSpec.setColumnType(type);
        columnTypeSnapshotSpec.setNullable(nullable);
        col.setTypeSnapshot(columnTypeSnapshotSpec);
        return col;
    }

    private UserHome createHierarchyTree() {
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome(this.userDomainIdentity);
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("conn");
        TableWrapper table1 = connectionWrapper.getTables().createAndAddNew(
                new PhysicalTableName("sch", "tab1"));
        TableWrapper table2 = connectionWrapper.getTables().createAndAddNew(
                new PhysicalTableName("sch", "tab2"));

        ColumnSpec col11 = createColumn("string", true);
        ColumnSpec col21 = createColumn("datetime", false);
        ColumnSpec col22 = createColumn("numeric", true);
        ColumnSpec col23 = createColumn("numeric", false);

        table1.getSpec().getColumns().put("col1", col11);
        table2.getSpec().getColumns().put("col1", col21);
        table2.getSpec().getColumns().put("col2", col22);
        table2.getSpec().getColumns().put("col3", col23);

        TableProfilingCheckCategoriesSpec t1categoriesSpec = new TableProfilingCheckCategoriesSpec();
        TableVolumeProfilingChecksSpec t1volumeChecksSpec = new TableVolumeProfilingChecksSpec();
        TableRowCountCheckSpec t1rowCountSpec = new TableRowCountCheckSpec();
        MinCountRule1ParametersSpec t1rowCountErrorSpec = new MinCountRule1ParametersSpec();
        MinCountRule100FatalParametersSpec t1rowCountFatalSpec = new MinCountRule100FatalParametersSpec();
        t1rowCountErrorSpec.setMinCount(50L);
        t1rowCountFatalSpec.setMinCount(20L);
        t1rowCountSpec.setError(t1rowCountErrorSpec);
        t1rowCountSpec.setFatal(t1rowCountFatalSpec);
        t1volumeChecksSpec.setProfileRowCount(t1rowCountSpec);
        t1categoriesSpec.setVolume(t1volumeChecksSpec);
        table1.getSpec().setProfilingChecks(t1categoriesSpec);

        ColumnProfilingCheckCategoriesSpec col21categoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        ColumnStringsProfilingChecksSpec col21stringChecksSpec = new ColumnStringsProfilingChecksSpec();
        ColumnStringLengthAboveMaxLengthCountCheckSpec col21stringLengthAboveCheckSpec = new ColumnStringLengthAboveMaxLengthCountCheckSpec();
        MaxCountRule0ErrorParametersSpec countRule0ParametersSpec = new MaxCountRule0ErrorParametersSpec();
        countRule0ParametersSpec.setMaxCount(40L);
        MaxCountRule100ParametersSpec countRule0ParametersSpec1 = new MaxCountRule100ParametersSpec();
        countRule0ParametersSpec1.setMaxCount(100L);
        col21stringLengthAboveCheckSpec.setError(countRule0ParametersSpec);
        col21stringLengthAboveCheckSpec.setFatal(countRule0ParametersSpec1);
        col21stringChecksSpec.setProfileStringLengthAboveMaxLengthCount(col21stringLengthAboveCheckSpec);
        col21categoriesSpec.setStrings(col21stringChecksSpec);
        col21.setProfilingChecks(col21categoriesSpec);

        ColumnMonitoringChecksRootSpec col23monitoringSpec = new ColumnMonitoringChecksRootSpec();
        col23.setMonitoringChecks(col23monitoringSpec);
        ColumnDailyMonitoringCheckCategoriesSpec col23categoriesSpec = new ColumnDailyMonitoringCheckCategoriesSpec();
        col23monitoringSpec.setDaily(col23categoriesSpec);
        ColumnNumericDailyMonitoringChecksSpec col23numericChecksSpec = new ColumnNumericDailyMonitoringChecksSpec();
        col23categoriesSpec.setNumeric(col23numericChecksSpec);
        ColumnNegativeCountCheckSpec columnNegativeCountCheckSpec = new ColumnNegativeCountCheckSpec();
        col23numericChecksSpec.setDailyNegativeValues(columnNegativeCountCheckSpec);
        MaxCountRule0WarningParametersSpec col23max1 = new MaxCountRule0WarningParametersSpec();
        col23max1.setMaxCount(15L);
        columnNegativeCountCheckSpec.setWarning(col23max1);

        userHomeContext.flush();
        return userHome;
    }

    private Map<String, String> getRuleOptionMap(String optionName, Object optionValue) {
        Map<String, String> result = new HashMap<>();
        result.put(optionName, optionValue.toString());
        return result;
    }

    @Test
    void updateAllChecksPatch_whenConnectionAndCheckGiven_enablesRequestedChecks() {
        UserHome userHome = createHierarchyTree();

        AllChecksModelCliPatchParameters allChecksModelCliPatchParameters = new AllChecksModelCliPatchParameters();
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters(){{
            setConnection("conn");
            setCheckName("profile_nulls_count");
        }};
        allChecksModelCliPatchParameters.setCheckSearchFilters(checkSearchFilters);
        allChecksModelCliPatchParameters.setFatalLevelOptions(getRuleOptionMap("max_count", 50));
        allChecksModelCliPatchParameters.setDisableFatalLevel(false);

        this.sut.updateAllChecksPatch(allChecksModelCliPatchParameters);

        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome(this.userDomainIdentity);
        userHome = userHomeContext.getUserHome();
        Collection<AbstractCheckSpec<?, ?, ?, ?>> checks = hierarchyNodeTreeSearcher.findChecks(userHome, checkSearchFilters);
        Assertions.assertNotNull(checks);
        Assertions.assertFalse(checks.isEmpty());
        for (AbstractCheckSpec<?,?,?,?> check: checks) {
            Assertions.assertFalse(check.isDisabled());
            Assertions.assertNull(check.getWarning());
            Assertions.assertNull(check.getError());
            Assertions.assertNotNull(check.getFatal());
        }
    }
}
