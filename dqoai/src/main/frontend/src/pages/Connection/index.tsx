import React, { useMemo } from 'react';

import MainLayout from '../../components/MainLayout';
import PageTabs from '../../components/PageTabs';
import { findTreeNode } from '../../utils/tree';
import { TREE_LEVEL } from '../../shared/enums';
import ConnectionView from '../../components/Connection/ConnectionView';
import SchemaView from '../../components/Connection/SchemaView';
import { useTree } from '../../contexts/treeContext';
import TableView from '../../components/Connection/TableView';
import ColumnsView from '../../components/Connection/ColumnsView';
import ColumnView from '../../components/Connection/ColumnView';
import ChecksView from '../../components/Connection/ChecksView';
import ColumnChecksView from '../../components/Connection/ColumnChecksView';

const ConnectionPage = () => {
  const { tabs, setActiveTab, activeTab, onAddTab, closeTab, treeData } =
    useTree();

  const activeNode = findTreeNode(treeData, activeTab);

  const params = useMemo(() => {
    if (activeNode?.level === TREE_LEVEL.DATABASE) {
      return {
        connectionName: activeNode.level
      };
    } else if (activeNode?.level === TREE_LEVEL.SCHEMA) {
      const connectionNode = findTreeNode(treeData, activeNode?.parentId ?? '');
      return {
        connectionName: connectionNode?.label ?? '',
        schemaName: activeNode.label
      };
    } else if (activeNode?.level === TREE_LEVEL.TABLE) {
      const schemaNode = findTreeNode(treeData, activeNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

      return {
        connectionName: connectionNode?.label ?? '',
        schemaName: schemaNode?.label ?? '',
        tableName: activeNode?.label
      };
    } else if (
      activeNode?.level === TREE_LEVEL.COLUMNS ||
      activeNode?.level === TREE_LEVEL.TABLE_CHECKS
    ) {
      const tableNode = findTreeNode(treeData, activeNode?.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

      return {
        connectionName: connectionNode?.label ?? '',
        schemaName: schemaNode?.label ?? '',
        tableName: tableNode?.label ?? ''
      };
    } else if (activeNode?.level === TREE_LEVEL.COLUMN) {
      const parentNode = findTreeNode(treeData, activeNode?.parentId ?? '');
      const tableNode = findTreeNode(treeData, parentNode?.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

      return {
        connectionName: connectionNode?.label ?? '',
        schemaName: schemaNode?.label ?? '',
        tableName: tableNode?.label ?? '',
        columnName: activeNode?.label ?? ''
      };
    } else if (activeNode?.level === TREE_LEVEL.COLUMN_CHECKS) {
      const columnNode = findTreeNode(treeData, activeNode?.parentId ?? '');
      const columnsNode = findTreeNode(treeData, columnNode?.parentId ?? '');
      const tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

      return {
        connectionName: connectionNode?.label ?? '',
        schemaName: schemaNode?.label ?? '',
        tableName: tableNode?.label ?? '',
        columnName: columnNode?.label ?? ''
      };
    }
  }, [activeNode]);

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
            <ConnectionView
              connectionName={activeNode.label}
              nodeId={activeTab}
            />
          )}
          {activeNode?.level === TREE_LEVEL.SCHEMA && (
            <SchemaView
              connectionName={params?.connectionName ?? ''}
              schemaName={params?.schemaName ?? ''}
            />
          )}
          {activeNode?.level === TREE_LEVEL.TABLE && (
            <TableView
              connectionName={params?.connectionName ?? ''}
              schemaName={params?.schemaName ?? ''}
              tableName={params?.tableName ?? ''}
            />
          )}
          {activeNode?.level === TREE_LEVEL.COLUMNS && (
            <ColumnsView
              connectionName={params?.connectionName ?? ''}
              schemaName={params?.schemaName ?? ''}
              tableName={params?.tableName ?? ''}
            />
          )}
          {activeNode?.level === TREE_LEVEL.TABLE_CHECKS && (
            <ChecksView
              connectionName={params?.connectionName ?? ''}
              schemaName={params?.schemaName ?? ''}
              tableName={params?.tableName ?? ''}
            />
          )}
          {activeNode?.level === TREE_LEVEL.COLUMN && (
            <ColumnView
              connectionName={params?.connectionName ?? ''}
              schemaName={params?.schemaName ?? ''}
              tableName={params?.tableName ?? ''}
              columnName={params?.columnName ?? ''}
            />
          )}
          {activeNode?.level === TREE_LEVEL.COLUMN_CHECKS && (
            <ColumnChecksView
              connectionName={params?.connectionName ?? ''}
              schemaName={params?.schemaName ?? ''}
              tableName={params?.tableName ?? ''}
              columnName={params?.columnName ?? ''}
            />
          )}
        </div>
      </div>
    </MainLayout>
  );
};

export default ConnectionPage;
