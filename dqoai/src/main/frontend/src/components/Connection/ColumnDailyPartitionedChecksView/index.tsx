import React, { useEffect, useState } from 'react';
import SvgIcon from '../../SvgIcon';
import DataQualityChecks from '../../DataQualityChecks';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { UIAllChecksModel } from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import Button from '../../Button';
import {
  getColumnDailyPartitionedChecks,
  updateColumnDailyPartitionedChecks
} from '../../../redux/actions/column.actions';

interface IColumnDailyPartitionedChecksViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
}

const ColumnDailyPartitionedChecksView = ({
  connectionName,
  schemaName,
  tableName,
  columnName
}: IColumnDailyPartitionedChecksViewProps) => {
  const { dailyPartitionedChecks, isUpdating } = useSelector(
    (state: IRootState) => state.column
  );
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  const [isUpdated, setIsUpdated] = useState(false);
  const dispatch = useActionDispatch();

  useEffect(() => {
    setUpdatedChecksUI(dailyPartitionedChecks);
  }, [dailyPartitionedChecks]);

  useEffect(() => {
    dispatch(
      getColumnDailyPartitionedChecks(
        connectionName,
        schemaName,
        tableName,
        columnName
      )
    );
  }, [connectionName, schemaName, tableName, columnName]);

  const onUpdate = async () => {
    if (!updatedChecksUI) return;

    await dispatch(
      updateColumnDailyPartitionedChecks(
        connectionName,
        schemaName,
        tableName,
        columnName,
        updatedChecksUI
      )
    );
    await dispatch(
      getColumnDailyPartitionedChecks(
        connectionName,
        schemaName,
        tableName,
        columnName
      )
    );
    setIsUpdated(false);
  };

  const onChangeUI = (ui: UIAllChecksModel) => {
    setUpdatedChecksUI(ui);
    setIsUpdated(true);
  };

  return (
    <div className="">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
          <div className="text-xl font-semibold">{`Daily Partitioned Checks for ${connectionName}.${schemaName}.${tableName}.${columnName}`}</div>
        </div>
        <Button
          color={isUpdated ? 'primary' : 'secondary'}
          variant="contained"
          label="Save"
          className="w-40"
          onClick={onUpdate}
          loading={isUpdating}
        />
      </div>
      <div>
        <DataQualityChecks checksUI={updatedChecksUI} onChange={onChangeUI} />
      </div>
    </div>
  );
};

export default ColumnDailyPartitionedChecksView;
