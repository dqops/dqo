import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { CheckContainerModel, CheckResultsOverviewDataModel } from '../../api';
import Button from '../../components/Button';
import DataQualityChecks from '../../components/DataQualityChecks';
import SvgIcon from '../../components/SvgIcon';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnDailyMonitoringChecks,
  updateColumnDailyMonitoringChecks
} from '../../redux/actions/column.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';
import { CheckResultOverviewApi } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';

const ColumnDailyChecksView = () => {
  const {
    checkTypes,
    connection: connectionName,
    schema: schemaName,
    table: tableName,
    column: columnName
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
    column: string;
  } = useDecodedParams();

  const { dailyMonitoring, isUpdating, loading } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const [updatedChecksUI, setUpdatedChecksUI] = useState<CheckContainerModel>();
  const [isUpdated, setIsUpdated] = useState(false);
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<
    CheckResultsOverviewDataModel[]
  >([]);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnMonitoringChecksOverview(
      connectionName,
      schemaName,
      tableName,
      columnName,
      'daily'
    ).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    setUpdatedChecksUI(dailyMonitoring);
  }, [dailyMonitoring]);

  useEffect(() => {
    dispatch(
      getColumnDailyMonitoringChecks(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName
      )
    );
  }, [connectionName, schemaName, tableName, columnName]);

  const onUpdate = async () => {
    if (!updatedChecksUI) return;

    await dispatch(
      updateColumnDailyMonitoringChecks(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName,
        updatedChecksUI
      )
    );
    await dispatch(
      getColumnDailyMonitoringChecks(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName,
        false
      )
    );
    setIsUpdated(false);
  };

  const onChangeUI = (ui: CheckContainerModel) => {
    setUpdatedChecksUI(ui);
    setIsUpdated(true);
  };

  return (
    <>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div
          className="flex items-center space-x-2"
          style={{ maxWidth: `calc(100% - 180px)` }}
        >
          <SvgIcon name="column-check" className="w-5 h-5 shrink-0" />
          <div className="text-lg font-semibold truncate">{`Daily monitoring checks ${connectionName}.${schemaName}.${tableName}.${columnName}`}</div>
        </div>
        <Button
          color={isUpdated ? 'primary' : 'secondary'}
          variant="contained"
          label="Save"
          className="w-40"
          onClick={onUpdate}
          loading={isUpdating}
        />
      </div>
      <div>
        <DataQualityChecks
          onUpdate={onUpdate}
          className="max-h-checks-2"
          checksUI={updatedChecksUI}
          onChange={onChangeUI}
          checkResultsOverview={checkResultsOverview}
          getCheckOverview={getCheckOverview}
          loading={loading}
        />
      </div>
    </>
  );
};

export default ColumnDailyChecksView;
