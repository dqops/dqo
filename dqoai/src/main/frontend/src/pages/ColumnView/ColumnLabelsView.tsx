import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import ColumnActionGroup from './ColumnActionGroup';
import LabelsView from '../../components/Connection/LabelsView';
import {
  getColumnLabels,
  setUpdatedLabels,
  updateColumnLabels
} from '../../redux/actions/column.actions';
import { getFirstLevelActiveTab, getFirstLevelState } from "../../redux/selectors";
import { CheckTypes } from "../../shared/routes";
import { useParams } from "react-router-dom";

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
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
  const { labels, isUpdating, isUpdatedLabels, columnBasic } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    if (
      !labels?.length ||
      columnBasic?.connection_name !== connectionName ||
      columnBasic?.table?.schema_name !== schemaName ||
      columnBasic?.table?.table_name !== tableName ||
      columnBasic.column_name !== columnName
    ) {
      dispatch(
        getColumnLabels(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, columnName)
      );
    }
  }, [checkTypes, firstLevelActiveTab, labels, connectionName, schemaName, columnName, tableName, columnBasic]);

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
      getColumnLabels(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, columnName)
    );
  };

  const handleChange = (value: string[]) => {
    dispatch(setUpdatedLabels(checkTypes, firstLevelActiveTab, value));
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
