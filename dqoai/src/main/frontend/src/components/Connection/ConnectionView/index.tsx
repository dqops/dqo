import React, { useEffect, useMemo, useState } from 'react';
import SvgIcon from '../../SvgIcon';
import Tabs from '../../Tabs';
import ConnectionDetail from './ConnectionDetail';
import ScheduleDetail from './ScheduleDetail';
import Button from '../../Button';
import TimeSeriesView from '../TimeSeriesView';
import {
  CommentSpec,
  ConnectionBasicModel,
  DataStreamMappingSpec,
  RecurringScheduleSpec,
  TimeSeriesConfigurationSpec
} from '../../../api';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  getConnectionBasic,
  getConnectionComments,
  getConnectionDefaultDataStreamsMapping,
  getConnectionLabels,
  getConnectionSchedule,
  getConnectionTime,
  updateConnectionBasic,
  updateConnectionComments,
  updateConnectionDefaultDataStreamsMapping,
  updateConnectionLabels,
  updateConnectionSchedule,
  updateConnectionTime
} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import CommentsView from '../CommentsView';
import LabelsView from '../LabelsView';
import qs from 'query-string';
import { useHistory } from 'react-router-dom';
import SchemasView from './SchemasView';
import DataStreamsMappingView from '../DataStreamsMappingView';
import { useTree } from '../../../contexts/treeContext';
import ConfirmDialog from './ConfirmDialog';
import { ConnectionApiClient } from '../../../services/apiClient';

interface IConnectionViewProps {
  connectionName: string;
  nodeId: string;
}

const tabs = [
  {
    label: 'Connection',
    value: 'connection'
  },
  {
    label: 'Schedule',
    value: 'schedule'
  },
  {
    label: 'Time series',
    value: 'time'
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
    label: 'Schemas',
    value: 'schemas'
  },
  {
    label: 'Data Streams',
    value: 'data-streams'
  }
];

const ConnectionView = ({ connectionName, nodeId }: IConnectionViewProps) => {
  const [activeTab, setActiveTab] = useState('connection');
  const {
    connectionBasic,
    schedule,
    timeSeries,
    comments,
    labels,
    isUpdating,
    defaultDataStreams
  } = useSelector((state: IRootState) => state.connection);
  const [updatedConnectionBasic, setUpdatedConnectionBasic] =
    useState<ConnectionBasicModel>();
  const [updatedSchedule, setUpdatedSchedule] =
    useState<RecurringScheduleSpec>();
  const [updatedTimeSeries, setUpdatedTimeSeries] =
    useState<TimeSeriesConfigurationSpec>();
  const [updatedComments, setUpdatedComments] = useState<CommentSpec[]>([]);
  const [updatedLabels, setUpdatedLabels] = useState<string[]>([]);
  const [updatedDataStreamsMapping, setUpdatedDataStreamsMapping] =
    useState<DataStreamMappingSpec>();
  const dispatch = useActionDispatch();
  const history = useHistory();
  const { tabMap, setTabMap, activeTab: pageTab } = useTree();
  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    setUpdatedConnectionBasic(connectionBasic);
  }, [connectionBasic]);

  useEffect(() => {
    setUpdatedSchedule(schedule);
  }, [schedule]);

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
    setUpdatedDataStreamsMapping(defaultDataStreams);
  }, [defaultDataStreams]);

  useEffect(() => {
    setUpdatedConnectionBasic(undefined);
    setUpdatedSchedule(undefined);
    setUpdatedTimeSeries(undefined);
    setUpdatedComments([]);
    setUpdatedLabels([]);
    setUpdatedDataStreamsMapping(undefined);

    dispatch(getConnectionBasic(connectionName));
    dispatch(getConnectionSchedule(connectionName));
    dispatch(getConnectionTime(connectionName));
    dispatch(getConnectionComments(connectionName));
    dispatch(getConnectionLabels(connectionName));
    dispatch(getConnectionDefaultDataStreamsMapping(connectionName));

    const searchQuery = qs.stringify({
      connection: connectionName
    });

    history.replace(`/?${searchQuery}`);
  }, [connectionName]);

  const onUpdate = async () => {
    if (activeTab === 'connection') {
      await dispatch(
        updateConnectionBasic(connectionName, updatedConnectionBasic)
      );
      await dispatch(getConnectionBasic(connectionName));
    }
    if (activeTab === 'schedule') {
      await dispatch(updateConnectionSchedule(connectionName, updatedSchedule));
      await dispatch(getConnectionSchedule(connectionName));
    }
    if (activeTab === 'time') {
      await dispatch(updateConnectionTime(connectionName, updatedTimeSeries));
      await dispatch(getConnectionTime(connectionName));
    }
    if (activeTab === 'comments') {
      await dispatch(updateConnectionComments(connectionName, updatedComments));
      await dispatch(getConnectionComments(connectionName));
    }
    if (activeTab === 'labels') {
      await dispatch(updateConnectionLabels(connectionName, updatedLabels));
      await dispatch(getConnectionLabels(connectionName));
    }
    if (activeTab === 'data-streams') {
      await dispatch(
        updateConnectionDefaultDataStreamsMapping(
          connectionName,
          updatedDataStreamsMapping
        )
      );
      await dispatch(getConnectionDefaultDataStreamsMapping(connectionName));
    }
  };

  const renderTabContent = () => {
    if (activeTab === 'connection') {
      return (
        <ConnectionDetail
          connectionBasic={updatedConnectionBasic}
          setConnectionBasic={setUpdatedConnectionBasic}
        />
      );
    }
    if (activeTab === 'schedule') {
      return (
        <ScheduleDetail
          schedule={updatedSchedule}
          setSchedule={setUpdatedSchedule}
        />
      );
    }
    if (activeTab === 'time') {
      return (
        <div className="p-4">
          <TimeSeriesView
            timeSeries={updatedTimeSeries}
            setTimeSeries={setUpdatedTimeSeries}
          />
        </div>
      );
    }
    if (activeTab === 'comments') {
      return (
        <CommentsView
          comments={updatedComments}
          onChange={setUpdatedComments}
        />
      );
    }
    if (activeTab === 'labels') {
      return <LabelsView labels={updatedLabels} onChange={setUpdatedLabels} />;
    }
    if (activeTab === 'schemas') {
      return <SchemasView connectionName={connectionName} />;
    }
    if (activeTab === 'data-streams') {
      return (
        <DataStreamsMappingView
          dataStreamsMapping={updatedDataStreamsMapping}
          onChange={setUpdatedDataStreamsMapping}
        />
      );
    }
    return null;
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
      setActiveTab('connection');
    }
  }, [pageTab, tabMap]);

  const isDisabled = useMemo(() => {
    if (activeTab === 'labels') {
      return updatedLabels.some((label) => !label);
    }

    return false;
  }, [updatedLabels]);

  const onRemove = async () => {
    if (connectionBasic) {
      await ConnectionApiClient.deleteConnection(
        connectionBasic.connection_name ?? ''
      );
    }
  };

  return (
    <div className="">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
          <div className="text-xl font-semibold">{connectionName}</div>
        </div>
        <div className="flex space-x-4">
          <Button
            color="info"
            variant="text"
            label="Delete"
            onClick={() => setIsOpen(true)}
          />
          <Button
            color="primary"
            variant="contained"
            label="Save"
            className="w-40"
            onClick={onUpdate}
            disabled={isDisabled}
            loading={isUpdating}
          />
        </div>
      </div>
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
      </div>
      {renderTabContent()}
      <ConfirmDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        connection={connectionBasic}
        onConfirm={onRemove}
        nodeId={nodeId}
      />
    </div>
  );
};

export default ConnectionView;
