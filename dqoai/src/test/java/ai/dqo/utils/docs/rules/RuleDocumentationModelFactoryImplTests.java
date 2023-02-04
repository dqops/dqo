package ai.dqo.utils.docs.rules;

import ai.dqo.BaseTest;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeDirectFactory;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingServiceImpl;
import ai.dqo.rules.averages.BetweenPercentMovingAverage7DaysRule5ParametersSpec;
import ai.dqo.utils.reflection.ReflectionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.Objects;

@SpringBootTest
public class RuleDocumentationModelFactoryImplTests extends BaseTest {
    private RuleDocumentationModelFactoryImpl sut;

    @BeforeEach
    void setUp() {
        Path projectRoot = Path.of(".");
        Path dqoHomePath = Path.of(System.getenv("DQO_HOME"));
        DqoHomeContext dqoHomeContext = DqoHomeDirectFactory.openDqoHome(dqoHomePath);
        SpecToUiCheckMappingServiceImpl specToUiCheckMappingService = new SpecToUiCheckMappingServiceImpl(
                new ReflectionServiceImpl(), new SensorDefinitionFindServiceImpl());
        this.sut = new RuleDocumentationModelFactoryImpl(projectRoot, dqoHomeContext, specToUiCheckMappingService);
    }

    @Test
    void createRuleDocumentation_whenCalledForClassWithRuleParameterFields_thenCreatesDocumentationModel() {
        BetweenPercentMovingAverage7DaysRule5ParametersSpec parametersSpec = new BetweenPercentMovingAverage7DaysRule5ParametersSpec();
        RuleDocumentationModel ruleDocumentation = this.sut.createRuleDocumentation(parametersSpec);

        Assertions.assertNotNull(ruleDocumentation);
        Assertions.assertEquals("Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.", ruleDocumentation.getRuleParametersJavaDoc());
        Assertions.assertEquals("averages", ruleDocumentation.getCategory());
        Assertions.assertEquals("between_percent_moving_average_7_days", ruleDocumentation.getRuleName());
        Assertions.assertEquals("averages/between_percent_moving_average_7_days", ruleDocumentation.getFullRuleName());

        Assertions.assertNotNull(ruleDocumentation.getRuleExample());

        Assertions.assertNotNull(ruleDocumentation.getDefinition());
        Assertions.assertEquals(2, ruleDocumentation.getDefinition().getSpec().getFields().size());
        Assertions.assertTrue(ruleDocumentation.getDefinition().getSpec().getFields().stream().anyMatch(f -> Objects.equals("maxPercentAbove", f.getFieldName())));
    }
}
