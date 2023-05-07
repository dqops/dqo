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

package ai.dqo.core.jobqueue.concurrency;

import ai.dqo.core.dqocloud.apikey.DqoCloudApiKey;
import ai.dqo.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import ai.dqo.core.dqocloud.apikey.DqoCloudLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Returns the limit of parallel jobs that is supported.
 */
@Component
public class ParallelJobLimitProviderImpl implements ParallelJobLimitProvider {
    private DqoCloudApiKeyProvider dqoCloudApiKeyProvider;

    @Autowired
    public ParallelJobLimitProviderImpl(DqoCloudApiKeyProvider dqoCloudApiKeyProvider) {
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
    }

    /**
     * Finds the supported parallel job limits for the job queue and the job scheduler.
     * @return Maximum number of parallel threads that are supported.
     */
    @Override
    public int getMaxDegreeOfParallelism() {
        DqoCloudApiKey apiKey = this.dqoCloudApiKeyProvider.getApiKey();
        if (apiKey == null) {
            return 1;
        }

        Integer jobLimits = apiKey.getApiKeyPayload().getLimits().get(DqoCloudLimit.JOBS_LIMIT);
        if (jobLimits == null) {
            return 1;
        }

        return jobLimits;
    }
}
