import React, { useEffect, useState } from 'react';

import { AxiosResponse } from 'axios';
import {
  ColumnBasicModel,
  ConnectionBasicModel,
  SchemaModel,
  TableBasicModel
} from '../api';
import {
  ColumnApiClient,
  ConnectionApiClient,
  SchemaApiClient,
  TableApiClient
} from '../services/apiClient';
import { TREE_LEVEL } from '../shared/enums';
import { CustomTreeNode, ITab } from '../shared/interfaces';
import { TreeNodeId } from '@naisutech/react-tree/types/Tree';
import { uniq } from 'lodash';
import { findTreeNode } from '../utils/tree';

const TreeContext = React.createContext({} as any);

function TreeProvider(props: any) {
  const [treeData, setTreeData] = useState<CustomTreeNode[]>([]);
  const [openNodes, setOpenNodes] = useState<TreeNodeId[]>([]);
  const [tabMap, setTabMap] = useState({});
  const [tabs, setTabs] = useState<ITab[]>([]);
  const [activeTab, setActiveTab] = useState<string>();
  const [sidebarWidth, setSidebarWidth] = useState(280);

  const getConnections = async () => {
    const res: AxiosResponse<ConnectionBasicModel[]> =
      await ConnectionApiClient.getAllConnections();

    setTreeData(
      res.data.map((item) => ({
        id: item.connection_name ?? '',
        parentId: null,
        label: item.connection_name ?? '',
        items: [],
        level: TREE_LEVEL.DATABASE,
        tooltip: item.connection_name
      }))
    );
  };

  useEffect(() => {
    (async () => {
      await getConnections();
    })();
  }, []);

  const toggleOpenNode = async (id: TreeNodeId) => {
    if (openNodes.includes(id)) {
      setOpenNodes(openNodes.filter((item) => item !== id));
      setTreeData(treeData.filter((item) => item.parentId !== id));
    } else {
      const newTreeData = [...treeData];
      const node = findTreeNode(newTreeData, id);

      if (!node) return;

      if (node.level === TREE_LEVEL.DATABASE) {
        const res: AxiosResponse<SchemaModel[]> =
          await SchemaApiClient.getSchemas(node.label);
        const items = res.data.map((schema) => ({
          id: `${node.id}.${schema.schema_name}`,
          label: schema.schema_name || '',
          level: TREE_LEVEL.SCHEMA,
          parentId: node.id,
          items: [],
          tooltip: `${node?.label}.${schema.schema_name}`
        }));
        setTreeData([...treeData, ...items]);
        setOpenNodes(uniq([...openNodes, id]));
      } else if (node.level === TREE_LEVEL.SCHEMA) {
        const connectionNode = findTreeNode(treeData, node.parentId ?? '');
        const res: AxiosResponse<TableBasicModel[]> =
          await TableApiClient.getTables(
            connectionNode?.label ?? '',
            node.label
          );
        const items = res.data.map((table) => ({
          id: `${node.id}.${table.target?.table_name}`,
          label: table.target?.table_name || '',
          level: TREE_LEVEL.TABLE,
          parentId: node.id,
          items: [],
          tooltip: `${connectionNode?.label}.${node.label}.${table.target?.table_name}`,
          hasCheck: table?.has_any_configured_checks,
        }));
        setTreeData([...treeData, ...items]);
        setOpenNodes(uniq([...openNodes, id]));
      } else if (node.level === TREE_LEVEL.TABLE) {
        const schemaNode = findTreeNode(treeData, node?.parentId ?? '');
        const connectionNode = findTreeNode(
          treeData,
          schemaNode?.parentId ?? ''
        );
        const items = [
          {
            id: `${node.id}.columns`,
            label: `Columns for ${connectionNode?.label}.${schemaNode?.label}.${node.label}`,
            level: TREE_LEVEL.COLUMNS,
            parentId: node.id,
            items: [],
            tooltip: `${connectionNode?.label}.${schemaNode?.label}.${node?.label} columns`
          },
          {
            id: `${node.id}.checks`,
            label: `Data quality checks for ${connectionNode?.label}.${schemaNode?.label}.${node.label}`,
            level: TREE_LEVEL.TABLE_CHECKS,
            parentId: node.id,
            items: [],
            tooltip: `${connectionNode?.label}.${schemaNode?.label}.${node?.label} checks`
          }
        ];

        setTreeData([...treeData, ...items]);
        setOpenNodes(uniq([...openNodes, id]));
      } else if (node.level === TREE_LEVEL.COLUMNS) {
        const tableNode = findTreeNode(treeData, node.parentId ?? '');
        const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
        const connectionNode = findTreeNode(
          treeData,
          schemaNode?.parentId ?? ''
        );
        const res: AxiosResponse<ColumnBasicModel[]> =
          await ColumnApiClient.getColumns(
            connectionNode?.label ?? '',
            schemaNode?.label ?? '',
            tableNode?.label ?? ''
          );
        const items = res.data.map((column) => ({
          id: `${node.id}.${column.column_name}`,
          label: column.column_name || '',
          level: TREE_LEVEL.COLUMN,
          parentId: node.id,
          items: [],
          tooltip: `${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}.${column.column_name}`,
          hasCheck: column?.has_any_configured_checks
        }));
        setTreeData([...treeData, ...items]);
        setOpenNodes(uniq([...openNodes, id]));
      } else if (node.level === TREE_LEVEL.TABLE_CHECKS) {
        const tableNode = findTreeNode(treeData, node.parentId ?? '');
        const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
        const connectionNode = findTreeNode(
          treeData,
          schemaNode?.parentId ?? ''
        );
        const res = await TableApiClient.getTableAdHocChecksUI(
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? ''
        );
        const items: CustomTreeNode[] = [];
        res.data.categories?.forEach((category) => {
          category.checks?.forEach((check) => {
            items.push({
              id: `${node.id}.${category.category}_${check?.check_name}`,
              label: `${category.category} - ${check?.check_name}` || '',
              level: TREE_LEVEL.CHECK,
              parentId: node.id,
              tooltip: `${category.category}_${check?.check_name} for ${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}`,
              items: []
            });
          });
        });
        setTreeData([...treeData, ...items]);
        setOpenNodes(uniq([...openNodes, id]));
      } else if (node.level === TREE_LEVEL.COLUMN) {
        const columnsNode = findTreeNode(treeData, node?.parentId ?? '');
        const tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
        const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
        const connectionNode = findTreeNode(
          treeData,
          schemaNode?.parentId ?? ''
        );

        const items = [
          {
            id: `${node.id}.checks`,
            label: `Data quality checks for ${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}.${node?.label}`,
            level: TREE_LEVEL.COLUMN_CHECKS,
            parentId: node.id,
            items: [],
            tooltip: `${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}.${node.label} checks`
          }
        ];

        setTreeData([...treeData, ...items]);
        setOpenNodes(uniq([...openNodes, id]));
      } else if (node.level === TREE_LEVEL.COLUMN_CHECKS) {
        const columnNode = findTreeNode(treeData, node.parentId ?? '');
        const columnsNode = findTreeNode(treeData, columnNode?.parentId ?? '');
        const tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
        const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
        const connectionNode = findTreeNode(
          treeData,
          schemaNode?.parentId ?? ''
        );
        const res = await ColumnApiClient.getColumnAdHocChecksUI(
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          columnNode?.label ?? ''
        );
        const items: CustomTreeNode[] = [];
        res.data.categories?.forEach((category) => {
          category.checks?.forEach((check) => {
            items.push({
              id: `${node.id}.${category.category}_${check?.check_name}`,
              label: `${category.category} - ${check?.check_name}` || '',
              level: TREE_LEVEL.CHECK,
              parentId: node.id,
              tooltip: `${category.category}_${check?.check_name} for ${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}.${node.label}`,
              items: []
            });
          });
        });
        setTreeData([...treeData, ...items]);
        setOpenNodes(uniq([...openNodes, id]));
      }
    }
  };

  const closeTab = (value: string) => {
    const newTabs = tabs.filter((item) => item.value !== value);
    setTabs(newTabs);
    if (value === activeTab) {
      setActiveTab(newTabs[newTabs.length - 1]?.value);
    }
  };

  const onAddTab = () => {
    const arr = tabs
      .filter((item) => item.type === 'editor')
      .map((item) => parseInt(item.value, 10));
    const maxEditor = Math.max(...arr, 0);

    const newTab = {
      label: `New Tab`,
      value: `${maxEditor + 1}`,
      type: 'editor'
    };

    setTabs([...tabs, newTab]);
    setActiveTab(newTab.value);
  };

  const changeActiveTab = async (node: CustomTreeNode) => {
    if (node.level === TREE_LEVEL.CHECK) {
      return;
    }
    const existTab = tabs.find((item) => item.value === node.id.toString());
    if (existTab) {
      setActiveTab(node.id.toString());
    } else {
      const newTab = {
        label: node.label ?? '',
        value: node.id.toString(),
        tooltip: node.tooltip
      };

      if (activeTab) {
        const newTabs = tabs.map((item) =>
          item.value === activeTab ? newTab : item
        );
        setTabs(newTabs);
      } else {
        setTabs([newTab]);
      }
      setActiveTab(node.id.toString());
    }
  };

  const removeTreeNode = (id: string) => {
    console.log(id);
    setOpenNodes(openNodes.filter((item) => item !== id));
    setTreeData(treeData.filter((item) => item.id !== id));
    const tabIndex = tabs.findIndex((tab) => tab.value === id);
    if (tabIndex > -1) {
      setActiveTab(tabs[(tabIndex + 1) % tabs.length]?.value);
      setTabs(tabs.filter((item) => item.value !== id));
    }
  };

  return (
    <TreeContext.Provider
      value={{
        treeData,
        setTreeData,
        tabs,
        activeTab,
        setActiveTab,
        closeTab,
        onAddTab,
        openNodes,
        setOpenNodes,
        toggleOpenNode,
        tabMap,
        setTabMap,
        changeActiveTab,
        sidebarWidth,
        setSidebarWidth,
        removeTreeNode
      }}
      {...props}
    />
  );
}

function useTree() {
  const context = React.useContext(TreeContext);

  if (context === undefined) {
    throw new Error('useTree must be used within a TreeProvider');
  }
  return context;
}

export { TreeProvider, useTree };
