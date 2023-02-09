import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import ColumnActionGroup from './ColumnActionGroup';
import LabelsView from '../../components/Connection/LabelsView';
import {
  getColumnLabels,
  setUpdatedLabels,
  updateColumnLabels
} from '../../redux/actions/column.actions';

interface IColumnLabelsViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
}

const ColumnLabelsView = ({
  connectionName,
  schemaName,
  tableName,
  columnName
}: IColumnLabelsViewProps) => {
  const { labels, isUpdating, isUpdatedLabels, columnBasic } = useSelector(
    (state: IRootState) => state.column
  );
  const dispatch = useActionDispatch();

  useEffect(() => {
    if (
      !labels?.length ||
      columnBasic?.connection_name !== connectionName ||
      columnBasic?.table?.schema_name !== schemaName ||
      columnBasic?.table?.table_name !== tableName ||
      columnBasic.column_name !== columnName
    ) {
      dispatch(
        getColumnLabels(connectionName, schemaName, tableName, columnName)
      );
    }
  }, [labels, connectionName, schemaName, columnName, tableName, columnBasic]);

  const onUpdate = async () => {
    await dispatch(
      updateColumnLabels(
        connectionName,
        schemaName,
        tableName,
        columnName,
        labels
      )
    );
    await dispatch(
      getColumnLabels(connectionName, schemaName, tableName, columnName)
    );
  };

  const handleChange = (value: string[]) => {
    dispatch(setUpdatedLabels(value));
  };

  return (
    <div>
      <ColumnActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedLabels}
        isUpdating={isUpdating}
      />
      <LabelsView labels={labels} onChange={handleChange} />
    </div>
  );
};

export default ColumnLabelsView;
