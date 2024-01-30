import React, { useEffect, useState } from 'react';
import ConnectionLayout from '../../components/ConnectionLayout';
import SvgIcon from '../../components/SvgIcon';
import Tabs from '../../components/Tabs';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useSelector } from 'react-redux';
import ConnectionDetail from '../../components/Connection/ConnectionView/ConnectionDetail';
import ScheduleDetail from '../../components/Connection/ConnectionView/ScheduleDetail';
import ConnectionCommentView from '../../components/Connection/ConnectionView/ConnectionCommentView';
import ConnectionLabelsView from '../../components/Connection/ConnectionView/ConnectionLabelsView';
import SourceSchemasView from '../../components/Connection/ConnectionView/SourceSchemasView';
import SchemasView from '../../components/Connection/ConnectionView/SchemasView';
import ConnectionDefaultGroupingConfiguration from '../../components/Connection/ConnectionView/ConnectionDataStream';
import qs from 'query-string';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';
import { IncidentsNotificationsView } from '../../components/Connection/ConnectionView/IncidentsNotificationsView';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setActiveFirstLevelUrl } from '../../redux/actions/source.actions';

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
    label: 'Default grouping template',
    value: 'data-groupings'
  },
  {
    label: 'Incidents and Notifications',
    value: 'incidents'
  }
];
const initCheckTabs = [
  // {
  //   label: 'Schedule',
  //   value: 'schedule'
  // },
  {
    label: 'Schemas',
    value: 'schemas'
  }
];

const ConnectionPage = () => {
  const {
    connection,
    tab: activeTab,
    checkTypes
  }: { connection: string; tab: string; checkTypes: CheckTypes } = useParams();
  const [tabs, setTabs] = useState(
    checkTypes === CheckTypes.SOURCES ? initSourceTabs : initCheckTabs
  );
  const history = useHistory();
  const location = useLocation() as any;
  const { import_schema, create_success } = qs.parse(location.search);

  const { isUpdatedConnectionBasic } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const dispatch = useActionDispatch();

  const onChangeTab = (tab: string) => {
    dispatch(
      setActiveFirstLevelUrl(
        checkTypes,
        firstLevelActiveTab,
        ROUTES.CONNECTION_DETAIL(checkTypes, connection, tab)
      )
    );
    history.push(ROUTES.CONNECTION_DETAIL(checkTypes, connection, tab));
  };

  useEffect(() => {
    if (isUpdatedConnectionBasic && tabs.length) {
      setTabs(
        tabs.map((item) =>
          item.value === 'detail'
            ? { ...item, isUpdated: isUpdatedConnectionBasic }
            : item
        )
      );
    }
  }, [isUpdatedConnectionBasic]);

  // useEffect(() => {
  //   setTabs(
  //     tabs.map((item) =>
  //       item.value === 'schedule'
  //         ? { ...item, isUpdated: isUpdatedSchedule }
  //         : item
  //     )
  //   );
  // }, [isUpdatedSchedule, tabs]);
  //
  // useEffect(() => {
  //   setTabs(
  //     tabs.map((item) =>
  //       item.value === 'comments'
  //         ? { ...item, isUpdated: isUpdatedComments }
  //         : item
  //     )
  //   );
  // }, [isUpdatedComments, tabs]);
  //
  // useEffect(() => {
  //   setTabs(
  //     tabs.map((item) =>
  //       item.value === 'labels' ? { ...item, isUpdated: isUpdatedLabels } : item
  //     )
  //   );
  // }, [isUpdatedLabels, tabs]);
  //
  // useEffect(() => {
  //   setTabs(
  //     tabs.map((item) =>
  //       item.value === 'data-streams'
  //         ? { ...item, isUpdated: isUpdatedDataStreamsMapping }
  //         : item
  //     )
  //   );
  // }, [isUpdatedDataStreamsMapping, tabs]);

  useEffect(() => {
    setTabs(checkTypes === CheckTypes.SOURCES ? initSourceTabs : initCheckTabs);
  }, [checkTypes]);

  useEffect(() => {
    if (checkTypes === CheckTypes.SOURCES) {
      return;
    }
    // if (import_schema === 'true') {
    setTabs([
      {
        label: 'Schemas',
        value: 'schemas'
      }
    ]);
    // } else {
    //   setTabs(initCheckTabs);
    // }
    onChangeTab('schemas');
  }, [import_schema, checkTypes]);

  return (
    <ConnectionLayout>
      <div className="relative">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="database" className="w-5 h-5 shrink-0" />
            <div className="text-xl font-semibold truncate">
              {connection || ''}
            </div>
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
        {activeTab === 'schemas' &&
          (import_schema === 'true' ? <SourceSchemasView /> : <SchemasView />)}
        {activeTab === 'data-groupings' && (
          <ConnectionDefaultGroupingConfiguration />
        )}
        {activeTab === 'incidents' && <IncidentsNotificationsView />}
      </div>
    </ConnectionLayout>
  );
};

export default ConnectionPage;
