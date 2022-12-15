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
  JobApiClient,
  SchemaApiClient,
  TableApiClient
} from '../services/apiClient';
import { TREE_LEVEL } from '../shared/enums';
import { CustomTreeNode, ITab } from '../shared/interfaces';
import { TreeNodeId } from '@naisutech/react-tree/types/Tree';
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
        tooltip: item.connection_name,
        run_checks_job_template: item.run_checks_job_template,
        run_profilers_job_template: item.run_profiler_job_template,
        open: false
      }))
    );
  };

  const addConnection = async (connection: ConnectionBasicModel) => {
    const newNode = {
      id: connection.connection_name ?? '',
      parentId: null,
      label: connection.connection_name ?? '',
      items: [],
      level: TREE_LEVEL.DATABASE,
      tooltip: connection.connection_name,
      run_checks_job_template: connection.run_checks_job_template,
      run_profilers_job_template: connection.run_profiler_job_template,
      open: false
    };
    setTreeData([...treeData, newNode]);
    await changeActiveTab(newNode);
  };

  useEffect(() => {
    (async () => {
      await getConnections();
    })();
  }, []);

  const resetTreeData = (node: CustomTreeNode, items: CustomTreeNode[]) => {
    setOpenNodes([
      ...openNodes.filter(
        (item) => item.toString().indexOf(node.id.toString()) !== 0
      ),
      node.id
    ]);
    const newTreeData = treeData
      .filter(
        (item) =>
          item.id === node.id ||
          item.level === node.level ||
          item.parentId !== node.id
      )
      .map((item) =>
        item.id === node.id ? { ...item, open: !item.open } : item
      );
    setTreeData([...newTreeData, ...items]);
  };

  const refreshDatabaseNode = async (node: CustomTreeNode) => {
    const res: AxiosResponse<SchemaModel[]> = await SchemaApiClient.getSchemas(
      node.label
    );

    const items = res.data.map((schema) => ({
      id: `${node.id}.${schema.schema_name}`,
      label: schema.schema_name || '',
      level: TREE_LEVEL.SCHEMA,
      parentId: node.id,
      items: [],
      tooltip: `${node?.label}.${schema.schema_name}`,
      run_checks_job_template: schema.run_checks_job_template,
      run_profilers_job_template: schema.run_profiler_job_template,
      open: false
    }));

    resetTreeData(node, items);
  };

  const refreshSchemaNode = async (node: CustomTreeNode) => {
    const connectionNode = findTreeNode(treeData, node.parentId ?? '');
    const res: AxiosResponse<TableBasicModel[]> =
      await TableApiClient.getTables(connectionNode?.label ?? '', node.label);
    const items = res.data.map((table) => ({
      id: `${node.id}.${table.target?.table_name}`,
      label: table.target?.table_name || '',
      level: TREE_LEVEL.TABLE,
      parentId: node.id,
      items: [],
      tooltip: `${connectionNode?.label}.${node.label}.${table.target?.table_name}`,
      hasCheck: table?.has_any_configured_checks,
      run_checks_job_template: table.run_checks_job_template,
      run_profilers_job_template: table.run_profiler_job_template,
      open: false
    }));
    resetTreeData(node, items);
  };

  const refreshTableNode = async (node: CustomTreeNode) => {
    const schemaNode = findTreeNode(treeData, node?.parentId ?? '');
    const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
    const items = [
      {
        id: `${node.id}.columns`,
        label: `Columns for ${connectionNode?.label}.${schemaNode?.label}.${node.label}`,
        level: TREE_LEVEL.COLUMNS,
        parentId: node.id,
        items: [],
        tooltip: `${connectionNode?.label}.${schemaNode?.label}.${node?.label} columns`,
        open: false
      },
      {
        id: `${node.id}.checks`,
        label: 'Ad-hoc checks',
        level: TREE_LEVEL.TABLE_CHECKS,
        parentId: node.id,
        items: [],
        tooltip: `${connectionNode?.label}.${schemaNode?.label}.${node?.label} checks`,
        open: false
      },
      {
        id: `${node.id}.dailyCheck`,
        label: 'Daily checkpoints',
        level: TREE_LEVEL.TABLE_DAILY_CHECKS,
        parentId: node.id,
        items: [],
        tooltip: `${connectionNode?.label}.${schemaNode?.label}.${node?.label} daily checkpoints`,
        open: false
      },
      {
        id: `${node.id}.monthlyCheck`,
        label: 'Monthly checkpoints',
        level: TREE_LEVEL.TABLE_MONTHLY_CHECKS,
        parentId: node.id,
        items: [],
        tooltip: `${connectionNode?.label}.${schemaNode?.label}.${node?.label} monthly checkpoints`,
        open: false
      },
      {
        id: `${node.id}.dailyPartitionedChecks`,
        label: 'Daily partitioned checks',
        level: TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS,
        parentId: node.id,
        items: [],
        tooltip: `${connectionNode?.label}.${schemaNode?.label}.${node?.label} daily partitioned checks`,
        open: false
      },
      {
        id: `${node.id}.monthlyPartitionedChecks`,
        label: 'Monthly partitioned checks',
        level: TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS,
        parentId: node.id,
        items: [],
        tooltip: `${connectionNode?.label}.${schemaNode?.label}.${node?.label} monthly partitioned checks`,
        open: false
      }
    ];

    resetTreeData(node, items);
  };

  const refreshColumnNode = async (node: CustomTreeNode) => {
    const columnsNode = findTreeNode(treeData, node?.parentId ?? '');
    const tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
    const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
    const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

    const items = [
      {
        id: `${node.id}.checks`,
        label: `Data quality checks for ${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}.${node?.label}`,
        level: TREE_LEVEL.COLUMN_CHECKS,
        parentId: node.id,
        items: [],
        tooltip: `${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}.${node.label} checks`,
        open: false
      },
      {
        id: `${node.id}.dailyCheck`,
        label: 'Daily checkpoints',
        level: TREE_LEVEL.COLUMN_DAILY_CHECKS,
        parentId: node.id,
        items: [],
        tooltip: `${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}.${node?.label} daily checkpoints`,
        open: false
      },
      {
        id: `${node.id}.monthlyCheck`,
        label: 'Monthly checkpoints',
        level: TREE_LEVEL.COLUMN_MONTHLY_CHECKS,
        parentId: node.id,
        items: [],
        tooltip: `${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}.${node?.label} monthly checkpoints`,
        open: false
      },
      {
        id: `${node.id}.dailyPartitionedChecks`,
        label: 'Daily partitioned checks',
        level: TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS,
        parentId: node.id,
        items: [],
        tooltip: `${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}.${node?.label} daily partitioned checks`,
        open: false
      },
      {
        id: `${node.id}.monthlyPartitionedChecks`,
        label: 'Monthly partitioned checks',
        level: TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS,
        parentId: node.id,
        items: [],
        tooltip: `${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}.${node?.label} monthly partitioned checks`,
        open: false
      }
    ];
    resetTreeData(node, items);
  };

  const refreshColumnsNode = async (node: CustomTreeNode) => {
    const tableNode = findTreeNode(treeData, node.parentId ?? '');
    const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
    const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
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
      hasCheck: column?.has_any_configured_checks,
      run_checks_job_template: column.run_checks_job_template,
      run_profilers_job_template: column.run_profiler_job_template,
      open: false
    }));
    resetTreeData(node, items);
  };

  const refreshTableChecksNode = async (node: CustomTreeNode) => {
    const tableNode = findTreeNode(treeData, node.parentId ?? '');
    const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
    const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
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
          items: [],
          open: false
        });
      });
    });
    resetTreeData(node, items);
  };

  const refreshColumnChecksNode = async (node: CustomTreeNode) => {
    const columnNode = findTreeNode(treeData, node.parentId ?? '');
    const columnsNode = findTreeNode(treeData, columnNode?.parentId ?? '');
    const tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
    const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
    const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
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
          items: [],
          open: false
        });
      });
    });
    resetTreeData(node, items);
  };

  const toggleOpenNode = async (id: TreeNodeId) => {
    if (openNodes.includes(id)) {
      setOpenNodes(openNodes.filter((item) => item !== id));
      setTreeData(
        treeData
          .filter((item) => item.parentId !== id)
          .map((item) => (item.id === id ? { ...item, open: false } : item))
      );
    } else {
      const newTreeData = [...treeData];
      const node = findTreeNode(newTreeData, id);

      if (!node) return;

      await refreshNode(node);
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

  const changeActiveTab = async (node: CustomTreeNode, isNew = false) => {
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
        const newTabs = isNew
          ? [...tabs, newTab]
          : tabs.map((item) => (item.value === activeTab ? newTab : item));
        setTabs(newTabs);
      } else {
        setTabs([newTab]);
      }
      setActiveTab(node.id.toString());
    }
  };

  const removeTreeNode = (id: string) => {
    setOpenNodes(openNodes.filter((item) => item !== id));
    setTreeData(treeData.filter((item) => item.id !== id));
    const tabIndex = tabs.findIndex((tab) => tab.value === id);
    if (tabIndex > -1) {
      setActiveTab(tabs[(tabIndex + 1) % tabs.length]?.value);
      setTabs(tabs.filter((item) => item.value !== id));
    }
  };

  const removeNode = async (node: CustomTreeNode) => {
    setOpenNodes(openNodes.filter((item) => item !== node.id));
    setTreeData(treeData.filter((item) => item.id !== node.id));
    const tabIndex = tabs.findIndex((tab) => tab.value === node.id);
    if (tabIndex > -1) {
      setActiveTab(tabs[(tabIndex + 1) % tabs.length]?.value);
      setTabs(tabs.filter((item) => item.value !== node.id));
    }

    if (node.level === TREE_LEVEL.DATABASE) {
      await ConnectionApiClient.deleteConnection(node.label);
    } else if (node.level === TREE_LEVEL.TABLE) {
      const schemaNode = findTreeNode(treeData, node?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
      await TableApiClient.deleteTable(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        node.label
      );
    } else if (node.level === TREE_LEVEL.COLUMN) {
      const parentNode = findTreeNode(treeData, node?.parentId ?? '');
      const tableNode = findTreeNode(treeData, parentNode?.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

      await ColumnApiClient.deleteColumn(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        node.label
      );
    }
  };

  const refreshNode = async (node: CustomTreeNode) => {
    if (node.level === TREE_LEVEL.DATABASE) {
      await refreshDatabaseNode(node);
    } else if (node.level === TREE_LEVEL.SCHEMA) {
      await refreshSchemaNode(node);
    } else if (node.level === TREE_LEVEL.TABLE) {
      await refreshTableNode(node);
    } else if (node.level === TREE_LEVEL.COLUMNS) {
      await refreshColumnsNode(node);
    } else if (node.level === TREE_LEVEL.TABLE_CHECKS) {
      await refreshTableChecksNode(node);
    } else if (node.level === TREE_LEVEL.COLUMN) {
      await refreshColumnNode(node);
    } else if (node.level === TREE_LEVEL.COLUMN_CHECKS) {
      await refreshColumnChecksNode(node);
    }
  };

  const runChecks = async (node: CustomTreeNode) => {
    if (node.run_checks_job_template) {
      JobApiClient.runChecks(node.run_checks_job_template);
      return;
    }

    let connectionNode: CustomTreeNode | undefined;
    let schemaNode: CustomTreeNode | undefined;
    let tableNode: CustomTreeNode | undefined;
    let columnNode: CustomTreeNode | undefined;

    if (
      node?.level === TREE_LEVEL.TABLE_CHECKS ||
      node?.level === TREE_LEVEL.TABLE_DAILY_CHECKS ||
      node?.level === TREE_LEVEL.TABLE_MONTHLY_CHECKS ||
      node?.level === TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS ||
      node?.level === TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS
    ) {
      tableNode = findTreeNode(treeData, node?.parentId ?? '');
      schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
    }

    if (
      node?.level === TREE_LEVEL.COLUMN_CHECKS ||
      node?.level === TREE_LEVEL.COLUMN_DAILY_CHECKS ||
      node?.level === TREE_LEVEL.COLUMN_MONTHLY_CHECKS ||
      node?.level === TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS ||
      node?.level === TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS
    ) {
      columnNode = findTreeNode(treeData, node?.parentId ?? '');
      const columnsNode = findTreeNode(treeData, columnNode?.parentId ?? '');
      tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
      schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
    }

    if (node.level === TREE_LEVEL.TABLE_CHECKS) {
      const res = await TableApiClient.getTableAdHocChecksUI(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? ''
      );
      JobApiClient.runChecks(res.data.run_checks_job_template);
      return;
    }
    if (node.level === TREE_LEVEL.TABLE_DAILY_CHECKS) {
      const res = await TableApiClient.getTableCheckpointsUI(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        'daily'
      );
      JobApiClient.runChecks(res.data.run_checks_job_template);
      return;
    }
    if (node.level === TREE_LEVEL.TABLE_MONTHLY_CHECKS) {
      const res = await TableApiClient.getTableCheckpointsUI(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        'monthly'
      );
      JobApiClient.runChecks(res.data.run_checks_job_template);
      return;
    }
    if (node.level === TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS) {
      const res = await TableApiClient.getTablePartitionedChecksUI(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        'daily'
      );
      JobApiClient.runChecks(res.data.run_checks_job_template);
      return;
    }
    if (node.level === TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS) {
      const res = await TableApiClient.getTablePartitionedChecksUI(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        'daily'
      );
      JobApiClient.runChecks(res.data.run_checks_job_template);
      return;
    }

    if (node.level === TREE_LEVEL.COLUMN_CHECKS) {
      const res = await ColumnApiClient.getColumnAdHocChecksUI(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        columnNode?.label ?? ''
      );
      JobApiClient.runChecks(res.data.run_checks_job_template);
      return;
    }
    if (node.level === TREE_LEVEL.COLUMN_DAILY_CHECKS) {
      const res = await ColumnApiClient.getColumnCheckpointsUI(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        columnNode?.label ?? '',
        'daily'
      );
      JobApiClient.runChecks(res.data.run_checks_job_template);
      return;
    }
    if (node.level === TREE_LEVEL.COLUMN_MONTHLY_CHECKS) {
      const res = await ColumnApiClient.getColumnCheckpointsUI(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        columnNode?.label ?? '',
        'monthly'
      );
      JobApiClient.runChecks(res.data.run_checks_job_template);
      return;
    }
    if (node.level === TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS) {
      const res = await ColumnApiClient.getColumnPartitionedChecksUI(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        columnNode?.label ?? '',
        'daily'
      );
      JobApiClient.runChecks(res.data.run_checks_job_template);
      return;
    }
    if (node.level === TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS) {
      const res = await ColumnApiClient.getColumnPartitionedChecksUI(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        columnNode?.label ?? '',
        'monthly'
      );
      JobApiClient.runChecks(res.data.run_checks_job_template);
      return;
    }
  };

  const runProfilersOnTable = async (node: CustomTreeNode) => {
    if (node.run_profilers_job_template) {
      JobApiClient.runProfilersOnTable(node.run_profilers_job_template);
      return;
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
        removeTreeNode,
        removeNode,
        refreshNode,
        runChecks,
        runProfilersOnTable,
        addConnection
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
