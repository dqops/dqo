import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { CheckContainerModel, CheckResultsOverviewDataModel } from '../../api';
import Button from '../../components/Button';
import DataQualityChecks from '../../components/DataQualityChecks';
import SvgIcon from '../../components/SvgIcon';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnProfilingChecksModelFilter,
  setColumnUpdatedProfilingChecksModelFilter
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

const ColumnProfilingChecksUIFilterView = () => {
  const {
    checkTypes,
    connection: connectionName,
    schema: schemaName,
    table: tableName,
    column: columnName,
    category,
    checkName
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
    column: string;
    category: string;
    checkName: string;
  } = useDecodedParams();
  const { checksUIFilter, isUpdatedChecksUIFilter, loading } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<
    CheckResultsOverviewDataModel[]
  >([]);
  const [isUpdating, setIsUpdating] = useState(false);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnProfilingChecksOverview(
      connectionName,
      schemaName,
      tableName,
      columnName,
      category,
      checkName
    ).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  const onUpdate = async () => {
    setIsUpdating(true);
    await ColumnApiClient.updateColumnProfilingChecksModel(
      connectionName,
      schemaName,
      tableName,
      columnName,
      checksUIFilter
    );
    await dispatch(
      getColumnProfilingChecksModelFilter(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName,
        category,
        checkName,
        false
      )
    );
    setIsUpdating(false);
  };

  useEffect(() => {
    dispatch(
      getColumnProfilingChecksModelFilter(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName,
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
    category,
    checkName
  ]);

  const onChange = (ui: CheckContainerModel) => {
    dispatch(
      setColumnUpdatedProfilingChecksModelFilter(
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
          color={isUpdatedChecksUIFilter ? 'primary' : 'secondary'}
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
          checksUI={checksUIFilter}
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

export default ColumnProfilingChecksUIFilterView;
