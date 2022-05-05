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
package ai.dqo.metadata.storage.localfiles.dqohome;

import ai.dqo.core.filesystem.virtual.FolderTreeNode;
import ai.dqo.core.filesystem.virtual.HomeFolderPath;
import ai.dqo.utils.BeanFactoryObjectMother;
import ai.dqo.utils.serialization.YamlSerializer;
import ai.dqo.utils.serialization.YamlSerializerObjectMother;
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
        DqoHomeContext dqoHomeContext = new DqoHomeContext(new FolderTreeNode(new HomeFolderPath()));
        FileDqoHomeImpl fileDqoHomeModel = FileDqoHomeImpl.create(dqoHomeContext, yamlSerializer);
        dqoHomeContext.setDqoHome(fileDqoHomeModel);
        return dqoHomeContext;
    }

    /**
     * Opens a local dqo home context.
     * @return Local dqo home context.
     */
    public static DqoHomeContext getRealDqoHomeContext() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        DqoHomeContextFactory dqoHomeContextFactory = beanFactory.getBean(DqoHomeContextFactory.class);
        return dqoHomeContextFactory.openLocalDqoHome();
    }
}
