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
