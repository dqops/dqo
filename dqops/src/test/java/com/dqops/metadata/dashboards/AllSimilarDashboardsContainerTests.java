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

package com.dqops.metadata.dashboards;

import com.dqops.BaseTest;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.dqohome.DqoHomeObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AllSimilarDashboardsContainerTests extends BaseTest {
    private AllSimilarDashboardsContainer sut;
    private DqoHome dqoHome;

    @BeforeEach
    void setUp() {
        this.sut = new AllSimilarDashboardsContainer();
        this.dqoHome = DqoHomeObjectMother.getDqoHome();
    }

    @Test
    void createDashboardFolderList_whenCalledOnRealDashboards_thenBuildsDashboardTreeFromUniqueDashboards() {
        DashboardsFolderListSpec originalFolderList = this.dqoHome.getDashboards().getSpec();
        originalFolderList.collectSimilarDashboards(this.sut, originalFolderList);

        DashboardsFolderListSpec resultFolderList = this.sut.createDashboardFolderList();
        Assertions.assertEquals(5, resultFolderList.size());
    }

}
