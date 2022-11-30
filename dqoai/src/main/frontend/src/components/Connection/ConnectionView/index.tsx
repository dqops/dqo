import React, { useEffect, useState } from 'react';
import SvgIcon from '../../SvgIcon';
import Tabs from '../../Tabs';
import ConnectionDetail from './ConnectionDetail';
import ScheduleDetail from './ScheduleDetail';
import qs from 'query-string';
import { useHistory } from 'react-router-dom';
import SchemasView from './SchemasView';
import { useTree } from '../../../contexts/treeContext';
import ConnectionCommentView from './ConnectionCommentView';
import ConnectionLabelsView from './ConnectionLabelsView';
import ConnectionDataStream from './ConnectionDataStream';

interface IConnectionViewProps {
  connectionName: string;
  nodeId: string;
}

const tabs = [
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
  const [activeTab, setActiveTab] = useState('connection');
  const history = useHistory();
  const { tabMap, setTabMap, activeTab: pageTab } = useTree();

  useEffect(() => {
    const searchQuery = qs.stringify({
      connection: connectionName
    });

    history.replace(`/?${searchQuery}`);
  }, [connectionName]);

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
      return <SchemasView connectionName={connectionName} />;
    }
    if (activeTab === 'data-streams') {
      return <ConnectionDataStream connectionName={connectionName} />;
    }
    return null;
  };

  const onChangeTab = (tab: string) => {
    setActiveTab(tab);
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
