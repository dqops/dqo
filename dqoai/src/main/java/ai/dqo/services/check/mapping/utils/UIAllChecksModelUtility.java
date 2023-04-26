/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.services.check.mapping.utils;

import ai.dqo.checks.CheckTarget;
import ai.dqo.services.check.mapping.models.UIAllChecksModel;

import java.util.List;
import java.util.Map;

/**
 * Utility class for manipulating {@link UIAllChecksModel}.
 */
public class UIAllChecksModelUtility {
    /**
     * Creates a {@link UIAllChecksModel} based on <code>sourceModel</code>, that is pruned according to the columns mapping.
     * @param tablesToColumnsMapping Table name to column name mapping. Doesn't consider name patterns. If check in <code>sourceModel</code> is table level, only the key-set is considered.
     * @param sourceModel            {@link UIAllChecksModel} that will be pruned.
     * @return New {@link UIAllChecksModel} with tables and columns filtered.
     */
    public static UIAllChecksModel selectConcreteTargets(Map<String, List<String>> tablesToColumnsMapping,
                                                         UIAllChecksModel sourceModel) {
        CheckTarget checkTargetFromTable = sourceModel.getTableChecksModel() != null
                ? sourceModel.getTableChecksModel().getCheckTarget()
                : null;
        CheckTarget checkTargetFromColumns = sourceModel.getColumnChecksModel() != null
                ? sourceModel.getColumnChecksModel().getCheckTarget()
                : null;
        CheckTarget checkTarget = checkTargetFromTable != null ? checkTargetFromTable : checkTargetFromColumns;

        if (checkTarget == null) {
            // Empty model.
            return sourceModel.;
        }
    }
}
