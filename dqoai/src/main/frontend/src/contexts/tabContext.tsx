import React, { useState } from 'react';
import { DataNode } from 'rc-tree/es/interface';

const TabContext = React.createContext({} as any);

export interface ITab {
  label: string;
  value: string;
}

function TabProvider(props: any) {
  const [tabs, setTabs] = useState<ITab[]>([]);
  const [activeTab, setActiveTab] = useState<string>();

  const addTab = (node: DataNode) => {
    if (tabs.find((item) => item.value === node.key.toString())) {
      setActiveTab(node.key.toString());
    } else {
      setActiveTab(node.key.toString());
      setTabs([...tabs, { label: node.title?.toString() || '', value: node.key.toString() }])
    }
  }
  
  const closeTab = (value: string) => {
    setTabs(tabs.filter((item) => item.value !== value));
  };

  return (
    <TabContext.Provider
      value={{
        tabs,
        activeTab,
        setActiveTab,
        addTab,
        closeTab,
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
