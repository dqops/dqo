import React, { useEffect, useMemo, useState } from 'react';
import { ITreeNode } from '../../../shared/interfaces';
import SvgIcon from '../../SvgIcon';
import Tabs from '../../Tabs';
import ConnectionDetail from './ConnectionDetail';
import ScheduleDetail from './ScheduleDetail';
import Button from '../../Button';
import CommentsTab from './CommentsTab';
import LabelsTab from './LabelsTab';
import TimeSeriesTab from './TimeSeriesTab';
import { SchemaApiClient } from '../../../services/apiClient';
import { ConnectionBasicModel, SchemaModel } from '../../../api';
import SchemaDetail from '../SchemaView/SchemaDetail';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  getConnectionBasic,
  updateConnectionBasic
} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';

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
  const { connectionBasic, isUpdating } = useSelector(
    (state: IRootState) => state.connection
  );
  const [updatedConnectionBasic, setUpdatedConnectionBasic] =
    useState<ConnectionBasicModel>();
  const dispatch = useActionDispatch();
  const connectionName = useMemo(() => node.module, [node]);

  useEffect(() => {
    setUpdatedConnectionBasic(connectionBasic);
  }, [connectionBasic]);

  useEffect(() => {
    SchemaApiClient.getSchemas(connectionName).then((res) => {
      setSchemas(res.data);
    });
    dispatch(getConnectionBasic(connectionName));
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
      return <ScheduleDetail connectionName={connectionName} />;
    }
    if (activeTab === 'time') {
      return <TimeSeriesTab connectionName={connectionName} />;
    }
    if (activeTab === 'comments') {
      return <CommentsTab connectionName={connectionName} />;
    }
    if (activeTab === 'labels') {
      return <LabelsTab connectionName={connectionName} />;
    }
    return (
      <SchemaDetail
        schema={schemas.find((item) => item.schema_name === activeTab)}
      />
    );
  };

  const onUpdate = async () => {
    if (activeTab === 'connection') {
      dispatch(updateConnectionBasic(connectionName, updatedConnectionBasic));
    }
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
          loading={!isUpdating}
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
