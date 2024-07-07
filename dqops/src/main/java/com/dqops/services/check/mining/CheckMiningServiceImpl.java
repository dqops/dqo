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

package com.dqops.services.check.mining;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.ExecutionContext;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service that proposes a configuration of data quality checks for a table and a target check type.
 */
@Service
public class CheckMiningServiceImpl {
    private final SpecToModelCheckMappingService specToModelCheckMappingService;

    @Autowired
    public CheckMiningServiceImpl(SpecToModelCheckMappingService specToModelCheckMappingService) {
        this.specToModelCheckMappingService = specToModelCheckMappingService;
    }

    /**
     * Proposes a configuration of data quality checks for the given table and the target check type and time scale.
     * @param connectionSpec Connection specification of the connection to the data source. Also identifies the provider type to propose checks supported by that provider.
     * @param tableSpec Table specification for which a list of checks is suggested.
     * @param executionContext Execution context to provide access to the user home and DQOps home.
     * @param checkType Target check type to propose.
     * @param checkTimeScale Target check time scale.
     * @return Check mining proposal.
     */
    public CheckMiningProposalModel proposeChecks(
            ConnectionSpec connectionSpec,
            TableSpec tableSpec,
            ExecutionContext executionContext,
            CheckType checkType,
            CheckTimeScale checkTimeScale) {
        CheckMiningProposalModel proposalModel = new CheckMiningProposalModel();

        AbstractRootChecksContainerSpec tableCheckRootContainer = tableSpec.getTableCheckRootContainer(
                checkType, checkTimeScale, false, true);



        return proposalModel;
    }
}
