package ai.dqo.utils.docs.sensors;

import ai.dqo.BaseTest;
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
public class SensorDocumentationGeneratorImplTests extends BaseTest {
    private SensorDocumentationGeneratorImpl sut;
    private Path projectRootPath;
    private DqoHome dqoHome;

    @BeforeEach
    void setUp() {
        this.projectRootPath = Path.of(".");
        HandlebarsDocumentationUtilities.configure(this.projectRootPath);
        Path dqoHomePath = Path.of(System.getenv("DQO_HOME"));
        DqoHomeContext dqoHomeContext = DqoHomeDirectFactory.openDqoHome(dqoHomePath);
        this.dqoHome = dqoHomeContext.getDqoHome();
        SpecToUiCheckMappingServiceImpl specToUiCheckMappingService = new SpecToUiCheckMappingServiceImpl(new ReflectionServiceImpl());
        SensorDocumentationModelFactoryImpl sensorDocumentationModelFactory = new SensorDocumentationModelFactoryImpl(dqoHomeContext, specToUiCheckMappingService);

        this.sut = new SensorDocumentationGeneratorImpl(sensorDocumentationModelFactory);
    }

    @Test
    void renderSensorDocumentation_whenCalled_generatesDocumentationForAllSensorsInMemory() {
        DocumentationFolder documentationFolder = this.sut.renderSensorDocumentation(this.projectRootPath, this.dqoHome);
        Assertions.assertEquals(0, documentationFolder.getSubFolders().size());
    }

    @Test
    void createSensorDocumentationModels_whenCalled_generatesListOfSensorDocumentationModel() {
        List<SensorDocumentationModel> sensorDocumentationModels = this.sut.createSensorDocumentationModels(this.projectRootPath);
        Assertions.assertTrue(sensorDocumentationModels.size() > 1);
    }

    @Test
    void groupSensorsByTarget_whenCalled_generatesListOfSensorGroupedDocumentationModel() {
        List<SensorDocumentationModel> sensorDocumentationModels = this.sut.createSensorDocumentationModels(this.projectRootPath);
        List<SensorGroupedDocumentationModel> sensorGroupedDocumentationModels = this.sut.groupSensorsByTarget(sensorDocumentationModels);
        Assertions.assertEquals(2, sensorGroupedDocumentationModels.size());
    }
}
