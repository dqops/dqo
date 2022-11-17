import React, { useEffect, useState } from 'react';
import SvgIcon from '../../SvgIcon';
import DataQualityChecks from '../../DataQualityChecks';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { UIAllChecksModel } from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableChecksUI,
  updateTableChecksUI
} from '../../../redux/actions/table.actions';
import Button from '../../Button';

interface IChecksViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const ChecksView = ({
  connectionName,
  schemaName,
  tableName
}: IChecksViewProps) => {
  const { checksUI, isUpdating } = useSelector(
    (state: IRootState) => state.table
  );
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  const dispatch = useActionDispatch();

  useEffect(() => {
    setUpdatedChecksUI(checksUI);
  }, [checksUI]);

  useEffect(() => {
    dispatch(getTableChecksUI(connectionName, schemaName, tableName));
  }, [connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    await dispatch(
      updateTableChecksUI(
        connectionName,
        schemaName,
        tableName,
        updatedChecksUI
      )
    );
    await dispatch(getTableChecksUI(connectionName, schemaName, tableName));
  };

  return (
    <div className="">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
          <div className="text-xl font-semibold">{`Data quality checks for ${connectionName}.${schemaName}.${tableName}`}</div>
        </div>
        <Button
          color="primary"
          variant="contained"
          label="Save"
          className="w-40"
          onClick={onUpdate}
          loading={isUpdating}
        />
      </div>
      <div>
        <DataQualityChecks
          checksUI={updatedChecksUI}
          onChange={setUpdatedChecksUI}
        />
      </div>
    </div>
  );
};

export default ChecksView;
