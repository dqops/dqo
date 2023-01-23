/*
 * Copyright © 2022 DQO.ai (support@dqo.ai)
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

package ai.dqo.data.errors.services;

import ai.dqo.data.errors.models.ErrorsFragmentFilter;
import ai.dqo.data.errors.snapshot.ErrorsSnapshot;
import ai.dqo.data.errors.snapshot.ErrorsSnapshotFactory;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.utils.datetime.LocalDateTimeTruncateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class ErrorsDeleteServiceImpl implements ErrorsDeleteService {
    private ErrorsSnapshotFactory errorsSnapshotFactory;

    @Autowired
    public ErrorsDeleteServiceImpl(ErrorsSnapshotFactory errorsSnapshotFactory) {
        this.errorsSnapshotFactory = errorsSnapshotFactory;
    }

    /**
     * Deletes the errors from a table, applying specific filters to get the fragment (if necessary).
     * @param filter Filter for the errors fragment that is of interest.
     */
    @Override
    public void deleteSelectedErrorsFragment(ErrorsFragmentFilter filter) {
        Map<String, String> conditions = filter.getColumnConditions();
        ErrorsSnapshot currentSnapshot = this.errorsSnapshotFactory.createSnapshot(
                filter.getTableSearchFilters().getConnectionName(),
                PhysicalTableName.fromSchemaTableFilter(filter.getTableSearchFilters().getSchemaTableName())
        );

        LocalDate startDeletionRange = filter.getDateStart();
        LocalDate endDeletionRange = filter.getDateEnd();
        if (filter.isIgnoreDateDay()) {
            startDeletionRange = LocalDateTimeTruncateUtility.truncateMonth(startDeletionRange);
            endDeletionRange = LocalDateTimeTruncateUtility.truncateMonth(endDeletionRange).plusMonths(1L).minusDays(1L);
        }

        currentSnapshot.markSelectedForDeletion(startDeletionRange, endDeletionRange, conditions);
        currentSnapshot.save();
    }
}
