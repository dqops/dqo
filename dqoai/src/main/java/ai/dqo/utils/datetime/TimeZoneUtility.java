/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.utils.datetime;

import com.google.common.base.Strings;

import java.time.ZoneId;
import java.util.ArrayList;
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
        return availableZoneIds;
    }
}
