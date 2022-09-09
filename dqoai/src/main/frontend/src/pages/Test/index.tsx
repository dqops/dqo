import React, { useState } from 'react';
import MainLayout from '../../components/MainLayout';
import PageTabs from '../../components/PageTabs';
import Tabs from '../../components/Tabs';
import { useTabs } from '../../contexts/tabContext';

const subTabs = [
  {
    label: 'Schema',
    value: 'schema'
  },
  {
    label: 'Details',
    value: 'details'
  },
  {
    label: 'Preview',
    value: 'preview'
  }
]

const TestPage = () => {
  const {
    tableTabs,
    activeTableTab,
    setActiveTableTab,
    databaseTabs,
    activeDatabaseTab,
    setActiveDatabaseTab,
    closeDatabaseTab,
  } = useTabs();

  return (
    <MainLayout>
      <div className="flex-1 h-full flex flex-col">
        <PageTabs
          tabs={databaseTabs}
          activeTab={activeDatabaseTab}
          onChange={setActiveDatabaseTab}
          onRemoveTab={closeDatabaseTab}
        />
        {
          activeDatabaseTab && (
            <div className="flex-1 bg-white border border-gray-300 flex-auto">
              <div className="py-6 border-b border-gray-300" />
              <Tabs tabs={tableTabs} activeTab={activeTableTab} onChange={setActiveTableTab} />
              <div className="border-b border-gray-300" />
            </div>
          )
        }
      </div>
    </MainLayout>
  );
};

export default TestPage;
