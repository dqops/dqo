/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.settings.instancename;

import com.dqops.core.configuration.DqoInstanceConfigurationProperties;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.metadata.settings.SettingsWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Service that knows the DQOps instance name and will return the instance name when requested.
 * The order of retrieving the instance name: local settings file, instance name command line parameter, environment variable, the host name of this instance.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class InstanceNameProviderImpl implements InstanceNameProvider {
    /**
     * Fallback instance name when a host name cannot be retrieved.
     */
    public static final String FALLBACK_INSTANCE_NAME = "localhost";

    private final UserHomeContextFactory userHomeContextFactory;
    private final DqoUserPrincipalProvider userPrincipalProvider;
    private final DqoInstanceConfigurationProperties dqoInstanceConfigurationProperties;
    private String instanceName;
    private final Object lock = new Object();

    @Autowired
    public InstanceNameProviderImpl(
            UserHomeContextFactory userHomeContextFactory,
            DqoUserPrincipalProvider userPrincipalProvider,
            DqoInstanceConfigurationProperties dqoInstanceConfigurationProperties) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.userPrincipalProvider = userPrincipalProvider;
        this.dqoInstanceConfigurationProperties = dqoInstanceConfigurationProperties;
    }

    /**
     * Retrieves the current DQOps instance name.
     * @return Current DQOps instance name.
     */
    @Override
    public String getInstanceName() {
        String currentInstanceName;
        synchronized (this.lock) {
            currentInstanceName = this.instanceName;
        }

        if (currentInstanceName == null) {
            invalidate();
        } else {
            return currentInstanceName;
        }

        synchronized (this.lock) {
            return this.instanceName;
        }
    }

    /**
     * Invalidates the default DQOps instance name. Should be called when the name in the local settings has changed.
     */
    @Override
    public void invalidate() {
        DqoUserPrincipal localInstanceAdminPrincipal = this.userPrincipalProvider.createLocalInstanceAdminPrincipal();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(localInstanceAdminPrincipal.getDataDomainIdentity(), true);
        SettingsWrapper settingsWrapper = userHomeContext.getUserHome().getSettings();
        if (settingsWrapper != null && settingsWrapper.getSpec() != null &&
                !Strings.isNullOrEmpty(settingsWrapper.getSpec().getInstanceName())) {

            synchronized (this.lock) {
                this.instanceName = settingsWrapper.getSpec().getInstanceName();
            }
            return;
        }

        if (!Strings.isNullOrEmpty(this.dqoInstanceConfigurationProperties.getName())) {
            synchronized (this.lock) {
                this.instanceName = this.dqoInstanceConfigurationProperties.getName();
            }
            return;
        }

        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            synchronized (this.lock) {
                if (!Strings.isNullOrEmpty(hostName)) {
                    this.instanceName = hostName;
                } else {
                    this.instanceName = FALLBACK_INSTANCE_NAME;
                }
            }
        }
        catch (UnknownHostException ex) {
            synchronized (this.lock) {
                this.instanceName = FALLBACK_INSTANCE_NAME;
            }
        }
    }
}
