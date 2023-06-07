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

package ai.dqo.services.check.matching;

import ai.dqo.checks.CheckTarget;

import java.util.List;

/**
 * Singleton that stores a cache of similar checks, detecting only built-in default checks.
 * Returns the definitions of similar checks in on the table level and on the column level.
 */
public interface SimilarCheckCache {
    /**
     * Returns a container of similar table level checks.
     *
     * @return Similar table level checks.
     */
    SimilarChecksContainer getTableLevelSimilarChecks();

    /**
     * Returns a container of similar column level checks.
     *
     * @return Similar column level checks.
     */
    SimilarChecksContainer getColumnLevelSimilarChecks();

    /**
     * Finds checks similar to a given check.
     * @param checkTarget Check target (table or column).
     * @param checkName Check name.
     * @return List of similar checks to this one (including the requested check) or returns null when there are no similar checks.
     */
    List<SimilarCheckModel> findSimilarChecksTo(CheckTarget checkTarget, String checkName);
}
