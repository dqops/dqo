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

package com.dqops.metadata.settings.instancename;

/**
 * Service that knows the DQOps instance name and will return the instance name when requested.
 * The order of retrieving the instance name: local settings file, instance name command line parameter, environment variable, the host name of this instance.
 */
public interface InstanceNameProvider {
    /**
     * Retrieves the current DQOps instance name.
     *
     * @return Current DQOps instance name.
     */
    String getInstanceName();

    /**
     * Invalidates the default DQOps instance name. Should be called when the name in the local settings has changed.
     */
    void invalidate();
}
