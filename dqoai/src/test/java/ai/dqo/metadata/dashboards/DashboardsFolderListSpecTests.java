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
package ai.dqo.metadata.dashboards;

import ai.dqo.BaseTest;
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.metadata.dqohome.DqoHomeObjectMother;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
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
