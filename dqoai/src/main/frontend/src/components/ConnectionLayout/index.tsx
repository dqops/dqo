import React, { ReactElement } from 'react';
import MainLayout from "../MainLayout";
import PageTabs from "../PageTabs";
import { useTree } from "../../contexts/treeContext";

interface ConnectionLayoutProps {
  children: any;
}

const ConnectionLayout = ({ children }: ConnectionLayoutProps) => {
  const { tabs, setActiveTab, activeTab, onAddTab, closeTab, treeData } =
    useTree();

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
        <div className="flex-1 bg-white border border-gray-300 flex-auto">
          {children}
        </div>
      </div>
    </MainLayout>
  );
};

export default ConnectionLayout;
