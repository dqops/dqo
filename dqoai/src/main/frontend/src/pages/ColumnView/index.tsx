import React, { useEffect, useMemo, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import SvgIcon from '../../components/SvgIcon';
import Tabs from '../../components/Tabs';
import ColumnDetails from './ColumnDetails';
import { useTree } from '../../contexts/treeContext';
import ColumnCommentsView from './ColumnCommentsView';
import ColumnLabelsView from './ColumnLabelsView';
import CheckpointsView from './CheckpointsView';
import ColumnProfilingView from './ColumnProfilingView';
import ColumnPartitionedChecksView from './ColumnPartitionedChecksView';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { CheckTypes, ROUTES } from "../../shared/routes";
import ConnectionLayout from "../../components/ConnectionLayout";

const initTabs = [
  {
    label: 'Column',
    value: 'detail'
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

const ColumnView = () => {
  const { connection: connectionName, schema: schemaName, table: tableName, column: columnName, tab: activeTab, checkTypes }: { connection: string, schema: string, table: string, column: string, tab: string, checkTypes: string } = useParams();
  const [tabs, setTabs] = useState(initTabs);

  const history = useHistory();
  const { activeTab: pageTab, tabMap, setTabMap } = useTree();
  const {
    isUpdatedColumnBasic,
    isUpdatedComments,
    isUpdatedLabels,
    isUpdatedChecksUi,
    isUpdatedDailyCheckpoints,
    isUpdatedMonthlyCheckpoints,
    isUpdatedDailyPartitionedChecks,
    isUpdatedMonthlyPartitionedChecks
  } = useSelector((state: IRootState) => state.column);
  const isCheckpointOnly = useMemo(() => checkTypes === CheckTypes.CHECKS, [checkTypes]);
  const isPartitionCheckOnly = useMemo(() => checkTypes === CheckTypes.PARTITION, [checkTypes]);
  const isProfilingCheckOnly = useMemo(() => checkTypes === CheckTypes.PROFILING, [checkTypes]);
  const showAllSubTabs = useMemo(
    () => !isCheckpointOnly && !isPartitionCheckOnly && !isProfilingCheckOnly,
    [isCheckpointOnly]
  ); // will update more in next tasks
  // useEffect(() => {
  //   if (tabMap[pageTab]) {
  //     setActiveTab(tabMap[pageTab]);
  //   } else {
  //     setActiveTab('column');
  //   }
  // }, [pageTab, tabMap]);

  const onChangeTab = (tab: string) => {
    history.push(ROUTES.COLUMN_LEVEL_PAGE(checkTypes, connectionName, schemaName, tableName, columnName, tab));
    setTabMap({
      ...tabMap,
      [pageTab]: tab
    });
  };

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'column'
          ? { ...item, isUpdated: isUpdatedColumnBasic }
          : item
      )
    );
  }, [isUpdatedColumnBasic]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'comments'
          ? { ...item, isUpdated: isUpdatedComments }
          : item
      )
    );
  }, [isUpdatedComments]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'labels' ? { ...item, isUpdated: isUpdatedLabels } : item
      )
    );
  }, [isUpdatedLabels]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'data-quality-checks'
          ? { ...item, isUpdated: isUpdatedChecksUi }
          : item
      )
    );
  }, [isUpdatedChecksUi]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'checkpoints'
          ? {
              ...item,
              isUpdated:
                isUpdatedDailyCheckpoints || isUpdatedMonthlyCheckpoints
            }
          : item
      )
    );
  }, [isUpdatedDailyCheckpoints, isUpdatedMonthlyCheckpoints]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'partitioned-checks'
          ? {
              ...item,
              isUpdated:
                isUpdatedDailyPartitionedChecks ||
                isUpdatedMonthlyPartitionedChecks
            }
          : item
      )
    );
  }, [isUpdatedDailyPartitionedChecks, isUpdatedMonthlyPartitionedChecks]);

  return (
    <ConnectionLayout>
      <div className="relative">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
          <div className="flex items-center space-x-2">
            <SvgIcon name="column" className="w-5 h-5" />
            <div className="text-xl font-semibold">{`${connectionName}.${schemaName}.${tableName}.${columnName}`}</div>
          </div>
        </div>
        {isCheckpointOnly && (
          <CheckpointsView />
        )}
        {isPartitionCheckOnly && (
          <ColumnPartitionedChecksView />
        )}
        {isProfilingCheckOnly && (
          <ColumnProfilingView
            connectionName={connectionName}
            schemaName={schemaName}
            tableName={tableName}
            columnName={columnName}
          />
        )}
        {showAllSubTabs && (
          <>
            <div className="border-b border-gray-300">
              <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
            </div>
            <div>
              {activeTab === 'detail' && (
                <ColumnDetails
                  connectionName={connectionName}
                  schemaName={schemaName}
                  tableName={tableName}
                  columnName={columnName}
                />
              )}
              {activeTab === 'comments' && (
                <ColumnCommentsView
                  connectionName={connectionName}
                  schemaName={schemaName}
                  tableName={tableName}
                  columnName={columnName}
                />
              )}
              {activeTab === 'labels' && (
                <ColumnLabelsView
                  connectionName={connectionName}
                  schemaName={schemaName}
                  tableName={tableName}
                  columnName={columnName}
                />
              )}
            </div>
          </>
        )}
      </div>
    </ConnectionLayout>
  );
};

export default ColumnView;
