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
package com.dqops.services.check;

import com.dqops.BaseTest;
import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
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
import com.dqops.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import com.dqops.core.jobqueue.*;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalObjectMother;
import com.dqops.core.principal.UserDomainIdentityObjectMother;
import com.dqops.core.scheduler.quartz.*;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.execution.ExecutionContextFactoryImpl;
import com.dqops.execution.rules.finder.RuleDefinitionFindService;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceObjectMother;
import com.dqops.execution.sensors.finder.SensorDefinitionFindService;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceObjectMother;
import com.dqops.metadata.fields.ParameterDefinitionSpec;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.search.HierarchyNodeTreeSearcherImpl;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rules.comparison.*;
import com.dqops.services.check.mapping.*;
import com.dqops.services.check.mapping.models.*;
import com.dqops.services.check.matching.SimilarCheckCacheImpl;
import com.dqops.services.check.models.AllChecksPatchParameters;
import com.dqops.services.check.models.BulkCheckDisableParameters;
import com.dqops.services.timezone.DefaultTimeZoneProviderObjectMother;
import com.dqops.utils.BeanFactoryObjectMother;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.reflection.ReflectionService;
import com.dqops.utils.reflection.ReflectionServiceSingleton;
import com.dqops.utils.serialization.JsonSerializerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class CheckServiceImplTests extends BaseTest {
    private CheckServiceImpl sut;

    private ExecutionContextFactory executionContextFactory;
    private HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private SpecToModelCheckMappingService specToModelCheckMappingService;
    private ReflectionService reflectionService;
    private AllChecksModelFactory allChecksModelFactory;

    @BeforeEach
    public void setUp() {
        UserHomeContextFactory userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();

        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        this.executionContextFactory = new ExecutionContextFactoryImpl(userHomeContextFactory, dqoHomeContextFactory);
        this.hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(new HierarchyNodeTreeWalkerImpl());
        this.reflectionService = ReflectionServiceSingleton.getInstance();

        SensorDefinitionFindService sensorDefinitionFindService = SensorDefinitionFindServiceObjectMother.getSensorDefinitionFindService();
        RuleDefinitionFindService ruleDefinitionFindService = RuleDefinitionFindServiceObjectMother.getRuleDefinitionFindService();
        JobDataMapAdapter jobDataMapAdapter = new JobDataMapAdapterImpl(new JsonSerializerImpl(), DqoUserConfigurationPropertiesObjectMother.createDefaultUserConfiguration());
        TriggerFactory triggerFactory = new TriggerFactoryImpl(jobDataMapAdapter, DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider());
        SchedulesUtilityService schedulesUtilityService = new SchedulesUtilityServiceImpl(triggerFactory, DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider());
        SimilarCheckCacheImpl similarCheckCache = new SimilarCheckCacheImpl(reflectionService, sensorDefinitionFindService, ruleDefinitionFindService, dqoHomeContextFactory);
        this.specToModelCheckMappingService = new SpecToModelCheckMappingServiceImpl(reflectionService,
                sensorDefinitionFindService, ruleDefinitionFindService, schedulesUtilityService, similarCheckCache);
        this.allChecksModelFactory = new AllChecksModelFactoryImpl(executionContextFactory, hierarchyNodeTreeSearcher, specToModelCheckMappingService);

        ModelToSpecCheckMappingService modelToSpecCheckMappingService = new ModelToSpecCheckMappingServiceImpl(reflectionService);
        AllChecksPatchApplier allChecksPatchApplier = new AllChecksPatchApplierImpl(modelToSpecCheckMappingService);

        DqoQueueJobFactory dqoQueueJobFactory = new DqoQueueJobFactoryImpl(BeanFactoryObjectMother.getBeanFactory());
        ParentDqoJobQueue dqoJobQueue = DqoJobQueueObjectMother.getDefaultParentJobQueue();

        this.sut = new CheckServiceImpl(
                allChecksModelFactory,
                allChecksPatchApplier,
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

    private ExecutionContext createHierarchyTree() {
        ExecutionContext executionContext = this.executionContextFactory.create(UserDomainIdentityObjectMother.createAdminIdentity());
        UserHome userHome = executionContext.getUserHomeContext().getUserHome();
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
        MinCountRuleFatalParametersSpec t1rowCountFatalSpec = new MinCountRuleFatalParametersSpec();
        t1rowCountErrorSpec.setMinCount(50L);
        t1rowCountFatalSpec.setMinCount(20L);
        t1rowCountSpec.setError(t1rowCountErrorSpec);
        t1rowCountSpec.setFatal(t1rowCountFatalSpec);
        t1volumeChecksSpec.setProfileRowCount(t1rowCountSpec);
        t1categoriesSpec.setVolume(t1volumeChecksSpec);
        table1.getSpec().setProfilingChecks(t1categoriesSpec);

        TableProfilingCheckCategoriesSpec t2categoriesSpec = new TableProfilingCheckCategoriesSpec();
        TableVolumeProfilingChecksSpec t2volumeChecksSpec = new TableVolumeProfilingChecksSpec();
        TableRowCountCheckSpec t2rowCountSpec = new TableRowCountCheckSpec();
        MinCountRule1ParametersSpec t2rowCountErrorSpec = new MinCountRule1ParametersSpec();
        MinCountRuleFatalParametersSpec t2rowCountFatalSpec = new MinCountRuleFatalParametersSpec();
        t2rowCountErrorSpec.setMinCount(100L);
        t2rowCountFatalSpec.setMinCount(10L);
        t2rowCountSpec.setError(t2rowCountErrorSpec);
        t2rowCountSpec.setFatal(t2rowCountFatalSpec);
        t2volumeChecksSpec.setProfileRowCount(t2rowCountSpec);
        t2categoriesSpec.setVolume(t2volumeChecksSpec);
        table2.getSpec().setProfilingChecks(t2categoriesSpec);

        ColumnProfilingCheckCategoriesSpec col21categoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        ColumnStringsProfilingChecksSpec col21stringChecksSpec = new ColumnStringsProfilingChecksSpec();
        ColumnStringLengthAboveMaxLengthCountCheckSpec col21stringLengthAboveCheckSpec = new ColumnStringLengthAboveMaxLengthCountCheckSpec();
        MaxCountRule10ParametersSpec countRule0ParametersSpec = new MaxCountRule10ParametersSpec();
        countRule0ParametersSpec.setMaxCount(40L);
        MaxCountRule15ParametersSpec countRule0ParametersSpec1 = new MaxCountRule15ParametersSpec();
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
        col23numericChecksSpec.setDailyNegativeCount(columnNegativeCountCheckSpec);
        MaxCountRule0ParametersSpec col23max1 = new MaxCountRule0ParametersSpec();
        col23max1.setMaxCount(15L);
        columnNegativeCountCheckSpec.setWarning(col23max1);

        executionContext.getUserHomeContext().flush();
        return executionContext;
    }

    private List<FieldModel> getParametersModels(HierarchyNode obj) {
        ClassInfo classInfo =  this.reflectionService.getClassInfoForClass(obj.getClass());
        if (classInfo == null) {
            return null;
        }

        List<FieldInfo> fieldInfos = classInfo.getFields();
        if (fieldInfos == null) {
            return null;
        }

        return fieldInfos.stream()
                .filter(fieldInfo -> !fieldInfo.getClassFieldName().equals("filter"))
                .map(fieldInfo -> {
                    FieldModel fieldModel = new FieldModel();

                    ParameterDefinitionSpec parameterDefinitionSpec = new ParameterDefinitionSpec();
                    parameterDefinitionSpec.setDataType(fieldInfo.getDataType());
                    parameterDefinitionSpec.setFieldName(fieldInfo.getYamlFieldName());
                    parameterDefinitionSpec.setDisplayName(fieldInfo.getDisplayName());
                    parameterDefinitionSpec.setHelpText(fieldInfo.getHelpText());
                    parameterDefinitionSpec.setRequired(!fieldModel.isOptional());
                    parameterDefinitionSpec.setDisplayHint(fieldInfo.getDisplayHint());

                    if (fieldInfo.getSampleValues() != null) {
                        parameterDefinitionSpec.setSampleValues(Arrays.asList(fieldInfo.getSampleValues()));
                    }
                    fieldModel.setDefinition(parameterDefinitionSpec);

                    fieldModel.setValue(fieldInfo.getFieldValue(obj));

                    return fieldModel;
                }).collect(Collectors.toList());
    }

    private CheckModel patchCheckModelTemplate(AbstractCheckSpec checkSpec, CheckModel checkModel) {
        checkModel.setCheckSpec(null);
        checkModel.setSensorParametersSpec(null);
        checkModel.setConfigured(true);
        checkModel.setFilter(checkSpec.getParameters().getFilter());
        checkModel.setDisabled(checkSpec.isDisabled());

        List<FieldModel> sensorParametersModels = getParametersModels(checkSpec.getParameters());
        checkModel.setSensorParameters(sensorParametersModels);
        
        RuleThresholdsModel ruleThresholdsModel = new RuleThresholdsModel();
        
        if (checkSpec.getWarning() != null) {
            RuleParametersModel warningModel = new RuleParametersModel();
            List<FieldModel> warning = getParametersModels(checkSpec.getWarning());
            warningModel.setRuleParameters(warning);
            warningModel.setConfigured(true);
            ruleThresholdsModel.setWarning(warningModel);
        }
        if (checkSpec.getError() != null) {
            RuleParametersModel errorModel = new RuleParametersModel();
            List<FieldModel> error = getParametersModels(checkSpec.getError());
            errorModel.setRuleParameters(error);
            errorModel.setConfigured(true);
            ruleThresholdsModel.setWarning(errorModel);
        }
        if (checkSpec.getFatal() != null) {
            RuleParametersModel fatalModel = new RuleParametersModel();
            List<FieldModel> fatal = getParametersModels(checkSpec.getFatal());
            fatalModel.setRuleParameters(fatal);
            fatalModel.setConfigured(true);
            ruleThresholdsModel.setFatal(fatalModel);
        }
        checkModel.setRule(ruleThresholdsModel);
        return checkModel;
    }

    @Test
    void disableChecks_whenConnectionAndCheckGiven_disablesRequestedChecks() {
        ExecutionContext executionContext = createHierarchyTree();
        UserHome userHome = executionContext.getUserHomeContext().getUserHome();
        DqoUserPrincipal adminPrincipal = DqoUserPrincipalObjectMother.createStandaloneAdmin();

        CheckSearchFilters checkSearchFilters = new CheckSearchFilters(){{
            setConnection("conn");
            setCheckName("profile_row_count");
        }};

        TableRowCountCheckSpec tableRowCountCheckSpec = userHome
                .getConnections().getByObjectName("conn", true)
                .getTables().getByObjectName(new PhysicalTableName("sch", "tab1"), true).getSpec()
                .getProfilingChecks().getVolume().getProfileRowCount();

        Assertions.assertNull(tableRowCountCheckSpec.getWarning());
        Assertions.assertNotNull(tableRowCountCheckSpec.getError());
        Assertions.assertNotNull(tableRowCountCheckSpec.getFatal());
        Assertions.assertEquals(50L, tableRowCountCheckSpec.getError().getMinCount());
        Assertions.assertEquals(20L, tableRowCountCheckSpec.getFatal().getMinCount());
        Assertions.assertFalse(tableRowCountCheckSpec.isDisabled());

        BulkCheckDisableParameters bulkCheckDisableParameters = new BulkCheckDisableParameters();
        bulkCheckDisableParameters.setCheckSearchFilters(checkSearchFilters);
        this.sut.disableChecks(bulkCheckDisableParameters, adminPrincipal);

        ExecutionContext executionContextSecond = executionContextFactory.create(adminPrincipal.getDataDomainIdentity());
        userHome = executionContextSecond.getUserHomeContext().getUserHome();
        Collection<AbstractCheckSpec<?,?,?,?>> checksEnabled = hierarchyNodeTreeSearcher.findChecks(userHome, checkSearchFilters);
        Assertions.assertTrue(checksEnabled.isEmpty());

        CheckSearchFilters checkSearchFiltersDisabled = checkSearchFilters.clone();
        checkSearchFiltersDisabled.setEnabled(false);
        Collection<AbstractCheckSpec<?,?,?,?>> checksDisabled = hierarchyNodeTreeSearcher.findChecks(userHome, checkSearchFiltersDisabled);

        Assertions.assertNotNull(checksDisabled);
        Assertions.assertEquals(2, checksDisabled.size());
        for (AbstractCheckSpec<?,?,?,?> check: checksDisabled) {
            Assertions.assertTrue(check.isDisabled());
        }

        tableRowCountCheckSpec = userHome
                .getConnections().getByObjectName("conn", true)
                .getTables().getByObjectName(new PhysicalTableName("sch", "tab1"), true).getSpec()
                .getProfilingChecks().getVolume().getProfileRowCount();
        Assertions.assertNull(tableRowCountCheckSpec.getWarning());
        Assertions.assertNotNull(tableRowCountCheckSpec.getError());
        Assertions.assertNotNull(tableRowCountCheckSpec.getFatal());
        // Configs are preserved
        Assertions.assertEquals(50L, tableRowCountCheckSpec.getError().getMinCount());
        Assertions.assertEquals(20L, tableRowCountCheckSpec.getFatal().getMinCount());
        Assertions.assertTrue(tableRowCountCheckSpec.isDisabled());
    }

    @Test
    void disableChecks_whenSpecificTablesGiven_disablesOnlyRequestedChecks() {
        ExecutionContext executionContext = createHierarchyTree();
        UserHome userHome = executionContext.getUserHomeContext().getUserHome();
        DqoUserPrincipal adminPrincipal = DqoUserPrincipalObjectMother.createStandaloneAdmin();

        CheckSearchFilters checkSearchFilters = new CheckSearchFilters(){{
            setConnection("conn");
            setCheckName("profile_row_count");
        }};

        TableRowCountCheckSpec tableRowCountCheckSpec = userHome
                .getConnections().getByObjectName("conn", true)
                .getTables().getByObjectName(new PhysicalTableName("sch", "tab1"), true).getSpec()
                .getProfilingChecks().getVolume().getProfileRowCount();

        Assertions.assertNull(tableRowCountCheckSpec.getWarning());
        Assertions.assertNotNull(tableRowCountCheckSpec.getError());
        Assertions.assertNotNull(tableRowCountCheckSpec.getFatal());
        Assertions.assertEquals(50L, tableRowCountCheckSpec.getError().getMinCount());
        Assertions.assertEquals(20L, tableRowCountCheckSpec.getFatal().getMinCount());
        Assertions.assertFalse(tableRowCountCheckSpec.isDisabled());

        BulkCheckDisableParameters bulkCheckDisableParameters = new BulkCheckDisableParameters();
        bulkCheckDisableParameters.setCheckSearchFilters(checkSearchFilters);
        Map<String, List<String>> selectedTables = new HashMap<>();
        selectedTables.put("tab1", null);
        bulkCheckDisableParameters.setSelectedTablesToColumns(selectedTables);
        this.sut.disableChecks(bulkCheckDisableParameters, adminPrincipal);

        ExecutionContext executionContextSecond = executionContextFactory.create(adminPrincipal.getDataDomainIdentity());
        userHome = executionContextSecond.getUserHomeContext().getUserHome();
        Collection<AbstractCheckSpec<?,?,?,?>> checksEnabled = hierarchyNodeTreeSearcher.findChecks(userHome, checkSearchFilters);
        Assertions.assertEquals(1, checksEnabled.size());

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
                .getProfilingChecks().getVolume().getProfileRowCount();
        Assertions.assertNull(tableRowCountCheckSpec.getWarning());
        Assertions.assertNotNull(tableRowCountCheckSpec.getError());
        Assertions.assertNotNull(tableRowCountCheckSpec.getFatal());
        // Configs of the disabled check are preserved
        Assertions.assertEquals(50L, tableRowCountCheckSpec.getError().getMinCount());
        Assertions.assertEquals(20L, tableRowCountCheckSpec.getFatal().getMinCount());
        Assertions.assertTrue(tableRowCountCheckSpec.isDisabled());
    }

    @Test
    void updateAllChecksPatch_whenConnectionAndCheckGiven_enablesRequestedChecks() {
        ExecutionContext executionContext = createHierarchyTree();

        AllChecksPatchParameters allChecksPatchParameters = new AllChecksPatchParameters();
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters(){{
            setConnection("conn");
            setCheckName("profile_nulls_count");
        }};
        allChecksPatchParameters.setCheckSearchFilters(checkSearchFilters);

        DqoUserPrincipal principal = DqoUserPrincipalObjectMother.createStandaloneAdmin();
        List<AllChecksModel> allChecksModel = this.allChecksModelFactory.fromCheckSearchFilters(checkSearchFilters, principal);
        CheckModel checkModel = allChecksModel.stream()
                .map(AllChecksModel::getColumnChecksModel)
                .flatMap(uiAllColumnChecksModel -> uiAllColumnChecksModel.getTableColumnChecksModels().stream())
                .flatMap(uiTableColumnChecksModel -> uiTableColumnChecksModel.getColumnChecksModels().stream())
                .flatMap(uiColumnChecksModel -> uiColumnChecksModel.getCheckContainers().entrySet().stream())
                .filter(containerTypeToCheckContainer -> containerTypeToCheckContainer.getKey().getCheckType() == CheckType.profiling)
                .map(Map.Entry::getValue)
                .flatMap(uiCheckContainerModel -> uiCheckContainerModel.getCategories().stream())
                .flatMap(uiQualityCategoryModel -> uiQualityCategoryModel.getChecks().stream())
                .findAny().get();

        MaxCountRule15ParametersSpec maxCountRule = new MaxCountRule15ParametersSpec(50L);
        ColumnNullsCountCheckSpec checkSpec = new ColumnNullsCountCheckSpec();
        checkSpec.setFatal(maxCountRule);

        CheckModel checkModelTemplate = patchCheckModelTemplate(checkSpec, checkModel);
        allChecksPatchParameters.setCheckModelPatch(checkModelTemplate);

        this.sut.updateAllChecksPatch(allChecksPatchParameters, principal);

        ExecutionContext executionContextSecond = executionContextFactory.create(principal.getDataDomainIdentity());
        UserHome userHome = executionContextSecond.getUserHomeContext().getUserHome();
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

    @Test
    void updateAllChecksPatch_whenSpecificColumnsGiven_enablesOnlyRequestedChecks() {
        ExecutionContext executionContext = createHierarchyTree();

        AllChecksPatchParameters allChecksPatchParameters = new AllChecksPatchParameters();
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters(){{
            setConnection("conn");
            setCheckName("profile_nulls_count");
        }};
        allChecksPatchParameters.setCheckSearchFilters(checkSearchFilters);

        DqoUserPrincipal principal = DqoUserPrincipalObjectMother.createStandaloneAdmin();
        List<AllChecksModel> allChecksModel = this.allChecksModelFactory.fromCheckSearchFilters(checkSearchFilters, principal);
        CheckModel checkModel = allChecksModel.stream()
                .map(AllChecksModel::getColumnChecksModel)
                .flatMap(uiAllColumnChecksModel -> uiAllColumnChecksModel.getTableColumnChecksModels().stream())
                .flatMap(uiTableColumnChecksModel -> uiTableColumnChecksModel.getColumnChecksModels().stream())
                .flatMap(uiColumnChecksModel -> uiColumnChecksModel.getCheckContainers().entrySet().stream())
                .filter(containerTypeToCheckContainer -> containerTypeToCheckContainer.getKey().getCheckType() == CheckType.profiling)
                .map(Map.Entry::getValue)
                .flatMap(uiCheckContainerModel -> uiCheckContainerModel.getCategories().stream())
                .flatMap(uiQualityCategoryModel -> uiQualityCategoryModel.getChecks().stream())
                .findAny().get();

        MaxCountRule15ParametersSpec maxCountRule = new MaxCountRule15ParametersSpec(50L);
        ColumnNullsCountCheckSpec checkSpec = new ColumnNullsCountCheckSpec();
        checkSpec.setFatal(maxCountRule);

        CheckModel checkModelTemplate = patchCheckModelTemplate(checkSpec, checkModel);
        allChecksPatchParameters.setCheckModelPatch(checkModelTemplate);

        Map<String, List<String>> selectedTablesToColumns = new HashMap<>();
        List<String> selectedColumns1 = new ArrayList<>();
        selectedColumns1.add("col1");
        List<String> selectedColumns2 = new ArrayList<>();
        selectedColumns2.add("col1");
        selectedColumns2.add("col3");
        selectedTablesToColumns.put("tab1", selectedColumns1);
        selectedTablesToColumns.put("tab2", selectedColumns2);
        allChecksPatchParameters.setSelectedTablesToColumns(selectedTablesToColumns);

        this.sut.updateAllChecksPatch(allChecksPatchParameters, principal);

        ExecutionContext executionContextSecond = executionContextFactory.create(principal.getDataDomainIdentity());
        UserHome userHome = executionContextSecond.getUserHomeContext().getUserHome();
        Collection<AbstractCheckSpec<?, ?, ?, ?>> checks = hierarchyNodeTreeSearcher.findChecks(userHome, checkSearchFilters);
        Assertions.assertNotNull(checks);
        Assertions.assertEquals(3, checks.size());
        for (AbstractCheckSpec<?,?,?,?> check: checks) {
            Assertions.assertFalse(check.isDisabled());
            Assertions.assertNull(check.getWarning());
            Assertions.assertNull(check.getError());
            Assertions.assertNotNull(check.getFatal());
        }

        // The column that was left out isn't configured
        CheckSearchFilters remainingColumnChecksSearch = checkSearchFilters.clone();
        remainingColumnChecksSearch.setFullTableName("sch.tab2");
        remainingColumnChecksSearch.setColumn("col2");

        Collection<AbstractCheckSpec<?, ?, ?, ?>> remainingCheck = hierarchyNodeTreeSearcher.findChecks(userHome, remainingColumnChecksSearch);
        Assertions.assertNotNull(remainingCheck);
        Assertions.assertTrue(remainingCheck.isEmpty());
    }
}
