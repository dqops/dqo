import qs from 'query-string';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useLocation } from 'react-router-dom';
import ConnectionCommentView from '../../components/Connection/ConnectionView/ConnectionCommentView';
import ConnectionDefaultGroupingConfiguration from '../../components/Connection/ConnectionView/ConnectionDataStream';
import ConnectionDetail from '../../components/Connection/ConnectionView/ConnectionDetail';
import ConnectionLabelsView from '../../components/Connection/ConnectionView/ConnectionLabelsView';
import { IncidentsNotificationsView } from '../../components/Connection/ConnectionView/IncidentsNotificationsView';
import ScheduleDetail from '../../components/Connection/ConnectionView/ScheduleDetail';
import SchemasView from '../../components/Connection/ConnectionView/SchemasView';
import SourceSchemasView from '../../components/Connection/ConnectionView/SourceSchemasView';
import SvgIcon from '../../components/SvgIcon';
import Tabs from '../../components/Tabs';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setActiveFirstLevelUrl } from '../../redux/actions/source.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { getFirstLevelConnectionTab, useDecodedParams } from '../../utils';
import ColumnListView from '../ColumnListView/ColumnListView';
import TableListView from '../TableListView/TableListView';

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
    label: 'Tables',
    value: 'tables'
  },
  {
    label: 'Columns',
    value: 'columns'
  },
  {
    label: 'Default grouping template',
    value: 'data-groupings'
  },
  {
    label: 'Incidents and notifications',
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
  }: {
    connection: string;
    tab: string;
    checkTypes: CheckTypes;
  } = useDecodedParams();
  const [tabs, setTabs] = useState(
    checkTypes === CheckTypes.SOURCES ? initSourceTabs : initCheckTabs
  );
  const history = useHistory();
  const location = useLocation() as any;
  const { import_schema, create_success } = qs.parse(location.search);

  const {
    isUpdatedConnectionBasic,
    isUpdatedLabels,
    isUpdatedSchedule,
    isUpdatedComments,
    isUpdatedDataStreamsMapping
  } = useSelector(getFirstLevelState(checkTypes));
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
    if (isUpdatedConnectionBasic !== undefined && tabs.length) {
      setTabs(
        tabs.map((item) =>
          item.value === 'detail'
            ? { ...item, isUpdated: isUpdatedConnectionBasic }
            : item
        )
      );
    }
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
    setTabs(checkTypes === CheckTypes.SOURCES ? initSourceTabs : initCheckTabs);
  }, [checkTypes]);

  useEffect(() => {
    if (checkTypes === CheckTypes.SOURCES) {
      return;
    }
    const newTabs = [
      {
        label: 'Schemas',
        value: 'schemas'
      },
      {
        label: 'Tables',
        value: 'tables'
      },
      {
        label: 'Columns',
        value: 'columns'
      }
    ];
    if (!newTabs.find((x) => x.value === activeTab)) {
      onChangeTab(getFirstLevelConnectionTab(checkTypes));
    }
    setTabs(newTabs);

    // } else {
    //   setTabs(initCheckTabs);
    // }
    // onChangeTab('schemas');
  }, [import_schema, checkTypes]);

  return (
    <>
      <div className="relative">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 pr-[570px]">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="database" className="w-5 h-5 shrink-0" />
            <div className="text-lg font-semibold truncate">
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
        {activeTab === 'tables' && <TableListView />}
        {activeTab === 'columns' && <ColumnListView />}
      </div>
    </>
  );
};

export default ConnectionPage;
