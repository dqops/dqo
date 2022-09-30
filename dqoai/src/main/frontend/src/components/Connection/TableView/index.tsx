import React, { useState } from 'react';
import { ITreeNode } from '../../../shared/interfaces';
import SvgIcon from '../../SvgIcon';
import Button from '../../Button';
import Tabs from '../../Tabs';
import TableDetails from './TableDetails';
import ScheduleDetail from './ScheduleDetail';
import TableColumns from './TableColumns';
import LabelsTab from './LabelsTab';
import CommentsTab from './CommentsTab';

interface ITableViewProps {
  node: ITreeNode;
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
    label: 'Labels',
    value: 'labels'
  },
  {
    label: 'Comments',
    value: 'comments'
  },
  {
    label: 'Columns',
    value: 'columns'
  }
];

const TableView = ({ node }: ITableViewProps) => {
  const [activeTab, setActiveTab] = useState('table');

  const connectionName = node.key.split('.')[1] || '';
  const schemaName = node.key.split('.')[2] || '';

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
        {activeTab === 'table' && (
          <TableDetails
            connectionName={connectionName}
            schemaName={schemaName}
            tableName={node.module}
          />
        )}
      </div>
      <div>
        {activeTab === 'schedule' && (
          <ScheduleDetail
            connectionName={connectionName}
            schemaName={schemaName}
            tableName={node.module}
          />
        )}
      </div>
      <div>
        {activeTab === 'comments' && (
          <CommentsTab
            connectionName={connectionName}
            schemaName={schemaName}
            tableName={node.module}
          />
        )}
      </div>
      <div>
        {activeTab === 'labels' && (
          <LabelsTab
            connectionName={connectionName}
            schemaName={schemaName}
            tableName={node.module}
          />
        )}
      </div>
      <div>
        {activeTab === 'columns' && (
          <TableColumns
            connectionName={connectionName}
            schemaName={schemaName}
            tableName={node.module}
          />
        )}
      </div>
    </div>
  );
};

export default TableView;
