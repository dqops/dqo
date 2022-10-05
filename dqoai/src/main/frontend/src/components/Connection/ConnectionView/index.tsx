import React, { useEffect, useState } from 'react';
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
import { SchemaModel } from '../../../api';
import SchemaDetail from '../SchemaView/SchemaDetail';

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

  useEffect(() => {
    SchemaApiClient.getSchemas(node.module).then((res) => {
      setSchemas(res.data);
    });
  }, [node]);

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
      return <ConnectionDetail connectionName={node.module} />;
    }
    if (activeTab === 'schedule') {
      return <ScheduleDetail connectionName={node.module} />;
    }
    if (activeTab === 'time') {
      return <TimeSeriesTab connectionName={node.module} />;
    }
    if (activeTab === 'comments') {
      return <CommentsTab connectionName={node.module} />;
    }
    if (activeTab === 'labels') {
      return <LabelsTab connectionName={node.module} />;
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
          <div className="text-xl font-semibold">{node.module}</div>
        </div>
        <Button
          color="primary"
          variant="contained"
          label="Save"
          className="w-40"
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
