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

import ai.dqo.core.filesystem.localfiles.HomeLocationFindService;
import ai.dqo.core.filesystem.localfiles.LocalFileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Local user home (DQO_USER_HOME) file storage service that manages files in the user home folder.
 */
@Service
public class LocalUserHomeFileStorageServiceImpl extends LocalFileStorageServiceImpl implements LocalUserHomeFileStorageService {
    /**
     * Creates a local file storage service that uses the DQO_USER_HOME folder.
     * @param homeLocationFindService User home location finder.
     */
    @Autowired
    public LocalUserHomeFileStorageServiceImpl(HomeLocationFindService homeLocationFindService) {
        super(homeLocationFindService.getUserHomePath());
    }
}
