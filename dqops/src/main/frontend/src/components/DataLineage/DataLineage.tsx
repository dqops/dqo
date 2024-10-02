import React from 'react';
import { useSelector } from 'react-redux';
import { getFirstLevelState } from '../../redux/selectors';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import SourceTables from '../Connection/TableView/SourceTables/SourceTables';
import DataLineageGraph from '../DataLineageGraph/DataLineageGraph';
import Tabs from '../Tabs';
const tabs = [
  { label: 'Data lineage graph', value: 'data-lineage-graph' },
  { label: 'Source tables', value: 'data-lineage' }
];
export default function DataLineage() {
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } = useDecodedParams();
  const { showSourceTables } = useSelector(
    getFirstLevelState(CheckTypes.SOURCES)
  );
  const [activeTab, setActiveTab] = React.useState(
    showSourceTables ? 'data-lineage' : 'data-lineage-graph'
  );

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
            configureSourceTables={() => setActiveTab('data-lineage')}
          />
        </div>
      )}
    </div>
  );
}
