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

package com.dqops.services.locking;

import java.util.Objects;

/**
 * Lock target object for a default check pattern.
 */
public class CheckPatternLockKey {
    private final String pattern;

    public CheckPatternLockKey(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Returns the pattern name.
     * @return Pattern name.
     */
    public String getPattern() {
        return pattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckPatternLockKey that = (CheckPatternLockKey) o;

        return Objects.equals(pattern, that.pattern);
    }

    @Override
    public int hashCode() {
        return pattern != null ? pattern.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CheckPatternLockKey{" +
                "pattern='" + pattern + '\'' +
                '}';
    }
}
