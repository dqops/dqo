package ai.dqo.utils.docs.rules;

import ai.dqo.BaseTest;
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeDirectFactory;
import ai.dqo.rest.models.checks.mapping.SpecToUiCheckMappingServiceImpl;
import ai.dqo.utils.docs.HandlebarsDocumentationUtilities;
import ai.dqo.utils.docs.files.DocumentationFolder;
import ai.dqo.utils.reflection.ReflectionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;

@SpringBootTest
public class RuleDocumentationGeneratorImplTests extends BaseTest {
    private RuleDocumentationGeneratorImpl sut;
    private Path projectRootPath;
    private DqoHome dqoHome;

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

        this.projectRootPath = Path.of(".");
        HandlebarsDocumentationUtilities.configure(this.projectRootPath);
        Path dqoHomePath = Path.of(System.getenv("DQO_HOME"));
        DqoHomeContext dqoHomeContext = DqoHomeDirectFactory.openDqoHome(dqoHomePath);
        this.dqoHome = dqoHomeContext.getDqoHome();
        SpecToUiCheckMappingServiceImpl specToUiCheckMappingService = new SpecToUiCheckMappingServiceImpl(new ReflectionServiceImpl());
        RuleDocumentationModelFactoryImpl ruleDocumentationModelFactory = new RuleDocumentationModelFactoryImpl(dqoHomeContext, specToUiCheckMappingService);

        this.sut = new RuleDocumentationGeneratorImpl(ruleDocumentationModelFactory);
    }

    @Test
    void renderRuleDocumentation_whenCalled_generatesDocumentationForAllRulesInMemory() {
        DocumentationFolder documentationFolder = this.sut.renderRuleDocumentation(this.projectRootPath, this.dqoHome);
        Assertions.assertEquals(3, documentationFolder.getSubFolders().size());
    }
}
