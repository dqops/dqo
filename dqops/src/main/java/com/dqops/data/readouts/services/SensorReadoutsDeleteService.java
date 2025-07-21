/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.readouts.services;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.data.readouts.models.SensorReadoutsFragmentFilter;

/**
 * Service that deletes outdated sensor readouts.
 */
public interface SensorReadoutsDeleteService {

    /**
     * Deletes the readouts from a table, applying specific filters to get the fragment (if necessary).
     * @param filter Filter for the readouts fragment that is of interest.
     * @param userIdentity User identity that specifies the data domain.
     * @return Data delete operation summary.
     */
    DeleteStoredDataResult deleteSelectedSensorReadoutsFragment(SensorReadoutsFragmentFilter filter, UserDomainIdentity userIdentity);
}
