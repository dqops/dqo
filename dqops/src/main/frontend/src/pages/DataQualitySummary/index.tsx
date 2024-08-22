import React from 'react';
import Tabs from '../../components/Tabs';
import ColumnListView from '../ColumnListView/ColumnListView';
import TableListView from '../TableListView/TableListView';
const tabs = [
  {
    label: 'Tables',
    value: 'tables'
  },
  {
    label: 'Columns',
    value: 'columns'
  }
];
export default function DataQualitySummary() {
  const [activeTab, setActiveTab] = React.useState('tables');
  return (
    <div className="py-2">
      <div className="border-b border-gray-300 px-0">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      </div>
      {activeTab === 'tables' && <TableListView />}
      {activeTab === 'columns' && <ColumnListView />}
    </div>
  );
}
