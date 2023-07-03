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
package com.dqops.core.synchronization.contract;

import com.dqops.core.filesystem.metadata.FileMetadata;
import reactor.netty.ByteBufFlux;

/**
 * Response object with a downloaded file. Combines the file metadata (length and hash) with the flux of byte buffers (file content).
 */
public class DownloadFileResponse {
    private final FileMetadata metadata;
    private final ByteBufFlux byteBufFlux;

    /**
     * Creates a download file response object.
     * @param metadata Metadata object with the file length and hash.
     * @param byteBufFlux Flux of byte buffers (the file content).
     */
    public DownloadFileResponse(FileMetadata metadata, ByteBufFlux byteBufFlux) {
        this.metadata = metadata;
        this.byteBufFlux = byteBufFlux;
    }

    /**
     * Returns the file metadata object (file length and file hash).
     * @return File metadata.
     */
    public FileMetadata getMetadata() {
        return metadata;
    }

    /**
     * Returns a flux of file buffers with the file content.
     * @return Flux of file buffers (file content).
     */
    public ByteBufFlux getByteBufFlux() {
        return byteBufFlux;
    }
}
