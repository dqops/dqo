/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.execution.sqltemplates.grouping;

import com.dqops.BaseTest;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FragmentedSqlQueryTests extends BaseTest {
    private FragmentedSqlQuery sut;
    private SqlQueryFragmentsParserImpl parser;

    @BeforeEach
    void setUp() {
        this.parser = new SqlQueryFragmentsParserImpl();
    }

    private FragmentedSqlQuery parseQuery(String sql) {
        FragmentedSqlQuery fragmentedSqlQuery = this.parser.parseQueryToComponents(sql,
                SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME, SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME);
        return fragmentedSqlQuery;
    }

    @Test
    void equals_whenTwoQueriesWithStaticSqlsThatAreEqual_thenReturnsTrue() {
        this.sut = parseQuery("""
                SELECT
                    count(*) as something
                FROM
                    schema.table""");

        FragmentedSqlQuery other = parseQuery("""
                SELECT
                    count(*) as something
                FROM
                    schema.table""");

        Assert.assertTrue(this.sut.equals(other));
        Assert.assertEquals(this.sut.hashCode(), other.hashCode());
    }

    @Test
    void equals_whenTwoQueriesWithStaticSqlsThatAreNotEqual_thenReturnsFalse() {
        this.sut = parseQuery("""
                SELECT
                    count(*) as something
                FROM
                    schema.table""");

        FragmentedSqlQuery other = parseQuery("""
                SELECT
                    count(*) as other" + "
                FROM
                    schema.table""");

        Assert.assertFalse(this.sut.equals(other));
        Assert.assertNotEquals(this.sut.hashCode(), other.hashCode());
    }

    @Test
    void equals_whenTwoQueriesThatHaveAlsoActualValuesSectionAndDifferByActualValueFragemnt_thenReturnsTrue() {
        this.sut = parseQuery("""
                SELECT
                    count(*) as actual_value
                FROM
                    schema.table""");

        FragmentedSqlQuery other = parseQuery("""
                SELECT
                    max(col) as actual_value
                FROM
                    schema.table""");

        Assert.assertTrue(this.sut.equals(other));
        Assert.assertEquals(this.sut.hashCode(), other.hashCode());
    }
}
