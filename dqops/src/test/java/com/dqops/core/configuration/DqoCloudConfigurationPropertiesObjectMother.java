/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.configuration;

import com.dqops.core.secrets.DevelopmentCredentialsSecretNames;
import com.dqops.core.secrets.SecretValueProviderObjectMother;
import com.dqops.utils.BeanFactoryObjectMother;
import org.apache.parquet.Strings;

import java.util.Objects;

/**
 * Object mother for the configuration.
 */
public final class DqoCloudConfigurationPropertiesObjectMother {
    private static boolean apiKeyWasConfigured = false;

    /**
     * Creates a default configuration properties that pull the settings from environment variables and the application-test.resource file.
     * @return Default configuration.
     */
    public static DqoCloudConfigurationProperties createDefaultCloudConfiguration() {
        DqoCloudConfigurationProperties configurationProperties = BeanFactoryObjectMother.getBeanFactory().getBean(DqoCloudConfigurationProperties.class).clone();
        return configurationProperties;
    }

    /**
     * Configures a testable API key. The API key is retrieved from the Secret Manager (and must be configured there by running terraform in the src/integration-test/credentials folder to upload custom credentials.
     */
    public static void configureTestableApiKey() {
        if (apiKeyWasConfigured) {
            return;
        }

        DqoCloudConfigurationProperties dqoCloudConfigurationProperties = BeanFactoryObjectMother.getBeanFactory().getBean(DqoCloudConfigurationProperties.class);
        if (Strings.isNullOrEmpty(dqoCloudConfigurationProperties.getApiKey())) {
            String apiKey = SecretValueProviderObjectMother.resolveProperty(DevelopmentCredentialsSecretNames.TESTABLE_API_KEY);
            if (!Objects.equals(apiKey, "//testable-api-key")) {
                dqoCloudConfigurationProperties.setApiKey(apiKey);
            }
        }
    }
}
