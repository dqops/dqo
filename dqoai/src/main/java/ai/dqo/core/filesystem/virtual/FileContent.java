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
package ai.dqo.core.filesystem.virtual;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Objects;

/**
 * File content holder.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FileContent implements Cloneable {
    private String textContent;

    /**
     * Creates an empty file content with no content.
     */
    public FileContent() {
    }

    /**
     * Creates a file content with a text.
     * @param textContent Text content.
     */
    public FileContent(String textContent) {
        this.textContent = textContent;
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
        this.textContent = textContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileContent that = (FileContent) o;
        return Objects.equals(textContent, that.textContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textContent);
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
