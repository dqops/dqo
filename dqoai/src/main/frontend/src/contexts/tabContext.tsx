import React, { useCallback, useEffect, useState } from 'react';

import { useSelector } from 'react-redux';

import { useActionDispatch } from '../hooks/useActionDispatch';
import { getAllConnections, setActiveConnection } from '../redux/actions/connection.actions';
import { getSchemasByConnection } from '../redux/actions/schema.actions';
import { IRootState } from '../redux/reducers';
import { TREE_LEVEL } from '../shared/enums';
import { ITab, TDataNode } from '../shared/interfaces';
import { findNode, generateTreeNodes } from '../utils/tree';

const TabContext = React.createContext({} as any);

function TabProvider(props: any) {
  const [treeData, setTreeData] = useState<TDataNode[]>([
    {
      key: 'dqo-ai',
      title: 'dqo-ai',
      level: TREE_LEVEL.DATABASE,
      children: [
        {
          key: 'dqo-ai.schema',
          title: 'Schema',
          level: TREE_LEVEL.SCHEMA,
          children: [
            {
              key: 'dqo-ai.public',
              title: 'Public',
              level: TREE_LEVEL.TABLE,
              children: [
                { key: 'dqo-ai.public.id', title: 'Id', level: TREE_LEVEL.COLUMN },
                { key: 'dqo-ai.public.title', title: 'Title', level: TREE_LEVEL.COLUMN },
              ],
            },
            {
              key: 'dqo-ai.album',
              title: 'Album',
              level: TREE_LEVEL.TABLE,
              children: [
                { key: 'dqo-ai.album.id', title: 'Id', level: TREE_LEVEL.COLUMN },
                { key: 'dqo-ai.album.title', title: 'Title', level: TREE_LEVEL.COLUMN },
                { key: 'dqo-ai.album.artistId', title: 'Artist Id', level: TREE_LEVEL.COLUMN },
              ],
            },
            {
              key: 'dqo-ai.customer',
              title: 'Customer',
              level: TREE_LEVEL.TABLE,
              children: [
                { key: 'dqo-ai.customer.id', title: 'Id', level: TREE_LEVEL.COLUMN },
                { key: 'dqo-ai.customer.title', title: 'Title', level: TREE_LEVEL.COLUMN },
                { key: 'dqo-ai.customer.firstName', title: 'FirstName', level: TREE_LEVEL.COLUMN },
                { key: 'dqo-ai.customer.lastName', title: 'LastName', level: TREE_LEVEL.COLUMN },
                { key: 'dqo-ai.customer.email', title: 'email', level: TREE_LEVEL.COLUMN },
              ],
            },
            {
              key: 'dqo-ai.employee',
              title: 'Employee',
              level: TREE_LEVEL.TABLE,
              children: [
                { key: 'dqo-ai.employee.id', title: 'Id', level: TREE_LEVEL.COLUMN },
                { key: 'dqo-ai.employee.title', title: 'Title', level: TREE_LEVEL.COLUMN },
                { key: 'dqo-ai.employee.firstName', title: 'FirstName', level: TREE_LEVEL.COLUMN },
                { key: 'dqo-ai.employee.lastName', title: 'LastName', level: TREE_LEVEL.COLUMN },
                { key: 'dqo-ai.employee.email', title: 'email', level: TREE_LEVEL.COLUMN },
              ],
            },
            {
              key: 'dqo-ai.invoice',
              title: 'Invoice',
              level: TREE_LEVEL.TABLE,
              children: [
                { key: 'dqo-ai.invoice.id', title: 'Id', level: TREE_LEVEL.COLUMN },
                { key: 'dqo-ai.invoice.title', title: 'Title', level: TREE_LEVEL.COLUMN },
                { key: 'dqo-ai.invoice.amount', title: 'Amount', level: TREE_LEVEL.COLUMN },
              ],
            },
          ],
        },
      ],
    },
    {
      key: 'documati',
      title: 'documati',
      level: TREE_LEVEL.DATABASE,
      children: [
        {
          key: 'documati.schema',
          title: 'Schema',
          level: TREE_LEVEL.SCHEMA,
          children: [
            {
              key: 'documati.public',
              title: 'Public',
              level: TREE_LEVEL.TABLE,
            },
          ],
        },
      ],
    },
  ]);
  const [tabs, setTabs] = useState<ITab[]>([]);
  const [activeTab, setActiveTab] = useState<string>();
  const dispatch = useActionDispatch();
  const { connections, activeConnection } = useSelector((state: IRootState) => state.connection);
  const { schemas } = useSelector((state: IRootState) => state.schema);

  const changeActiveTab = (node: TDataNode) => {
    const existTab = tabs.find((item) => item.value === node.key.toString());
    if (node.level === TREE_LEVEL.DATABASE) {
      dispatch(setActiveConnection(node.key.toString()));
      dispatch(getSchemasByConnection(node.key.toString()));
    }
    if (existTab) {
      setActiveTab(node.key.toString());
      return;
    }

    const newTab = {
      label: node.title?.toString() || '',
      value: node.key.toString(),
    };

    if (activeTab) {
      const newTabs = tabs.map((item) => (item.value === activeTab ? newTab : item));
      setTabs(newTabs);
    } else {
      setTabs([newTab]);
    }
    setActiveTab(node.key.toString());
  };

  const closeTab = (value: string) => {
    const newTabs = tabs.filter((item) => item.value !== value);
    setTabs(newTabs);
    if (value === activeTab) {
      setActiveTab(newTabs[newTabs.length - 1]?.value);
    }
  };

  const getTabLabel = useCallback((value: string) => findNode(treeData, value)?.title, [treeData]);

  const onAddTab = () => {
    const arr = tabs
      .filter((item) => item.type === 'editor')
      .map((item) => parseInt(item.value, 10));
    const maxEditor = Math.max(...arr, 0);

    const newTab = {
      label: `New Tab`,
      value: `${maxEditor + 1}`,
      type: 'editor',
    };

    setTabs([...tabs, newTab]);
    setActiveTab(newTab.value);
  };

  const expandTab = (keys: string[], info: any) => {
    console.log('keys', keys, info);
  };

  useEffect(() => {
    dispatch(getAllConnections());
  }, []);

  useEffect(() => {
    setTreeData(generateTreeNodes(connections, ['name'], TREE_LEVEL.DATABASE));
  }, [connections]);

  useEffect(() => {
    if (activeConnection) {
      setTreeData(
        treeData.map((item) =>
          item.key.toString() === activeConnection
            ? {
                ...item,
                children: generateTreeNodes(
                  schemas,
                  ['connectionName', 'schemaName'],
                  TREE_LEVEL.SCHEMA,
                ),
              }
            : item,
        ),
      );
    }
  }, [activeConnection, schemas]);

  return (
    <TabContext.Provider
      value={{
        changeActiveTab,
        treeData,
        getTabLabel,
        tabs,
        activeTab,
        setActiveTab,
        closeTab,
        onAddTab,
        expandTab,
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
