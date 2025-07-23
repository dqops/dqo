/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.jobqueue.concurrency;

import com.dqops.core.configuration.DqoQueueConfigurationProperties;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.dqops.core.dqocloud.apikey.DqoCloudLimit;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Returns the limit of parallel jobs that is supported.
 */
@Component
public class ParallelJobLimitProviderImpl implements ParallelJobLimitProvider {
    private final DqoCloudApiKeyProvider dqoCloudApiKeyProvider;
    private final DqoQueueConfigurationProperties dqoQueueConfigurationProperties;
    private final DqoUserPrincipalProvider userPrincipalProvider;

    /**
     * Default dependency injection constructor.
     * @param dqoCloudApiKeyProvider DQOps Cloud api key provider - used to retrieve the licensed limit of concurrent jobs.
     * @param dqoQueueConfigurationProperties DQOps Queue configuration parameters with a user-provided additional concurrency limit.
     * @param userPrincipalProvider User principal provider.
     */
    @Autowired
    public ParallelJobLimitProviderImpl(DqoCloudApiKeyProvider dqoCloudApiKeyProvider,
                                        DqoQueueConfigurationProperties dqoQueueConfigurationProperties,
                                        DqoUserPrincipalProvider userPrincipalProvider) {
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
        this.dqoQueueConfigurationProperties = dqoQueueConfigurationProperties;
        this.userPrincipalProvider = userPrincipalProvider;
    }

    /**
     * Finds the supported parallel job limits for the job queue and the job scheduler.
     * @return Maximum number of parallel threads that are supported.
     */
    @Override
    public int getMaxDegreeOfParallelism() {
        DqoUserPrincipal userPrincipalForAdministrator = this.userPrincipalProvider.createLocalInstanceAdminPrincipal();
        DqoCloudApiKey apiKey = this.dqoCloudApiKeyProvider.getApiKey(userPrincipalForAdministrator.getDataDomainIdentity());
        if (apiKey == null) {
            return 1;
        }

        Integer jobLimits = apiKey.getApiKeyPayload().getLimits().get(DqoCloudLimit.JOBS_LIMIT);
        if (jobLimits == null) {
            return 1;
        }

        if (this.dqoQueueConfigurationProperties.getMaxConcurrentJobs() != null) {
            return Math.min(this.dqoQueueConfigurationProperties.getMaxConcurrentJobs(), jobLimits);
        }

        return jobLimits;
    }
}
