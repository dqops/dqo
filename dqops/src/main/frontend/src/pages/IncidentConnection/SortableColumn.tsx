import React from "react";
import SvgIcon from "../../components/SvgIcon";
import clsx from "clsx";

type SortableColumnProps = {
  label: string;
  className?: string;
  order: string;
  direction?: 'asc' | 'desc';
  onChange: (orderBy: string, direction?: 'asc' | 'desc') => void;
}

export const SortableColumn = ({ label, className, order, direction, onChange }: SortableColumnProps) => {
  const handleChange = () => {
    if (!direction) {
      onChange(order, 'asc');
    } else if (direction === 'asc') {
      onChange(order, 'desc');
    } else if (direction === 'desc') {
      onChange(order, undefined);
    }
  };

  return (
    <div className={clsx('flex items-center gap-2', className)}>
      <span>{label}</span>
      <div className="cursor-pointer" onClick={handleChange}>
        <div className="w-3 h-3">
          {(!direction || direction === 'asc') && (
            <SvgIcon name="chevron-up" className="w-3 h-3" />
          )}
        </div>
        <div className="w-3 h-3">
          {(!direction || direction === 'desc') && (
            <SvgIcon name="chevron-down" className="w-3 h-3" />
          )}
        </div>
      </div>
    </div>
  )
}