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
package com.dqops.core.configuration;

import com.dqops.utils.logging.DqoConsoleLoggingMode;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for the dqo.logging that configures how file logging works inside the DQOps user home .log folder.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.logging")
@EqualsAndHashCode(callSuper = false)
public class DqoLoggingConfigurationProperties implements Cloneable {
    /**
     * The default logging pattern used in logs inside the user home's .logs folder.
     */
    public static final String DEFAULT_PATTERN = "%-12date{YYYY-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} -%kvp- %msg%rootException%n";

    /**
     * Default maximum number of old files that are stored.
     */
    public static final int DEFAULT_MAX_HISTORY = 7;

    /**
     * Default total file size cap for log files before files are deleted to save space.
     */
    public static final String DEFAULT_TOTAL_SIZE_CAP = "10mb";

    private boolean enableUserHomeLogging = true;
    private Integer maxHistory = DEFAULT_MAX_HISTORY;
    private String pattern = DEFAULT_PATTERN;
    private String totalSizeCap = DEFAULT_TOTAL_SIZE_CAP;
    private DqoConsoleLoggingMode console = DqoConsoleLoggingMode.OFF;
    private boolean consoleWithJansi = true;
    private boolean consoleImmediateFlush;
    private boolean logErrorsToStderr;
    private String jsonLogFieldLevel;
    private String jsonLogFieldMessage;
    private String jsonLogFieldTimestamp;
    private String jsonLogFieldArguments;
    private String jsonTimestampPattern;
    private boolean encodeMessage = true;
    private Integer jsonMessageMaxLength;

    /**
     * Returns the flag if file logging inside the user home's .log folder should be enabled.
     * @return True when logging should be enabled.
     */
    public boolean isEnableUserHomeLogging() {
        return enableUserHomeLogging;
    }

    /**
     * Sets the flag too enable logging inside the user home folder.
     * @param enableUserHomeLogging Enable logging in the user home folder.
     */
    public void setEnableUserHomeLogging(boolean enableUserHomeLogging) {
        this.enableUserHomeLogging = enableUserHomeLogging;
    }

    /**
     * Returns the number of historic log files to preserve.
     * @return Number of historic log files to preserve.
     */
    public Integer getMaxHistory() {
        return maxHistory;
    }

    /**
     * Sets the number of historic log files to store.
     * @param maxHistory Number of historic log files.
     */
    public void setMaxHistory(Integer maxHistory) {
        this.maxHistory = maxHistory;
    }

    /**
     * Returns the logging pattern for logback used for file logging.
     * @return Logback log entry pattern.
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Sets the pattern used for log entries in the file logs.
     * @param pattern Logging pattern.
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Returns the total log file size cap. For example: 10mb. See {@link ch.qos.logback.core.util.FileSize} for more patterns.
     * @return Total log file size cap.
     */
    public String getTotalSizeCap() {
        return totalSizeCap;
    }

    /**
     * Sets the total log size cap, for example "10mb".
     * @param totalSizeCap Total log file size cap.
     */
    public void setTotalSizeCap(String totalSizeCap) {
        this.totalSizeCap = totalSizeCap;
    }

    /**
     * Returns the configured console logging mode.
     * @return Console logging mode.
     */
    public DqoConsoleLoggingMode getConsole() {
        return console;
    }

    /**
     * Sets the configured console logging mode.
     * @param console Console logging mode.
     */
    public void setConsole(DqoConsoleLoggingMode console) {
        this.console = console;
    }

    /**
     * When using a standard FORMATTED_LINE console logger, enable JAnsi to show formatted messages.
     * @return True to enable JAnsi in logging.
     */
    public boolean isConsoleWithJansi() {
        return consoleWithJansi;
    }

    /**
     * Sets true to enable logging to console with JAnsi.
     * @param consoleWithJansi True - use jansi.
     */
    public void setConsoleWithJansi(boolean consoleWithJansi) {
        this.consoleWithJansi = consoleWithJansi;
    }

    /**
     * Turns on immediate flush to the console for all log entries. Usable for logging from a docker container.
     * @return True when immediate flush is enabled.
     */
    public boolean isConsoleImmediateFlush() {
        return consoleImmediateFlush;
    }

    /**
     * Enables or disables a mode to perform an immediate flush when console logging is enabled.
     * @param consoleImmediateFlush Perform an immediate flush to console.
     */
    public void setConsoleImmediateFlush(boolean consoleImmediateFlush) {
        this.consoleImmediateFlush = consoleImmediateFlush;
    }

    /**
     * Logs errors to stderr stream (Stream.err).
     * @return Logs errors to the standard error output stream.
     */
    public boolean isLogErrorsToStderr() {
        return logErrorsToStderr;
    }

    /**
     * Logs errors to the standard error stream.
     * @param logErrorsToStderr Log errors to standard error stream.
     */
    public void setLogErrorsToStderr(boolean logErrorsToStderr) {
        this.logErrorsToStderr = logErrorsToStderr;
    }

    /**
     * Returns a custom json key name for the key where the log severity (info, warn, etc) is stored in JSON formatted logs.
     * @return Returns the log level.
     */
    public String getJsonLogFieldLevel() {
        return jsonLogFieldLevel;
    }

    /**
     * Sets the field name used to store the severity.
     * @param jsonLogFieldLevel Json log severity field.
     */
    public void setJsonLogFieldLevel(String jsonLogFieldLevel) {
        this.jsonLogFieldLevel = jsonLogFieldLevel;
    }

    /**
     * Returns the field name in json where the "message" is saved. By default, it is "message".
     * @return The field name to store the message of a log entry.
     */
    public String getJsonLogFieldMessage() {
        return jsonLogFieldMessage;
    }

    /**
     * Sets the json field name where the message is stored.
     * @param jsonLogFieldMessage Field name to store the message.
     */
    public void setJsonLogFieldMessage(String jsonLogFieldMessage) {
        this.jsonLogFieldMessage = jsonLogFieldMessage;
    }

    /**
     * Returns the json field name where the timestamp is stored. The default is "time".
     * @return The field name in json where the timestamp of the log event is stored.
     */
    public String getJsonLogFieldTimestamp() {
        return jsonLogFieldTimestamp;
    }

    /**
     * Sets the field name where the timestamp is stored.
     * @param jsonLogFieldTimestamp The field where the timestamp is stored.
     */
    public void setJsonLogFieldTimestamp(String jsonLogFieldTimestamp) {
        this.jsonLogFieldTimestamp = jsonLogFieldTimestamp;
    }

    /**
     * Returns the field name in a json message that is a key/value object with additional event arguments.
     * @return The field name in a json message that is a key/value object with additional event arguments.
     */
    public String getJsonLogFieldArguments() {
        return jsonLogFieldArguments;
    }

    /**
     * Sets the field name in a json message that is a key/value object with additional event arguments.
     * @param jsonLogFieldArguments The field name in a json message that is a key/value object with additional event arguments.
     */
    public void setJsonLogFieldArguments(String jsonLogFieldArguments) {
        this.jsonLogFieldArguments = jsonLogFieldArguments;
    }

    /**
     * Returns the pattern used to format the event's timestamp field in a json formatted log.
     * @return Json field timestamp pattern.
     */
    public String getJsonTimestampPattern() {
        return jsonTimestampPattern;
    }

    /**
     * Sets the timestamp's field pattern that the event timestamp is formatted in json logs.
     * @param jsonTimestampPattern Timestamp pattern.
     */
    public void setJsonTimestampPattern(String jsonTimestampPattern) {
        this.jsonTimestampPattern = jsonTimestampPattern;
    }

    /**
     * Returns the maximum length of the message field in a json formatted log entry. Remaining characters are truncated.
     * @return Maximum length of the message field.
     */
    public Integer getJsonMessageMaxLength() {
        return jsonMessageMaxLength;
    }

    /**
     * Sets the maximum length of a message field in a json formatted log entry.
     * @param jsonMessageMaxLength Maximum length in characters.
     */
    public void setJsonMessageMaxLength(Integer jsonMessageMaxLength) {
        this.jsonMessageMaxLength = jsonMessageMaxLength;
    }

    /**
     * Applies additional quoting of double quote " and backslashes \\ when logging to console in JSON format is enabled.
     * This option is enabled by default, to enable proper logging of message when DQOps runs inside a docker container.
     * @return Encode double quotes in JSON log entries.
     */
    public boolean isEncodeMessage() {
        return encodeMessage;
    }

    /**
     * Turns on the flag to encode double quotes.
     * @param encodeMessage Encode double quotes.
     */
    public void setEncodeMessage(boolean encodeMessage) {
        this.encodeMessage = encodeMessage;
    }

    /**
     * Creates a clone of the object.
     * @return Cloned instance.
     */
    @Override
    public DqoLoggingConfigurationProperties clone() {
        try {
            DqoLoggingConfigurationProperties cloned = (DqoLoggingConfigurationProperties) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone object", ex);
        }
    }
}
