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
