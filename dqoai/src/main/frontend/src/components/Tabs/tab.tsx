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
      className={`z-10 first:border-l border-gray-300 text-sm leading-20 text-black px-8 py-1.5 cursor-pointer border-t border-r ${active ? 'font-semibold relative bg-white border-b-3 border-black-600' : ''}`}
      onClick={() => onChange && onChange(tab)}
    >
      {tab.label}
      {
        active && <div className="absolute top-full left-0 -translate-y-1/2 h-0.5 bg-white w-full" />
      }
    </div>
  );
};

export default Tab;
