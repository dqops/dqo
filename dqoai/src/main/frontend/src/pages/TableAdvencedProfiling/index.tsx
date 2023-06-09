import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import {
  CheckResultsOverviewDataModel,
  UICheckContainerModel
} from '../../api';
import TableActionGroup from '../../components/Connection/TableView/TableActionGroup';
import DataQualityChecks from '../../components/DataQualityChecks';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getTableProfilingChecksUI,
  updateTableProfilingChecksUI,
  setUpdatedChecksUi
} from '../../redux/actions/table.actions';
import {
  getFirstLevelState,
  getFirstLevelActiveTab
} from '../../redux/selectors';
import { CheckResultOverviewApi } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';

const TableAdvancedProfiling = () => {
  const {
    checkTypes,
    connection: connectionName,
    schema: schemaName,
    table: tableName
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
  } = useParams();
  const { checksUI, loading, isUpdating, isUpdatedChecksUi, tableBasic } =
    useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<
    CheckResultsOverviewDataModel[]
  >([]);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    dispatch(
      getTableProfilingChecksUI(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName
      )
    );
  }, [
    checkTypes,
    firstLevelActiveTab,
    connectionName,
    schemaName,
    tableName,
    tableBasic
  ]);

  const onUpdate = async () => {
    if (!checksUI) {
      return;
    }
    await dispatch(
      updateTableProfilingChecksUI(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        checksUI
      )
    );
    await dispatch(
      getTableProfilingChecksUI(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        false
      )
    );
  };

  const handleChange = (value: UICheckContainerModel) => {
    dispatch(setUpdatedChecksUi(checkTypes, firstLevelActiveTab, value));
  };

  const getCheckOverview = () => {
    CheckResultOverviewApi.getTableProfilingChecksOverview(
      connectionName,
      schemaName,
      tableName
    ).then((res) => {
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

export default TableAdvancedProfiling;
