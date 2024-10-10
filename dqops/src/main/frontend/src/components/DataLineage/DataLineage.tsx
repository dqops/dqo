import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setActiveTabState } from '../../redux/actions/source.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import SourceTables from '../Connection/TableView/SourceTables/SourceTables';
import DataLineageGraph from '../DataLineageGraph/DataLineageGraph';
import Tabs from '../Tabs';
const tabs = [
  { label: 'Data lineage graph', value: 'data-lineage-graph' },
  { label: 'Source tables', value: 'data-lineage' },
  { label: 'Target tables', value: 'target-tables' }
];
export default function DataLineage() {
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } = useDecodedParams();
  const firstLevelActiveTab = useSelector(
    getFirstLevelActiveTab(CheckTypes.SOURCES)
  );
  const dispatch = useActionDispatch();
  const { showSourceTables } = useSelector(
    getFirstLevelState(CheckTypes.SOURCES)
  );
  const [activeTab, setActiveTab] = React.useState(
    showSourceTables ? 'data-lineage' : 'data-lineage-graph'
  );

  const onChangeActiveTab = (tab: string) => {
    dispatch(setActiveTabState(CheckTypes.SOURCES, firstLevelActiveTab, {}));
    setActiveTab(tab);
  };

  useEffect(() => {
    if (showSourceTables) {
      setActiveTab('data-lineage');
    }
  }, [showSourceTables]);

  return (
    <div className="py-2">
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeActiveTab} />
      </div>
      {activeTab === 'data-lineage' && <SourceTables />}
      {activeTab === 'target-tables' && <SourceTables isTarget />}
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
