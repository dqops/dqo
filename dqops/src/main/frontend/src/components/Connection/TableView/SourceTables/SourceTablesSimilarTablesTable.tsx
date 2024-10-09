import { IconButton, Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import { SimilarTableModel } from '../../../../api';
import { TableApiClient } from '../../../../services/apiClient';
import { sortPatterns, useDecodedParams } from '../../../../utils';
import Loader from '../../../Loader';
import ClientSidePagination from '../../../Pagination/ClientSidePagination';
import SvgIcon from '../../../SvgIcon';

const SOURCE_HEADER_ELEMENTS = [
  { label: 'Source connection', key: 'connection_name' },
  { label: 'Source schema', key: 'schema_name' },
  { label: 'Source table', key: 'table_name' },
  { label: 'Action', key: 'action' }
];

const TARGET_HEADER_ELEMENTS = [
  { label: 'Target connection', key: 'connection_name' },
  { label: 'Target schema', key: 'schema_name' },
  { label: 'Target table', key: 'table_name' },
  { label: 'Action', key: 'action' }
];

export default function SourceTablesSimilarTablesTable({
  isTarget,
  setSourceTableEdit,
  existingTables
}: {
  isTarget?: boolean;
  setSourceTableEdit: (
    obj: { connection: string; schema: string; table: string } | null
  ) => void;
  existingTables: { connection: string; schema: string; table: string }[];
}) {
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } = useDecodedParams();
  const [loading, setLoading] = useState(true);
  const [dir, setDir] = useState<'asc' | 'desc'>('asc');
  const [tables, setTables] = useState<Array<SimilarTableModel>>([]);
  const [displayedTables, setDisplayedTables] = useState<SimilarTableModel[]>(
    []
  );
  const [indexSortingElement, setIndexSortingElement] = useState(1);

  const handleSort = (elem: { label: string; key: string }, index: number) => {
    const newDir = dir === 'asc' ? 'desc' : 'asc';
    setDisplayedTables(
      sortPatterns(tables, elem.key as keyof SimilarTableModel, newDir)
    );
    setDir(newDir);
    setIndexSortingElement(index);
  };

  useEffect(() => {
    setLoading(true);
    TableApiClient.findSimilarTables(connection, schema, table)
      .then((res) =>
        setTables(
          (res.data ?? []).filter(
            (table) =>
              !existingTables.find(
                (existingTable) =>
                  existingTable.connection === table.connection_name &&
                  existingTable.schema === table.schema_name &&
                  existingTable.table === table.table_name
              )
          )
        )
      )
      .catch((err) => console.error(err))
      .finally(() => setLoading(false));
  }, [connection, schema, table]);

  if (loading) {
    return (
      <div className="w-full h-screen flex justify-center items-center">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }
  console.log('tables', existingTables);

  return (
    <div className="text-sm">
      <thead>
        <tr>
          {(isTarget ? TARGET_HEADER_ELEMENTS : SOURCE_HEADER_ELEMENTS).map(
            (elem, index) => {
              return (
                <th key={index} onClick={() => handleSort(elem, index)}>
                  <div
                    className={clsx(
                      'flex items-center gap-x-1 px-3.5',
                      elem.key === 'action' && 'ml-10.5'
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
                        {!(indexSortingElement === index && dir === 'desc') ? (
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
      <tbody className="border-t border-gray-100">
        {displayedTables && tables.length === 0 && (
          <tr className="!h-6">
            <td>This table has no {isTarget ? 'target' : 'source'} tables.</td>
          </tr>
        )}
        {(displayedTables ?? tables).map((table, index) => (
          <React.Fragment key={index}>
            <tr className="!h-6">
              <td className="px-4">
                <div className="min-w-20 !max-w-100 truncate">
                  {table.connection_name}
                </div>
              </td>
              <td className="px-4">
                <div className="min-w-20 !max-w-100 truncate">
                  {table.schema_name}
                </div>
              </td>
              <td className="px-4">
                <div className="min-w-20 !max-w-100 truncate">
                  {table.table_name}
                </div>
              </td>

              <td className="px-4">
                <div className="flex items-center gap-x-4">
                  <IconButton
                    ripple={false}
                    onClick={() =>
                      setSourceTableEdit({
                        connection: table.connection_name ?? '',
                        schema: table.schema_name ?? '',
                        table: table.table_name ?? ''
                      })
                    }
                    size="sm"
                    color="teal"
                    className={clsx(
                      '!shadow-none hover:!shadow-none hover:bg-[#028770] ml-12'
                    )}
                  >
                    <Tooltip content="Add table">
                      <div>
                        <SvgIcon name="add" className="w-4" />
                      </div>
                    </Tooltip>
                  </IconButton>
                </div>
              </td>
            </tr>
          </React.Fragment>
        ))}
      </tbody>
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
    </div>
  );
}
