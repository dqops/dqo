import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import ColumnActionGroup from './ColumnActionGroup';
import LabelsView from '../LabelsView';
import {
  getColumnLabels,
  updateColumnLabels
} from '../../../redux/actions/column.actions';

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
  const { labels, isUpdating } = useSelector(
    (state: IRootState) => state.column
  );
  const [updatedLabels, setUpdatedLabels] = useState<string[]>([]);
  const dispatch = useActionDispatch();
  const [isUpdated, setIsUpdated] = useState(false);

  useEffect(() => {
    setUpdatedLabels(labels);
  }, [labels]);

  useEffect(() => {
    dispatch(
      getColumnLabels(connectionName, schemaName, tableName, columnName)
    );
  }, []);

  const onUpdate = async () => {
    await dispatch(
      updateColumnLabels(
        connectionName,
        schemaName,
        tableName,
        columnName,
        updatedLabels
      )
    );
    await dispatch(
      getColumnLabels(connectionName, schemaName, tableName, columnName)
    );
  };

  const handleChange = (value: string[]) => {
    setUpdatedLabels(value);
    setIsUpdated(true);
  };

  return (
    <div>
      <ColumnActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
      />
      <LabelsView labels={updatedLabels} onChange={handleChange} />
    </div>
  );
};

export default ColumnLabelsView;
