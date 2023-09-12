import React, { useState } from 'react';

import {
  Popover,
  PopoverContent,
  PopoverHandler
} from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';
import { CustomTreeNode } from '../../shared/interfaces';
import { TREE_LEVEL } from '../../shared/enums';
import { useTree } from '../../contexts/treeContext';
import { useHistory, useParams } from 'react-router-dom';
import { ROUTES } from '../../shared/routes';
import DeleteOnlyDataDialog from './DeleteOnlyDataDialog';
import { RUN_CHECK_TIME_WINDOW_FILTERS } from '../../shared/constants';
import {
  TimeWindowFilterParameters,
  RunChecksParameters
} from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setActiveFirstLevelTab } from '../../redux/actions/source.actions';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';

interface ContextMenuProps {
  node: CustomTreeNode;
  openConfirm: (node: CustomTreeNode) => void;
  openAddColumnDialog: (node: CustomTreeNode) => void;
  openAddTableDialog: (node: CustomTreeNode) => void;
  openAddSchemaDialog: (node: CustomTreeNode) => void;
}

const ContextMenu = ({
  node,
  openConfirm,
  openAddColumnDialog,
  openAddTableDialog,
  openAddSchemaDialog
}: ContextMenuProps) => {
  const { checkTypes }: { checkTypes: any } = useParams();
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const {
    refreshNode,
    runChecks,
    collectStatisticsOnTable,
    deleteStoredData,
    runPartitionedChecks
  } = useTree();
  const [open, setOpen] = useState(false);
  const history = useHistory();
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const [isClicked, setIsClicked] = useState<boolean>(false);
  const dispatch = useActionDispatch();

  const handleRefresh = () => {
    refreshNode(node);
    setOpen(false);
  };

  const handleRunChecks = () => {
    runChecks(node);
    setOpen(false);
  };

  const handleCollectStatisticsOnTable = () => {
    collectStatisticsOnTable(node);
    setOpen(false);
  };

  const copyToClipboard = (e: any) => {
    e.stopPropagation();
    window.navigator.clipboard.writeText(node?.label);
    setOpen(false);
  };

  const importMetaData = () => {
    dispatch(
      setActiveFirstLevelTab(
        checkTypes,
        ROUTES.CONNECTION_LEVEL_VALUE(checkTypes, node.label)
      )
    );
    history.push(
      `${ROUTES.CONNECTION_DETAIL(
        checkTypes,
        node.label || '',
        'schemas'
      )}?import_schema=true`
    );
  };

  const importTables = () => {
    const [connection, schema] = node.id.toString().split('.');
    dispatch(
      setActiveFirstLevelTab(
        checkTypes,
        ROUTES.SCHEMA_LEVEL_VALUE(checkTypes, connection, schema)
      )
    );
    history.push(
      `${ROUTES.SCHEMA_LEVEL_PAGE(checkTypes, connection, schema, 'tables')}`
    );
  };

  const openPopover = (e: MouseEvent) => {
    setOpen(!open);
    e.stopPropagation();
  };

  const setSetectedRun = (selected: TimeWindowFilterParameters) => {
    const obj: RunChecksParameters = {
      timeWindowFilter: selected,
      checkSearchFilters: node.run_checks_job_template
    };
    return obj;
  };

  return (
    <Popover placement="bottom-end" open={open} handler={setOpen}>
      <PopoverHandler onClick={openPopover}>
        <div className="text-gray-700 !absolute right-0 w-7 h-7 rounded-full flex items-center justify-center bg-white">
          <SvgIcon name="options" className="w-5 h-5 text-gray-500" />
        </div>
      </PopoverHandler>
      <PopoverContent
        className="z-50 min-w-50 max-w-50 border-gray-500 p-2"
        onClick={(e) => e.stopPropagation()}
      >
        <div onClick={(e) => e.stopPropagation()}>
          {node.level !== TREE_LEVEL.COLUMNS &&
            checkTypes !== 'partitioned' && (
              <div
                className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
                onClick={userProfile.can_run_checks=== true ? handleRunChecks : undefined}
              >
                Run checks
              </div>
            )}
          {checkTypes === 'partitioned' &&
            (node.level === TREE_LEVEL.COLUMN ||
              node.level === TREE_LEVEL.TABLE) && (
              <div className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded flex items-center gap-x-14">
                Run checks
                <SvgIcon
                  name="options"
                  className="w-5 h-5"
                  onClick={() => setIsClicked(!isClicked)}
                />
              </div>
            )}
          {isClicked && (
            <div className="w-80 h-81 bg-white absolute left-50 top-0 rounded border">
              {Object.entries(RUN_CHECK_TIME_WINDOW_FILTERS).map(
                ([key, value]) => (
                  <div
                    className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
                    key={key}
                    onClick={() =>
                      value
                        ? runPartitionedChecks(setSetectedRun(value))
                        : handleRunChecks()
                    }
                  >
                    {key}
                  </div>
                )
              )}
            </div>
          )}
          {[
            TREE_LEVEL.DATABASE,
            TREE_LEVEL.SCHEMA,
            TREE_LEVEL.TABLE,
            TREE_LEVEL.COLUMN
          ].includes(node.level) && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={userProfile.can_collect_statistics=== true ? handleCollectStatisticsOnTable : undefined}
            >
              Collect statistics
            </div>
          )}
          {node.level === TREE_LEVEL.DATABASE && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={ userProfile.can_manage_data_sources === true ? importMetaData : undefined}
            >
              Import metadata
            </div>
          )}
          {node.level === TREE_LEVEL.DATABASE && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={() =>{userProfile.can_manage_data_sources=== true ? openAddSchemaDialog(node) : undefined}}
            >
              Add schema
            </div>
          )}
          {node.level === TREE_LEVEL.SCHEMA && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={ userProfile.can_manage_data_sources=== true ? importTables : undefined}
            >
              Import tables
            </div>
          )}
          {(node.level === TREE_LEVEL.DATABASE ||
            node.level === TREE_LEVEL.SCHEMA ||
            node.level === TREE_LEVEL.TABLE ||
            node.level === TREE_LEVEL.COLUMN) && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={copyToClipboard}
            >
              Copy full name
            </div>
          )}
          <div
            className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
            onClick={handleRefresh}
          >
            Refresh
          </div>
          {node.level === TREE_LEVEL.DATABASE && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={() =>{userProfile.can_manage_data_sources=== true ? openConfirm(node) : undefined}}
            >
              Delete connection
            </div>
          )}
          {node.level === TREE_LEVEL.TABLE && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={() =>{userProfile.can_manage_data_sources=== true ? openConfirm(node): undefined}}
            >
              Delete table
            </div>
          )}
          {node.level === TREE_LEVEL.COLUMN && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={() =>{userProfile.can_manage_data_sources=== true ? openConfirm(node): undefined}}
            >
              Delete column
            </div>
          )}
          {node.level === TREE_LEVEL.SCHEMA && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={() =>{userProfile.can_manage_data_sources=== true ? openAddTableDialog(node): undefined}}
            >
              Add table
            </div>
          )}
          {(node.level === TREE_LEVEL.TABLE ||
            node.level === TREE_LEVEL.COLUMN) && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={() =>{userProfile.can_manage_data_sources=== true ?openAddColumnDialog(node): undefined}}
            >
              Add column
            </div>
          )}
          {(node.level === TREE_LEVEL.DATABASE ||
            node.level === TREE_LEVEL.SCHEMA ||
            node.level === TREE_LEVEL.TABLE) && (
            <>
              <div
                className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
                onClick={() =>{userProfile.can_delete_data ? setDeleteDataDialogOpened(true): undefined}}
              >
                Delete data
              </div>
              <DeleteOnlyDataDialog
                open={deleteDataDialogOpened}
                onClose={() => setDeleteDataDialogOpened(false)}
                onDelete={(params) => {
                  setDeleteDataDialogOpened(false);
                  deleteStoredData(node, params);
                  setOpen(false);
                }}
              />
            </>
          )}

          {node.level === TREE_LEVEL.COLUMN && (
            <>
              <div
                className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
                onClick={() => {userProfile.can_delete_data ? setDeleteDataDialogOpened(true): undefined}}
              >
                Delete data
              </div>
              <DeleteOnlyDataDialog
                open={deleteDataDialogOpened}
                onClose={() => setDeleteDataDialogOpened(false)}
                onDelete={(params) => {
                  setDeleteDataDialogOpened(false);

                  deleteStoredData(
                    node,
                    params,
                    node.run_checks_job_template?.columnName && [
                      node.run_checks_job_template?.columnName
                    ]
                  );
                  setOpen(false);
                }}
                nameOfCol={node.run_checks_job_template?.columnName}
              />
            </>
          )}
        </div>
      </PopoverContent>
    </Popover>
  );
};

export default ContextMenu;
