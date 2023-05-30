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
package ai.dqo.metadata.search;

import ai.dqo.metadata.search.pattern.SearchPattern;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Hierarchy node search filters.
 */
public class RuleDefinitionSearchFilters {
    private String ruleName;
    private Boolean enabled = true;

    @JsonIgnore
    private SearchPattern ruleNameSearchPattern;

    /**
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public RuleDefinitionSearchFiltersVisitor createSearchFilterVisitor() {
        return new RuleDefinitionSearchFiltersVisitor(this);
    }

    /**
     * Gets a rule name search pattern.
     * @return Rule name search pattern.
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * Sets a rule name search pattern.
     * @param ruleName Sensor name search pattern.
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    /**
     * Sets the enabled search criteria. null - search is ignored on the enabled/disabled flags,
     * true - only enabled (not explicitly disabled) nodes are returned (disabled connection or table stops search for nested elements),
     * false - only nodes that are disabled are returned.
     * @return Enabled search flag.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets an enabled (disabled) search flag.
     * @param enabled Enabled search flag.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns the {@link SearchPattern} related to <code>ruleName</code>.
     * Lazy getter, parses <code>ruleName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>ruleName</code>.
     */
    public SearchPattern getRuleNameSearchPattern() {
        if (ruleNameSearchPattern == null && ruleName != null) {
            ruleNameSearchPattern = SearchPattern.create(false, ruleName);
        }

        return ruleNameSearchPattern;
    }
}
