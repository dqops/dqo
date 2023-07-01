import React, { useEffect, useState } from 'react';
import SvgIcon from '../../components/SvgIcon';
import DataQualityChecks from '../../components/DataQualityChecks';
import { useSelector } from 'react-redux';
import { CheckResultsOverviewDataModel, CheckContainerModel } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import Button from '../../components/Button';
import {
  getColumnMonthlyPartitionedChecks,
  updateColumnMonthlyPartitionedChecks
} from '../../redux/actions/column.actions';
import { CheckResultOverviewApi } from '../../services/apiClient';
import { useParams } from "react-router-dom";
import ConnectionLayout from "../../components/ConnectionLayout";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../redux/selectors";
import { CheckTypes } from "../../shared/routes";
import ColumnNavigation from "../../components/ColumnNavigation";

const ColumnMonthlyPartitionedChecksView = () => {
  const { checkTypes, connection: connectionName, schema: schemaName, table: tableName, column: columnName }: { checkTypes: CheckTypes, connection: string, schema: string, table: string, column: string } = useParams();
  const { monthlyPartitionedChecks, isUpdating, loading } = useSelector(getFirstLevelState(checkTypes));
  const [updatedChecksUI, setUpdatedChecksUI] = useState<CheckContainerModel>();
  const [isUpdated, setIsUpdated] = useState(false);
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnPartitionedChecksOverview(connectionName, schemaName, tableName, columnName, 'monthly').then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    setUpdatedChecksUI(monthlyPartitionedChecks);
  }, [monthlyPartitionedChecks]);

  useEffect(() => {
    dispatch(
      getColumnMonthlyPartitionedChecks(
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
      updateColumnMonthlyPartitionedChecks(
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
      getColumnMonthlyPartitionedChecks(
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
    <ConnectionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2" style={{ maxWidth: `calc(100% - 180px)` }}>
          <SvgIcon name="column-check" className="w-5 h-5 shrink-0" />
          <div className="text-xl font-semibold truncate">{`Monthly partition checks for  ${connectionName}.${schemaName}.${tableName}.${columnName}`}</div>
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
      <ColumnNavigation defaultTab="monthly" />
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
    </ConnectionLayout>
  );
};

export default ColumnMonthlyPartitionedChecksView;
