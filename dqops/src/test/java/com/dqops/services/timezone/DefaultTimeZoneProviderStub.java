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

public class DefaultTimeZoneProviderStub implements DefaultTimeZoneProvider {

    private ZoneId timeZone;

    public void setTimeZone(ZoneId timeZone){
        this.timeZone = timeZone;
    }

    @Override
    public ZoneId getDefaultTimeZoneId() {
        return timeZone;
    }

    @Override
    public ZoneId getDefaultTimeZoneId(UserHomeContext userHomeContext) {
        return null;
    }

    @Override
    public void invalidate() {

    }
}
