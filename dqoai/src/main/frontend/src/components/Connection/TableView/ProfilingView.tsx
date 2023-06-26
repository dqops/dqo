import React, { useEffect, useState } from 'react';

import TableActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';

import {
  getTableProfilingChecksModel,
  updateTableProfilingChecksModel
} from '../../../redux/actions/table.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { useHistory, useParams } from 'react-router-dom';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import TableAdvancedProfiling from '../../../pages/TableAdvencedProfiling';

import Tabs from '../../Tabs';

import TableStatisticsView from '../../../pages/TableStatisticsView';
import { DataGroupingConfigurationSpec } from '../../../api';

import { setCreatedDataStream } from '../../../redux/actions/rule.actions';
import { addFirstLevelTab } from '../../../redux/actions/source.actions';
import { DataGroupingConfigurationsApi } from '../../../services/apiClient';
interface LocationState {
  bool: boolean;
  data_stream_name: string;
  spec: DataGroupingConfigurationSpec;
}

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
  const [nameOfDataStream, setNameOfDataStream] = useState<string>('');
  const [levels, setLevels] = useState<DataGroupingConfigurationSpec>({});
  const [selected, setSelected] = useState<number>(0);
  const history = useHistory();

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
      updateTableProfilingChecksModel(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        checksUI
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
  const updateData2 = (nameOfDS: string): void => {
    setNameOfDataStream(nameOfDS);
  };

  const setLevelsData2 = (levelsToSet: DataGroupingConfigurationSpec): void => {
    setLevels(levelsToSet);
  };

  const setNumberOfSelected2 = (param: number): void => {
    setSelected(param);
  };
  const doNothing = (): void => {};
  const postDataStream = async () => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      'sources',
      connectionName,
      schemaName,
      tableName,
      'data-streams'
    );
    const value = ROUTES.TABLE_LEVEL_VALUE(
      'sources',
      connectionName,
      schemaName,
      tableName
    );
    const data: LocationState = {
      bool: true,
      data_stream_name: nameOfDataStream,
      spec: levels
    };

    try {
      const response =
        await DataGroupingConfigurationsApi.createTableGroupingConfiguration(
          connectionName,
          schemaName,
          tableName,
          { data_grouping_configuration_name: nameOfDataStream, spec: levels }
        );
      if (response.status === 409) {
        doNothing();
      }
    } catch (error: any) {
      if (error.response && error.response.status) {
        doNothing();
      }
    }
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value,
        state: data,
        label: tableName
      })
    );
    history.push(url);
    setCreatedDataStream(false, '', {});
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
          createDataStream={selected > 0 && selected <= 9 && true}
          createDataStreamFunc={postDataStream}
          maxToCreateDataStream={selected > 9 && true}
        />
      )}
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
          updateData2={updateData2}
          setLevelsData2={setLevelsData2}
          setNumberOfSelected2={setNumberOfSelected2}
        />
      )}
      {activeTab === 'advanced' && <TableAdvancedProfiling />}
    </div>
  );
};

export default ProfilingView;
