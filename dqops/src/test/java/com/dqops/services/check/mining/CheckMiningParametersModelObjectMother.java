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

import com.dqops.core.configuration.DqoRuleMiningConfigurationProperties;
import com.dqops.core.configuration.DqoCheckMiningConfigurationPropertiesObjectMother;
import com.dqops.rules.TargetRuleSeverityLevel;

/**
 * Object mother for {@link CheckMiningParametersModel}
 */
public class CheckMiningParametersModelObjectMother {
    /**
     * Creates a default configuration of a check mining parameter with a similar configuration as expected from the UI or default parameters.
     * @return Default configuration.
     */
    public static CheckMiningParametersModel create() {
        DqoRuleMiningConfigurationProperties checkMiningConfigurationProperties = DqoCheckMiningConfigurationPropertiesObjectMother.getDefault();

        CheckMiningParametersModel checkMiningParametersModel = new CheckMiningParametersModel();
        checkMiningParametersModel.setFailChecksAtPercentErrorRows(checkMiningConfigurationProperties.getDefaultFailChecksAtPercentErrorRows());
        checkMiningParametersModel.setMaxPercentErrorRowsForPercentChecks(checkMiningConfigurationProperties.getDefaultMaxPercentErrorRows());
        checkMiningParametersModel.setSeverityLevel(TargetRuleSeverityLevel.error);
        return checkMiningParametersModel;
    }
}
