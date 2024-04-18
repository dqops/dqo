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
package com.dqops.metadata.storage.localfiles.dqohome;

import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.utils.BeanFactoryObjectMother;
import com.dqops.utils.serialization.YamlSerializer;
import com.dqops.utils.serialization.YamlSerializerObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * Dqo home context object mother. Provides access to the dqo home object.
 */
public class DqoHomeContextObjectMother {
    /**
     * Creates an empty dqo home context that uses a virtual file system for storage.
     * @return Empty dqo home context.
     */
    public static DqoHomeContext createEmptyInMemoryFileHomeContext() {
        YamlSerializer yamlSerializer = YamlSerializerObjectMother.createNew();
        DqoHomeContext dqoHomeContext = new DqoHomeContext(new FolderTreeNode(new HomeFolderPath(UserDomainIdentity.DEFAULT_DATA_DOMAIN)));
        FileDqoHomeImpl fileDqoHomeModel = FileDqoHomeImpl.create(dqoHomeContext, yamlSerializer, false);
        dqoHomeContext.setDqoHome(fileDqoHomeModel);
        return dqoHomeContext;
    }

    /**
     * Opens a local dqo home context.
     * @return Local dqo home context.
     */
    public static DqoHomeContext getRealDqoHomeContext() {
        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        return dqoHomeContextFactory.openLocalDqoHome();
    }
}
