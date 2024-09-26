import React from 'react';
import { useDecodedParams } from '../../utils';
import SourceTables from '../Connection/TableView/SourceTables/SourceTables';
import DataLineageGraph from '../DataLineageGraph/DataLineageGraph';
import Tabs from '../Tabs';
const tabs = [
  { label: 'Source tables', value: 'data-lineage' },
  { label: 'Data lineage graph', value: 'data-lineage-graph' }
];
export default function DataLineage() {
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } = useDecodedParams();
  const [activeTab, setActiveTab] = React.useState('data-lineage');
  return (
    <div className="py-2">
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      </div>
      {activeTab === 'data-lineage' && <SourceTables />}
      {activeTab === 'data-lineage-graph' && (
        <div className="p-4">
          <DataLineageGraph
            connection={connection}
            schema={schema}
            table={table}
          />
        </div>
      )}
    </div>
  );
}
