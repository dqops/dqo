import React, { useEffect, useState } from "react";
import ConnectionLayout from "../../components/ConnectionLayout";
import SvgIcon from "../../components/SvgIcon";
import Tabs from "../../components/Tabs";
import { useHistory, useLocation, useParams } from "react-router-dom";
import { CheckTypes, ROUTES } from "../../shared/routes";
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
import qs from 'query-string';

const initSourceTabs = [
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
    label: 'Default data stream template',
    value: 'data-streams'
  }
];
const initCheckTabs = [
  {
    label: 'Schedule',
    value: 'schedule'
  },
  {
    label: 'Schemas',
    value: 'schemas'
  }
];

const ConnectionPage = () => {
  const { connection, tab: activeTab, checkTypes }: { connection: string, tab: string, checkTypes: string } = useParams();
  const [tabs, setTabs] = useState(checkTypes === CheckTypes.SOURCES ? initSourceTabs : initCheckTabs);
  const history = useHistory();
  const { tabMap, setTabMap, activeTab: pageTab } = useTree();
  const {
    isUpdatedConnectionBasic,
    isUpdatedSchedule,
    isUpdatedComments,
    isUpdatedLabels,
    isUpdatedDataStreamsMapping
  } = useSelector((state: IRootState) => state.connection);
  const location = useLocation() as any;
  const { import_schema, create_success } = qs.parse(location.search);

  const onChangeTab = (tab: string) => {
    history.push(ROUTES.CONNECTION_DETAIL(checkTypes, connection, tab));

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

  useEffect(() => {
    setTabs(checkTypes === CheckTypes.SOURCES ? initSourceTabs : initCheckTabs)
  }, [checkTypes])

  useEffect(() => {
    if (checkTypes === CheckTypes.SOURCES) {
      return;
    }

    if (import_schema === 'true') {
      setTabs([
        {
          label: 'Schemas',
          value: 'schemas'
        }
      ]);
    } else {
      setTabs(initCheckTabs)
    }
  }, [import_schema, checkTypes]);

  return (
    <ConnectionLayout>
      <div className="relative">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="database" className="w-5 h-5 shrink-0" />
            <div className="text-xl font-semibold truncate">{connection || ''}</div>
          </div>
        </div>
        {create_success !== 'true' && (
          <div className="border-b border-gray-300">
            <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
          </div>
        )}
        {activeTab === 'detail' && <ConnectionDetail />}
        {activeTab === 'schedule' && <ScheduleDetail />}
        {activeTab === 'comments' && <ConnectionCommentView />}
        {activeTab === 'labels' && <ConnectionLabelsView />}
        {activeTab === 'schemas' && (
          import_schema === 'true' ? <SourceSchemasView /> : <SchemasView />
        )}
        {activeTab === 'data-streams' && <ConnectionDataStream />}
      </div>
    </ConnectionLayout>
  )
};

export default ConnectionPage;
