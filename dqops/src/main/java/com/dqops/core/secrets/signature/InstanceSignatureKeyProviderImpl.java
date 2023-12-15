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

package com.dqops.core.secrets.signature;

import com.dqops.core.configuration.DqoInstanceConfigurationProperties;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.settings.LocalSettingsSpec;
import com.dqops.metadata.settings.SettingsWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Service that returns (and generates) a local signature key that is used to sign the API keys.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class InstanceSignatureKeyProviderImpl implements InstanceSignatureKeyProvider {
    private UserHomeContextFactory userHomeContextFactory;
    private SecretValueProvider secretValueProvider;
    private DqoInstanceConfigurationProperties dqoInstanceConfigurationProperties;
    private DqoUserPrincipalProvider userPrincipalProvider;
    private SecureRandom secureRandom;
    private byte[] cachedInstanceKey;
    private final Object lock = new Object();

    /**
     * Default injection constructor.
     * @param userHomeContextFactory DQOps local home context factory - used to load the local user home.
     * @param secretValueProvider Secret value provider, used to decode secrets.
     * @param dqoInstanceConfigurationProperties DQOps instance (dqo.instance.*) configuration parameters.
     * @param userPrincipalProvider Current user principal provider.
     */
    @Autowired
    public InstanceSignatureKeyProviderImpl(UserHomeContextFactory userHomeContextFactory,
                                            SecretValueProvider secretValueProvider,
                                            DqoInstanceConfigurationProperties dqoInstanceConfigurationProperties,
                                            DqoUserPrincipalProvider userPrincipalProvider) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.secretValueProvider = secretValueProvider;
        this.dqoInstanceConfigurationProperties = dqoInstanceConfigurationProperties;
        this.userPrincipalProvider = userPrincipalProvider;
    }

    /**
     * Returns an instance signature key. This method will generate and save an instance key if it is missing (it has a side condition).
     * @return Instance signature key.
     */
    @Override
    public byte[] getInstanceSignatureKey() {
        synchronized (this.lock) {
            if (this.cachedInstanceKey != null) {
                return this.cachedInstanceKey;
            }

            if (!Strings.isNullOrEmpty(this.dqoInstanceConfigurationProperties.getSignatureKey())) {
                byte[] decodedSignatureKeyFromConfiguration = Base64.getDecoder().decode(
                        this.dqoInstanceConfigurationProperties.getSignatureKey());
                this.cachedInstanceKey = decodedSignatureKeyFromConfiguration;
                return decodedSignatureKeyFromConfiguration;
            }

            DqoUserPrincipal userPrincipalForAdministrator = this.userPrincipalProvider.createUserPrincipalForAdministrator();
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipalForAdministrator.getDataDomainIdentity());
            SettingsWrapper settingsWrapper = userHomeContext.getUserHome().getSettings();
            LocalSettingsSpec localSettingsSpec = settingsWrapper.getSpec();
            String instanceKeyBase64String = null;

            if (localSettingsSpec != null) {
                SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(userHomeContext.getUserHome());
                LocalSettingsSpec settings = localSettingsSpec.expandAndTrim(this.secretValueProvider, secretValueLookupContext);
                instanceKeyBase64String = settings.getInstanceSignatureKey();
            }

            byte[] instanceKeyBytes;
            if (Strings.isNullOrEmpty(instanceKeyBase64String)) {
                if (this.secureRandom == null) {
                    this.secureRandom = new SecureRandom();
                }

                instanceKeyBytes = new byte[32];
                this.secureRandom.nextBytes(instanceKeyBytes);

                if (localSettingsSpec == null) {
                    localSettingsSpec = new LocalSettingsSpec();
                    settingsWrapper.setSpec(localSettingsSpec);
                }

                String encodedNewKey = Base64.getEncoder().encodeToString(instanceKeyBytes);
                localSettingsSpec.setInstanceSignatureKey(encodedNewKey);
                userHomeContext.flush();
            } else {
                instanceKeyBytes = Base64.getDecoder().decode(instanceKeyBase64String);
            }

            this.cachedInstanceKey = instanceKeyBytes;

            return instanceKeyBytes;
        }
    }

    /**
     * Invalidates the instance signature key. The key will be retrieved from the configuration again.
     */
    @Override
    public void invalidate() {
        synchronized (this.lock) {
            this.cachedInstanceKey = null;
        }
    }
}
