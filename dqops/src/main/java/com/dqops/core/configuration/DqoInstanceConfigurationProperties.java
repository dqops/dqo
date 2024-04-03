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

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for the dqo.instance parameters.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.instance")
@EqualsAndHashCode(callSuper = false)
public class DqoInstanceConfigurationProperties implements Cloneable {
    private String signatureKey;
    private String returnBaseUrl;
    private boolean validateReturnBaseUrl = false;
    private int authenticationTokenExpirationMinutes = 24 * 60 * 7; // 7 days

    /**
     * Returns a signature key that is used instead of the signature key from the local settings. It is a base64 encoded byte array.
     * @return Signature key, base64 encoded.
     */
    public String getSignatureKey() {
        return signatureKey;
    }

    /**
     * Sets a signature key that is used to sign api keys.
     * @param signatureKey Signature key, base64 encoded.
     */
    public void setSignatureKey(String signatureKey) {
        this.signatureKey = signatureKey;
    }

    /**
     * Returns the return base url for this DQOps instance. It is the base url where the /login page on the DQOps Cloud will return after authenticating.
     * This parameter can be modified if Spring Boot does not manage host name, port or protocol because an additional reverse proxy or a load balancer is working as a proxy.
     * @return Base url for the return address, the default value that DQOps uses internally is "http://localhost:8888/"
     */
    public String getReturnBaseUrl() {
        return returnBaseUrl;
    }

    /**
     * Sets the return base url.
     * @param returnBaseUrl Return base url.
     */
    public void setReturnBaseUrl(String returnBaseUrl) {
        this.returnBaseUrl = returnBaseUrl;
    }

    /**
     * Returns the time in minutes how long is the authentication token valid.
     * @return Authentication token valid minutes.
     */
    public int getAuthenticationTokenExpirationMinutes() {
        return authenticationTokenExpirationMinutes;
    }

    /**
     * Sets the duration of the authentication token validity.
     * @param authenticationTokenExpirationMinutes Authentication token validity.
     */
    public void setAuthenticationTokenExpirationMinutes(int authenticationTokenExpirationMinutes) {
        this.authenticationTokenExpirationMinutes = authenticationTokenExpirationMinutes;
    }

    /**
     * True when the server checks the return base url and will return an error if the base url is invalid.
     * @return True when validating the base return url.
     */
    public boolean isValidateReturnBaseUrl() {
        return validateReturnBaseUrl;
    }

    /**
     * Sets the configuration to validate the return base url.
     * @param validateReturnBaseUrl True when the return base url is validated.
     */
    public void setValidateReturnBaseUrl(boolean validateReturnBaseUrl) {
        this.validateReturnBaseUrl = validateReturnBaseUrl;
    }

    /**
     * Creates a clone of the object.
     * @return Cloned instance.
     */
    @Override
    public DqoInstanceConfigurationProperties clone() {
        try {
            DqoInstanceConfigurationProperties cloned = (DqoInstanceConfigurationProperties) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone object", ex);
        }
    }
}
