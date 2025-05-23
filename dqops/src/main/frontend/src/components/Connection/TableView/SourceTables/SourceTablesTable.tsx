import { IconButton, Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { TableLineageTableListModel } from '../../../../api';
import { useActionDispatch } from '../../../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../../../redux/actions/source.actions';
import { DataLineageApiClient } from '../../../../services/apiClient';
import { CheckTypes, ROUTES } from '../../../../shared/routes';
import { sortPatterns, useDecodedParams } from '../../../../utils';
import ConfirmDialog from '../../../CustomTree/ConfirmDialog';
import QualityDimensionStatuses from '../../../DataQualityChecks/QualityDimension/QualityDimensionStatuses';
import Loader from '../../../Loader';
import ClientSidePagination from '../../../Pagination/ClientSidePagination';
import SvgIcon from '../../../SvgIcon';

const SOURCE_HEADER_ELEMENTS = [
  { label: 'Source connection', key: 'source_connection' },
  { label: 'Source schema', key: 'source_schema' },
  { label: 'Source table', key: 'source_table' },
  { label: 'Action', key: 'action' }
];

const TARGET_HEADER_ELEMENTS = [
  { label: 'Target connection', key: 'source_connection' },
  { label: 'Target schema', key: 'source_schema' },
  { label: 'Target table', key: 'source_table' },
  { label: 'Action', key: 'action' }
];

export default function SourceTablesTable({
  connection: parentConnection,
  schema: parentSchema,
  table: parentTable,
  setSourceTableEdit,
  showHeader: showHeader = true,
  isTarget,
  setExistingTables
}: {
  connection?: string;
  schema?: string;
  table?: string;
  setSourceTableEdit?: (
    obj: { connection: string; schema: string; table: string } | null
  ) => void;
  showHeader?: boolean;
  isTarget?: boolean;
  setExistingTables?: (
    tables: { connection: string; schema: string; table: string }[]
  ) => void;
}) {
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } =
    parentConnection && parentSchema && parentTable
      ? {
          connection: parentConnection,
          schema: parentSchema,
          table: parentTable
        }
      : useDecodedParams();

  const [dir, setDir] = useState<'asc' | 'desc'>('asc');
  const [sorceTableToDelete, setSorceTableToDelete] = useState<{
    connection: string;
    schema: string;
    table: string;
  } | null>(null);
  const [indexSortingElement, setIndexSortingElement] = useState(1);
  const [displayedTables, setDisplayedTables] = useState<
    TableLineageTableListModel[]
  >([]);
  const [expandedLineage, setExpandedLineage] = useState<
    { connection: string; schema: string; table: string }[] | null
  >(null);
  const [tables, setTables] = useState<TableLineageTableListModel[]>([]);
  const [loading, setLoading] = useState(false);
  const [refreshTimer, setRefreshTimer] = useState<NodeJS.Timer>();

  const getTables = async () => {
    setLoading(true);
    if (isTarget) {
      await DataLineageApiClient.getTableTargetTables(connection, schema, table)
        .then((res) => {
          setLoading(false);
          setTables(res.data);
          refetchTables(res.data);
          const existingTables = res.data.map((table) => ({
            connection: table.target_connection ?? '',
            schema: table.target_schema ?? '',
            table: table.target_table ?? ''
          }));
          setExistingTables && setExistingTables(existingTables);
        })
        .finally(() => {
          setLoading(false);
          setRefreshTimer(undefined);
        });
    } else {
      DataLineageApiClient.getTableSourceTables(connection, schema, table)
        .then((res) => {
          setLoading(false);
          setTables(res.data);
          refetchTables(res.data);
          const existingTables = res.data.map((table) => ({
            connection: table.source_connection ?? '',
            schema: table.source_schema ?? '',
            table: table.source_table ?? ''
          }));
          setExistingTables && setExistingTables(existingTables);
        })
        .finally(() => {
          setLoading(false);
          setRefreshTimer(undefined);
        });
    }
  };

  const refetchTables = (tables?: TableLineageTableListModel[]) => {
    const shouldRefetch = tables?.some(
      (table) => !table?.table_data_quality_status
    );

    if (shouldRefetch) {
      setRefreshTimer(
        setTimeout(() => {
          getTables();
        }, 5000)
      );
    }
  };

  useEffect(() => {
    getTables();
    return () => {
      if (refreshTimer) {
        clearTimeout(refreshTimer);
      }
    };
  }, [connection, schema, table]);

  const handleSort = (elem: { label: string; key: string }, index: number) => {
    const newDir = dir === 'asc' ? 'desc' : 'asc';
    setDisplayedTables(
      sortPatterns(tables, elem.key as keyof TableLineageTableListModel, newDir)
    );
    setDir(newDir);
    setIndexSortingElement(index);
  };

  const handleDeleteSourceTable = (
    source: { connection: string; schema: string; table: string } | null
  ) => {
    DataLineageApiClient.deleteTableSourceTable(
      connection,
      schema,
      table,
      source?.connection ?? '',
      source?.schema ?? '',
      source?.table ?? ''
    ).then(() => {
      const newTables = tables.filter(
        (table) =>
          !(
            (isTarget ? table.target_connection : table.source_connection) ===
              source?.connection &&
            (isTarget ? table.target_schema : table.source_schema) ===
              source?.schema &&
            (isTarget ? table.target_table : table.source_table) ===
              source?.table
          )
      );
      setTables(newTables);
    });
  };

  const dispatch = useActionDispatch();
  const history = useHistory();

  const goTable = (dataLineage: TableLineageTableListModel) => {
    const connection = isTarget
      ? dataLineage.target_connection
      : dataLineage.source_connection;
    const schema = isTarget
      ? dataLineage.target_schema
      : dataLineage.source_schema;
    const table = isTarget
      ? dataLineage.target_table
      : dataLineage.source_table;

    const url = ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      connection ?? '',
      schema ?? '',
      table ?? '',
      'detail'
    );

    const value = ROUTES.TABLE_LEVEL_VALUE(
      CheckTypes.SOURCES,
      connection ?? '',
      schema ?? '',
      table ?? ''
    );

    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value,
        label: table ?? '',
        state: {}
      })
    );
    history.push(url);
  };

  const onChangeExpandedLineage = (lineage: TableLineageTableListModel) => {
    const connection = isTarget
      ? lineage.target_connection
      : lineage.source_connection;
    const schema = isTarget ? lineage.target_schema : lineage.source_schema;
    const table = isTarget ? lineage.target_table : lineage.source_table;

    const newExpandedLineage = [...(expandedLineage ?? [])];

    if (
      newExpandedLineage.find(
        (expandedLiceage) =>
          expandedLiceage.connection === connection &&
          expandedLiceage.schema === schema &&
          expandedLiceage.table === table
      )
    ) {
      newExpandedLineage.splice(
        newExpandedLineage.findIndex(
          (expandedLiceage) =>
            expandedLiceage.connection === connection &&
            expandedLiceage.schema === schema &&
            expandedLiceage.table === table
        ),
        1
      );
    } else {
      newExpandedLineage.push({
        connection: connection ?? '',
        schema: schema ?? '',
        table: table ?? ''
      });
    }

    setExpandedLineage(newExpandedLineage);
  };

  if (loading) {
    return (
      <div className="w-full h-screen flex justify-center items-center">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }

  return (
    <>
      <table className="text-sm">
        {showHeader && (
          <thead>
            <tr>
              <th></th>
              <th></th>
              {(isTarget ? TARGET_HEADER_ELEMENTS : SOURCE_HEADER_ELEMENTS).map(
                (elem, index) => {
                  if (elem.key === 'action' && !setSourceTableEdit) {
                    return null;
                  }
                  return (
                    <th key={index} onClick={() => handleSort(elem, index)}>
                      <div
                        className={clsx(
                          'flex items-center gap-x-1 px-4',
                          elem.key === 'action' && 'ml-10'
                        )}
                      >
                        {elem.label}
                        {elem.key !== 'action' && (
                          <div>
                            {!(
                              indexSortingElement === index && dir === 'asc'
                            ) ? (
                              <SvgIcon
                                name="chevron-up"
                                className="w-2 h-2 text-black cursor-pointer"
                                onClick={() => handleSort(elem, index)}
                              />
                            ) : (
                              <div className="w-2 h-2" />
                            )}
                            {!(
                              indexSortingElement === index && dir === 'desc'
                            ) ? (
                              <SvgIcon
                                name="chevron-down"
                                className="w-2 h-2 text-black cursor-pointer"
                                onClick={() => handleSort(elem, index)}
                              />
                            ) : (
                              <div className="w-2 h-2" />
                            )}
                          </div>
                        )}
                      </div>
                    </th>
                  );
                }
              )}
            </tr>
          </thead>
        )}
        <tbody className={clsx('', showHeader && 'border-t border-gray-100')}>
          {displayedTables && tables.length === 0 && (
            <tr className="!h-6 ml-4">
              <td>
                <div className="ml-4">
                  This table has no {isTarget ? 'target' : 'source'} tables.
                </div>
              </td>
            </tr>
          )}
          {(displayedTables ?? tables).map((table, index) => (
            <React.Fragment key={index}>
              <tr className="!h-6">
                <td
                  onClick={() => onChangeExpandedLineage(table)}
                  className="cursor-pointer"
                >
                  {table.table_data_quality_status && (
                    <div className="pl-2">
                      {expandedLineage?.find(
                        (lineage) =>
                          lineage.connection ===
                            (isTarget
                              ? table.target_connection
                              : table.source_connection) &&
                          lineage.schema ===
                            (isTarget
                              ? table.target_schema
                              : table.source_schema) &&
                          lineage.table ===
                            (isTarget ? table.target_table : table.source_table)
                      ) ? (
                        <SvgIcon name="chevron-down" className="w-4" />
                      ) : (
                        <SvgIcon name="chevron-right" className="w-4" />
                      )}
                    </div>
                  )}
                </td>
                <td>
                  <div className="flex items-center gap-x-2 min-w-20 !max-w-40">
                    {table.table_data_quality_status ? (
                      <QualityDimensionStatuses
                        dimensions={table.table_data_quality_status?.dimensions}
                      />
                    ) : (
                      <SvgIcon name="hourglass" className="w-4 h-4" />
                    )}
                    {!table.table_data_quality_status?.table_exist && (
                      <Tooltip
                        color="light"
                        content="Table doesn't exist"
                        placement="top"
                      >
                        <div>
                          <SvgIcon
                            name="warning-generic"
                            className="w-5 h-5 text-yellow-600"
                          />
                        </div>
                      </Tooltip>
                    )}
                  </div>
                </td>
                <td className="px-4">
                  <div className="min-w-20 !max-w-100 truncate">
                    {isTarget
                      ? table.target_connection
                      : table.source_connection}
                  </div>
                </td>
                <td className="px-4">
                  <div className="min-w-20 !max-w-100 truncate">
                    {isTarget ? table.target_schema : table.source_schema}
                  </div>
                </td>
                <td className="px-4">
                  <div className="min-w-20 !max-w-100 truncate">
                    {isTarget ? table.target_table : table.source_table}
                  </div>
                </td>
                {setSourceTableEdit && (
                  <td className="px-4">
                    <div className="flex items-center gap-x-4">
                      {table.table_data_quality_status?.table_exist ? (
                        <IconButton
                          ripple={false}
                          onClick={() =>
                            setSourceTableEdit &&
                            setSourceTableEdit({
                              connection:
                                (isTarget
                                  ? table.target_connection
                                  : table.source_connection) ?? '',
                              schema:
                                (isTarget
                                  ? table.target_schema
                                  : table.source_schema) ?? '',
                              table:
                                (isTarget
                                  ? table.target_table
                                  : table.source_table) ?? ''
                            })
                          }
                          size="sm"
                          color="teal"
                          className={clsx(
                            '!shadow-none hover:!shadow-none hover:bg-[#028770]'
                          )}
                        >
                          <Tooltip content="Edit data lineage mapping">
                            <div>
                              <SvgIcon name="edit" className="w-4" />
                            </div>
                          </Tooltip>
                        </IconButton>
                      ) : (
                        <div className="w-8" />
                      )}
                      <IconButton
                        onClick={() =>
                          setSorceTableToDelete({
                            connection:
                              (isTarget
                                ? table.target_connection
                                : table.source_connection) ?? '',
                            schema:
                              (isTarget
                                ? table.target_schema
                                : table.source_schema) ?? '',
                            table:
                              (isTarget
                                ? table.target_table
                                : table.source_table) ?? ''
                          })
                        }
                        size="sm"
                        color="teal"
                        className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                      >
                        <Tooltip content="Delete data lineage mapping">
                          <div>
                            <SvgIcon name="delete" className="w-4" />
                          </div>
                        </Tooltip>
                      </IconButton>
                      {table.table_data_quality_status?.table_exist && (
                        <IconButton
                          onClick={() => goTable(table)}
                          size="sm"
                          color="teal"
                          className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                        >
                          <Tooltip
                            content={
                              isTarget
                                ? 'Jump to the target (downstream) table'
                                : 'Jump to the source (upstream) table'
                            }
                          >
                            <div>
                              <SvgIcon
                                name="data_sources_white"
                                className="w-5"
                              />
                            </div>
                          </Tooltip>
                        </IconButton>
                      )}
                    </div>
                  </td>
                )}
              </tr>
              {expandedLineage?.find(
                (lineage) =>
                  lineage.connection ===
                    (isTarget
                      ? table.target_connection
                      : table.source_connection) &&
                  lineage.schema ===
                    (isTarget ? table.target_schema : table.source_schema) &&
                  lineage.table ===
                    (isTarget ? table.target_table : table.source_table) &&
                  table.table_data_quality_status?.table_exist
              ) && (
                <tr>
                  <td colSpan={5} className="pl-4 py-1">
                    <SourceTablesTable
                      connection={
                        isTarget
                          ? table.target_connection
                          : table.source_connection
                      }
                      schema={
                        isTarget ? table.target_schema : table.source_schema
                      }
                      table={isTarget ? table.target_table : table.source_table}
                      showHeader={false}
                      isTarget={isTarget}
                    />
                  </td>
                </tr>
              )}
            </React.Fragment>
          ))}
        </tbody>
      </table>

      <ConfirmDialog
        open={!!sorceTableToDelete}
        onConfirm={async () => {
          handleDeleteSourceTable(sorceTableToDelete);
          setSorceTableToDelete(null);
        }}
        onClose={() => setSorceTableToDelete(null)}
        message={
          `Are you sure you want to delete this ` +
          (isTarget ? `target` : `source`) +
          ` table?`
        }
      />
      <div
        className={'px-4 ' + (!tables || tables.length < 25) ? 'hidden' : ''}
      >
        <ClientSidePagination
          items={tables}
          onChangeItems={setDisplayedTables}
          className="pl-0 !w-full pr-4"
          hidden={tables.length < 25}
        />
      </div>
    </>
  );
}
