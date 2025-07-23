/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
