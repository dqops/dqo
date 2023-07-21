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
package com.dqops.core.configuration;

import com.dqops.core.secrets.DevelopmentCredentialsSecretNames;
import com.dqops.core.secrets.SecretValueProviderObjectMother;
import com.dqops.utils.BeanFactoryObjectMother;
import org.apache.parquet.Strings;

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
            dqoCloudConfigurationProperties.setApiKey(apiKey);
        }
    }
}
