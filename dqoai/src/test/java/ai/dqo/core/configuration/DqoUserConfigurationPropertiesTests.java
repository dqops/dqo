package ai.dqo.core.configuration;

import ai.dqo.BaseTest;
import ai.dqo.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DqoUserConfigurationPropertiesTests extends BaseTest {
    private DqoUserConfigurationProperties sut;

    @BeforeEach
    void setUp() {
        this.sut = BeanFactoryObjectMother.getBeanFactory().getBean(DqoUserConfigurationProperties.class);
    }

    @Test
    void dqo_whenHomeRetrieved_returnsConfigurationForHomeFolder() {
        String home = this.sut.getHome();
        String dqo_home = System.getenv("DQO_HOME");
        assertNotNull(dqo_home);
        assertNotNull(home);
        assertTrue(Files.isDirectory(Path.of(home)));
    }
}
