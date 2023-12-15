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
