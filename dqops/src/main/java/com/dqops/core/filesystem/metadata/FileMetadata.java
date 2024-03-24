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
package com.dqops.core.filesystem.metadata;

import com.dqops.utils.serialization.PathAsStringJsonDeserializer;
import com.dqops.utils.serialization.PathAsStringJsonSerializer;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Instant;
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

    @JsonProperty("c")
    private String md5;

    @JsonProperty("s")
    private long statusCheckedAt;

    @JsonProperty("l")
    private long fileLength;

    @JsonIgnore
    private boolean deleted;

    /**
     * Default constructor - to be used by the deserializer.
     */
    public FileMetadata() {
    }

    /**
     * Creates a metadata object that describes a single local file.
     * @param relativePath Path to the file that is relative to the root folder. The root folder is a root of the file kind (sensor readouts files, rule results files, etc.)
     * @param lastModifiedAt Last modified timestamp retrieved from the file system.
     * @param md5 MD5 hash of the file as base64 formatted string.
     * @param statusCheckedAt The timestamp (now) when the file status was checked for the last time.
     * @param fileLength File length in bytes.
     */
    public FileMetadata(Path relativePath,
                        long lastModifiedAt,
                        String md5,
                        long statusCheckedAt,
                        long fileLength) {
        this.relativePath = relativePath;
        this.lastModifiedAt = lastModifiedAt;
        this.md5 = md5;
        this.statusCheckedAt = statusCheckedAt;
        this.fileLength = fileLength;
    }

    /**
     * Creates a file metadata for a deleted file. Stores only the file path and the deleted flag as true.
     * @param relativePath Relative path to the file.
     * @return File metadata for a deleted file.
     */
    public static FileMetadata createDeleted(Path relativePath) {
        FileMetadata fileMetadata = new FileMetadata(relativePath, 0L, null, Instant.now().toEpochMilli(), 0L);
        fileMetadata.deleted = true;
        return fileMetadata;
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
     * Returns a path to the file that is relative to the root folder. The root folder is a root of the file kind (sensor readouts files, rule results files, etc.)
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
     * Gets the hash of the file as a base64 encoded MD5 hash.
     * @return File hash as base64 encoded MD5.
     */
    public String getMd5() {
        return md5;
    }

    /**
     * Private setter - to set the file hash.
     * @param md5 File hash as base64 encoded MD5.
     */
    private void setMd5(String md5) {
        this.md5 = md5;
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
     * Returns the file size.
     * @return File size in bytes.
     */
    public long getFileLength() {
        return fileLength;
    }

    /**
     * Private setter - used by the deserializer.
     * @param fileLength File length.
     */
    private void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    /**
     * Returns true if the file is deleted.
     * @return True when the file is deleted.
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the flag to mark the file as deleted.
     * @param deleted True - deleted, false - not deleted.
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Called by Jackson property when an undeclared property was present in the deserialized YAML or JSON text.
     * @param name Undeclared (and ignored) property name.
     * @param value Property value.
     */
    @JsonAnySetter
    public void handleUndeclaredProperty(String name, Object value) {
    }

    /**
     * Calculates a 64 bit hash of the file path and the hash itself. The timestamps are not part of the hash.
     * @return 64 bit farm hash.
     */
    public HashCode calculateHash64() {
        String pathString = this.relativePath.toString().replace('\\', '/');
        HashFunction hashFunction = Hashing.farmHashFingerprint64();
        HashCode pathHash = hashFunction.hashString(pathString, StandardCharsets.UTF_8);
        HashCode dataHash = this.md5 != null ? hashFunction.hashString(this.md5, StandardCharsets.UTF_8) : HashCode.fromLong(0L);
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

        return relativePath.equals(that.relativePath); // we are comparing only the paths, because we use it as a hash table key
    }

    @Override
    public int hashCode() {
        return relativePath.hashCode();
    }

    @Override
    public String toString() {
        return "FileMetadata{" +
                "relativePath=" + relativePath +
                '}';
    }
}
