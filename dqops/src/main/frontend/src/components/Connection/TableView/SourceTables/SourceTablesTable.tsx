import { IconButton } from '@material-tailwind/react';
import clsx from 'clsx';
import React, { useState } from 'react';
import { TableLineageSourceListModel } from '../../../../api';
import { DataLineageApiClient } from '../../../../services/apiClient';
import { CheckTypes } from '../../../../shared/routes';
import { sortPatterns, useDecodedParams } from '../../../../utils';
import ConfirmDialog from '../../../CustomTree/ConfirmDialog';
import Loader from '../../../Loader';
import SvgIcon from '../../../SvgIcon';
const HEADER_ELEMENTS = [
  { label: 'Source connection', key: 'source_connection' },
  { label: 'Source schema', key: 'source_schema' },
  { label: 'Source table', key: 'source_table' },
  { label: 'Action', key: 'action' }
];

export default function SourceTablesTable({
  tables,
  onChange,
  loading,
  setSourceTableEdit
}: {
  tables: TableLineageSourceListModel[];
  onChange: (tables: TableLineageSourceListModel[]) => void;
  loading: boolean;
  setSourceTableEdit: (
    obj: { connection: string; schema: string; table: string } | null
  ) => void;
}) {
  const {
    connection,
    schema,
    table,
    checkTypes
  }: {
    connection: string;
    schema: string;
    table: string;
    checkTypes: CheckTypes;
  } = useDecodedParams();
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

  const handleSort = (elem: { label: string; key: string }, index: number) => {
    const newDir = dir === 'asc' ? 'desc' : 'asc';
    onChange(
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
    source: {
      connection: string;
      schema: string;
      table: string;
    } | null
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
            table.source_connection === source?.connection &&
            table.source_schema === source?.schema &&
            table.source_table === source?.table
          )
      );
      onChange(newTables);
    });
  };

  if (loading) {
    return (
      <div className="w-full h-screen flex justify-center items-center">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }

  //   const sourceTables = useMemo(() => [...tables], []);
  return (
    <>
      <table className="text-sm">
        <thead>
          <tr>
            {HEADER_ELEMENTS.map((elem, index) => (
              <th key={index} onClick={() => handleSort(elem, index)}>
                <div className="flex items-center gap-x-1 px-4">
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
            ))}
          </tr>
        </thead>
        <div className="w-full h-1.5"></div>
        <tbody className="border-t border-gray-100 mt-1">
          {tables.map((table, index) => (
            <tr key={index}>
              <td className="max-w-60 truncate px-4">
                {table.source_connection}
              </td>
              <td className="max-w-60 truncate px-4">{table.source_schema}</td>
              <td className="max-w-100 truncate px-4">{table.source_table}</td>
              <td>
                {' '}
                <div className="flex items-center gap-x-4 my-0.5">
                  <IconButton
                    ripple={false}
                    onClick={() =>
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
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <ConfirmDialog
        open={sorceTableToDelete !== null}
        onConfirm={async () => {
          handleDeleteSourceTable(sorceTableToDelete);
          setSorceTableToDelete(null);
        }}
        onClose={() => setSorceTableToDelete(null)}
        message={`Are you sure you want to delete this source table?`}
      />
      {/* <ClientSidePagination
        items={sourceTables}
        onChangeItems={setDisplayedTables}
        className="pl-0 !w-full pr-4"
      /> */}
    </>
  );
}
