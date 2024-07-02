import { Tooltip } from '@material-tailwind/react';
import { AxiosResponse } from 'axios';
import clsx from 'clsx';
import { groupBy } from 'lodash';
import React, { useEffect, useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import { useRouteMatch } from 'react-router-dom';
import { ConnectionModel } from '../../../api';
import {
  checkTypesToJobTemplateKey,
  useTree
} from '../../../contexts/treeContext';
import { IRootState } from '../../../redux/reducers';
import { getFirstLevelActiveTab } from '../../../redux/selectors';
import { ConnectionApiClient } from '../../../services/apiClient';
import { TABLES_LIMIT_TREE_PAGING } from '../../../shared/config';
import { TREE_LEVEL } from '../../../shared/enums';
import { CustomTreeNode } from '../../../shared/interfaces';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import { urlencodeDecoder, useDecodedParams } from '../../../utils';
import { findTreeNode } from '../../../utils/tree';
import AddColumnDialog from '../../CustomTree/AddColumnDialog';
import AddSchemaDialog from '../../CustomTree/AddSchemaDialog';
import AddTableDialog from '../../CustomTree/AddTableDialog';
import ConfirmDialog from '../../CustomTree/ConfirmDialog';
import ContextMenu from '../../CustomTree/ContextMenu';
import Input from '../../Input';
import Loader from '../../Loader';
import SvgIcon from '../../SvgIcon';

const Tree = () => {
  const {
    removeNode,
    loadingNodes,
    changeActiveTab,
    setActiveTab,
    treeData,
    toggleOpenNode,
    activeTab,
    switchTab,
    refreshNode,
    setTreeData,
    loadMoreTables,
    tablesLoading,
    searchTable,
    loadedTables,
    searchColumn
  } = useTree();
  const { checkTypes }: { checkTypes: CheckTypes } = useDecodedParams();
  const [isOpen, setIsOpen] = useState(false);
  const [selectedNode, setSelectedNode] = useState<CustomTreeNode>();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const match = useRouteMatch();
  const [flag, setFlag] = useState(false);
  const [addColumnDialogOpen, setAddColumnDialogOpen] = useState(false);
  const [addTableDialogOpen, setAddTableDialogOpen] = useState(false);
  const [addSchemaDialogOpen, setAddSchemaDialogOpen] = useState(false);
  const [search, setSearch] = useState<Record<string, string>>({});
  const [funnel, setFunnel] = useState<Record<string, boolean>>({});

  const { job_dictionary_state, advisorJobId } = useSelector(
    (state: IRootState) => state.job || {}
  );
  useEffect(() => {
    if (advisorJobId && advisorJobId !== 0) {
      const job = job_dictionary_state[advisorJobId] ?? {};
      const id = job?.parameters?.importTableParameters?.connectionName ?? '';

      const schemaNode = findTreeNode(treeData, id);

      if (schemaNode?.open === true) {
        refreshNode(schemaNode, true);
      }
    }
  }, [advisorJobId, job_dictionary_state[advisorJobId]]);

  const handleNodeClick = (node: CustomTreeNode) => {
    switchTab(node, checkTypes);
    changeActiveTab(node, true);
  };

  const getNewTreeData = (
    newTreeData: CustomTreeNode[],
    items: CustomTreeNode[],
    node: CustomTreeNode
  ) => {
    newTreeData = newTreeData
      .filter(
        (item) =>
          item.id === node.id ||
          item.level === node.level ||
          item.parentId !== node.id
      )
      .map((item) => (item.id === node.id ? { ...item, open: true } : item));
    return [...newTreeData, ...items];
  };

  useEffect(() => {
    (async () => {
      const undecodedTerms = firstLevelActiveTab.split('/');
      const terms = undecodedTerms.map((x) => urlencodeDecoder(x));
      const connection = terms[3] || '';
      let newTreeData = [...(treeData || [])];

      if (!newTreeData.length) {
        const res: AxiosResponse<ConnectionModel[]> =
          await ConnectionApiClient.getAllConnections();
        const mappedConnectionsToTreeData = res.data.map((item) => ({
          id: item.connection_name ?? '',
          parentId: null,
          label: item.connection_name ?? '',
          items: [],
          level: TREE_LEVEL.DATABASE,
          tooltip: item.connection_name,
          run_checks_job_template:
            item[
              checkTypesToJobTemplateKey[
                checkTypes as keyof typeof checkTypesToJobTemplateKey
              ] as keyof ConnectionModel
            ],
          collect_statistics_job_template: item.collect_statistics_job_template,
          data_clean_job_template: item.data_clean_job_template,
          open: false
        }));
        newTreeData = [...mappedConnectionsToTreeData];
      }

      const connectionNode = findTreeNode(newTreeData, connection);
      if (connectionNode && !connectionNode.open) {
        const items = await refreshNode(connectionNode, false);
        newTreeData = getNewTreeData(newTreeData, items, connectionNode);
      }

      const schema = terms[5] || '';
      const schemaNode = findTreeNode(newTreeData, `${connection}.${schema}`);
      if (schemaNode && !schemaNode.open) {
        const items = await refreshNode(schemaNode, false);
        newTreeData = getNewTreeData(newTreeData, items, schemaNode);
      }

      const table = terms[7] || '';
      const tableNode = findTreeNode(
        newTreeData,
        `${connection}.${schema}.${table}`
      );
      if (tableNode && !tableNode.open) {
        const items = await refreshNode(tableNode, false);
        newTreeData = getNewTreeData(newTreeData, items, tableNode);
      }

      if (match.path === ROUTES.PATTERNS.CONNECTION) {
        setActiveTab(connectionNode?.id || '');
      }

      if (match.path === ROUTES.PATTERNS.SCHEMA) {
        setActiveTab(schemaNode?.id || '');
      }

      if (match.path === ROUTES.PATTERNS.TABLE) {
        setActiveTab(tableNode?.id || '');
      }

      if (match.path === ROUTES.PATTERNS.TABLE_PROFILING) {
        setActiveTab(`${tableNode?.id || ''}.checks`);
      }

      if (match.path === ROUTES.PATTERNS.TABLE_COLUMNS) {
        setActiveTab(`${tableNode?.id || ''}.columns`);
      }

      if (match.path === ROUTES.PATTERNS.TABLE_INCIDENTS_NOTIFICATION) {
        setActiveTab(`${tableNode?.id || ''}.incidents`);
      }

      if (match.path === ROUTES.PATTERNS.TABLE_MONITORING_DAILY) {
        setActiveTab(`${tableNode?.id || ''}.dailyCheck`);
      }

      if (match.path === ROUTES.PATTERNS.TABLE_MONITORING_MONTHLY) {
        setActiveTab(`${tableNode?.id || ''}.monthlyCheck`);
      }

      if (match.path === ROUTES.PATTERNS.TABLE_PARTITIONED_DAILY) {
        setActiveTab(`${tableNode?.id || ''}.dailyPartitionedChecks`);
      }

      if (match.path === ROUTES.PATTERNS.TABLE_PARTITIONED_MONTHLY) {
        setActiveTab(`${tableNode?.id || ''}.monthlyPartitionedChecks`);
      }

      if (match.path === ROUTES.PATTERNS.COLUMN) {
        const columnsNode = findTreeNode(
          newTreeData,
          `${tableNode?.id || ''}.columns`
        );
        if (columnsNode && !columnsNode.open) {
          const items = await refreshNode(columnsNode, false);
          newTreeData = getNewTreeData(newTreeData, items, columnsNode);
        }
        setActiveTab(`${columnsNode?.id || ''}.${terms[9] || ''}`);
      }
      if (match.path === ROUTES.PATTERNS.COLUMN_PROFILING) {
        const columnsNode = findTreeNode(
          newTreeData,
          `${tableNode?.id || ''}.columns`
        );
        if (columnsNode && !columnsNode.open) {
          const items = await refreshNode(columnsNode, false);
          newTreeData = getNewTreeData(newTreeData, items, columnsNode);
        }

        const column = terms[9];
        const columnNode = findTreeNode(
          newTreeData,
          `${columnsNode?.id || ''}.${column}`
        );
        if (columnNode && !columnNode.open) {
          const items = await refreshNode(columnNode, false);
          newTreeData = getNewTreeData(newTreeData, items, columnNode);
        }

        setActiveTab(`${columnNode?.id || ''}.checks`);
      }
      if (match.path === ROUTES.PATTERNS.COLUMN_MONITORING_DAILY) {
        const columnsNode = findTreeNode(
          newTreeData,
          `${tableNode?.id || ''}.columns`
        );
        if (columnsNode && !columnsNode.open) {
          const items = await refreshNode(columnsNode, false);
          newTreeData = getNewTreeData(newTreeData, items, columnsNode);
        }

        const column = terms[9];
        const columnNode = findTreeNode(
          newTreeData,
          `${columnsNode?.id || ''}.${column}`
        );
        if (columnNode && !columnNode.open) {
          const items = await refreshNode(columnNode, false);
          newTreeData = getNewTreeData(newTreeData, items, columnNode);
        }

        setActiveTab(`${columnNode?.id || ''}.dailyCheck`);
      }
      if (match.path === ROUTES.PATTERNS.COLUMN_MONITORING_MONTHLY) {
        const columnsNode = findTreeNode(
          newTreeData,
          `${tableNode?.id || ''}.columns`
        );
        if (columnsNode && !columnsNode.open) {
          const items = await refreshNode(columnsNode, false);
          newTreeData = getNewTreeData(newTreeData, items, columnsNode);
        }

        const column = terms[9];
        const columnNode = findTreeNode(
          newTreeData,
          `${columnsNode?.id || ''}.${column}`
        );
        if (columnNode && !columnNode.open) {
          const items = await refreshNode(columnNode, false);
          newTreeData = getNewTreeData(newTreeData, items, columnNode);
        }

        setActiveTab(`${columnNode?.id || ''}.monthlyCheck`);
      }
      setTreeData(newTreeData);

      setFlag((prev) => !prev);
    })();
  }, [firstLevelActiveTab]);

  const groupedData = groupBy(treeData, 'parentId');

  const getIcon = (level: TREE_LEVEL) => {
    if (level === TREE_LEVEL.DATABASE) return 'database';
    if (level === TREE_LEVEL.SCHEMA) return 'schema';
    if (level === TREE_LEVEL.TABLE) return 'table';
    if (level === TREE_LEVEL.CHECK) return 'search';
    if (level === TREE_LEVEL.COLUMNS || level === TREE_LEVEL.COLUMN)
      return 'column';
    if (
      level === TREE_LEVEL.COLUMN_CHECKS ||
      level === TREE_LEVEL.COLUMN_MONTHLY_CHECKS ||
      level === TREE_LEVEL.COLUMN_DAILY_CHECKS ||
      level === TREE_LEVEL.COLUMN_PARTITIONED_DAILY_CHECKS ||
      level === TREE_LEVEL.COLUMN_PARTITIONED_MONTHLY_CHECKS
    ) {
      return 'column-check';
    }
    if (
      level === TREE_LEVEL.TABLE_CHECKS ||
      level === TREE_LEVEL.TABLE_DAILY_CHECKS ||
      level === TREE_LEVEL.TABLE_MONTHLY_CHECKS ||
      level === TREE_LEVEL.TABLE_PARTITIONED_DAILY_CHECKS ||
      level === TREE_LEVEL.TABLE_PARTITIONED_MONTHLY_CHECKS
    ) {
      return 'table-check';
    }

    return 'column';
  };

  const openConfirm = (node: CustomTreeNode) => {
    setSelectedNode(node);
    setIsOpen(true);
  };

  const openAddColumnDialog = (node: CustomTreeNode) => {
    setSelectedNode(node);
    setAddColumnDialogOpen(true);
  };

  const closeAddColumnDialog = () => {
    setAddColumnDialogOpen(false);
    setSelectedNode(undefined);
  };

  const openAddTableDialog = (node: CustomTreeNode) => {
    setSelectedNode(node);
    setAddTableDialogOpen(true);
  };

  const closeAddTableDialog = () => {
    setAddTableDialogOpen(false);
    setSelectedNode(undefined);
  };

  const openAddSchemaDialog = (node: CustomTreeNode) => {
    setSelectedNode(node);
    setAddSchemaDialogOpen(true);
  };

  const closeAddSchemaDialog = () => {
    setAddSchemaDialogOpen(false);
    setSelectedNode(undefined);
  };

  const renderIcon = (node: CustomTreeNode) => {
    if (
      node.level === TREE_LEVEL.TABLE_INCIDENTS ||
      node.level === TREE_LEVEL.CHECK ||
      (node.level === TREE_LEVEL.COLUMN && checkTypes === CheckTypes.SOURCES)
    ) {
      return <div className="w-4 min-w-4 shrink-0 mr-1" />;
    }
    if (loadingNodes[node.id]) {
      return (
        <SvgIcon
          className="w-4 min-w-4 cursor-pointer shrink-0 animate-spin mr-1"
          name="spinner"
        />
      );
    }
    return (
      <div
        className="
      w-4 min-w-4 cursor-pointer shrink-0 relative mr-1"
      >
        <div
          className="w-7 h-6 min-w-7 min-h-6 absolute top-1.5"
          style={{ right: '-5px' }}
          onClick={() => {
            !(node.parsingYamlError && node.parsingYamlError.length > 0)
              ? toggleOpenNode(node.id)
              : undefined;
          }}
        >
          <SvgIcon
            className="w-4 min-w-4 cursor-pointer shrink-0 absolute right-1"
            name={!node.open ? 'arrow-alt-right' : 'arrow-alt-down'}
          />
        </div>
      </div>
    );
  };

  const renderParsingYamlErrorToolTip = (node: CustomTreeNode) => {
    return (
      <Tooltip
        content={node.parsingYamlError}
        className="max-w-120 z-50"
        placement="right-start"
      >
        <div
          style={{
            position: 'absolute',
            right: '70px',
            top: '-9px',
            borderRadius: '3px'
          }}
          className="bg-white"
        >
          <SvgIcon name="warning" className="w-5 h-5" />
        </div>
      </Tooltip>
    );
  };

  const renderErrorMessageToolTip = (node: CustomTreeNode) => {
    return (
      <Tooltip
        content={node.error_message}
        className="max-w-120 z-50"
        placement="right-start"
      >
        <div
          style={{
            position: 'absolute',
            right: '45px',
            top: '-9px',
            borderRadius: '3px'
          }}
        >
          <SvgIcon name="warning-generic" className="w-5 h-5 text-yellow-600" />{' '}
          {/* text-[#e0ca00] */}
        </div>
      </Tooltip>
    );
  };
  const renderTreeNode = (node: CustomTreeNode, deep: number) => {
    return (
      <div style={{ paddingLeft: deep ? 16 : 0 }}>
        <div
          className={clsx(
            'px-2 cursor-pointer flex  hover:bg-gray-100  mb-0.5',
            activeTab === node.id ? 'bg-gray-100' : '',
            node.error_message !== undefined ? 'text-[#797500]' : '',
            node.level === TREE_LEVEL.TABLE &&
              checkTypes === CheckTypes.PARTITIONED &&
              node.configured
              ? 'text-orange-500'
              : '',
            node?.parsingYamlError !== undefined ? 'text-red-900' : ''
          )}
        >
          {renderIcon(node)}
          <div
            className="flex space-x-2 py-1 flex-1 w-full text-[13px]"
            onClick={() => {
              !(node.parsingYamlError && node.parsingYamlError.length > 0)
                ? handleNodeClick(node)
                : undefined;
            }}
          >
            <SvgIcon
              name={getIcon(node.level)}
              className={clsx('w-4 shrink-0 min-w-4')}
            />
            <Tooltip
              content={node.tooltip ?? node.id}
              className="max-w-120 py-4 px-4  delay-300 pointer-events-none"
              placement="top-start"
            >
              <div className="flex flex-1 justify-between items-center">
                <div
                  className={clsx(
                    `flex-1 truncate`,
                    node.hasCheck ? 'font-bold' : ''
                  )}
                >
                  {node.label}
                </div>
                {node.level === TREE_LEVEL.SCHEMA ||
                  (node.level === TREE_LEVEL.COLUMNS && (
                    <div className="!absolute right-5.5 h-5 w-5 flex items-center justify-center  rounded-full bg-white">
                      <SvgIcon
                        name={funnel[node.id] ? 'filled_funnel' : 'funnel'}
                        className="w-3 h-3"
                        onClick={(event) => {
                          event.stopPropagation();
                          setFunnel({
                            ...funnel,
                            [node.id]: !funnel[node.id]
                          });
                        }}
                      />
                    </div>
                  ))}
                <div className="relative ">
                  {node.parsingYamlError && node.parsingYamlError.length > 0
                    ? renderParsingYamlErrorToolTip(node)
                    : null}

                  {node.error_message && node.error_message.length > 0
                    ? renderErrorMessageToolTip(node)
                    : null}
                </div>
                <ContextMenu
                  node={node}
                  openConfirm={openConfirm}
                  openAddColumnDialog={openAddColumnDialog}
                  openAddTableDialog={openAddTableDialog}
                  openAddSchemaDialog={openAddSchemaDialog}
                />
              </div>
            </Tooltip>
          </div>
        </div>
        {node.open && <div>{renderTree(node.id.toString(), deep + 1)}</div>}
      </div>
    );
  };

  const renderTree = (parentId: string, deep: number) => {
    if (!groupedData[parentId]) return;
    const isTableLevel = groupedData[parentId].find(
      (item) => item?.level === TREE_LEVEL.TABLE
    );

    const isColumnLevel = groupedData[parentId].find(
      (item) => item?.level === TREE_LEVEL.COLUMN
    );

    const searchForSource = (column: boolean) => {
      const onChangeSearchTable = (
        e: React.ChangeEvent<HTMLInputElement>,
        parentId: string
      ) => {
        setSearch({ ...search, [parentId]: e.target.value });
      };
      const onKeyDownSearchTable = (
        e: React.KeyboardEvent<HTMLInputElement>,
        parentId: string
      ) => {
        if (e.key === 'Enter') {
          if (column) {
            searchColumn(groupedData[parentId], groupedData, search[parentId]);
          } else {
            searchTable(groupedData[parentId], groupedData, search[parentId]);
          }
        }
      };

      return (
        <div
          className={clsx(
            'ml-4 pl-2 cursor-pointer flex items-center gap-x-2 text-[13px] py-1 mb-0.5'
          )}
        >
          <Input
            className="w-1/2 !h-7 !text-[13px]"
            placeholder="Search"
            value={search[parentId]}
            onChange={(e) => onChangeSearchTable(e, parentId)}
            onKeyDown={(e) => onKeyDownSearchTable(e, parentId)}
          />
        </div>
      );
    };

    return (
      <div>
        {(isTableLevel || isColumnLevel) &&
          funnel[parentId] &&
          searchForSource(isColumnLevel)}
        {groupedData[parentId].map((item) => (
          <>
            <div key={item.id}>{renderTreeNode(item, deep)}</div>
          </>
        ))}
        {isTableLevel &&
          !loadedTables[parentId] &&
          groupedData[parentId].length % TABLES_LIMIT_TREE_PAGING === 0 && (
            <div
              className={clsx(
                'ml-4 pl-7 cursor-pointer flex text-[13px] hover:bg-gray-100 py-1.5 mb-0.5 text-teal-500',
                parentId === tablesLoading && 'pl-0'
              )}
              onClick={() =>
                loadMoreTables(
                  groupedData[parentId],
                  groupedData,
                  search[parentId]
                )
              }
            >
              {parentId === tablesLoading && (
                <Loader className="w-4 h-4 ml-1" isFull={false} />
              )}
              Load more tables
            </div>
          )}
      </div>
    );
  };

  const message = useMemo(() => {
    if (selectedNode?.level === TREE_LEVEL.DATABASE) {
      return `Are you sure want to remove connection ${selectedNode?.label}?`;
    }

    if (selectedNode?.level === TREE_LEVEL.SCHEMA) {
      return `Are you sure want to remove schema ${selectedNode?.label}?`;
    }

    if (selectedNode?.level === TREE_LEVEL.TABLE) {
      return `Are you sure want to remove table ${selectedNode?.label}?`;
    }

    if (selectedNode?.level === TREE_LEVEL.COLUMN) {
      return `Are you sure want to remove column ${selectedNode?.label}?`;
    }

    return '';
  }, [selectedNode]);

  return (
    <div className={clsx('pl-2', checkTypes === 'sources' ? 'mt-4' : 'mt-0')}>
      <div className="hidden">{flag}</div>
      <div>{renderTree('null', 0)}</div>
      <ConfirmDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        message={message}
        onConfirm={() => removeNode(selectedNode)}
      />

      <AddColumnDialog
        open={addColumnDialogOpen}
        onClose={closeAddColumnDialog}
        node={selectedNode}
      />
      <AddTableDialog
        open={addTableDialogOpen}
        onClose={closeAddTableDialog}
        node={selectedNode}
      />
      <AddSchemaDialog
        open={addSchemaDialogOpen}
        onClose={closeAddSchemaDialog}
        node={selectedNode}
      />
    </div>
  );
};

export default Tree;
