import React, { useEffect, useState } from 'react';
import {
  TimestampColumnsSpec,
  TimestampColumnsSpecPartitionedChecksTimestampSourceEnum
} from '../../../api';
import Select from '../../Select';
import ColumnSelect from '../../DataQualityChecks/ColumnSelect';
import ActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  getTableBasic,
  updateTableBasic
} from '../../../redux/actions/table.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';

interface TimestampsViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const partitionedChecksOptions = [
  {
    label: 'Event Timestamp Column (event_timestamp)',
    value:
      TimestampColumnsSpecPartitionedChecksTimestampSourceEnum.event_timestamp
  },
  {
    label: 'Ingestion Timestamp Column (ingestion_timestamp)',
    value:
      TimestampColumnsSpecPartitionedChecksTimestampSourceEnum.ingestion_timestamp
  }
];

const TimestampsView = ({
  connectionName,
  schemaName,
  tableName
}: TimestampsViewProps) => {
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
  }, []);

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

  const isDisabled =
    !isUpdated ||
    (columnsSpec?.partitioned_checks_timestamp_source ===
      TimestampColumnsSpecPartitionedChecksTimestampSourceEnum.ingestion_timestamp &&
      !columnsSpec?.ingestion_timestamp_column) ||
    (columnsSpec?.partitioned_checks_timestamp_source ===
      TimestampColumnsSpecPartitionedChecksTimestampSourceEnum.event_timestamp &&
      !columnsSpec?.event_timestamp_column);

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
          label="Event Timestamp Column"
          value={columnsSpec?.event_timestamp_column}
          onChange={(column) =>
            handleChange({
              event_timestamp_column: column
            })
          }
          error={
            columnsSpec?.partitioned_checks_timestamp_source ===
              TimestampColumnsSpecPartitionedChecksTimestampSourceEnum.event_timestamp &&
            !columnsSpec?.event_timestamp_column
          }
        />
      </div>

      <div className="mb-4">
        <ColumnSelect
          label="Ingestion Timestamp Column"
          value={columnsSpec?.ingestion_timestamp_column}
          onChange={(column) =>
            handleChange({
              ingestion_timestamp_column: column
            })
          }
          error={
            columnsSpec?.partitioned_checks_timestamp_source ===
              TimestampColumnsSpecPartitionedChecksTimestampSourceEnum.ingestion_timestamp &&
            !columnsSpec?.ingestion_timestamp_column
          }
        />
      </div>

      <div className="mb-4">
        <Select
          label="Partitioned Checks Timestamp Source"
          options={partitionedChecksOptions}
          value={columnsSpec?.partitioned_checks_timestamp_source}
          onChange={(column) =>
            handleChange({
              partitioned_checks_timestamp_source: column
            })
          }
        />
      </div>
    </div>
  );
};

export default TimestampsView;
