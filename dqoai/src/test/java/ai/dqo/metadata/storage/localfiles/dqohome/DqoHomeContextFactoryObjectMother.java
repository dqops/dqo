package ai.dqo.metadata.storage.localfiles.dqohome;

import ai.dqo.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother for {@link DqoHomeContextFactory}
 */
public class DqoHomeContextFactoryObjectMother {
    /**
     * Returns a real DQO home context factory.
     * @return DQO Home context factory.
     */
    public static DqoHomeContextFactory getRealDqoHomeContextFactory() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        DqoHomeContextFactory dqoHomeContextFactory = beanFactory.getBean(DqoHomeContextFactory.class);
        return dqoHomeContextFactory;
    }
}
