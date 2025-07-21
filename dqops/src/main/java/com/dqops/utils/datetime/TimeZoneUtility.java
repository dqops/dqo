/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.datetime;

import com.google.common.base.Strings;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

/**
 * Timezone parse utility.
 */
public final class TimeZoneUtility {
    /**
     * Parses a timezone name to a Java ZoneId. Throws exceptions when the timezone is invalid.
     * @param timeZone Timezone name.
     * @return Parsed zone id.
     */
    public static ZoneId parseZoneId(String timeZone) {
        if (Strings.isNullOrEmpty(timeZone)) {
            return ZoneId.of("UTC");
        }

        try {
            ZoneId zoneId = ZoneId.of(timeZone);
            return zoneId;
        }
        catch (Exception ex) {
            ZoneId zoneIdShort = ZoneId.of(timeZone, ZoneId.SHORT_IDS);
            return zoneIdShort;
        }
    }

    /**
     * Parses a named timezone or returns the default timezone (UTC).
     * @param timeZoneName Time zone name.
     * @return Parsed time zone or UTC time zone.
     */
    public static TimeZone parseTimeOneOrDefault(String timeZoneName) {
        try {
            ZoneId zoneId = parseZoneId(timeZoneName);
            return TimeZone.getTimeZone(zoneId);
        }
        catch (Exception ex) {
            return TimeZone.getTimeZone("UTC");
        }
    }

    /**
     * Gets the list of available zone IDs.
     * @return List of available zone IDs.
     */
    public static List<String> getAvailableZoneIds() {
        List<String> availableZoneIds = new ArrayList<>(ZoneId.getAvailableZoneIds());
        availableZoneIds.sort(Comparator.naturalOrder());
        return availableZoneIds;
    }
}
