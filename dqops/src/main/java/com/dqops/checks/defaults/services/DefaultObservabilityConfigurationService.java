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

package com.dqops.checks.defaults.services;

import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.userhome.UserHome;

import java.util.List;

/**
 * Service that will apply the default configuration of the data observability (the default checks) on new tables and columns that are imported.
 */
public interface DefaultObservabilityConfigurationService {
    /**
     * Applies the default configuration of default checks on a list of tables that were imported.
     *
     * @param tableSpecList           List of tables.
     * @param providerDialectSettings Provider specific dialect settings, to detect the column type and if certain categories of checks could be applied.
     * @param userHome                User home, to read the configuration.
     */
    void applyDefaultChecks(List<TableSpec> tableSpecList, ProviderDialectSettings providerDialectSettings, UserHome userHome);
}
