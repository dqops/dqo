import React from "react";

export interface TabOption {
  label: string;
  value: string;
}

export interface TabProps {
  tab: TabOption;
  active?: boolean;
  onChange?: (tab: TabOption) => void;
}

const Tab = ({
  tab,
  active,
  onChange
}: TabProps) => {
  return (
    <div
      className={`text-sm leading-20 text-black px-8 py-2 cursor-pointer ${active ? 'font-semibold relative bg-white border-b-2 border-blue-600' : ''}`}
      onClick={() => onChange && onChange(tab)}
    >
      {tab.label}
    </div>
  );
};

export default Tab;
