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

package com.dqops.rest.models.credentials;

/**
 * Credential type - a text credential or a binary credential that must be updated as a base64 value.
 */
public enum CredentialType {
    /**
     * The credential is a valid text value.
     */
    text,

    /**
     * The credential is a binary file that cannot be parsed as an utf-8 txt value.
     */
    binary
}
