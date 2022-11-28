import React, { useEffect, useState } from 'react';
import SvgIcon from '../../SvgIcon';
import DataQualityChecks from '../../DataQualityChecks';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { UIAllChecksModel } from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableDailyPartitionedChecks,
  updateTableDailyPartitionedChecks
} from '../../../redux/actions/table.actions';
import Button from '../../Button';

interface IDailyPartitionedChecksViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const DailyPartitionedChecksView = ({
  connectionName,
  schemaName,
  tableName
}: IDailyPartitionedChecksViewProps) => {
  const { dailyPartitionedChecks, isUpdating } = useSelector(
    (state: IRootState) => state.table
  );
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  const [isUpdated, setIsUpdated] = useState(false);
  const dispatch = useActionDispatch();

  useEffect(() => {
    setUpdatedChecksUI(dailyPartitionedChecks);
  }, [dailyPartitionedChecks]);

  useEffect(() => {
    dispatch(
      getTableDailyPartitionedChecks(connectionName, schemaName, tableName)
    );
  }, [connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    if (!updatedChecksUI) return;

    await dispatch(
      updateTableDailyPartitionedChecks(
        connectionName,
        schemaName,
        tableName,
        updatedChecksUI
      )
    );
    await dispatch(
      getTableDailyPartitionedChecks(connectionName, schemaName, tableName)
    );
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
          <div className="text-xl font-semibold">{`Daily Partitioned checks for ${connectionName}.${schemaName}.${tableName}`}</div>
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

export default DailyPartitionedChecksView;
