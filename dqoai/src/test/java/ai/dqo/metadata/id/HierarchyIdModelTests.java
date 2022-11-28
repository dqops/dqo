package ai.dqo.metadata.id;

import ai.dqo.BaseTest;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.metadata.userhome.UserHomeImpl;
import ai.dqo.metadata.userhome.UserHomeObjectMother;
import ai.dqo.utils.serialization.JsonSerializer;
import ai.dqo.utils.serialization.JsonSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HierarchyIdModelTests extends BaseTest {
    private JsonSerializer jsonSerializer;
    private UserHomeImpl userHome;

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
        this.jsonSerializer = JsonSerializerObjectMother.createNew();
        this.userHome = UserHomeObjectMother.createBareUserHome();
    }

    @Test
    void toHierarchyId_whenTableHierarchyIdDeserialized_thenReturnsValidHierarchyIdThatEqualsOriginalHierarchyId() {
        ConnectionWrapper connectionWrapper = this.userHome.getConnections().createAndAddNew("conn1");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("schema", "tab1"));
        TableSpec tableSpec = new TableSpec();
        tableWrapper.setSpec(tableSpec);

        HierarchyId originalHierarchyId = tableSpec.getHierarchyId();
        Assertions.assertNotNull(originalHierarchyId);
        HierarchyIdModel sut = originalHierarchyId.toHierarchyIdModel();

        String serialized = this.jsonSerializer.serialize(sut);
        HierarchyIdModel deserializedSut = this.jsonSerializer.deserialize(serialized, HierarchyIdModel.class);

        HierarchyId recreatedHierarchyId = deserializedSut.toHierarchyId();
        Assertions.assertEquals(originalHierarchyId, recreatedHierarchyId);
    }
}
