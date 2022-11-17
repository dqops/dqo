import React from 'react';

import Tab, { TabOption } from './tab';
import { useTree } from '../../contexts/treeContext';

export interface TabsProps {
  tabs: TabOption[];
  className?: string;
  activeTab?: string;
  onChange?: any;
  onCloseTab?: (value: string) => void;
}

const Tabs = ({
  tabs,
  className,
  activeTab,
  onChange,
  onCloseTab
}: TabsProps) => {
  const { sidebarWidth } = useTree();
  const onChangeTab = (tab: TabOption) => {
    onChange(tab.value);
  };

  return (
    <div
      className={`flex overflow-auto pb-2 ${className}`}
      style={{ maxWidth: `calc(100vw - ${sidebarWidth + 40}px` }}
    >
      {tabs.map((tab) => (
        <Tab
          key={tab.value}
          tab={tab}
          active={activeTab === tab.value}
          onChange={onChangeTab}
          closable={!!onCloseTab}
          onClose={() => onCloseTab && onCloseTab(tab.value)}
        />
      ))}
    </div>
  );
};

export default Tabs;
