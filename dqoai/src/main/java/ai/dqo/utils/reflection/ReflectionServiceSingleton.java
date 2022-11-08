package ai.dqo.utils.reflection;

import ai.dqo.utils.StaticBeanFactory;

/**
 * Provides static access to the {@link ReflectionService} singleton instance from static code.
 */
public class ReflectionServiceSingleton {
    private static ReflectionService reflectionService;

    /**
     * Returns a static shared instance of the reflection service.
     * @return Reflection service.
     */
    public static ReflectionService getInstance() {
        if (reflectionService == null) {
            reflectionService = StaticBeanFactory.getBeanFactory().getBean(ReflectionService.class);
        }
        return reflectionService;
    }
}
