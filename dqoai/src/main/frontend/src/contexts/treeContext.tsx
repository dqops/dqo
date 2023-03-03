import React, { useCallback, useEffect, useLayoutEffect, useMemo, useState } from 'react';

import { AxiosResponse } from 'axios';
import {
  CheckSearchFilters,
  ColumnBasicModel,
  ConnectionBasicModel,
  SchemaModel,
  TableBasicModel, UICheckBasicModel
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
import { CheckTypes, ROUTES } from "../shared/routes";
import { useHistory, useParams } from "react-router-dom";

const TreeContext = React.createContext({} as any);

const checkTypesToJobTemplateKey= {
  [CheckTypes.SOURCES]: 'run_checks_job_template',
  [CheckTypes.PROFILING]: 'run_profiling_checks_job_template',
  [CheckTypes.CHECKS]: 'run_whole_table_checks_job_template',
  [CheckTypes.TIME_PARTITIONED]: 'run_time_period_checks_job_template'
};

const checkTypesToHasConfiguredCheckKey = {
  [CheckTypes.SOURCES]: 'has_any_configured_checks',
  [CheckTypes.PROFILING]: 'has_any_configured_profiling_checks',
  [CheckTypes.TIME_PARTITIONED]: 'has_any_configured_time_period_checks',
  [CheckTypes.CHECKS]: 'has_any_configured_whole_table_checks'
};

function TreeProvider(props: any) {
  const [treeDataMaps, setTreeDataMaps] = useState<Record<string, CustomTreeNode[]>>({});
  const [sourceRoute, setSourceRoute] = useState('');
  const treeData = useMemo(() => treeDataMaps[sourceRoute] ?? [], [treeDataMaps, sourceRoute]);
  const setTreeData = useCallback((_treeData: CustomTreeNode[]) => {
    setTreeDataMaps(prev => ({
      ...prev,
      [sourceRoute]: _treeData
    }));
  }, [sourceRoute]);
  const [openNodes, setOpenNodes] = useState<CustomTreeNode[]>([]);
  const [tabMaps, setTabMaps] = useState<Record<string, ITab[]>>({}); // `blue box tab level`
  const [subTabMap, setSubTabMap] = useState<{[key: string]: string}>({}); // sub tab under `blue box tab level`
  const tabs = useMemo(() => tabMaps[sourceRoute] ?? [], [tabMaps, sourceRoute]);
  const setTabs = useCallback((_tabMaps: ITab[]) => {
    setTabMaps(prev => ({
      ...prev,
      [sourceRoute]: _tabMaps
    }));
  }, [sourceRoute]);

  const [activeNode, setActiveNode] = useState<CustomTreeNode>();
  const [activeTabMaps, setActiveTabMaps] = useState<Record<string, string>>({});
  const activeTab = activeTabMaps[sourceRoute];

  const [sidebarWidth, setSidebarWidth] = useState(280);
  const history = useHistory();

  const getConnections = async () => {
    const res: AxiosResponse<ConnectionBasicModel[]> =
      await ConnectionApiClient.getAllConnections();
    const mappedConnectionsToTreeData = res.data.map((item) => ({
      id: item.connection_name ?? '',
      parentId: null,
      label: item.connection_name ?? '',
      items: [],
      level: TREE_LEVEL.DATABASE,
      tooltip: item.connection_name,
      run_checks_job_template: item[checkTypesToJobTemplateKey[sourceRoute as keyof typeof checkTypesToJobTemplateKey] as keyof ConnectionBasicModel],
      collect_statistics_job_template: item.collect_statistics_job_template,
      data_clean_job_template: item.data_clean_job_template,
      open: false
    }));
    const treeDataMaps = [
      CheckTypes.CHECKS,
      CheckTypes.SOURCES,
      CheckTypes.PROFILING,
      CheckTypes.TIME_PARTITIONED,
    ].reduce((acc, cur) => ({
      ...acc,
      [cur]: mappedConnectionsToTreeData
    }), {});
    setTreeDataMaps(treeDataMaps);
  };

  const addConnection = async (connection: ConnectionBasicModel) => {
    const newNode = {
      id: connection.connection_name ?? '',
      parentId: null,
      label: connection.connection_name ?? '',
      items: [],
      level: TREE_LEVEL.DATABASE,
      tooltip: connection.connection_name,
      run_checks_job_template: connection[checkTypesToJobTemplateKey[sourceRoute as keyof typeof checkTypesToJobTemplateKey] as keyof ConnectionBasicModel] as CheckSearchFilters,
      collect_statistics_job_template: connection.collect_statistics_job_template,
      data_clean_job_template: connection.data_clean_job_template,
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
        (item) => item.id.toString().indexOf(node.id.toString()) !== 0
      ),
      node
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
      run_checks_job_template: schema[checkTypesToJobTemplateKey[sourceRoute as keyof typeof checkTypesToJobTemplateKey] as keyof SchemaModel] as CheckSearchFilters,
      collect_statistics_job_template: schema.collect_statistics_job_template,
      data_clean_job_template: schema.data_clean_job_template,
      open: false
    }));

    resetTreeData(node, items);

    return items;
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
      hasCheck: !!table?.[checkTypesToHasConfiguredCheckKey[sourceRoute as keyof typeof checkTypesToHasConfiguredCheckKey] as keyof TableBasicModel],
      run_checks_job_template: table[checkTypesToJobTemplateKey[sourceRoute as keyof typeof checkTypesToJobTemplateKey] as keyof TableBasicModel] as CheckSearchFilters,
      collect_statistics_job_template: table.collect_statistics_job_template,
      data_clean_job_template: table.data_clean_job_template,
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
        label: `Columns`,
        level: TREE_LEVEL.COLUMNS,
        parentId: node.id,
        items: [],
        tooltip: `${connectionNode?.label}.${schemaNode?.label}.${node?.label} columns`,
        open: false
      }
    ];
    console.log('sourceRoute', sourceRoute)
    if (sourceRoute === CheckTypes.PROFILING) {
      items.push(
        {
          id: `${node.id}.checks`,
          label: 'Profiling checks',
          level: TREE_LEVEL.TABLE_CHECKS,
          parentId: node.id,
          items: [],
          tooltip: `${connectionNode?.label}.${schemaNode?.label}.${node?.label} checks`,
          open: false
        });
    }
    if (sourceRoute === CheckTypes.CHECKS) {
      items.push(
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
        }
      )
    }
    if (sourceRoute === CheckTypes.TIME_PARTITIONED) {
      items.push(
        {
          id: `${node.id}.dailyPartitionedChecks`,
          label: 'Day period checks',
          level: TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS,
          parentId: node.id,
          items: [],
          tooltip: `${connectionNode?.label}.${schemaNode?.label}.${node?.label} day period checks`,
          open: false
        },
        {
          id: `${node.id}.monthlyPartitionedChecks`,
          label: 'Month period checks',
          level: TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS,
          parentId: node.id,
          items: [],
          tooltip: `${connectionNode?.label}.${schemaNode?.label}.${node?.label} month period checks`,
          open: false
        }
      )
    }

    resetTreeData(node, items);
  };

  const refreshColumnNode = async (node: CustomTreeNode) => {
    const columnsNode = findTreeNode(treeData, node?.parentId ?? '');
    const tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
    const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
    const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
    const items = [];
    if (sourceRoute === CheckTypes.PROFILING) {
      items.push({
        id: `${node.id}.checks`,
        label: `Profiling checks`,
        level: TREE_LEVEL.COLUMN_CHECKS,
        parentId: node.id,
        items: [],
        tooltip: `${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}.${node.label} checks`,
        open: false
      });
    }
    if (sourceRoute === CheckTypes.CHECKS) {
      items.push(
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
        }
      );
    }
    if (sourceRoute === CheckTypes.TIME_PARTITIONED) {
      items.push(
        {
          id: `${node.id}.dailyPartitionedChecks`,
          label: 'Day period checks',
          level: TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS,
          parentId: node.id,
          items: [],
          tooltip: `${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}.${node?.label} day period checks`,
          open: false
        },
        {
          id: `${node.id}.monthlyPartitionedChecks`,
          label: 'Month period checks',
          level: TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS,
          parentId: node.id,
          items: [],
          tooltip: `${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}.${node?.label} month period checks`,
          open: false
        }
      );
    }
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
      hasCheck: !!column?.[checkTypesToHasConfiguredCheckKey[sourceRoute as keyof typeof checkTypesToHasConfiguredCheckKey] as keyof ColumnBasicModel],
      run_checks_job_template: column[checkTypesToJobTemplateKey[sourceRoute as keyof typeof checkTypesToJobTemplateKey] as keyof ColumnBasicModel] as CheckSearchFilters,
      collect_statistics_job_template: column.collect_statistics_job_template,
      data_clean_job_template: column.data_clean_job_template,
      open: false
    }));
    resetTreeData(node, items);
  };

  const refreshTableChecksNode = async (node: CustomTreeNode) => {
    const tableNode = findTreeNode(treeData, node.parentId ?? '');
    const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
    const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
    const res = await TableApiClient.getTableAdHocChecksUIBasic(
      connectionNode?.label ?? '',
      schemaNode?.label ?? '',
      tableNode?.label ?? ''
    );
    addChecks(res.data.checks || [], node, `${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}`);
  };

  const refreshTableCheckpointsNode = async (node: CustomTreeNode, timePartitioned: 'daily' | 'monthly') => {
    const tableNode = findTreeNode(treeData, node.parentId ?? '');
    const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
    const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

    const res = await TableApiClient.getTableCheckpointsUIBasic(
      connectionNode?.label ?? '',
      schemaNode?.label ?? '',
      tableNode?.label ?? '',
      timePartitioned
    );
    addChecks(res.data.checks || [], node, `${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}`);
  };
  
  const refreshTablePartitionedCheckpointsNode = async (node: CustomTreeNode, timePartitioned: 'daily' | 'monthly') => {
    const tableNode = findTreeNode(treeData, node.parentId ?? '');
    const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
    const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
    
    const res = await TableApiClient.getTablePartitionedChecksUIBasic(
      connectionNode?.label ?? '',
      schemaNode?.label ?? '',
      tableNode?.label ?? '',
      timePartitioned
    );
    addChecks(res.data.checks || [], node, `${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}`);
  };
  
  const refreshColumnChecksNode = async (node: CustomTreeNode) => {
    const columnNode = findTreeNode(treeData, node.parentId ?? '');
    const columnsNode = findTreeNode(treeData, columnNode?.parentId ?? '');
    const tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
    const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
    const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
    const res = await ColumnApiClient.getColumnAdHocChecksUIBasic(
      connectionNode?.label ?? '',
      schemaNode?.label ?? '',
      tableNode?.label ?? '',
      columnNode?.label ?? ''
    );
    addChecks(res.data.checks || [], node, `${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}.${node.label}`);
  };

  const refreshColumnCheckpointsNode = async (node: CustomTreeNode, timePartitioned: 'daily' | 'monthly') => {
    const columnNode = findTreeNode(treeData, node.parentId ?? '');
    const columnsNode = findTreeNode(treeData, columnNode?.parentId ?? '');
    const tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
    const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
    const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
    const res = await ColumnApiClient.getColumnCheckpointsUIBasic(
      connectionNode?.label ?? '',
      schemaNode?.label ?? '',
      tableNode?.label ?? '',
      columnNode?.label ?? '',
      timePartitioned
    );
    addChecks(res.data.checks || [], node, `${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}.${node.label}`);
  };
  
  const refreshColumnPartitionedChecksNode = async (node: CustomTreeNode, timePartitioned: 'daily' | 'monthly') => {
    const columnNode = findTreeNode(treeData, node.parentId ?? '');
    const columnsNode = findTreeNode(treeData, columnNode?.parentId ?? '');
    const tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
    const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
    const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
    const res = await ColumnApiClient.getColumnPartitionedChecksUIBasic(
      connectionNode?.label ?? '',
      schemaNode?.label ?? '',
      tableNode?.label ?? '',
      columnNode?.label ?? '',
      timePartitioned
    );
    addChecks(res.data.checks || [], node, `${connectionNode?.label}.${schemaNode?.label}.${tableNode?.label}.${node.label}`);
  };
  
  const addChecks = (checks: UICheckBasicModel[], node: CustomTreeNode, tooltipSuffix: string) => {
    const items: CustomTreeNode[] = [];
    checks?.forEach((check) => {
      items.push({
        id: `${node.id}.${check?.check_category}_${check?.check_name}`,
        label: check?.check_name || '',
        level: TREE_LEVEL.CHECK,
        parentId: node.id,
        category: check?.check_category,
        tooltip: `${check?.check_category}_${check?.check_name} for ${tooltipSuffix}`,
        hasCheck: check?.configured,
        items: [],
        open: false
      });
    });
    resetTreeData(node, items);
  }

  const toggleOpenNode = async (id: TreeNodeId) => {
    if (openNodes.map((item) => item.id).includes(id)) {
      setOpenNodes(openNodes.filter((item) => item.id !== id));
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

  const updateActiveTabMap = (activeTabId: string) => {
    setActiveTabMaps(prev => ({
      ...prev,
      [sourceRoute]: activeTabId
    }));
  }

  const closeTab = (value: string) => {
    const newTabs = tabs.filter((item) => item.value !== value);
    setTabs(newTabs);
    if (value === activeTab) {
      const newActiveTab = newTabs[newTabs.length - 1]?.value;
      const newActiveNode = findTreeNode(treeData, newActiveTab);
      updateActiveTabMap(newActiveTab);
      setActiveNode(newActiveNode);
    } else {
      updateActiveTabMap('');
      setActiveNode(undefined)
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
    updateActiveTabMap(newTab.value);
    setActiveNode(undefined);
  };

  const changeActiveTab = async (node: CustomTreeNode, isNew = false) => {
    if (!node) return;
    const nodeId = node.id.toString();
    const existTab = tabs.find((item) => item.value === node.id.toString());
    if (!existTab) {
      const newTab = {
        label: node.label ?? '',
        value: nodeId,
        tooltip: node.tooltip
      };

      if (activeTab) {
        const newTabs = isNew || !tabs.length
          ? [...tabs, newTab]
          : tabs.map((item) => (item.value === activeTab ? newTab : item));
        setTabs(newTabs);
      } else {
        setTabs([newTab]);
      }
    }
    updateActiveTabMap(nodeId);
    setActiveNode(node);
  };

  const removeTreeNode = (id: string) => {
    setOpenNodes(openNodes.filter((item) => item.id !== id));
    setTreeData(treeData.filter((item) => item.id !== id));
    const tabIndex = tabs.findIndex((tab) => tab.value === id);
    if (tabIndex > -1) {
      const newActiveTab = tabs[(tabIndex + 1) % tabs.length]?.value;
      const newActiveNode = findTreeNode(treeData, newActiveTab);
      updateActiveTabMap(newActiveTab);
      setActiveNode(newActiveNode);
    }
  };

  const removeNode = async (node: CustomTreeNode) => {
    setOpenNodes(openNodes.filter((item) => item.id !== node.id));
    setTreeData(treeData.filter((item) => item.id !== node.id));
    const tabIndex = tabs.findIndex((tab) => tab.value === node.id);
    if (tabIndex > -1) {
      const newActiveTab = tabs[(tabIndex + 1) % tabs.length]?.value;
      const newActiveNode = findTreeNode(treeData, newActiveTab);
      updateActiveTabMap(newActiveTab);
      setActiveNode(newActiveNode);
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
    if (!node) return;
    if (node.level === TREE_LEVEL.DATABASE) {
      return await refreshDatabaseNode(node);
    } else if (node.level === TREE_LEVEL.SCHEMA) {
      await refreshSchemaNode(node);
    } else if (node.level === TREE_LEVEL.TABLE) {
      await refreshTableNode(node);
    } else if (node.level === TREE_LEVEL.COLUMNS) {
      await refreshColumnsNode(node);
    } else if (node.level === TREE_LEVEL.TABLE_CHECKS) {
      await refreshTableChecksNode(node);
    } else if (node.level === TREE_LEVEL.TABLE_DAILY_CHECKS) {
      await refreshTableCheckpointsNode(node, 'daily');
    } else if (node.level === TREE_LEVEL.TABLE_MONTHLY_CHECKS) {
      await refreshTableCheckpointsNode(node, 'monthly');
    }  else if (node.level === TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS) {
      await refreshTablePartitionedCheckpointsNode(node, 'daily');
    } else if (node.level === TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS) {
      await refreshTablePartitionedCheckpointsNode(node, 'monthly');
    } else if (node.level === TREE_LEVEL.COLUMN) {
      await refreshColumnNode(node);
    } else if (node.level === TREE_LEVEL.COLUMN_CHECKS) {
      await refreshColumnChecksNode(node);
    } else if (node.level === TREE_LEVEL.COLUMN_DAILY_CHECKS) {
      await refreshColumnCheckpointsNode(node, 'daily');
    } else if (node.level === TREE_LEVEL.COLUMN_MONTHLY_CHECKS) {
      await refreshColumnCheckpointsNode(node, 'monthly');
    } else if (node.level === TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS) {
      await refreshColumnPartitionedChecksNode(node, 'daily');
    } else if (node.level === TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS) {
      await refreshColumnPartitionedChecksNode(node, 'monthly');
    }
  };

  const runChecks = async (node: CustomTreeNode) => {
    if (node.run_checks_job_template) {
      JobApiClient.runChecks({
        checkSearchFilters: node.run_checks_job_template
      });
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
      JobApiClient.runChecks({
        checkSearchFilters: res.data.run_checks_job_template
      });
      return;
    }
    if (node.level === TREE_LEVEL.TABLE_DAILY_CHECKS) {
      const res = await TableApiClient.getTableCheckpointsUI(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        'daily'
      );
      JobApiClient.runChecks({
        checkSearchFilters: res.data.run_checks_job_template
      });
      return;
    }
    if (node.level === TREE_LEVEL.TABLE_MONTHLY_CHECKS) {
      const res = await TableApiClient.getTableCheckpointsUI(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        'monthly'
      );
      JobApiClient.runChecks({
        checkSearchFilters: res.data.run_checks_job_template
      });
      return;
    }
    if (node.level === TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS) {
      const res = await TableApiClient.getTablePartitionedChecksUI(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        'daily'
      );
      JobApiClient.runChecks({
        checkSearchFilters: res.data.run_checks_job_template
      });
      return;
    }
    if (node.level === TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS) {
      const res = await TableApiClient.getTablePartitionedChecksUI(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        'daily'
      );
      JobApiClient.runChecks({
        checkSearchFilters: res.data.run_checks_job_template
      });
      return;
    }

    if (node.level === TREE_LEVEL.COLUMN_CHECKS) {
      const res = await ColumnApiClient.getColumnAdHocChecksUI(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        columnNode?.label ?? ''
      );
      JobApiClient.runChecks({
        checkSearchFilters: res.data.run_checks_job_template
      });
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
      JobApiClient.runChecks({
        checkSearchFilters: res.data.run_checks_job_template
      });
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
      JobApiClient.runChecks({
        checkSearchFilters: res.data.run_checks_job_template
      });
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
      JobApiClient.runChecks({
        checkSearchFilters: res.data.run_checks_job_template
      });
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
      JobApiClient.runChecks({
        checkSearchFilters: res.data.run_checks_job_template
      });
      return;
    }
  };

  const collectStatisticsOnTable = async (node: CustomTreeNode) => {
    if (node.collect_statistics_job_template) {
      JobApiClient.collectStatisticsOnTable(node.collect_statistics_job_template);
      return;
    }
  };

  const deleteStoredData = async (node: CustomTreeNode, params: { [key: string]: string | boolean }) => {
    if (node.data_clean_job_template) {
      let checkType;
      switch (sourceRoute) {
        case CheckTypes.CHECKS:
          checkType = 'checkpoint';
          break;
        case CheckTypes.PROFILING:
          checkType = 'adhoc';
          break;
        case CheckTypes.TIME_PARTITIONED:
          checkType = 'partitioned';
          break;
        default:
          checkType = undefined;
          break;
      }
      JobApiClient.deleteStoredData({
        ...node.data_clean_job_template,
        checkType,
        ...params,
      });
      return;
    }
  };

  const handleChangeActiveTab = (newActiveTab: string) => {
    const newActiveNode = findTreeNode(treeData, newActiveTab);
    updateActiveTabMap(newActiveTab);
    setActiveNode(newActiveNode);
  };

  const pushHistory = (path: string) => {
    history.push(path);
  }

  const switchTab = (node: CustomTreeNode) => {
    console.log('node:', node);
    if (!node) return;
    const defaultConnectionTab = sourceRoute === CheckTypes.SOURCES ? 'detail' : 'schedule';
    if (node.level === TREE_LEVEL.DATABASE) {
      pushHistory(ROUTES.CONNECTION_DETAIL(sourceRoute, node.label, subTabMap[node.label] || defaultConnectionTab));
    } else if (node.level === TREE_LEVEL.SCHEMA) {
      const connectionNode = findTreeNode(treeData, node.parentId ?? '');

      pushHistory(ROUTES.SCHEMA_LEVEL_PAGE(sourceRoute, connectionNode?.label ?? '', node.label, 'tables'));
    } else if (node.level === TREE_LEVEL.TABLE) {
      const schemaNode = findTreeNode(treeData, node?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

      let tab = subTabMap[node.id];
      if (sourceRoute === CheckTypes.CHECKS || sourceRoute === CheckTypes.TIME_PARTITIONED) {
        tab = tab || 'daily';
      } else {
        tab = tab || 'detail'
      }
      pushHistory(ROUTES.TABLE_LEVEL_PAGE(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', node.label, tab));
    } else if ([TREE_LEVEL.TABLE_CHECKS, TREE_LEVEL.TABLE_DAILY_CHECKS, TREE_LEVEL.TABLE_MONTHLY_CHECKS, TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS, TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS].includes(node.level)) {
      const tableNode = findTreeNode(treeData, node.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

      if (node.level === TREE_LEVEL.TABLE_CHECKS) {
        pushHistory(ROUTES.TABLE_AD_HOCS(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? ''));
      } else if (node.level === TREE_LEVEL.TABLE_DAILY_CHECKS) {
        pushHistory(ROUTES.TABLE_CHECKPOINTS(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', 'daily'));
      } else if (node.level === TREE_LEVEL.TABLE_MONTHLY_CHECKS) {
        pushHistory(ROUTES.TABLE_CHECKPOINTS(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', 'monthly'));
      } else if (node.level === TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS) {
        pushHistory(ROUTES.TABLE_PARTITIONED(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', 'daily'));
      } else if (node.level === TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS) {
        pushHistory(ROUTES.TABLE_PARTITIONED(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', 'monthly'));
      }
    } else if ([TREE_LEVEL.COLUMN_CHECKS, TREE_LEVEL.COLUMN_DAILY_CHECKS, TREE_LEVEL.COLUMN_MONTHLY_CHECKS, TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS, TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS].includes(node.level)) {
      const columnNode = findTreeNode(treeData, node?.parentId ?? '');
      const columnsNode = findTreeNode(treeData, columnNode?.parentId ?? '');
      const tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

      if (node?.level === TREE_LEVEL.COLUMN_CHECKS) {
        pushHistory(ROUTES.COLUMN_AD_HOCS(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', columnNode?.label ?? ''));
      } else if (node.level === TREE_LEVEL.COLUMN_DAILY_CHECKS) {
        pushHistory(ROUTES.COLUMN_CHECKPOINTS(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', columnNode?.label ?? '', 'daily'));
      } else if (node.level === TREE_LEVEL.COLUMN_MONTHLY_CHECKS) {
        pushHistory(ROUTES.COLUMN_CHECKPOINTS(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', columnNode?.label ?? '', 'monthly'));
      } else if (node.level === TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS) {
        pushHistory(ROUTES.COLUMN_PARTITIONED(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', columnNode?.label ?? '', 'daily'));
      } else if (node.level === TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS) {
        pushHistory(ROUTES.COLUMN_PARTITIONED(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', columnNode?.label ?? '', 'monthly'));
      }
    } else if (node.level === TREE_LEVEL.CHECK) {
      const parentNode = findTreeNode(treeData, node.parentId ?? '');
      if (!parentNode) {
        return;
      }
      if ([TREE_LEVEL.TABLE_CHECKS, TREE_LEVEL.TABLE_DAILY_CHECKS, TREE_LEVEL.TABLE_MONTHLY_CHECKS, TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS, TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS].includes(parentNode.level)) {
        const tableNode = findTreeNode(treeData, parentNode?.parentId ?? '');
        const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
        const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
        if (parentNode?.level === TREE_LEVEL.TABLE_CHECKS) {
          pushHistory(ROUTES.TABLE_AD_HOCS_UI_FILTER(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', node.category ?? '', node.label));
        } else if (parentNode.level === TREE_LEVEL.TABLE_DAILY_CHECKS) {
          pushHistory(ROUTES.TABLE_CHECKPOINTS_UI_FILTER(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', 'daily', node.category ?? '', node.label));
        } else if (parentNode.level === TREE_LEVEL.TABLE_MONTHLY_CHECKS) {
          pushHistory(ROUTES.TABLE_CHECKPOINTS_UI_FILTER(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', 'monthly', node.category ?? '', node.label));
        } else if (parentNode.level === TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS) {
          pushHistory(ROUTES.TABLE_PARTITIONED_UI_FILTER(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', 'daily', node.category ?? '', node.label));
        } else if (parentNode.level === TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS) {
          pushHistory(ROUTES.TABLE_PARTITIONED_UI_FILTER(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', 'monthly', node.category ?? '', node.label));
        }
      } else if ([TREE_LEVEL.COLUMN_CHECKS, TREE_LEVEL.COLUMN_DAILY_CHECKS, TREE_LEVEL.COLUMN_MONTHLY_CHECKS, TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS, TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS].includes(parentNode.level)) {
        const columnNode = findTreeNode(treeData, parentNode?.parentId ?? '');
        const columnsNode = findTreeNode(treeData, columnNode?.parentId ?? '');
        const tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
        const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
        const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

        if (parentNode?.level === TREE_LEVEL.COLUMN_CHECKS) {
          pushHistory(ROUTES.COLUMN_AD_HOCS_UI_FILTER(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', columnNode?.label ?? '', node.category ?? '', node.label));
        } else if (parentNode.level === TREE_LEVEL.COLUMN_DAILY_CHECKS) {
          pushHistory(ROUTES.COLUMN_CHECKPOINTS_UI_FILTER(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', columnNode?.label ?? '', 'daily', node.category ?? '', node.label));
        } else if (parentNode.level === TREE_LEVEL.COLUMN_MONTHLY_CHECKS) {
          pushHistory(ROUTES.COLUMN_CHECKPOINTS_UI_FILTER(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', columnNode?.label ?? '', 'monthly', node.category ?? '', node.label));
        } else if (parentNode.level === TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS) {
          pushHistory(ROUTES.COLUMN_PARTITIONED_UI_FILTER(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', columnNode?.label ?? '', 'daily', node.category ?? '', node.label));
        } else if (parentNode.level === TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS) {
          pushHistory(ROUTES.COLUMN_PARTITIONED_UI_FILTER(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', columnNode?.label ?? '', 'monthly', node.category ?? '', node.label));
        }
      }
    } else if (node.level === TREE_LEVEL.COLUMNS) {
      const tableNode = findTreeNode(treeData, node.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');
      pushHistory(ROUTES.TABLE_COLUMNS(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? ''));
    } else if (node.level === TREE_LEVEL.COLUMN) {
      const columnsNode = findTreeNode(treeData, node?.parentId ?? '');
      const tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

      let tab = subTabMap[node.id];
      if (sourceRoute === CheckTypes.CHECKS || sourceRoute === CheckTypes.TIME_PARTITIONED) {
        tab = tab || 'daily';
      } else {
        tab = tab || 'detail'
      }

      pushHistory(ROUTES.COLUMN_LEVEL_PAGE(sourceRoute, connectionNode?.label ?? '', schemaNode?.label ?? '', tableNode?.label ?? '', node.label, tab));
    } else {
      pushHistory('/checks');
    }
  }

  useEffect(() => {
    const _activeTab = activeTabMaps[sourceRoute];
    if (_activeTab) {
      const node = findTreeNode(treeData, _activeTab);
      if (node) {
        switchTab(node);
      }
    }
  }, [sourceRoute]);

  useLayoutEffect(() => {
    const initialPathName = history.location.pathname;
    const activeSourceRoute = initialPathName.split('/')[1] ?? '';
    setSourceRoute(activeSourceRoute);
    const unlisten = history.listen((location) => {
      const pathName = location.pathname;
      const activeSourceRoute = pathName.split('/')[1] ?? '';
      setSourceRoute(activeSourceRoute);
    });
    return unlisten;
  }, [history]);

  return (
    <TreeContext.Provider
      value={{
        treeData,
        setTreeData,
        getConnections,
        tabs,
        activeTab,
        setActiveTab: handleChangeActiveTab,
        closeTab,
        onAddTab,
        openNodes,
        toggleOpenNode,
        tabMap: subTabMap,
        setTabMap: setSubTabMap,
        changeActiveTab,
        sidebarWidth,
        setSidebarWidth,
        removeTreeNode,
        removeNode,
        refreshNode,
        runChecks,
        collectStatisticsOnTable,
        addConnection,
        switchTab,
        activeNode,
        setSourceRoute,
        deleteStoredData
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
