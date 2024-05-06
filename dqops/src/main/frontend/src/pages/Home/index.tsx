import React from 'react';
import { useHistory } from 'react-router-dom';
import Header from '../../components/Header';
import Tabs from '../../components/Tabs';
import GlobalTables from './GlobalTables';
import StaticHomePage from './StaticHomePage';

const tabs = [
  { label: 'Home', value: 'home' },
  { label: 'Tables', value: 'tables' }
];
const HomePage = () => {
  const history = useHistory();
  const activeTab = location.pathname.replace('/', '');
  const onChange = (activeTab: string) => {
    history.push('/' + activeTab);
  };
  return (
    <div className="min-h-screen">
      <Header />
      tabs
      <div className="w-full mt-12 border-b border-gray-300 px-0">
        <Tabs tabs={tabs} onChange={onChange} activeTab={activeTab} />
      </div>
      {activeTab === 'home' && <StaticHomePage />}
      {activeTab === 'tables' && <GlobalTables />}
    </div>
  );
};

export default HomePage;
