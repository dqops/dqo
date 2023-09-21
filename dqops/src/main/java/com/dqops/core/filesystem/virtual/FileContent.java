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
package com.dqops.core.filesystem.virtual;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

/**
 * File content holder.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FileContent implements Cloneable {
    private String textContent;
    private byte[] byteContent;

    @JsonIgnore
    private Instant lastModified;

    @JsonIgnore
    private volatile Object cachedObjectInstance;

    /**
     * Creates an empty file content with no content.
     */
    public FileContent() {
        this.lastModified = Instant.now();
    }

    /**
     * Creates a file content with a text.
     * @param textContent Text content.
     */
    public FileContent(String textContent) {
        this.textContent = textContent;
        this.lastModified = Instant.now();
    }

    /**
     * Creates a file content with a text.
     * @param textContent Text content.
     * @param cachedObjectInstance Cached object instance.
     */
    public FileContent(String textContent, Object cachedObjectInstance) {
        this.textContent = textContent;
        this.cachedObjectInstance = cachedObjectInstance;
        this.lastModified = Instant.now();
    }

    /**
     * Creates a file content with a text, storing also the file modified timestamp.
     * @param textContent Text content of the file.
     * @param lastModified Last modified timestamp from the file.
     */
    public FileContent(String textContent, Instant lastModified) {
        this.textContent = textContent;
        this.lastModified = lastModified;
    }

    /**
     * Creates a file content with a byte array content.
     * @param byteContent Byte array content.
     */
    public FileContent(byte[] byteContent) {
        this.byteContent = byteContent;
        this.lastModified = Instant.now();
    }

    /**
     * Creates a file content with a byte array content.
     * @param byteContent Byte array content.
     * @param cachedObjectInstance Cached object instance.
     */
    public FileContent(byte[] byteContent, Object cachedObjectInstance) {
        this.byteContent = byteContent;
        this.cachedObjectInstance = cachedObjectInstance;
        this.lastModified = Instant.now();
    }

    /**
     * Creates a file content with a byte array content, storing also the file modified timestamp.
     * @param byteContent Byte array content of the file.
     * @param lastModified Last modified timestamp from the file.
     */
    public FileContent(byte[] byteContent, Instant lastModified) {
        this.byteContent = byteContent;
        this.lastModified = lastModified;
    }

    /**
     * Creates a file content with a text, storing also the file modified timestamp.
     * @param textContent Text content of the file.
     * @param byteContent Alternative byte array content.
     * @param lastModified Last modified timestamp from the file.
     */
    public FileContent(String textContent, byte[] byteContent, Instant lastModified) {
        this.textContent = textContent;
        this.byteContent = byteContent;
        this.lastModified = lastModified;
    }

    /**
     * Returns the text content of a file.
     * @return Text content.
     */
    public String getTextContent() {
        return textContent;
    }

    /**
     * Sets a new text content of the file.
     * @param textContent New text content.
     */
    public void setTextContent(String textContent) {
        if (this.byteContent != null) {
            throw new IllegalStateException("The object already contains a byte array");
        }
        this.textContent = textContent;
    }

    /**
     * Returns the content of the file as a byte array.
     * @return Content of the file as a byte array.
     */
    public byte[] getByteContent() {
        return byteContent;
    }

    /**
     * Stores the content of the file as a byte array.
     * @param byteContent Byte array with the content of the file.
     */
    public void setByteContent(byte[] byteContent) {
        if (this.textContent != null) {
            throw new IllegalStateException("The object already contains a text object");
        }
        this.byteContent = byteContent;
    }

    /**
     * Returns the file last modified timestamp.
     * @return File last modified timestamp.
     */
    public Instant getLastModified() {
        return lastModified;
    }

    /**
     * Sets the file last modified timestamp.
     * @param lastModified Last modified timestamp.
     */
    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * Cached object instance that was stored.
     * @return Cached object instance.
     */
    public Object getCachedObjectInstance() {
        return cachedObjectInstance;
    }

    /**
     * Stores a cached object instance.
     * @param cachedObjectInstance Cached object instance.
     */
    public void setCachedObjectInstance(Object cachedObjectInstance) {
        this.cachedObjectInstance = cachedObjectInstance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileContent that = (FileContent) o;

        if (!Objects.equals(textContent, that.textContent)) return false;
        return Arrays.equals(byteContent, that.byteContent);
    }

    @Override
    public int hashCode() {
        int result = textContent != null ? textContent.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(byteContent);
        return result;
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public FileContent clone() {
        try {
            return (FileContent) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone failed");
        }
    }
}
