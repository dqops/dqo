import React, { useEffect, useState } from 'react';
import DataQualityChecks from '../../DataQualityChecks';
import TableActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { CheckResultsOverviewDataModel, UICheckContainerModel } from '../../../api';
import {
  getTableProfilingChecksUI,
  setUpdatedChecksUi,
  updateTableProfilingChecksUI
} from '../../../redux/actions/table.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { CheckResultOverviewApi } from '../../../services/apiClient';
import { useParams } from "react-router-dom";
import { getFirstLevelActiveTab } from "../../../redux/selectors";
import { CheckTypes } from "../../../shared/routes";

const ProfilingView = () => {
  const { checkTypes, connection: connectionName, schema: schemaName, table: tableName }: { checkTypes: CheckTypes, connection: string, schema: string, table: string } = useParams();
  const { checksUI, loading, isUpdating, isUpdatedChecksUi, tableBasic } = useSelector(
    (state: IRootState) => state.table
  );
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    if (
      !checksUI ||
      tableBasic?.connection_name !== connectionName ||
      tableBasic?.target?.schema_name !== schemaName ||
      tableBasic?.target?.table_name !== tableName
    ) {
      dispatch(getTableProfilingChecksUI(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName));
    }
  }, [connectionName, schemaName, tableName, tableBasic]);

  const onUpdate = async () => {
    if (!checksUI) {
      return;
    }
    await dispatch(
      updateTableProfilingChecksUI(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, checksUI)
    );
    await dispatch(
      getTableProfilingChecksUI(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName)
    );
  };

  const handleChange = (value: UICheckContainerModel) => {
    dispatch(setUpdatedChecksUi(checkTypes, firstLevelActiveTab, value));
  };

  const getCheckOverview = () => {
    CheckResultOverviewApi.getTableProfilingChecksOverview(connectionName, schemaName, tableName).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  return (
    <div className="flex-grow min-h-0 flex flex-col">
      <TableActionGroup
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
