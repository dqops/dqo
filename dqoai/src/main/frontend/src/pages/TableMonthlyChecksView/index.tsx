import React, { useEffect, useState } from 'react';
import SvgIcon from '../../components/SvgIcon';
import DataQualityChecks from '../../components/DataQualityChecks';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { CheckResultsOverviewDataModel, UIAllChecksModel } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getTableMonthlyRecurring,
  updateTableMonthlyRecurring
} from '../../redux/actions/table.actions';
import Button from '../../components/Button';
import { CheckResultOverviewApi } from "../../services/apiClient";
import { useParams } from "react-router-dom";
import ConnectionLayout from "../../components/ConnectionLayout";

const TableMonthlyChecksView = () => {
  const { connection: connectionName, schema: schemaName, table: tableName }: { connection: string, schema: string, table: string } = useParams();
  const { monthlyRecurring, isUpdating, loading } = useSelector(
    (state: IRootState) => state.table
  );
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  const [isUpdated, setIsUpdated] = useState(false);
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);

  const getCheckOverview = () => {
    CheckResultOverviewApi.getTableRecurringOverview(connectionName, schemaName, tableName, 'monthly').then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    setUpdatedChecksUI(monthlyRecurring);
  }, [monthlyRecurring]);

  useEffect(() => {
    dispatch(getTableMonthlyRecurring(connectionName, schemaName, tableName));
  }, [connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    if (!updatedChecksUI) return;

    await dispatch(
      updateTableMonthlyRecurring(
        connectionName,
        schemaName,
        tableName,
        updatedChecksUI
      )
    );
    setIsUpdated(false);

    await dispatch(
      getTableMonthlyRecurring(connectionName, schemaName, tableName)
    );
  };

  const onChangeUI = (ui: UIAllChecksModel) => {
    setUpdatedChecksUI(ui);
    setIsUpdated(true);
  };

  return (
    <ConnectionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2">
          <SvgIcon name="table-check" className="w-5 h-5" />
          <div className="text-xl font-semibold">{`Monthly recurring checks ${connectionName}.${schemaName}.${tableName}`}</div>
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
          className="max-h-checks-1"
          checksUI={updatedChecksUI}
          onChange={onChangeUI}
          onUpdate={onUpdate}
          checkResultsOverview={checkResultsOverview}
          getCheckOverview={getCheckOverview}
          loading={loading}
        />
      </div>
    </ConnectionLayout>
  );
};

export default TableMonthlyChecksView;
