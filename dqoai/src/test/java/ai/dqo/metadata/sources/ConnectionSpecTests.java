/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.metadata.sources;

import ai.dqo.BaseTest;
import ai.dqo.connectors.ProviderType;
import ai.dqo.connectors.postgresql.PostgresqlParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZoneId;
import java.util.LinkedHashMap;

@SpringBootTest
public class ConnectionSpecTests extends BaseTest {
    private ConnectionSpec sut;

    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
		this.sut = new ConnectionSpec();
    }

    @Test
    void zoneId_whenDefault_thenReturnsUTC() {
        Assertions.assertEquals("UTC", this.sut.getTimeZone());
    }

    @Test
    void getJavaTimeZoneId_whenDefault_thenReturnsUTC() {
        ZoneId zoneId = this.sut.getJavaTimeZoneId();
        Assertions.assertEquals("UTC", zoneId.toString());
    }

    @Test
    void getJavaTimeZoneId_whenESTTimeZone_thenReturnsEST() {
		this.sut.setTimeZone("EST");
        ZoneId zoneId = this.sut.getJavaTimeZoneId();
        Assertions.assertEquals("-05:00", zoneId.toString());  // does it change with the daylight saving time over the year?
    }

    @Test
    void getJavaTimeZoneId_whenUSEasternTimeZone_thenReturnsEST() {
		this.sut.setTimeZone("US/Eastern");
        ZoneId zoneId = this.sut.getJavaTimeZoneId();
        Assertions.assertEquals("US/Eastern", zoneId.toString());
    }

    @Test
    void getJavaTimeZoneId_whenUnknownTimeZone_thenReturnsUTC() {
		this.sut.setTimeZone("unknown name");
        ZoneId zoneId = this.sut.getJavaTimeZoneId();
        Assertions.assertEquals("UTC", zoneId.toString());
    }

    @Test
    void isDirty_whenDatabaseNameSet_thenIsDirtyIsTrue() {
		this.sut.setDatabaseName("test");
        Assertions.assertEquals("test", this.sut.getDatabaseName());
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSameDatabaseNameStringAsCurrentSet_thenIsDirtyIsFalse() {
		this.sut.setDatabaseName("test");
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
		this.sut.setDatabaseName("test");
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenDialectSet_thenIsDirtyIsTrue() {
		this.sut.setProviderType(ProviderType.bigquery);
        Assertions.assertEquals(ProviderType.bigquery, this.sut.getProviderType());
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSameDialectStringAsCurrentSet_thenIsDirtyIsFalse() {
		this.sut.setProviderType(ProviderType.bigquery);
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
		this.sut.setProviderType(ProviderType.bigquery);
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

    @Test
    void copyNotNullPropertiesFrom_whenNoFieldsUpdated_thenNothingChangedAndNotDirty() {
        ConnectionSpec source = new ConnectionSpec();
        this.sut.clearDirty(true);
        this.sut.copyNotNullPropertiesFrom(source);

        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void copyNotNullPropertiesFrom_whenProviderUpdated_thenIsDirtyAndFieldUpdated() {
        ConnectionSpec source = new ConnectionSpec();
        source.setProviderType(ProviderType.snowflake);
        this.sut.clearDirty(true);
        this.sut.copyNotNullPropertiesFrom(source);

        Assertions.assertTrue(this.sut.isDirty());
        Assertions.assertEquals(ProviderType.snowflake, this.sut.getProviderType());
    }

    @Test
    void copyNotNullPropertiesFrom_whenHostNameInPostgresqlUpdatedAndPostgresWasMissing_thenCreatesAndUpdatesField() {
        ConnectionSpec source = new ConnectionSpec();
        source.setPostgresql(new PostgresqlParametersSpec() {{
            setHost("somehost");
        }});
        this.sut.clearDirty(true);
        this.sut.copyNotNullPropertiesFrom(source);

        Assertions.assertTrue(this.sut.isDirty());
        Assertions.assertEquals("somehost", this.sut.getPostgresql().getHost());
    }

    @Test
    void copyNotNullPropertiesFrom_whenHostNameInPostgresqlUpdatedAndPostgresWasAlreadyPresent_thenUpdatesOnlyRequestedFields() {
        ConnectionSpec source = new ConnectionSpec();
        source.setPostgresql(new PostgresqlParametersSpec() {{
            setHost("somehost");
        }});
        this.sut.setPostgresql(new PostgresqlParametersSpec() {{
            setHost("oldhost");
            setPort("7777");
        }});
        this.sut.clearDirty(true);
        this.sut.copyNotNullPropertiesFrom(source);

        Assertions.assertTrue(this.sut.isDirty());
        Assertions.assertEquals("somehost", this.sut.getPostgresql().getHost());
        Assertions.assertEquals("7777", this.sut.getPostgresql().getPort());
    }
}
