package ai.dqo.utils.docs.checks;

import ai.dqo.BaseTest;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import ai.dqo.execution.sqltemplates.JinjaTemplateRenderServiceObjectMother;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingService;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingServiceImpl;
import ai.dqo.services.check.mapping.UiToSpecCheckMappingServiceImpl;
import ai.dqo.services.check.matching.SimilarCheckMatchingServiceImpl;
import ai.dqo.utils.docs.rules.RuleDocumentationModelFactoryImpl;
import ai.dqo.utils.docs.sensors.SensorDocumentationModelFactoryImpl;
import ai.dqo.utils.reflection.ReflectionServiceImpl;
import ai.dqo.utils.serialization.YamlSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.List;

@SpringBootTest
public class CheckDocumentationModelFactoryImplTests extends BaseTest {
    private CheckDocumentationModelFactoryImpl sut;

    @BeforeEach
    void setUp() {
        Path projectRoot = Path.of(".");
        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        SpecToUiCheckMappingService specToUiCheckMappingService = new SpecToUiCheckMappingServiceImpl(
                reflectionService, new SensorDefinitionFindServiceImpl());
        SimilarCheckMatchingServiceImpl similarCheckMatchingService = new SimilarCheckMatchingServiceImpl(specToUiCheckMappingService);
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        UiToSpecCheckMappingServiceImpl uiToSpecCheckMappingService = new UiToSpecCheckMappingServiceImpl(reflectionService);
        this.sut = new CheckDocumentationModelFactoryImpl(
                dqoHomeContext,
                similarCheckMatchingService,
                new SensorDocumentationModelFactoryImpl(dqoHomeContext, specToUiCheckMappingService),
                new RuleDocumentationModelFactoryImpl(projectRoot, dqoHomeContext, specToUiCheckMappingService),
                uiToSpecCheckMappingService,
                YamlSerializerObjectMother.getDefault(),
                JinjaTemplateRenderServiceObjectMother.getDefault()
        );
    }

    @Test
    void makeDocumentationForTableChecks_whenCalled_thenGeneratesTableLevelSensorDocumentation() {
        List<CheckCategoryDocumentationModel> documentationModels = this.sut.makeDocumentationForTableChecks();
        Assertions.assertTrue(documentationModels.size() > 1);
    }

    @Test
    void makeDocumentationForColumnChecks_whenCalled_thenGeneratesColumnLevelSensorDocumentation() {
        List<CheckCategoryDocumentationModel> documentationModels = this.sut.makeDocumentationForColumnChecks();
        Assertions.assertTrue(documentationModels.size() > 1);
    }
}
