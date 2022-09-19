import React from 'react';

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
}

const PageTabs = ({
  tabs,
  className,
  activeTab,
  onChange,
  onRemoveTab,
  onAddTab,
}: IPageTabsProps) => {
  const onChangeTab = (tab: TabOption) => {
    onChange(tab.value);
  };

  return (
    <div className={`flex space-x-4 ${className}`}>
      <div className='flex'>
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
      <IconButton className='bg-blue-100 hover:bg-opacity-80 w-8 h-8 m-1' onClick={onAddTab}>
        <SvgIcon name='add' className='w-5 text-blue-500' />
      </IconButton>
    </div>
  );
};

export default PageTabs;
