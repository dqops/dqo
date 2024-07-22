import React, { useMemo, useState } from 'react';

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
import AddColumnDialog from './AddColumnDialog';
import AddSchemaDialog from './AddSchemaDialog';
import AddTableDialog from './AddTableDialog';
import CollectStatisticsDialog from './CollectStatisticsDialog';
import ConfirmDialog from './ConfirmDialog';
import DeleteStoredDataExtendedPopUp from './DeleteStoredDataExtendedPopUp';
import RunChecksDialog from './RunChecksDialog';

interface ContextMenuProps {
  node: CustomTreeNode;
}

const ContextMenu = ({ node }: ContextMenuProps) => {
  const { checkTypes }: { checkTypes: any } = useDecodedParams();
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const {
    refreshNode,
    collectStatisticsOnTable,
    deleteStoredData,
    runPartitionedChecks,
    reimportTableMetadata,
    removeNode
  } = useTree();
  const [open, setOpen] = useState(false);
  const history = useHistory();
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const [runChecksDialogOpened, setRunChecksDialogOpened] = useState(false);
  const [collectStatisticsDialogOpened, setCollectStatisticsDialogOpened] =
    useState(false);
  const [addColumnDialogOpen, setAddColumnDialogOpen] = useState(false);
  const [addTableDialogOpen, setAddTableDialogOpen] = useState(false);
  const [addSchemaDialogOpen, setAddSchemaDialogOpen] = useState(false);
  const [deleteNodePopup, setDeleteNodePopup] = useState(false);
  const dispatch = useActionDispatch();

  const handleRefresh = () => {
    refreshNode(node);
    setOpen(false);
  };
  const openDeleteNodePopup = () => {
    setDeleteNodePopup(true);
  };

  const openAddColumnDialog = () => {
    setAddColumnDialogOpen(true);
  };

  const closeAddColumnDialog = () => {
    setAddColumnDialogOpen(false);
  };

  const openAddTableDialog = () => {
    setAddTableDialogOpen(true);
  };

  const closeAddTableDialog = () => {
    setAddTableDialogOpen(false);
  };

  const openAddSchemaDialog = () => {
    setAddSchemaDialogOpen(true);
  };

  const closeAddSchemaDialog = () => {
    setAddSchemaDialogOpen(false);
  };

  const handleRunChecks = (
    filter?: CheckSearchFilters,
    value?: TimeWindowFilterParameters,
    collectErrorSample?: boolean
  ) => {
    // console.log(filter, value);
    runPartitionedChecks({
      check_search_filters: filter,
      time_window_filter: value,
      collect_error_samples: collectErrorSample
    });
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
        url: ROUTES.CONNECTION_DETAIL(
          checkTypes,
          node.label,
          'schemas?import_schema=true'
        ),
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

  const message = useMemo(() => {
    if (node?.level === TREE_LEVEL.DATABASE) {
      return `Are you sure want to remove connection ${node?.label}?`;
    }

    if (node?.level === TREE_LEVEL.SCHEMA) {
      return `Are you sure want to remove schema ${node?.label}?`;
    }

    if (node?.level === TREE_LEVEL.TABLE) {
      return `Are you sure want to remove table ${node?.label}?`;
    }

    if (node?.level === TREE_LEVEL.COLUMN) {
      return `Are you sure want to remove column ${node?.label}?`;
    }

    return '';
  }, [node]);

  return (
    <Popover placement="bottom-end" open={open} handler={setOpen}>
      <PopoverHandler onClick={openPopover}>
        <div className="text-gray-700 !absolute right-0 w-5 h-5 rounded-full flex items-center justify-center bg-white">
          <SvgIcon name="options" className="w-5 h-5 text-gray-500" />
        </div>
      </PopoverHandler>
      <PopoverContent
        className="z-50 min-w-50 max-w-50 border-gray-500 p-2"
        onClick={(e) => e.stopPropagation()}
      >
        <div onClick={(e) => e.stopPropagation()}>
          {node.level !== TREE_LEVEL.COLUMNS && (
            <>
              <div
                className="text-gray-900 cursor-pointer hover:bg-gray-100 pl-4 py-2 rounded flex items-center justify-between"
                onClick={() => {
                  userProfile.can_manage_data_sources
                    ? setRunChecksDialogOpened(true)
                    : undefined;
                }}
              >
                Run checks
                {checkTypes === CheckTypes.PARTITIONED && (
                  <SvgIcon name="options" className="w-5 h-5" />
                )}
              </div>
            </>
          )}
          {/* {checkTypes === 'partitioned' &&
            [
              TREE_LEVEL.DATABASE,
              TREE_LEVEL.SCHEMA,
              TREE_LEVEL.TABLE,
              TREE_LEVEL.COLUMN
            ].includes(node.level) && (
              <RunChecksPartitionedMenu onClick={onRunPartitionedChecks} />
            )} */}
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
                  ? openAddSchemaDialog()
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
                  ? openDeleteNodePopup()
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
                  ? openDeleteNodePopup()
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
                  ? openDeleteNodePopup()
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
                  ? openAddTableDialog()
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
                  ? openAddColumnDialog()
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
            </>
          )}
          {node.level === TREE_LEVEL.TABLE &&
            checkTypes === CheckTypes.SOURCES && (
              <div
                className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
                onClick={() =>
                  reimportTableMetadata(node, () => setOpen(false))
                }
              >
                Reimport metadata
              </div>
            )}
        </div>
        {deleteDataDialogOpened && (
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
        )}
        {collectStatisticsDialogOpened && (
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
        )}
        {runChecksDialogOpened && (
          <RunChecksDialog
            checkType={checkTypes}
            open={runChecksDialogOpened}
            onClose={() => {
              setRunChecksDialogOpened(false), setOpen(false);
            }}
            onClick={(
              filter: CheckSearchFilters,
              timeWindowFilter?: TimeWindowFilterParameters,
              collectErrorSample?: boolean
            ) => {
              handleRunChecks(filter, timeWindowFilter, collectErrorSample);
              setOpen(false);
              setRunChecksDialogOpened(false);
            }}
            runChecksJobTemplate={node.run_checks_job_template ?? {}}
          />
        )}
        {deleteNodePopup && (
          <ConfirmDialog
            open={deleteNodePopup}
            onClose={() => setDeleteNodePopup(false)}
            message={message}
            onConfirm={() => removeNode(node)}
          />
        )}
        {addColumnDialogOpen && (
          <AddColumnDialog
            open={addColumnDialogOpen}
            onClose={closeAddColumnDialog}
            node={node}
          />
        )}
        {addTableDialogOpen && (
          <AddTableDialog
            open={addTableDialogOpen}
            onClose={closeAddTableDialog}
            node={node}
          />
        )}
        {addSchemaDialogOpen && (
          <AddSchemaDialog
            open={addSchemaDialogOpen}
            onClose={closeAddSchemaDialog}
            node={node}
          />
        )}
      </PopoverContent>
    </Popover>
  );
};

export default ContextMenu;
