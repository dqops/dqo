/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.dqohome;

import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.utils.serialization.YamlSerializer;
import com.dqops.utils.serialization.YamlSerializerObjectMother;

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
        DqoHomeContext dqoHomeContext = new DqoHomeContext(new FolderTreeNode(new HomeFolderPath(UserDomainIdentity.ROOT_DATA_DOMAIN)));
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
