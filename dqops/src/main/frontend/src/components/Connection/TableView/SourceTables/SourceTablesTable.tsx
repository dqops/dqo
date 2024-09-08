import React, { useState } from 'react';
import { TableLineageSourceListModel } from '../../../../api';
import { CheckTypes } from '../../../../shared/routes';
import { sortPatterns, useDecodedParams } from '../../../../utils';
import ConfirmDialog from '../../../CustomTree/ConfirmDialog';
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
  tables,
  onChange,
  loading
}: {
  tables: TableLineageSourceListModel[];
  onChange: (tables: TableLineageSourceListModel[]) => void;
  loading: boolean;
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
  const [notificationPatternDelete, setPatternDelete] = useState('');
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

  const handleDeletePattern = (tableName: string) => {
    // DataLineageApiClient.deleteTableSourceTable().then(() => {
    //   onChange(
    //     filteredNotificationsConfigurations.filter(
    //       (pattern) => pattern.name !== patternName
    //     )
    //   );
    // });
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
      <table>
        <thead>
          <tr>
            {HEADER_ELEMENTS.map((elem, index) => (
              <th key={index} onClick={() => handleSort(elem, index)}>
                <div className="flex items-center gap-x-1">
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
        <tbody>
          {displayedTables.map((table, index) => (
            <tr key={index}>
              <td>{table.source_connection}</td>
              <td>{table.source_schema}</td>
              <td>{table.source_table}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <ConfirmDialog
        open={notificationPatternDelete.length > 0}
        onConfirm={async () => {
          handleDeletePattern(notificationPatternDelete);
          setPatternDelete('');
        }}
        onClose={() => setPatternDelete('')}
        message={`Are you sure you want to delete the ${notificationPatternDelete} notification filter?`}
      />
      <ClientSidePagination
        items={displayedTables}
        onChangeItems={setDisplayedTables}
        className="pl-0 !w-full pr-4"
      />
    </>
  );
}
