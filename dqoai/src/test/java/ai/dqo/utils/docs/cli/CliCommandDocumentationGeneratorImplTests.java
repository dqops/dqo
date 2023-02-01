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

    @BeforeEach
    void setUp() {
        this.sut = new CliCommandDocumentationGeneratorImpl(new CliCommandDocumentationModelFactoryImpl());
    }

    @Test
    void createCommandModels_whenCalled_generatesModelsForAllCliCommands() {
        List<CliRootCommandDocumentationModel> commandModels = this.sut.createCommandModels();
        Assertions.assertTrue(commandModels.size() > 1);
    }
}
