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

package ai.dqo.execution.sqltemplates.grouping;

import org.apache.parquet.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Rendered SQL query that was divided into components that are static SQLs (the beginning and the ending of an SQL query) that are the same between similar queries
 * and the components responsible for the calculating the actual value, that could differ between queries.
 */
public class FragmentedSqlQuery {
    private final String originalSql;
    private final ArrayList<SqlQueryFragment> components = new ArrayList<>();
    private String actualValueAlias;
    private String expectedValueAlias;

    /**
     * Creates an SQL component list.
     * @param originalSql The original SQL that will be divided into components.
     * @param actualValueAlias The alias of the actual value.
     * @param expectedValueAlias The alias of the expected value.
     */
    public FragmentedSqlQuery(String originalSql, String actualValueAlias, String expectedValueAlias) {
        this.originalSql = originalSql;
        this.actualValueAlias = actualValueAlias;
        this.expectedValueAlias = expectedValueAlias;
    }

    /**
     * Creates a single fragment query that does not support grouping and substitution.
     * It is a single static sql fragment where the actual_value component was not detected.
     * @param sql SQL query.
     * @param actualValueAlias The alias of the actual value.
     * @param expectedValueAlias The alias of the expected value.
     * @return SQL query fragment as a single static SQL.
     */
    public static FragmentedSqlQuery createNotGroupableQuery(String sql, String actualValueAlias, String expectedValueAlias) {
        FragmentedSqlQuery fragmentedSqlQuery = new FragmentedSqlQuery(sql, actualValueAlias, expectedValueAlias);
        fragmentedSqlQuery.add(new SqlQueryFragment(SqlQueryFragmentType.STATIC_FRAGMENT, sql));
        return fragmentedSqlQuery;
    }

    /**
     * Adds an SQL fragment component to the list of components.
     * @param sqlQueryFragment SQL Query component.
     */
    public void add(SqlQueryFragment sqlQueryFragment) {
        this.components.add(sqlQueryFragment);
    }

    /**
     * Returns the original SQL query that is divided into components.
     * @return The original SQL query.
     */
    public String getOriginalSql() {
        return originalSql;
    }

    /**
     * Returns a collection of SQL components.
     * @return Collection of sql components.
     */
    public List<SqlQueryFragment> getComponents() {
        return components;
    }

    /**
     * Returns the name of the actual_value alias (output column name) that is used in the query.
     * @return Detected output column name used to extract the actual value (the sensor readout).
     */
    public String getActualValueAlias() {
        return actualValueAlias;
    }

    /**
     * Sets the column name that contains the output sensor readout.
     * @param actualValueAlias The output column alias that contains the sensor readout value.
     */
    public void setActualValueAlias(String actualValueAlias) {
        if (Strings.isNullOrEmpty(actualValueAlias)) {
            throw new NullPointerException("Actual value alias cannot be null or empty.");
        }
        this.actualValueAlias = actualValueAlias;
    }

    /**
     * Returns the name of the expected_value alias (secondary output column name) that is used in the query.
     * @return Detected output column name used to extract the expected value (the secondary sensor readout).
     */
    public String getExpectedValueAlias() {
        return expectedValueAlias;
    }

    /**
     * Sets the column name that contains the output sensor expected value.
     * @param expectedValueAlias The output column alias that contains the sensor expected value readout value.
     */
    public void setExpectedValueAlias(String expectedValueAlias) {
        if (Strings.isNullOrEmpty(expectedValueAlias)) {
            throw new NullPointerException("Expected value alias cannot be null or empty.");
        }
        this.expectedValueAlias = expectedValueAlias;
    }

    /**
     * Compares this query divided to components to another similar query. Only static query fragments (the beginning and the ending of the query) are compared.
     * The content of the mutable sql fragments (that calculate the actual_value and expected_value) are not compared, to enable matching similar queries.
     * @param o Other query to compare.
     * @return True when two similar queries match, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FragmentedSqlQuery that = (FragmentedSqlQuery) o;

        return components.equals(that.components);
    }

    /**
     * Returns a hashcode calculated from components, but the hashcode does not include mutable components (the sql fragments that calculate the actual_value and expected_value),
     * in order to enable matching similar queries.
     * @return Hash code of static and non-mutable sql query fragments.
     */
    @Override
    public int hashCode() {
        return components.hashCode();
    }
}
