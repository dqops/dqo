import React, { useEffect, useState } from "react";
import ConnectionLayout from "../../components/ConnectionLayout";
import SvgIcon from "../../components/SvgIcon";
import Tabs from "../../components/Tabs";
import { useHistory, useParams } from "react-router-dom";
import { ROUTES } from "../../shared/routes";
import { useTree } from "../../contexts/treeContext";
import { useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";
import ConnectionDetail from "../../components/Connection/ConnectionView/ConnectionDetail";
import ScheduleDetail from "../../components/Connection/ConnectionView/ScheduleDetail";
import ConnectionCommentView from "../../components/Connection/ConnectionView/ConnectionCommentView";
import ConnectionLabelsView from "../../components/Connection/ConnectionView/ConnectionLabelsView";
import SourceSchemasView from "../../components/Connection/ConnectionView/SourceSchemasView";
import SchemasView from "../../components/Connection/ConnectionView/SchemasView";
import ConnectionDataStream from "../../components/Connection/ConnectionView/ConnectionDataStream";

const initTabs = [
  {
    label: 'Connection',
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
    label: 'Schemas',
    value: 'schemas'
  },
  {
    label: 'Data Streams',
    value: 'data-streams'
  }
];

const ConnectionPage = () => {
  const { connection, tab: activeTab }: { connection: string, tab: string } = useParams();
  const [tabs, setTabs] = useState(initTabs);
  const history = useHistory();
  const { tabMap, setTabMap, activeTab: pageTab } = useTree();
  const {
    isUpdatedConnectionBasic,
    isUpdatedSchedule,
    isUpdatedComments,
    isUpdatedLabels,
    isUpdatedDataStreamsMapping
  } = useSelector((state: IRootState) => state.connection);
  const [showMetaData, setShowMetaData] = useState(false);

  const onChangeTab = (tab: string) => {
    history.push(ROUTES.CONNECTION_DETAIL(connection, tab));

    setTabMap({
      ...tabMap,
      [pageTab]: tab
    });
  };

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'detail'
          ? { ...item, isUpdated: isUpdatedConnectionBasic }
          : item
      )
    );
  }, [isUpdatedConnectionBasic]);

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
        item.value === 'data-streams'
          ? { ...item, isUpdated: isUpdatedDataStreamsMapping }
          : item
      )
    );
  }, [isUpdatedDataStreamsMapping]);

  return (
    <ConnectionLayout>
      <div className="relative">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
          <div className="flex items-center space-x-2">
            <SvgIcon name="database" className="w-5 h-5" />
            <div className="text-xl font-semibold">{connection || ''}</div>
          </div>
        </div>
        <div className="border-b border-gray-300">
          <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
        </div>
        {activeTab === 'detail' && <ConnectionDetail />}
        {activeTab === 'schedule' && <ScheduleDetail />}
        {activeTab === 'comments' && <ConnectionCommentView />}
        {activeTab === 'labels' && <ConnectionLabelsView />}
        {activeTab === 'schemas' && (
          showMetaData ? <SourceSchemasView /> : <SchemasView />
        )}
        {activeTab === 'data-streams' && <ConnectionDataStream />}
      </div>
    </ConnectionLayout>
  )
};

export default ConnectionPage;
