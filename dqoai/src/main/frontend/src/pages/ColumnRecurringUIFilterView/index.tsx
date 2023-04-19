import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import SvgIcon from '../../components/SvgIcon';
import DataQualityChecks from '../../components/DataQualityChecks';
import { CheckResultsOverviewDataModel, UICheckContainerModel } from '../../api';
import {
  getColumnRecurringUIFilter, setColumnUpdatedRecurringUIFilter,
} from '../../redux/actions/column.actions';
import { CheckResultOverviewApi, ColumnApiClient } from "../../services/apiClient";
import { useParams } from "react-router-dom";
import ConnectionLayout from "../../components/ConnectionLayout";
import Button from "../../components/Button";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../redux/selectors";
import { CheckTypes } from "../../shared/routes";

const ColumnRecurringUIFilterView = () => {
  const { checkTypes, connection: connectionName, schema: schemaName, table: tableName, column: columnName, timePartitioned, category, checkName }: {
    checkTypes: CheckTypes,
    connection: string,
    schema: string,
    table: string,
    column: string,
    timePartitioned: 'daily' | 'monthly',
    category: string,
    checkName: string
  } = useParams();
  const { recurringUIFilter, isUpdatedRecurringUIFilter, loading } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<CheckResultsOverviewDataModel[]>([]);
  const [isUpdating, setIsUpdating] = useState(false);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const getCheckOverview = () => {
    CheckResultOverviewApi.getColumnRecurringOverview(connectionName, schemaName, tableName, columnName, timePartitioned).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };
  const onUpdate = async () => {
    setIsUpdating(true);
    await ColumnApiClient.updateColumnRecurringUI(
      connectionName,
      schemaName,
      tableName,
      columnName,
      timePartitioned,
      recurringUIFilter
    );
    await dispatch(
      getColumnRecurringUIFilter(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, columnName, timePartitioned, category, checkName, false)
    );
    setIsUpdating(false);
  };

  useEffect(() => {
    dispatch(
      getColumnRecurringUIFilter(checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, columnName, timePartitioned, category, checkName)
    );
  }, [checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName, columnName, category, checkName]);

  const onChange = (ui: UICheckContainerModel) => {
    dispatch(setColumnUpdatedRecurringUIFilter(checkTypes, firstLevelActiveTab, ui));
  };

  return (
    <ConnectionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2" style={{ maxWidth: `calc(100% - 180px)` }}>
          <SvgIcon name="search" className="w-5 h-5 shrink-0" />
          <div className="text-xl font-semibold truncate">{`${connectionName}.${schemaName}.${tableName}.${columnName}.checks.${category} - ${checkName}`}</div>
        </div>
        <Button
            color={isUpdatedRecurringUIFilter ? 'primary' : 'secondary'}
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
          checksUI={recurringUIFilter}
          onChange={onChange}
          checkResultsOverview={checkResultsOverview}
          getCheckOverview={getCheckOverview}
          loading={loading}
        />
      </div>
    </ConnectionLayout>
  );
};

export default ColumnRecurringUIFilterView;
