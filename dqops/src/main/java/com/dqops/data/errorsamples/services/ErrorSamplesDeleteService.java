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
package com.dqops.data.errorsamples.services;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.errorsamples.models.ErrorsSamplesFragmentFilter;
import com.dqops.data.models.DeleteStoredDataResult;

/**
 * Service that deletes error samples.
 */
public interface ErrorSamplesDeleteService {

    /**
     * Deletes the error samples from a table, applying specific filters to get the fragment (if necessary).
     * @param filter Filter for the error samples fragment that is of interest.
     * @param userIdentity User identity that specifies the data domain.
     * @return Data delete operation summary.
     */
    DeleteStoredDataResult deleteSelectedErrorSamplesFragment(ErrorsSamplesFragmentFilter filter, UserDomainIdentity userIdentity);
}
