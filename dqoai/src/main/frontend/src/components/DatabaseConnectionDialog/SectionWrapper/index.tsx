import React from 'react';
import clsx from 'clsx';

interface ISectionWrapperProps {
  title: string;
  children?: any;
  className?: string;
}

const SectionWrapper: React.FC<ISectionWrapperProps> = ({ title, children, className }) => {
  return (
    <div className={clsx('border border-gray-300 px-4 py-6 relative rounded', className)}>
      <div className="px-1 absolute bg-white left-2 top-0 -translate-y-1/2 text-gray-700 font-semibold">{title}</div>
      {children}
    </div>
  );
};

export default SectionWrapper;