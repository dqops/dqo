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
package com.dqops.cli;

import com.dqops.cli.commands.cloud.impl.CloudLoginService;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.connectors.jdbc.JdbcTypeColumnMapping;
import com.dqops.core.configuration.DqoCloudConfigurationProperties;
import com.dqops.core.configuration.DqoSchedulerConfigurationProperties;
import com.dqops.core.configuration.RootConfigurationProperties;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.dqops.core.jobqueue.DqoJobQueue;
import com.dqops.core.jobqueue.ParentDqoJobQueue;
import com.dqops.core.jobqueue.monitoring.DqoJobQueueMonitoringService;
import com.dqops.core.scheduler.JobSchedulerService;
import com.dqops.core.synchronization.status.FileSynchronizationChangeDetectionService;
import com.dqops.data.storage.TablesawParquetSupportFix;
import com.dqops.metadata.storage.localfiles.userhome.LocalUserHomeCreator;
import com.dqops.rest.server.LocalUrlAddresses;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.utils.python.PythonExecutionException;
import com.dqops.utils.python.PythonVirtualEnv;
import com.dqops.utils.python.PythonVirtualEnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * Initializes the local instance, configures a DQO user home, logs the user to the cloud dqo instance.
 * Component called by the CLI command runner just before the first command is executed.
 */
@Component
public class CliInitializerImpl implements CliInitializer {
    private LocalUserHomeCreator localUserHomeCreator;
    private DqoCloudApiKeyProvider dqoCloudApiKeyProvider;
    private TerminalReader terminalReader;
    private CloudLoginService cloudLoginService;
    private DqoSchedulerConfigurationProperties dqoSchedulerConfigurationProperties;
    private DqoCloudConfigurationProperties dqoCloudConfigurationProperties;
    private JobSchedulerService jobSchedulerService;
    private DqoJobQueueMonitoringService jobQueueMonitoringService;
    private DqoJobQueue dqoJobQueue;
    private ParentDqoJobQueue parentDqoJobQueue;
    private FileSynchronizationChangeDetectionService fileSynchronizationChangeDetectionService;
    private DefaultTimeZoneProvider defaultTimeZoneProvider;
    private TerminalWriter terminalWriter;
    private LocalUrlAddresses localUrlAddresses;
    private PythonVirtualEnvService pythonVirtualEnvService;
    private RootConfigurationProperties rootConfigurationProperties;

    /**
     * Called by the dependency injection container to provide dependencies.
     * @param localUserHomeCreator Local user home creator - used to create the user home.
     * @param dqoCloudApiKeyProvider Cloud api key provider - to detect if the api key was given.
     * @param terminalReader Terminal reader - used to ask the user to log in.
     * @param cloudLoginService Cloud login service - used to log the user to dqo cloud.
     * @param dqoSchedulerConfigurationProperties Scheduler configuration parameters, decide if the scheduler should be started instantly.
     * @param dqoCloudConfigurationProperties DQO Cloud configuration parameters.
     * @param jobSchedulerService Job scheduler service, may be started when the dqo.scheduler.start property is true.
     * @param jobQueueMonitoringService DQO job queue monitoring service that tracks the statuses of jobs.
     * @param dqoJobQueue Job queue service, used to start the job queue when the application starts.
     * @param parentDqoJobQueue Job queue service that queues and executes only parent jobs, must be started when the application starts.
     * @param fileSynchronizationChangeDetectionService File synchronization changes detection service, compares the dates, sizes and existence of all files that could be synchronized to DQO Cloud with the index of previously synchronized files.
     * @param defaultTimeZoneProvider Default time zone provider, used to configure the default time zone.
     * @param terminalWriter Terminal writer - used for displaying additional handy information during the init process.
     * @param localUrlAddresses Local URL addresses - used to store centralized information regarding URLs.
     * @param pythonVirtualEnvService Python virtual environment service. Used to initialize a private python venv.
     * @param rootConfigurationProperties Root configuration parameters that are mapped to parameters not configured without any prefix, such as --silent.
     */
    @Autowired
    public CliInitializerImpl(LocalUserHomeCreator localUserHomeCreator,
                              DqoCloudApiKeyProvider dqoCloudApiKeyProvider,
                              TerminalReader terminalReader,
                              CloudLoginService cloudLoginService,
                              DqoSchedulerConfigurationProperties dqoSchedulerConfigurationProperties,
                              DqoCloudConfigurationProperties dqoCloudConfigurationProperties,
                              JobSchedulerService jobSchedulerService,
                              DqoJobQueueMonitoringService jobQueueMonitoringService,
                              DqoJobQueue dqoJobQueue,
                              ParentDqoJobQueue parentDqoJobQueue,
                              FileSynchronizationChangeDetectionService fileSynchronizationChangeDetectionService,
                              DefaultTimeZoneProvider defaultTimeZoneProvider,
                              TerminalWriter terminalWriter,
                              LocalUrlAddresses localUrlAddresses,
                              PythonVirtualEnvService pythonVirtualEnvService,
                              RootConfigurationProperties rootConfigurationProperties) {
        this.localUserHomeCreator = localUserHomeCreator;
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
        this.terminalReader = terminalReader;
        this.cloudLoginService = cloudLoginService;
        this.dqoSchedulerConfigurationProperties = dqoSchedulerConfigurationProperties;
        this.dqoCloudConfigurationProperties = dqoCloudConfigurationProperties;
        this.jobSchedulerService = jobSchedulerService;
        this.jobQueueMonitoringService = jobQueueMonitoringService;
        this.dqoJobQueue = dqoJobQueue;
        this.parentDqoJobQueue = parentDqoJobQueue;
        this.fileSynchronizationChangeDetectionService = fileSynchronizationChangeDetectionService;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
        this.terminalWriter = terminalWriter;
        this.localUrlAddresses = localUrlAddresses;
        this.pythonVirtualEnvService = pythonVirtualEnvService;
        this.rootConfigurationProperties = rootConfigurationProperties;
    }

    /**
     * Attempts to log in to DQOps Cloud. Retrieves the ApiKey for future use.
     * @param headless Is application running in headless mode.
     */
    protected void tryLoginToDqoCloud(boolean headless) {
        try {
            DqoCloudApiKey apiKey = this.dqoCloudApiKeyProvider.getApiKey();
            if (apiKey != null) {
                return; // api key is provided somehow (by an environment variable or in the local settings)
            }
        } catch (Exception ex) {
            System.err.println("Cannot retrieve the API Key, the key is probably invalid: " + ex.getMessage());
//            ex.printStackTrace(System.err);
        }

        if (headless) {
            return; // we don't have the api key, and we can't ask for it, some commands will simply fail
        }

        if (this.dqoCloudConfigurationProperties.isStartWithoutApiKey()) {
            return;
        }

        if (!this.terminalReader.promptBoolean("Log in to DQOps Cloud?", true)) {
            return;
        }

        this.cloudLoginService.logInToDqoCloud();
    }

    /**
     * Shows the initial information with the links to the UI.
     */
    protected void displayUiLinks() {
        String dqoUiHome = this.localUrlAddresses.getDqoUiUrl();
        String swaggerUi = this.localUrlAddresses.getSwaggerUiUrl();
        this.terminalWriter.writeLine("Press CTRL and click the link to open it in the browser:");
        this.terminalWriter.writeUrl(dqoUiHome, "- DQOps User Interface Console (" + dqoUiHome + ")\n");
        this.terminalWriter.writeUrl(swaggerUi, "- DQOps API Reference (" + swaggerUi + ")\n");
    }

    /**
     * Initializes the local folder, creates a dqo user home, configures some properties.
     * @param args Command line parameters used to start dqo. Initializer will look for the --headless parameter to perform silent initialization.
     */
    @Override
    public void initializeApp(String[] args) {
        TablesawParquetSupportFix.ensureInitialized();
        JdbcTypeColumnMapping.ensureInitializedJdbc();

        boolean isHeadless = Arrays.stream(args).anyMatch(arg -> Objects.equals(arg, "--headless") || Objects.equals(arg, "-hl"));
        this.localUserHomeCreator.ensureDefaultUserHomeIsInitialized(isHeadless);
        this.defaultTimeZoneProvider.invalidate();

        if (!this.pythonVirtualEnvService.isVirtualEnvInitialized()) {
            this.terminalWriter.writeLine("Please wait, checking Python installation. This may take 30 seconds for the first time if DQOps needs to initialize a Python virtual environment in DQO home directory.");
            PythonVirtualEnv virtualEnv = this.pythonVirtualEnvService.getVirtualEnv();
            if (virtualEnv == null) {
                throw new PythonExecutionException("Cannot find any python executable instance. Make sure that Python is installed and could be found on the path (defined in PATH) or the --dqo.python.interpreter parameter points to a python executable.");
            }
            if (virtualEnv.getVirtualEnvPath() != null) {
                this.terminalWriter.writeLine("Python virtual environment was configured at " + virtualEnv.getVirtualEnvPath().toAbsolutePath());
            }
        }

        try {
            this.tryLoginToDqoCloud(isHeadless);
        }
        finally {
            this.jobQueueMonitoringService.start();
            this.fileSynchronizationChangeDetectionService.detectNotSynchronizedChangesInBackground();
            this.dqoJobQueue.start();
            this.parentDqoJobQueue.start();

            if (this.dqoSchedulerConfigurationProperties.getStart() != null &&
                    this.dqoSchedulerConfigurationProperties.getStart()) {
                this.jobSchedulerService.start(
                        this.dqoSchedulerConfigurationProperties.getSynchronizationMode(),
                        this.dqoSchedulerConfigurationProperties.getCheckRunMode());
                this.jobSchedulerService.triggerMetadataSynchronization();
            }

            if (CliApplication.isRequiredWebServer() && !this.rootConfigurationProperties.isSilent()) {
                this.displayUiLinks();
            }
        }
    }
}
