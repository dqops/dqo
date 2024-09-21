import { IconButton, Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { TableLineageSourceListModel } from '../../../../api';
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

const HEADER_ELEMENTS = [
  { label: 'Source connection', key: 'source_connection' },
  { label: 'Source schema', key: 'source_schema' },
  { label: 'Source table', key: 'source_table' },
  { label: 'Action', key: 'action' }
];

export default function SourceTablesTable({
  connection: parentConnection,
  schema: parentSchema,
  table: parentTable,
  setSourceTableEdit,
  showHeader: showHeader = true
}: {
  connection?: string;
  schema?: string;
  table?: string;
  setSourceTableEdit?: (
    obj: { connection: string; schema: string; table: string } | null
  ) => void;
  showHeader?: boolean;
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
    TableLineageSourceListModel[]
  >([]);
  const [expandedLineage, setExpandedLineage] = useState<
    { connection: string; schema: string; table: string }[] | null
  >(null);
  const [tables, setTables] = useState<TableLineageSourceListModel[]>([]);
  const [loading, setLoading] = useState(false);

  const getTables = async () => {
    setLoading(true);
    DataLineageApiClient.getTableSourceTables(connection, schema, table)
      .then((res) => {
        setLoading(false);
        setTables(res.data);
        refetchTables(res.data);
      })
      .finally(() => setLoading(false));
  };

  const refetchTables = (tables?: TableLineageSourceListModel[]) => {
    const shouldRefetch = tables?.some(
      (table) => !table?.source_table_data_quality_status
    );

    if (shouldRefetch) {
      setTimeout(() => {
        getTables();
      }, 5000);
    }
  };

  useEffect(() => {
    getTables();
  }, [connection, schema, table]);

  const handleSort = (elem: { label: string; key: string }, index: number) => {
    const newDir = dir === 'asc' ? 'desc' : 'asc';
    setDisplayedTables(
      sortPatterns(
        tables,
        elem.key as keyof TableLineageSourceListModel,
        newDir
      )
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
        (t) =>
          !(
            t.source_connection === source?.connection &&
            t.source_schema === source?.schema &&
            t.source_table === source?.table
          )
      );
      setDisplayedTables(newTables);
    });
  };

  const dispatch = useActionDispatch();
  const history = useHistory();

  const goTable = (dataLineage: TableLineageSourceListModel) => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      dataLineage.source_connection ?? '',
      dataLineage.source_schema ?? '',
      dataLineage.source_table ?? '',
      'detail'
    );
    const value = ROUTES.TABLE_LEVEL_VALUE(
      CheckTypes.SOURCES,
      dataLineage.source_connection ?? '',
      dataLineage.source_schema ?? '',
      dataLineage.source_table ?? ''
    );
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value,
        label: dataLineage.source_table ?? '',
        state: {}
      })
    );
    history.push(url);
  };

  const onChangeExpandedLineage = (lineage: TableLineageSourceListModel) => {
    const newExpandedLineage = [...(expandedLineage ?? [])];
    if (
      newExpandedLineage.find(
        (expandedLiceage) =>
          expandedLiceage.connection === lineage.source_connection &&
          expandedLiceage.schema === lineage.source_schema &&
          expandedLiceage.table === lineage.source_table
      )
    ) {
      newExpandedLineage.splice(
        newExpandedLineage.findIndex(
          (expandedLiceage) =>
            expandedLiceage.connection === lineage.source_connection &&
            expandedLiceage.schema === lineage.source_schema &&
            expandedLiceage.table === lineage.source_table
        ),
        1
      );
    } else {
      newExpandedLineage.push({
        connection: lineage.source_connection ?? '',
        schema: lineage.source_schema ?? '',
        table: lineage.source_table ?? ''
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
      <table className="text-sm w-full">
        {showHeader && (
          <thead>
            <tr>
              <th></th>
              <th></th>
              {HEADER_ELEMENTS.map((elem, index) => {
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
                          {!(indexSortingElement === index && dir === 'asc') ? (
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
              })}
            </tr>
          </thead>
        )}
        <tbody className={clsx('', showHeader && 'border-t border-gray-100')}>
          {(tables.length > 25 ? displayedTables : tables).map(
            (table, index) => (
              <React.Fragment key={index}>
                <tr className="h-10">
                  <td
                    onClick={() => onChangeExpandedLineage(table)}
                    className="cursor-pointer"
                  >
                    {table.source_table_data_quality_status && (
                      <div className="pl-2">
                        {expandedLineage?.find(
                          (lineage) =>
                            lineage.connection === table.source_connection &&
                            lineage.schema === table.source_schema &&
                            lineage.table === table.source_table
                        ) ? (
                          <SvgIcon name="chevron-down" className="w-4" />
                        ) : (
                          <SvgIcon name="chevron-right" className="w-4" />
                        )}
                      </div>
                    )}
                  </td>
                  <td>
                    <div className="flex items-center gap-x-2 !w-25 !max-w-25">
                      {table.source_table_data_quality_status ? (
                        <QualityDimensionStatuses
                          dimensions={
                            table.source_table_data_quality_status?.dimensions
                          }
                        />
                      ) : (
                        <SvgIcon name="hourglass" className="w-4 h-4" />
                      )}
                      {!table.source_table_data_quality_status?.table_exist && (
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
                    <div className="!w-40 !max-w-40 truncate">
                      {table.source_connection}
                    </div>
                  </td>
                  <td className="px-4">
                    <div className="!w-40 !max-w-40 truncate">
                      {table.source_schema}
                    </div>
                  </td>
                  <td className="px-4">
                    <div className="!w-40 !max-w-40 truncate">
                      {table.source_table}
                    </div>
                  </td>
                  {setSourceTableEdit && (
                    <td className="px-4">
                      <div className="flex items-center gap-x-4">
                        {table.source_table_data_quality_status?.table_exist ? (
                          <IconButton
                            ripple={false}
                            onClick={() =>
                              setSourceTableEdit &&
                              setSourceTableEdit({
                                connection: table.source_connection ?? '',
                                schema: table.source_schema ?? '',
                                table: table.source_table ?? ''
                              })
                            }
                            size="sm"
                            color="teal"
                            className={clsx(
                              '!shadow-none hover:!shadow-none hover:bg-[#028770]'
                            )}
                          >
                            <SvgIcon name="edit" className="w-4" />
                          </IconButton>
                        ) : (
                          <div className="w-8" />
                        )}
                        <IconButton
                          onClick={() =>
                            setSorceTableToDelete({
                              connection: table.source_connection ?? '',
                              schema: table.source_schema ?? '',
                              table: table.source_table ?? ''
                            })
                          }
                          size="sm"
                          color="teal"
                          className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                        >
                          <SvgIcon name="delete" className="w-4" />
                        </IconButton>
                        {table.source_table_data_quality_status
                          ?.table_exist && (
                          <IconButton
                            onClick={() => goTable(table)}
                            size="sm"
                            color="teal"
                            className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                          >
                            <SvgIcon
                              name="data_sources_white"
                              className="w-5"
                            />
                          </IconButton>
                        )}
                      </div>
                    </td>
                  )}
                </tr>
                {expandedLineage?.find(
                  (lineage) =>
                    lineage.connection === table.source_connection &&
                    lineage.schema === table.source_schema &&
                    lineage.table === table.source_table &&
                    table.source_table_data_quality_status?.table_exist
                ) && (
                  <tr>
                    <td colSpan={5} className="pl-4 pt-4">
                      <SourceTablesTable
                        connection={table.source_connection}
                        schema={table.source_schema}
                        table={table.source_table}
                        showHeader={false}
                      />
                    </td>
                  </tr>
                )}
              </React.Fragment>
            )
          )}
        </tbody>
      </table>

      <ConfirmDialog
        open={!!sorceTableToDelete}
        onConfirm={async () => {
          handleDeleteSourceTable(sorceTableToDelete);
          setSorceTableToDelete(null);
        }}
        onClose={() => setSorceTableToDelete(null)}
        message={`Are you sure you want to delete this data lineage?`}
      />
      {tables.length > 25 && (
        <div className="px-4">
          <ClientSidePagination
            items={tables}
            onChangeItems={setDisplayedTables}
            className="pl-0 !w-full pr-4"
          />
        </div>
      )}
    </>
  );
}
