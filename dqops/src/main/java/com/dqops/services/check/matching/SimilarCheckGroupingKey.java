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

/**
 * Key object used to find similar checks in other check types.
 * It should support equals and hashcode.
 */
public interface SimilarCheckGroupingKey {
    /**
     * Predicate method to determine whether the check belongs to the similar check group identified by this key.
     * @param checkSpec Check spec to match to the group.
     * @return True if provided check matches the group.
     */
    boolean matches(AbstractCheckSpec<?, ?, ?, ?> checkSpec);
}
