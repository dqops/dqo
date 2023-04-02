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
package ai.dqo.metadata.storage.localfiles.userhome;

import ai.dqo.cli.terminal.TerminalFactory;
import ai.dqo.core.configuration.DqoLoggingConfigurationProperties;
import ai.dqo.core.configuration.DqoUserConfigurationProperties;
import ai.dqo.core.filesystem.BuiltInFolderNames;
import ai.dqo.core.filesystem.localfiles.HomeLocationFindService;
import ai.dqo.core.filesystem.localfiles.LocalFileSystemException;
import ai.dqo.metadata.storage.localfiles.SpecFileNames;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Component that ensures that a DQO local user home was created and the default files were written.
 */
@Component
public class LocalUserHomeCreatorImpl implements LocalUserHomeCreator {
    private HomeLocationFindService homeLocationFindService;
    private TerminalFactory terminalFactory;
    private DqoLoggingConfigurationProperties loggingConfigurationProperties;
    private DqoUserConfigurationProperties dqoUserConfigurationProperties;

    /**
     * Default constructor called by the IoC container.
     * @param homeLocationFindService User home location finder.
     * @param terminalFactory Terminal factory, creates a terminal reader - used to prompt the user before the default user home is created,
     *                        and a terminal writer - used to notify the user that the default user home will not be created.
     * @param loggingConfigurationProperties Logging configuration parameters to configure logging in the user home's .logs folder.
     * @param dqoUserConfigurationProperties DQO user home configuration parameters.
     */
    @Autowired
    public LocalUserHomeCreatorImpl(HomeLocationFindService homeLocationFindService,
                                    TerminalFactory terminalFactory,
                                    DqoLoggingConfigurationProperties loggingConfigurationProperties,
                                    DqoUserConfigurationProperties dqoUserConfigurationProperties) {
        this.homeLocationFindService = homeLocationFindService;
        this.terminalFactory = terminalFactory;
        this.loggingConfigurationProperties = loggingConfigurationProperties;
        this.dqoUserConfigurationProperties = dqoUserConfigurationProperties;
    }

    /**
     * Tries to initialize (create) a folder.
     *
     * @param path Path to the folder.
     */
    public void initializeEmptyFolder(Path path) {
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception ex) {
                throw new LocalFileSystemException("Cannot create a folder " + path, ex);
            }
        } else {
            if (!Files.isDirectory(path)) {
                throw new BeanInitializationException("Local home cannot be initialized because " + path + " already exists and it is a file, not a folder.");
            }
        }
    }

    /**
     * Initializes the DQO user home at the default location.
     */
    @Override
    public void initializeDefaultDqoUserHome() {
        String userHomePathString = this.homeLocationFindService.getUserHomePath();

        if (userHomePathString == null) {
            return;
        }

        initializeDqoUserHome(userHomePathString);
    }

    /**
     * Checks the default DQO_USER_HOME path if it points to a valid and initialized DQO user home.
     * @return True when the path points to a valid DQO User home, false otherwise (the user home must be initialized before first use).
     */
    @Override
    public boolean isDefaultDqoUserHomeInitialized() {
        String userHomePathString = this.homeLocationFindService.getUserHomePath();

        if (userHomePathString == null) {
            return false;
        }

        return isDqoUserHomeInitialized(userHomePathString);
    }

    /**
     * Checks the given path if it points to a valid and initialized DQO user home.
     * @param userHomePathString Path to a potential DQO user home.
     * @return True when the path points to a valid DQO User home, false otherwise.
     */
    @Override
    public boolean isDqoUserHomeInitialized(String userHomePathString) {
        Path userHomePath = Path.of(userHomePathString);
        if (!Files.exists(userHomePath)) {
            return false;
        }

        if (!Files.exists(userHomePath.resolve(".gitignore"))) {
            return false;
        }

        if (!Files.exists(userHomePath.resolve(HomeLocationFindService.DQO_USER_HOME_MARKER_NAME))) {
            return false;
        }

        // we are not checking the existence of other folders (like rules, sensors, sources) because they
        // may have been empty when teh code was checked into Git and the user just checked the configuration out
        // those folders will be created on first use anyway, we need to ensure that the "marker" is there

        return true;
    }

    /**
     * Initializes a DQO user home at a given location.
     * @param userHomePathString Path to the DQO user home.
     */
    @Override
    public void initializeDqoUserHome(String userHomePathString) {
        try {
            Path userHomePath = Path.of(userHomePathString);
            initializeEmptyFolder(userHomePath);
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.SOURCES));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.SENSORS));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.RULES));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.CHECKS));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.DATA));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.INDEX));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.LOGS));
//        initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.CREDENTIALS));

            Path gitIgnorePath = userHomePath.resolve(".gitignore");
            if (!Files.exists(gitIgnorePath)) {
                String gitIgnoreContent =
                        BuiltInFolderNames.CREDENTIALS + "/\n" +
                                BuiltInFolderNames.DATA + "/\n" +
                                BuiltInFolderNames.INDEX + "/\n" +
                                BuiltInFolderNames.LOGS + "/\n" +
                                ".venv/\n" +
                                SpecFileNames.SETTINGS_SPEC_FILE_NAME_YAML + "\n";

                Files.writeString(gitIgnorePath, gitIgnoreContent);
            }

            Path userHomeMarkerPath = userHomePath.resolve(HomeLocationFindService.DQO_USER_HOME_MARKER_NAME);
            if (!Files.exists(userHomeMarkerPath)) {
                String userHomeMarkerContent = "This is a marker file to identify a DQO_USER_HOME folder. Please check this file to Git.";

                Files.writeString(userHomeMarkerPath, userHomeMarkerContent);
            }
        }
        catch (Exception ex) {
            throw new LocalFileSystemException("Cannot initialize a DQO User home at " + userHomePathString, ex);
        }
    }

    /**
     * Ensures that the DQO User home is initialized at the default location. Prompts the user before creating the user home to confirm.
     * NOTE: this method may forcibly stop the program execution if the user did not agree to create the DQO User home.
     * @param isHeadless Is headless mode - when true, then the dqo user home is created silently, when false (interactive execution) then the user is asked to confirm.
     */
    @Override
    public void ensureDefaultUserHomeIsInitialized(boolean isHeadless) {
        String userHomePathString = this.homeLocationFindService.getUserHomePath();
        if (userHomePathString == null) {
            return; // the dqo user home is not required for some reason (configurable)
        }

        if (this.isDefaultDqoUserHomeInitialized()) {
            activateFileLoggingInUserHome();
            return;
        }

        if (isHeadless || this.dqoUserConfigurationProperties.isInitializeUserHome()) {
            this.initializeDefaultDqoUserHome();
            activateFileLoggingInUserHome();
        }
        else {
            if (this.terminalFactory.getReader().promptBoolean("Initialize a DQO user home at " + userHomePathString, true)) {
                this.initializeDefaultDqoUserHome();
                activateFileLoggingInUserHome();
                return;
            }

            this.terminalFactory.getWriter().writeLine("DQO user home will not be created, exiting.");
            System.exit(100);
        }
    }

    /**
     * Activates logging inside the user home folder. Adds a rolling file logger that will write logs in this folder.
     */
    public void activateFileLoggingInUserHome() {
        if (!this.loggingConfigurationProperties.isEnableUserHomeLogging()) {
            return;
        }

        String userHomePath = this.homeLocationFindService.getUserHomePath();
        Path logsFolderPath = Path.of(userHomePath).resolve(BuiltInFolderNames.LOGS);
        if (!Files.exists(logsFolderPath)) {
            initializeEmptyFolder(logsFolderPath);
        }

        final String logFileNameBase = "dqo-logs";
        String currentLogFileName = logsFolderPath.resolve(logFileNameBase + ".log").toAbsolutePath().toString();
        String historicLogFileName = logsFolderPath.resolve(logFileNameBase + "-%d{yyyy-MM-dd_HH}.log").toAbsolutePath().toString();

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        PatternLayoutEncoder layoutEncoder = new PatternLayoutEncoder();
        layoutEncoder.setContext(loggerContext);
        layoutEncoder.setPattern(this.loggingConfigurationProperties.getPattern());
        layoutEncoder.start();

        RollingFileAppender logFileAppender = new RollingFileAppender();
        logFileAppender.setContext(loggerContext);
        logFileAppender.setName("dqo-file-log");
        logFileAppender.setEncoder(layoutEncoder);
        logFileAppender.setAppend(true);
        logFileAppender.setFile(currentLogFileName);

        TimeBasedRollingPolicy logFilePolicy = new TimeBasedRollingPolicy();
        logFilePolicy.setContext(loggerContext);
        logFilePolicy.setParent(logFileAppender);
        logFilePolicy.setFileNamePattern(historicLogFileName);
        if (this.loggingConfigurationProperties.getMaxHistory() != null) {
            logFilePolicy.setMaxHistory(this.loggingConfigurationProperties.getMaxHistory());
        }
        if (this.loggingConfigurationProperties.getTotalSizeCap() != null) {
            logFilePolicy.setTotalSizeCap(FileSize.valueOf(this.loggingConfigurationProperties.getTotalSizeCap()));
        }
        logFilePolicy.start();

        logFileAppender.setRollingPolicy(logFilePolicy);
        logFileAppender.start();

        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(logFileAppender);
    }
}
