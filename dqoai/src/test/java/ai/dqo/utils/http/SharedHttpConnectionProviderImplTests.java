package ai.dqo.utils.http;

import ai.dqo.BaseTest;
import ai.dqo.core.configuration.DqoHttpClientPoolConfigurationProperties;
import ai.dqo.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.netty.resources.ConnectionProvider;

@SpringBootTest
public class SharedHttpConnectionProviderImplTests extends BaseTest {
    private SharedHttpConnectionProviderImpl sut;
    private BeanFactory beanFactory;

    @BeforeEach
    void setUp() {
        beanFactory = BeanFactoryObjectMother.getBeanFactory();
        DqoHttpClientPoolConfigurationProperties httpClientPoolConfigurationProperties =
                beanFactory.getBean(DqoHttpClientPoolConfigurationProperties.class).clone();
        this.sut = new SharedHttpConnectionProviderImpl(httpClientPoolConfigurationProperties);
    }

    @Test
    void getInstance_whenInstanceOfSharedHttpConnectionProviderRetrieved_thenReturnsSingleton() {
        SharedHttpConnectionProvider instance = this.beanFactory.getBean(SharedHttpConnectionProvider.class);
        Assertions.assertNotNull(instance);
        SharedHttpConnectionProvider instance2 = this.beanFactory.getBean(SharedHttpConnectionProvider.class);
        Assertions.assertNotNull(instance2);
        Assertions.assertSame(instance, instance2);
    }

    @Test
    void getConnectionProvider_whenRetrieved_thenReturnsConfiguredConnectionProvider() {
        ConnectionProvider connectionProvider = this.sut.getConnectionProvider();
        Assertions.assertNotNull(connectionProvider);
    }

    @Test
    void getConnectionProvider_whenRetrievedMultipleTimes_thenReturnsTheSameInstance() {
        ConnectionProvider connectionProvider = this.sut.getConnectionProvider();
        Assertions.assertNotNull(connectionProvider);
        ConnectionProvider connectionProvider2 = this.sut.getConnectionProvider();
        Assertions.assertNotNull(connectionProvider2);
        Assertions.assertSame(connectionProvider, connectionProvider2);
    }
}
