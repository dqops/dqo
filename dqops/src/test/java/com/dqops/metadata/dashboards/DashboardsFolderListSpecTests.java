/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.dashboards;

import com.dqops.BaseTest;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.dqohome.DqoHomeObjectMother;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DashboardsFolderListSpecTests extends BaseTest {
    private DashboardsFolderListSpec sut;
    private DqoHome dqoHome;
    private DqoHomeContext dqoHomeContext;

    @BeforeEach
    void setUp() {
        this.sut = new DashboardsFolderListSpec();
        this.dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        this.dqoHome = this.dqoHomeContext.getDqoHome();
    }

    @Test
    void dqoHomeDashboards_whenSpecRetrieved_thenReturnsDefaultListOfDashboards() {
        DashboardsFolderListSpec dqoFolderList = this.dqoHome.getDashboards().getSpec();
        Assertions.assertNotNull(dqoFolderList);
        Assertions.assertTrue(dqoFolderList.size() > 1);
    }

    @Test
    void collectSimilarDashboards_whenCalledOnTheBuiltinDashboardList_thenReturnsAllPossibleDashboards() {
        DashboardsFolderListSpec dqoFolderList = this.dqoHome.getDashboards().getSpec();
        AllSimilarDashboardsContainer allSimilarDashboardsContainer = new AllSimilarDashboardsContainer();
        dqoFolderList.collectSimilarDashboards(allSimilarDashboardsContainer, dqoFolderList);

        Assertions.assertTrue(allSimilarDashboardsContainer.getSimilarDashboards().size() > 1);
    }

    @Test
    void equals_whenTwoListsEqualObjects_thenEqualsReturnsTrue() {
        DashboardsFolderListSpec originalFolderList = this.dqoHome.getDashboards().getSpec();
        AllSimilarDashboardsContainer container1 = new AllSimilarDashboardsContainer();
        originalFolderList.collectSimilarDashboards(container1, originalFolderList);
        this.sut = container1.createDashboardFolderList();

        AllSimilarDashboardsContainer otherContainer = new AllSimilarDashboardsContainer();
        originalFolderList.collectSimilarDashboards(otherContainer, originalFolderList);
        DashboardsFolderListSpec other = otherContainer.createDashboardFolderList();

        Assertions.assertEquals(this.sut, other);
    }

    @Test
    void createExpandedDashboardTree_whenCalledForDefaultDashboardList_thenCreatesExpandedTree() {
        this.sut = this.dqoHome.getDashboards().getSpec();
        DashboardsFolderListSpec expandedDashboardTree = this.sut.createExpandedDashboardTree();
        Assertions.assertNotNull(expandedDashboardTree);
        Assertions.assertEquals(this.sut.size(), expandedDashboardTree.size());
    }

//    @Test
//    @Disabled("This test should be disabled for regular use, it is used only for one-time migration of a folder tree that has all dashboards, into a dashboard configuration with parameter templates.")
//    void collectSimilarDashboards_whenCalled_thenUpdatesDefaultDashboardListInDqoHome() {
//        DashboardsFolderListSpec dqoFolderList = this.dqoHome.getDashboards().getSpec();
//        AllSimilarDashboardsContainer allSimilarDashboardsContainer = new AllSimilarDashboardsContainer();
//        dqoFolderList.collectSimilarDashboards(allSimilarDashboardsContainer, dqoFolderList);
//        DashboardsFolderListSpec recreatedDashboardFolderList = allSimilarDashboardsContainer.createDashboardFolderList();
//
//        this.dqoHome.getDashboards().setSpec(recreatedDashboardFolderList);
//        this.dqoHomeContext.flush();
//    }
}
