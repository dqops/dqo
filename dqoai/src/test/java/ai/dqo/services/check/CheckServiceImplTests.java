/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
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

package ai.dqo.services.check;

import ai.dqo.BaseTest;
import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.checks.column.checkspecs.numeric.ColumnNegativeCountCheckSpec;
import ai.dqo.checks.column.checkspecs.strings.ColumnStringLengthAboveMaxLengthCountCheckSpec;
import ai.dqo.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import ai.dqo.checks.column.profiling.ColumnProfilingStringsChecksSpec;
import ai.dqo.checks.column.recurring.ColumnDailyRecurringCategoriesSpec;
import ai.dqo.checks.column.recurring.ColumnRecurringSpec;
import ai.dqo.checks.column.recurring.numeric.ColumnNumericDailyRecurringSpec;
import ai.dqo.checks.table.checkspecs.standard.TableRowCountCheckSpec;
import ai.dqo.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import ai.dqo.checks.table.profiling.TableProfilingStandardChecksSpec;
import ai.dqo.core.configuration.DqoQueueConfigurationProperties;
import ai.dqo.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import ai.dqo.core.jobqueue.*;
import ai.dqo.core.jobqueue.monitoring.DqoJobQueueMonitoringService;
import ai.dqo.core.jobqueue.monitoring.DqoJobQueueMonitoringServiceImpl;
import ai.dqo.core.locks.UserHomeLockManager;
import ai.dqo.core.locks.UserHomeLockManagerObjectMother;
import ai.dqo.core.scheduler.quartz.*;
import ai.dqo.core.synchronization.filesystems.local.LocalFileSystemSynchronizationOperations;
import ai.dqo.core.synchronization.filesystems.local.LocalFileSystemSynchronizationOperationsImpl;
import ai.dqo.core.synchronization.filesystems.local.LocalSynchronizationFileSystemFactory;
import ai.dqo.core.synchronization.filesystems.local.LocalSynchronizationFileSystemFactoryImpl;
import ai.dqo.core.synchronization.status.FileSynchronizationChangeDetectionService;
import ai.dqo.core.synchronization.status.FileSynchronizationChangeDetectionServiceImpl;
import ai.dqo.core.synchronization.status.SynchronizationStatusTracker;
import ai.dqo.core.synchronization.status.SynchronizationStatusTrackerStub;
import ai.dqo.data.local.LocalDqoUserHomePathProvider;
import ai.dqo.data.local.LocalDqoUserHomePathProviderObjectMother;
import ai.dqo.execution.ExecutionContextFactory;
import ai.dqo.execution.ExecutionContextFactoryImpl;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindService;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindServiceObjectMother;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcher;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcherImpl;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rules.comparison.MaxCountRule0ParametersSpec;
import ai.dqo.rules.comparison.MaxCountRule10ParametersSpec;
import ai.dqo.rules.comparison.MinCountRule0ParametersSpec;
import ai.dqo.rules.comparison.MinCountRuleFatalParametersSpec;
import ai.dqo.services.check.mapping.*;
import ai.dqo.services.check.models.UIAllChecksPatchParameters;
import ai.dqo.services.timezone.DefaultTimeZoneProviderObjectMother;
import ai.dqo.utils.BeanFactoryObjectMother;
import ai.dqo.utils.reflection.ReflectionService;
import ai.dqo.utils.reflection.ReflectionServiceSingleton;
import ai.dqo.utils.serialization.JsonSerializerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class CheckServiceImplTests extends BaseTest {
    private CheckServiceImpl sut;

    private UserHomeContextFactory userHomeContextFactory;
    private HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;

    @BeforeEach
    public void setUp() {
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();

        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        ExecutionContextFactory executionContextFactory = new ExecutionContextFactoryImpl(userHomeContextFactory, dqoHomeContextFactory);
        this.hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(new HierarchyNodeTreeWalkerImpl());
        ReflectionService reflectionService = ReflectionServiceSingleton.getInstance();

        SensorDefinitionFindService sensorDefinitionFindService = SensorDefinitionFindServiceObjectMother.getSensorDefinitionFindService();
        JobDataMapAdapter jobDataMapAdapter = new JobDataMapAdapterImpl(new JsonSerializerImpl());
        TriggerFactory triggerFactory = new TriggerFactoryImpl(jobDataMapAdapter, DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider());
        SchedulesUtilityService schedulesUtilityService = new SchedulesUtilityServiceImpl(triggerFactory, DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider());
        SpecToUiCheckMappingService specToUiCheckMappingService = new SpecToUiCheckMappingServiceImpl(reflectionService, sensorDefinitionFindService, schedulesUtilityService);
        UIAllChecksPatchFactory uiAllChecksPatchFactory = new UIAllChecksPatchFactoryImpl(executionContextFactory, hierarchyNodeTreeSearcher, specToUiCheckMappingService);

        UiToSpecCheckMappingService uiToSpecCheckMappingService = new UiToSpecCheckMappingServiceImpl(reflectionService);
        UIAllChecksPatchApplier uiAllChecksPatchApplier = new UIAllChecksPatchApplierImpl(uiToSpecCheckMappingService);

        DqoQueueJobFactory dqoQueueJobFactory = new DqoQueueJobFactoryImpl(BeanFactoryObjectMother.getBeanFactory());

        DqoQueueConfigurationProperties dqoQueueConfigurationProperties = new DqoQueueConfigurationProperties();
        DqoJobConcurrencyLimiter dqoJobConcurrencyLimiter = new DqoJobConcurrencyLimiterImpl();
        DqoJobIdGenerator dqoJobIdGenerator = new DqoJobIdGeneratorImpl();
        DqoJobQueueMonitoringService dqoJobQueueMonitoringService = new DqoJobQueueMonitoringServiceImpl(dqoJobIdGenerator, dqoQueueConfigurationProperties);
        LocalFileSystemSynchronizationOperations localFileSystemSynchronizationOperations = new LocalFileSystemSynchronizationOperationsImpl();
        LocalDqoUserHomePathProvider localDqoUserHomePathProvider = LocalDqoUserHomePathProviderObjectMother.createLocalUserHomeProviderStub(
                DqoUserConfigurationPropertiesObjectMother.createDefaultUserConfiguration());
        LocalSynchronizationFileSystemFactory localSynchronizationFileSystemFactory = new LocalSynchronizationFileSystemFactoryImpl(
                localFileSystemSynchronizationOperations,
                localDqoUserHomePathProvider);
        SynchronizationStatusTracker synchronizationStatusTracker = new SynchronizationStatusTrackerStub();
        UserHomeLockManager userHomeLockManager = UserHomeLockManagerObjectMother.getDefaultGlobalLockManager();
        FileSynchronizationChangeDetectionService fileSynchronizationChangeDetectionService = new FileSynchronizationChangeDetectionServiceImpl(
                userHomeContextFactory,
                localSynchronizationFileSystemFactory,
                userHomeLockManager,
                synchronizationStatusTracker);
        DqoJobQueue dqoJobQueue = new DqoJobQueueImpl(
                dqoQueueConfigurationProperties,
                dqoJobConcurrencyLimiter,
                dqoJobIdGenerator,
                dqoJobQueueMonitoringService,
                fileSynchronizationChangeDetectionService);

        this.sut = new CheckServiceImpl(
                uiAllChecksPatchFactory,
                uiAllChecksPatchApplier,
                dqoQueueJobFactory,
                dqoJobQueue,
                userHomeContextFactory);
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
        UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome();
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
        TableProfilingStandardChecksSpec t1standardChecksSpec = new TableProfilingStandardChecksSpec();
        TableRowCountCheckSpec t1rowCountSpec = new TableRowCountCheckSpec();
        MinCountRule0ParametersSpec t1rowCountErrorSpec = new MinCountRule0ParametersSpec();
        MinCountRuleFatalParametersSpec t1rowCountFatalSpec = new MinCountRuleFatalParametersSpec();
        t1rowCountErrorSpec.setMinCount(50L);
        t1rowCountFatalSpec.setMinCount(20L);
        t1rowCountSpec.setError(t1rowCountErrorSpec);
        t1rowCountSpec.setFatal(t1rowCountFatalSpec);
        t1standardChecksSpec.setRowCount(t1rowCountSpec);
        t1categoriesSpec.setStandard(t1standardChecksSpec);
        table1.getSpec().setChecks(t1categoriesSpec);

        ColumnProfilingCheckCategoriesSpec col21categoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        ColumnProfilingStringsChecksSpec col21stringChecksSpec = new ColumnProfilingStringsChecksSpec();
        ColumnStringLengthAboveMaxLengthCountCheckSpec col21stringLengthAboveCheckSpec = new ColumnStringLengthAboveMaxLengthCountCheckSpec();
        MaxCountRule0ParametersSpec countRule0ParametersSpec = new MaxCountRule0ParametersSpec();
        countRule0ParametersSpec.setMaxCount(40L);
        MaxCountRule0ParametersSpec countRule0ParametersSpec1 = new MaxCountRule0ParametersSpec();
        countRule0ParametersSpec1.setMaxCount(100L);
        col21stringLengthAboveCheckSpec.setError(countRule0ParametersSpec);
        col21stringLengthAboveCheckSpec.setFatal(countRule0ParametersSpec1);
        col21stringChecksSpec.setStringLengthAboveMaxLengthCount(col21stringLengthAboveCheckSpec);
        col21categoriesSpec.setStrings(col21stringChecksSpec);
        col21.setChecks(col21categoriesSpec);

        ColumnRecurringSpec col23recurringSpec = new ColumnRecurringSpec();
        col23.setRecurring(col23recurringSpec);
        ColumnDailyRecurringCategoriesSpec col23categoriesSpec = new ColumnDailyRecurringCategoriesSpec();
        col23recurringSpec.setDaily(col23categoriesSpec);
        ColumnNumericDailyRecurringSpec col23numericChecksSpec = new ColumnNumericDailyRecurringSpec();
        col23categoriesSpec.setNumeric(col23numericChecksSpec);
        ColumnNegativeCountCheckSpec columnNegativeCountCheckSpec = new ColumnNegativeCountCheckSpec();
        col23numericChecksSpec.setDailyNegativeCount(columnNegativeCountCheckSpec);
        MaxCountRule10ParametersSpec col23max1 = new MaxCountRule10ParametersSpec();
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
    void disableChecks_whenConnectionAndCheckGiven_disablesRequestedChecks() {
        UserHome userHome = createHierarchyTree();

        CheckSearchFilters checkSearchFilters = new CheckSearchFilters(){{
            setConnectionName("conn");
            setCheckName("row_count");
        }};

        TableRowCountCheckSpec tableRowCountCheckSpec = userHome
                .getConnections().getByObjectName("conn", true)
                .getTables().getByObjectName(new PhysicalTableName("sch", "tab1"), true).getSpec()
                .getChecks().getStandard().getRowCount();

        Assertions.assertNull(tableRowCountCheckSpec.getWarning());
        Assertions.assertNotNull(tableRowCountCheckSpec.getError());
        Assertions.assertNotNull(tableRowCountCheckSpec.getFatal());
        Assertions.assertEquals(50L, tableRowCountCheckSpec.getError().getMinCount());
        Assertions.assertEquals(20L, tableRowCountCheckSpec.getFatal().getMinCount());
        Assertions.assertFalse(tableRowCountCheckSpec.isDisabled());

        this.sut.disableChecks(checkSearchFilters);

        userHome = userHomeContextFactory.openLocalUserHome().getUserHome();
        Collection<AbstractCheckSpec<?,?,?,?>> checksEnabled = hierarchyNodeTreeSearcher.findChecks(userHome, checkSearchFilters);
        Assertions.assertTrue(checksEnabled.isEmpty());

        CheckSearchFilters checkSearchFiltersDisabled = checkSearchFilters.clone();
        checkSearchFiltersDisabled.setEnabled(false);
        Collection<AbstractCheckSpec<?,?,?,?>> checksDisabled = hierarchyNodeTreeSearcher.findChecks(userHome, checkSearchFiltersDisabled);

        Assertions.assertNotNull(checksDisabled);
        Assertions.assertEquals(1, checksDisabled.size());
        for (AbstractCheckSpec<?,?,?,?> check: checksDisabled) {
            Assertions.assertTrue(check.isDisabled());
        }

        tableRowCountCheckSpec = userHome
                .getConnections().getByObjectName("conn", true)
                .getTables().getByObjectName(new PhysicalTableName("sch", "tab1"), true).getSpec()
                .getChecks().getStandard().getRowCount();
        Assertions.assertNull(tableRowCountCheckSpec.getWarning());
        Assertions.assertNotNull(tableRowCountCheckSpec.getError());
        Assertions.assertNotNull(tableRowCountCheckSpec.getFatal());
        // Configs are preserved
        Assertions.assertEquals(50L, tableRowCountCheckSpec.getError().getMinCount());
        Assertions.assertEquals(20L, tableRowCountCheckSpec.getFatal().getMinCount());
        Assertions.assertTrue(tableRowCountCheckSpec.isDisabled());
    }

    @Test
    void updateAllChecksPatch_whenConnectionAndCheckGiven_enablesRequestedChecks() {
        UserHome userHome = createHierarchyTree();

        UIAllChecksPatchParameters uiAllChecksPatchParameters = new UIAllChecksPatchParameters();
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters(){{
            setConnectionName("conn");
            setCheckName("nulls_count");
        }};
        uiAllChecksPatchParameters.setCheckSearchFilters(checkSearchFilters);
        uiAllChecksPatchParameters.setFatalLevelOptions(getRuleOptionMap("max_count", 50));
        uiAllChecksPatchParameters.setDisableFatalLevel(false);

        this.sut.updateAllChecksPatch(uiAllChecksPatchParameters);

        userHome = userHomeContextFactory.openLocalUserHome().getUserHome();
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
