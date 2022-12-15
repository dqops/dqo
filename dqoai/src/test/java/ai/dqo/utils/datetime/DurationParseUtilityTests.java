package ai.dqo.utils.datetime;

import ai.dqo.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@SpringBootTest
public class DurationParseUtilityTests extends BaseTest {
    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
    }

    @Test
    public void parseSimpleDuration_whenEmptyString_thenReturnsNull() {
        Assertions.assertNull(DurationParseUtility.parseSimpleDuration(""));
    }

    @Test
    public void parseSimpleDuration_whenNullString_thenReturnsNull() {
        Assertions.assertNull(DurationParseUtility.parseSimpleDuration(null));
    }

    @Test
    public void parseSimpleDuration_whenJustNumber_thenReturnsDurationSeconds() {
        Duration result = DurationParseUtility.parseSimpleDuration("50");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Duration.of(50L, ChronoUnit.SECONDS), result);
    }

    @Test
    public void parseSimpleDuration_whenJustNumberWithS_thenReturnsDurationSeconds() {
        Duration result = DurationParseUtility.parseSimpleDuration("40s");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Duration.of(40L, ChronoUnit.SECONDS), result);
    }

    @Test
    public void parseSimpleDuration_whenJustNumberWithM_thenReturnsDurationMinutes() {
        Duration result = DurationParseUtility.parseSimpleDuration("30m");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Duration.of(30L, ChronoUnit.MINUTES), result);
    }

    @Test
    public void parseSimpleDuration_whenJustNumberWithH_thenReturnsDurationHours() {
        Duration result = DurationParseUtility.parseSimpleDuration("20H");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Duration.of(20L, ChronoUnit.HOURS), result);
    }
}
