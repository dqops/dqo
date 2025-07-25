/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.userhome;

import com.dqops.checks.defaults.DefaultObservabilityCheckSettingsFactory;
import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.core.configuration.DqoDockerUserhomeConfigurationProperties;
import com.dqops.core.configuration.DqoInstanceConfigurationProperties;
import com.dqops.core.configuration.DqoLoggingConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.dqops.core.filesystem.localfiles.LocalFileSystemException;
import com.dqops.core.filesystem.virtual.FileNameSanitizer;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityFactory;
import com.dqops.core.scheduler.defaults.DefaultSchedulesProvider;
import com.dqops.metadata.dashboards.DashboardsFolderListSpec;
import com.dqops.metadata.policies.column.ColumnQualityPolicySpec;
import com.dqops.metadata.policies.column.ColumnQualityPolicyWrapper;
import com.dqops.metadata.policies.table.TableQualityPolicySpec;
import com.dqops.metadata.policies.table.TableQualityPolicyWrapper;
import com.dqops.metadata.incidents.IncidentNotificationSpec;
import com.dqops.metadata.scheduling.CronSchedulesSpec;
import com.dqops.metadata.settings.LocalSettingsSpec;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import com.dqops.metadata.storage.localfiles.columndefaultpatterns.ColumnLevelDataQualityPolicyYaml;
import com.dqops.metadata.storage.localfiles.credentials.DefaultCloudCredentialFileContent;
import com.dqops.metadata.storage.localfiles.credentials.DefaultCloudCredentialFileNames;
import com.dqops.metadata.storage.localfiles.dashboards.DashboardYaml;
import com.dqops.metadata.storage.localfiles.defaultschedules.DefaultSchedulesYaml;
import com.dqops.metadata.storage.localfiles.settings.LocalSettingsYaml;
import com.dqops.metadata.storage.localfiles.defaultnotifications.DefaultNotificationsYaml;
import com.dqops.metadata.storage.localfiles.tabledefaultpatterns.TableLevelDataQualityPolicyYaml;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.utils.serialization.YamlSerializer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Component that ensures that a DQOps local user home was created and the default files were written.
 */
@Component
@Slf4j
public class LocalUserHomeCreatorImpl implements LocalUserHomeCreator {
    /**
     * The file name of a special file that is present in an empty DQOps user home inside a docker container to tell us that the user home was not mounted.
     */
    public static final String DOCKER_USER_HOME_MARKER = ".DQO_USER_HOME_NOT_MOUNTED";

    /**
     * Base file name (prefix) of log files in the .logs folder.
     */
    public static final String LOG_FILES_BASE_NAME = "dqo-logs";

    private HomeLocationFindService homeLocationFindService;
    private UserHomeContextFactory userHomeContextFactory;
    private TerminalFactory terminalFactory;
    private DqoLoggingConfigurationProperties loggingConfigurationProperties;
    private DqoUserConfigurationProperties userConfigurationProperties;
    private DqoDockerUserhomeConfigurationProperties dockerUserhomeConfigurationProperties;
    private DqoInstanceConfigurationProperties dqoInstanceConfigurationProperties;
    private YamlSerializer yamlSerializer;
    private DefaultSchedulesProvider defaultSchedulesProvider;
    private DefaultObservabilityCheckSettingsFactory defaultObservabilityCheckSettingsFactory;
    private UserDomainIdentityFactory userDomainIdentityFactory;

    /**
     * Default constructor called by the IoC container.
     * @param homeLocationFindService User home location finder.
     * @param userHomeContextFactory User home context factory.
     * @param terminalFactory Terminal factory, creates a terminal reader - used to prompt the user before the default user home is created,
     *                        and a terminal writer - used to notify the user that the default user home will not be created.
     * @param loggingConfigurationProperties Logging configuration parameters to configure logging in the user home's .logs folder.
     * @param userConfigurationProperties DQOps user home configuration parameters.
     * @param dockerUserhomeConfigurationProperties DQOps user home configuration properties related specifically to running under docker.
     * @param dqoInstanceConfigurationProperties DQOps instance configuration parameters.
     * @param yamlSerializer Yaml serializer.
     * @param defaultSchedulesProvider Default cron schedules provider.
     * @param defaultObservabilityCheckSettingsFactory Factory that creates the initial configuration of data observability checks.
     * @param userDomainIdentityFactory User data domain identity factory.
     */
    @Autowired
    public LocalUserHomeCreatorImpl(HomeLocationFindService homeLocationFindService,
                                    UserHomeContextFactory userHomeContextFactory,
                                    TerminalFactory terminalFactory,
                                    DqoLoggingConfigurationProperties loggingConfigurationProperties,
                                    DqoUserConfigurationProperties userConfigurationProperties,
                                    DqoDockerUserhomeConfigurationProperties dockerUserhomeConfigurationProperties,
                                    DqoInstanceConfigurationProperties dqoInstanceConfigurationProperties,
                                    YamlSerializer yamlSerializer,
                                    DefaultSchedulesProvider defaultSchedulesProvider,
                                    DefaultObservabilityCheckSettingsFactory defaultObservabilityCheckSettingsFactory,
                                    UserDomainIdentityFactory userDomainIdentityFactory) {
        this.homeLocationFindService = homeLocationFindService;
        this.userHomeContextFactory = userHomeContextFactory;
        this.terminalFactory = terminalFactory;
        this.loggingConfigurationProperties = loggingConfigurationProperties;
        this.userConfigurationProperties = userConfigurationProperties;
        this.dockerUserhomeConfigurationProperties = dockerUserhomeConfigurationProperties;
        this.dqoInstanceConfigurationProperties = dqoInstanceConfigurationProperties;
        this.yamlSerializer = yamlSerializer;
        this.defaultSchedulesProvider = defaultSchedulesProvider;
        this.defaultObservabilityCheckSettingsFactory = defaultObservabilityCheckSettingsFactory;
        this.userDomainIdentityFactory = userDomainIdentityFactory;
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
     * Initializes the DQOps user home at the default location.
     * @return True when the user home was initialized, false otherwise.
     */
    @Override
    public boolean initializeDefaultDqoUserHome() {
        String userHomePathString = this.homeLocationFindService.getRootUserHomePath();
        if (userHomePathString == null) {
            return false;
        }

        Path userHomePath = Path.of(userHomePathString);
        if (Files.exists(userHomePath)) {
            try (Stream<Path> paths = Files.list(userHomePath)) {
                Optional<Path> firstPath = paths
                        .filter(p -> !Objects.equals(p.getFileName().toString(), DOCKER_USER_HOME_MARKER))
                        .findFirst();
                if (firstPath.isPresent()) {
                    this.terminalFactory.getWriter().writeLine("Cannot initialize a DQOps user home folder at: " + userHomePathString +
                                                               " because the folder is not empty.");
                    Boolean initializeAnyway = this.terminalFactory.getReader().promptBoolean("Initialize a DQOps user home folder anyway?", false);
                    if (initializeAnyway == null || !initializeAnyway) {
                        return false;
                    }
                }
            }
            catch (IOException ioe) {
                this.terminalFactory.getWriter().writeLine("Cannot access the DQOps user home folder at: " + userHomePathString +
                                                           ", error: " + ioe.getMessage());
                return false;
            }
        }

        initializeDqoUserHome(userHomePathString);
        return true;
    }

    /**
     * Checks the default DQO_USER_HOME path if it points to a valid and initialized DQOps user home.
     * @return True when the path points to a valid DQOps User home, false otherwise (the user home must be initialized before first use).
     */
    @Override
    public boolean isDefaultDqoUserHomeInitialized() {
        String userHomePathString = this.homeLocationFindService.getRootUserHomePath();

        if (userHomePathString == null) {
            return false;
        }

        return isDqoUserHomeInitialized(userHomePathString);
    }

    /**
     * Checks the given path if it points to a valid and initialized DQOps user home.
     * @param userHomePathString Path to a potential DQOps user home.
     * @return True when the path points to a valid DQOps User home, false otherwise.
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

        return true;
    }

    /**
     * Initializes a DQOps user home at a given location.
     * @param userHomePathString Path to the DQOps user home.
     */
    @Override
    public void initializeDqoUserHome(String userHomePathString) {
        try {
            Path userHomePath = Path.of(userHomePathString);
            if (log.isDebugEnabled()) {
                log.debug("Initializing DQOps User Home folder at " + userHomePath.normalize().toAbsolutePath().toString());
            }

            initializeEmptyFolder(userHomePath);
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.SOURCES));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.SENSORS));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.RULES));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.CHECKS));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.SETTINGS));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.DATA));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.INDEX));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.LOGS));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.CREDENTIALS));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.DICTIONARIES));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.PATTERNS));


            Path gitIgnorePath = userHomePath.resolve(".gitignore");
            if (!Files.exists(gitIgnorePath)) {
                String gitIgnoreContent =
                        BuiltInFolderNames.CREDENTIALS + "/\n" +
                        BuiltInFolderNames.DATA + "/\n" +
                        BuiltInFolderNames.INDEX + "/\n" +
                        BuiltInFolderNames.LOGS + "/\n" +
                        BuiltInFolderNames.BIN + "/\n" +
                        BuiltInFolderNames.JARS + "/\n" +
                        ".venv/\n" +
                        SpecFileNames.LOCAL_SETTINGS_SPEC_FILE_NAME_YAML + "\n";

                Files.writeString(gitIgnorePath, gitIgnoreContent);
            }

            Path userHomeMarkerPath = userHomePath.resolve(HomeLocationFindService.DQO_USER_HOME_MARKER_NAME);
            if (!Files.exists(userHomeMarkerPath)) {
                String userHomeMarkerContent = "This is a marker file to identify a DQO_USER_HOME folder. Please check this file to Git.";

                Files.writeString(userHomeMarkerPath, userHomeMarkerContent);
            }

            Path localSettingsPath = userHomePath.resolve(SpecFileNames.LOCAL_SETTINGS_SPEC_FILE_NAME_YAML);
            if (!Files.exists(localSettingsPath)) {
                LocalSettingsYaml localSettingsYaml = new LocalSettingsYaml();
                String emptyLocalSettings = this.yamlSerializer.serialize(localSettingsYaml);
                Files.writeString(localSettingsPath, emptyLocalSettings);
            }

            Path customDashboardsPath = userHomePath.resolve(BuiltInFolderNames.SETTINGS).resolve(SpecFileNames.DASHBOARDS_SPEC_FILE_NAME_YAML);
            if (!Files.exists(customDashboardsPath)) {
                DashboardYaml dashboardYaml = new DashboardYaml();
                DashboardsFolderListSpec dashboardsFolderListSpec = dashboardYaml.getSpec();
                addDefaultDashboardFolders(dashboardsFolderListSpec);
                String emptyDashboards = this.yamlSerializer.serialize(dashboardYaml);
                Files.writeString(customDashboardsPath, emptyDashboards);
            }

            Path defaultSchedulesPath = userHomePath.resolve(BuiltInFolderNames.SETTINGS).resolve(SpecFileNames.DEFAULT_MONITORING_SCHEDULES_SPEC_FILE_NAME_YAML);
            if (!Files.exists(defaultSchedulesPath)) {
                DefaultSchedulesYaml schedulesYaml = new DefaultSchedulesYaml();
                schedulesYaml.setSpec(this.defaultSchedulesProvider.createDefaultSchedules());
                String defaultSchedules = this.yamlSerializer.serialize(schedulesYaml);
                Files.writeString(defaultSchedulesPath, defaultSchedules);
            }

            List<TableQualityPolicySpec> defaultTableQualityPolicies = this.defaultObservabilityCheckSettingsFactory.createDefaultTableQualityPolicies();
            for (TableQualityPolicySpec tableQualityPolicySpec : defaultTableQualityPolicies) {
                String policyFileName = FileNameSanitizer.encodeForFileSystem(tableQualityPolicySpec.getPolicyName()) + SpecFileNames.TABLE_DEFAULT_CHECKS_SPEC_FILE_EXT_YAML;
                Path tablePolicyFilePath = userHomePath.resolve(BuiltInFolderNames.PATTERNS)
                    .resolve(policyFileName);

                if (!Files.exists(tablePolicyFilePath)) {
                    TableLevelDataQualityPolicyYaml qualityPolicyYaml = new TableLevelDataQualityPolicyYaml();
                    qualityPolicyYaml.setSpec(tableQualityPolicySpec);
                    String serializedQualityPolicy = this.yamlSerializer.serialize(qualityPolicyYaml);
                    Files.writeString(tablePolicyFilePath, serializedQualityPolicy);
                }
            }

            List<ColumnQualityPolicySpec> defaultColumnQualityPolicies = this.defaultObservabilityCheckSettingsFactory.createDefaultColumnQualityPolicies();
            for (ColumnQualityPolicySpec columnQualityPolicySpec : defaultColumnQualityPolicies) {
                String policyFileName = FileNameSanitizer.encodeForFileSystem(columnQualityPolicySpec.getPolicyName()) + SpecFileNames.COLUMN_DEFAULT_CHECKS_SPEC_FILE_EXT_YAML;
                Path columnPolicyFilePath = userHomePath.resolve(BuiltInFolderNames.PATTERNS)
                        .resolve(policyFileName);

                if (!Files.exists(columnPolicyFilePath)) {
                    ColumnLevelDataQualityPolicyYaml qualityPolicyYaml = new ColumnLevelDataQualityPolicyYaml();
                    qualityPolicyYaml.setSpec(columnQualityPolicySpec);
                    String serializedQualityPolicy = this.yamlSerializer.serialize(qualityPolicyYaml);
                    Files.writeString(columnPolicyFilePath, serializedQualityPolicy);
                }
            }

            Path defaultNotificationWebhooksPath = userHomePath.resolve(BuiltInFolderNames.SETTINGS).resolve(SpecFileNames.DEFAULT_NOTIFICATIONS_FILE_NAME_YAML);
            if (!Files.exists(defaultNotificationWebhooksPath)) {
                DefaultNotificationsYaml webhooksYaml = new DefaultNotificationsYaml();
                String defaultWebhooks = this.yamlSerializer.serialize(webhooksYaml);
                Files.writeString(defaultNotificationWebhooksPath, defaultWebhooks);
            }

            Path rulesRequirementTxtPath = userHomePath.resolve(BuiltInFolderNames.RULES).resolve("requirements.txt");
            if (!Files.exists(rulesRequirementTxtPath)) {
                Files.writeString(rulesRequirementTxtPath, "# packages in this file are installed when DQOps starts\n");
            }

            if (this.userConfigurationProperties.isInitializeDefaultCloudCredentials()) {
                Path credentialsFolderPath = userHomePath.resolve(BuiltInFolderNames.CREDENTIALS);
                Path defaultGcpCredentialsPath = credentialsFolderPath
                        .resolve(DefaultCloudCredentialFileNames.GCP_APPLICATION_DEFAULT_CREDENTIALS_JSON_NAME);
                if (!Files.exists(defaultGcpCredentialsPath)) {
                    Files.writeString(defaultGcpCredentialsPath, DefaultCloudCredentialFileContent.GCP_APPLICATION_DEFAULT_CREDENTIALS_JSON_INITIAL_CONTENT);
                }

                Path defaultAwsCredentialsPath = credentialsFolderPath
                        .resolve(DefaultCloudCredentialFileNames.AWS_DEFAULT_CREDENTIALS_NAME);
                if (!Files.exists(defaultAwsCredentialsPath)) {
                    Files.writeString(defaultAwsCredentialsPath, DefaultCloudCredentialFileContent.AWS_DEFAULT_CREDENTIALS_INITIAL_CONTENT);
                }

                Path defaultAwsConfigPath = credentialsFolderPath
                        .resolve(DefaultCloudCredentialFileNames.AWS_DEFAULT_CONFIG_NAME);
                if (!Files.exists(defaultAwsConfigPath)) {
                    Files.writeString(defaultAwsConfigPath, DefaultCloudCredentialFileContent.AWS_DEFAULT_CONFIG_INITIAL_CONTENT);
                }

                Path defaultAzureCredentialsPath = credentialsFolderPath
                        .resolve(DefaultCloudCredentialFileNames.AZURE_DEFAULT_CREDENTIALS_NAME);
                if (!Files.exists(defaultAzureCredentialsPath)) {
                    Files.writeString(defaultAzureCredentialsPath, DefaultCloudCredentialFileContent.AZURE_DEFAULT_CREDENTIALS_INITIAL_CONTENT);
                }
            }
        }
        catch (Exception ex) {
            throw new LocalFileSystemException("Cannot initialize a DQOps User home at " + userHomePathString, ex);
        }
    }

    /**
     * Adds default dashboard folders.
     * @param dashboardsFolderListSpec Target object to add default dashboard folders.
     */
    private void addDefaultDashboardFolders(DashboardsFolderListSpec dashboardsFolderListSpec) {
        dashboardsFolderListSpec.getOrCreateChildFolder("Profiling");
        dashboardsFolderListSpec.getOrCreateChildFolder("Monitoring");
        dashboardsFolderListSpec.getOrCreateChildFolder("Partitions");
        dashboardsFolderListSpec.getOrCreateChildFolder("DQOps usage");
        dashboardsFolderListSpec.getOrCreateChildFolder("Aggregated results for all check types");
    }

    /**
     * Checks for the existence of <code>.DQO_USER_HOME_NOT_MOUNTED</code> file in DQO_USER_HOME.
     * @param userHomePath DQOps User Home path.
     * @return True if the application is run inside a docker container and DQO_USER_HOME hasn't been mounted to an external volume.
     */
    protected boolean isUninitializedInUnmountedDockerVolume(Path userHomePath) {
        try (Stream<Path> filesStream = Files.walk(userHomePath, 1)) {
            return filesStream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .anyMatch(fileName -> fileName.equals(DOCKER_USER_HOME_MARKER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ensures that the DQOps User home is initialized at the default location. Prompts the user before creating the user home to confirm.
     * NOTE: this method may forcibly stop the program execution if the user did not agree to create the DQOps User home.
     * @param isHeadless Is headless mode - when true, then the dqo user home is created silently, when false (interactive execution) then the user is asked to confirm.
     * @return True when a new user home was initialized. False when the existing user home was used.
     */
    @Override
    public boolean ensureDefaultUserHomeIsInitialized(boolean isHeadless) {
        String userHomePathString = this.homeLocationFindService.getRootUserHomePath();
        if (userHomePathString == null) {
            return false; // the dqo user home is not required for some reason (configurable)
        }

        Path userHomePath = Paths.get(userHomePathString);
        if (this.isDefaultDqoUserHomeInitialized()) {
            if (log.isDebugEnabled()) {
                log.debug("Using a DQOps User Home folder at " + userHomePath.normalize().toAbsolutePath().toString());
            }
            activateFileLoggingInUserHome();
            upgradeUserHomeConfigurationWhenMissing(this.userConfigurationProperties.getDefaultDataDomain());
            return false;
        }

        if (this.isUninitializedInUnmountedDockerVolume(userHomePath) && !this.dockerUserhomeConfigurationProperties.isAllowUnmounted()) {
            TerminalWriter terminalWriter = this.terminalFactory.getWriter();
            terminalWriter.writeLine("DQOps User Home volume is not mounted to the docker's folder " + userHomePathString + ".");
            terminalWriter.writeLine("In order to mount a volume, execute docker run with parameter \"-v\":");
            terminalWriter.writeLine("\tdocker run -it -v $DQO_USER_HOME:" + userHomePathString + " -p 8888:8888 dqops/dqo");
            terminalWriter.writeLine("To run DQOps in docker using a User Home folder inside the docker image (not advised),"
                    + " do one of the following:");
            terminalWriter.writeLine("\t- Start DQOps with a parameter --dqo.docker.user-home.allow-unmounted=true");
            terminalWriter.writeLine("\t- or set the environment variable DQO_DOCKER_USER_HOME_ALLOW_UNMOUNTED=true");
            terminalWriter.writeLine("DQOps will quit.");
            log.error("DQOps User Home folder cannot be initialized at " + userHomePath.normalize().toAbsolutePath().toString());
            System.exit(101);
            return false;
        }

        if (isHeadless || this.userConfigurationProperties.isInitializeUserHome()) {
            if (!this.initializeDefaultDqoUserHome()) {
                System.exit(101);
            }
            activateFileLoggingInUserHome();
            return true;
        }
        else {
            if (this.terminalFactory.getReader().promptBoolean("Initialize a DQOps user home at " + userHomePathString, true)) {
                if (!this.initializeDefaultDqoUserHome()) {
                    System.exit(101);
                }
                activateFileLoggingInUserHome();
                return true;
            }

            this.terminalFactory.getWriter().writeLine("DQOps user home will not be created, exiting.");
            log.error("DQOps User Home folder initialization cancelled, cannot create the DQOps User Home at " + userHomePath.normalize().toAbsolutePath().toString());
            System.exit(100);
            return true; // will not reach here
        }
    }

    /**
     * Verifies if the user home configuration (and the local settings) are valid and are not missing configuration.
     * Applies missing default observability check configuration when it is not configured.
     * @param dataDomain Data Domain name.
     */
    public void upgradeUserHomeConfigurationWhenMissing(String dataDomain) {
        UserDomainIdentity rootDataDomainAdminIdentity = this.userDomainIdentityFactory.createDataDomainAdminIdentityForCloudDomain(dataDomain);
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(rootDataDomainAdminIdentity, false);
        UserHome userHome = userHomeContext.getUserHome();
        LocalSettingsSpec localSettingsSpec = userHome.getSettings().getSpec();

        if (localSettingsSpec == null) {
            localSettingsSpec = new LocalSettingsSpec();
            userHome.getSettings().setSpec(localSettingsSpec);
        }

        if (userHome.getDefaultSchedules() != null && userHome.getDefaultSchedules().getSpec() == null) {
            CronSchedulesSpec defaultMonitoringSchedules = this.defaultSchedulesProvider.createDefaultSchedules();
            userHome.getDefaultSchedules().setSpec(defaultMonitoringSchedules);
        }

        if (userHome.getDefaultIncidentNotifications() != null && userHome.getDefaultIncidentNotifications().getSpec() == null) {
            userHome.getDefaultIncidentNotifications().setSpec(new IncidentNotificationSpec());
        }

        if (userHome.getDashboards() != null && userHome.getDashboards().getSpec() == null) {
            DashboardsFolderListSpec dashboardsFolderListSpec = new DashboardsFolderListSpec();
            addDefaultDashboardFolders(dashboardsFolderListSpec);
            userHome.getDashboards().setSpec(dashboardsFolderListSpec);
        }

        if (localSettingsSpec.getInstanceSignatureKey() == null && this.dqoInstanceConfigurationProperties.getSignatureKey() == null) {
            SecureRandom secureRandom = new SecureRandom();
            byte[] instanceKeyBytes = new byte[32];
            secureRandom.nextBytes(instanceKeyBytes);

            String encodedNewKey = Base64.getEncoder().encodeToString(instanceKeyBytes);
            localSettingsSpec.setInstanceSignatureKey(encodedNewKey);
        }

        Path userHomePath = userHomeContext.getHomeRoot().getPhysicalAbsolutePath();
        if (userHomePath != null) {
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.SOURCES));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.SENSORS));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.RULES));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.CHECKS));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.SETTINGS));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.DATA));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.INDEX));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.LOGS));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.CREDENTIALS));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.DICTIONARIES));
            initializeEmptyFolder(userHomePath.resolve(BuiltInFolderNames.PATTERNS));

            Path rulesRequirementTxtPath = userHomePath.resolve(BuiltInFolderNames.RULES)
                    .resolve("requirements.txt");
            if (!Files.exists(rulesRequirementTxtPath)) {
                try {
                    Files.writeString(rulesRequirementTxtPath, "# packages in this file are installed when DQOps starts\n");
                }
                catch (IOException ioe) {
                    log.warn("Cannot write a requirements.txt file, error: " + ioe.getMessage(), ioe);
                }
            }
        }

        List<TableQualityPolicySpec> defaultTableQualityPolicies = this.defaultObservabilityCheckSettingsFactory.createDefaultTableQualityPolicies();
        if (defaultTableQualityPolicies.stream().noneMatch(
                policy -> userHome.getTableQualityPolicies().getByObjectName(policy.getPolicyName(), true) != null)) {

            for (TableQualityPolicySpec expectedPolicySpec : defaultTableQualityPolicies) {
                String policyName = expectedPolicySpec.getPolicyName();
                TableQualityPolicyWrapper newPolicyWrapper = userHome.getTableQualityPolicies().createAndAddNew(policyName);
                newPolicyWrapper.setSpec(expectedPolicySpec);
            }

            // migrate the old "default" policy file by deleting it
            TableQualityPolicyWrapper oldDefaultPolicy = userHome.getTableQualityPolicies()
                .getByObjectName(SpecFileNames.DEFAULT_CHECKS_PATTERN_NAME, true);
            if (oldDefaultPolicy != null) {
                oldDefaultPolicy.markForDeletion();
            }
        }

        List<ColumnQualityPolicySpec> defaultColumnQualityPolicies = this.defaultObservabilityCheckSettingsFactory.createDefaultColumnQualityPolicies();
        if (defaultColumnQualityPolicies.stream().noneMatch(
                policy -> userHome.getColumnQualityPolicies().getByObjectName(policy.getPolicyName(), true) != null)) {

            for (ColumnQualityPolicySpec expectedPolicySpec : defaultColumnQualityPolicies) {
                String policyName = expectedPolicySpec.getPolicyName();
                ColumnQualityPolicyWrapper newPolicyWrapper = userHome.getColumnQualityPolicies().createAndAddNew(policyName);
                newPolicyWrapper.setSpec(expectedPolicySpec);
            }

            // migrate the old "default" policy file by deleting it
            ColumnQualityPolicyWrapper oldDefaultPolicy = userHome.getColumnQualityPolicies()
                    .getByObjectName(SpecFileNames.DEFAULT_CHECKS_PATTERN_NAME, true);
            if (oldDefaultPolicy != null) {
                oldDefaultPolicy.markForDeletion();
            }
        }

        userHomeContext.flush();
        userHome.warmUpConnections();
        userHome.warmUpTables();
    }

    /**
     * Activates logging inside the user home folder. Adds a rolling file logger that will write logs in this folder.
     */
    public void activateFileLoggingInUserHome() {
        if (!this.loggingConfigurationProperties.isEnableUserHomeLogging()) {
            return;
        }

        String userHomePath = this.homeLocationFindService.getRootUserHomePath();
        Path logsFolderPath = Path.of(userHomePath).resolve(BuiltInFolderNames.LOGS);
        if (!Files.exists(logsFolderPath)) {
            initializeEmptyFolder(logsFolderPath);
        }

        String currentLogFileName = logsFolderPath.resolve(LOG_FILES_BASE_NAME + ".log").toAbsolutePath().toString();
        String historicLogFileName = logsFolderPath.resolve(LOG_FILES_BASE_NAME + "-%d{yyyy-MM-dd_HH}.log").toAbsolutePath().toString();

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
