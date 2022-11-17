import React from 'react';

import clsx from 'clsx';

interface IIconButtonProps {
  children: any;
  className?: string;
  onClick?: any;
}

const IconButton = ({ children, className, onClick }: IIconButtonProps) => {
  return (
    <button
      className={clsx(
        'focus:outline-none w-10 h-10 rounded-full flex items-center justify-center',
        className
      )}
      onClick={onClick}
    >
      {children}
    </button>
  );
};

export default IconButton;
