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
    void getSource_whenNewObject_thenHasDefaultValueTag() {
        Assertions.assertNotNull(this.sut.getSource());
        Assertions.assertEquals(DataStreamLevelSource.TAG, this.sut.getSource());
    }

    @Test
    void isDefault_whenNoValuesAssigned_thenReturnsFalseBecauseSourceIsAssignedValueToForceSerialization() {
        Assertions.assertFalse(this.sut.isDefault());
    }

    @Test
    void isDefault_whenSourceConfigured_thenReturnsFalse() {
        this.sut.setSource(DataStreamLevelSource.TAG);
        Assertions.assertFalse(this.sut.isDefault());
    }

    @Test
    void isDefault_whenStaticValueConfigured_thenReturnsFalse() {
        this.sut.setTag("abc");
        Assertions.assertFalse(this.sut.isDefault());
    }

    @Test
    void isDefault_whenColumnConfigured_thenReturnsFalse() {
        this.sut.setColumn("country");
        Assertions.assertFalse(this.sut.isDefault());
    }
}
