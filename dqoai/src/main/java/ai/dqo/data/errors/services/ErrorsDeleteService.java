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
package ai.dqo.data.errors.services;

import ai.dqo.data.errors.models.ErrorsFragmentFilter;

/**
 * Service that deletes outdated errors of a check run.
 */
public interface ErrorsDeleteService {

    /**
     * Deletes the errors from a table, applying specific filters to get the fragment (if necessary).
     * @param filter Filter for the errors fragment that is of interest.
     */
    void deleteSelectedErrorsFragment(ErrorsFragmentFilter filter);
}
