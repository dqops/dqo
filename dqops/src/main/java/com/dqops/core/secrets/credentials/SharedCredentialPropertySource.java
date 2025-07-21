/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.secrets.credentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

/**
 * DQOps shared credential property source.
 */
@Component
public class SharedCredentialPropertySource extends PropertySource<SharedCredentialSource> {
    public static final String PREFIX = "credential://";
    private final SharedCredentialSource sharedCredentialSource;

    @Autowired
    public SharedCredentialPropertySource(SharedCredentialSource sharedCredentialSource) {
        super("dqo-shared-credentials");
        this.sharedCredentialSource = sharedCredentialSource;
    }

    /**
     * Checks if the property starts with the valid prefix and delegates the value extraction to the correct value source.
     * @param propertyName Property name.
     * @return Value.
     */
    @Override
    public Object getProperty(String propertyName) {
        if (propertyName != null && propertyName.startsWith(PREFIX)) {
            return this.sharedCredentialSource.getProperty(propertyName.substring(PREFIX.length()));
        }
        return null;
    }
}
