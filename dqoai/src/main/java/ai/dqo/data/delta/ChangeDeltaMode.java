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
package ai.dqo.data.delta;

/**
 * Change delta modes supported by the storage service. Parquet files could be simply replaced, or we can detect
 * rows that should be inserted/updated/deleted.
 */
public enum ChangeDeltaMode {
    /**
     * All rows are replaced in a snapshot. The snapshot is written to disk.
     */
    REPLACE_ALL,

    /**
     * New or modified rows are found and marked for insertion, update or deletion.
     */
    INSERT_UPDATE_DELETE
}
