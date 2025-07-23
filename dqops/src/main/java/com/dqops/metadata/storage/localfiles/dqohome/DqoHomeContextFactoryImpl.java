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

import com.dqops.core.filesystem.localfiles.LocalFileSystemFactory;
import com.dqops.core.filesystem.localfiles.LocalFolderTreeNode;
import com.dqops.utils.serialization.YamlSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Creates a DQO_HOME come context and loads the home model from the file system.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DqoHomeContextFactoryImpl implements DqoHomeContextFactory {
    private final YamlSerializer yamlSerializer;
    private final LocalFileSystemFactory localFileSystemFactory;
    private DqoHomeContext sharedDqoHomeContext;

    @Autowired
    public DqoHomeContextFactoryImpl(YamlSerializer yamlSerializer, LocalFileSystemFactory localFileSystemFactory) {
        this.yamlSerializer = yamlSerializer;
        this.localFileSystemFactory = localFileSystemFactory;
    }

    /**
     * Opens and returns a shared DQOps user home.
     * @return Dqo home context with an active DQO_HOME home model that is backed by the local home file system.
     */
    @Override
    public DqoHomeContext openLocalDqoHome() {
        synchronized (this) {
            if (this.sharedDqoHomeContext == null) {
                this.sharedDqoHomeContext = loadNewLocalDqoHome(false);
            }

            return this.sharedDqoHomeContext;
        }
    }

    /**
     * Loads a new DQOps user home context, accessing the files again.
     * @param readOnly Open the user home in read-only mode.
     * @return New instance of a DQOps home context with an active DQO_HOME home model that is backed by the local home file system.
     */
    public DqoHomeContext loadNewLocalDqoHome(boolean readOnly) {
        LocalFolderTreeNode homeRoot = this.localFileSystemFactory.openLocalDqoHome();
        DqoHomeContext dqoHomeContext = new DqoHomeContext(homeRoot);
        FileDqoHomeImpl fileDqoHomeModel = FileDqoHomeImpl.create(dqoHomeContext, this.yamlSerializer, readOnly);
        dqoHomeContext.setDqoHome(fileDqoHomeModel);
        return dqoHomeContext;
    }
}
