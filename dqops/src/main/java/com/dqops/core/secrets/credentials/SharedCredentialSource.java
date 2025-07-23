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

import com.dqops.core.secrets.CurrentSecretValueLookupContext;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.credentials.SharedCredentialList;
import com.dqops.metadata.credentials.SharedCredentialWrapper;
import com.dqops.metadata.userhome.UserHome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Property source that reads secrets the DQOps user home's .credentials folder.
 */
@Component
public class SharedCredentialSource {
    private static final Logger LOG = LoggerFactory.getLogger(SharedCredentialSource.class);

    @Autowired
    @Lazy
    private SecretValueProvider secretValueProvider;

    /**
     * Default injection constructor.
     */
    @Autowired
    public SharedCredentialSource() {
    }

    /**
     * Retrieves a secret from the .credentials folder.
     * @param propertyName Property name (credential name).
     * @return Property value (secret).
     */
    public Object getProperty(String propertyName) {
        int indexOfDefaultValue = propertyName.indexOf(':');
        final String corePropertyName = (indexOfDefaultValue > 0) ?
                propertyName.substring(0, indexOfDefaultValue) : propertyName;
        final String defaultValue = (indexOfDefaultValue > 0) ?
                propertyName.substring(indexOfDefaultValue + 1) : null;

        SecretValueLookupContext currentLookupContext = CurrentSecretValueLookupContext.getCurrentLookupContext();

        try {
            UserHome userHome = currentLookupContext.getUserHome();
            if (userHome == null) {
                return null;
            }

            SharedCredentialList credentials = userHome.getCredentials();
            SharedCredentialWrapper sharedCredentialWrapper = credentials.getByObjectName(corePropertyName, true);
            if (sharedCredentialWrapper == null || sharedCredentialWrapper.getObject() == null ||
                    (sharedCredentialWrapper.getObject().getTextContent() == null && sharedCredentialWrapper.getObject().getByteContent() == null)) {
                LOG.warn("Cannot find a shared secret named '" + corePropertyName + "' in the shared credentials folder .credentials/, " +
                        "the name of the file in the .credentials/ folder should match the secret name.");
                return this.secretValueProvider.expandValue(defaultValue, currentLookupContext);
            }

            if (sharedCredentialWrapper.getObject().getTextContent() != null) {
                return sharedCredentialWrapper.getObject().getTextContent();
            }

            return sharedCredentialWrapper.getObject().getByteContent();
        }
        catch (Exception ex) {
            LOG.warn("Cannot find a shared secret named '" + corePropertyName + "' in the shared credentials folder .credentials/, " +
                    "the name of the file in the .credentials/ folder should match the secret name.", ex);
            return this.secretValueProvider.expandValue(defaultValue, currentLookupContext);
        }
    }
}
