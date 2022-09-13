import React from 'react';
import MainLayout from '../../components/MainLayout';
import PageTabs from '../../components/PageTabs';
import Tabs from '../../components/Tabs';
import { useTabs } from '../../contexts/tabContext';
import {Table} from '../../components/Table';

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
    setActiveColumnTab,
    closeTableTab,
    getTabLabel
  } = useTabs();

  const columns = [
    {
      header: () => <div className="text-base font-semibold">Id</div>,
      value: 'id',
      className: '!px-6 border-b',
      render: (text: string) => <div className="text-sm text-black-500">{text}</div>
    },
    {
      header: () => <div className="text-base font-semibold">Type</div>,
      value: 'type',
      className: '!px-6 border-b',
      render: (text: string) => <div className="text-sm text-black-500">{text}</div>
    },
    {
      header: () => <div className="text-base font-semibold">Value</div>,
      value: 'value',
      className: '!px-6 border-b',
      render: (text: string) => <div className="text-sm text-black-500">{text}</div>
    },
    {
      header: () => <div className="text-base font-semibold">Content ID</div>,
      value: 'contentId',
      className: '!px-6 border-b',
      render: (text: string) => <div className="text-sm text-black-500">{text}</div>
    },
  ];

  const data = [
    {
      id: 1,
      type: 'string',
      value: 'John',
      contentId: '123',
    },
    {
      id: 2,
      type: 'string',
      value: 'John',
      contentId: '123',
    },
    {
      id: 3,
      type: 'string',
      value: 'John',
      contentId: '123',
    },
    {
      id: 4,
      type: 'string',
      value: 'John',
      contentId: '123',
    },
    {
      id: 5,
      type: 'string',
      value: 'John',
      contentId: '123',
    },
  ];

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
              <Tabs
                tabs={tableTabs}
                activeTab={activeTableTab}
                onChange={setActiveTableTab}
                onCloseTab={closeTableTab}
              />
              <div className="border-b border-gray-300" />
              
              <div className="px-0">
                <Tabs tabs={columnTabs} activeTab={activeColumnTab} onChange={setActiveColumnTab} />
                <div className="border-b border-gray-300" />
                
                {
                  activeColumnTab && (
                    <div className="px-4 py-6">
                      <div className="text-2xl mb-4 font-semibold">
                        {`${getTabLabel(activeTableTab)} Table / ${getTabLabel(activeColumnTab)} Column`}</div>
                      <Table columns={columns} data={data} className="w-full border border-gray-300" />
                    </div>
                  )
                }
              </div>
            </div>
          )
        }
      </div>
    </MainLayout>
  );
};

export default TestPage;
