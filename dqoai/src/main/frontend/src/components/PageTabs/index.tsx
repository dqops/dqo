import React from 'react';

import Tab, { TabOption } from './tab';

export interface IPageTabsProps {
  tabs: TabOption[];
  className?: string;
  activeTab?: string;
  onChange?: any;
  onRemoveTab: (value: string) => void;
}

const PageTabs = ({
  tabs,
  className,
  activeTab,
  onChange,
  onRemoveTab,
}: IPageTabsProps) => {
  const onChangeTab = (tab: TabOption) => {
    onChange(tab.value);
  };

  return (
    <div className={`flex space-x-4 ${className}`}>
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
