import React, { useEffect, useState } from "react";
import ConnectionLayout from "../../components/ConnectionLayout";
import SvgIcon from "../../components/SvgIcon";
import Tabs from "../../components/Tabs";
import { useHistory, useParams } from "react-router-dom";
import { ROUTES } from "../../shared/routes";
import { useTree } from "../../contexts/treeContext";
import { useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";
import TableDetails from "../../components/Connection/TableView/TableDetails";
import ScheduleDetail from "../../components/Connection/TableView/ScheduleDetail";
import AdhocView from "../../components/Connection/TableView/AdhocView";
import CheckpointsView from "../../components/Connection/TableView/CheckpointsView";
import PartitionedChecks from "../../components/Connection/TableView/PartitionedChecks";
import TableCommentView from "../../components/Connection/TableView/TableCommentView";
import TableLabelsView from "../../components/Connection/TableView/TableLabelsView";
import TableDataStream from "../../components/Connection/TableView/TableDataStream";
import TimestampsView from "../../components/Connection/TableView/TimestampsView";

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
    label: 'Ad-hoc checks',
    value: 'data-quality-checks'
  },
  {
    label: 'Checkpoints',
    value: 'checkpoints'
  },
  {
    label: 'Partitioned checks',
    value: 'partitioned-checks'
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
    label: 'Timestamps',
    value: 'timestamps'
  }
];

const TablePage = () => {
  const { connection, schema, table, tab: activeTab }: { connection: string, schema: string, table: string, tab: string } = useParams();
  const { activeTab: pageTab, tabMap, setTabMap } = useTree();
  const history = useHistory();
  const [tabs, setTabs] = useState(initTabs);
  const {
    isUpdatedTableBasic,
    isUpdatedComments,
    isUpdatedLabels,
    isUpdatedChecksUi,
    isUpdatedDailyCheckpoints,
    isUpdatedMonthlyCheckpoints,
    isUpdatedDailyPartitionedChecks,
    isUpdatedMonthlyPartitionedChecks,
    isUpdatedSchedule,
    isUpdatedDataStreamsMapping
  } = useSelector((state: IRootState) => state.table);

  const onChangeTab = (tab: string) => {
    history.push(ROUTES.TABLE_LEVEL_PAGE(connection, schema, table, tab));
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
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-13 items-center">
          <div className="flex items-center space-x-2">
            <SvgIcon name="database" className="w-5 h-5" />
            <div className="text-xl font-semibold">{`${connection}.${schema}.${table}`}</div>
          </div>
        </div>
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
          {activeTab === 'data-quality-checks' && <AdhocView />}
        </div>
        <div>
          {activeTab === 'checkpoints' && <CheckpointsView />}
        </div>
        <div>
          {activeTab === 'partitioned-checks' && <PartitionedChecks />}
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
      </div>
    </ConnectionLayout>
  );
};

export default TablePage;
