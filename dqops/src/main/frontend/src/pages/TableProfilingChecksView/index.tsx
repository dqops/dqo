import { isEqual } from 'lodash';
import React, { useEffect, useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import { CheckContainerModel, CheckResultsOverviewDataModel } from '../../api';
import Button from '../../components/Button';
import DataQualityChecks from '../../components/DataQualityChecks';
import SvgIcon from '../../components/SvgIcon';
import TableNavigation from '../../components/TableNavigation';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getTableProfilingChecksModel,
  updateTableProfilingChecksModel
} from '../../redux/actions/table.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';
import { CheckResultOverviewApi } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';

const TableProfilingsView = () => {
  const {
    checkTypes,
    connection: connectionName,
    schema: schemaName,
    table: tableName
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
  } = useParams();

  const { checksUI, isUpdating, loading } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const [updatedChecksUI, setUpdatedChecksUI] = useState<CheckContainerModel>();
  const dispatch = useActionDispatch();
  const [checkResultsOverview, setCheckResultsOverview] = useState<
    CheckResultsOverviewDataModel[]
  >([]);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    setUpdatedChecksUI(checksUI);
  }, [checksUI]);

  useEffect(() => {
    dispatch(
      getTableProfilingChecksModel(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName
      )
    );
  }, [checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    if (!updatedChecksUI) {
      return;
    }

    await dispatch(
      updateTableProfilingChecksModel(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        updatedChecksUI
      )
    );
    await dispatch(
      getTableProfilingChecksModel(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        false
      )
    );
  };

  const isUpdated = useMemo(
    () => !isEqual(updatedChecksUI, checksUI),
    [checksUI, updatedChecksUI]
  );

  const getCheckOverview = () => {
    CheckResultOverviewApi.getTableProfilingChecksOverview(
      connectionName,
      schemaName,
      tableName
    ).then((res) => {
      setCheckResultsOverview(res.data);
    });
  };

  return (
    <>
      <div className="relative">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14 max-w-full">
          <div
            className="flex items-center space-x-2"
            style={{ maxWidth: `calc(100% - 180px)` }}
          >
            <SvgIcon name="table-check" className="w-5 h-5 shrink-0" />
            <div className="text-lg font-semibold truncate">{`Profiling checks for ${connectionName}.${schemaName}.${tableName}`}</div>
          </div>
          <Button
            color={isUpdated ? 'primary' : 'secondary'}
            variant="contained"
            label="Save"
            className="w-40 shrink-0"
            onClick={onUpdate}
            loading={isUpdating}
          />
        </div>
      </div>
      <TableNavigation />
      <div>
        <DataQualityChecks
          onUpdate={onUpdate}
          checksUI={updatedChecksUI}
          onChange={setUpdatedChecksUI}
          className="max-h-checks-2"
          checkResultsOverview={checkResultsOverview}
          getCheckOverview={getCheckOverview}
          loading={loading}
        />
      </div>
    </>
  );
};

export default TableProfilingsView;
