import React, { useEffect, useState } from 'react';
import SvgIcon from '../../components/SvgIcon';
import DataQualityChecks from '../../components/DataQualityChecks';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { CheckResultsOverviewDataModel, UICheckContainerModel } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import Button from '../../components/Button';
import {
  getColumnDailyPartitionedChecks,
  updateColumnDailyPartitionedChecks
} from '../../redux/actions/column.actions';
import { CheckResultOverviewApi } from '../../services/apiClient';
import { useParams } from "react-router-dom";
import ConnectionLayout from "../../components/ConnectionLayout";

const ColumnDailyPartitionedChecksView = () => {
  const { connection: connectionName, schema: schemaName, table: tableName, column: columnName }: { connection: string, schema: string, table: string, column: string } = useParams();
  const { dailyPartitionedChecks, isUpdating, loading } = useSelector(
    (state: IRootState) => state.column
  );
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UICheckContainerModel>();
  const [isUpdated, setIsUpdated] = useState(false);
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  
  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnPartitionedChecksOverview(connectionName, schemaName, tableName, columnName, 'daily').then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    setUpdatedChecksUI(dailyPartitionedChecks);
  }, [dailyPartitionedChecks]);

  useEffect(() => {
    dispatch(
      getColumnDailyPartitionedChecks(
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
      updateColumnDailyPartitionedChecks(
        connectionName,
        schemaName,
        tableName,
        columnName,
        updatedChecksUI
      )
    );
    await dispatch(
      getColumnDailyPartitionedChecks(
        connectionName,
        schemaName,
        tableName,
        columnName
      )
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
          <SvgIcon name="column-check" className="w-5 h-5 shrink-0" />
          <div className="text-xl font-semibold truncate">{`Daily partition checks for ${connectionName}.${schemaName}.${tableName}.${columnName}`}</div>
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

export default ColumnDailyPartitionedChecksView;
