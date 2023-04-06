import React, { useEffect, useState } from 'react';
import SvgIcon from '../../components/SvgIcon';
import DataQualityChecks from '../../components/DataQualityChecks';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { CheckResultsOverviewDataModel, UICheckContainerModel } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getTableDailyRecurring,
  updateTableDailyRecurring
} from '../../redux/actions/table.actions';
import Button from '../../components/Button';
import { CheckResultOverviewApi } from "../../services/apiClient";
import { useParams } from "react-router-dom";
import ConnectionLayout from "../../components/ConnectionLayout";
import { CheckTypes } from "../../shared/routes";
import { getFirstLevelActiveTab } from "../../redux/selectors";

const TableDailyChecksView = () => {
  const { checkTypes, connection: connectionName, schema: schemaName, table: tableName }: { checkTypes: CheckTypes, connection: string, schema: string, table: string } = useParams();
  const { dailyRecurring, isUpdating, loading } = useSelector(
    (state: IRootState) => state.table
  );
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UICheckContainerModel>();
  const [isUpdated, setIsUpdated] = useState(false);
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const getCheckOverview = () => {
    CheckResultOverviewApi.getTableRecurringOverview(connectionName, schemaName, tableName, 'daily').then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    setUpdatedChecksUI(dailyRecurring);
  }, [dailyRecurring]);

  useEffect(() => {
    dispatch(getTableDailyRecurring(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName));
  }, [connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    if (!updatedChecksUI) return;

    await dispatch(
      updateTableDailyRecurring(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        updatedChecksUI
      )
    );
    await dispatch(
      getTableDailyRecurring(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName)
    );
    setIsUpdated(false);
  };

  const onChangeUI = (ui: UICheckContainerModel) => {
    setUpdatedChecksUI(ui);
    setIsUpdated(true);
  };

  return (
    <ConnectionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2" style={{ maxWidth: `calc(100% - 180px)` }}>
          <SvgIcon name="table-check" className="w-5 h-5 shrink-0" />
          <div className="text-xl font-semibold truncate">{`Daily recurring checks ${connectionName}.${schemaName}.${tableName}`}</div>
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
          className="max-h-checks-1"
          checksUI={updatedChecksUI}
          onChange={onChangeUI}
          checkResultsOverview={checkResultsOverview}
          getCheckOverview={getCheckOverview}
          loading={loading}
        />
      </div>
    </ConnectionLayout>
  );
};

export default TableDailyChecksView;
