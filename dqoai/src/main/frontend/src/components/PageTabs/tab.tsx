import React from "react";
import clsx from 'clsx';
import SvgIcon from '../SvgIcon';

export interface TabOption {
  label: string;
  value: string;
}

export interface TabProps {
  tab: TabOption;
  active?: boolean;
  onChange?: (tab: TabOption) => void;
  onRemove: () => void;
}

const Tab = ({
  tab,
  active,
  onChange,
  onRemove,
}: TabProps) => {
  const handleRemove = (e: any) => {
    e.stopPropagation();
    onRemove();
  };

  return (
    <div
      className={clsx(
        'text-sm leading-20 text-black px-4 py-2 cursor-pointer flex text-gray-700 font-medium space-x-4 items-center',
        active ? 'font-semibold relative bg-white border-b-2 border-blue-600 text-blue-700 rounded-t-md shadow-sm' : '',
      )}
      onClick={() => onChange && onChange(tab)}
    >
      <div className="truncate max-w-40">{tab.label}</div>
      <SvgIcon name="close" onClick={handleRemove} />
    </div>
  );
};

export default Tab;
