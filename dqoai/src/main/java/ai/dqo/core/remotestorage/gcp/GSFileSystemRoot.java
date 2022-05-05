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
package ai.dqo.core.remotestorage.gcp;

import ai.dqo.core.filesystem.filesystemservice.contract.AbstractFileSystemRoot;
import com.google.cloud.storage.Storage;

import java.nio.file.Path;

/**
 * File system root for a google storage bucket.
 */
public class GSFileSystemRoot extends AbstractFileSystemRoot {
    private Storage storage;
    private String bucketName;

    /**
     * Creates a root file system.
     *
     * @param rootPath Root file system path. The path is just relative to the bucket. The path may be null if the root is the root folder of the bucket.
     * @param storage Configured google storage service with credentials.
     * @param bucketName Bucket name.
     */
    public GSFileSystemRoot(Path rootPath, Storage storage, String bucketName) {
        super(rootPath);
        this.storage = storage;
        this.bucketName = bucketName;
    }

    /**
     * Returns the remote bucket name.
     * @return Remote bucket name.
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * Configured google storage service, associated with credentials.
     * @return Google storage.
     */
    public Storage getStorage() {
        return storage;
    }
}
