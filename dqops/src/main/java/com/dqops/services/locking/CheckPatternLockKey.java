/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
