import React from 'react';

import clsx from 'clsx';

import SvgIcon from '../SvgIcon';

interface Column {
  label?: string;
  value: string;
  className?: string;
  render?: any;
  header?: any;
}

export interface TableProps {
  columns: Column[];
  data: any[];
  className?: string;
  emptyMessage?: string;
  onClickRow?: (item: any) => void;
  loading?: boolean;
  getRowClass?: (item: any) => string;
}

export const Table: React.FC<TableProps> = ({
  columns,
  data,
  className,
  emptyMessage = 'No Data',
  onClickRow,
  loading,
  getRowClass
}) => {
  return (
    <div className="w-full">
      <table className={className}>
        <thead>
          <tr>
            {columns.map((column) => (
              <th
                key={column.value}
                className={`text-left px-2 pt-2 pb-2 text-base font-semibold min-w-20 pr-4 ${column.className}`}
              >
                {column.header ? (
                  <>{column.header(column.label)}</>
                ) : (
                  <div>{column.label}</div>
                )}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {loading ? (
            <tr>
              <td
                colSpan={columns.length}
                className="py-5 min-h-80 text-base text-center"
              >
                <div className="min-h-80 flex items-center justify-center">
                  <SvgIcon name="spinner" className="mr-2" />
                  <span className="text-sm">Loading...</span>
                </div>
              </td>
            </tr>
          ) : (
            <>
              {data.length === 0 ? (
                <tr>
                  <td
                    colSpan={columns.length}
                    className="py-5 text-base text-center"
                  >
                    <div className="min-h-80 flex items-center justify-center">
                      {emptyMessage}
                    </div>
                  </td>
                </tr>
              ) : (
                <>
                  {data.map((item, index) => (
                    <tr
                      data-testid="table-row"
                      key={index}
                      onClick={() => onClickRow && onClickRow(item)}
                      className="border-b border-gray-150 last:border-b-0"
                    >
                      {columns.map((column) => (
                        <td
                          key={column.value}
                          className={clsx(
                            'text-left px-2 py-4',
                            column.className,
                            getRowClass ? getRowClass(item) : ''
                          )}
                        >
                          {column.render
                            ? column.render(item[column.value], item, index)
                            : column.value === 'timePeriod' ||
                              column.value === 'executedAt'
                            ? (item[column.value] as string).replace(/Z/gi, '')
                            : item[column.value]}
                        </td>
                      ))}
                    </tr>
                  ))}
                </>
              )}
            </>
          )}
        </tbody>
      </table>
    </div>
  );
};
