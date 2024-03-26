import React, { useEffect, useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import ColumnNavigation from '../../components/ColumnNavigation';
import SvgIcon from '../../components/SvgIcon';
import Tabs from '../../components/Tabs';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setActiveFirstLevelUrl } from '../../redux/actions/source.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState,
  getSecondLevelTab
} from '../../redux/selectors';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import ColumnCommentsView from './ColumnCommentsView';
import ColumnDetails from './ColumnDetails';
import ColumnLabelsView from './ColumnLabelsView';
import ColumnMonitoringChecksView from './ColumnMonitoringChecksView';
import ColumnPartitionedChecksView from './ColumnPartitionedChecksView';
import ColumnProfilingView from './ColumnProfilingChecksView';

const initTabs = [
  {
    label: 'Column metadata',
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
  const {
    connection: connectionName,
    schema: schemaName,
    table: tableName,
    column: columnName,
    tab,
    checkTypes
  }: {
    connection: string;
    schema: string;
    table: string;
    column: string;
    tab: string;
    checkTypes: CheckTypes;
  } = useDecodedParams();
  const [tabs, setTabs] = useState(initTabs);

  const history = useHistory();
  const {
    isUpdatedColumnBasic,
    isUpdatedComments,
    isUpdatedLabels,
    isUpdatedChecksUi,
    isUpdatedDailyMonitoring,
    isUpdatedMonthlyMonitoring,
    isUpdatedDailyPartitionedChecks,
    isUpdatedMonthlyPartitionedChecks
  } = useSelector(getFirstLevelState(checkTypes));
  const isMonitoringOnly = useMemo(
    () => checkTypes === CheckTypes.MONITORING,
    [checkTypes]
  );
  const isPartitionCheckOnly = useMemo(
    () => checkTypes === CheckTypes.PARTITIONED,
    [checkTypes]
  );
  const isProfilingCheckOnly = useMemo(
    () => checkTypes === CheckTypes.PROFILING,
    [checkTypes]
  );
  const showAllSubTabs = () =>
    !isMonitoringOnly && !isPartitionCheckOnly && !isProfilingCheckOnly;

  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const dispatch = useActionDispatch();
  const activeTab = getSecondLevelTab(checkTypes, tab);

  const onChangeTab = (tab: string) => {
    dispatch(
      setActiveFirstLevelUrl(
        checkTypes,
        firstLevelActiveTab,
        ROUTES.COLUMN_LEVEL_PAGE(
          checkTypes,
          connectionName,
          schemaName,
          tableName,
          columnName,
          tab
        )
      )
    );
    history.push(
      ROUTES.COLUMN_LEVEL_PAGE(
        checkTypes,
        connectionName,
        schemaName,
        tableName,
        columnName,
        tab
      )
    );
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
        item.value === 'monitoring'
          ? {
              ...item,
              isUpdated: isUpdatedDailyMonitoring || isUpdatedMonthlyMonitoring
            }
          : item
      )
    );
  }, [isUpdatedDailyMonitoring, isUpdatedMonthlyMonitoring]);

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
      return 'Profiling checks for ';
    }
    if (isMonitoringOnly) {
      if (activeTab === 'monthly') {
        return 'Monthly monitoring checks for ';
      } else {
        return 'Daily monitoring checks for ';
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
    return '';
  }, [isProfilingCheckOnly, isMonitoringOnly, isPartitionCheckOnly, activeTab]);

  return (
    <>
      <div className="relative">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[360px]">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="column" className="w-5 h-5 shrink-0" />
            <div className="text-lg font-semibold truncate">{`${description}${connectionName}.${schemaName}.${tableName}.${columnName}`}</div>
          </div>
        </div>
        <ColumnNavigation />
        {isMonitoringOnly && <ColumnMonitoringChecksView />}
        {isPartitionCheckOnly && <ColumnPartitionedChecksView />}
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
    </>
  );
};

export default ColumnView;
