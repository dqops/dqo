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
 * Instance name provider.
 */
public class InstanceNameProviderStub implements InstanceNameProvider {
    private String instanceName;

    public InstanceNameProviderStub() {
        this("localhost");
    }

    public InstanceNameProviderStub(String instanceName) {
        this.instanceName = instanceName;
    }

    /**
     * Retrieves the current DQOps instance name.
     *
     * @return Current DQOps instance name.
     */
    @Override
    public String getInstanceName() {
        return this.instanceName;
    }

    /**
     * Invalidates the default DQOps instance name. Should be called when the name in the local settings has changed.
     */
    @Override
    public void invalidate() {

    }
}
