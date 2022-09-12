package ai.dqo.utils.datetime;

import com.google.common.base.Strings;

import java.time.ZoneId;
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
}
