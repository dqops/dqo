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
 * Helper class to calculate a similarity score of sample values.
 */
public class DataSimilarityFormula {
    /**
     * Hash seeds.
     */
    private static final long[] SEEDS = new long[] { 0x80aee9b1521cff73L, 0xaef13661c3891612L, 0xd9b062cfb56a1592L, 0xe82be4fa6de9f1dcL };

    /**
     * Similarity score length in words.
     */
    public static final int WORD_COUNT = SEEDS.length;
    private long[] r = new long[SEEDS.length * 64];

    /**
     * Appends a hash
     * @param h Hash.
     * @param c Count.
     */
    public void append(long h, long c) {
        for (int w = 0; w < SEEDS.length; w++) {
            long x = h ^ SEEDS[w];
            x = (x ^ (x >>> 30)) * 0xbf58476d1ce4e5b9L;
            x = (x ^ (x >>> 27)) * 0x94d049bb133111ebL;
            x = x ^ (x >>> 31);

            for (int b = 0; b < 64; b++) {
                r[(w << 6) + b] += (x & ~(1L << b)) == x ? -c : c;
            }
        }
    }

    /**
     * Returns a similarity score.
     * @return Similarity score.
     */
    public long[] getScore() {
        long[] s = new long[WORD_COUNT];

        for (int w = 0; w < s.length; w++) {
            for (int b = 0; b < 64; b++) {
                s[w] |= r[(w << 6) + b] >= 0 ? 1L << b : 0L;
            }
        }

        return s;
    }
}
