package ai.dqo.utils.docs.sensors;

import ai.dqo.BaseTest;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeDirectFactory;
import ai.dqo.rest.models.checks.mapping.SpecToUiCheckMappingServiceImpl;
import ai.dqo.sensors.column.strings.ColumnStringsStringRegexMatchPercentSensorParametersSpec;
import ai.dqo.utils.reflection.ReflectionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.Objects;

@SpringBootTest
public class SensorDocumentationModelFactoryImplTests extends BaseTest {
    private SensorDocumentationModelFactoryImpl sut;

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
        Path dqoHomePath = Path.of(System.getenv("DQO_HOME"));
        DqoHomeContext dqoHomeContext = DqoHomeDirectFactory.openDqoHome(dqoHomePath);
        SpecToUiCheckMappingServiceImpl specToUiCheckMappingService = new SpecToUiCheckMappingServiceImpl(new ReflectionServiceImpl());
        this.sut = new SensorDocumentationModelFactoryImpl(dqoHomeContext, specToUiCheckMappingService);
    }

    @Test
    void createSensorDocumentation_whenCalledForClassWithSensorParameterFields_thenCreatesDocumentationModel() {
        ColumnStringsStringRegexMatchPercentSensorParametersSpec parametersSpec = new ColumnStringsStringRegexMatchPercentSensorParametersSpec();
        SensorDocumentationModel sensorDocumentation = this.sut.createSensorDocumentation(parametersSpec);

        Assertions.assertNotNull(sensorDocumentation);
        Assertions.assertEquals("Column level sensor that calculates the percent of values that fit to a regex in a column.", sensorDocumentation.getSensorParametersJavaDoc());
        Assertions.assertEquals("column", sensorDocumentation.getTarget());
        Assertions.assertEquals("strings", sensorDocumentation.getCategory());
        Assertions.assertEquals("string_regex_match_percent", sensorDocumentation.getSensorName());
        Assertions.assertEquals("column/strings/string_regex_match_percent", sensorDocumentation.getFullSensorName());

        Assertions.assertNotNull(sensorDocumentation.getDefinition());
        Assertions.assertEquals(1, sensorDocumentation.getDefinition().getSpec().getFields().size());
        Assertions.assertTrue(sensorDocumentation.getDefinition().getSpec().getFields().stream().anyMatch(f -> Objects.equals("regex", f.getFieldName())));
    }
}
