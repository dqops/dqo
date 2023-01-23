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

package ai.dqo.data.readouts.services;

import ai.dqo.data.readouts.models.SensorReadoutsFragmentFilter;
import ai.dqo.data.readouts.snapshot.SensorReadoutsSnapshot;
import ai.dqo.data.readouts.snapshot.SensorReadoutsSnapshotFactory;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.utils.datetime.LocalDateTimeTruncateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class SensorReadoutsDeleteServiceImpl implements SensorReadoutsDeleteService {
    private SensorReadoutsSnapshotFactory sensorReadoutsSnapshotFactory;

    @Autowired
    public SensorReadoutsDeleteServiceImpl(SensorReadoutsSnapshotFactory sensorReadoutsSnapshotFactory) {
        this.sensorReadoutsSnapshotFactory = sensorReadoutsSnapshotFactory;
    }

    /**
     * Deletes the readouts from a table, applying specific filters to get the fragment (if necessary).
     * @param filter Filter for the readouts fragment that is of interest.
     */
    @Override
    public void deleteSelectedSensorReadoutsFragment(SensorReadoutsFragmentFilter filter) {
        Map<String, String> conditions = filter.getColumnConditions();

        SensorReadoutsSnapshot currentSnapshot = this.sensorReadoutsSnapshotFactory.createSnapshot(
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
