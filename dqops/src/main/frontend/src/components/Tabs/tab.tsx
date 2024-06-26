import React from 'react';
import SvgIcon from '../SvgIcon';

export interface TabOption {
  label: string;
  value: string;
  isUpdated?: boolean;
  isDisabled?: boolean;
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
      className={`relative text-sm leading-20 py-2 cursor-pointer px-8 rounded-t-md ${
        active ? 'font-semibold relative border-b-2 border-primary' : ''
      }
        ${tab.isDisabled ? 'border-gray-150 bg-gray-100' : 'text-gray-800'}
     `}
      style={active ? { backgroundColor: '#E4F7F4' } : {}}
      onClick={() => !tab.isDisabled && onChange && onChange(tab)}
    >
      <div className="text-center truncate">
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
