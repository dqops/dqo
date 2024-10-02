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

package com.dqops.core.similarity;

/**
 * Helper class to calculate a similarity score of sample values.
 */
public class DataSimilarityCalculator {
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
                r[(w << 6) + b] += (x & (1L << b)) == x ? c : -c;
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
                if (r[(w << 6) + b] >= 0L) {
                    s[w] |= 1L << b;
                }
            }
        }

        return s;
    }
}
