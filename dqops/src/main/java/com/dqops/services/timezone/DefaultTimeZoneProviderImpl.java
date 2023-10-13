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
package com.dqops.services.timezone;

import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.settings.SettingsSpec;
import com.dqops.metadata.settings.SettingsWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.utils.datetime.TimeZoneUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * Service that returns the default time zone configured on the DQOps instance.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DefaultTimeZoneProviderImpl implements DefaultTimeZoneProvider {
    private DqoConfigurationProperties dqoConfigurationProperties;
    private UserHomeContextFactory userHomeContextFactory;
    private SecretValueProvider secretValueProvider;
    private ZoneId cachedTimeZone;

    /**
     * Creates a default time zone provider.
     * @param dqoConfigurationProperties DQOps configuration properties with the system provided time zone. The configuration object has a default value that is the local computer's time zone.
     * @param userHomeContextFactory User home context factory to read the time zone that was customized by the user.
     * @param secretValueProvider Secret value provider that will extract secrets in the user local settings (to support using environment variables or secret managers).
     */
    @Autowired
    public DefaultTimeZoneProviderImpl(DqoConfigurationProperties dqoConfigurationProperties,
                                       UserHomeContextFactory userHomeContextFactory,
                                       SecretValueProvider secretValueProvider) {
        this.dqoConfigurationProperties = dqoConfigurationProperties;
        this.userHomeContextFactory = userHomeContextFactory;
        this.secretValueProvider = secretValueProvider;
    }

    /**
     * Retrieves the default time zone. The time zone could be configured in the user local settings. If it is not customized, then the default time zone
     * in the configuration file is taken. If the time zone was not customized using environment variables then the default time zone is the time zone of the local computer.
     * @return Default Java time zone.
     */
    @Override
    public ZoneId getDefaultTimeZoneId() {
        synchronized (this) {
            if (this.cachedTimeZone != null) {
                return this.cachedTimeZone;
            }
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory != null ? this.userHomeContextFactory.openLocalUserHome() : null;
        ZoneId defaultTimeZoneId = getDefaultTimeZoneId(userHomeContext);

        synchronized (this) {
            this.cachedTimeZone = defaultTimeZoneId;
            TimeZone.setDefault(TimeZone.getTimeZone(defaultTimeZoneId));
            return defaultTimeZoneId;
        }
    }

    /**
     * Retrieves the default time zone. The time zone could be configured in the user local settings. If it is not customized, then the default time zone
     * in the configuration file is taken. If the time zone was not customized using environment variables then the default time zone is the time zone of the local computer.
     * @param userHomeContext DQOps User home context with parameters.
     * @return Default Java time zone.
     */
    @Override
    public ZoneId getDefaultTimeZoneId(UserHomeContext userHomeContext) {
        String timeZone = this.getTimeZoneFromUserLocalSettings(userHomeContext);

        if (timeZone == null) {
            // we don't have a user configured time zone, we need to take the time zone from configuration
            timeZone = this.dqoConfigurationProperties.getDefaultTimeZone();
        }

        try {
            ZoneId zoneId = TimeZoneUtility.parseZoneId(timeZone);
            return zoneId;
        }
        catch (Exception ex) {
            // ignore exceptions here, we will use UTC as a fallback
        }

        return ZoneId.systemDefault();
    }

    /**
     * Retrieves the time zone name from the local settings file in the user home, that is a user configured time zone.
     * @param userHomeContext DQOps User home context with parameters.
     * @return The time zone name of a user configured time zone or null when the time zone is not configured.
     */
    public String getTimeZoneFromUserLocalSettings(UserHomeContext userHomeContext) {
        if (this.userHomeContextFactory == null) {
            return null;
        }

        SettingsWrapper settingsWrapper = userHomeContext.getUserHome().getSettings();
        SettingsSpec settingsSpec = settingsWrapper.getSpec();
        if (settingsSpec == null) {
            return null;
        }

        SecretValueLookupContext lookupContext = new SecretValueLookupContext(userHomeContext.getUserHome());
        return this.secretValueProvider.expandValue(settingsSpec.getTimeZone(), lookupContext);
    }

    /**
     * Invalidates the cached default time zone. This method is called when the use changes the default time zone using the CLI or using any other configuration option.
     */
    @Override
    public void invalidate() {
        synchronized (this) {
            this.cachedTimeZone = null;
        }

        getDefaultTimeZoneId();
    }
}
