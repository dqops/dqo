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
import TableProfilingChecks from '../../../pages/TableProfilingChecks';

import Tabs from '../../Tabs';

import TableStatisticsView from '../../../pages/TableStatisticsView';
import {
  DataGroupingConfigurationSpec,
  TableColumnsStatisticsModel
} from '../../../api';

import { setCreatedDataStream } from '../../../redux/actions/definition.actions';
import { addFirstLevelTab, closeFirstLevelTab } from '../../../redux/actions/source.actions';
import {
  ColumnApiClient,
  DataGroupingConfigurationsApi
} from '../../../services/apiClient';
import { TableReferenceComparisons } from './TableReferenceComparisons';
import ConfirmDialog from '../../CustomTree/ConfirmDialog';
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
    label: 'Profiling Checks',
    value: 'advanced'
  },
  {
    label: 'Table Comparisons',
    value: 'reference-comparisons'
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
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
  const [objectNotFound, setObjectNotFound] = useState(false)
  const fetchColumns = async () => {
        await ColumnApiClient.getColumnsStatistics(
          connectionName,
          schemaName,
          tableName
        ).then((res) => 
      setStatistics(res.data))
      .catch((res) => (res?.response.status === 404 && setObjectNotFound(true)));
  };

  useEffect(() => {
    if (activeTab === 'statistics') {
      fetchColumns();
    }
  }, [connectionName, schemaName, tableName, activeTab]);

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
      'data-groupings'
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

  const onChangeTab = (tab: string) => {
    history.push(
      ROUTES.TABLE_LEVEL_PAGE(
        checkTypes,
        connectionName,
        schemaName,
        tableName,
        tab
      )
    );
    setActiveTab(tab);
  };

  // useEffect(() => {
  //   if(objectNotFound === true ){
  //     setTimeout(() => (
  //       // console.log("deleted"),
  //       dispatch(closeFirstLevelTab(checkTypes, firstLevelActiveTab))
  //     ), 2000)
  //   }
  // }, [objectNotFound])


  // console.log(objectNotFound)
  //   if(objectNotFound === true){
  //     return <div className='w-full text-center flex items-center justify-center text-xl text-red-500'>
  //       The definition of this object was deleted in DQO user home, closing the tab.
  //     </div>
  //   }

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
          statistics={statistics}
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
      <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
      {activeTab === 'statistics' && (
        <TableStatisticsView
          connectionName={connectionName}
          schemaName={schemaName}
          tableName={tableName}
          updateData2={updateData2}
          setLevelsData2={setLevelsData2}
          setNumberOfSelected2={setNumberOfSelected2}
          statistics={statistics}
        />
      )}
      {activeTab === 'advanced' && <TableProfilingChecks />}
      {activeTab === 'reference-comparisons' && (
        <TableReferenceComparisons
          checkTypes={checkTypes}
          checksUI={checksUI}
          fetchChecks={onUpdate}
        />
      )}
      <ConfirmDialog
      open={objectNotFound}
      onConfirm={() => new Promise(() => dispatch(closeFirstLevelTab(checkTypes, firstLevelActiveTab)))}
      isCancelExcluded={true} 
      onClose={() => dispatch(closeFirstLevelTab(checkTypes, firstLevelActiveTab))}
      message='The definition of this object was deleted in DQO user home, closing the tab'/>
    </div>
  );
};

export default ProfilingView;
