import React from 'react';

import MainLayout from '../../components/MainLayout';
import PageTabs from '../../components/PageTabs';
import { useTabs } from '../../contexts/tabContext';
import { findNode } from '../../utils/tree';
import { TREE_LEVEL } from '../../shared/enums';
import ConnectionView from '../../components/Connection/ConnectionView';
import TableView from '../../components/Connection/TableView';
import SchemaView from '../../components/Connection/SchemaView';
import ColumnView from '../../components/Connection/ColumnView';

const TestPage = () => {
  const { tabs, setActiveTab, activeTab, onAddTab, closeTab, treeData } =
    useTabs();

  const activeNode = findNode(treeData, activeTab);

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
          {activeNode?.level === TREE_LEVEL.DATABASE && (
            <ConnectionView node={activeNode} />
          )}
          {activeNode?.level === TREE_LEVEL.SCHEMA && (
            <SchemaView node={activeNode} />
          )}
          {activeNode?.level === TREE_LEVEL.TABLE && (
            <TableView node={activeNode} />
          )}
          {activeNode?.level === TREE_LEVEL.COLUMN && (
            <ColumnView node={activeNode} />
          )}
        </div>
      </div>
    </MainLayout>
  );
};

export default TestPage;
