import React, { useState } from 'react';

import {
  Popover,
  PopoverContent,
  PopoverHandler
} from '@material-tailwind/react';
import clsx from 'clsx';
import { useSelector } from 'react-redux';
import {
  CheckDefinitionListModel,
  CheckDefinitionModel,
  SensorFolderModel
} from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  addFirstLevelTab,
  deleteCheck,
  refreshChecksFolderTree
} from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import { ChecksApi } from '../../services/apiClient';
import { ROUTES } from '../../shared/routes';
import ConfirmDialog from '../CustomTree/ConfirmDialog';
import SvgIcon from '../SvgIcon';
import AddFolderDialog from './AddFolderDialog';
import CreateCheckDialog from './CreateChecksDialog';

interface RuleContextMenuProps {
  folder?: SensorFolderModel;
  path?: string[];
  singleCheck?: boolean;
  check?: CheckDefinitionListModel;
}

const DataQualityContextMenu = ({
  folder,
  path,
  singleCheck,
  check,
}: RuleContextMenuProps) => {
  const [open, setOpen] = useState(false);
  const [isOpen, setIsOpen] = useState(false);
  const [createPopUp, setCreatePopUp] = useState(false);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const dispatch = useActionDispatch();
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const {
    refreshChecksTreeIndicator 
  } = useSelector((state: IRootState) => state.definition);

  const openPopover = (e: MouseEvent) => {
    setOpen(!open);
    e.stopPropagation();
  };

  const closeModal = () => {
    setIsOpen(false);
    setOpen(false);
  };

  const createCheck = async (fullCheckName: string, body?: CheckDefinitionModel) => {
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
    setOpen(false);
  };
  const onCopy = (): void => {
    // console.log(check);
    dispatch(
      addFirstLevelTab({
        url: ROUTES.CHECK_DETAIL(check?.check_name + '_copy' ?? ''),
        value: ROUTES.CHECK_DETAIL_VALUE(check?.check_name + '_copy' ?? ''),
        state: {
          full_check_name: check?.full_check_name,
          copied: true,
          path: path,
          type: 'create'
        },
        label: `${
          String(check?.full_check_name).split('/')[
            String(check?.full_check_name).split('/').length - 1
          ]
        }_copy`
      })
    );
  };

  const deleteChecksFromTree = async () => {
    await dispatch(deleteCheck(check?.full_check_name ?? '')).then(
      () =>dispatch(refreshChecksFolderTree(refreshChecksTreeIndicator ? false : true))
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
        {singleCheck ? (
          <div onClick={(e) => e.stopPropagation()}>
            <div
              className={clsx(
                'text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded'
              )}
              onClick={onCopy}
            >
              Copy check
            </div>
            {check?.built_in === false ? (
              <div
                className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
                onClick={() => setDeleteDialogOpen(true)}
              >
                Delete check
              </div>
            ) : null}
            <ConfirmDialog
              open={deleteDialogOpen}
              onClose={() => setDeleteDialogOpen(false)}
              onConfirm={deleteChecksFromTree}
              message={`Are you sure you want to delete the check ${check?.full_check_name}`}
            />
          </div>
        ) : (
          <div onClick={(e) => e.stopPropagation()}>
            <div
              className={clsx(
                'text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded'
              )}
              onClick={
                userProfile.can_manage_definitions !== true
                  ? undefined
                  : openAddNewCheck
              }
            >
              Add new check
            </div>
          </div>
        )}
        <div onClick={(e) => e.stopPropagation()}>
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
