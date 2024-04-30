import React, { useState } from 'react';
import Header from '../../components/Header';
import Tabs from '../../components/Tabs';
import GlobalTables from './GlobalTables';
import StaticHomePage from './StaticHomePage';

const tabs = [
  { label: 'Home', value: 'home' },
  { label: 'Tables', value: 'tables' }
];
const HomePage = () => {
  const [activeTab, setActiveTab] = useState('home');
  return (
    <div className="min-h-screen overflow-hidden">
      <Header />
      tabs
      <div className="w-full mt-12 border-b border-gray-300 px-0">
        <Tabs tabs={tabs} onChange={setActiveTab} activeTab={activeTab} />
      </div>
      {activeTab === 'home' && <StaticHomePage />}
      {activeTab === 'tables' && <GlobalTables />}
    </div>
  );
};

export default HomePage;
