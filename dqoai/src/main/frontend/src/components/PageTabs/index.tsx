import React, { useMemo } from 'react';

import IconButton from '../IconButton';
import SvgIcon from '../SvgIcon';
import Tab, { TabOption } from './tab';

export interface IPageTabsProps {
  tabs: TabOption[];
  className?: string;
  activeTab?: string;
  onChange?: any;
  onRemoveTab: (value: string) => void;
  onAddTab: () => void;
  limit?: number;
}

const PageTabs = ({
  tabs,
  className,
  activeTab,
  onChange,
  onRemoveTab,
  onAddTab,
  limit = 10,
}: IPageTabsProps) => {
  const onChangeTab = (tab: TabOption) => {
    onChange(tab.value);
  };

  useMemo(() => {
    if (tabs.length > limit) {
      onRemoveTab(tabs[0].value);
    }
  }, [tabs, limit, onRemoveTab]);

  return (
    <div className={`flex space-x-4 overflow-x-auto ${className}`}>
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
      <IconButton
        className="bg-primary hover:bg-opacity-80 w-7 h-7 m-1 !rounded-md"
        onClick={onAddTab}
      >
        <SvgIcon name="add" className="w-5 text-white" />
      </IconButton>
    </div>
  );
};

export default PageTabs;
