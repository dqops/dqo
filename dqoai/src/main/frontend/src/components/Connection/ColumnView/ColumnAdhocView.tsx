import React, { useEffect, useState } from 'react';
import DataQualityChecks from '../../DataQualityChecks';
import ColumnActionGroup from './ColumnActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { UIAllChecksModel } from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getColumnChecksUi,
  updateColumnCheckUI
} from '../../../redux/actions/column.actions';

interface IAdhocViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
}

const AdhocView = ({
  connectionName,
  schemaName,
  tableName,
  columnName
}: IAdhocViewProps) => {
  const [isUpdated, setIsUpdated] = useState(false);
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();

  const { checksUI, isUpdating } = useSelector(
    (state: IRootState) => state.column
  );
  const dispatch = useActionDispatch();

  useEffect(() => {
    dispatch(
      getColumnChecksUi(connectionName, schemaName, tableName, columnName)
    );
  }, []);

  useEffect(() => {
    setUpdatedChecksUI(checksUI);
  }, [checksUI]);

  const onUpdate = async () => {
    await dispatch(
      updateColumnCheckUI(
        connectionName,
        schemaName,
        tableName,
        columnName,
        updatedChecksUI
      )
    );
    await dispatch(
      getColumnChecksUi(connectionName, schemaName, tableName, columnName)
    );
    setIsUpdated(false);
  };

  const handleChange = (value: UIAllChecksModel) => {
    setUpdatedChecksUI(value);
    setIsUpdated(true);
  };

  return (
    <div>
      <ColumnActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
      />
      <DataQualityChecks checksUI={updatedChecksUI} onChange={handleChange} />
    </div>
  );
};

export default AdhocView;
