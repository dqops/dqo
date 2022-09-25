import React, { useCallback, useEffect, useState } from 'react';

import { useSelector } from 'react-redux';

import { IRootState } from '../redux/reducers';
import { TREE_LEVEL } from '../shared/enums';
import { ITab, ITreeNode } from '../shared/interfaces';
import { findNode } from '../utils/tree';
import { AxiosResponse } from 'axios';
import { ConnectionBasicModel, SchemaModel, TableBasicModel, ColumnBasicModel } from '../api';
import {
  ColumnApiClient,
  ConnectionApiClient,
  SchemaApiClient,
  TableApiClient
} from '../services/apiClient';

const TabContext = React.createContext({} as any);

function TabProvider(props: any) {
  const [treeData, setTreeData] = useState<ITreeNode>({
    key: 'root',
    module: '',
    level: TREE_LEVEL.ROOT,
    collapsed: false,
    parent: ''
  });
  const [tabs, setTabs] = useState<ITab[]>([]);
  const [activeTab, setActiveTab] = useState<string>();
  const { activeConnection } = useSelector(
    (state: IRootState) => state.connection
  );
  const { schemas } = useSelector((state: IRootState) => state.schema);

  const calculateTree = async (node: ITreeNode) => {
    const newTreeData = Object.assign({}, treeData);

    const treeNode = findNode(newTreeData, node.key);
    if (!treeNode) return;

    if (!treeNode?.children) {
      if (node.level === TREE_LEVEL.DATABASE) {
        const res : AxiosResponse<SchemaModel[], any> = await SchemaApiClient.getSchemas(node.module);
        treeNode.children = res.data.map(schema => ({
          module: schema.schema_name || '',
          key: `${node.key}.${schema.schema_name}`,
          level: TREE_LEVEL.SCHEMA,
          parent: node.key,
          collapsed: true
        }));
      } else if (node.level === TREE_LEVEL.SCHEMA) {
        const connectionName = node.key.split('.')[1] || '';
        const res : AxiosResponse<TableBasicModel[], any> = await TableApiClient.getTables(connectionName, node.module);

        treeNode.children = res.data.map(table => ({
          module: table.target?.table_name || '',
          key: `${node.key}.${table.target?.table_name}`,
          level: TREE_LEVEL.TABLE,
          parent: node.key,
          collapsed: true
        }));
      } else if (node.level === TREE_LEVEL.TABLE) {
        const connectionName = node.key.split('.')[1] || '';
        const schemaName = node.key.split('.')[2] || '';
        const res : AxiosResponse<ColumnBasicModel[], any> = await ColumnApiClient.getColumns(
          connectionName,
          schemaName,
          node.module
        );

        treeNode.children = res.data.map(column => ({
          module: column.column_name || '',
          key: `${node.key}.${column.column_name}`,
          level: TREE_LEVEL.COLUMN,
          parent: node.key,
          collapsed: true
        }));
      }
    }
    treeNode.collapsed = !treeNode.collapsed;
    setTreeData(newTreeData);
  };

  const changeActiveTab = async (node: ITreeNode) => {
    const existTab = tabs.find((item) => item.value === node.key.toString());
    if (existTab) {
      setActiveTab(node.key.toString());
    } else {
      const newTab = {
        label: node.module?.toString() || '',
        value: node.key.toString()
      };

      if (activeTab) {
        const newTabs = tabs.map((item) =>
          item.value === activeTab ? newTab : item
        );
        setTabs(newTabs);
      } else {
        setTabs([newTab]);
      }
      setActiveTab(node.key.toString());
    }
    await calculateTree(node);
  };

  const closeTab = (value: string) => {
    const newTabs = tabs.filter((item) => item.value !== value);
    setTabs(newTabs);
    if (value === activeTab) {
      setActiveTab(newTabs[newTabs.length - 1]?.value);
    }
  };

  const getTabLabel = useCallback(
    (value: string) => findNode(treeData, value)?.module,
    [treeData]
  );

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

  const expandTab = (keys: string[], info: any) => {
    console.log('keys', keys, info);
  };

  const getConnections = async () => {
    const res : AxiosResponse<ConnectionBasicModel[], any> = await ConnectionApiClient.getAllConnections();

    setTreeData({
      ...treeData,
      children: res.data.map(connection => ({
        module: connection.connection_name || '',
        key: `root.${connection.connection_name}`,
        level: TREE_LEVEL.DATABASE,
        parent: 'root',
        collapsed: true
      }))
    });
  };
  useEffect(() => {
    (async () => {
      await getConnections();
    })();
  }, []);

  useEffect(() => {
    // if (activeConnection) {
    //   setTreeData(
    //     treeData.map((item) =>
    //       item.key.toString() === activeConnection
    //         ? {
    //             ...item,
    //             children: generateTreeNodes(
    //               schemas,
    //               ['connectionName', 'schemaName'],
    //               TREE_LEVEL.SCHEMA
    //             )
    //           }
    //         : item
    //     )
    //   );
    // }
  }, [activeConnection, schemas]);

  return (
    <TabContext.Provider
      value={{
        changeActiveTab,
        treeData,
        setTreeData,
        getTabLabel,
        tabs,
        activeTab,
        setActiveTab,
        closeTab,
        onAddTab,
        expandTab
      }}
      {...props}
    />
  );
}

function useTabs() {
  const context = React.useContext(TabContext);

  if (context === undefined) {
    throw new Error('useTabs must be used within a TabProvider');
  }
  return context;
}

export { TabProvider, useTabs };
