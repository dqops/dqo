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

import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Table similarity score.
 */
@Data
@EqualsAndHashCode
public class TableSimilarityContainer implements Cloneable {
    private long[] ts;
    private Map<String, long[]> cs = new LinkedHashMap<>();
    private Instant lm;

    /**
     * Returns table data similarity scores.
     * @return Table data similarity scores.
     */
    public long[] getTs() {
        return ts;
    }

    /**
     * Sets table data similarity scores.
     * @param ts Table data similarity scores.
     */
    public void setTs(long[] ts) {
        this.ts = ts;
    }

    /**
     * Returns column data similarity scores.
     * @return Column data similarity scores.
     */
    public Map<String, long[]> getCs() {
        return cs;
    }

    /**
     * Sets column data similarity scores.
     * @param cs Column data similarity scores.
     */
    public void setCs(Map<String, long[]> cs) {
        this.cs = cs;
    }

    /**
     * Returns the last modification timestamp of the table statistics last modification timestamp.
     * @return Data last modification timestamp.
     */
    public Instant getLm() {
        return lm;
    }

    /**
     * Sets the timestamp when the data was last modified.
     * @param lm Data last modification timestamp.
     */
    public void setLm(Instant lm) {
        this.lm = lm;
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public TableSimilarityContainer clone() {
        try {
            TableSimilarityContainer clone = (TableSimilarityContainer) super.clone();
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
