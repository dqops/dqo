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

package com.dqops.cli.terminal.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.Encoder;
import com.dqops.core.configuration.DqoLoggingConfigurationProperties;
import net.logstash.logback.encoder.LogstashEncoder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Bootstrap component that configures console logging during boot.
 */
@Component
@Lazy(false)
public class ConsoleLoggingConfiguratorInitializingBean implements InitializingBean {
    private DqoLoggingConfigurationProperties loggingConfigurationProperties;

    /**
     * Dependency injection constructor.
     * @param loggingConfigurationProperties Configuration parameters with the logging settings.
     */
    @Autowired
    public ConsoleLoggingConfiguratorInitializingBean(DqoLoggingConfigurationProperties loggingConfigurationProperties) {
        this.loggingConfigurationProperties = loggingConfigurationProperties;
    }

    /**
     * Called by Spring Boot when the application starts.
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.loggingConfigurationProperties.getConsole() == null ||
                this.loggingConfigurationProperties.getConsole() == DqoConsoleLoggingMode.OFF) {
            return;
        }

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        Encoder<ILoggingEvent> encoder = null;

        switch (loggingConfigurationProperties.getConsole()) {
            case OFF:
                return;

            case PATTERN:
                PatternLayoutEncoder lineEncoder = new PatternLayoutEncoder();
                lineEncoder.setContext(loggerContext);
                lineEncoder.setPattern(this.loggingConfigurationProperties.getPattern());
                lineEncoder.start();
                encoder = lineEncoder;
                consoleAppender.setWithJansi(this.loggingConfigurationProperties.isConsoleWithJansi());
                break;

            case JSON:
                LogstashEncoder jsonEncoder = new LogstashEncoder();
                jsonEncoder.setContext(loggerContext);
                jsonEncoder.start();
                encoder = jsonEncoder;
                consoleAppender.setWithJansi(false);
                break;
        }

        consoleAppender.setEncoder(encoder);
        consoleAppender.setImmediateFlush(this.loggingConfigurationProperties.isConsoleImmediateFlush());
        consoleAppender.start();
        rootLogger.addAppender(consoleAppender);
    }
}
