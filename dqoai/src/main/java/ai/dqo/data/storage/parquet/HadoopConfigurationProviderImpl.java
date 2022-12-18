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
            }

            return hadoopConfiguration;
        }
    }
}
