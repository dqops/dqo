import React, { useEffect, useState } from 'react';

import TableActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';

import {
  getTableProfilingChecksUI,
  updateTableProfilingChecksUI
} from '../../../redux/actions/table.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { useParams } from 'react-router-dom';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { CheckTypes } from '../../../shared/routes';
import TableAdvancedProfiling from '../../../pages/TableAdvencedProfiling';

import Tabs from '../../Tabs';

import TableStatisticsView from '../../../pages/TableStatisticsView';

const tabs = [
  {
    label: 'Basic data statistics',
    value: 'statistics'
  },
  {
    label: 'Advanced Profiling',
    value: 'advanced'
  }
];

const ProfilingView = () => {
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
  const { checksUI, isUpdating, isUpdatedChecksUi, tableBasic } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [activeTab, setActiveTab] = useState('statistics');

  useEffect(() => {
    dispatch(
      getTableProfilingChecksUI(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName
      )
    );
  }, [
    checkTypes,
    firstLevelActiveTab,
    connectionName,
    schemaName,
    tableName,
    tableBasic
  ]);

  const onUpdate = async () => {
    if (!checksUI) {
      return;
    }
    await dispatch(
      updateTableProfilingChecksUI(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        checksUI
      )
    );
    await dispatch(
      getTableProfilingChecksUI(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        false
      )
    );
  };

  return (
    <div className="flex-grow min-h-0 flex flex-col">
      {activeTab === 'statistics' && (
        <TableActionGroup
          shouldDelete={false}
          onUpdate={onUpdate}
          isUpdated={isUpdatedChecksUi}
          isUpdating={isUpdating}
          collectStatistic={true}
          addSaveButton={false}
        />
      )}{' '}
      {activeTab === 'advanced' && (
        <TableActionGroup
          shouldDelete={false}
          onUpdate={onUpdate}
          isUpdated={isUpdatedChecksUi}
          isUpdating={isUpdating}
        />
      )}
      <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      {activeTab === 'statistics' && (
        <TableStatisticsView
          connectionName={connectionName}
          schemaName={schemaName}
          tableName={tableName}
        />
      )}
      {activeTab === 'advanced' && <TableAdvancedProfiling />}
    </div>
  );
};

export default ProfilingView;
