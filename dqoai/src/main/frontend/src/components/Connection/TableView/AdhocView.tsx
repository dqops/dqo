import React, { useEffect, useState } from 'react';
import DataQualityChecks from '../../DataQualityChecks';
import TableActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { UIAllChecksModel } from '../../../api';
import {
  getTableAdHocChecksUI,
  updateTableAdHocChecksUI
} from '../../../redux/actions/table.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';

interface IAdhocViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const AdhocView = ({
  connectionName,
  schemaName,
  tableName
}: IAdhocViewProps) => {
  const [isUpdated, setIsUpdated] = useState(false);
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();

  const { checksUI, isUpdating } = useSelector(
    (state: IRootState) => state.table
  );
  const dispatch = useActionDispatch();

  useEffect(() => {
    dispatch(getTableAdHocChecksUI(connectionName, schemaName, tableName));
  }, []);

  useEffect(() => {
    setUpdatedChecksUI(checksUI);
  }, [checksUI]);

  const onUpdate = async () => {
    if (!updatedChecksUI) {
      return;
    }
    await dispatch(
      updateTableAdHocChecksUI(
        connectionName,
        schemaName,
        tableName,
        updatedChecksUI
      )
    );
    await dispatch(
      getTableAdHocChecksUI(connectionName, schemaName, tableName)
    );
    setIsUpdated(false);
  };

  const handleChange = (value: UIAllChecksModel) => {
    setUpdatedChecksUI(value);
    setIsUpdated(true);
  };

  return (
    <div>
      <TableActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
      />
      <DataQualityChecks checksUI={updatedChecksUI} onChange={handleChange} />
    </div>
  );
};

export default AdhocView;
