import React, { useMemo } from 'react';

import Tab, { TabOption } from './tab';

export interface IPageTabsProps {
  tabs: TabOption[];
  className?: string;
  activeTab?: string;
  onChange?: any;
  onRemoveTab: (value: string) => void;
  limit?: number;
}

const PageTabs = ({
  tabs,
  className,
  activeTab,
  onChange,
  onRemoveTab,
  limit = 7,
}: IPageTabsProps) => {
  const onChangeTab = (tab: TabOption) => {
    onChange(tab);
  };

  useMemo(() => {
    if (tabs.length > limit) {
      onRemoveTab(tabs[0].value);
    }
  }, [tabs, limit, onRemoveTab]);

  return (
    <div className={`flex space-x-4 overflow-x-auto min-h-10 max-h-10 ${className}`}>
      <div className="flex">
        {tabs.map((tab) => (
          <Tab
            key={tab.value}
            tab={tab}
            active={activeTab === tab.value}
            onChange={onChangeTab}
            onRemove={() => onRemoveTab(tab.value)}
          />
        ))}
      </div>
    </div>
  );
};

export default PageTabs;
