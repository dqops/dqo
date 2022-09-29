import React, { useState } from 'react';
import { ITreeNode } from '../../../shared/interfaces';
import SvgIcon from '../../SvgIcon';
import Tabs from '../../Tabs';
import ConnectionDetail from './ConnectionDetail';
import ScheduleDetail from './ScheduleDetail';
import Button from '../../Button';
import CommentsTab from './CommentsTab';
import LabelsTab from './LabelsTab';
import TimeSeriesTab from './TimeSeriesTab';

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
  }
];

const ConnectionView = ({ node }: IConnectionViewProps) => {
  const [activeTab, setActiveTab] = useState('connection');

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
      <div>
        {activeTab === 'connection' && (
          <ConnectionDetail connectionName={node.module} />
        )}
        {activeTab === 'schedule' && (
          <ScheduleDetail connectionName={node.module} />
        )}
        {activeTab === 'time' && <TimeSeriesTab connectionName={node.module} />}
        {activeTab === 'comments' && (
          <CommentsTab connectionName={node.module} />
        )}
        {activeTab === 'labels' && <LabelsTab connectionName={node.module} />}
      </div>
    </div>
  );
};

export default ConnectionView;
