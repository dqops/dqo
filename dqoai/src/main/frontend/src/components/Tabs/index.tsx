import React from "react";
import Tab, { TabOption } from "./tab";

export interface TabsProps {
  tabs: TabOption[];
  className?: string;
  activeTab?: string;
  onChange?: any;
}

const Tabs = (
  {
    tabs,
    className,
    activeTab,
    onChange,
  }: TabsProps
) => {
  const onChangeTab = (tab: TabOption) => {
    onChange(tab.value);
  };

  return (
    <div className={`flex ${className}`}>
      {
        tabs.map((tab) => (
          <Tab
            key={tab.value}
            tab={tab}
            active={activeTab === tab.value}
            onChange={onChangeTab}
          />
        ))
      }
    </div>
  );
};

export default Tabs;
