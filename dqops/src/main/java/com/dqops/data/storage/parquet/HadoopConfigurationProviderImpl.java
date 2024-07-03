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
