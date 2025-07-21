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

import com.dqops.core.filesystem.cache.LocalFileSystemCache;
import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.dqops.core.filesystem.localfiles.LocalFileStorageServiceImpl;
import com.dqops.metadata.storage.localfiles.HomeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Local user home (DQO_HOME) file storage service that manages files in the dqo.io system home folder.
 */
@Service
public class LocalDqoHomeFileStorageServiceImpl extends LocalFileStorageServiceImpl implements LocalDqoHomeFileStorageService {
    /**
     * Creates a local file storage service that uses the DQO_HOME folder.
     * @param homeLocationFindService Dqo home finder service.
     * @param localFileSystemCache Local file system cache.
     */
    @Autowired
    public LocalDqoHomeFileStorageServiceImpl(HomeLocationFindService homeLocationFindService,
                                              LocalFileSystemCache localFileSystemCache) {
        super(homeLocationFindService.getDqoHomePath(), HomeType.DQO_HOME, localFileSystemCache);
    }

    /**
     * Creates a local DQOps home file storage service given a direct path to the DQO_HOME folder.
     * @param homePath Path to the DQO_HOME folder.
     */
    public LocalDqoHomeFileStorageServiceImpl(String homePath,
                                              LocalFileSystemCache localFileSystemCache) {
        super(homePath, HomeType.DQO_HOME, localFileSystemCache);
    }
}
