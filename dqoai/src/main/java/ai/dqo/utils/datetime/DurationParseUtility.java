package ai.dqo.utils.datetime;

import com.google.common.base.Strings;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Locale;

/**
 * Utility method for parsing simple duration strings to a {@link java.time.Duration}
 */
public final class DurationParseUtility {
    /**
     * Parses a duration text. Supported texts:  "10s" (10 seconds), "20m" (20 minutes), "4h" (4 hours). When the time scale letter is not given, the default value is seconds.
     * @param text Text to parse.
     * @return Parsed duration.
     */
    public static Duration parseSimpleDuration(String text) {
        if (Strings.isNullOrEmpty(text)) {
            return null;
        }

        String lowerText = text.toLowerCase(Locale.ENGLISH);
        char lastChar = lowerText.charAt(lowerText.length() - 1);
        TemporalUnit temporalUnit = null;
        String numberText = lowerText;

        if (Character.isLetter(lastChar)) {
            numberText = lowerText.substring(0, lowerText.length() - 1);

            switch (lastChar) {
                case 's':
                    temporalUnit = ChronoUnit.SECONDS;
                    break;
                case 'm':
                    temporalUnit = ChronoUnit.MINUTES;
                    break;
                case 'h':
                    temporalUnit = ChronoUnit.HOURS;
                    break;
                default:
                    throw new InvalidDurationFormatException("Duration text " + text + " is not supported, supported formats: \"10s\" (10 seconds), \"20m\" (20 minutes), \"4h\" (4 hours).");
            }
        }
        else {
            temporalUnit = ChronoUnit.SECONDS; // the default unit
        }

        try {
            int number = Integer.parseInt(numberText);
            return Duration.of((long)number, temporalUnit);
        }
        catch (NumberFormatException ex) {
            throw new InvalidDurationFormatException("Duration text " + text + " is not supported, supported formats: \"10s\" (10 seconds), \"20m\" (20 minutes), \"4h\" (4 hours).", ex);
        }
    }
}
