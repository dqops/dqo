import React from 'react';

import clsx from 'clsx';
import SvgIcon from '../../SvgIcon';

interface ISectionWrapperProps {
  title: string;
  children?: any;
  className?: string;
  svgIcon?: boolean;
  onClick?: () => void;
}

const SectionWrapper = ({
  title,
  children,
  className,
  svgIcon,
  onClick
}: ISectionWrapperProps) => {
  return (
    <div
      className={clsx(
        'border border-gray-300 px-4 pt-5 pb-5 relative rounded text-sm',
        className
      )}
    >
      <div
        className={clsx(
          'px-2 absolute bg-white left-2 top-0 -translate-y-1/2 text-gray-700 font-semibold',
          svgIcon ? 'flex items-center justify-center' : ''
        )}
        onClick={onClick}
      >
        {svgIcon && <SvgIcon name="chevron-down" className="w-5 h-5" />}
        {title}
      </div>
      {children}
    </div>
  );
};

export default SectionWrapper;
