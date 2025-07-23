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
