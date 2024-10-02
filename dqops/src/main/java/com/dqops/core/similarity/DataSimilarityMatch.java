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
