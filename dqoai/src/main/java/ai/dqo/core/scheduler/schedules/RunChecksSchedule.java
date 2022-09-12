package ai.dqo.core.scheduler.schedules;

import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.utils.datetime.TimeZoneUtility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * Comparable object that specifies a schedule to run data quality checks and the timezone.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class RunChecksSchedule implements Cloneable {
    private RecurringScheduleSpec recurringSchedule;
    private String timeZone;

    public RunChecksSchedule() {
    }

    public RunChecksSchedule(RecurringScheduleSpec recurringSchedule, String timeZone) {
        this.recurringSchedule = recurringSchedule;
        this.timeZone = timeZone;
    }

    /**
     * Returns the recurring schedule that will be used.
     * @return Recurring schedule.
     */
    public RecurringScheduleSpec getRecurringSchedule() {
        return recurringSchedule;
    }

    /**
     * Sets the recurring schedule.
     * @param recurringSchedule Recurring schedule configuration.
     */
    public void setRecurringSchedule(RecurringScheduleSpec recurringSchedule) {
        this.recurringSchedule = recurringSchedule;
    }

    /**
     * Returns the Java timezone that will be used to calculate the calendar. It is the timezone of the connection.
     * @return Timezone to be used for the schedule.
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * Sets the timezone to use for all calculations.
     * @param timeZone Timezone.
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * Parses the java timezone.
     * @return Java timezone.
     */
    @JsonIgnore
    public TimeZone getJavaTimeZone() {
        try {
            ZoneId zoneId = TimeZoneUtility.parseZoneId(this.timeZone);
            return TimeZone.getTimeZone(zoneId);
        }
        catch (Exception ex) {
            return TimeZone.getTimeZone("UTC");
        }
    }

    /**
     * Creates and returns a deep copy of this object.
     */
    @Override
    public RunChecksSchedule clone() {
        try {
            RunChecksSchedule cloned = (RunChecksSchedule) super.clone();
            if (cloned.recurringSchedule != null) {
                cloned.recurringSchedule = cloned.recurringSchedule.clone();
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
    }
}
