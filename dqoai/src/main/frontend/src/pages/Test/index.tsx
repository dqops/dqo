import React, {useState} from 'react';
import MainLayout from '../../components/MainLayout';
import {TabOption} from '../../components/PageTabs/tab';
import PageTabs from '../../components/PageTabs';
import Tabs from '../../components/Tabs';

const initialTabs = [
  {
    label: 'Test Page 1',
    value: 'page1'
  },
  {
    label: 'Test Page 2',
    value: 'page2'
  },
  {
    label: 'Test Page 3',
    value: 'page3'
  },
  {
    label: 'Test Page 4',
    value: 'page4'
  },
  {
    label: 'Test Page 5',
    value: 'page5'
  }
];

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
  const [activeTab, setActiveTab] = useState('page1');
  const [tabs, setTabs] = useState<TabOption[]>(initialTabs);
  const [subActiveTab, setSubActiveTab] = useState('schema');

  const onRemoveTab = (value: string) => {
    setTabs(tabs.filter((item) => item.value !== value));
  }

  return (
    <MainLayout>
      <div className="flex-1 h-full flex flex-col">
        <PageTabs
          tabs={tabs}
          activeTab={activeTab}
          onChange={setActiveTab}
          onRemoveTab={onRemoveTab}
        />
        <div className="flex-1 bg-white border border-gray-300 flex-auto">
          <div className="py-6 border-b border-gray-300" />
          <Tabs tabs={subTabs} activeTab={subActiveTab} onChange={setSubActiveTab} />
          <div className="border-b border-gray-300" />
        </div>
      </div>
    </MainLayout>
  );
};

export default TestPage;
