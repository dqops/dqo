import React, { useCallback, useEffect, useMemo, useState } from 'react';

import { TreeNodeId } from '@naisutech/react-tree/types/Tree';
import axios, { AxiosResponse } from 'axios';
import { useSelector } from 'react-redux';
import { useHistory, useLocation } from 'react-router-dom';
import {
  CheckListModel,
  CheckSearchFilters,
  ColumnListModel,
  ConnectionModel,
  RunChecksParameters,
  SchemaModel,
  TableListModel
} from '../api';
import { useActionDispatch } from '../hooks/useActionDispatch';
import { toggleMenu } from '../redux/actions/job.actions';
import { addFirstLevelTab } from '../redux/actions/source.actions';
import { IRootState } from '../redux/reducers';
import { getFirstLevelActiveTab } from '../redux/selectors';
import {
  ColumnApiClient,
  ConnectionApiClient,
  JobApiClient,
  SchemaApiClient,
  TableApiClient
} from '../services/apiClient';
import { TABLES_LIMIT_TREE_PAGING } from '../shared/config';
import { TREE_LEVEL } from '../shared/enums';
import { CustomTreeNode, ITab } from '../shared/interfaces';
import { CheckTypes, ROUTES } from '../shared/routes';
import {
  getFirstLevelColumnTab,
  getFirstLevelTableTab,
  urlencodeDecoder,
  urlencodeEncoder
} from '../utils';
import { findTreeNode } from '../utils/tree';

const TreeContext = React.createContext({} as any);

export const checkTypesToJobTemplateKey = {
  [CheckTypes.SOURCES]: 'run_checks_job_template',
  [CheckTypes.PROFILING]: 'run_profiling_checks_job_template',
  [CheckTypes.MONITORING]: 'run_monitoring_checks_job_template',
  [CheckTypes.PARTITIONED]: 'run_partition_checks_job_template'
};

const checkTypesToHasConfiguredCheckKey = {
  [CheckTypes.SOURCES]: 'has_any_configured_checks',
  [CheckTypes.PROFILING]: 'has_any_configured_profiling_checks',
  [CheckTypes.MONITORING]: 'has_any_configured_monitoring_checks',
  [CheckTypes.PARTITIONED]: 'has_any_configured_partition_checks'
};

function TreeProvider(props: any) {
  const [treeDataMaps, setTreeDataMaps] = useState<
    Record<string, CustomTreeNode[]>
  >({});
  const location = useLocation();
  const initialPathName = location.pathname;
  const activeSourceRoute = (initialPathName.split('/')[1] ??
    CheckTypes.SOURCES) as CheckTypes;
  const checkTypes: CheckTypes = [
    CheckTypes.MONITORING,
    CheckTypes.SOURCES,
    CheckTypes.PROFILING,
    CheckTypes.PARTITIONED
  ].includes(activeSourceRoute)
    ? activeSourceRoute
    : CheckTypes.SOURCES;
  const treeData = useMemo(
    () => treeDataMaps[checkTypes] ?? [],
    [treeDataMaps, checkTypes]
  );
  const setTreeData = useCallback(
    (_treeData: CustomTreeNode[]) => {
      setTreeDataMaps((prev) => ({
        ...prev,
        [checkTypes]: _treeData
      }));
    },
    [checkTypes]
  );
  const [openNodes, setOpenNodes] = useState<CustomTreeNode[]>([]);
  const [tabMaps, setTabMaps] = useState<Record<string, ITab[]>>({}); // `blue box tab level`
  const [subTabMap, setSubTabMap] = useState<{ [key: string]: string }>({}); // sub tab under `blue box tab level`
  const [tablesLoading, setTablesLoading] = useState('');
  const [columnsLoading, setColumnsLoading] = useState('');
  const tabs = useMemo(() => tabMaps[checkTypes] ?? [], [tabMaps, checkTypes]);
  const setTabs = useCallback(
    (_tabMaps: ITab[]) => {
      setTabMaps((prev) => ({
        ...prev,
        [checkTypes]: _tabMaps
      }));
    },
    [checkTypes]
  );

  const [activeNode, setActiveNode] = useState<CustomTreeNode>();
  const [activeTabMaps, setActiveTabMaps] = useState<Record<string, string>>(
    {}
  );
  const activeTab = activeTabMaps[checkTypes];

  const [sidebarWidth, setSidebarWidth] = useState(280);
  const [sidebarScrollHeight, setSidebarScrollHeight] = useState(0);
  const [selectedTreeNode, setSelectedTreeNode] = useState<CustomTreeNode>();
  const history = useHistory();
  const dispatch = useActionDispatch();
  const [loadingNodes, setLoadingNodes] = useState<Record<string, boolean>>({});
  const [objectNotFound, setObjectNotFound] = useState(false);
  const [loadedTables, setLoadedTables] = useState<{ [key: string]: boolean }>(
    {}
  );
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const { userProfile } = useSelector((state: IRootState) => state.job);
  const getConnections = async () => {
    try {
      const res: AxiosResponse<ConnectionModel[]> =
        await ConnectionApiClient.getAllConnections();
      const mappedConnectionsToTreeData = res.data.map((item) => ({
        id: urlencodeDecoder(item.connection_name ?? ''),
        parentId: null,
        label: item.connection_name ?? '',
        items: [],
        level: TREE_LEVEL.DATABASE,
        tooltip: item.connection_name,
        run_checks_job_template:
          item[
            checkTypesToJobTemplateKey[
              checkTypes as keyof typeof checkTypesToJobTemplateKey
            ] as keyof ConnectionModel
          ],
        collect_statistics_job_template: item.collect_statistics_job_template,
        data_clean_job_template: item.data_clean_job_template,
        open: false,
        parsingYamlError: item.yaml_parsing_error
      }));
      const treeDataMaps = [
        CheckTypes.MONITORING,
        CheckTypes.SOURCES,
        CheckTypes.PROFILING,
        CheckTypes.PARTITIONED
      ].reduce(
        (acc, cur) => ({
          ...acc,
          [cur]: mappedConnectionsToTreeData
        }),
        {}
      );
      setTreeDataMaps(treeDataMaps);
    } catch (err) {
      console.warn(err);
    }
  };

  const addConnection = async (connection: ConnectionModel) => {
    const newNode = {
      id: urlencodeDecoder(connection.connection_name ?? ''),
      parentId: null,
      label: connection.connection_name ?? '',
      items: [],
      level: TREE_LEVEL.DATABASE,
      tooltip: connection.connection_name,
      run_checks_job_template: connection[
        checkTypesToJobTemplateKey[
          checkTypes as keyof typeof checkTypesToJobTemplateKey
        ] as keyof ConnectionModel
      ] as CheckSearchFilters,
      collect_statistics_job_template:
        connection.collect_statistics_job_template,
      data_clean_job_template: connection.data_clean_job_template,
      open: false
    };

    const newTreeDataMaps = [
      CheckTypes.MONITORING,
      CheckTypes.SOURCES,
      CheckTypes.PROFILING,
      CheckTypes.PARTITIONED
    ].reduce(
      (acc, cur) => ({
        ...acc,
        [cur]: [...treeDataMaps[cur], newNode]
      }),
      {}
    );
    const url = `${ROUTES.CONNECTION_DETAIL(
      CheckTypes.SOURCES,
      connection.connection_name ?? '',
      'schemas'
    )}?import_schema=true`;
    await dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value: ROUTES.CONNECTION_LEVEL_VALUE(
          checkTypes,
          connection.connection_name ?? ''
        ),
        state: {},
        label: connection.connection_name ?? ''
      })
    );
    setTreeDataMaps(newTreeDataMaps);

    history.push(url);
  };

  useEffect(() => {
    getConnections();
  }, []);

  const resetTreeData = (
    node: CustomTreeNode,
    items: CustomTreeNode[],
    loading?: boolean
  ) => {
    setOpenNodes((prev) => [
      ...prev.filter(
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
        item.id === node.id ? { ...item, open: loading ?? !item.open } : item
      );
    setTreeData([...newTreeData, ...items]);
  };

  const refreshDatabaseNode = async (
    node: CustomTreeNode,
    reset = true
  ): Promise<CustomTreeNode[]> => {
    const res: AxiosResponse<SchemaModel[]> = await SchemaApiClient.getSchemas(
      node.label
    );

    const items = res.data.map((schema) => ({
      id: `${node.id}.${urlencodeDecoder(schema.schema_name ?? '')}`,
      label: schema.schema_name || '',
      level: TREE_LEVEL.SCHEMA,
      parentId: node.id,
      items: [],
      tooltip:
        schema.directory_prefix ?? `${node?.label}.${schema.schema_name}`,
      run_checks_job_template: schema[
        checkTypesToJobTemplateKey[
          checkTypes as keyof typeof checkTypesToJobTemplateKey
        ] as keyof SchemaModel
      ] as CheckSearchFilters,
      collect_statistics_job_template: schema.collect_statistics_job_template,
      data_clean_job_template: schema.data_clean_job_template,
      open: false,
      error_message: schema.error_message
    }));

    if (reset) {
      resetTreeData(node, items);
    }

    return items;
  };

  const addSchema = async (node: CustomTreeNode, schemaName: string) => {
    const newNode = {
      id: `${node.id}.${urlencodeDecoder(schemaName)}`,
      label: schemaName || '',
      level: TREE_LEVEL.SCHEMA,
      parentId: node.id,
      items: [],
      tooltip: `${node?.label}.${schemaName}`,
      open: false
    };
    setTreeData([...treeData, newNode]);
  };

  const refreshSchemaNode = async (
    node: CustomTreeNode,
    reset = true
  ): Promise<CustomTreeNode[]> => {
    const oldLoadedTables = { ...loadedTables };
    oldLoadedTables[node?.id ?? ''] = false;
    setLoadedTables(oldLoadedTables);
    const res: AxiosResponse<TableListModel[]> = await TableApiClient.getTables(
      node.parentId?.toString() || '',
      node.label,
      undefined,
      1,
      TABLES_LIMIT_TREE_PAGING
    );
    const items = res.data.map((table) => ({
      id: `${node.id}.${urlencodeDecoder(table.target?.table_name ?? '')}`,
      label: table.target?.table_name || '',
      level: TREE_LEVEL.TABLE,
      parentId: node.id,
      items: [],
      tooltip: `${node.parentId?.toString() || ''}.${node.label}.${
        table.target?.table_name
      }`,
      hasCheck:
        !!table?.[
          checkTypesToHasConfiguredCheckKey[
            checkTypes as keyof typeof checkTypesToHasConfiguredCheckKey
          ] as keyof TableListModel
        ],
      run_checks_job_template: table[
        checkTypesToJobTemplateKey[
          checkTypes as keyof typeof checkTypesToJobTemplateKey
        ] as keyof TableListModel
      ] as CheckSearchFilters,
      collect_statistics_job_template: table.collect_statistics_job_template,
      data_clean_job_template: table.data_clean_job_template,
      open: false,
      configured: table.partitioning_configuration_missing,
      parsingYamlError: table.yaml_parsing_error
    }));
    if (reset) {
      resetTreeData(node, items);
    }

    return items;
  };

  const refreshTableNode = async (node: CustomTreeNode, reset = true) => {
    const items = [
      {
        id: `${node.id}.columns`,
        label: `Columns`,
        level: TREE_LEVEL.COLUMNS,
        parentId: node.id,
        items: [],
        tooltip: `${node.tooltip} columns`,
        open: false
      }
    ];
    if (checkTypes === CheckTypes.PROFILING) {
      items.push({
        id: `${node.id}.checks`,
        label: 'Profiling checks',
        level: TREE_LEVEL.TABLE_CHECKS,
        parentId: node.id,
        items: [],
        tooltip: `${node.tooltip} checks`,
        open: false
      });
    }
    if (checkTypes === CheckTypes.MONITORING) {
      if (
        userProfile.license_type &&
        userProfile.license_type?.toLowerCase() !== 'free' &&
        !userProfile.trial_period_expires_at
      ) {
        items.push(
          {
            id: `${node.id}.dailyCheck`,
            label: 'Daily monitoring',
            level: TREE_LEVEL.TABLE_DAILY_CHECKS,
            parentId: node.id,
            items: [],
            tooltip: `${node.tooltip} daily monitoring`,
            open: false
          },
          {
            id: `${node.id}.monthlyCheck`,
            label: 'Monthly monitoring',
            level: TREE_LEVEL.TABLE_MONTHLY_CHECKS,
            parentId: node.id,
            items: [],
            tooltip: `${node.tooltip} monthly monitoring`,
            open: false
          }
        );
      } else {
        items.push({
          id: `${node.id}.dailyCheck`,
          label: 'Daily monitoring',
          level: TREE_LEVEL.TABLE_DAILY_CHECKS,
          parentId: node.id,
          items: [],
          tooltip: `${node.tooltip} daily monitoring`,
          open: false
        });
      }
    }
    if (checkTypes === CheckTypes.PARTITIONED) {
      if (
        userProfile.license_type &&
        userProfile.license_type?.toLowerCase() !== 'free' &&
        !userProfile.trial_period_expires_at
      ) {
        items.push(
          {
            id: `${node.id}.dailyPartitionedChecks`,
            label: 'Day period checks',
            level: TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS,
            parentId: node.id,
            items: [],
            tooltip: `${node.tooltip} day period checks`,
            open: false
          },
          {
            id: `${node.id}.monthlyPartitionedChecks`,
            label: 'Month period checks',
            level: TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS,
            parentId: node.id,
            items: [],
            tooltip: `${node.tooltip} month period checks`,
            open: false
          }
        );
      } else {
        items.push({
          id: `${node.id}.dailyPartitionedChecks`,
          label: 'Day period checks',
          level: TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS,
          parentId: node.id,
          items: [],
          tooltip: `${node.tooltip} day period checks`,
          open: false
        });
      }
    }

    if (reset) {
      resetTreeData(node, items);
    }

    return items;
  };

  const refreshColumnNode = async (node: CustomTreeNode, reset = true) => {
    const { connection, table, schema } = parseNodeId(node.id);

    const items = [];
    if (checkTypes === CheckTypes.PROFILING) {
      items.push({
        id: `${node.id}.checks`,
        label: `Profiling checks`,
        level: TREE_LEVEL.COLUMN_CHECKS,
        parentId: node.id,
        items: [],
        tooltip: `${connection}.${schema}.${table}.${node.label} checks`,
        open: false
      });
    }
    if (checkTypes === CheckTypes.MONITORING) {
      if (
        userProfile.license_type &&
        userProfile.license_type?.toLowerCase() !== 'free' &&
        !userProfile.trial_period_expires_at
      ) {
        items.push(
          {
            id: `${node.id}.dailyCheck`,
            label: 'Daily monitoring',
            level: TREE_LEVEL.COLUMN_DAILY_CHECKS,
            parentId: node.id,
            items: [],
            tooltip: `${connection}.${schema}.${table}.${node?.label} daily monitoring`,
            open: false
          },
          {
            id: `${node.id}.monthlyCheck`,
            label: 'Monthly monitoring',
            level: TREE_LEVEL.COLUMN_MONTHLY_CHECKS,
            parentId: node.id,
            items: [],
            tooltip: `${connection}.${schema}.${table}.${node?.label} monthly monitoring`,
            open: false
          }
        );
      } else {
        items.push({
          id: `${node.id}.dailyCheck`,
          label: 'Daily monitoring',
          level: TREE_LEVEL.COLUMN_DAILY_CHECKS,
          parentId: node.id,
          items: [],
          tooltip: `${connection}.${schema}.${table}.${node?.label} daily monitoring`,
          open: false
        });
      }
    }
    if (checkTypes === CheckTypes.PARTITIONED) {
      if (
        userProfile.license_type &&
        userProfile.license_type?.toLowerCase() !== 'free' &&
        !userProfile.trial_period_expires_at
      ) {
        items.push(
          {
            id: `${node.id}.dailyPartitionedChecks`,
            label: 'Day period checks',
            level: TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS,
            parentId: node.id,
            items: [],
            tooltip: `${connection}.${schema}.${table}.${node?.label} day period checks`,
            open: false
          },
          {
            id: `${node.id}.monthlyPartitionedChecks`,
            label: 'Month period checks',
            level: TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS,
            parentId: node.id,
            items: [],
            tooltip: `${connection}.${schema}.${table}.${node?.label} month period checks`,
            open: false
          }
        );
      } else {
        items.push({
          id: `${node.id}.dailyPartitionedChecks`,
          label: 'Day period checks',
          level: TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS,
          parentId: node.id,
          items: [],
          tooltip: `${connection}.${schema}.${table}.${node?.label} day period checks`,
          open: false
        });
      }
    }
    if (reset) {
      resetTreeData(node, items);
    }

    return items;
  };

  const parseNodeId = (id: TreeNodeId) => {
    const terms = id.toString().split('.');

    const table = urlencodeEncoder(terms[2]);
    const schema = urlencodeEncoder(terms[1]);
    const connection = urlencodeEncoder(terms[0]);
    const column = urlencodeEncoder(terms[4]);

    return { connection, schema, table, column };
  };

  const refreshColumnsNode = async (node: CustomTreeNode, reset = true) => {
    const { connection, schema, table } = parseNodeId(node.id);

    const res: AxiosResponse<ColumnListModel[]> =
      await ColumnApiClient.getColumns(
        connection ?? '',
        schema ?? '',
        table ?? '',
        false
      );
    const items = res.data.map((column) => ({
      id: `${node.id}.${urlencodeDecoder(column.column_name ?? '')}`,
      label: column.column_name || '',
      level: TREE_LEVEL.COLUMN,
      parentId: node.id,
      items: [],
      tooltip: `${connection}.${schema}.${table}.${column.column_name}`,
      hasCheck:
        !!column?.[
          checkTypesToHasConfiguredCheckKey[
            checkTypes as keyof typeof checkTypesToHasConfiguredCheckKey
          ] as keyof ColumnListModel
        ],
      run_checks_job_template: column[
        checkTypesToJobTemplateKey[
          checkTypes as keyof typeof checkTypesToJobTemplateKey
        ] as keyof ColumnListModel
      ] as CheckSearchFilters,
      collect_statistics_job_template: column.collect_statistics_job_template,
      data_clean_job_template: column.data_clean_job_template,
      open: false,
      configured:
        column.has_any_configured_checks ||
        column.has_any_configured_partition_checks ||
        column.has_any_configured_profiling_checks ||
        column.has_any_configured_monitoring_checks
    }));
    if (reset) {
      resetTreeData(node, items);
    }

    return items;
  };

  const refreshTableChecksNode = async (node: CustomTreeNode, reset = true) => {
    const { connection, schema, table } = parseNodeId(node.id);
    const res = await TableApiClient.getTableProfilingChecksBasicModel(
      connection ?? '',
      schema ?? '',
      table ?? ''
    );
    return addChecks(
      res.data.checks || [],
      node,
      `${connection}.${schema}.${table}`,
      reset
    );
  };

  const refreshTableMonitoringNode = async (
    node: CustomTreeNode,
    timePartitioned: 'daily' | 'monthly',
    reset = true
  ) => {
    const { connection, schema, table } = parseNodeId(node.id);

    const res = await TableApiClient.getTableMonitoringChecksBasicModel(
      connection ?? '',
      schema ?? '',
      table ?? '',
      timePartitioned
    );
    return addChecks(
      res.data.checks || [],
      node,
      `${connection}.${schema}.${table}`,
      reset
    );
  };

  const refreshTablePartitionedMonitoringNode = async (
    node: CustomTreeNode,
    timePartitioned: 'daily' | 'monthly',
    reset = true
  ) => {
    const { connection, schema, table } = parseNodeId(node.id);

    const res = await TableApiClient.getTablePartitionedChecksBasicModel(
      connection ?? '',
      schema ?? '',
      table ?? '',
      timePartitioned
    );
    return addChecks(
      res.data.checks || [],
      node,
      `${connection}.${schema}.${table}`,
      reset
    );
  };

  const refreshColumnChecksNode = async (
    node: CustomTreeNode,
    reset = true
  ) => {
    const { connection, schema, table, column } = parseNodeId(node.id);
    const res = await ColumnApiClient.getColumnProfilingChecksBasicModel(
      connection ?? '',
      schema ?? '',
      table ?? '',
      column ?? ''
    );
    return addChecks(
      res.data.checks || [],
      node,
      `${connection}.${schema}.${table}.${node.label}`,
      reset
    );
  };

  const refreshColumnMonitoringNode = async (
    node: CustomTreeNode,
    timePartitioned: 'daily' | 'monthly',
    reset = true
  ) => {
    const { connection, schema, table, column } = parseNodeId(node.id);
    const res = await ColumnApiClient.getColumnMonitoringChecksBasicModel(
      connection ?? '',
      schema ?? '',
      table ?? '',
      column ?? '',
      timePartitioned
    );
    return addChecks(
      res.data.checks || [],
      node,
      `${connection}.${schema}.${table}.${node.label}`,
      reset
    );
  };

  const refreshColumnPartitionedChecksNode = async (
    node: CustomTreeNode,
    timePartitioned: 'daily' | 'monthly',
    reset = true
  ) => {
    const { connection, schema, table, column } = parseNodeId(node.id);
    const res = await ColumnApiClient.getColumnPartitionedChecksBasicModel(
      connection ?? '',
      schema ?? '',
      table ?? '',
      column ?? '',
      timePartitioned
    );
    return addChecks(
      res.data.checks || [],
      node,
      `${connection}.${schema}.${table}.${node.label}`,
      reset
    );
  };

  const addChecks = (
    checks: CheckListModel[],
    node: CustomTreeNode,
    tooltipSuffix: string,
    reset = true
  ): CustomTreeNode[] => {
    const items: CustomTreeNode[] = [];
    checks?.forEach((check) => {
      items.push({
        id: `${node.id}.${urlencodeDecoder(
          check?.check_category ?? ''
        )}_${urlencodeDecoder(check?.check_name ?? '')}`,
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
    if (reset) {
      resetTreeData(node, items);
    }

    return items;
  };

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
    setActiveTabMaps((prev) => ({
      ...prev,
      [checkTypes]: activeTabId
    }));
  };

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
      setActiveNode(undefined);
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
    const existTab = tabs.find((item) => item.value === nodeId);
    if (!existTab) {
      const newTab = {
        label: node.label ?? '',
        value: nodeId,
        tooltip: node.tooltip
      };

      if (activeTab) {
        const newTabs =
          isNew || !tabs.length
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
    const newTreeDataMaps = [
      CheckTypes.MONITORING,
      CheckTypes.SOURCES,
      CheckTypes.PROFILING,
      CheckTypes.PARTITIONED
    ].reduce(
      (acc, cur) => ({
        ...acc,
        [cur]: treeDataMaps[cur]?.filter(
          (item) =>
            !(
              item.id.toString().startsWith(node.id.toString() + '.') ||
              item.id.toString() === node.id.toString()
            )
        )
      }),
      {}
    );

    setTreeDataMaps(newTreeDataMaps);

    const tabIndex = tabs.findIndex((tab) => tab.value === node.id);
    if (tabIndex > -1) {
      const newActiveTab = tabs[(tabIndex + 1) % tabs.length]?.value;
      const newActiveNode = findTreeNode(treeData, newActiveTab);
      updateActiveTabMap(newActiveTab);
      setActiveNode(newActiveNode);

      const newTabsMaps = [
        CheckTypes.MONITORING,
        CheckTypes.SOURCES,
        CheckTypes.PROFILING,
        CheckTypes.PARTITIONED
      ].reduce(
        (acc, cur) => ({
          ...acc,
          [cur]: (tabMaps[cur] || [])?.filter(
            (item) =>
              !(
                item.value.toString().startsWith(node.id.toString() + '.') ||
                item.value.toString() === node.id.toString()
              )
          )
        }),
        {}
      );

      setTabMaps(newTabsMaps);
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

  const refreshNode = async (
    node: CustomTreeNode,
    reset = true
  ): Promise<CustomTreeNode[]> => {
    if (!node) return [];
    setLoadingNodes((prev) => ({
      ...prev,
      [node.id]: true
    }));
    let newItems: CustomTreeNode[] = [];
    if (node.level === TREE_LEVEL.DATABASE) {
      newItems = await refreshDatabaseNode(node, reset);
    } else if (node.level === TREE_LEVEL.SCHEMA) {
      newItems = await refreshSchemaNode(node, reset);
    } else if (node.level === TREE_LEVEL.TABLE) {
      newItems = await refreshTableNode(node, reset);
    } else if (node.level === TREE_LEVEL.COLUMNS) {
      newItems = await refreshColumnsNode(node, reset);
    } else if (node.level === TREE_LEVEL.TABLE_CHECKS) {
      newItems = await refreshTableChecksNode(node, reset);
    } else if (node.level === TREE_LEVEL.TABLE_DAILY_CHECKS) {
      newItems = await refreshTableMonitoringNode(node, 'daily', reset);
    } else if (node.level === TREE_LEVEL.TABLE_MONTHLY_CHECKS) {
      newItems = await refreshTableMonitoringNode(node, 'monthly', reset);
    } else if (node.level === TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS) {
      newItems = await refreshTablePartitionedMonitoringNode(
        node,
        'daily',
        reset
      );
    } else if (node.level === TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS) {
      newItems = await refreshTablePartitionedMonitoringNode(
        node,
        'monthly',
        reset
      );
    } else if (node.level === TREE_LEVEL.COLUMN) {
      newItems = await refreshColumnNode(node, reset);
    } else if (node.level === TREE_LEVEL.COLUMN_CHECKS) {
      newItems = await refreshColumnChecksNode(node, reset);
    } else if (node.level === TREE_LEVEL.COLUMN_DAILY_CHECKS) {
      newItems = await refreshColumnMonitoringNode(node, 'daily', reset);
    } else if (node.level === TREE_LEVEL.COLUMN_MONTHLY_CHECKS) {
      newItems = await refreshColumnMonitoringNode(node, 'monthly', reset);
    } else if (node.level === TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS) {
      newItems = await refreshColumnPartitionedChecksNode(node, 'daily', reset);
    } else if (node.level === TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS) {
      newItems = await refreshColumnPartitionedChecksNode(
        node,
        'monthly',
        reset
      );
    }

    setLoadingNodes((prev) => ({
      ...prev,
      [node.id]: false
    }));

    return newItems;
  };

  const runPartitionedChecks = async (obj: RunChecksParameters) => {
    await JobApiClient.runChecks(undefined, false, undefined, obj);
  };

  const runChecks = async (node: CustomTreeNode) => {
    if (node.run_checks_job_template) {
      JobApiClient.runChecks(undefined, false, undefined, {
        check_search_filters: node.run_checks_job_template
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
      const res = await TableApiClient.getTableProfilingChecksModel(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? ''
      );
      JobApiClient.runChecks(undefined, false, undefined, {
        check_search_filters: res.data.run_checks_job_template
      });
      return;
    }
    if (node.level === TREE_LEVEL.TABLE_DAILY_CHECKS) {
      const res = await TableApiClient.getTableMonitoringChecksModel(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        'daily'
      );
      JobApiClient.runChecks(undefined, false, undefined, {
        check_search_filters: res.data.run_checks_job_template
      });
      return;
    }
    if (node.level === TREE_LEVEL.TABLE_MONTHLY_CHECKS) {
      const res = await TableApiClient.getTableMonitoringChecksModel(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        'monthly'
      );
      JobApiClient.runChecks(undefined, false, undefined, {
        check_search_filters: res.data.run_checks_job_template
      });
      return;
    }
    if (node.level === TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS) {
      const res = await TableApiClient.getTablePartitionedChecksModel(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        'daily'
      );
      JobApiClient.runChecks(undefined, false, undefined, {
        check_search_filters: res.data.run_checks_job_template
      });
      return;
    }
    if (node.level === TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS) {
      const res = await TableApiClient.getTablePartitionedChecksModel(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        'daily'
      );
      JobApiClient.runChecks(undefined, false, undefined, {
        check_search_filters: res.data.run_checks_job_template
      });
      return;
    }

    if (node.level === TREE_LEVEL.COLUMN_CHECKS) {
      const res = await ColumnApiClient.getColumnProfilingChecksModel(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        columnNode?.label ?? ''
      );
      JobApiClient.runChecks(undefined, false, undefined, {
        check_search_filters: res.data.run_checks_job_template
      });
      return;
    }
    if (node.level === TREE_LEVEL.COLUMN_DAILY_CHECKS) {
      const res = await ColumnApiClient.getColumnMonitoringChecksModel(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        columnNode?.label ?? '',
        'daily'
      );
      JobApiClient.runChecks(undefined, false, undefined, {
        check_search_filters: res.data.run_checks_job_template
      });
      return;
    }
    if (node.level === TREE_LEVEL.COLUMN_MONTHLY_CHECKS) {
      const res = await ColumnApiClient.getColumnMonitoringChecksModel(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        columnNode?.label ?? '',
        'monthly'
      );
      JobApiClient.runChecks(undefined, false, undefined, {
        check_search_filters: res.data.run_checks_job_template
      });
      return;
    }
    if (node.level === TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS) {
      const res = await ColumnApiClient.getColumnPartitionedChecksModel(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        columnNode?.label ?? '',
        'daily'
      );
      JobApiClient.runChecks(undefined, false, undefined, {
        check_search_filters: res.data.run_checks_job_template
      });
      return;
    }
    if (node.level === TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS) {
      const res = await ColumnApiClient.getColumnPartitionedChecksModel(
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        columnNode?.label ?? '',
        'monthly'
      );
      JobApiClient.runChecks(undefined, false, undefined, {
        check_search_filters: res.data.run_checks_job_template
      });
      return;
    }
  };

  const collectStatisticsOnTable = async (node: CustomTreeNode) => {
    if (node.collect_statistics_job_template) {
      JobApiClient.collectStatisticsOnTable(
        undefined,
        false,
        undefined,
        false,
        undefined,
        node.collect_statistics_job_template
      );
      return;
    }
  };

  const deleteStoredData = async (
    node: CustomTreeNode,
    params: { [key: string]: string | boolean },
    colArr?: string[]
  ) => {
    if (node.data_clean_job_template) {
      let checkType;
      switch (checkTypes) {
        case CheckTypes.MONITORING:
          checkType = 'monitoring';
          break;
        case CheckTypes.PROFILING:
          checkType = 'profiling';
          break;
        case CheckTypes.PARTITIONED:
          checkType = 'partitioned';
          break;
        default:
          checkType = undefined;
          break;
      }
      if (colArr && colArr.length > 0) {
        node.data_clean_job_template.columnNames = colArr;
      }
      JobApiClient.deleteStoredData(undefined, false, undefined, {
        ...node.data_clean_job_template,

        checkType,
        ...params
      });
      return;
    } else {
      let checkType;
      switch (checkTypes) {
        case CheckTypes.MONITORING:
          checkType = 'monitoring';
          break;
        case CheckTypes.PROFILING:
          checkType = 'profiling';
          break;
        case CheckTypes.PARTITIONED:
          checkType = 'partitioned';
          break;
        default:
          checkType = undefined;
          break;
      }
      JobApiClient.deleteStoredData(undefined, false, undefined, {
        connection: node.collect_statistics_job_template?.connection,
        fullTableName: node.collect_statistics_job_template?.fullTableName,
        checkType,
        ...params
      });
    }
  };

  const handleChangeActiveTab = (newActiveTab: string) => {
    const newActiveNode = findTreeNode(treeData, newActiveTab);
    updateActiveTabMap(newActiveTab);
    setActiveNode(newActiveNode);
  };

  const switchTab = async (node: CustomTreeNode, checkType: CheckTypes) => {
    if (!node) return;
    setSelectedTreeNode(node);
    const defaultConnectionTab =
      checkType === CheckTypes.SOURCES ? 'detail' : 'schemas';
    if (node.level === TREE_LEVEL.DATABASE) {
      const url = ROUTES.CONNECTION_DETAIL(
        checkType,
        node.label,
        defaultConnectionTab
      );
      const value = ROUTES.CONNECTION_LEVEL_VALUE(checkType, node.label);
      if (firstLevelActiveTab === value) {
        return;
      }
      dispatch(
        addFirstLevelTab(checkType, {
          url,
          value,
          state: {},
          label: node.label
        })
      );
      history.push(url);
    } else if (node.level === TREE_LEVEL.SCHEMA) {
      const connectionNode = findTreeNode(treeData, node.parentId ?? '');
      const url = ROUTES.SCHEMA_LEVEL_PAGE(
        checkType,
        connectionNode?.label ?? '',
        node.label,
        'data-quality-summary'
      );
      const value = ROUTES.SCHEMA_LEVEL_VALUE(
        checkType,
        connectionNode?.label ?? '',
        node.label
      );
      if (firstLevelActiveTab === value) {
        return;
      }

      dispatch(
        addFirstLevelTab(checkType, {
          url,
          value: ROUTES.SCHEMA_LEVEL_VALUE(
            checkType,
            connectionNode?.label ?? '',
            node.label
          ),
          state: {},
          label: node.label
        })
      );
      history.push(
        ROUTES.SCHEMA_LEVEL_PAGE(
          checkType,
          connectionNode?.label ?? '',
          node.label,
          'data-quality-summary'
        )
      );
    } else if (node.level === TREE_LEVEL.TABLE) {
      const schemaNode = findTreeNode(treeData, node?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

      const tab = subTabMap[node.id] || getFirstLevelTableTab(checkType);

      const url = ROUTES.TABLE_LEVEL_PAGE(
        checkType,
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        node.label,
        tab
      );

      const value = ROUTES.TABLE_LEVEL_VALUE(
        checkType,
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        node.label
      );

      if (firstLevelActiveTab === value) {
        return;
      }

      dispatch(
        addFirstLevelTab(checkType, {
          url,
          value,
          state: {},
          label: node.label
        })
      );
      history.push(
        ROUTES.TABLE_LEVEL_PAGE(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          node.label,
          tab
        )
      );
    } else if (
      [
        TREE_LEVEL.TABLE_CHECKS,
        TREE_LEVEL.TABLE_DAILY_CHECKS,
        TREE_LEVEL.TABLE_MONTHLY_CHECKS,
        TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS,
        TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS
      ].includes(node.level)
    ) {
      const tableNode = findTreeNode(treeData, node.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');

      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

      let url = '';
      let value = '';
      if (node.level === TREE_LEVEL.TABLE_CHECKS) {
        url = ROUTES.TABLE_PROFILING(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? ''
        );
        value = ROUTES.TABLE_PROFILING_VALUE(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? ''
        );
      } else if (node.level === TREE_LEVEL.TABLE_DAILY_CHECKS) {
        url = ROUTES.TABLE_MONITORING(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          'daily'
        );
        value = ROUTES.TABLE_MONITORING_VALUE(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          'daily'
        );
      } else if (node.level === TREE_LEVEL.TABLE_MONTHLY_CHECKS) {
        url = ROUTES.TABLE_MONITORING(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          'monthly'
        );
        value = ROUTES.TABLE_MONITORING_VALUE(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          'monthly'
        );
      } else if (node.level === TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS) {
        url = ROUTES.TABLE_PARTITIONED(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          'daily'
        );
        value = ROUTES.TABLE_PARTITIONED_VALUE(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          'daily'
        );
      } else if (node.level === TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS) {
        url = ROUTES.TABLE_PARTITIONED(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          'monthly'
        );
        value = ROUTES.TABLE_PARTITIONED_VALUE(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          'monthly'
        );
      }
      if (firstLevelActiveTab === value) {
        return;
      }
      dispatch(
        addFirstLevelTab(checkType, {
          url: url,
          value,
          state: {},
          label: node.label
        })
      );
      history.push(url);
    } else if (
      [
        TREE_LEVEL.COLUMN_CHECKS,
        TREE_LEVEL.COLUMN_DAILY_CHECKS,
        TREE_LEVEL.COLUMN_MONTHLY_CHECKS,
        TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS,
        TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS
      ].includes(node.level)
    ) {
      const columnNode = findTreeNode(treeData, node?.parentId ?? '');
      const columnsNode = findTreeNode(treeData, columnNode?.parentId ?? '');
      const tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

      let url = '';
      let value = '';
      if (node?.level === TREE_LEVEL.COLUMN_CHECKS) {
        url = ROUTES.COLUMN_PROFILING(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          columnNode?.label ?? ''
        );
        value = ROUTES.COLUMN_PROFILING_VALUE(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          columnNode?.label ?? ''
        );
      } else if (node.level === TREE_LEVEL.COLUMN_DAILY_CHECKS) {
        url = ROUTES.COLUMN_MONITORING(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          columnNode?.label ?? '',
          'daily'
        );
        value = ROUTES.COLUMN_MONITORING_VALUE(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          columnNode?.label ?? '',
          'daily'
        );
      } else if (node.level === TREE_LEVEL.COLUMN_MONTHLY_CHECKS) {
        url = ROUTES.COLUMN_MONITORING(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          columnNode?.label ?? '',
          'monthly'
        );
        value = ROUTES.COLUMN_MONITORING_VALUE(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          columnNode?.label ?? '',
          'monthly'
        );
      } else if (node.level === TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS) {
        url = ROUTES.COLUMN_PARTITIONED(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          columnNode?.label ?? '',
          'daily'
        );
        value = ROUTES.COLUMN_PARTITIONED_VALUE(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          columnNode?.label ?? '',
          'daily'
        );
      } else if (node.level === TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS) {
        url = ROUTES.COLUMN_PARTITIONED(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          columnNode?.label ?? '',
          'monthly'
        );
        value = ROUTES.COLUMN_PARTITIONED_VALUE(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? '',
          columnNode?.label ?? '',
          'monthly'
        );
      }

      if (value === firstLevelActiveTab) {
        return;
      }

      dispatch(
        addFirstLevelTab(checkType, {
          url,
          value,
          state: {},
          label: node.label
        })
      );
      history.push(url);
    } else if (node.level === TREE_LEVEL.CHECK) {
      const parentNode = findTreeNode(treeData, node.parentId ?? '');
      if (!parentNode) {
        return;
      }
      if (
        [
          TREE_LEVEL.TABLE_CHECKS,
          TREE_LEVEL.TABLE_DAILY_CHECKS,
          TREE_LEVEL.TABLE_MONTHLY_CHECKS,
          TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS,
          TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS
        ].includes(parentNode.level)
      ) {
        const tableNode = findTreeNode(treeData, parentNode?.parentId ?? '');
        const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
        const connectionNode = findTreeNode(
          treeData,
          schemaNode?.parentId ?? ''
        );

        let url = '';
        if (parentNode?.level === TREE_LEVEL.TABLE_CHECKS) {
          url = ROUTES.TABLE_PROFILING_UI_FILTER(
            checkType,
            connectionNode?.label ?? '',
            schemaNode?.label ?? '',
            tableNode?.label ?? '',
            node.category ?? '',
            node.label
          );
        } else if (parentNode.level === TREE_LEVEL.TABLE_DAILY_CHECKS) {
          url = ROUTES.TABLE_MONITORING_UI_FILTER(
            checkType,
            connectionNode?.label ?? '',
            schemaNode?.label ?? '',
            tableNode?.label ?? '',
            'daily',
            node.category ?? '',
            node.label
          );
        } else if (parentNode.level === TREE_LEVEL.TABLE_MONTHLY_CHECKS) {
          url = ROUTES.TABLE_MONITORING_UI_FILTER(
            checkType,
            connectionNode?.label ?? '',
            schemaNode?.label ?? '',
            tableNode?.label ?? '',
            'monthly',
            node.category ?? '',
            node.label
          );
        } else if (
          parentNode.level === TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS
        ) {
          url = ROUTES.TABLE_PARTITIONED_UI_FILTER(
            checkType,
            connectionNode?.label ?? '',
            schemaNode?.label ?? '',
            tableNode?.label ?? '',
            'daily',
            node.category ?? '',
            node.label
          );
        } else if (
          parentNode.level === TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS
        ) {
          url = ROUTES.TABLE_PARTITIONED_UI_FILTER(
            checkType,
            connectionNode?.label ?? '',
            schemaNode?.label ?? '',
            tableNode?.label ?? '',
            'monthly',
            node.category ?? '',
            node.label
          );
        }

        if (firstLevelActiveTab === url) {
          return;
        }
        dispatch(
          addFirstLevelTab(checkType, {
            url,
            value: url,
            state: {},
            label: node.label
          })
        );
        history.push(url);
      } else if (
        [
          TREE_LEVEL.COLUMN_CHECKS,
          TREE_LEVEL.COLUMN_DAILY_CHECKS,
          TREE_LEVEL.COLUMN_MONTHLY_CHECKS,
          TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS,
          TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS
        ].includes(parentNode.level)
      ) {
        const columnNode = findTreeNode(treeData, parentNode?.parentId ?? '');
        const columnsNode = findTreeNode(treeData, columnNode?.parentId ?? '');
        const tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
        const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
        const connectionNode = findTreeNode(
          treeData,
          schemaNode?.parentId ?? ''
        );

        let url = '';
        if (parentNode?.level === TREE_LEVEL.COLUMN_CHECKS) {
          url = ROUTES.COLUMN_PROFILING_UI_FILTER(
            checkType,
            connectionNode?.label ?? '',
            schemaNode?.label ?? '',
            tableNode?.label ?? '',
            columnNode?.label ?? '',
            node.category ?? '',
            node.label
          );
        } else if (parentNode.level === TREE_LEVEL.COLUMN_DAILY_CHECKS) {
          url = ROUTES.COLUMN_MONITORING_UI_FILTER(
            checkType,
            connectionNode?.label ?? '',
            schemaNode?.label ?? '',
            tableNode?.label ?? '',
            columnNode?.label ?? '',
            'daily',
            node.category ?? '',
            node.label
          );
        } else if (parentNode.level === TREE_LEVEL.COLUMN_MONTHLY_CHECKS) {
          url = ROUTES.COLUMN_MONITORING_UI_FILTER(
            checkType,
            connectionNode?.label ?? '',
            schemaNode?.label ?? '',
            tableNode?.label ?? '',
            columnNode?.label ?? '',
            'monthly',
            node.category ?? '',
            node.label
          );
        } else if (
          parentNode.level === TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS
        ) {
          url = ROUTES.COLUMN_PARTITIONED_UI_FILTER(
            checkType,
            connectionNode?.label ?? '',
            schemaNode?.label ?? '',
            tableNode?.label ?? '',
            columnNode?.label ?? '',
            'daily',
            node.category ?? '',
            node.label
          );
        } else if (
          parentNode.level === TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS
        ) {
          url = ROUTES.COLUMN_PARTITIONED_UI_FILTER(
            checkType,
            connectionNode?.label ?? '',
            schemaNode?.label ?? '',
            tableNode?.label ?? '',
            columnNode?.label ?? '',
            'monthly',
            node.category ?? '',
            node.label
          );
        }
        if (firstLevelActiveTab === url) {
          return;
        }
        dispatch(
          addFirstLevelTab(checkType, {
            url: url,
            value: url,
            state: {},
            label: node.label
          })
        );
        history.push(url);
      }
    } else if (node.level === TREE_LEVEL.COLUMNS) {
      const tableNode = findTreeNode(treeData, node.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

      const url = ROUTES.TABLE_COLUMNS(
        checkType,
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? ''
      );
      const value = ROUTES.TABLE_COLUMNS_VALUE(
        checkType,
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? ''
      );
      if (firstLevelActiveTab === value) {
        return;
      }
      dispatch(
        addFirstLevelTab(checkType, {
          url,
          value,
          state: {},
          label: node.label
        })
      );
      history.push(url);
    } else if (node.level === TREE_LEVEL.TABLE_INCIDENTS) {
      const tableNode = findTreeNode(treeData, node.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

      const url = ROUTES.TABLE_INCIDENTS_NOTIFICATION(
        checkType,
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? ''
      );
      if (firstLevelActiveTab === url) {
        return;
      }
      dispatch(
        addFirstLevelTab(checkType, {
          url,
          value: url,
          state: {},
          label: node.label
        })
      );
      history.push(
        ROUTES.TABLE_INCIDENTS_NOTIFICATION(
          checkType,
          connectionNode?.label ?? '',
          schemaNode?.label ?? '',
          tableNode?.label ?? ''
        )
      );
    } else if (node.level === TREE_LEVEL.COLUMN) {
      const columnsNode = findTreeNode(treeData, node?.parentId ?? '');
      const tableNode = findTreeNode(treeData, columnsNode?.parentId ?? '');
      const schemaNode = findTreeNode(treeData, tableNode?.parentId ?? '');
      const connectionNode = findTreeNode(treeData, schemaNode?.parentId ?? '');

      const tab = subTabMap[node.id] || getFirstLevelColumnTab(checkType);

      const url = ROUTES.COLUMN_LEVEL_PAGE(
        checkType,
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        node.label,
        tab
      );
      const value = ROUTES.COLUMN_LEVEL_VALUE(
        checkType,
        connectionNode?.label ?? '',
        schemaNode?.label ?? '',
        tableNode?.label ?? '',
        node.label
      );
      if (value === firstLevelActiveTab) {
        return;
      }

      dispatch(
        addFirstLevelTab(checkType, {
          url,
          value,
          state: {},
          label: node.label
        })
      );
      history.push(url);
    } else {
      history.push('/checks');
    }
  };

  // useEffect(() => {
  //   const _activeTab = activeTabMaps[checkType];
  //   if (_activeTab) {
  //     const node = findTreeNode(treeData, _activeTab);
  //     if (node) {
  //       switchTab(node, checkType as CheckTypes);
  //     }
  //   }
  // }, [checkType]);

  const deleteData = (identify: string) => {
    const newTabMaps = { ...tabMaps };

    for (const key of Object.keys(tabMaps)) {
      const valueTabs = tabMaps[key];
      newTabMaps[key] = valueTabs.filter(
        (item) => item.value.toString().indexOf(identify) === -1
      );
    }

    if (!newTabMaps[checkTypes].length) {
      const arr = tabs
        .filter((item) => item.type === 'editor')
        .map((item) => parseInt(item.value, 10));
      const maxEditor = Math.max(...arr, 0);

      const newTab = {
        label: `New Tab`,
        value: `${maxEditor + 1}`,
        type: 'editor'
      };

      newTabMaps[checkTypes] = [newTab];
    }

    const newTreeDataMaps = [
      CheckTypes.MONITORING,
      CheckTypes.SOURCES,
      CheckTypes.PROFILING,
      CheckTypes.PARTITIONED
    ].reduce(
      (acc, cur) => ({
        ...acc,
        [cur]: (treeDataMaps[cur] || []).filter((item) => item.id !== identify)
      }),
      {}
    );

    setTreeDataMaps(newTreeDataMaps);

    setActiveTabMaps((prev) => ({
      ...prev,
      [checkTypes]: newTabMaps[checkTypes][0].value
    }));

    setTabMaps(newTabMaps);
  };

  const reimportTableMetadata = async (
    node: CustomTreeNode,
    closeMenuCallBack?: () => void
  ) => {
    const { connection, schema, table } = parseNodeId(node.id);
    await JobApiClient.importTables(undefined, false, undefined, {
      connectionName: connection,
      schemaName: schema,
      tableNames: [table]
    });
    dispatch(toggleMenu(true));
    if (closeMenuCallBack) {
      closeMenuCallBack();
    }
  };

  axios.interceptors.response.use(undefined, function (error) {
    const statusCode = error.response ? error.response.status : null;
    if (statusCode === 401 || statusCode === 403) {
      return; // handled elsewhere
    }

    if (statusCode === 404) {
      setObjectNotFound(true);
    }
    return Promise.reject(error);
  });

  const loadMoreTables = async (
    data: any[],
    parentData: any,
    search: string
  ): Promise<CustomTreeNode[]> => {
    setTablesLoading(data[0].parentId);
    const tables = [...data];
    const connection = data[0].parentId.split('.')[0];
    const schema = data[0].parentId.split('.')[1];
    const parentNode = parentData[connection].find(
      (item: any) => item.id === data[0].parentId
    );
    const addPrefix = (str: string) => {
      return str.includes('*') || str.length === 0 ? str : '*' + str + '*';
    };
    const page = Math.max(tables.length / TABLES_LIMIT_TREE_PAGING) + 1;
    const res: AxiosResponse<TableListModel[]> = await TableApiClient.getTables(
      connection,
      schema,
      undefined,
      page,
      TABLES_LIMIT_TREE_PAGING,
      search && addPrefix(search)
    );
    const items = res.data.map((table) => ({
      id: `${parentNode.id}.${urlencodeDecoder(
        table.target?.table_name ?? ''
      )}`,
      label: table.target?.table_name || '',
      level: TREE_LEVEL.TABLE,
      parentId: parentNode.id,
      items: [],
      tooltip: `${parentNode.id?.toString() || ''}.${data[0].label}.${
        table.target?.table_name
      }`,
      hasCheck:
        !!table?.[
          checkTypesToHasConfiguredCheckKey[
            checkTypes as keyof typeof checkTypesToHasConfiguredCheckKey
          ] as keyof TableListModel
        ],
      run_checks_job_template: table[
        checkTypesToJobTemplateKey[
          checkTypes as keyof typeof checkTypesToJobTemplateKey
        ] as keyof TableListModel
      ] as CheckSearchFilters,
      collect_statistics_job_template: table.collect_statistics_job_template,
      data_clean_job_template: table.data_clean_job_template,
      open: false,
      configured: table.partitioning_configuration_missing,
      parsingYamlError: table.yaml_parsing_error
    }));
    if (items.length === 0) {
      const oldLoadedTables = { ...loadedTables };
      oldLoadedTables[parentNode.id] = true;
      setLoadedTables(oldLoadedTables);
    }
    const newItems = [...tables, ...items];
    resetTreeData(parentNode, newItems, true);
    setTablesLoading('');
    return items;
  };

  const searchTable = async (data: any[], parentData: any, search: string) => {
    const addPrefix = (str: string) => {
      return str.includes('*') || str.length === 0 ? str : '*' + str + '*';
    };
    const connection = data[0].parentId.split('.')[0];
    const schema = data[0].parentId.split('.')[1];
    const parentNode = parentData[connection].find(
      (item: any) => item.id === data[0].parentId
    );

    const res: AxiosResponse<TableListModel[]> = await TableApiClient.getTables(
      connection,
      schema,
      undefined,
      1,
      TABLES_LIMIT_TREE_PAGING,
      addPrefix(search)
    );
    const items = res.data.map((table) => ({
      id: `${parentNode.id}.${urlencodeDecoder(
        table.target?.table_name ?? ''
      )}`,
      label: table.target?.table_name || '',
      level: TREE_LEVEL.TABLE,
      parentId: parentNode.id,
      items: [],
      tooltip: `${parentNode.id?.toString() || ''}.${data[0].label}.${
        table.target?.table_name
      }`,
      hasCheck:
        !!table?.[
          checkTypesToHasConfiguredCheckKey[
            checkTypes as keyof typeof checkTypesToHasConfiguredCheckKey
          ] as keyof TableListModel
        ],
      run_checks_job_template: table[
        checkTypesToJobTemplateKey[
          checkTypes as keyof typeof checkTypesToJobTemplateKey
        ] as keyof TableListModel
      ] as CheckSearchFilters,
      collect_statistics_job_template: table.collect_statistics_job_template,
      data_clean_job_template: table.data_clean_job_template,
      open: false,
      configured: table.partitioning_configuration_missing,
      parsingYamlError: table.yaml_parsing_error
    }));
    if (items.length === 0) {
      const oldLoadedTables = { ...loadedTables };
      oldLoadedTables[parentNode.id] = true;
      setLoadedTables(oldLoadedTables);
    } else {
      const oldLoadedTables = { ...loadedTables };
      oldLoadedTables[parentNode.id] = false;
      setLoadedTables(oldLoadedTables);
    }
    resetTreeData(parentNode, items, true);
    setTablesLoading('');
  };

  const searchColumn = async (data: any[], parentData: any, search: string) => {
    const parentId = data[0].parentId;
    const tableParentId = String(parentId).replace('.columns', '');
    const parentNode = parentData[tableParentId].find(
      (item: any) => item.id === parentId
    );
    const table = data[0].parentId.split('.')[2];
    const connection = data[0].parentId.split('.')[0];
    const schema = data[0].parentId.split('.')[1];

    const res: AxiosResponse<ColumnListModel[]> =
      await ColumnApiClient.getColumns(connection, schema, table);
    const items = res.data.map((column) => ({
      id: `${parentNode.id}.${urlencodeDecoder(column.column_name ?? '')}`,
      label: column.column_name || '',
      level: TREE_LEVEL.COLUMN,
      parentId: parentNode.id,
      items: [],
      tooltip: `${connection}.${schema}.${table}.${column.column_name}`,
      hasCheck:
        !!column?.[
          checkTypesToHasConfiguredCheckKey[
            checkTypes as keyof typeof checkTypesToHasConfiguredCheckKey
          ] as keyof ColumnListModel
        ],
      run_checks_job_template: column[
        checkTypesToJobTemplateKey[
          checkTypes as keyof typeof checkTypesToJobTemplateKey
        ] as keyof ColumnListModel
      ] as CheckSearchFilters,
      collect_statistics_job_template: column.collect_statistics_job_template,
      data_clean_job_template: column.data_clean_job_template,
      open: false,
      configured:
        column.has_any_configured_checks ||
        column.has_any_configured_partition_checks ||
        column.has_any_configured_profiling_checks ||
        column.has_any_configured_monitoring_checks
    }));

    const newItems = items.filter((item) =>
      item.label.toLowerCase().includes(search.toLowerCase())
    );

    resetTreeData(parentNode, newItems, true);
    setColumnsLoading('');
  };

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
        tabMaps,
        setTabMap: setSubTabMap,
        changeActiveTab,
        sidebarWidth,
        setSidebarWidth,
        sidebarScrollHeight,
        setSidebarScrollHeight,
        removeTreeNode,
        removeNode,
        refreshNode,
        runChecks,
        collectStatisticsOnTable,
        addConnection,
        switchTab,
        activeNode,
        deleteStoredData,
        deleteData,
        selectedTreeNode,
        setSelectedTreeNode,
        loadingNodes,
        addSchema,
        refreshDatabaseNode,
        runPartitionedChecks,
        objectNotFound,
        setObjectNotFound,
        reimportTableMetadata,
        loadMoreTables,
        tablesLoading,
        searchTable,
        loadedTables,
        columnsLoading,
        searchColumn
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
