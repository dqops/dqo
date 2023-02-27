package ai.dqo.core.configuration;

import ai.dqo.BaseTest;
import ai.dqo.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DqoStorageGcpConfigurationPropertiesTests extends BaseTest {
    private DqoStorageGcpConfigurationProperties sut;

    @BeforeEach
    void setUp() {
        this.sut = BeanFactoryObjectMother.getBeanFactory().getBean(DqoStorageGcpConfigurationProperties.class);
    }

    @Test
    void http2_whenDefaultConfiguration_thenReturnsTrue() {
        Assertions.assertTrue(this.sut.isHttp2());
    }

    @Test
    void getHttp2MaxConcurrentStreams_whenDefaultConfiguration_thenReturns5000() {
        Assertions.assertEquals(2000, this.sut.getHttp2MaxConcurrentStreams().intValue());
    }
}
