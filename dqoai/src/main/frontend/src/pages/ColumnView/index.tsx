import React, { useEffect, useMemo, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import SvgIcon from '../../components/SvgIcon';
import Tabs from '../../components/Tabs';
import ColumnDetails from './ColumnDetails';
import { useTree } from '../../contexts/treeContext';
import ColumnCommentsView from './ColumnCommentsView';
import ColumnLabelsView from './ColumnLabelsView';
import ColumnRecurringChecksView from './ColumnRecurringChecksView';
import ColumnProfilingView from './ColumnProfilingChecksView';
import ColumnPartitionedChecksView from './ColumnPartitionedChecksView';
import { useSelector } from 'react-redux';
import { CheckTypes, ROUTES } from "../../shared/routes";
import ConnectionLayout from "../../components/ConnectionLayout";
import { getFirstLevelState } from "../../redux/selectors";
import ColumnNavigation from "../../components/ColumnNavigation";

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
  const { connection: connectionName, schema: schemaName, table: tableName, column: columnName, tab: activeTab, checkTypes }: { connection: string, schema: string, table: string, column: string, tab: string, checkTypes: CheckTypes } = useParams();
  const [tabs, setTabs] = useState(initTabs);

  const history = useHistory();
  const { activeTab: pageTab, tabMap, setTabMap } = useTree();
  const {
    isUpdatedColumnBasic,
    isUpdatedComments,
    isUpdatedLabels,
    isUpdatedChecksUi,
    isUpdatedDailyRecurring,
    isUpdatedMonthlyRecurring,
    isUpdatedDailyPartitionedChecks,
    isUpdatedMonthlyPartitionedChecks
  } = useSelector(getFirstLevelState(checkTypes));
  const isRecurringOnly = useMemo(() => checkTypes === CheckTypes.RECURRING, [checkTypes]);
  const isPartitionCheckOnly = useMemo(() => checkTypes === CheckTypes.PARTITIONED, [checkTypes]);
  const isProfilingCheckOnly = useMemo(() => checkTypes === CheckTypes.PROFILING, [checkTypes]);
  const showAllSubTabs =(
    () => !isRecurringOnly && !isPartitionCheckOnly && !isProfilingCheckOnly
  );
   // will update more in next tasks
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
        item.value === 'recurring'
          ? {
              ...item,
              isUpdated:
                isUpdatedDailyRecurring || isUpdatedMonthlyRecurring
            }
          : item
      )
    );
  }, [isUpdatedDailyRecurring, isUpdatedMonthlyRecurring]);

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

  const description = useMemo(() => {
    if (isProfilingCheckOnly) {
      return 'Advanced profiling for ';
    }
    if (isRecurringOnly) {
      if (activeTab === 'monthly') {
        return 'Monthly recurring checks for ';
      } else {
        return 'Daily recurring checks for ';
      }
    }
    if (isPartitionCheckOnly) {
      if (activeTab === 'monthly') {
        return 'Monthly partition checks for ';
      } else {
        return 'Daily partition checks for ';
      }
    }

    if (activeTab === 'detail') {
      return 'Data source configuration for ';
    }
    return ''
  }, [isProfilingCheckOnly, isRecurringOnly, isPartitionCheckOnly, activeTab]);

  return (
    <ConnectionLayout>
      <div className="relative">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[360px]">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="column" className="w-5 h-5 shrink-0" />
            <div className="text-xl font-semibold truncate">{`${description}${connectionName}.${schemaName}.${tableName}.${columnName}`}</div>
          </div>
        </div>
        <ColumnNavigation />
        {isRecurringOnly && (
          <ColumnRecurringChecksView />
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
        {showAllSubTabs() && (
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
