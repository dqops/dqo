package ai.dqo.core.locks;

import ai.dqo.core.configuration.DqoCoreConfigurationProperties;
import ai.dqo.core.configuration.DqoCoreConfigurationPropertiesObjectMother;
import ai.dqo.utils.BeanFactoryObjectMother;

/**
 * Object mother for {@link UserHomeLockManager}
 */
public final class UserHomeLockManagerObjectMother {
    /**
     * Creates a new instance of a user home lock manager.
     * @return User home lock manager.
     */
    public static UserHomeLockManager createNewLockManager() {
        DqoCoreConfigurationProperties coreConfigurationProperties = DqoCoreConfigurationPropertiesObjectMother.getCoreConfigurationProperties();
        return new UserHomeLockManagerImpl(coreConfigurationProperties);
    }

    /**
     * Returns the real shared global lock manager.
     * @return Global lock manager that is protecting access to the user home.
     */
    public static UserHomeLockManager getDefaultGlobalLockManager() {
        return BeanFactoryObjectMother.getBeanFactory().getBean(UserHomeLockManager.class);
    }
}
