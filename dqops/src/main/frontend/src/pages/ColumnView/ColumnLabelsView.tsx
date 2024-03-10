import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import LabelsView from '../../components/Connection/LabelsView';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnLabels,
  setUpdatedLabels,
  updateColumnLabels,
} from '../../redux/actions/column.actions';
import { setIsUpdatedLabels } from "../../redux/actions/connection.actions";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../redux/selectors";
import { CheckTypes } from "../../shared/routes";
import ColumnActionGroup from './ColumnActionGroup';
import { useDecodedParams } from '../../utils';

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
  const { checkTypes }: { checkTypes: CheckTypes } = useDecodedParams();
  const { labels, isUpdating, isUpdatedLabels } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    dispatch(
      getColumnLabels(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, columnName)
    );
  }, [checkTypes, firstLevelActiveTab, connectionName, schemaName, columnName, tableName]);

  const onUpdate = async () => {
    await dispatch(
      updateColumnLabels(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName,
        labels
      )
    );
    await dispatch(
      getColumnLabels(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, columnName, false)
    );
    dispatch(setIsUpdatedLabels(checkTypes, firstLevelActiveTab, false));
  };

  const handleChange = (value: string[]) => {
    dispatch(setUpdatedLabels(checkTypes, firstLevelActiveTab, value));
    dispatch(setIsUpdatedLabels(checkTypes, firstLevelActiveTab, true));
  };

  return (
    <div className="px-4">
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
