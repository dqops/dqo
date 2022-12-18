package ai.dqo.data.storage.parquet;

import ai.dqo.utils.BeanFactoryObjectMother;

/**
 * Object mother that returns a shared hadoop configuration provider (the default singleton).
 */
public class HadoopConfigurationProviderObjectMother {
    /**
     * Returns the default hadoop configuration provider instance from spring.
     * @return Hadoop configuration provider.
     */
    public static HadoopConfigurationProvider getDefault() {
        return BeanFactoryObjectMother.getBeanFactory().getBean(HadoopConfigurationProvider.class);
    }
}
