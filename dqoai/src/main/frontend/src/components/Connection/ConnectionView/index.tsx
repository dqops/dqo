import React, { useEffect, useMemo, useState } from 'react';
import { ITreeNode } from '../../../shared/interfaces';
import SvgIcon from '../../SvgIcon';
import Tabs from '../../Tabs';
import ConnectionDetail from './ConnectionDetail';
import ScheduleDetail from './ScheduleDetail';
import Button from '../../Button';
import TimeSeriesTab from './TimeSeriesTab';
import { SchemaApiClient } from '../../../services/apiClient';
import {
  CommentSpec,
  ConnectionBasicModel,
  RecurringScheduleSpec,
  SchemaModel,
  TimeSeriesConfigurationSpec
} from '../../../api';
import SchemaDetail from '../SchemaView/SchemaDetail';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  getConnectionBasic,
  getConnectionComments,
  getConnectionLabels,
  getConnectionSchedule,
  getConnectionTime,
  updateConnectionBasic,
  updateConnectionComments,
  updateConnectionLabels,
  updateConnectionSchedule,
  updateConnectionTime
} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import CommentsView from '../CommentsView';
import LabelsView from '../LabelsView';

interface IConnectionViewProps {
  node: ITreeNode;
}

const initTabs = [
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
  }
];

const ConnectionView = ({ node }: IConnectionViewProps) => {
  const [activeTab, setActiveTab] = useState('connection');
  const [schemas, setSchemas] = useState<SchemaModel[]>([]);
  const [tabs, setTabs] = useState(initTabs);
  const {
    connectionBasic,
    schedule,
    timeSeries,
    comments,
    labels,
    isUpdating
  } = useSelector((state: IRootState) => state.connection);
  const [updatedConnectionBasic, setUpdatedConnectionBasic] =
    useState<ConnectionBasicModel>();
  const [updatedSchedule, setUpdatedSchedule] =
    useState<RecurringScheduleSpec>();
  const [updatedTimeSeries, setUpdatedTimeSeries] =
    useState<TimeSeriesConfigurationSpec>();
  const [updatedComments, setUpdatedComments] = useState<CommentSpec[]>([]);
  const [updatedLabels, setUpdatedLabels] = useState<string[]>([]);
  const dispatch = useActionDispatch();
  const connectionName = useMemo(() => node.module, [node]);

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
    SchemaApiClient.getSchemas(connectionName).then((res) => {
      setSchemas(res.data);
    });

    setUpdatedConnectionBasic(undefined);
    setUpdatedSchedule(undefined);
    setUpdatedTimeSeries(undefined);
    setUpdatedComments([]);
    setUpdatedLabels([]);

    dispatch(getConnectionBasic(connectionName));
    dispatch(getConnectionSchedule(connectionName));
    dispatch(getConnectionTime(connectionName));
    dispatch(getConnectionComments(connectionName));
    dispatch(getConnectionLabels(connectionName));
  }, [connectionName]);

  useEffect(() => {
    setTabs([
      ...initTabs,
      ...schemas.map((item) => ({
        label: item.schema_name || '',
        value: item.schema_name || ''
      }))
    ]);
  }, [schemas]);

  const onUpdate = async () => {
    if (activeTab === 'connection') {
      dispatch(updateConnectionBasic(connectionName, updatedConnectionBasic));
    }
    if (activeTab === 'schedule') {
      dispatch(updateConnectionSchedule(connectionName, updatedSchedule));
    }
    if (activeTab === 'time') {
      dispatch(updateConnectionTime(connectionName, updatedTimeSeries));
    }
    if (activeTab === 'comments') {
      dispatch(updateConnectionComments(connectionName, updatedComments));
    }
    if (activeTab === 'labels') {
      dispatch(updateConnectionLabels(connectionName, updatedLabels));
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
        <TimeSeriesTab
          timeSeries={updatedTimeSeries}
          setTimeSeries={setUpdatedTimeSeries}
        />
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
    return (
      <SchemaDetail
        schema={schemas.find((item) => item.schema_name === activeTab)}
      />
    );
  };

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
          loading={isUpdating}
        />
      </div>
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      </div>
      {renderTabContent()}
    </div>
  );
};

export default ConnectionView;
