import React, { useState } from 'react';

import {
  Popover,
  PopoverContent,
  PopoverHandler
} from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';
import { RuleListModel, SensorFolderModel } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab, refreshRuleFolderTree } from '../../redux/actions/definition.actions';
import { ROUTES } from '../../shared/routes';
import AddFolderDialog from './AddFolderDialog';
import { RulesApi } from '../../services/apiClient';
import ConfirmDialog from '../CustomTree/ConfirmDialog';
import { IRootState } from '../../redux/reducers';
import { useSelector } from 'react-redux';

interface RuleContextMenuProps {
  folder?: SensorFolderModel;
  path?: string[];
  singleRule?: boolean;
  rule?: RuleListModel;
}

const RuleContextMenu = ({
  folder,
  path,
  singleRule,
  rule,
}: RuleContextMenuProps) => {
  const {
    refreshRulesTreeIndicator 
  } = useSelector((state: IRootState) => state.definition);
  const [open, setOpen] = useState(false);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const dispatch = useActionDispatch();
  const [isOpen, setIsOpen] = useState(false);

  const openPopover = (e: MouseEvent) => {
    setOpen(!open);
    e.stopPropagation();
  };

  const openAddNewRule = () => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.RULE_DETAIL([...(path || []), 'new_rule'].join('-')),
        value: ROUTES.RULE_DETAIL_VALUE(
          [...(path || []), 'new_rule'].join('-')
        ),
        state: {
          type: 'create',
          path
        },
        label: 'New rule'
      })
    );
  };

  const openAddNewFolder = () => {
    setIsOpen(true);
  };

  const closeModal = () => {
    setIsOpen(false);
    setOpen(false);
  };
  const onCopy = (): void => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.RULE_DETAIL(rule?.rule_name + '_copy' ?? ''),
        value: ROUTES.RULE_DETAIL_VALUE(rule?.rule_name + '_copy' ?? ''),
        state: {
          full_rule_name: rule?.full_rule_name,
          copied: true,
          path: path,
          type: 'create'
        },
        label: `${
          String(rule?.full_rule_name).split('/')[
            String(rule?.full_rule_name).split('/').length - 1
          ]
        }_copy`
      })
    );
  };

  const deleteRuleFromTree = async () => {
    await RulesApi.deleteRule(rule?.full_rule_name ?? '').then(
      () => dispatch(refreshRuleFolderTree(refreshRulesTreeIndicator ? false : true))
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
        {singleRule === true ? (
          <div onClick={(e) => e.stopPropagation()}>
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={onCopy}
            >
              Copy rule
            </div>
            {rule?.built_in === false ? (
              <div
                className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
                onClick={() => setDeleteDialogOpen(true)}
              >
                Delete rule
              </div>
            ) : null}
            <ConfirmDialog
              open={deleteDialogOpen}
              onClose={() => setDeleteDialogOpen(false)}
              onConfirm={deleteRuleFromTree}
              message={`Are you sure you want to delete the rule ${rule?.full_rule_name}`}
            />
          </div>
        ) : (
          <div>
            <div onClick={(e) => e.stopPropagation()}>
              <div
                className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
                onClick={openAddNewRule}
              >
                Add new rule
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
            </div>
          </div>
        )}
      </PopoverContent>
    </Popover>
  );
};

export default RuleContextMenu;
