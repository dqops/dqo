import React, { useEffect } from 'react';

import clsx from 'clsx';

import { Tooltip } from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';

export interface TabOption {
  label: string;
  value: string;

  url?: string;
  tooltip?: string;
}

export interface TabProps {
  tab: TabOption;
  active?: boolean;
  onChange?: (tab: TabOption) => void;
  onRemove: () => void;
}

const Tab = ({ tab, active, onChange, onRemove }: TabProps) => {
  const handleRemove = (e: any) => {
    e.stopPropagation();
    onRemove();
  };

  useEffect(() => {
    if (tab.label === '') {
      onRemove();
    }
  }, [tab]);

  const content = (
    <div
      className={clsx(
        'text-sm leading-20 px-4 py-2 cursor-pointer flex font-medium space-x-4 items-center',
        active
          ? 'font-semibold relative bg-white border-b-2 border-primary text-gray-700 rounded-t-md shadow-sm border-t border-l border-r'
          : ''
      )}
      onClick={() => onChange && onChange(tab)}
    >
      <div className="truncate max-w-40">{tab.label}</div>
      <SvgIcon name="close" onClick={handleRemove} />
    </div>
  );

  if (tab?.tooltip) {
    return (
      <Tooltip
        content={tab?.tooltip}
        className="max-w-80 py-2 px-2 bg-gray-800 delay-300"
        placement="top-start"
      >
        {content}
      </Tooltip>
    );
  }

  return content;
};

export default Tab;
