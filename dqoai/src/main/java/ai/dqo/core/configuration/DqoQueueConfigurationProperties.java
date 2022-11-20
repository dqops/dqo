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
 * Configuration POJO with the configuration for dqo.ai. Properties are mapped to the "dqo.queue." prefix that are responsible for the configuration of the job queue.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.queue")
@EqualsAndHashCode(callSuper = false)
public class DqoQueueConfigurationProperties implements Cloneable {
    private int threads = 10;
    private Integer maxNonBlockingCapacity;  // the default is null, which is unlimited

    /**
     * Returns the number of threads that the job queue uses for processing jobs.
     * @return Number of threads for the job queue.
     */
    public int getThreads() {
        return threads;
    }

    /**
     * Sets the number of threads that will be used by the job queue.
     * @param threads Number of threads.
     */
    public void setThreads(int threads) {
        this.threads = threads;
    }

    /**
     * Maximum queue capacity before pushing new jobs will become a blocing operation.
     * @return Max non-blocking queue capacity.
     */
    public Integer getMaxNonBlockingCapacity() {
        return maxNonBlockingCapacity;
    }

    /**
     * Sets the maximum queue capacity before blocking on pushing jobs will begin.
     * @param maxNonBlockingCapacity New maximum non-blocking capacity or null when the queue capacity should be unbounded.
     */
    public void setMaxNonBlockingCapacity(Integer maxNonBlockingCapacity) {
        this.maxNonBlockingCapacity = maxNonBlockingCapacity;
    }

    /**
     * Clones the current object.
     * @return
     */
    @Override
    public DqoQueueConfigurationProperties clone() {
        try {
            return (DqoQueueConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
