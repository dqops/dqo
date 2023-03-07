import React, { useEffect, useState } from 'react';
import {
  TimestampColumnsSpec
} from '../../../api';
import ColumnSelect from '../../DataQualityChecks/ColumnSelect';
import ActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  getTableBasic,
  updateTableBasic
} from '../../../redux/actions/table.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { useParams } from "react-router-dom";

const TimestampsView = () => {
  const { connection: connectionName, schema: schemaName, table: tableName }: { connection: string, schema: string, table: string } = useParams();
  const [isUpdated, setIsUpdated] = useState(false);
  const [columnsSpec, setColumnsSpec] = useState<TimestampColumnsSpec>();
  const { tableBasic, isUpdating } = useSelector(
    (state: IRootState) => state.table
  );
  const dispatch = useActionDispatch();

  const handleChange = (obj: any) => {
    setColumnsSpec({
      ...columnsSpec,
      ...obj
    });
    setIsUpdated(true);
  };

  useEffect(() => {
    setColumnsSpec(tableBasic?.timestamp_columns);
  }, [tableBasic?.timestamp_columns]);

  useEffect(() => {
    dispatch(getTableBasic(connectionName, schemaName, tableName));
  }, [connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    await dispatch(
      updateTableBasic(connectionName, schemaName, tableName, {
        ...tableBasic,
        timestamp_columns: columnsSpec
      })
    );
    await dispatch(getTableBasic(connectionName, schemaName, tableName));
    setIsUpdated(false);
  };

  const isDisabled = !isUpdated;

  return (
    <div className="py-4 px-8 flex flex-col">
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
        isDisabled={isDisabled}
      />

      <div className="mb-4">
        <ColumnSelect
          label="Event timestamp column name for timeliness checks"
          value={columnsSpec?.event_timestamp_column}
          onChange={(column) =>
            handleChange({
              event_timestamp_column: column
            })
          }
        />
      </div>

      <div className="mb-4">
        <ColumnSelect
          label="Ingestion timestamp column name for timeliness checks"
          value={columnsSpec?.ingestion_timestamp_column}
          onChange={(column) =>
            handleChange({
              ingestion_timestamp_column: column
            })
          }
        />
      </div>

      <div className="mb-4">
        <ColumnSelect
          label="DATE or DATETIME column name for time partition checks"
          value={columnsSpec?.partition_by_column}
          onChange={(column) =>
            handleChange({
              partition_by_column: column
            })
          }
          error={
            !columnsSpec?.partition_by_column
          }
        />
      </div>      
    </div>
  );
};

export default TimestampsView;
