/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.data.storage.parquet;

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
    private Configuration hadoopConfiguration;

    /**
     * Returns a shared hadoop configuration.
     * @return Shared hadoop configuration.
     */
    @Override
    public Configuration getHadoopConfiguration() {
        synchronized (this) {
            if (hadoopConfiguration == null) {
                hadoopConfiguration = new Configuration();
                hadoopConfiguration.get("dqo.fake.property"); // called only to load the configuration in the current thread, to avoid race condition
                hadoopConfiguration.set("fs.inmemory.size.mb", "100"); // max in-memory file system size
                hadoopConfiguration.set("io.file.buffer.size", "65536"); // copy block size
                hadoopConfiguration.set("fs.ramfs.impl", "ai.dqo.data.storage.parquet.InMemoryFileSystem");
            }

            return hadoopConfiguration;
        }
    }
}
