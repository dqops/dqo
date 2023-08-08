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

/**
 * Service that returns (and generates) a local signature key that is used to sign the API keys.
 */
public interface InstanceSignatureKeyProvider {
    /**
     * Returns an instance signature key. This method will generate and save an instance key if it is missing (it has a side condition).
     *
     * @return Instance signature key.
     */
    byte[] getInstanceSignatureKey();

    /**
     * Invalidates the instance signature key. The key will be retrieved from the configuration again.
     */
    void invalidate();
}
