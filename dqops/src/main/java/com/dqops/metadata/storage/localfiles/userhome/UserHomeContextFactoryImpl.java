/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.userhome;

import com.dqops.core.filesystem.localfiles.LocalFileSystemFactory;
import com.dqops.core.filesystem.localfiles.LocalFolderTreeNode;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.utils.serialization.JsonSerializer;
import com.dqops.utils.serialization.YamlSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates a user come context and loads the home model from the file system.
 */
@Component
public class UserHomeContextFactoryImpl implements UserHomeContextFactory {
    private final YamlSerializer yamlSerializer;
    private final JsonSerializer jsonSerializer;
    private final LocalFileSystemFactory localFileSystemFactory;
    private final UserHomeContextCache userHomeContextCache;

    /**
     * Default injection constructor.
     * @param yamlSerializer Configured yaml serializer.
     * @param jsonSerializer Configured json serializer.
     * @param localFileSystemFactory Local file system factory to manage local files.
     */
    @Autowired
    public UserHomeContextFactoryImpl(YamlSerializer yamlSerializer,
                                      JsonSerializer jsonSerializer,
                                      LocalFileSystemFactory localFileSystemFactory,
                                      UserHomeContextCache userHomeContextCache) {
        this.yamlSerializer = yamlSerializer;
        this.jsonSerializer = jsonSerializer;
        this.localFileSystemFactory = localFileSystemFactory;
        this.userHomeContextCache = userHomeContextCache;
        userHomeContextCache.setUserHomeContextFactory(this);
    }

    /**
     * Opens a local home context, loads the files from the local file system.
     * @param userDomainIdentity User identity that identifies the user for whom we are opening the user home and the data domain for which we are opening the DQOps user home.
     * @param readOnly Make the context read-only.
     * @return User home context with an active user home model that is backed by the local home file system.
     */
    @Override
    public UserHomeContext openLocalUserHome(UserDomainIdentity userDomainIdentity, boolean readOnly) {
        LocalFolderTreeNode homeRoot = this.localFileSystemFactory.openLocalUserHome(userDomainIdentity);
        UserHomeContext userHomeContext = new UserHomeContext(homeRoot, userDomainIdentity);
        FileUserHomeImpl fileUserHomeModel = FileUserHomeImpl.create(userHomeContext, this.yamlSerializer, this.jsonSerializer, readOnly);
        userHomeContext.setUserHome(fileUserHomeModel);
        userHomeContext.setUserModelCache(this.userHomeContextCache);
        return userHomeContext;
    }
}
