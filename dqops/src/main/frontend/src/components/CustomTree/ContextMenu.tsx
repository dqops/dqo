import React, { useState } from 'react';

import {
  Popover,
  PopoverContent,
  PopoverHandler
} from '@material-tailwind/react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import {
  CheckSearchFilters,
  RunChecksParameters,
  StatisticsCollectorSearchFilters,
  TimeWindowFilterParameters
} from '../../api';
import { useTree } from '../../contexts/treeContext';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import { TREE_LEVEL } from '../../shared/enums';
import { CustomTreeNode } from '../../shared/interfaces';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { urlencodeEncoder, useDecodedParams } from '../../utils';
import SvgIcon from '../SvgIcon';
import CollectStatisticsDialog from './CollectStatisticsDialog';
import DeleteStoredDataExtendedPopUp from './DeleteStoredDataExtendedPopUp';
import RunChecksDialog from './RunChecksDialog';
import RunChecksPartitionedMenu from './RunChecksPartitionedMenu';

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
  const { checkTypes }: { checkTypes: any } = useDecodedParams();
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const {
    refreshNode,
    runChecks,
    collectStatisticsOnTable,
    deleteStoredData,
    runPartitionedChecks,
    reimportTableMetadata
  } = useTree();
  const [open, setOpen] = useState(false);
  const history = useHistory();
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const [runChecksDialogOpened, setRunChecksDialogOpened] = useState(false);
  const [collectStatisticsDialogOpened, setCollectStatisticsDialogOpened] =
    useState(false);
  const dispatch = useActionDispatch();

  const handleRefresh = () => {
    refreshNode(node);
    setOpen(false);
  };

  const handleRunChecks = (filter?: CheckSearchFilters) => {
    if (filter) {
      runChecks({ ...node, run_checks_job_template: filter });
    } else {
      runChecks(node);
    }
    setOpen(false);
  };

  const handleRunPartitionedChecks = (value: TimeWindowFilterParameters) => {
    runPartitionedChecks(setSetectedRun(value));
    setOpen(false);
  };

  const handleCollectStatisticsOnTable = (
    filter: StatisticsCollectorSearchFilters
  ) => {
    collectStatisticsOnTable({
      ...node,
      collect_statistics_job_template: filter
    });
    setOpen(false);
  };

  const copyToClipboard = (e: any) => {
    e.stopPropagation();
    window.navigator.clipboard.writeText(node?.label);
    setOpen(false);
  };

  const importMetaData = () => {
    setOpen(false);
    dispatch(
      addFirstLevelTab(checkTypes, {
        url: ROUTES.CONNECTION_DETAIL(checkTypes, node.label, 'schemas?import_schema=true'),
        value: ROUTES.CONNECTION_LEVEL_VALUE(checkTypes, node.label),
        label: `${node.label}`
      })
    );
    history.push(
      `${ROUTES.CONNECTION_DETAIL(
        checkTypes,
        node.label || '',
        'schemas?import_schema=true'
      )}`
    );
  };

  const importTables = () => {
    setOpen(false);
    const [connection, schema] = node.id.toString().split('.');
    const url = ROUTES.SCHEMA_LEVEL_PAGE(
      CheckTypes.SOURCES,
      connection,
      schema ?? '',
      'import-tables'
    );
    const value = ROUTES.SCHEMA_LEVEL_VALUE(
      CheckTypes.SOURCES,
      connection,
      schema ?? ''
    );
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value,
        state: {},
        label: urlencodeEncoder(schema)
      })
    );
    history.push(url);
  };

  const openPopover = (e: MouseEvent) => {
    setOpen(!open);
    e.stopPropagation();
  };

  const setSetectedRun = (selected: TimeWindowFilterParameters) => {
    const obj: RunChecksParameters = {
      time_window_filter: selected,
      check_search_filters: node.run_checks_job_template
    };
    return obj;
  };

  const onRunPartitionedChecks = (value: TimeWindowFilterParameters | null) => {
    if (value) {
      handleRunPartitionedChecks(value);
    } else {
      handleRunChecks();
    }
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
              <>
                <div
                  className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
                  onClick={() => {
                    userProfile.can_manage_data_sources
                      ? setRunChecksDialogOpened(true)
                      : undefined;
                  }}
                >
                  Run checks
                </div>
                <RunChecksDialog
                  open={runChecksDialogOpened}
                  onClose={() => {
                    setRunChecksDialogOpened(false), 
                    setOpen(false);
                  }}
                  onClick={() => {
                    handleRunChecks();
                    setOpen(false);
                    setRunChecksDialogOpened(false);
                  }}
                  runChecksJobTemplate={node.run_checks_job_template ?? {}}
                />
              </>
            )}
          {checkTypes === 'partitioned' &&
            [
              TREE_LEVEL.DATABASE,
              TREE_LEVEL.SCHEMA,
              TREE_LEVEL.TABLE,
              TREE_LEVEL.COLUMN
            ].includes(node.level) && (
              <RunChecksPartitionedMenu onClick={onRunPartitionedChecks} />
            )}
          {[
            TREE_LEVEL.DATABASE,
            TREE_LEVEL.SCHEMA,
            TREE_LEVEL.TABLE,
            TREE_LEVEL.COLUMN
          ].includes(node.level) && (
            <>
              <div
                className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
                onClick={() => {
                  userProfile.can_manage_data_sources
                    ? setCollectStatisticsDialogOpened(true)
                    : undefined;
                }}
              >
                Collect statistics
              </div>
              <CollectStatisticsDialog
                open={collectStatisticsDialogOpened}
                onClose={() => {
                  setCollectStatisticsDialogOpened(false);
                  setOpen(false);
                }}
                onClick={(filter) => {
                  handleCollectStatisticsOnTable(filter), 
                  setCollectStatisticsDialogOpened(false);
                  setOpen(false);
                }}
                collectStatisticsJobTemplate={
                  node.collect_statistics_job_template ?? {}
                }
              />
            </>
          )}
          {node.level === TREE_LEVEL.DATABASE && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={() => {
                userProfile.can_manage_data_sources === true
                  ? importMetaData()
                  : undefined;
              }}
            >
              Import metadata
            </div>
          )}
          {node.level === TREE_LEVEL.DATABASE && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={() => {
                userProfile.can_manage_data_sources === true
                  ? openAddSchemaDialog(node)
                  : undefined;
              }}
            >
              Add schema
            </div>
          )}
          {node.level === TREE_LEVEL.SCHEMA && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={
                userProfile.can_manage_data_sources === true
                  ? importTables
                  : undefined
              }
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
              Copy name
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
              onClick={() => {
                userProfile.can_manage_data_sources === true
                  ? openConfirm(node)
                  : undefined;
              }}
            >
              Delete connection
            </div>
          )}
          {node.level === TREE_LEVEL.TABLE && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={() => {
                userProfile.can_manage_data_sources === true
                  ? openConfirm(node)
                  : undefined;
              }}
            >
              Delete table
            </div>
          )}
          {node.level === TREE_LEVEL.COLUMN && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={() => {
                userProfile.can_manage_data_sources === true
                  ? openConfirm(node)
                  : undefined;
              }}
            >
              Delete column
            </div>
          )}
          {node.level === TREE_LEVEL.SCHEMA && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={() => {
                userProfile.can_manage_data_sources === true
                  ? (openAddTableDialog(node), setOpen(false))
                  : undefined;
              }}
            >
              Add table
            </div>
          )}
          {(node.level === TREE_LEVEL.TABLE ||
            node.level === TREE_LEVEL.COLUMNS ||
            node.level === TREE_LEVEL.COLUMN) && (
            <div
              className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
              onClick={() => {
                userProfile.can_manage_data_sources === true
                  ? openAddColumnDialog(node)
                  : undefined;
              }}
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
                onClick={() => {
                  userProfile.can_delete_data
                    ? setDeleteDataDialogOpened(true)
                    : undefined;
                }}
              >
                Delete data quality results
              </div>
              <DeleteStoredDataExtendedPopUp
                open={deleteDataDialogOpened}
                onClose={() => {
                  setDeleteDataDialogOpened(false), setOpen(false);
                }}
                onDelete={(params) => {
                  setDeleteDataDialogOpened(false);
                  deleteStoredData(node, params);
                  setOpen(false);
                }}
                nodeId={String(node.id)}
              />
            </>
          )}

          {node.level === TREE_LEVEL.COLUMN && (
            <>
              <div
                className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
                onClick={() => {
                  userProfile.can_delete_data
                    ? setDeleteDataDialogOpened(true)
                    : undefined;
                }}
              >
                Delete data quality results
              </div>
              <DeleteStoredDataExtendedPopUp
                open={deleteDataDialogOpened}
                onClose={() => setDeleteDataDialogOpened(false)}
                onDelete={(params) => {
                  setDeleteDataDialogOpened(false);

                  deleteStoredData(
                    node,
                    params,
                    node.run_checks_job_template?.column && [
                      node.run_checks_job_template?.column
                    ]
                  );
                  setOpen(false);
                }}
                nameOfCol={node.run_checks_job_template?.column}
                nodeId={String(node.id)}
              />
            </>
          )}
          {node.level === TREE_LEVEL.TABLE &&
            checkTypes === CheckTypes.SOURCES && (
              <div
                className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
                onClick={() => reimportTableMetadata(node, () => setOpen(false))}
              >
                Reimport metadata
              </div>
            )}
        </div>
      </PopoverContent>
    </Popover>
  );
};

export default ContextMenu;
