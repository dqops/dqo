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

package com.dqops.metadata.similarity;

import com.dqops.utils.exceptions.DqoRuntimeException;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Table similarity score.
 */
@Data
@EqualsAndHashCode
public class TableSimilarityStore implements Cloneable {
    private long[] ts;
    private Map<String, long[]> cs = new LinkedHashMap<>();

    /**
     * Returns table similarity scores.
     * @return Table similarity scores.
     */
    public long[] getTs() {
        return ts;
    }

    /**
     * Sets table similarity scores.
     * @param ts Table similarity scores.
     */
    public void setTs(long[] ts) {
        this.ts = ts;
    }

    /**
     * Returns column similarity scores.
     * @return Column similarity scores.
     */
    public Map<String, long[]> getCs() {
        return cs;
    }

    /**
     * Sets column similarity scores.
     * @param cs Column similarity scores.
     */
    public void setCs(Map<String, long[]> cs) {
        this.cs = cs;
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public TableSimilarityStore clone() {
        try {
            TableSimilarityStore clone = (TableSimilarityStore) super.clone();
            if (this.ts != null) {
                clone.ts = Arrays.copyOf(this.ts, this.ts.length);
            }

            clone.cs = new LinkedHashMap<>();
            for (Map.Entry<String, long[]> csEntry : this.cs.entrySet()) {
                if (csEntry.getValue() == null) {
                    continue;
                }

                clone.cs.put(csEntry.getKey(), Arrays.copyOf(csEntry.getValue(), csEntry.getValue().length));
            }

            return clone;
        }
        catch (CloneNotSupportedException cex) {
            throw new DqoRuntimeException("Clone not supported", cex);
        }
    }
}
