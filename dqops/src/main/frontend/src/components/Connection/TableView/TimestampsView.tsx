import React, { useEffect } from 'react';
import ColumnSelect from '../../DataQualityChecks/ColumnSelect';
import ActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';
import {
  getTableTimestamps, setUpdatedTablePartitioning,
  updateTableTimestamps
} from '../../../redux/actions/table.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { useParams } from "react-router-dom";
import SectionWrapper from "../../Dashboard/SectionWrapper";
import NumberInput from "../../NumberInput";
import Checkbox from "../../Checkbox";
import { CheckTypes } from "../../../shared/routes";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../../redux/selectors";
import clsx from 'clsx';
import { IRootState } from '../../../redux/reducers';

const TimestampsView = () => {
  const { checkTypes, connection: connectionName, schema: schemaName, table: tableName }: { checkTypes:CheckTypes, connection: string, schema: string, table: string } = useParams();
  const { tablePartitioning, updatingTablePartitioning, isUpdatedTablePartitioning } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const { userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );

  const handleChangeTimestamps = (obj: any) => {
    dispatch(setUpdatedTablePartitioning(checkTypes, firstLevelActiveTab, {
      ...tablePartitioning,
      timestamp_columns: {
        ...tablePartitioning?.timestamp_columns,
        ...obj
      }
    }));
  };

  const handleChangeIncremental = (obj: any) => {
    dispatch(setUpdatedTablePartitioning(checkTypes, firstLevelActiveTab,{
      ...tablePartitioning,
      incremental_time_window: {
        ...tablePartitioning?.incremental_time_window,
        ...obj
      }
    }));
  };

  useEffect(() => {
    dispatch(getTableTimestamps(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName));
  }, [checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    if (!tablePartitioning) return;

    await dispatch(
      updateTableTimestamps(checkTypes, firstLevelActiveTab,connectionName, schemaName, tableName, tablePartitioning)
    );
    dispatch(getTableTimestamps(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName));
  };

  const isDisabled = !isUpdatedTablePartitioning;

  return (
    <div className="py-6 px-8 flex flex-col">
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedTablePartitioning}
        isUpdating={updatingTablePartitioning}
        isDisabled={isDisabled}
      />

      <div className={clsx("mb-4", userProfile.can_manage_data_sources ? "" : "cursor-not-allowed pointer-events-none")}>
        <ColumnSelect
          label="Event timestamp column name for timeliness checks"
          value={tablePartitioning?.timestamp_columns?.event_timestamp_column}
          onChange={(column) =>
            handleChangeTimestamps({
              event_timestamp_column: column
            })
          }
          
        />
      </div>

      <div className={clsx("mb-4", userProfile.can_manage_data_sources ? "" : "cursor-not-allowed pointer-events-none")}>
        <ColumnSelect
          label="Ingestion timestamp column name for timeliness checks"
          value={tablePartitioning?.timestamp_columns?.ingestion_timestamp_column}
          onChange={(column) =>
            handleChangeTimestamps({
              ingestion_timestamp_column: column
            })
          }
        />
      </div>

      <div className={clsx("mb-8", userProfile.can_manage_data_sources ? "" : "cursor-not-allowed pointer-events-none")}>
        <ColumnSelect
          label="Date or datetime column name for partition checks"
          value={tablePartitioning?.timestamp_columns?.partition_by_column}
          onChange={(column) =>
            handleChangeTimestamps({
              partition_by_column: column
            })
          }
          error={
            !tablePartitioning?.timestamp_columns?.partition_by_column
          }
        />
      </div>

      <SectionWrapper className={clsx("mb-8", userProfile.can_manage_data_sources ? "" : "cursor-not-allowed pointer-events-none")} title="Incremental daily partitioned checks time window">
        <div className="flex mb-4">
          <span className="w-80 text-sm">Recent days</span>

          <NumberInput
            className="!text-sm"
            onChange={(value) =>
              handleChangeIncremental({
                daily_partitioning_recent_days: value
              })
            }
            value={tablePartitioning?.incremental_time_window?.daily_partitioning_recent_days}
          />
        </div>

        <div className="flex">
          <span className="w-80 text-sm">Run checks also for today</span>

          <Checkbox
            onChange={(checked) =>
              handleChangeIncremental({
                daily_partitioning_include_today: checked
              })
            }
            checked={tablePartitioning?.incremental_time_window?.daily_partitioning_include_today}
          />
        </div>
      </SectionWrapper>

      <SectionWrapper className={clsx("", userProfile.can_manage_data_sources ? "" : "cursor-not-allowed pointer-events-none")} title="Incremental monthly partitioned checks time window">
        <div className="flex mb-4 text-sm">
          <span className="w-80">Recent months</span>

          <NumberInput
            className="!text-sm"
            onChange={(value) =>
              handleChangeIncremental({
                monthly_partitioning_recent_months: value
              })
            }
            value={tablePartitioning?.incremental_time_window?.monthly_partitioning_recent_months}
          />
        </div>

        <div className="flex text-sm">
          <span className="w-80">Run checks also for current month</span>

          <Checkbox
            className="!text-sm"
            onChange={(checked) =>
              handleChangeIncremental({
                monthly_partitioning_include_current_month: checked
              })
            }
            checked={tablePartitioning?.incremental_time_window?.monthly_partitioning_include_current_month}
          />
        </div>
      </SectionWrapper>
    </div>
  );
};

export default TimestampsView;
