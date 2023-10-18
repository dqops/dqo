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
package com.dqops.metadata.credentials;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.metadata.basespecs.ObjectName;
import com.dqops.metadata.basespecs.PojoElementWrapper;

/**
 * Credential file wrapper.
 */
public interface SharedCredentialWrapper extends PojoElementWrapper<FileContent>, ObjectName<String> {
    /**
     * Gets the file name of the credential file.
     * @return Credential file name.
     */
    String getCredentialName();

    /**
     * Sets a credential file name.
     * @param credentialName Shared credential name.
     */
    void setCredentialName(String credentialName);

    /**
     * Creates a deep clone of the object.
     * @return Deeply cloned object.
     */
    SharedCredentialWrapper clone();
}
