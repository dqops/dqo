import React, { useEffect, useState } from 'react';
import SvgIcon from '../../components/SvgIcon';
import DataQualityChecks from '../../components/DataQualityChecks';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { CheckResultsOverviewDataModel, UIAllChecksModel } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import Button from '../../components/Button';
import {
  getColumnMonthlyPartitionedChecks,
  updateColumnMonthlyPartitionedChecks
} from '../../redux/actions/column.actions';
import { CheckResultOverviewApi } from '../../services/apiClient';
import { useParams } from "react-router-dom";
import ConnectionLayout from "../../components/ConnectionLayout";

const ColumnMonthlyPartitionedChecksView = () => {
  const { connection: connectionName, schema: schemaName, table: tableName, column: columnName }: { connection: string, schema: string, table: string, column: string } = useParams();
  const { monthlyPartitionedChecks, isUpdating } = useSelector(
    (state: IRootState) => state.column
  );
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  const [isUpdated, setIsUpdated] = useState(false);
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  
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
        connectionName,
        schemaName,
        tableName,
        columnName,
        updatedChecksUI
      )
    );
    await dispatch(
      getColumnMonthlyPartitionedChecks(
        connectionName,
        schemaName,
        tableName,
        columnName
      )
    );
    setIsUpdated(false);
  };

  const onChangeUI = (ui: UIAllChecksModel) => {
    setUpdatedChecksUI(ui);
    setIsUpdated(true);
  };

  return (
    <ConnectionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
          <div className="text-xl font-semibold">{`Monthly Partitioned Checks for ${connectionName}.${schemaName}.${tableName}.${columnName}`}</div>
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
        />
      </div>
    </ConnectionLayout>
  );
};

export default ColumnMonthlyPartitionedChecksView;
