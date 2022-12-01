import React, { useState } from 'react';

import {
  Popover,
  PopoverHandler,
  PopoverContent
} from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';
import { CustomTreeNode } from '../../shared/interfaces';
import { TREE_LEVEL } from '../../shared/enums';
import { useTree } from '../../contexts/treeContext';

interface ContextMenuProps {
  node: CustomTreeNode;
  openConfirm: (node: CustomTreeNode) => void;
}

const ContextMenu = ({ node, openConfirm }: ContextMenuProps) => {
  const { refreshNode } = useTree();
  const [open, setOpen] = useState(false);

  const handleRefresh = () => {
    refreshNode(node);
    setOpen(false);
  };

  const openPopover = (e: MouseEvent) => {
    setOpen(!open);
    e.stopPropagation();
  };

  return (
    <Popover placement="bottom-end" open={open} handler={setOpen}>
      <PopoverHandler onClick={openPopover}>
        <div className="text-gray-700 !absolute right-0 w-7 h-7 rounded-full flex items-center justify-center bg-white">
          <SvgIcon name="options" className="w-5 h-5 text-gray-500" />
        </div>
      </PopoverHandler>
      <PopoverContent className="z-50 min-w-50 max-w-50 border-gray-500 p-2">
        <div>
          {[TREE_LEVEL.DATABASE, TREE_LEVEL.TABLE, TREE_LEVEL.COLUMN].includes(
            node.level
          ) && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={() => openConfirm(node)}
            >
              Delete
            </div>
          )}
          <div
            className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
            onClick={handleRefresh}
          >
            Refresh
          </div>
        </div>
      </PopoverContent>
    </Popover>
  );
};

export default ContextMenu;
