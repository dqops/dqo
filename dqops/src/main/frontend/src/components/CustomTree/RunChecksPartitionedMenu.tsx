import {
  Popover,
  PopoverHandler,
  PopoverContent
} from '@material-tailwind/react';
import React, { useState } from 'react';
import SvgIcon from '../SvgIcon';
import { RUN_CHECK_TIME_WINDOW_FILTERS } from '../../shared/constants';
import { TimeWindowFilterParameters } from '../../api';

type TRunChecksPartitionedMenuProps = {
  onClick: (value: TimeWindowFilterParameters | null) => void;
};

export default function RunChecksPartitionedMenu({
  onClick
}: TRunChecksPartitionedMenuProps) {
  const [open, setOpen] = useState(false);

  const openPopover = (e: MouseEvent) => {
    setOpen(!open);
    e.stopPropagation();
  };

  return (
    <Popover placement="bottom-start" open={open} handler={setOpen}>
      <PopoverHandler onClick={openPopover}>
        <div className="text-gray-900 cursor-pointer hover:bg-gray-100 pl-4 py-2 rounded flex items-center justify-between">
          <div onClick={() => onClick(null)}>
          Run checks
          </div>
          <SvgIcon name="options" className="w-5 h-5" />
        </div>
      </PopoverHandler>
      <PopoverContent
        className="z-50 min-w-77 max-w-77 border-gray-500 p-2 !fixed !top-10 !left-68 text-sm"
        onClick={(e) => e.stopPropagation()}
      >
        {Object.entries(RUN_CHECK_TIME_WINDOW_FILTERS).map(([key, value]) => (
          <div
            className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
            key={key}
            onClick={() => onClick(value)}
          >
            {key}
          </div>
        ))}
      </PopoverContent>
    </Popover>
  );
}
