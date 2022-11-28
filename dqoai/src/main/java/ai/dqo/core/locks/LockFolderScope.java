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
package ai.dqo.core.locks;

import ai.dqo.core.filesystem.BuiltInFolderNames;
import ai.dqo.core.filesystem.virtual.HomeFolderPath;

import java.util.Objects;

/**
 * Lock scopes - identifies folders that are locked for shared read and exclusive write.
 */
public enum LockFolderScope {
    SOURCES,
    RULES,
    SENSORS,
    SENSOR_READOUTS,
    RULE_RESULTS;

    /**
     * Creates a folder lock scope from the folder path.
     * @param folderPath Folder path.
     * @return Lock folder scope or null when locking on the target folder is not supported.
     */
    public static LockFolderScope fromHomeFolderPath(HomeFolderPath folderPath) {
        if (folderPath.isEmpty()) {
            return null;
        }

        String folder1 = folderPath.get(0).getObjectName();
        if (Objects.equals(folder1, BuiltInFolderNames.SOURCES)) {
            return SOURCES;
        }

        if (Objects.equals(folder1, BuiltInFolderNames.SENSORS)) {
            return SENSORS;
        }

        if (Objects.equals(folder1, BuiltInFolderNames.RULES)) {
            return RULES;
        }

        if (Objects.equals(folder1, BuiltInFolderNames.DATA) && folderPath.size() > 1) {
            String folder2 = folderPath.get(1).getObjectName();
            if (Objects.equals(folder2, BuiltInFolderNames.SENSOR_READOUTS)) {
                return SENSOR_READOUTS;
            }
            else if (Objects.equals(folder2, BuiltInFolderNames.RULE_RESULTS)) {
                return RULE_RESULTS;
            }
        }

        return null; // unknown folder, not
    }
}
