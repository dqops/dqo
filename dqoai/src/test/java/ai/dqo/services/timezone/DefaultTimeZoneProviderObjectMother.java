package ai.dqo.services.timezone;

import ai.dqo.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother for the default time zone provider.
 */
public class DefaultTimeZoneProviderObjectMother {
    /**
     * Returns the default (singleton) time zone provider.
     * @return Default time zone provider.
     */
    public static DefaultTimeZoneProvider getDefaultTimeZoneProvider() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(DefaultTimeZoneProvider.class);
    }
}
