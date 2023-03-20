package ai.dqo.testutils;

import ai.dqo.connectors.jdbc.JdbcTypeColumnMapping;
import ai.dqo.core.configuration.DqoCloudConfigurationPropertiesObjectMother;
import ai.dqo.data.storage.TablesawParquetSupportFix;
import ai.dqo.metadata.storage.localfiles.userhome.LocalUserHomeCreatorObjectMother;
import ai.dqo.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * JUnit 5 extension with a setup method called before all test methods. The test class must extend the {@link ai.dqo.BaseTest}
 */
public class BeforeAllIntegrationTestExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {
    /**
     * Called before each test run.
     * @param extensionContext
     * @throws Exception
     */
    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        BeanFactory beanFactory = SpringExtension.getApplicationContext(extensionContext);
        BeanFactoryObjectMother.setBeanFactory(beanFactory); // let object mothers use the bean factory without propagating too many object instances
        TablesawParquetSupportFix.ensureInitialized();
        JdbcTypeColumnMapping.ensureInitializedJdbc();
        LocalUserHomeCreatorObjectMother.initializeDefaultDqoUserHomeSilentlyOnce();
        DqoCloudConfigurationPropertiesObjectMother.configureTestableApiKey();
        // to be extended in the future when the need appears
    }

    /**
     * Called after running all tests. Supports closing/disposing resources.
     * @throws Throwable
     */
    @Override
    public void close() throws Throwable {
    }
}
