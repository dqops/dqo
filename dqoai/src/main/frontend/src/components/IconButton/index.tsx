import React from 'react';
import clsx from 'clsx';

interface IIconButtonProps {
  children: any;
  className?: string;
}

const IconButton: React.FC<IIconButtonProps> = ({ children, className }) => {
  return (
    <button className={clsx('focus:outline-none w-10 h-10 hover:bg-red-50 rounded-full flex items-center justify-center', className)}>
      {children}
    </button>
  );
};

export default IconButton;
