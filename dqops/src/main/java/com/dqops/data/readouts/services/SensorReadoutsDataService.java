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
package com.dqops.data.readouts.services;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.readouts.models.SensorReadoutsListModel;

/**
 * Service that returns data from sensor readouts.
 */
public interface SensorReadoutsDataService {
    /**
     * Retrieves the complete model of the readouts of sensor activations for the given root checks container (group of checks).
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @param userDomainIdentity      User identity with the data domain.
     * @return Complete model of the sensor readouts.
     */
    SensorReadoutsListModel[] readSensorReadoutsDetailed(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                         SensorReadoutsDetailedFilterParameters loadParameters,
                                                         UserDomainIdentity userDomainIdentity);
}
