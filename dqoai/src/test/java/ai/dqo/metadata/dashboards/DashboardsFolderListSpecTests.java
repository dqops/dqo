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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DashboardsFolderListSpecTests extends BaseTest {
    private DashboardsFolderListSpec sut;
    private DqoHome dqoHome;

    @BeforeEach
    void setUp() {
        this.sut = new DashboardsFolderListSpec();
        this.dqoHome = DqoHomeObjectMother.getDqoHome();
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
        dqoFolderList.collectSimilarDashboards(allSimilarDashboardsContainer, null);

        Assertions.assertTrue(allSimilarDashboardsContainer.getSimilarDashboards().size() > 1);
    }
}
