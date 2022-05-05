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

/**
 * File status in the file system tree.
 */
public enum FileTreeNodeStatus {
    /**
     * The file was not loaded and the file tree node is just a virtual node with the file name.
     */
    NOT_LOADED,

    /**
     * The file was loaded and was not modified.
     */
    LOADED_NOT_MODIFIED,

    /**
     * The file was added as a new file and should be saved to the file system on flush.
     */
    NEW,

    /**
     * An existing file that was loaded and is modified.
     */
    MODIFIED,

    /**
     * An existing file that is marked for deletion on flush.
     */
    TO_BE_DELETED,

    /**
     * A file was deleted and it is present in the file tree system only as an orphaned node.
     */
    DELETED
}
