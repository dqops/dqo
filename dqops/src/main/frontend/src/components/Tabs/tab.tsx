import React from 'react';

import SvgIcon from '../SvgIcon';

export interface TabOption {
  label: string;
  value: string;
  isUpdated?: boolean;
}

export interface TabProps {
  tab: TabOption;
  active?: boolean;
  onChange?: (tab: TabOption) => void;
  closable?: boolean;
  onClose?: () => void;
}

const Tab = ({ tab, active, onChange, closable, onClose }: TabProps) => {
  const handleClose = (e: any) => {
    e.stopPropagation();
    if (onClose) {
      onClose();
    }
  };

  return (
    <div
      className={`relative text-sm leading-20 py-2 cursor-pointer ${
        active
          ? 'font-semibold relative bg-white border-b-2 border-primary'
          : ''
      }`}
      onClick={() => onChange && onChange(tab)}
    >
      <div className="max-w-45 w-45 text-center truncate">
        {tab.label}
        {tab.isUpdated ? '*' : ''}
      </div>
      {closable && active && (
        <SvgIcon
          name="close"
          onClick={handleClose}
          className="absolute right-1.5 top-1/2 -translate-y-1/2 transform"
        />
      )}
    </div>
  );
};

export default Tab;
