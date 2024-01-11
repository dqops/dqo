/*
 * Copyright © 2024 DQOps (support@dqops.com)
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

package com.dqops.services.check.matching;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.services.check.mapping.models.CheckModel;

public interface SimilarCheckGroupingKeyFactory {
    /**
     * Create a matching key to separate checks into groups.
     * @param checkModel Check model for which to match the similar check grouping key.
     * @return Similar check grouping key.
     */
    SimilarCheckGroupingKey createSimilarCheckGroupingKey(CheckModel checkModel);

    /**
     * Create a matching key to separate checks into groups.
     * @param checkSpec Check spec for which to match the similar check grouping key.
     * @return Similar check grouping key.
     */
    SimilarCheckGroupingKey createSimilarCheckGroupingKey(AbstractCheckSpec<?, ?, ?, ?> checkSpec);
}
