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
package com.dqops.services.timezone;

import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;

import java.time.ZoneId;

/**
 * Service that returns the default time zone configured on the DQOps instance.
 */
public interface DefaultTimeZoneProvider {
    /**
     * Retrieves the default time zone. The time zone can be configured in the user's local settings. If it has not been customized, the default time zone
     * in the configuration file is taken. If the time zone has not been customized using environment variables, the default time zone is the local computer's time zone.
     * @return Default Java time zone.
     */
    ZoneId getDefaultTimeZoneId();

    /**
     * Retrieves the default time zone. The time zone can be configured in the user's local settings. If it has not been customized, the default time zone
     * in the configuration file is taken. If the time zone has not been customized using environment variables, the default time zone is the local computer's time zone.
     * @param userHomeContext DQOps User home context with parameters.
     * @return Default Java time zone.
     */
    ZoneId getDefaultTimeZoneId(UserHomeContext userHomeContext);

    /**
     * Invalidates the cached default time zone. This method is called when the use changes the default time zone using the CLI or using any other configuration option.
     */
    void invalidate();
}
