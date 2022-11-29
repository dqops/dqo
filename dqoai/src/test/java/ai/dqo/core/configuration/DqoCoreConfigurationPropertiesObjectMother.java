package ai.dqo.core.configuration;

import ai.dqo.utils.BeanFactoryObjectMother;

/**
 * Object mother for {@link DqoCoreConfigurationProperties}
 */
public final class DqoCoreConfigurationPropertiesObjectMother {
    /**
     * Creates a new dqo core configuration properties (cloned).
     * @return DQO core configuration properties.
     */
    public static DqoCoreConfigurationProperties getCoreConfigurationProperties() {
        DqoCoreConfigurationProperties configurationProperties = BeanFactoryObjectMother.getBeanFactory().getBean(DqoCoreConfigurationProperties.class).clone();
        return configurationProperties;
    }
}
