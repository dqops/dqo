package ai.dqo.connectors.postgresql;

import ai.dqo.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;

@SpringBootTest
public class PostgresqlParametersSpecTests extends BaseTest {
    private PostgresqlParametersSpec sut;

    @BeforeEach
    void setUp() {
        this.sut = new PostgresqlParametersSpec();
    }

    @Test
    void isDirty_whenDatabaseSet_thenIsDirtyIsTrue() {
        this.sut.setDatabase("test");
        Assertions.assertEquals("test", this.sut.getDatabase());
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSameDatabaseStringAsCurrentSet_thenIsDirtyIsFalse() {
        this.sut.setDatabase("test");
        Assertions.assertTrue(this.sut.isDirty());
        this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
        this.sut.setDatabase("test");
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenPasswordSet_thenIsDirtyIsTrue() {
        this.sut.setPassword("test");
        Assertions.assertEquals("test", this.sut.getPassword());
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSamePasswordeStringAsCurrentSet_thenIsDirtyIsFalse() {
        this.sut.setPassword("test");
        Assertions.assertTrue(this.sut.isDirty());
        this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
        this.sut.setPassword("test");
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenPropertiesSet_thenIsDirtyIsTrue() {
        LinkedHashMap hashMap = new LinkedHashMap<String, String>();
        hashMap.put("test", "test");
        this.sut.setProperties(hashMap);
        Assertions.assertEquals(hashMap, this.sut.getProperties());
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSamePropertiesObjectAsCurrentSet_thenIsDirtyIsFalse() {
        LinkedHashMap hashMap = new LinkedHashMap<String, String>();
        hashMap.put("test", "test");
        this.sut.setProperties(hashMap);
        this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
        this.sut.setProperties(hashMap);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenUserSet_thenIsDirtyIsTrue() {
        this.sut.setUser("test");
        Assertions.assertEquals("test", this.sut.getUser());
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSameUserStringAsCurrentSet_thenIsDirtyIsFalse() {
        this.sut.setUser("test");
        Assertions.assertTrue(this.sut.isDirty());
        this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
        this.sut.setUser("test");
        Assertions.assertFalse(this.sut.isDirty());
    }
}
