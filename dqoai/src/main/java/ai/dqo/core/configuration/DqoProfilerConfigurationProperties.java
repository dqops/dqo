/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for the dqo.profiler that configures how the data profiler works.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.profiler")
@EqualsAndHashCode(callSuper = false)
public class DqoProfilerConfigurationProperties implements Cloneable {
    private int truncatedStringsLength = 50;

    /**
     * Returns the length of a the results returned by a profiler (for min, max operations) on string columns.
     * @return Maximum string length that is stored in the profiler results.
     */
    public int getTruncatedStringsLength() {
        return truncatedStringsLength;
    }

    /**
     * Sets the length of strings that are stored in the result_string column in the profiler_results table.
     * @param truncatedStringsLength Maximum string length.
     */
    public void setTruncatedStringsLength(int truncatedStringsLength) {
        this.truncatedStringsLength = truncatedStringsLength;
    }

    /**
     * Creates a clone of the object.
     * @return Cloned instance.
     */
    @Override
    public DqoProfilerConfigurationProperties clone() {
        try {
            DqoProfilerConfigurationProperties cloned = (DqoProfilerConfigurationProperties) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone object", ex);
        }
    }
}
