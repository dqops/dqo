import React from 'react';

import MainLayout from '../../components/MainLayout';
import PageTabs from '../../components/PageTabs';
import { useTabs } from '../../contexts/tabContext';

const TestPage = () => {
  const { tabs, setActiveTab, activeTab, onAddTab, closeTab, getTabLabel } =
    useTabs();

  return (
    <MainLayout>
      <div className="flex-1 h-full flex flex-col">
        <PageTabs
          tabs={tabs}
          activeTab={activeTab}
          onChange={setActiveTab}
          onRemoveTab={closeTab}
          onAddTab={onAddTab}
        />
        {activeTab && (
          <div className="flex-1 bg-white border border-gray-300 flex-auto">
            {activeTab && (
              <div className="px-4 py-6">
                <div>{getTabLabel(activeTab)}</div>
                {/*<div className="text-2xl mb-4 font-semibold">*/}
                {/*  {`${getTabLabel(activeTableTab)} Table / ${getTabLabel(activeColumnTab)} Column`}</div>*/}
                {/*<Table columns={columns} data={data} className="w-full border border-gray-300" />*/}
              </div>
            )}
          </div>
        )}
      </div>
    </MainLayout>
  );
};

export default TestPage;
