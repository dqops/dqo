package ai.dqo.utils.docs.rules;

import ai.dqo.BaseTest;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeDirectFactory;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingServiceImpl;
import ai.dqo.utils.docs.HandlebarsDocumentationUtilities;
import ai.dqo.utils.docs.files.DocumentationFolder;
import ai.dqo.utils.reflection.ReflectionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.List;

@SpringBootTest
public class RuleDocumentationGeneratorImplTests extends BaseTest {
    private RuleDocumentationGeneratorImpl sut;
    private Path projectRootPath;
    private DqoHome dqoHome;

    @BeforeEach
    void setUp() {
        this.projectRootPath = Path.of(".");
        HandlebarsDocumentationUtilities.configure(this.projectRootPath);
        Path dqoHomePath = Path.of(System.getenv("DQO_HOME"));
        DqoHomeContext dqoHomeContext = DqoHomeDirectFactory.openDqoHome(dqoHomePath);
        this.dqoHome = dqoHomeContext.getDqoHome();
        SpecToUiCheckMappingServiceImpl specToUiCheckMappingService = new SpecToUiCheckMappingServiceImpl(
                new ReflectionServiceImpl(), new SensorDefinitionFindServiceImpl());
        RuleDocumentationModelFactoryImpl ruleDocumentationModelFactory = new RuleDocumentationModelFactoryImpl(projectRootPath, dqoHomeContext, specToUiCheckMappingService);

        this.sut = new RuleDocumentationGeneratorImpl(ruleDocumentationModelFactory);
    }

    @Test
    void renderRuleDocumentation_whenCalled_generatesDocumentationForAllRulesInMemory() {
        DocumentationFolder documentationFolder = this.sut.renderRuleDocumentation(this.projectRootPath, this.dqoHome);
        Assertions.assertEquals(0, documentationFolder.getSubFolders().size());
    }

    @Test
    void createRuleDocumentationModels_whenCalled_generatesListOfRuleDocumentationModel() {
        List<RuleDocumentationModel> ruleDocumentationModels = this.sut.createRuleDocumentationModels(projectRootPath);
        Assertions.assertTrue(ruleDocumentationModels.size() > 1);
    }

    @Test
    void groupRulesByCategory_whenCalled_generatesListOfRuleGroupedDocumentationModel() {
        List<RuleDocumentationModel> ruleDocumentationModels = this.sut.createRuleDocumentationModels(projectRootPath);
        List<RuleGroupedDocumentationModel> ruleGroupedDocumentationModels = this.sut.groupRulesByCategory(ruleDocumentationModels);
        Assertions.assertTrue(ruleGroupedDocumentationModels.size() >= 3);
    }
}
