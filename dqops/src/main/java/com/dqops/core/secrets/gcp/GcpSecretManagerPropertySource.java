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
package com.dqops.core.secrets.gcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

/**
 * GCP Secret Manager property source.
 */
@Component
public class GcpSecretManagerPropertySource extends PropertySource<GcpSecretManagerSource> {
    public static final String PREFIX = "sm://";
    private final GcpSecretManagerSource secretManagerSource;

    @Autowired
    public GcpSecretManagerPropertySource(GcpSecretManagerSource secretManagerSource) {
        super("gcp-secret-manager");
        this.secretManagerSource = secretManagerSource;
    }

    /**
     * Checks if the property starts with the valid prefix and delegates the value extraction to the correct value source.
     * @param propertyName Property name.
     * @return Value.
     */
    @Override
    public Object getProperty(String propertyName) {
        if (propertyName != null && propertyName.startsWith(PREFIX)) {
            return this.secretManagerSource.getProperty(propertyName.substring(PREFIX.length()));
        }
        return null;
    }
}
