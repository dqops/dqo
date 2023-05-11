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
package ai.dqo.data.checkresults.services;

import ai.dqo.data.checkresults.models.CheckResultsFragmentFilter;

/**
 * Service that deletes outdated results of a check.
 */
public interface CheckResultsDeleteService {

    /**
     * Deletes the results from a table, applying specific filters to get the fragment (if necessary).
     * @param filter Filter for the result fragment that is of interest.
     */
    void deleteSelectedCheckResultsFragment(CheckResultsFragmentFilter filter);
}
