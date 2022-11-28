import React, { useEffect, useState } from 'react';
import SvgIcon from '../../SvgIcon';
import Tabs from '../../Tabs';
import TableDetails from './TableDetails';
import ScheduleDetail from './ScheduleDetail';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { UIAllChecksModel } from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableDailyCheckpoints,
  getTableMonthlyCheckpoints,
  getTableDailyPartitionedChecks,
  getTableMonthlyPartitionedChecks
} from '../../../redux/actions/table.actions';
import { useHistory } from 'react-router-dom';
import qs from 'query-string';
import { useTree } from '../../../contexts/treeContext';
import TimestampsView from './TimestampsView';
import CheckpointsView from './CheckpointsView';
import PartitionedChecks from './PartitionedChecks';
import AdhocView from './AdhocView';
import TableCommentView from './TableCommentView';
import TableLabelsView from './TableLabelsView';
import TableDataStream from './TableDataStream';

interface ITableViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const tabs = [
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

  const {
    dailyCheckpoints,
    monthlyCheckpoints,
    dailyPartitionedChecks,
    monthlyPartitionedChecks
  } = useSelector((state: IRootState) => state.table);
  const { activeTab: pageTab, tabMap, setTabMap } = useTree();

  const [updatedDailyCheckpoints, setUpdatedDailyCheckpoints] =
    useState<UIAllChecksModel>();
  const [updatedMonthlyCheckpoints, setUpdatedMonthlyCheckpoints] =
    useState<UIAllChecksModel>();
  const [updatedDailyPartitionedChecks, setUpdatedDailyPartitionedChecks] =
    useState<UIAllChecksModel>();
  const [updatedMonthlyPartitionedChecks, setUpdatedMonthlyPartitionedChecks] =
    useState<UIAllChecksModel>();
  const dispatch = useActionDispatch();
  const history = useHistory();

  useEffect(() => {
    setUpdatedDailyCheckpoints(dailyCheckpoints);
  }, [dailyCheckpoints]);

  useEffect(() => {
    setUpdatedMonthlyCheckpoints(monthlyCheckpoints);
  }, [monthlyCheckpoints]);

  useEffect(() => {
    setUpdatedDailyPartitionedChecks(dailyPartitionedChecks);
  }, [dailyPartitionedChecks]);

  useEffect(() => {
    setUpdatedMonthlyPartitionedChecks(monthlyPartitionedChecks);
  }, [monthlyPartitionedChecks]);

  useEffect(() => {
    setUpdatedDailyCheckpoints(undefined);
    setUpdatedMonthlyCheckpoints(undefined);

    dispatch(getTableDailyCheckpoints(connectionName, schemaName, tableName));
    dispatch(getTableMonthlyCheckpoints(connectionName, schemaName, tableName));
    dispatch(
      getTableDailyPartitionedChecks(connectionName, schemaName, tableName)
    );
    dispatch(
      getTableMonthlyPartitionedChecks(connectionName, schemaName, tableName)
    );

    const searchQuery = qs.stringify({
      connection: connectionName,
      schema: schemaName,
      table: tableName
    });

    history.replace(`/?${searchQuery}`);
  }, [connectionName, schemaName, tableName]);

  const onChangeTab = (tab: string) => {
    setActiveTab(tab);
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

  return (
    <div className="relative">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-13 items-center">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
          <div className="text-xl font-semibold">{`${connectionName}.${schemaName}.${tableName}`}</div>
        </div>
        {/*<div className="flex space-x-4 items-center">*/}
        {/*  <Button*/}
        {/*    variant="text"*/}
        {/*    color="info"*/}
        {/*    label="Delete"*/}
        {/*    onClick={() => setIsOpen(true)}*/}
        {/*  />*/}
        {/*  <Button*/}
        {/*    color={isUpdated ? 'primary' : 'secondary'}*/}
        {/*    variant="contained"*/}
        {/*    label="Save"*/}
        {/*    className="w-40"*/}
        {/*    onClick={onUpdate}*/}
        {/*    loading={isUpdating}*/}
        {/*    disabled={isDisabled}*/}
        {/*  />*/}
        {/*</div>*/}
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
            dailyCheckpoints={updatedDailyCheckpoints}
            monthlyCheckpoints={updatedMonthlyCheckpoints}
            onDailyCheckpointsChange={setUpdatedDailyCheckpoints}
            onMonthlyCheckpointsChange={setUpdatedMonthlyCheckpoints}
          />
        )}
      </div>
      <div>
        {activeTab === 'partitioned-checks' && (
          <PartitionedChecks
            dailyPartitionedChecks={updatedDailyPartitionedChecks}
            monthlyPartitionedChecks={updatedMonthlyPartitionedChecks}
            onDailyPartitionedChecks={setUpdatedDailyPartitionedChecks}
            onMonthlyPartitionedChecks={setUpdatedMonthlyPartitionedChecks}
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
