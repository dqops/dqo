import React, { useMemo } from 'react';

import MainLayout from '../../components/MainLayout';
import PageTabs from '../../components/PageTabs';
import { findTreeNode } from '../../utils/tree';
import { TREE_LEVEL } from '../../shared/enums';
import { useTree } from '../../contexts/treeContext';
import ColumnsView from '../../components/Connection/ColumnsView';
import ColumnView from '../../components/Connection/ColumnView';
import ColumnChecksView from '../../components/Connection/ColumnChecksView';
import ColumnDailyChecksView from '../../components/Connection/ColumnDailyChecksView';
import ColumnMonthlyChecksView from '../../components/Connection/ColumnMonthlyChecksView';
import ColumnDailyPartitionedChecksView from '../../components/Connection/ColumnDailyPartitionedChecksView';
import ColumnTableMonthlyPartitionedChecksView from '../../components/Connection/ColumnMonthlyPartitionedChecksView';
import TableAdHockChecksUIFilterView from '../TableAdHockChecksUIFilterView';
import TableCheckpointsUIFilterView from '../../components/Connection/TableCheckpointsUIFilterView';
import TablePartitionedChecksUIFilterView from '../../components/Connection/TablePartitionedChecksUIFilterView';
import ColumnAdHockChecksUIFilterView from '../../components/Connection/ColumnAdHockChecksUIFilterView';
import ColumnCheckpointsUIFilterView from '../../components/Connection/ColumnCheckpointsUIFilterView';
import ColumnPartitionedChecksUIFilterView from '../../components/Connection/ColumnPartitionedChecksUIFilterView';

const ChecksPage = () => {
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
      activeNode?.level === TREE_LEVEL.TABLE_CHECKS ||
      activeNode?.level === TREE_LEVEL.TABLE_DAILY_CHECKS ||
      activeNode?.level === TREE_LEVEL.TABLE_MONTHLY_CHECKS ||
      activeNode?.level === TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS ||
      activeNode?.level === TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS
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
    } else if (
      activeNode?.level === TREE_LEVEL.COLUMN_CHECKS ||
      activeNode?.level === TREE_LEVEL.COLUMN_DAILY_CHECKS ||
      activeNode?.level === TREE_LEVEL.COLUMN_MONTHLY_CHECKS ||
      activeNode?.level === TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS ||
      activeNode?.level === TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS
    ) {
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
    } else if (activeNode?.level === TREE_LEVEL.CHECK) {
      const parentNode = findTreeNode(treeData, activeNode?.parentId ?? '');
      if (!parentNode) {
        return;
      }
      if ([TREE_LEVEL.TABLE_CHECKS, TREE_LEVEL.TABLE_DAILY_CHECKS, TREE_LEVEL.TABLE_MONTHLY_CHECKS, TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS, TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS].includes(parentNode.level)) {
        const tableNode = findTreeNode(treeData, parentNode?.parentId ?? '');
        const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
        const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
  
        return {
          connectionName: connectionNode?.label ?? '',
          schemaName: schemaNode?.label ?? '',
          tableName: tableNode?.label ?? '',
          category: activeNode?.category ?? '',
          checkName: activeNode?.label,
          level: 'table',
          parentNode,
        };
      } else {
        const columnNode = findTreeNode(treeData, parentNode?.parentId ?? '');
        const columnsNode = findTreeNode(treeData, columnNode?.parentId ?? '');
        const tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
        const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
        const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
  
        return {
          connectionName: connectionNode?.label ?? '',
          schemaName: schemaNode?.label ?? '',
          tableName: tableNode?.label ?? '',
          columnName: columnNode?.label ?? '',
          category: activeNode?.category ?? '',
          checkName: activeNode?.label,
          level: 'column',
          parentNode
        };
      }
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
          {activeNode?.level === TREE_LEVEL.COLUMNS && (
            <ColumnsView
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
          {activeNode?.level === TREE_LEVEL.COLUMN_DAILY_CHECKS && (
            <ColumnDailyChecksView
              connectionName={params?.connectionName ?? ''}
              schemaName={params?.schemaName ?? ''}
              tableName={params?.tableName ?? ''}
              columnName={params?.columnName ?? ''}
            />
          )}
          {activeNode?.level === TREE_LEVEL.COLUMN_MONTHLY_CHECKS && (
            <ColumnMonthlyChecksView
              connectionName={params?.connectionName ?? ''}
              schemaName={params?.schemaName ?? ''}
              tableName={params?.tableName ?? ''}
              columnName={params?.columnName ?? ''}
            />
          )}
          {activeNode?.level === TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS && (
            <ColumnDailyPartitionedChecksView
              connectionName={params?.connectionName ?? ''}
              schemaName={params?.schemaName ?? ''}
              tableName={params?.tableName ?? ''}
              columnName={params?.columnName ?? ''}
            />
          )}
          {activeNode?.level ===
            TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS && (
            <ColumnTableMonthlyPartitionedChecksView
              connectionName={params?.connectionName ?? ''}
              schemaName={params?.schemaName ?? ''}
              tableName={params?.tableName ?? ''}
              columnName={params?.columnName ?? ''}
            />
          )}
          {activeNode?.level ===
          TREE_LEVEL.CHECK && (
            <>
              {params?.parentNode?.level === TREE_LEVEL.TABLE_DAILY_CHECKS && (
                <TableCheckpointsUIFilterView
                  connectionName={params?.connectionName ?? ''}
                  schemaName={params?.schemaName ?? ''}
                  tableName={params?.tableName ?? ''}
                  category={params?.category ?? ''}
                  checkName={params?.checkName ?? ''}
                  timePartitioned="daily"
                />
              )}
              {params?.parentNode?.level === TREE_LEVEL.TABLE_MONTHLY_CHECKS && (
                <TableCheckpointsUIFilterView
                  connectionName={params?.connectionName ?? ''}
                  schemaName={params?.schemaName ?? ''}
                  tableName={params?.tableName ?? ''}
                  category={params?.category ?? ''}
                  checkName={params?.checkName ?? ''}
                  timePartitioned="monthly"
                />
              )}
              {params?.parentNode?.level === TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS && (
                <TablePartitionedChecksUIFilterView
                  connectionName={params?.connectionName ?? ''}
                  schemaName={params?.schemaName ?? ''}
                  tableName={params?.tableName ?? ''}
                  category={params?.category ?? ''}
                  checkName={params?.checkName ?? ''}
                  timePartitioned="daily"
                />
              )}
              {params?.parentNode?.level === TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS && (
                <TablePartitionedChecksUIFilterView
                  connectionName={params?.connectionName ?? ''}
                  schemaName={params?.schemaName ?? ''}
                  tableName={params?.tableName ?? ''}
                  category={params?.category ?? ''}
                  checkName={params?.checkName ?? ''}
                  timePartitioned="monthly"
                />
              )}
              {params?.parentNode?.level === TREE_LEVEL.COLUMN_CHECKS && (
                <ColumnAdHockChecksUIFilterView
                  connectionName={params?.connectionName ?? ''}
                  schemaName={params?.schemaName ?? ''}
                  tableName={params?.tableName ?? ''}
                  columnName={params?.columnName ?? ''}
                  category={params?.category ?? ''}
                  checkName={params?.checkName ?? ''}
                />
              )}
              {params?.parentNode?.level === TREE_LEVEL.COLUMN_DAILY_CHECKS && (
                <ColumnCheckpointsUIFilterView
                  connectionName={params?.connectionName ?? ''}
                  schemaName={params?.schemaName ?? ''}
                  tableName={params?.tableName ?? ''}
                  columnName={params?.columnName ?? ''}
                  category={params?.category ?? ''}
                  checkName={params?.checkName ?? ''}
                  timePartitioned="daily"
                />
              )}
              {params?.parentNode?.level === TREE_LEVEL.COLUMN_MONTHLY_CHECKS && (
                <ColumnCheckpointsUIFilterView
                  connectionName={params?.connectionName ?? ''}
                  schemaName={params?.schemaName ?? ''}
                  tableName={params?.tableName ?? ''}
                  columnName={params?.columnName ?? ''}
                  category={params?.category ?? ''}
                  checkName={params?.checkName ?? ''}
                  timePartitioned="monthly"
                />
              )}
              {params?.parentNode?.level === TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS && (
                <ColumnPartitionedChecksUIFilterView
                  connectionName={params?.connectionName ?? ''}
                  schemaName={params?.schemaName ?? ''}
                  tableName={params?.tableName ?? ''}
                  columnName={params?.columnName ?? ''}
                  category={params?.category ?? ''}
                  checkName={params?.checkName ?? ''}
                  timePartitioned="daily"
                />
              )}
              {params?.parentNode?.level === TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS && (
                <ColumnPartitionedChecksUIFilterView
                  connectionName={params?.connectionName ?? ''}
                  schemaName={params?.schemaName ?? ''}
                  tableName={params?.tableName ?? ''}
                  columnName={params?.columnName ?? ''}
                  category={params?.category ?? ''}
                  checkName={params?.checkName ?? ''}
                  timePartitioned="monthly"
                />
              )}
            </>
          )}
        </div>
      </div>
    </MainLayout>
  );
};

export default ChecksPage;
