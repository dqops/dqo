package ai.dqo.data.storage.parquet;

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
