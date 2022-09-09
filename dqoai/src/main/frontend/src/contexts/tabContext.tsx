import React, { useState } from 'react';
import { ITab, TDataNode } from '../shared/interfaces';
import { TREE_LEVEL } from '../shared/enums';

const TabContext = React.createContext({} as any);

function TabProvider(props: any) {
  const [activeDatabaseTab, setActiveDatabaseTab] = useState<string>();
  const [activeTableTab, setActiveTableTab] = useState<string>();
  const [databaseTabs, setDatabaseTabs] = useState<ITab[]>([]);
  const [tableTabs, setTableTabs] = useState<ITab[]>([]);
  const [treeData] = useState<TDataNode[]>([
    {
      key: "dqo-ai",
      title: "dqo-ai",
      level: TREE_LEVEL.DATABASE,
      children: [
        {
          key: "dqo-ai.public",
          title: "Public",
          level: TREE_LEVEL.SCHEMA,
          children: [
            { key: "dqo-ai.public.id", title: "Id", level: TREE_LEVEL.COLUMN },
            { key: "dqo-ai.public.title", title: "Title", level: TREE_LEVEL.COLUMN },
          ]
        },
        {
          key: "dqo-ai.album",
          title: "Album",
          level: TREE_LEVEL.SCHEMA,
          children: [
            { key: "dqo-ai.album.id", title: "Id", level: TREE_LEVEL.COLUMN },
            { key: "dqo-ai.album.title", title: "Title", level: TREE_LEVEL.COLUMN },
            { key: "dqo-ai.album.artistId", title: "Artist Id", level: TREE_LEVEL.COLUMN },
          ]
        },
        {
          key: "dqo-ai.customer",
          title: "Customer",
          level: TREE_LEVEL.SCHEMA,
          children: [
            { key: "dqo-ai.customer.id", title: "Id", level: TREE_LEVEL.COLUMN },
            { key: "dqo-ai.customer.title", title: "Title", level: TREE_LEVEL.COLUMN },
            { key: "dqo-ai.customer.firstName", title: "FirstName", level: TREE_LEVEL.COLUMN },
            { key: "dqo-ai.customer.lastName", title: "LastName", level: TREE_LEVEL.COLUMN },
            { key: "dqo-ai.customer.email", title: "email", level: TREE_LEVEL.COLUMN },
          ]
        },
        {
          key: "dqo-ai.employee",
          title: "Employee",
          level: TREE_LEVEL.SCHEMA,
          children: [
            { key: "dqo-ai.employee.id", title: "Id", level: TREE_LEVEL.COLUMN },
            { key: "dqo-ai.employee.title", title: "Title", level: TREE_LEVEL.COLUMN },
            { key: "dqo-ai.employee.firstName", title: "FirstName", level: TREE_LEVEL.COLUMN },
            { key: "dqo-ai.employee.lastName", title: "LastName", level: TREE_LEVEL.COLUMN },
            { key: "dqo-ai.employee.email", title: "email", level: TREE_LEVEL.COLUMN },
          ]
        },
        {
          key: "dqo-ai.invoice",
          title: "Invoice",
          level: TREE_LEVEL.SCHEMA,
          children: [
            { key: "dqo-ai.invoice.id", title: "Id", level: TREE_LEVEL.COLUMN },
            { key: "dqo-ai.invoice.title", title: "Title", level: TREE_LEVEL.COLUMN },
            { key: "dqo-ai.invoice.amount", title: "Amount", level: TREE_LEVEL.COLUMN },
          ]
        },
      ]
    },
    {
      key: "documati",
      title: "documati",
      level: TREE_LEVEL.DATABASE,
      children: [
        {
          key: "documati.public",
          title: 'Public',
          level: TREE_LEVEL.SCHEMA
        }
      ]
    },
  ]);
  
  const addDatabaseTab = (node: TDataNode) => {
    if (databaseTabs.find((item) => item.value === node.key.toString())) {
      setActiveDatabaseTab(node.key.toString());
    } else {
      setActiveDatabaseTab(node.key.toString());
      setDatabaseTabs([...databaseTabs, { label: node.title?.toString() || '', value: node.key.toString() }])
    }
  };
  
  const closeDatabaseTab = (value: string) => {
    setDatabaseTabs(databaseTabs.filter((item) => item.value !== value));
    if (value === activeDatabaseTab) {
      setTableTabs([]);
      setActiveDatabaseTab('');
    }
  };
  
  const addTableTab = (node: TDataNode) => {
    const databaseTab = node.key.toString().split('.')[0];
    const databaseNode = treeData.find((item) => item.key === databaseTab);
    if (databaseNode) {
      addDatabaseTab(databaseNode);
    }
    setActiveTableTab(node.key.toString());
    if (activeDatabaseTab === databaseTab) {
      const exist = tableTabs.find((item) => item.value === node.key.toString());
      if (!exist) {
        setTableTabs([...tableTabs, { label: node.title?.toString() || '', value: node.key.toString() }])
      }
    } else {
      setTableTabs([{ label: node.title?.toString() || '', value: node.key.toString() }])
    }
  };
  
  const closeTableTab = (value: string) => {
    setDatabaseTabs(databaseTabs.filter((item) => item.value !== value));
  };
  
  const addTab = (node: TDataNode) => {
    if (node.level === TREE_LEVEL.DATABASE) {
      addDatabaseTab(node);
    }
    if (node.level === TREE_LEVEL.SCHEMA) {
      addTableTab(node);
    }
  }
  
  return (
    <TabContext.Provider
      value={{
        databaseTabs,
        activeDatabaseTab,
        setActiveDatabaseTab,
        closeDatabaseTab,
        tableTabs,
        activeTableTab,
        setActiveTableTab,
        closeTableTab,
        addTab,
        treeData,
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
