package ai.dqo.rules.stdev;

import ai.dqo.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PercentileMovingStdevRuleThresholdsSpecTests extends BaseTest {
    private PercentileMovingStdevRuleThresholdsSpec sut;



    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
        this.sut = new PercentileMovingStdevRuleThresholdsSpec();
    }

    @Test
    void getTimeWindow_whenObjectCreated_thenTimewindowIsInitialized() {
        Assertions.assertNotNull(this.sut.getTimeWindow());
    }
}
