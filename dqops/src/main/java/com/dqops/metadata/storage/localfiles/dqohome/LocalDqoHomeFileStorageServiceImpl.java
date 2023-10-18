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
