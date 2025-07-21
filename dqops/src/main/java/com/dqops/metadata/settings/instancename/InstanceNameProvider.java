/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.settings.instancename;

/**
 * Service that knows the DQOps instance name and will return the instance name when requested.
 * The order of retrieving the instance name: local settings file, instance name command line parameter, environment variable, the host name of this instance.
 */
public interface InstanceNameProvider {
    /**
     * Retrieves the current DQOps instance name.
     *
     * @return Current DQOps instance name.
     */
    String getInstanceName();

    /**
     * Invalidates the default DQOps instance name. Should be called when the name in the local settings has changed.
     */
    void invalidate();
}
