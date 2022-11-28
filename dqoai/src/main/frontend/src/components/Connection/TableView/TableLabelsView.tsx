import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  getTableLabels,
  updateTableLabels
} from '../../../redux/actions/table.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import ActionGroup from './ActionGroup';
import LabelsView from '../LabelsView';

interface ITableLabelsViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const TableLabelsView = ({
  connectionName,
  schemaName,
  tableName
}: ITableLabelsViewProps) => {
  const { labels, isUpdating } = useSelector(
    (state: IRootState) => state.table
  );
  const [updatedLabels, setUpdatedLabels] = useState<string[]>([]);
  const dispatch = useActionDispatch();
  const [isUpdated, setIsUpdated] = useState(false);

  useEffect(() => {
    setUpdatedLabels(labels);
  }, [labels]);

  useEffect(() => {
    dispatch(getTableLabels(connectionName, schemaName, tableName));
  }, []);

  const onUpdate = async () => {
    await dispatch(
      updateTableLabels(connectionName, schemaName, tableName, updatedLabels)
    );
    await dispatch(getTableLabels(connectionName, schemaName, tableName));
  };

  const handleChange = (value: string[]) => {
    setUpdatedLabels(value);
    setIsUpdated(true);
  };

  return (
    <div>
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
      />
      <LabelsView labels={updatedLabels} onChange={handleChange} />
    </div>
  );
};

export default TableLabelsView;
