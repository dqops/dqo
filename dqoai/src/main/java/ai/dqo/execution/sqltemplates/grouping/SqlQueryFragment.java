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

import java.util.Objects;

/**
 * A single fragment (component) of an SQL query, for a query that was analyzed to detect common parts and sensor specific parts.
 */
public class SqlQueryFragment {
    private SqlQueryFragmentType fragmentType;
    private String text;

    /**
     * Creates an sql fragment.
     * @param fragmentType Sql fragment type (the type of an SQL fragment).
     * @param text The text of the sql fragment.
     */
    public SqlQueryFragment(SqlQueryFragmentType fragmentType, String text) {
        this.fragmentType = fragmentType;
        this.text = text;
    }

    /**
     * Returns the SQL fragment component type.
     * @return SQL query component type.
     */
    public SqlQueryFragmentType getFragmentType() {
        return fragmentType;
    }

    /**
     * Returns the content of the SQL query fragment for this component.
     * @return The SQL fragment of the query.
     */
    public String getText() {
        return text;
    }

    /**
     * Compares this object to another object.
     * When the other object is also {@link SqlQueryFragment} and they are {@link SqlQueryFragmentType#STATIC_FRAGMENT}, we are also comparing the static text (the query fragment).
     * @param o Other object to compare.
     * @return True when they are equal (excluding the sql text for actual and expected value calculation).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlQueryFragment that = (SqlQueryFragment) o;

        if (fragmentType != that.fragmentType) return false;
        if (fragmentType == SqlQueryFragmentType.ACTUAL_VALUE ||
                fragmentType == SqlQueryFragmentType.EXPECTED_VALUE) return true; // we are not comparing the content of the text for actual_value and expected_value pieces, because they can differ
        return Objects.equals(text, that.text);
    }

    /**
     * Calculates a hash code. The hash code is also calculated from the static SQL fragment, but only when the type of the component is a static sql.
     * @return Hashcode.
     */
    @Override
    public int hashCode() {
        int result = fragmentType != null ? fragmentType.hashCode() : 0;
        if (fragmentType == SqlQueryFragmentType.STATIC_FRAGMENT) {
            // including the SQL text in the hash code for static fragments only, we do not calculate hash codes for the content that can change
            result = 31 * result + (text != null ? text.hashCode() : 0);
        }
        return result;
    }
}
