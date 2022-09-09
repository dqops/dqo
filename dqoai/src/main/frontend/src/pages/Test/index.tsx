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
    columnTabs,
    activeColumnTab,
    setActiveColumnTab
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
              <Tabs tabs={tableTabs} activeTab={activeTableTab} onChange={setActiveTableTab} />
              <div className="border-b border-gray-300" />
              
              <div className="px-0">
                <Tabs tabs={columnTabs} activeTab={activeColumnTab} onChange={setActiveColumnTab} />
                <div className="border-b border-gray-300" />
              </div>
            </div>
          )
        }
      </div>
    </MainLayout>
  );
};

export default TestPage;
