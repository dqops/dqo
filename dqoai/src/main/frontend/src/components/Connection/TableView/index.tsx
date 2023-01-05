import React, { useEffect, useState } from 'react';
import SvgIcon from '../../SvgIcon';
import Tabs from '../../Tabs';
import TableDetails from './TableDetails';
import ScheduleDetail from './ScheduleDetail';
import { useHistory, useLocation } from 'react-router-dom';
import qs from 'query-string';
import { useTree } from '../../../contexts/treeContext';
import TimestampsView from './TimestampsView';
import CheckpointsView from './CheckpointsView';
import PartitionedChecks from './PartitionedChecks';
import AdhocView from './AdhocView';
import TableCommentView from './TableCommentView';
import TableLabelsView from './TableLabelsView';
import TableDataStream from './TableDataStream';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';

interface ITableViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const initTabs = [
  {
    label: 'Table',
    value: 'table'
  },
  {
    label: 'Schedule',
    value: 'schedule'
  },
  {
    label: 'Ad-hoc checks',
    value: 'data-quality-checks'
  },
  {
    label: 'Checkpoints',
    value: 'checkpoints'
  },
  {
    label: 'Partitioned checks',
    value: 'partitioned-checks'
  },
  {
    label: 'Comments',
    value: 'comments'
  },
  {
    label: 'Labels',
    value: 'labels'
  },
  {
    label: 'Data Streams',
    value: 'data-streams'
  },
  {
    label: 'Timestamps',
    value: 'timestamps'
  }
];

const TableView = ({
  connectionName,
  schemaName,
  tableName
}: ITableViewProps) => {
  const [activeTab, setActiveTab] = useState('table');
  const { activeTab: pageTab, tabMap, setTabMap } = useTree();
  const history = useHistory();
  const location = useLocation();
  const [tabs, setTabs] = useState(initTabs);
  const {
    isUpdatedTableBasic,
    isUpdatedComments,
    isUpdatedLabels,
    isUpdatedChecksUi,
    isUpdatedDailyCheckpoints,
    isUpdatedMonthlyCheckpoints,
    isUpdatedDailyPartitionedChecks,
    isUpdatedMonthlyPartitionedChecks,
    isUpdatedSchedule,
    isUpdatedDataStreamsMapping
  } = useSelector((state: IRootState) => state.table);

  useEffect(() => {
    const searchQuery = qs.stringify({
      connection: connectionName,
      schema: schemaName,
      table: tableName
    });

    history.replace(`/checks?${searchQuery}`);
  }, [connectionName, schemaName, tableName]);

  const onChangeTab = (tab: string) => {
    setActiveTab(tab);
    const searchQuery = qs.stringify({
      connection: connectionName,
      schema: schemaName,
      table: tableName,
      tab
    });
    history.replace(`/checks?${searchQuery}`);
    setTabMap({
      ...tabMap,
      [pageTab]: tab
    });
  };

  useEffect(() => {
    if (tabMap[pageTab]) {
      setActiveTab(tabMap[pageTab]);
    } else {
      setActiveTab('table');
    }
  }, [pageTab, tabMap]);

  useEffect(() => {
    const params = qs.parse(location.search);
    if (params.tab) {
      setActiveTab(params.tab as string);
      setTabMap({
        ...tabMap,
        [pageTab]: params.tab
      });
    }
  }, [location.search]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'table'
          ? { ...item, isUpdated: isUpdatedTableBasic }
          : item
      )
    );
  }, [isUpdatedTableBasic]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'schedule'
          ? { ...item, isUpdated: isUpdatedSchedule }
          : item
      )
    );
  }, [isUpdatedSchedule]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'data-streams'
          ? { ...item, isUpdated: isUpdatedDataStreamsMapping }
          : item
      )
    );
  }, [isUpdatedDataStreamsMapping]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'comments'
          ? { ...item, isUpdated: isUpdatedComments }
          : item
      )
    );
  }, [isUpdatedComments]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'labels' ? { ...item, isUpdated: isUpdatedLabels } : item
      )
    );
  }, [isUpdatedLabels]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'data-quality-checks'
          ? { ...item, isUpdated: isUpdatedChecksUi }
          : item
      )
    );
  }, [isUpdatedChecksUi]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'checkpoints'
          ? {
              ...item,
              isUpdated:
                isUpdatedDailyCheckpoints || isUpdatedMonthlyCheckpoints
            }
          : item
      )
    );
  }, [isUpdatedDailyCheckpoints, isUpdatedMonthlyCheckpoints]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'partitioned-checks'
          ? {
              ...item,
              isUpdated:
                isUpdatedDailyPartitionedChecks ||
                isUpdatedMonthlyPartitionedChecks
            }
          : item
      )
    );
  }, [isUpdatedDailyPartitionedChecks, isUpdatedMonthlyPartitionedChecks]);

  return (
    <div className="relative">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-13 items-center">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
          <div className="text-xl font-semibold">{`${connectionName}.${schemaName}.${tableName}`}</div>
        </div>
      </div>
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
      </div>
      <div>
        {activeTab === 'table' && (
          <TableDetails
            connectionName={connectionName}
            schemaName={schemaName}
            tableName={tableName}
          />
        )}
      </div>
      <div>
        {activeTab === 'schedule' && (
          <ScheduleDetail
            connectionName={connectionName}
            schemaName={schemaName}
            tableName={tableName}
          />
        )}
      </div>
      <div>
        {activeTab === 'data-quality-checks' && (
          <AdhocView
            connectionName={connectionName}
            schemaName={schemaName}
            tableName={tableName}
          />
        )}
      </div>
      <div>
        {activeTab === 'checkpoints' && (
          <CheckpointsView
            connectionName={connectionName}
            schemaName={schemaName}
            tableName={tableName}
          />
        )}
      </div>
      <div>
        {activeTab === 'partitioned-checks' && (
          <PartitionedChecks
            connectionName={connectionName}
            schemaName={schemaName}
            tableName={tableName}
          />
        )}
      </div>
      <div>
        {activeTab === 'comments' && (
          <TableCommentView
            connectionName={connectionName}
            schemaName={schemaName}
            tableName={tableName}
          />
        )}
      </div>
      <div>
        {activeTab === 'labels' && (
          <TableLabelsView
            connectionName={connectionName}
            schemaName={schemaName}
            tableName={tableName}
          />
        )}
      </div>
      <div>
        {activeTab === 'data-streams' && (
          <TableDataStream
            connectionName={connectionName}
            schemaName={schemaName}
            tableName={tableName}
          />
        )}
      </div>
      <div>
        {activeTab === 'timestamps' && (
          <TimestampsView
            connectionName={connectionName}
            schemaName={schemaName}
            tableName={tableName}
          />
        )}
      </div>
    </div>
  );
};

export default TableView;
