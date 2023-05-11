import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getTablePartitionedChecksUIFilter,
  setTableUpdatedPartitionedChecksUiFilter,
} from '../../redux/actions/table.actions';
import SvgIcon from '../../components/SvgIcon';
import DataQualityChecks from '../../components/DataQualityChecks';
import { CheckResultsOverviewDataModel, UICheckContainerModel } from '../../api';
import { CheckResultOverviewApi, TableApiClient } from "../../services/apiClient";
import { useParams } from "react-router-dom";
import ConnectionLayout from "../../components/ConnectionLayout";
import Button from "../../components/Button";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../redux/selectors";
import { CheckTypes } from "../../shared/routes";

const TablePartitionedChecksUIFilterView = () => {
  const { checkTypes, connection: connectionName, schema: schemaName, table: tableName, timePartitioned, category, checkName }: { checkTypes: CheckTypes, connection: string, schema: string, table: string, timePartitioned: 'daily' | 'monthly', category: string, checkName: string } = useParams();
  const { partitionedChecksUIFilter, isUpdatedPartitionedChecksUIFilter, loading } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const [isUpdating, setIsUpdating] = useState(false);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const getCheckOverview = () => {
    CheckResultOverviewApi.getTablePartitionedChecksOverview(connectionName, schemaName, tableName, timePartitioned).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    dispatch(
      getTablePartitionedChecksUIFilter(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, timePartitioned, category, checkName)
    );
  }, [connectionName, schemaName, tableName, category, checkName]);

  const onUpdate = async () => {
    setIsUpdating(true);
    await TableApiClient.updateTableRecurringChecksUI(
      connectionName,
      schemaName,
      tableName,
      timePartitioned,
      partitionedChecksUIFilter
    );

    await dispatch(
      getTablePartitionedChecksUIFilter(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, timePartitioned, category, checkName, false)
    );
    setIsUpdating(false);
  };

  const onChange = (ui: UICheckContainerModel) => {
    dispatch(setTableUpdatedPartitionedChecksUiFilter(checkTypes, firstLevelActiveTab, ui));
  };

  return (
    <ConnectionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2" style={{ maxWidth: `calc(100% - 180px)` }}>
          <SvgIcon name="search" className="w-5 h-5 shrink-0" />
          <div className="text-xl font-semibold truncate">{`${connectionName}.${schemaName}.${tableName}.checks.${category} - ${checkName}`}</div>
        </div>
        <Button
          color={isUpdatedPartitionedChecksUIFilter ? 'primary' : 'secondary'}
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
          checksUI={partitionedChecksUIFilter}
          onChange={onChange}
          checkResultsOverview={checkResultsOverview}
          getCheckOverview={getCheckOverview}
          loading={loading}
        />
      </div>
    </ConnectionLayout>
  );
};

export default TablePartitionedChecksUIFilterView;
