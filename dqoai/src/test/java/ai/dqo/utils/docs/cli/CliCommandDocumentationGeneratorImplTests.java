package ai.dqo.utils.docs.cli;

import ai.dqo.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CliCommandDocumentationGeneratorImplTests extends BaseTest {
    private CliCommandDocumentationGeneratorImpl sut;

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();

        this.sut = new CliCommandDocumentationGeneratorImpl(new CliCommandDocumentationModelFactoryImpl());
    }

    @Test
    void createCommandModels_whenCalled_generatesModelsForAllCliCommands() {
        List<CliRootCommandDocumentationModel> commandModels = this.sut.createCommandModels();
        Assertions.assertTrue(commandModels.size() > 1);
    }
}
