import React, { useEffect, useState } from 'react';
import SvgIcon from '../../SvgIcon';
import Tabs from '../../Tabs';
import ConnectionDetail from './ConnectionDetail';
import ScheduleDetail from './ScheduleDetail';
import qs from 'query-string';
import { useHistory, useLocation } from 'react-router-dom';
import SourceSchemasView from './SourceSchemasView';
import { useTree } from '../../../contexts/treeContext';
import ConnectionCommentView from './ConnectionCommentView';
import ConnectionLabelsView from './ConnectionLabelsView';
import ConnectionDataStream from './ConnectionDataStream';
import SchemasView from './SchemasView';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';

interface IConnectionViewProps {
  connectionName: string;
  nodeId: string;
}

const initTabs = [
  {
    label: 'Connection',
    value: 'connection'
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

const ConnectionView = ({ connectionName }: IConnectionViewProps) => {
  const [tabs, setTabs] = useState(initTabs);
  const [activeTab, setActiveTab] = useState('connection');
  const history = useHistory();
  const { tabMap, setTabMap, activeTab: pageTab } = useTree();
  const location = useLocation();
  const [showMetaData, setShowMetaData] = useState(false);
  const {
    isUpdatedConnectionBasic,
    isUpdatedSchedule,
    isUpdatedComments,
    isUpdatedLabels,
    isUpdatedDataStreamsMapping
  } = useSelector((state: IRootState) => state.connection);

  useEffect(() => {
    const params = qs.parse(location.search);

    const searchQuery = qs.stringify({
      ...params,
      connection: connectionName
    });

    history.replace(`/checks?${searchQuery}`);
  }, [connectionName, location.search]);

  useEffect(() => {
    const params = qs.parse(location.search);
    if (params.tab) {
      setActiveTab(params.tab as string);
      setTabMap({
        ...tabMap,
        [pageTab]: params.tab
      });
    }
    if (params.source) {
      setShowMetaData(true);
    } else {
      setShowMetaData(false);
    }
  }, [location.search]);

  const renderTabContent = () => {
    if (activeTab === 'connection') {
      return <ConnectionDetail connectionName={connectionName} />;
    }
    if (activeTab === 'schedule') {
      return <ScheduleDetail connectionName={connectionName} />;
    }
    if (activeTab === 'comments') {
      return <ConnectionCommentView connectionName={connectionName} />;
    }
    if (activeTab === 'labels') {
      return <ConnectionLabelsView connectionName={connectionName} />;
    }
    if (activeTab === 'schemas') {
      if (showMetaData) {
        return <SourceSchemasView connectionName={connectionName} />;
      } else {
        return <SchemasView connectionName={connectionName} />;
      }
    }
    if (activeTab === 'data-streams') {
      return <ConnectionDataStream connectionName={connectionName} />;
    }
    return null;
  };

  const onChangeTab = (tab: string) => {
    setActiveTab(tab);
    const params = qs.parse(location.search);
    const { source, ...data } = params;

    console.log('source', source);
    const searchQuery = qs.stringify({
      ...data,
      tab
    });
    history.replace(`/checks?${searchQuery}`);

    setTabMap({
      ...tabMap,
      [pageTab]: tab
    });
  };

  useEffect(() => {
    if (tabMap[pageTab]) {
      setActiveTab(tabMap[pageTab]);
    } else {
      setActiveTab('connection');
    }
  }, [pageTab, tabMap]);

  useEffect(() => {
    setTabs(
      tabs.map((item) =>
        item.value === 'connection'
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
    <div className="relative">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
          <div className="text-xl font-semibold">{connectionName}</div>
        </div>
      </div>
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
      </div>
      {renderTabContent()}
    </div>
  );
};

export default ConnectionView;
