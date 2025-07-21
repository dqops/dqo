/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.mining;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.execution.ExecutionContext;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;

/**
 * Service that proposes a configuration of data quality checks for a table and a target check type. This is a data quality rule mining engine.
 */
public interface CheckMiningService {
    /**
     * Proposes a configuration of data quality checks for the given table and the target check type and time scale.
     *
     * @param connectionSpec   Connection specification of the connection to the data source. Also identifies the provider type to propose checks supported by that provider.
     * @param tableSpec        Table specification for which a list of checks is suggested.
     * @param executionContext Execution context to provide access to the user home and DQOps home.
     * @param checkType        Target check type to propose.
     * @param checkTimeScale   Target check time scale.
     * @param miningParameters Check mining parameters.
     * @return Check mining proposal.
     */
    CheckMiningProposalModel proposeChecks(
            ConnectionSpec connectionSpec,
            TableSpec tableSpec,
            ExecutionContext executionContext,
            CheckType checkType,
            CheckTimeScale checkTimeScale,
            CheckMiningParametersModel miningParameters);
}
