/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.sources;

import com.dqops.BaseTest;
import com.dqops.connectors.ProviderType;
import com.dqops.connectors.postgresql.PostgresqlParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZoneId;
import java.util.LinkedHashMap;

@SpringBootTest
public class ConnectionSpecTests extends BaseTest {
    private ConnectionSpec sut;

    @BeforeEach
    void setUp() {
		this.sut = new ConnectionSpec();
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
