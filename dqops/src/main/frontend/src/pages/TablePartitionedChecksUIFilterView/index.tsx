import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { CheckContainerModel, CheckResultsOverviewDataModel } from '../../api';
import Button from "../../components/Button";
import DataQualityChecks from '../../components/DataQualityChecks';
import SvgIcon from '../../components/SvgIcon';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getTablePartitionedChecksModelFilter,
  setTableUpdatedPartitionedChecksModelFilter,
} from '../../redux/actions/table.actions';
import { getFirstLevelActiveTab, getFirstLevelState } from "../../redux/selectors";
import { CheckResultOverviewApi, TableApiClient } from "../../services/apiClient";
import { CheckTypes } from "../../shared/routes";
import { useDecodedParams } from '../../utils';

const TablePartitionedChecksUIFilterView = () => {
  const { checkTypes, connection: connectionName, schema: schemaName, table: tableName, timePartitioned, category, checkName }: { checkTypes: CheckTypes, connection: string, schema: string, table: string, timePartitioned: 'daily' | 'monthly', category: string, checkName: string } = useDecodedParams();
  const { partitionedChecksUIFilter, isUpdatedPartitionedChecksUIFilter, loading } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const [isUpdating, setIsUpdating] = useState(false);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const getCheckOverview = () => {
    CheckResultOverviewApi.getTablePartitionedChecksOverview(connectionName, schemaName, tableName, timePartitioned, category, checkName).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    dispatch(
      getTablePartitionedChecksModelFilter(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, timePartitioned, category, checkName)
    );
  }, [connectionName, schemaName, tableName, category, checkName]);

  const onUpdate = async () => {
    setIsUpdating(true);
    await TableApiClient.updateTableMonitoringChecksModel(
      connectionName,
      schemaName,
      tableName,
      timePartitioned,
      partitionedChecksUIFilter
    );

    await dispatch(
      getTablePartitionedChecksModelFilter(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, timePartitioned, category, checkName, false)
    );
    setIsUpdating(false);
  };

  const onChange = (ui: CheckContainerModel) => {
    dispatch(setTableUpdatedPartitionedChecksModelFilter(checkTypes, firstLevelActiveTab, ui));
  };

  return (
    <>
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
          isFiltered = {true}
        />
      </div>
    </>
  );
};

export default TablePartitionedChecksUIFilterView;
