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

import com.dqops.core.filesystem.cache.LocalFileSystemCacheObjectMother;
import com.dqops.core.filesystem.localfiles.LocalFolderTreeNode;
import com.dqops.core.filesystem.localfiles.LocalFolderTreeNodeObjectMother;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityObjectMother;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.utils.serialization.JsonSerializer;
import com.dqops.utils.serialization.JsonSerializerObjectMother;
import com.dqops.utils.serialization.YamlSerializer;
import com.dqops.utils.serialization.YamlSerializerObjectMother;
import org.junit.jupiter.api.Assertions;

/**
 * User home context object mother.
 */
public final class UserHomeContextObjectMother {
    /**
     * Creates a user home context that uses an in-memory file system only.
     * @return User home context tht uses a virtual file system.
     */
    public static UserHomeContext createInMemoryFileHomeContext() {
        YamlSerializer yamlSerializer = YamlSerializerObjectMother.getDefault();
        JsonSerializer jsonSerializer = JsonSerializerObjectMother.getDefault();
        UserDomainIdentity adminIdentity = UserDomainIdentityObjectMother.createAdminIdentity();
        UserHomeContext userHomeContext = new UserHomeContext(new FolderTreeNode(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN)), adminIdentity);
        FileUserHomeImpl fileUserHomeModel = FileUserHomeImpl.create(userHomeContext, yamlSerializer, jsonSerializer, false);
        userHomeContext.setUserHome(fileUserHomeModel);
        return userHomeContext;
    }

    /**
     * Creates a user home context that uses an in-memory file system only and has a sample table added.
     * @param sampleTableMetadata Sample table metadata.
     * @return User home context tht uses a virtual file system.
     */
    public static UserHomeContext createInMemoryFileHomeContextForSampleTable(SampleTableMetadata sampleTableMetadata) {
        YamlSerializer yamlSerializer = YamlSerializerObjectMother.getDefault();
        JsonSerializer jsonSerializer = JsonSerializerObjectMother.getDefault();
        UserDomainIdentity adminIdentity = UserDomainIdentityObjectMother.createAdminIdentity();
        UserHomeContext userHomeContext = new UserHomeContext(new FolderTreeNode(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN)), adminIdentity);
        FileUserHomeImpl fileUserHomeModel = FileUserHomeImpl.create(userHomeContext, yamlSerializer, jsonSerializer, false);
        userHomeContext.setUserHome(fileUserHomeModel);

		addSampleTable(userHomeContext, sampleTableMetadata);

        return userHomeContext;
    }

    /**
     * Adds a sample table to the user context.
     * @param userHomeContext Target user home context to add the sample table spec and its connection spec.
     * @param sampleTableMetadata Sample table with the connection spec and the table spec.
     */
    public static void addSampleTable(UserHomeContext userHomeContext, SampleTableMetadata sampleTableMetadata) {
//        Assertions.assertNull(sampleTableMetadata.getConnectionSpec().getHierarchyId(), "We have a race condition, the spec object was already attached to a home context");

        ConnectionWrapper connectionWrapper = userHomeContext.getUserHome().getConnections().getByObjectName(sampleTableMetadata.getConnectionName(), true);
        if (connectionWrapper == null) {
            connectionWrapper = userHomeContext.getUserHome().getConnections().createAndAddNew(sampleTableMetadata.getConnectionName());
            connectionWrapper.setSpec(sampleTableMetadata.getConnectionSpec());
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(sampleTableMetadata.getTableSpec().getPhysicalTableName());
        tableWrapper.setSpec(sampleTableMetadata.getTableSpec());

        LocalFileSystemCacheObjectMother.invalidateAll();
    }

    /**
     * Creates a temporary (target/temporary-user-home) folder used for init testing.
     * @param recreateTemporaryHomeFolder Recreate the temporary user home, removing all files.
     * @return User home context that uses a temporary user home (full file based).
     */
    public static UserHomeContext createTemporaryFileHomeContext(boolean recreateTemporaryHomeFolder) {
        LocalFolderTreeNode homeFolder = LocalFolderTreeNodeObjectMother.createEmptyTemporaryUserHome(recreateTemporaryHomeFolder);
        YamlSerializer yamlSerializer = YamlSerializerObjectMother.getDefault();
        JsonSerializer jsonSerializer = JsonSerializerObjectMother.getDefault();
        UserDomainIdentity adminIdentity = UserDomainIdentityObjectMother.createAdminIdentity();
        UserHomeContext userHomeContext = new UserHomeContext(homeFolder, adminIdentity);
        FileUserHomeImpl fileUserHomeModel = FileUserHomeImpl.create(userHomeContext, yamlSerializer, jsonSerializer, false);
        userHomeContext.setUserHome(fileUserHomeModel);
        return userHomeContext;
    }

    /**
     * Creates a default user home (target/test-user-home) folder used for init testing. It is the same user home folder
     * that would be created by creating a user home from the Spring IoC, but we can also request to clean it.
     * @param recreateDefaultHomeFolder Recreate the temporary user home, removing all files.
     * @return User home context that uses a default (test) user home (full file based).
     */
    public static UserHomeContext createDefaultHomeContext(boolean recreateDefaultHomeFolder) {
        LocalFolderTreeNode homeFolder = LocalFolderTreeNodeObjectMother.createDefaultUserHome(recreateDefaultHomeFolder);
        YamlSerializer yamlSerializer = YamlSerializerObjectMother.getDefault();
        JsonSerializer jsonSerializer = JsonSerializerObjectMother.getDefault();
        UserDomainIdentity adminIdentity = UserDomainIdentityObjectMother.createAdminIdentity();
        UserHomeContext userHomeContext = new UserHomeContext(homeFolder, adminIdentity);
        FileUserHomeImpl fileUserHomeModel = FileUserHomeImpl.create(userHomeContext, yamlSerializer, jsonSerializer, false);
        userHomeContext.setUserHome(fileUserHomeModel);
        return userHomeContext;
    }

    /**
     * Creates a file home context given a virtual file system implementation
     * @param homeFolder Home folder.
     * @return User home context that uses a given folder.
     */
    public static UserHomeContext createFileHomeContext(FolderTreeNode homeFolder) {
        YamlSerializer yamlSerializer = YamlSerializerObjectMother.getDefault();
        JsonSerializer jsonSerializer = JsonSerializerObjectMother.getDefault();
        UserDomainIdentity adminIdentity = UserDomainIdentityObjectMother.createAdminIdentity();
        UserHomeContext userHomeContext = new UserHomeContext(homeFolder, adminIdentity);
        FileUserHomeImpl fileUserHomeModel = FileUserHomeImpl.create(userHomeContext, yamlSerializer, jsonSerializer, false);
        userHomeContext.setUserHome(fileUserHomeModel);
        return userHomeContext;
    }
}
