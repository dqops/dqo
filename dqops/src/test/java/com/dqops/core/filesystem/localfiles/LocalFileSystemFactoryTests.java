/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.filesystem.localfiles;

import com.dqops.BaseTest;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityObjectMother;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocalFileSystemFactoryTests extends BaseTest {
    private LocalFileSystemFactory sut;
    private UserDomainIdentity userDomainIdentity;

    @BeforeEach
    void setUp() {
        this.sut = BeanFactoryObjectMother.getBeanFactory().getBean(LocalFileSystemFactory.class);
        this.userDomainIdentity = UserDomainIdentityObjectMother.createAdminIdentity();
    }

    @Test
    void openLocalUserHome_whenCalled_thenCreatesFileSystemForLocalHome() {
        LocalFolderTreeNode localFolderTreeNode = this.sut.openLocalUserHome(this.userDomainIdentity);
        Assertions.assertNotNull(localFolderTreeNode);
    }

    @Test
    void openLocalUserHome_whenCalledTwice_thenReturnsNewInstancesWithNewContext() {
        LocalFolderTreeNode first = this.sut.openLocalUserHome(this.userDomainIdentity);
        LocalFolderTreeNode second = this.sut.openLocalUserHome(this.userDomainIdentity);
        Assertions.assertNotNull(first);
        Assertions.assertNotNull(second);
        Assertions.assertNotSame(first, second);
        Assertions.assertNotNull(first.getContext());
        Assertions.assertNotSame(first.getContext(), second.getContext());
    }

}
