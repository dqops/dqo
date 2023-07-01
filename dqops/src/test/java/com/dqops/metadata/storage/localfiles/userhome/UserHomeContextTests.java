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
