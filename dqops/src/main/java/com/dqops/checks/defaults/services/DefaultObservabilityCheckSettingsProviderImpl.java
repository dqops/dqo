package com.dqops.checks.defaults.services;

import com.dqops.checks.defaults.DefaultObservabilityCheckSettingsSpec;
import com.dqops.metadata.userhome.UserHome;

/**
 * Provider that provides the default configuration of the data observability (the default checks) object.
 */
public class DefaultObservabilityCheckSettingsProviderImpl implements DefaultObservabilityCheckSettingsProvider {

    /**
     * User home used for providing default data observability checks. When specification does not exist, a new empty one is created.
     * @param userHome A user home
     * @return The default data observability checks.
     */
    public DefaultObservabilityCheckSettingsSpec provideFromUserHomeContext(UserHome userHome){
        DefaultObservabilityCheckSettingsSpec defaultDataObservabilityChecks = null;

        if (userHome.getDefaultObservabilityChecks() == null
                || userHome.getDefaultObservabilityChecks().getSpec() == null) {
            defaultDataObservabilityChecks = new DefaultObservabilityCheckSettingsSpec();
            userHome.getDefaultObservabilityChecks().setSpec(defaultDataObservabilityChecks);
        } else {
            defaultDataObservabilityChecks = userHome.getDefaultObservabilityChecks().getSpec();
        }
        return defaultDataObservabilityChecks;
    }

}
