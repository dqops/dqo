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
package ai.dqo.metadata.storage.localfiles.userhome;

import ai.dqo.core.filesystem.localfiles.LocalFolderTreeNode;
import ai.dqo.core.filesystem.localfiles.LocalFolderTreeNodeObjectMother;
import ai.dqo.core.filesystem.virtual.FolderTreeNode;
import ai.dqo.core.filesystem.virtual.HomeFolderPath;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.utils.serialization.JsonSerializer;
import ai.dqo.utils.serialization.JsonSerializerObjectMother;
import ai.dqo.utils.serialization.YamlSerializer;
import ai.dqo.utils.serialization.YamlSerializerObjectMother;
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
        UserHomeContext userHomeContext = new UserHomeContext(new FolderTreeNode(new HomeFolderPath()));
        FileUserHomeImpl fileUserHomeModel = FileUserHomeImpl.create(userHomeContext, yamlSerializer, jsonSerializer);
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
        UserHomeContext userHomeContext = new UserHomeContext(new FolderTreeNode(new HomeFolderPath()));
        FileUserHomeImpl fileUserHomeModel = FileUserHomeImpl.create(userHomeContext, yamlSerializer, jsonSerializer);
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
        Assertions.assertNull(sampleTableMetadata.getConnectionSpec().getHierarchyId(), "We have a race condition, the spec object was already attached to a home context");

        ConnectionWrapper connectionWrapper = userHomeContext.getUserHome().getConnections().createAndAddNew(sampleTableMetadata.getConnectionName());
        connectionWrapper.setSpec(sampleTableMetadata.getConnectionSpec());

        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(sampleTableMetadata.getTableSpec().getPhysicalTableName());
        tableWrapper.setSpec(sampleTableMetadata.getTableSpec());
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
        UserHomeContext userHomeContext = new UserHomeContext(homeFolder);
        FileUserHomeImpl fileUserHomeModel = FileUserHomeImpl.create(userHomeContext, yamlSerializer, jsonSerializer);
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
        UserHomeContext userHomeContext = new UserHomeContext(homeFolder);
        FileUserHomeImpl fileUserHomeModel = FileUserHomeImpl.create(userHomeContext, yamlSerializer, jsonSerializer);
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
        UserHomeContext userHomeContext = new UserHomeContext(homeFolder);
        FileUserHomeImpl fileUserHomeModel = FileUserHomeImpl.create(userHomeContext, yamlSerializer, jsonSerializer);
        userHomeContext.setUserHome(fileUserHomeModel);
        return userHomeContext;
    }
}
