import React from 'react';

import SvgIcon from '../SvgIcon';

export interface TabOption {
  label: string;
  value: string;
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
      className={`relative text-sm leading-20 text-black px-8 py-2 cursor-pointer ${
        active ? 'font-semibold relative bg-white border-b-2 border-blue-600' : ''
      }`}
      onClick={() => onChange && onChange(tab)}
    >
      <div className='truncate max-w-40'>{tab.label}</div>
      {closable && active && (
        <SvgIcon
          name='close'
          onClick={handleClose}
          className='absolute right-1.5 top-1/2 -translate-y-1/2 transform'
        />
      )}
    </div>
  );
};

export default Tab;
