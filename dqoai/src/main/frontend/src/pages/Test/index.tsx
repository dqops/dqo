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
  const { tabs, activeTab, setActiveTab, closeTab } = useTabs();
  const [subActiveTab, setSubActiveTab] = useState('schema');

  return (
    <MainLayout>
      <div className="flex-1 h-full flex flex-col">
        <PageTabs
          tabs={tabs}
          activeTab={activeTab}
          onChange={setActiveTab}
          onRemoveTab={closeTab}
        />
        {
          activeTab && (
            <div className="flex-1 bg-white border border-gray-300 flex-auto">
              <div className="py-6 border-b border-gray-300" />
              <Tabs tabs={subTabs} activeTab={subActiveTab} onChange={setSubActiveTab} />
              <div className="border-b border-gray-300" />
            </div>
          )
        }
      </div>
    </MainLayout>
  );
};

export default TestPage;
