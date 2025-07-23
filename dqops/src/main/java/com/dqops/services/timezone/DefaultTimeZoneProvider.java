/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
