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

import com.dqops.utils.exceptions.DqoRuntimeException;
import org.apache.hadoop.conf.Configuration;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Returns a static hadoop configuration, loaded only once.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class HadoopConfigurationProviderImpl implements HadoopConfigurationProvider {
    private static Configuration hadoopConfiguration;

    /**
     * Returns a shared hadoop configuration.
     * @return Shared hadoop configuration.
     */
    @Override
    public Configuration getHadoopConfiguration() {
        synchronized (this) {
            try {
                if (hadoopConfiguration == null) {
                    hadoopConfiguration = new Configuration();
                    hadoopConfiguration.get("dqo.fake.property"); // called only to load the configuration in the current thread, to avoid race condition
                    hadoopConfiguration.getClassByName(org.apache.hadoop.fs.LocalFileSystem.class.getName()); // force loading the class cache

                    hadoopConfiguration.set("fs.inmemory.size.mb", "100"); // max in-memory file system size
                    hadoopConfiguration.set("io.file.buffer.size", "65536"); // copy block size
                    hadoopConfiguration.set("fs.defaultFS", "file:///");
                    hadoopConfiguration.set("fs.ramfs.impl", com.dqops.data.storage.parquet.DqoInMemoryFileSystem.class.getName());
                    hadoopConfiguration.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
                }

                return hadoopConfiguration;
            }
            catch (Exception ex) {
                throw new DqoRuntimeException("Failed to initialize hadoop configuration, exception: " + ex.getMessage(), ex);
            }
        }
    }
}
