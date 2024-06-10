import React, { useEffect, useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import MonitoringView from '../../components/Connection/TableView/MonitoringView';
import PartitionedChecks from '../../components/Connection/TableView/PartitionedChecks';
import ProfilingView from '../../components/Connection/TableView/ProfilingView';
import ScheduleDetail from '../../components/Connection/TableView/ScheduleDetail';
import TableCommentView from '../../components/Connection/TableView/TableCommentView';
import TableDataGroupingConfiguration from '../../components/Connection/TableView/TableDataGroupingConfigurations';
import TableDetails from '../../components/Connection/TableView/TableDetails';
import TableIncidentsNotificationsView from '../../components/Connection/TableView/TableIncidentsNotificationsView';
import TableLabelsView from '../../components/Connection/TableView/TableLabelsView';
import TimestampsView from '../../components/Connection/TableView/TimestampsView';
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

const initTabs = [
  {
    label: 'Table',
    value: 'detail'
  },
  {
    label: 'Schedule',
    value: 'schedule'
  },
  {
    label: 'Comments',
    value: 'comments'
  },
  {
    label: 'Labels',
    value: 'labels'
  },
  {
    label: 'Data groupings',
    value: 'data-groupings'
  },
  {
    label: 'Date and time columns',
    value: 'timestamps'
  },
  {
    label: 'Incident configuration',
    value: 'incident_configuration'
  }
];

const TablePage = () => {
  const {
    connection,
    schema,
    table,
    tab,
    checkTypes
  }: {
    connection: string;
    schema: string;
    table: string;
    tab: string;
    checkTypes: CheckTypes;
  } = useDecodedParams();
  const history = useHistory();
  const [tabs, setTabs] = useState(initTabs);
  const {
    isUpdatedTableBasic,
    isUpdatedComments,
    isUpdatedLabels,
    isUpdatedChecksUi,
    isUpdatedDailyMonitoring,
    isUpdatedMonthlyMonitoring,
    isUpdatedDailyPartitionedChecks,
    isUpdatedMonthlyPartitionedChecks,
    isUpdatedSchedule,
    isUpdatedDataGroupingConfiguration
  } = useSelector(getFirstLevelState(checkTypes));
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const dispatch = useActionDispatch();
  const activeTab = getSecondLevelTab(checkTypes, tab);

  const isMonitoringOnly = useMemo(
    () => checkTypes === CheckTypes.MONITORING,
    [checkTypes]
  );
  const isPartitionChecksOnly = useMemo(
    () => checkTypes === CheckTypes.PARTITIONED,
    [checkTypes]
  );
  const isProfilingChecksOnly = useMemo(
    () => checkTypes === CheckTypes.PROFILING,
    [checkTypes]
  );
  const showAllSubTabs = useMemo(
    () => !isMonitoringOnly && !isPartitionChecksOnly && !isProfilingChecksOnly,
    [isMonitoringOnly, isPartitionChecksOnly, isProfilingChecksOnly]
  );

  const onChangeTab = (tab: string) => {
    dispatch(
      setActiveFirstLevelUrl(
        checkTypes,
        firstLevelActiveTab,
        ROUTES.TABLE_LEVEL_PAGE(checkTypes, connection, schema, table, tab)
      )
    );

    history.push(
      ROUTES.TABLE_LEVEL_PAGE(checkTypes, connection, schema, table, tab)
    );
  };

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'detail'
          ? { ...item, isUpdated: isUpdatedTableBasic }
          : item
      )
    );
  }, [isUpdatedTableBasic]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'schedule'
          ? { ...item, isUpdated: isUpdatedSchedule }
          : item
      )
    );
  }, [isUpdatedSchedule]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'data-groupings'
          ? { ...item, isUpdated: isUpdatedDataGroupingConfiguration }
          : item
      )
    );
  }, [isUpdatedDataGroupingConfiguration]);

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
    if (isProfilingChecksOnly) {
      return 'Profiling checks for ';
    }
    if (isMonitoringOnly) {
      if (activeTab === 'monthly') {
        return 'Monthly monitoring checks for ';
      } else {
        return 'Daily monitoring checks for ';
      }
    }
    if (isPartitionChecksOnly) {
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
  }, [
    isProfilingChecksOnly,
    isMonitoringOnly,
    isPartitionChecksOnly,
    activeTab
  ]);

  return (
    <>
      <div className="relative h-full min-h-full flex flex-col">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 items-center flex-shrink-0 pr-[340px]">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="table" className="w-5 h-5 shrink-0" />
            <div className="text-lg font-semibold truncate">{`${description}${connection}.${schema}.${table}`}</div>
          </div>
        </div>
        {isProfilingChecksOnly && <ProfilingView />}
        {isMonitoringOnly && <MonitoringView />}
        {isPartitionChecksOnly && <PartitionedChecks />}
        {showAllSubTabs && (
          <>
            <div className="border-b border-gray-300">
              <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
            </div>
            <div>{activeTab === 'detail' && <TableDetails />}</div>
            <div>{activeTab === 'schedule' && <ScheduleDetail />}</div>
            <div>{activeTab === 'comments' && <TableCommentView />}</div>
            <div>{activeTab === 'labels' && <TableLabelsView />}</div>
            <div>
              {activeTab === 'data-groupings' && (
                <TableDataGroupingConfiguration />
              )}
            </div>

            <div>{activeTab === 'timestamps' && <TimestampsView />}</div>
            <div>
              {activeTab === 'incident_configuration' && (
                <TableIncidentsNotificationsView />
              )}
            </div>
          </>
        )}
      </div>
    </>
  );
};

export default TablePage;
