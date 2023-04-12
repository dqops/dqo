import React, { useEffect, useState } from 'react';
import DataQualityChecks from '../../components/DataQualityChecks';
import ColumnActionGroup from './ColumnActionGroup';
import { useSelector } from 'react-redux';
import { CheckResultsOverviewDataModel, UICheckContainerModel } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnChecksUi,
  setUpdatedChecksUi,
  updateColumnCheckUI
} from '../../redux/actions/column.actions';
import { CheckResultOverviewApi } from "../../services/apiClient";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../redux/selectors";
import { CheckTypes } from "../../shared/routes";
import { useParams } from "react-router-dom";

interface IProfilingViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
}

const ProfilingView = ({
  connectionName,
  schemaName,
  tableName,
  columnName
}: IProfilingViewProps) => {
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
  const { columnBasic, checksUI, isUpdating, isUpdatedChecksUi, loading } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnProfilingChecksOverview(connectionName, schemaName, tableName, columnName).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    if (
      !checksUI ||
      columnBasic?.connection_name !== connectionName ||
      columnBasic?.table?.schema_name !== schemaName ||
      columnBasic?.table?.table_name !== tableName ||
      columnBasic.column_name !== columnName
    ) {
      dispatch(
        getColumnChecksUi(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, columnName)
      );
    }
  }, [checkTypes, firstLevelActiveTab, connectionName, schemaName, columnName, tableName, columnBasic]);

  const onUpdate = async () => {
    if (!checksUI) {
      return;
    }
    await dispatch(
      updateColumnCheckUI(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName,
        checksUI
      )
    );
    await dispatch(
      getColumnChecksUi(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, columnName)
    );
  };

  const handleChange = (value: UICheckContainerModel) => {
    dispatch(setUpdatedChecksUi(checkTypes, firstLevelActiveTab, value));
  };

  return (
    <div>
      <ColumnActionGroup
        shouldDelete={false}
        onUpdate={onUpdate}
        isUpdated={isUpdatedChecksUi}
        isUpdating={isUpdating}
      />
      <DataQualityChecks
        onUpdate={onUpdate}
        checksUI={checksUI}
        onChange={handleChange}
        checkResultsOverview={checkResultsOverview}
        getCheckOverview={getCheckOverview}
        loading={loading}
      />
    </div>
  );
};

export default ProfilingView;
