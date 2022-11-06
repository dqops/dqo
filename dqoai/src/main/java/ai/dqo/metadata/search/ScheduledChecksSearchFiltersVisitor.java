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

import ai.dqo.checks.AbstractCheckDeprecatedSpec;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.traversal.TreeNodeTraversalResult;
import ai.dqo.sensors.AbstractSensorParametersSpec;

import java.util.Objects;

/**
 * Visitor for {@link ScheduleRootsSearchFilters} that finds any node (connection, table, column, check) that has a given filter configured.
 */
public class ScheduledChecksSearchFiltersVisitor extends AbstractSearchVisitor {
    private final ScheduledChecksSearchFilters filters;

    /**
     * Creates a visitor for the given filters.
     * @param filters Check search filters.
     */
    public ScheduledChecksSearchFiltersVisitor(ScheduledChecksSearchFilters filters) {
        this.filters = filters;
    }

    /**
     * Accept a connection spec.
     *
     * @param connectionSpec Connection spec.
     * @param foundNodes     Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionSpec connectionSpec, SearchParameterObject foundNodes) {
        RecurringScheduleSpec connectionSchedule = connectionSpec.getSchedule();
        assert this.filters.getSchedule() != null;

        if (connectionSchedule != null) {
            if (!Objects.equals(connectionSchedule, this.filters.getSchedule())) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;  // this connection has a different schedule
            }
        }

        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }


    /**
     * Accepts a table specification.
     *
     * @param tableSpec  Table specification.
     * @param foundNodes Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableSpec tableSpec, SearchParameterObject foundNodes) {
        Boolean enabledFilter = this.filters.getEnabled();
        if (enabledFilter != null) {
            boolean tableIsEnabled = !tableSpec.isDisabled();
            if (enabledFilter != tableIsEnabled) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        RecurringScheduleSpec tableSchedule = tableSpec.getScheduleOverride();
        assert this.filters.getSchedule() != null;

        if (tableSchedule != null) {
            if (!Objects.equals(tableSchedule, this.filters.getSchedule())) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;  // this table has a different schedule, no longer collecting nested checks
            }
        }

        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a column specification.
     *
     * @param columnSpec Column specification.
     * @param foundNodes Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnSpec columnSpec, SearchParameterObject foundNodes) {
        Boolean enabledFilter = this.filters.getEnabled();
        if (enabledFilter != null) {
            boolean columnIsEnabled = !columnSpec.isDisabled();
            if (enabledFilter != columnIsEnabled) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        RecurringScheduleSpec columnSchedule = columnSpec.getScheduleOverride();
        assert this.filters.getSchedule() != null;

        if (columnSchedule != null) {
            if (!Objects.equals(columnSchedule, this.filters.getSchedule())) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;  // this column has a different schedule, no longer collecting nested checks
            }
        }

        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts any check specification.
     *
     * @param abstractCheckSpec Data quality check specification (any).
     * @param foundNodes        Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractCheckDeprecatedSpec abstractCheckSpec, SearchParameterObject foundNodes) {
        Boolean enabledFilter = this.filters.getEnabled();
        AbstractSensorParametersSpec sensorParameters = abstractCheckSpec.getSensorParameters();

        if (enabledFilter != null) {
            boolean checkIsEnabled = !sensorParameters.isDisabled();
            if (enabledFilter != checkIsEnabled) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        RecurringScheduleSpec checkSchedule = abstractCheckSpec.getScheduleOverride();
        assert this.filters.getSchedule() != null;

        if (checkSchedule != null) {
            if (!Objects.equals(checkSchedule, this.filters.getSchedule())) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;  // do not add to the results, this is the deepest node to traverse
            }
        }

        foundNodes.getNodes().add(abstractCheckSpec);

        return TreeNodeTraversalResult.SKIP_CHILDREN; // no need to traverse deeper
    }
}
