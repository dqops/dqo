/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.userhome;

import com.dqops.BaseTest;
import com.dqops.connectors.postgresql.PostgresqlParametersSpec;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.userhome.UserHome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserHomeContextTests extends BaseTest {
    @Test
    void flush_whenNewSourceAdded_thenIsWrittenToDisk() {
        UserHomeContext sut = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
        UserHome userHome = sut.getUserHome();
        ConnectionList sources = userHome.getConnections();
        ConnectionWrapper sourceWrapper = sources.createAndAddNew("src1");
        ConnectionSpec sourceModel = sourceWrapper.getSpec();
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        sourceModel.setPostgresql(postgresql);
        postgresql.setUser("user");

        sut.flush();

        UserHomeContext sut2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        ConnectionWrapper reloadedSource = sut2.getUserHome().getConnections().getByObjectName("src1", true);
        ConnectionSpec reloadedModel = reloadedSource.getSpec();
        Assertions.assertEquals("user", reloadedModel.getPostgresql().getUser());
    }
}
