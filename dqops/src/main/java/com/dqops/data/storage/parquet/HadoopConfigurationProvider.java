/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.storage.parquet;

import org.apache.hadoop.conf.Configuration;

/**
 * Returns a static hadoop configuration, loaded only once.
 */
public interface HadoopConfigurationProvider {
    /**
     * Returns a shared hadoop configuration.
     *
     * @return Shared hadoop configuration.
     */
    Configuration getHadoopConfiguration();
}
