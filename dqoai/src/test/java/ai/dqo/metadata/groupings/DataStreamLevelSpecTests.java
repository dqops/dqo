package ai.dqo.metadata.groupings;

import ai.dqo.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DataStreamLevelSpecTests extends BaseTest {
    private DataStreamLevelSpec sut;

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
        this.sut = new DataStreamLevelSpec();
    }

    @Test
    void getSource_whenNewObject_thenHasDefaultValueStaticValue() {
        Assertions.assertNotNull(this.sut.getSource());
        Assertions.assertEquals(DataStreamLevelSource.STATIC_VALUE, this.sut.getSource());
    }

    @Test
    void isDefault_whenNoValuesAssigned_thenReturnsFalseBecauseSourceIsAssignedValueToForceSerialization() {
        Assertions.assertFalse(this.sut.isDefault());
    }

    @Test
    void isDefault_whenSourceConfigured_thenReturnsFalse() {
        this.sut.setSource(DataStreamLevelSource.STATIC_VALUE);
        Assertions.assertFalse(this.sut.isDefault());
    }

    @Test
    void isDefault_whenStaticValueConfigured_thenReturnsFalse() {
        this.sut.setStaticValue("abc");
        Assertions.assertFalse(this.sut.isDefault());
    }

    @Test
    void isDefault_whenColumnConfigured_thenReturnsFalse() {
        this.sut.setColumn("country");
        Assertions.assertFalse(this.sut.isDefault());
    }
}
