import React, { useEffect, useMemo, useState } from 'react';
import { ITreeNode } from '../../../shared/interfaces';
import SvgIcon from '../../SvgIcon';
import Tabs from '../../Tabs';
import ConnectionDetail from './ConnectionDetail';
import ScheduleDetail from './ScheduleDetail';
import Button from '../../Button';
import TimeSeriesView from '../TimeSeriesView';
import {
  CommentSpec,
  ConnectionBasicModel,
  DimensionsConfigurationSpec,
  RecurringScheduleSpec,
  TimeSeriesConfigurationSpec
} from '../../../api';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  getConnectionBasic,
  getConnectionComments,
  getConnectionDefaultDimensions,
  getConnectionLabels,
  getConnectionSchedule,
  getConnectionTime,
  updateConnectionBasic,
  updateConnectionComments,
  updateConnectionDefaultDimensions,
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
import { useTabs } from '../../../contexts/tabContext';
import DimensionsView from '../DimensionsView';

interface IConnectionViewProps {
  node: ITreeNode;
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
    label: 'Dimensions',
    value: 'dimensions'
  }
];

const ConnectionView = ({ node }: IConnectionViewProps) => {
  const [activeTab, setActiveTab] = useState('connection');
  const {
    connectionBasic,
    schedule,
    timeSeries,
    comments,
    labels,
    isUpdating,
    defaultDimensions
  } = useSelector((state: IRootState) => state.connection);
  const [updatedConnectionBasic, setUpdatedConnectionBasic] =
    useState<ConnectionBasicModel>();
  const [updatedSchedule, setUpdatedSchedule] =
    useState<RecurringScheduleSpec>();
  const [updatedTimeSeries, setUpdatedTimeSeries] =
    useState<TimeSeriesConfigurationSpec>();
  const [updatedComments, setUpdatedComments] = useState<CommentSpec[]>([]);
  const [updatedLabels, setUpdatedLabels] = useState<string[]>([]);
  const [updatedDimensions, setUpdatedDimensions] =
    useState<DimensionsConfigurationSpec>();
  const dispatch = useActionDispatch();
  const connectionName = useMemo(() => node.module, [node]);
  const history = useHistory();
  const { tabMap, setTabMap } = useTabs();

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
    setUpdatedDimensions(defaultDimensions);
  }, [defaultDimensions]);

  useEffect(() => {
    setUpdatedConnectionBasic(undefined);
    setUpdatedSchedule(undefined);
    setUpdatedTimeSeries(undefined);
    setUpdatedComments([]);
    setUpdatedLabels([]);
    setUpdatedDimensions(undefined);

    dispatch(getConnectionBasic(connectionName));
    dispatch(getConnectionSchedule(connectionName));
    dispatch(getConnectionTime(connectionName));
    dispatch(getConnectionComments(connectionName));
    dispatch(getConnectionLabels(connectionName));
    dispatch(getConnectionDefaultDimensions(connectionName));

    const searchQuery = qs.stringify({
      connection: connectionName
    });

    history.replace(`/connection?${searchQuery}`);
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
    if (activeTab === 'dimensions') {
      await dispatch(
        updateConnectionDefaultDimensions(connectionName, updatedDimensions)
      );
      await dispatch(getConnectionDefaultDimensions(connectionName));
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
    if (activeTab === 'dimensions') {
      return (
        <DimensionsView
          dimensions={updatedDimensions}
          onChange={setUpdatedDimensions}
        />
      );
    }
    return null;
  };

  const onChangeTab = (tab: string) => {
    setActiveTab(tab);
    setTabMap({
      ...tabMap,
      [node.module]: tab
    });
  };

  useEffect(() => {
    if (tabMap[node.module]) {
      setActiveTab(tabMap[node.module]);
    }
  }, [node, tabMap]);

  const isDisabled = useMemo(() => {
    if (activeTab === 'labels') {
      return updatedLabels.some((label) => !label);
    }

    return false;
  }, [updatedLabels]);

  return (
    <div className="">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
          <div className="text-xl font-semibold">{connectionName}</div>
        </div>
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
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
      </div>
      {renderTabContent()}
    </div>
  );
};

export default ConnectionView;
