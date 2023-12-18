import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getTableProfilingChecksModelFilter, setTableUpdatedProfilingChecksModelFilter,
} from '../../redux/actions/table.actions';
import SvgIcon from '../../components/SvgIcon';
import DataQualityChecks from '../../components/DataQualityChecks';
import { CheckResultsOverviewDataModel, CheckContainerModel } from '../../api';
import { CheckResultOverviewApi, TableApiClient } from "../../services/apiClient";
import { useParams } from "react-router-dom";
import ConnectionLayout from "../../components/ConnectionLayout";
import Button from "../../components/Button";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../redux/selectors";
import { CheckTypes } from "../../shared/routes";

const TableProfilingChecksUIFilterView = () => {
  const { checkTypes, connection: connectionName, schema: schemaName, table: tableName, category, checkName }: { checkTypes: CheckTypes, connection: string, schema: string, table: string, category: string, checkName: string } = useParams();
  const { checksUIFilter, isUpdatedChecksUIFilter, loading } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const [isUpdating, setIsUpdating] = useState(false);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const getCheckOverview = () => {
    CheckResultOverviewApi.getTableProfilingChecksOverview(connectionName, schemaName, tableName).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  useEffect(() => {
    dispatch(
      getTableProfilingChecksModelFilter(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, category, checkName)
    );
  }, [connectionName, schemaName, tableName, category, checkName]);

  const onUpdate = async () => {
    setIsUpdating(true);
    await TableApiClient.updateTableProfilingChecksModel(
      connectionName,
      schemaName,
      tableName,
      checksUIFilter
    );
    dispatch(
      getTableProfilingChecksModelFilter(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, category, checkName, false)
    );
    setIsUpdating(false);
  };

  const onChange = (data: CheckContainerModel) => {
    dispatch(setTableUpdatedProfilingChecksModelFilter(checkTypes, firstLevelActiveTab, data));
  };

  return (
    <ConnectionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2" style={{ maxWidth: `calc(100% - 180px)` }}>
          <SvgIcon name="search" className="w-5 h-5 shrink-0" />
          <div className="text-xl font-semibold truncate">{`${connectionName}.${schemaName}.${tableName}.checks.${category} - ${checkName}`}</div>
        </div>
        <Button
          color={isUpdatedChecksUIFilter ? 'primary' : 'secondary'}
          variant="contained"
          label="Save"
          className="w-40"
          onClick={onUpdate}
          loading={isUpdating}
        />
      </div>
      <div>
        <DataQualityChecks
          onUpdate={() => {}}
          className="max-h-checks-1"
          checksUI={checksUIFilter}
          onChange={onChange}
          checkResultsOverview={checkResultsOverview}
          getCheckOverview={getCheckOverview}
          loading={loading}
          isFiltered = {true}
        />
      </div>
    </ConnectionLayout>
  );
};

export default TableProfilingChecksUIFilterView;
