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
package ai.dqo.core.filesystem.metadata;

import ai.dqo.utils.serialization.PathAsStringJsonDeserializer;
import ai.dqo.utils.serialization.PathAsStringJsonSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * File metadata - information about the file.
 */
public class FileMetadata implements Cloneable {
    @JsonSerialize(using = PathAsStringJsonSerializer.class)
    @JsonDeserialize(using = PathAsStringJsonDeserializer.class)
    @JsonProperty("p")
    private Path relativePath;

    @JsonProperty("m")
    private long lastModifiedAt;

    @JsonProperty("h")
    private byte[] fileHash;

    @JsonProperty("s")
    private long statusCheckedAt;

    /**
     * Default constructor - to be used by the deserializer.
     */
    public FileMetadata() {
    }

    /**
     * Creates a metadata object that describes a single local file.
     * @param relativePath Path to the file that is relative to the root folder. The root folder is a root of the file kind (sensor readings files, alert files, etc.)
     * @param lastModifiedAt Last modified timestamp retrieved from the file system.
     * @param fileHash Hash of the file. It is actually a content of the .[filename].parquet.crc file if such a file was found. Otherwise, a hash must be calculated from the file.
     * @param statusCheckedAt The timestamp (now) when the file status was checked for the last time.
     */
    public FileMetadata(Path relativePath,
                        long lastModifiedAt,
                        byte[] fileHash,
                        long statusCheckedAt) {
        this.relativePath = relativePath;
        this.lastModifiedAt = lastModifiedAt;
        this.fileHash = fileHash;
        this.statusCheckedAt = statusCheckedAt;
    }

    /**
     * Returns the file name from the path (the last element of the full path).
     * @return File name.
     */
    @JsonIgnore
    public String getFileName() {
        return this.relativePath.getName(this.relativePath.getNameCount() - 1).toString();
    }

    /**
     * Returns a path to the file that is relative to the root folder. The root folder is a root of the file kind (sensor readings files, alert files, etc.)
     * @return Relative file path using a linux folder notation.
     */
    public Path getRelativePath() {
        return relativePath;
    }

    /**
     * Private setter - to be used by the deserializer.
     * @param relativePath Relative path.
     */
    private void setRelativePath(Path relativePath) {
        this.relativePath = relativePath;
    }

    /**
     * Gets the last modified timestamp retrieved from the file system.
     * @return Last modified timestamp.
     */
    public long getLastModifiedAt() {
        return lastModifiedAt;
    }

    /**
     * Private setter - to be used by the deserializer.
     * @param lastModifiedAt File last modified timestamp.
     */
    private void setLastModifiedAt(long lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    /**
     * Gets the hash of the file. It is actually a content of the .[filename].parquet.crc file if such a file was found.
     * @return File hash.
     */
    public byte[] getFileHash() {
        return fileHash;
    }

    /**
     * Private setter - to set the file hash.
     * @param fileHash File hash.
     */
    private void setFileHash(byte[] fileHash) {
        this.fileHash = fileHash;
    }

    /**
     * The timestamp when the file status was checked.
     * @return Timestamp of the time when the file status was checked.
     */
    public long getStatusCheckedAt() {
        return statusCheckedAt;
    }

    /**
     * Private setter - used by the deserializer.
     * @param statusCheckedAt Status checked timestamp.
     */
    private void setStatusCheckedAt(long statusCheckedAt) {
        this.statusCheckedAt = statusCheckedAt;
    }

    /**
     * Calculates a 64 bit hash of the file path and the hash itself. The timestamps are not part of the hash.
     * @return 64 bit farm hash.
     */
    public HashCode calculateHash64() {
        String pathString = this.relativePath.toString().replace('\\', '/');
        HashFunction hashFunction = Hashing.farmHashFingerprint64();
        HashCode pathHash = hashFunction.hashString(pathString, StandardCharsets.UTF_8);
        HashCode dataHash = hashFunction.hashBytes(this.fileHash);
        HashCode hashCode = Hashing.combineUnordered(new ArrayList<HashCode>() {{
            add(pathHash);
            add(dataHash);
        }});
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileMetadata that = (FileMetadata) o;

        return relativePath.equals(that.relativePath);
    }

    @Override
    public int hashCode() {
        return relativePath.hashCode();
    }
}
