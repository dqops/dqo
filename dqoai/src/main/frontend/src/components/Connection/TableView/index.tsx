import React, { useEffect, useMemo, useState } from 'react';
import SvgIcon from '../../SvgIcon';
import Button from '../../Button';
import Tabs from '../../Tabs';
import TableDetails from './TableDetails';
import ScheduleDetail from './ScheduleDetail';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  CommentSpec,
  DataStreamMappingSpec,
  TableDailyCheckpointCategoriesSpec,
  TableDailyPartitionedCheckCategoriesSpec,
  TableMonthlyCheckpointCategoriesSpec,
  TableMonthlyPartitionedCheckCategoriesSpec,
  TimeSeriesConfigurationSpec,
  UIAllChecksModel
} from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableAdHocChecksUI,
  getTableComments,
  getTableDataStreamMapping,
  getTableLabels,
  getTableTime,
  updateTableAdHocChecksUI,
  updateTableComments,
  updateTableDataStreamMapping,
  updateTableLabels,
  updateTableTime,
  getTableDailyCheckpoints,
  getTableMonthlyCheckpoints
} from '../../../redux/actions/table.actions';
import CommentsView from '../CommentsView';
import LabelsView from '../LabelsView';
import DataQualityChecks from '../../DataQualityChecks';
import { useHistory } from 'react-router-dom';
import qs from 'query-string';
import DataStreamsMappingView from '../DataStreamsMappingView';
import { useTree } from '../../../contexts/treeContext';
import TimestampsView from './TimestampsView';
import CheckpointsView from './CheckpointsView';
import PartitionedChecks from './PartitionedChecks';

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
    timeSeries,
    comments,
    labels,
    checksUI,
    isUpdating,
    dataStreamsMapping,
    dailyCheckpoints,
    monthlyCheckpoints,
    dailyPartitionedChecks,
    monthlyPartitionedChecks
  } = useSelector((state: IRootState) => state.table);
  const { activeTab: pageTab, tabMap, setTabMap } = useTree();

  const [updatedTimeSeries, setUpdatedTimeSeries] =
    useState<TimeSeriesConfigurationSpec>();
  const [updatedComments, setUpdatedComments] = useState<CommentSpec[]>([]);
  const [updatedLabels, setUpdatedLabels] = useState<string[]>([]);
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  const [updatedDataStreamMapping, setUpdatedDataStreamMapping] =
    useState<DataStreamMappingSpec>();
  const [updatedDailyCheckpoints, setUpdatedDailyCheckpoints] =
    useState<TableDailyCheckpointCategoriesSpec>();
  const [updatedMonthlyCheckpoints, setUpdatedMonthlyCheckpoints] =
    useState<TableMonthlyCheckpointCategoriesSpec>();
  const [updatedDailyPartitionedChecks, setUpdatedDailyPartitionedChecks] =
    useState<TableDailyPartitionedCheckCategoriesSpec>();
  const [updatedMonthlyPartitionedChecks, setUpdatedMonthlyPartitionedChecks] =
    useState<TableMonthlyPartitionedCheckCategoriesSpec>();
  const dispatch = useActionDispatch();
  const history = useHistory();

  useEffect(() => {
    setUpdatedTimeSeries(timeSeries);
  }, [timeSeries]);

  useEffect(() => {
    setUpdatedComments(comments);
  }, [comments]);
  useEffect(() => {
    setUpdatedLabels(labels);
  }, [labels]);

  useEffect(() => {
    setUpdatedChecksUI(checksUI);
  }, [checksUI]);

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
    setUpdatedDataStreamMapping(dataStreamsMapping);
  }, [dataStreamsMapping]);

  useEffect(() => {
    setUpdatedTimeSeries(undefined);
    setUpdatedComments([]);
    setUpdatedLabels([]);
    setUpdatedDataStreamMapping(undefined);
    setUpdatedDailyCheckpoints(undefined);
    setUpdatedMonthlyCheckpoints(undefined);

    dispatch(getTableTime(connectionName, schemaName, tableName));
    dispatch(getTableComments(connectionName, schemaName, tableName));
    dispatch(getTableLabels(connectionName, schemaName, tableName));
    dispatch(getTableAdHocChecksUI(connectionName, schemaName, tableName));
    dispatch(getTableDataStreamMapping(connectionName, schemaName, tableName));
    dispatch(getTableDailyCheckpoints(connectionName, schemaName, tableName));
    dispatch(getTableMonthlyCheckpoints(connectionName, schemaName, tableName));

    const searchQuery = qs.stringify({
      connection: connectionName,
      schema: schemaName,
      table: tableName
    });

    history.replace(`/?${searchQuery}`);
  }, [connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    if (activeTab === 'time') {
      await dispatch(
        updateTableTime(
          connectionName,
          schemaName,
          tableName,
          updatedTimeSeries
        )
      );
      await dispatch(getTableTime(connectionName, schemaName, tableName));
    }
    if (activeTab === 'comments') {
      await dispatch(
        updateTableComments(
          connectionName,
          schemaName,
          tableName,
          updatedComments
        )
      );
      await dispatch(getTableComments(connectionName, schemaName, tableName));
    }
    if (activeTab === 'labels') {
      await dispatch(
        updateTableLabels(connectionName, schemaName, tableName, updatedLabels)
      );
      await dispatch(getTableLabels(connectionName, schemaName, tableName));
    }
    if (activeTab === 'data-quality-checks') {
      await dispatch(
        updateTableAdHocChecksUI(
          connectionName,
          schemaName,
          tableName,
          updatedChecksUI
        )
      );
      await dispatch(
        getTableAdHocChecksUI(connectionName, schemaName, tableName)
      );
    }
    if (activeTab === 'data-streams') {
      await dispatch(
        updateTableDataStreamMapping(
          connectionName,
          schemaName,
          tableName,
          updatedDataStreamMapping
        )
      );
      await dispatch(
        getTableDataStreamMapping(connectionName, schemaName, tableName)
      );
    }
  };

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

  const isDisabled = useMemo(() => {
    if (activeTab === 'labels') {
      return updatedLabels.some((label) => !label);
    }

    return false;
  }, [updatedLabels]);

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
          <DataQualityChecks
            checksUI={updatedChecksUI}
            onChange={setUpdatedChecksUI}
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
          <CommentsView
            comments={updatedComments}
            onChange={setUpdatedComments}
          />
        )}
      </div>
      <div>
        {activeTab === 'labels' && (
          <LabelsView labels={updatedLabels} onChange={setUpdatedLabels} />
        )}
      </div>
      <div>
        {activeTab === 'data-streams' && (
          <DataStreamsMappingView
            dataStreamsMapping={updatedDataStreamMapping}
            onChange={setUpdatedDataStreamMapping}
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
