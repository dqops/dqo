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
package com.dqops.core.synchronization.service;

import com.dqops.BaseIntegrationTest;
import com.dqops.connectors.postgresql.PostgresqlParametersSpec;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.fileexchange.FileSynchronizationDirection;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationListener;
import com.dqops.core.synchronization.listeners.SilentFileSystemSynchronizationListener;
import com.dqops.core.synchronization.service.DqoCloudSynchronizationService;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DqoCloudSynchronizationCliServiceIntegrationTests extends BaseIntegrationTest {
    private DqoCloudSynchronizationService sut;
    private FileSystemSynchronizationListener listener;

    @BeforeEach
    void setUp() {
        this.sut = BeanFactoryObjectMother.getBeanFactory().getBean(DqoCloudSynchronizationService.class);
        this.listener = new SilentFileSystemSynchronizationListener();
    }

    @Test
    void synchronizeFolder_whenSourcesAddedAndSyncOnNewUserCome_thenSendsAndDownloadsBackTheSource() {
        UserHomeContext firstUserHomeContext = UserHomeContextObjectMother.createDefaultHomeContext(true);
        UserDomainIdentity userDomainIdentity = firstUserHomeContext.getUserIdentity();
        ConnectionWrapper initialConnWrapper = firstUserHomeContext.getUserHome().getConnections().createAndAddNew("src1");
        PostgresqlParametersSpec postgresql = new PostgresqlParametersSpec();
        initialConnWrapper.getSpec().setPostgresql(postgresql);
        postgresql.setDatabase("DB1");
        firstUserHomeContext.flush();

        this.sut.synchronizeFolder(DqoRoot.sources, userDomainIdentity, FileSynchronizationDirection.full, false, this.listener);

        // clean
        UserHomeContext cleanUserHome = UserHomeContextObjectMother.createDefaultHomeContext(true);
        this.sut.synchronizeFolder(DqoRoot.sources, userDomainIdentity, FileSynchronizationDirection.full, false, this.listener);  // this should download
        this.sut.synchronizeFolder(DqoRoot.sources, userDomainIdentity, FileSynchronizationDirection.full, false, this.listener);  // this should detect no changes...

        UserHomeContext restoredUserHomeContext = UserHomeContextObjectMother.createDefaultHomeContext(false);
        ConnectionWrapper secondConnWrapper = restoredUserHomeContext.getUserHome().getConnections().getByObjectName("src1", true);
        Assertions.assertNotNull(secondConnWrapper);
        Assertions.assertEquals("DB1", secondConnWrapper.getSpec().getPostgresql().getDatabase());

        secondConnWrapper.markForDeletion();
        restoredUserHomeContext.flush();
        this.sut.synchronizeFolder(DqoRoot.sources, userDomainIdentity, FileSynchronizationDirection.full, false, this.listener);

        UserHomeContext userHomeAfterDelete = UserHomeContextObjectMother.createDefaultHomeContext(true);
        this.sut.synchronizeFolder(DqoRoot.sources, userDomainIdentity, FileSynchronizationDirection.full, false, this.listener);
        ConnectionWrapper connectionWrapperAfterDelete = userHomeAfterDelete.getUserHome().getConnections().getByObjectName("src1", true);
        Assertions.assertNull(connectionWrapperAfterDelete);
    }
}
