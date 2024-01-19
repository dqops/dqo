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
package com.dqops.core.secrets;

import com.dqops.core.secrets.credentials.SharedCredentialPropertySource;
import com.dqops.core.secrets.gcp.GcpSecretManagerPropertySource;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.parquet.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Provider that returns secret values. It can retrieve values from environment variables or in the future, from Secret Managers, Vaults, etc.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SecretValueProviderImpl implements SecretValueProvider {
    private static final Logger LOG = LoggerFactory.getLogger(SecretValueProviderImpl.class);

    private final ConfigurableBeanFactory beanFactory;
    private final GcpSecretManagerPropertySource gcpSecretManagerPropertySource;
    private final SharedCredentialPropertySource sharedCredentialPropertySource;
    private final ConfigurableEnvironment environment;
    private final Cache<String, String> secretValuesCache =
            CacheBuilder.newBuilder()
                    .maximumSize(10000)
                    .expireAfterAccess(1, TimeUnit.HOURS)
                    .build();

    /**
     * Default injection constructor that uses a spring placeholder resolver.
     * @param environment Spring environment.
     * @param beanFactory Spring bean factory.
     * @param gcpSecretManagerPropertySource GCP Secret Manager custom property source.
     * @param sharedCredentialPropertySource DQOps shared credential custom property source.
     */
    @Autowired
    public SecretValueProviderImpl(BeanFactory beanFactory,
                                   ConfigurableEnvironment environment,
                                   GcpSecretManagerPropertySource gcpSecretManagerPropertySource,
                                   SharedCredentialPropertySource sharedCredentialPropertySource) {
        this.environment = environment;
        this.beanFactory = (ConfigurableBeanFactory)beanFactory;
        this.gcpSecretManagerPropertySource = gcpSecretManagerPropertySource;
        this.sharedCredentialPropertySource = sharedCredentialPropertySource;
        this.environment.getPropertySources().addFirst(sharedCredentialPropertySource);
        this.environment.getPropertySources().addFirst(gcpSecretManagerPropertySource);
    }

    /**
     * Expands a value that references possible secret values. For example ${ENVIRONMENT_VARIABLE_NAME}
     * @param value Value to expand.
     * @param lookupContext Secret lookup context with the user home used to look up credentials.
     * @return Value (when no expansions possible) or an expanded value.
     */
    @Override
    public String expandValue(String value, SecretValueLookupContext lookupContext) {
        if (Strings.isNullOrEmpty(value)) {
            return value;
        }

        if (!value.contains("${")) {
            return value;
        }

        try (CurrentSecretValueLookupContext currentSecretValueLookupContext = CurrentSecretValueLookupContext.storeLookupContext(lookupContext)) {
            String resolvedAndCachedValue = this.secretValuesCache.get(value, () -> {
                try {
                    String expandedValue = this.beanFactory.resolveEmbeddedValue(value);
                    return expandedValue;
                } catch (Exception ex) {
                    throw new SecretExpandFailedException("Cannot resolve value " + value, ex);
                }
            });

            return resolvedAndCachedValue;
        }
        catch (Exception ex) {
            throw new SecretExpandFailedException("Cannot resolve value " + value, ex);
        }
    }

    /**
     * Expands properties in a given hash map. Returns a cloned instance with all property values expanded.
     *
     * @param properties Properties to expand.
     * @param lookupContext Lookup context with the user home used to look up credentials.
     * @return Expanded properties.
     */
    @Override
    public Map<String, String> expandProperties(Map<String, String> properties,
                                                SecretValueLookupContext lookupContext) {
        if (properties == null) {
            return null;
        }

        if (properties.size() == 0) {
            return properties;
        }

        LinkedHashMap<String, String> expanded = new LinkedHashMap<>();
        for (Map.Entry<String, String> keyValuePair : properties.entrySet()) {
            expanded.put(keyValuePair.getKey(), expandValue(keyValuePair.getValue(), lookupContext));
        }

        return Collections.unmodifiableMap(expanded);
    }

    /**
     * Expands entries in a given list. Returns a cloned instance with all entry values expanded.
     *
     * @param list Entries list to expand.
     * @param lookupContext Lookup context with the user home used to look up credentials.
     * @return Expanded entries.
     */
    public List<String> expandList(List<String> list,
                                   SecretValueLookupContext lookupContext){
        if (list == null) {
            return null;
        }

        if (list.size() == 0) {
            return list;
        }

        List<String> expanded = new ArrayList<>();
        for (String element : list) {
            expanded.add(expandValue(element, lookupContext));
        }
        return Collections.synchronizedList(expanded);
    }
}
