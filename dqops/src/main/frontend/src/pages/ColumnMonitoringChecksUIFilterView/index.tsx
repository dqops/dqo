import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { CheckContainerModel, CheckResultsOverviewDataModel } from '../../api';
import Button from '../../components/Button';
import DataQualityChecks from '../../components/DataQualityChecks';
import SvgIcon from '../../components/SvgIcon';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnMonitoringChecksModelFilter,
  setColumnUpdatedMonitoringChecksModelFilter
} from '../../redux/actions/column.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';
import {
  CheckResultOverviewApi,
  ColumnApiClient
} from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';

const ColumnMonitoringChecksUIFilterView = () => {
  const {
    checkTypes,
    connection: connectionName,
    schema: schemaName,
    table: tableName,
    column: columnName,
    timePartitioned,
    category,
    checkName
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
    column: string;
    timePartitioned: 'daily' | 'monthly';
    category: string;
    checkName: string;
  } = useDecodedParams();
  const { monitoringUIFilter, isUpdatedMonitoringUIFilter, loading } =
    useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<
    CheckResultsOverviewDataModel[]
  >([]);
  const [isUpdating, setIsUpdating] = useState(false);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnMonitoringChecksOverview(
      connectionName,
      schemaName,
      tableName,
      columnName,
      timePartitioned,
      category,
      checkName
    ).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };
  const onUpdate = async () => {
    setIsUpdating(true);
    await ColumnApiClient.updateColumnMonitoringChecksModel(
      connectionName,
      schemaName,
      tableName,
      columnName,
      timePartitioned,
      monitoringUIFilter
    );
    await dispatch(
      getColumnMonitoringChecksModelFilter(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName,
        timePartitioned,
        category,
        checkName,
        false
      )
    );
    setIsUpdating(false);
  };

  useEffect(() => {
    dispatch(
      getColumnMonitoringChecksModelFilter(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName,
        timePartitioned,
        category,
        checkName
      )
    );
  }, [
    checkTypes,
    firstLevelActiveTab,
    connectionName,
    schemaName,
    tableName,
    columnName,
    category,
    checkName
  ]);

  const onChange = (ui: CheckContainerModel) => {
    dispatch(
      setColumnUpdatedMonitoringChecksModelFilter(
        checkTypes,
        firstLevelActiveTab,
        ui
      )
    );
  };

  return (
    <>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div
          className="flex items-center space-x-2"
          style={{ maxWidth: `calc(100% - 180px)` }}
        >
          <SvgIcon name="search" className="w-5 h-5 shrink-0" />
          <div className="text-lg font-semibold truncate">{`${connectionName}.${schemaName}.${tableName}.${columnName}.checks.${category} - ${checkName}`}</div>
        </div>
        <Button
          color={isUpdatedMonitoringUIFilter ? 'primary' : 'secondary'}
          variant="contained"
          label="Save"
          className="ml-auto w-40"
          onClick={onUpdate}
          loading={isUpdating}
        />
      </div>
      <div>
        <DataQualityChecks
          onUpdate={() => {}}
          className="max-h-checks-1"
          checksUI={monitoringUIFilter}
          onChange={onChange}
          checkResultsOverview={checkResultsOverview}
          getCheckOverview={getCheckOverview}
          loading={loading}
          isFiltered={true}
        />
      </div>
    </>
  );
};

export default ColumnMonitoringChecksUIFilterView;
