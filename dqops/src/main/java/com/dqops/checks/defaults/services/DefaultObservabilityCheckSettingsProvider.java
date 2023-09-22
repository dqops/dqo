package com.dqops.checks.defaults.services;

import com.dqops.checks.defaults.DefaultObservabilityCheckSettingsSpec;
import com.dqops.metadata.userhome.UserHome;

/**
 * Provider that provides the default configuration of the data observability (the default checks) object.
 */
public interface DefaultObservabilityCheckSettingsProvider {

    /**
     * User home used for providing default data observability checks. When specification does not exist, a new empty one is created.
     * @param userHome A user home
     * @return The default data observability checks.
     */
    DefaultObservabilityCheckSettingsSpec provideFromUserHomeContext(UserHome userHome);

}