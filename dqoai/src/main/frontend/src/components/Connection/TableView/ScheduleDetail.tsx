import React, { useEffect } from 'react';
import ActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableSchedule,
  setUpdatedSchedule,
  updateTableSchedule
} from '../../../redux/actions/table.actions';
import { useParams } from "react-router-dom";
import ScheduleView from "../../ScheduleView";

const ScheduleDetail = () => {
  const { connection: connectionName, schema: schemaName, table: tableName }: { connection: string, schema: string, table: string } = useParams();

  const { schedule, isUpdating, isUpdatedSchedule, tableBasic } = useSelector(
    (state: IRootState) => state.table
  );
  const dispatch = useActionDispatch();

  useEffect(() => {
    if (
      !schedule ||
      tableBasic?.connection_name !== connectionName ||
      tableBasic?.target?.schema_name !== schemaName ||
      tableBasic?.target?.table_name !== tableName
    ) {
      dispatch(getTableSchedule(connectionName, schemaName, tableName));
    }
  }, [connectionName, schemaName, tableName, tableBasic]);

  const handleChange = (obj: any) => {
    dispatch(
      setUpdatedSchedule({
        ...schedule,
        ...obj
      })
    );
  };

  const onUpdate = async () => {
    if (!schedule) {
      return;
    }
    await dispatch(
      updateTableSchedule(connectionName, schemaName, tableName, schedule)
    );
    await dispatch(getTableSchedule(connectionName, schemaName, tableName));
  };
  

  return (
    <div className="p-4">
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedSchedule}
        isUpdating={isUpdating}
      />
      <ScheduleView handleChange={handleChange} schedule={schedule} />
    </div>
  );
};

export default ScheduleDetail;
