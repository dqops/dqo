import React, { useEffect, useMemo, useState } from "react";
import ConnectionLayout from "../../components/ConnectionLayout";
import SvgIcon from "../../components/SvgIcon";
import Tabs from "../../components/Tabs";
import { useHistory, useParams } from "react-router-dom";
import { CheckTypes, ROUTES } from "../../shared/routes";
import { useTree } from "../../contexts/treeContext";
import { useSelector } from "react-redux";
import TableDetails from "../../components/Connection/TableView/TableDetails";
import ScheduleDetail from "../../components/Connection/TableView/ScheduleDetail";
import ProfilingView from "../../components/Connection/TableView/ProfilingView";
import RecurringView from "../../components/Connection/TableView/RecurringView";
import PartitionedChecks from "../../components/Connection/TableView/PartitionedChecks";
import TableCommentView from "../../components/Connection/TableView/TableCommentView";
import TableLabelsView from "../../components/Connection/TableView/TableLabelsView";
import TableDataStream from "../../components/Connection/TableView/TableDataStream";
import TimestampsView from "../../components/Connection/TableView/TimestampsView";
import { findTreeNode } from "../../utils/tree";
import clsx from "clsx";
import { getFirstLevelState } from "../../redux/selectors";

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
    label: 'Data Streams',
    value: 'data-streams'
  },
  {
    label: 'Date and time columns',
    value: 'timestamps'
  }
];

type NavigationMenu = {
  label: string;
  value: string;
}

const navigations: NavigationMenu[] = [
  {
    label: 'Table metadata',
    value: CheckTypes.SOURCES
  },
  {
    label: 'Advanced profiling',
    value: CheckTypes.PROFILING
  },
  {
    label: 'Recurring checks',
    value: CheckTypes.RECURRING
  },
  {
    label: 'Partition checks',
    value: CheckTypes.PARTITIONED
  },
];

const TablePage = () => {
  const { connection, schema, table, tab: activeTab, checkTypes }: { connection: string, schema: string, table: string, tab: string, checkTypes: CheckTypes } = useParams();
  const {
    activeTab: pageTab,
    tabMap,
    setTabMap,
    treeData,
  } = useTree();
  const history = useHistory();
  const [tabs, setTabs] = useState(initTabs);
  const {
    isUpdatedTableBasic,
    isUpdatedComments,
    isUpdatedLabels,
    isUpdatedChecksUi,
    isUpdatedDailyRecurring,
    isUpdatedMonthlyRecurring,
    isUpdatedDailyPartitionedChecks,
    isUpdatedMonthlyPartitionedChecks,
    isUpdatedSchedule,
    isUpdatedDataStreamsMapping
  } = useSelector(getFirstLevelState(checkTypes));
  const isRecurringOnly = useMemo(() => checkTypes === CheckTypes.RECURRING, [checkTypes]);
  const isPartitionChecksOnly = useMemo(() => checkTypes === CheckTypes.PARTITIONED, [checkTypes]);
  const isProfilingChecksOnly = useMemo(() => checkTypes === CheckTypes.PROFILING, [checkTypes]);
  const showAllSubTabs = useMemo(
    () => !isRecurringOnly && !isPartitionChecksOnly && !isProfilingChecksOnly,
    [isRecurringOnly, isPartitionChecksOnly, isProfilingChecksOnly]
  );

  const onChangeTab = (tab: string) => {
    history.push(ROUTES.TABLE_LEVEL_PAGE(checkTypes, connection, schema, table, tab));
    setTabMap({
      ...tabMap,
      [pageTab]: tab
    });
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
        item.value === 'data-streams'
          ? { ...item, isUpdated: isUpdatedDataStreamsMapping }
          : item
      )
    );
  }, [isUpdatedDataStreamsMapping]);

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

  const activeNode = findTreeNode(treeData, pageTab);

  const description = useMemo(() => {
    if (isProfilingChecksOnly) {
      return 'Advanced profiling for ';
    }
    if (isRecurringOnly) {
      if (activeTab === 'monthly') {
        return 'Monthly recurring checks for ';
      } else {
        return 'Daily recurring checks for ';
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
    return ''
  }, [isProfilingChecksOnly, isRecurringOnly, isPartitionChecksOnly, activeTab]);

  const activeIndex = useMemo(() => {
    return navigations.findIndex((item) => item.value === checkTypes);
  }, [checkTypes]);
  const onChangeNavigation = async (item: NavigationMenu) => {
    if (item.value === CheckTypes.SOURCES) {
      history.push(ROUTES.TABLE_LEVEL_PAGE(item.value, connection, schema, table, 'detail'))
    } else if (item.value === CheckTypes.PROFILING) {
      console.log('')
      history.push(ROUTES.TABLE_LEVEL_PAGE(item.value, connection, schema, table, 'detail'));
    } else if (item.value === CheckTypes.RECURRING) {
      history.push(ROUTES.TABLE_LEVEL_PAGE(item.value, connection, schema, table, 'detail'));
    } else if (item.value === CheckTypes.PARTITIONED) {
      history.push(ROUTES.TABLE_LEVEL_PAGE(item.value, connection, schema, table, 'detail'));
    }
  };

  return (
    <ConnectionLayout>
      {!activeNode ? (
        <div />
      ) : (
        <div className="relative h-full flex flex-col">
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 items-center flex-shrink-0 pr-[340px]">
            <div className="flex items-center space-x-2 max-w-full">
              <SvgIcon name="table" className="w-5 h-5 shrink-0" />
              <div className="text-xl font-semibold truncate">{`${description}${connection}.${schema}.${table}`}</div>
            </div>
          </div>
          <div className="flex space-x-3 px-4 pt-2 border-b border-gray-300 pb-4 mb-2">
            {navigations.map((item, index) => (
              <div
                className={clsx("flex items-center cursor-pointer w-70", activeIndex === index ? "font-bold" : "")}
                key={item.value}
                onClick={() => onChangeNavigation(item)}
              >
                {activeIndex > index ? <SvgIcon name="chevron-left" className="w-3 mr-2" /> : ''}
                <span>{item.label}</span>
                {activeIndex < index ? <SvgIcon name="chevron-right" className="w-6 ml-2" /> : ''}
              </div>
            ))}
          </div>
          {isProfilingChecksOnly && (
            <ProfilingView />
          )}
          {isRecurringOnly && (
            <RecurringView />
          )}
          {isPartitionChecksOnly && (
            <PartitionedChecks />
          )}
          {showAllSubTabs && (
            <>
              <div className="border-b border-gray-300">
                <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
              </div>
              <div>
                {activeTab === 'detail' && <TableDetails />}
              </div>
              <div>
                {activeTab === 'schedule' && <ScheduleDetail />}
              </div>
              <div>
                {activeTab === 'comments' && <TableCommentView />}
              </div>
              <div>
                {activeTab === 'labels' && <TableLabelsView />}
              </div>
              <div>
                {activeTab === 'data-streams' && <TableDataStream />}
              </div>
              <div>
                {activeTab === 'timestamps' && <TimestampsView />}
              </div>
            </>
          )}
        </div>
      )}
    </ConnectionLayout>
  );
};

export default TablePage;
