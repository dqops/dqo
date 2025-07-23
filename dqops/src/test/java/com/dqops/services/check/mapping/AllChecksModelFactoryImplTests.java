/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.check.mapping;

import com.dqops.BaseTest;
import com.dqops.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalObjectMother;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityObjectMother;
import com.dqops.core.scheduler.quartz.*;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.execution.ExecutionContextFactoryImpl;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcherImpl;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.services.check.mapping.models.AllChecksModel;
import com.dqops.services.check.matching.SimilarCheckCacheImpl;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.services.timezone.DefaultTimeZoneProviderObjectMother;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.dqops.utils.serialization.JsonSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AllChecksModelFactoryImplTests extends BaseTest {
    private AllChecksModelFactoryImpl sut;
    private ConnectionSpec connectionSpec;
    private TableSpec tableSpec;
    private ExecutionContext executionContext;

    @BeforeEach
    void setUp() {
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
        SimilarCheckCacheImpl similarCheckCache = new SimilarCheckCacheImpl(reflectionService, sensorDefinitionFindService, ruleDefinitionFindService, dqoHomeContextFactory);
        SpecToModelCheckMappingService specToModelCheckMappingService = new SpecToModelCheckMappingServiceImpl(
                reflectionService,
                sensorDefinitionFindService,
                ruleDefinitionFindService,
                schedulesUtilityService,
                similarCheckCache);

        ExecutionContextFactory executionContextFactory = new ExecutionContextFactoryImpl(
                UserHomeContextFactoryObjectMother.createWithInMemoryContext(),
                dqoHomeContextFactory
        );

        this.sut = new AllChecksModelFactoryImpl(
                executionContextFactory,
                new HierarchyNodeTreeSearcherImpl(new HierarchyNodeTreeWalkerImpl()),
                specToModelCheckMappingService);

        UserDomainIdentity adminIdentity = UserDomainIdentityObjectMother.createAdminIdentity();
        this.executionContext = executionContextFactory.create(adminIdentity, false);

        UserHome userHome = this.executionContext.getUserHomeContext().getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("conn");
        this.connectionSpec = connectionWrapper.getSpec();

        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("public", "tab1"));
        this.tableSpec = tableWrapper.getSpec();

        ColumnSpecMap columnSpecMap = this.tableSpec.getColumns();
        ColumnSpec columnSpec = new ColumnSpec();
        columnSpecMap.put("col1", columnSpec);
        this.tableSpec.setDefaultDataGroupingConfiguration(new DataGroupingConfigurationSpec());
    }

    @Test
    void fromCheckSearchFilters_whenConnectionNameGiven_thenCreatesFullConnectionUiModel() {
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters();
        checkSearchFilters.setConnection(this.connectionSpec.getConnectionName());

        DqoUserPrincipal principal = DqoUserPrincipalObjectMother.createStandaloneAdmin();
        List<AllChecksModel> allChecksModels = this.sut.findAllConfiguredAndPossibleChecks(checkSearchFilters, principal);
        Assertions.assertNotNull(allChecksModels);
        Assertions.assertEquals(1, allChecksModels.size());
    }
}
