/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.similarity;

/**
 * Helper class that calculate a match between two similarity scores.
 */
public class DataSimilarityMatch {
    /**
     * Calculate a similarity score of two similarity codes.
     * @param xa First similarity code.
     * @param ya Second similarity code.
     * @return Similarity score.
     */
    public static int calculateMatch(long[] xa, long[] ya) {
        int m = 0;
        for (int i = 0; i < xa.length; i++) {
            long x = xa[i] ^ ya[i];

            x -= (x >>> 1) & 0x5555555555555555L;
            x = (x & 0x3333333333333333L) + ((x >>> 2) & 0x3333333333333333L);
            x = (x + (x >>> 4)) & 0x0f0f0f0f0f0f0f0fL;
            x += x >>>  8;
            x += x >>> 16;
            x += x >>> 32;
            m += (int)(x & 0x7f);
        }

        return m;
    }
}
