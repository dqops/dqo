import React, { useState } from 'react';

import {
  Popover,
  PopoverContent,
  PopoverHandler
} from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';
import { CheckSpecModel, SensorBasicFolderModel } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../redux/actions/definition.actions';
import { ROUTES } from '../../shared/routes';
import AddFolderDialog from './AddFolderDialog';
import { ChecksApi } from '../../services/apiClient';
import CreateCheckDialog from './CreateChecksDialog';
import clsx from 'clsx';

interface RuleContextMenuProps {
  folder?: SensorBasicFolderModel;
  path?: string[];
  canCreateCheckHere: boolean;
}

const DataQualityContextMenu = ({
  folder,
  path,
  canCreateCheckHere
}: RuleContextMenuProps) => {
  const [open, setOpen] = useState(false);
  const [isOpen, setIsOpen] = useState(false);
  const [createPopUp, setCreatePopUp] = useState(false);
  const dispatch = useActionDispatch();

  const openPopover = (e: MouseEvent) => {
    setOpen(!open);
    e.stopPropagation();
  };

  const openAddNewFolder = () => {
    setIsOpen(true);
  };

  const closeModal = () => {
    setIsOpen(false);
    setOpen(false);
  };

  const createCheck = async (fullCheckName: string, body?: CheckSpecModel) => {
    await ChecksApi.createCheck(fullCheckName, body);
  };

  const openAddNewCheck = () => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.CHECK_DETAIL([...(path || []), 'new_check'].join('-')),
        value: ROUTES.CHECK_DETAIL_VALUE(
          [...(path || []), 'new_check'].join('-')
        ),
        state: {
          type: 'create',
          path
        },
        label: 'New check'
      })
    );
  };

  return (
    <Popover placement="bottom-end" open={open} handler={setOpen}>
      <PopoverHandler onClick={openPopover}>
        <div className="text-gray-700 !absolute right-0 w-7 h-7 rounded-full flex items-center justify-center bg-white">
          <SvgIcon name="options" className="w-5 h-5 text-gray-500" />
        </div>
      </PopoverHandler>
      <PopoverContent className="z-50 min-w-50 max-w-50 border-gray-500 p-2">
        <div onClick={(e) => e.stopPropagation()}>
          <div
            className={clsx(
              'text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded',
              canCreateCheckHere ? 'text-gray-900' : 'text-red-500'
            )}
            onClick={canCreateCheckHere ? openAddNewCheck : undefined}
          >
            Add new check
          </div>
        </div>
        <div onClick={(e) => e.stopPropagation()}>
          <div
            className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
            onClick={openAddNewFolder}
          >
            Add new folder
          </div>
          <AddFolderDialog
            open={isOpen}
            onClose={closeModal}
            path={path}
            folder={folder}
            type="rule"
          />
          <CreateCheckDialog
            open={createPopUp}
            onClose={() => setCreatePopUp(false)}
            onConfirm={createCheck}
          />
        </div>
      </PopoverContent>
    </Popover>
  );
};

export default DataQualityContextMenu;
