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
package ai.dqo.core.filesystem.filesystemservice.contract;

/**
 * File metadata about a file that is about to be uploaded or downloaded.
 */
public class DqoFileMetadata {
    private long fileLength;
    private byte[] customHash;

    /**
     * Creates a file metadata object.
     * @param fileLength File content length in bytes.
     * @param customHash Custom hash bytes.
     */
    public DqoFileMetadata(long fileLength, byte[] customHash) {
        this.fileLength = fileLength;
        this.customHash = customHash;
    }

    /**
     * Returns the file content in bytes.
     * @return File length.
     */
    public long getFileLength() {
        return fileLength;
    }

    /**
     * Returns a custom file hash.
     * @return Custom file hash.
     */
    public byte[] getCustomHash() {
        return customHash;
    }
}
