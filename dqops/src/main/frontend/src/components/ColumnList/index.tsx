import clsx from 'clsx';
import React, { useState } from 'react';
import { ColumnListModel, LabelModel } from '../../api';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import SectionWrapper from '../Dashboard/SectionWrapper';
import { Pagination } from '../Pagination';
import SvgIcon from '../SvgIcon';
import ColumnListItem from './ColumnListItem';
function getValueForKey<T>(obj: T, key: string): string | undefined {
  const keys = key.split('.');
  let value: any = obj;

  for (const k of keys) {
    value = value?.[k];
    if (value === undefined) {
      break;
    }
  }

  return value?.toString();
}

type TButtonTabs = {
  label: string;
  value: string;
  sortable?: boolean;
  toRotate?: boolean | undefined;
};

type TColumnWithSchema = ColumnListModel & {
  schema?: string;
  table_name?: string;
  column_type?: string;
};

const headeritems: TButtonTabs[] = [
  {
    label: 'Table',
    value: 'table_name'
  },
  {
    label: 'Column',
    value: 'column_name'
  },
  {
    label: 'Column type',
    value: 'column_type'
  },
  {
    label: 'Stage',
    value: 'stage'
  },
  {
    label: 'Labels',
    value: 'labels'
  },
  { label: 'Data Quality KPI', value: 'data-quality-kpi', sortable: false }
];

type TColumnListProps = {
  columns: TColumnWithSchema[];
  setColumns: any;
  filters: any;
  onChangeFilters: (filters: any) => void;
  labels: TLabel[];
  onChangeLabels: (index: number) => void;
};

type TLabel = LabelModel & { clicked: boolean };

function ColumnList({
  columns,
  setColumns,
  filters,
  onChangeFilters,
  labels,
  onChangeLabels
}: TColumnListProps) {
  const {
    checkTypes,
    connection,
    schema
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
  } = useDecodedParams();
  const [sortingDir, setSortingDir] = useState<'asc' | 'desc'>('asc');

  const renderItem = (
    label: string,
    key: string,
    sortable?: boolean,
    toRotate?: boolean
  ) => {
    const sortTables = (key: string): void => {
      setColumns((prev: any) => {
        const array = [...prev];
        array.sort((a, b) => {
          const valueA = getValueForKey(a, key);
          const valueB = getValueForKey(b, key);

          return sortingDir === 'asc'
            ? (valueA || '').localeCompare(valueB || '')
            : (valueB || '').localeCompare(valueA || '');
        });

        setSortingDir((prev) => (prev === 'asc' ? 'desc' : 'asc'));
        return array;
      });
    };
    return (
      <th
        className="px-4 cursor-pointer"
        onClick={() => sortable !== false && sortTables(key)}
        key={key}
      >
        <div className="flex text-sm items-center relative mt-30">
          <span className={clsx(toRotate ? ' z-9' : '')}>{label}</span>
          {sortable !== false && (
            <div className="flex flex-col items-center">
              <SvgIcon name="chevron-up" className="w-3 h-2" />
              <SvgIcon name="chevron-down" className="w-3 h-2" />
            </div>
          )}
        </div>
      </th>
    );
  };

  const getDimensionKey = () => {
    const uniqueDimensions: string[] = [];
    columns.forEach((column) => {
      Object.keys(column.data_quality_status?.dimensions ?? {}).forEach((x) => {
        if (!uniqueDimensions.includes(x) && !basicDimensionTypes.includes(x)) {
          uniqueDimensions.push(x);
        }
      });
    });

    return uniqueDimensions;
  };

  const basicDimensionTypes = ['Completeness', 'Validity', 'Consistency'];
  const headerItems = [
    checkTypes && connection
      ? undefined
      : {
          label: 'Connection',
          value: 'connection_name'
        },
    checkTypes && connection && schema
      ? undefined
      : {
          label: 'Schema',
          value: 'schema'
        },

    ...headeritems,

    ...basicDimensionTypes.map((x) => ({
      label: x,
      value: x,
      sortable: false,
      toRotate: true
    })),

    ...getDimensionKey().map((x) => ({
      label: x,
      value: x,
      sortable: false,
      toRotate: true
    })),
    {
      label: 'Actions',
      value: 'actions',
      sortable: false
    }
  ];

  const prepareLabel = (label: string | undefined) => {
    if (!label) return;
    if (label.length > 20) {
      return label.slice(0, 20) + '...';
    }
    return label;
  };

  const isEnd = columns.length < filters.pageSize;

  return (
    <div className="bg-white">
      <div className="flex">
        <div className="w-[280px]">
          <SectionWrapper
            title="Filter by labels"
            className="text-sm w-[250px] mx-4 mb-4 mt-6 "
          >
            {labels.map((label, index) => (
              <div
                className={clsx(
                  'flex gap-2 mb-2 cursor-pointer whitespace-normal break-all',
                  {
                    'font-bold text-gray-700': label.clicked,
                    'text-gray-500': !label.clicked
                  }
                )}
                key={index}
                onClick={() => onChangeLabels(index)}
              >
                <span>{prepareLabel(label.label)}</span>({label.labels_count})
              </div>
            ))}
          </SectionWrapper>
        </div>

        <div className="overflow-x-auto">
          <table>
            <thead>
              <tr className="mb-2">
                {headerItems.map(
                  (item) =>
                    item?.label &&
                    item.value &&
                    renderItem(
                      item.label,
                      item.value,
                      item.sortable,
                      item.toRotate
                    )
                )}
              </tr>
            </thead>
            <tbody>
              {columns.map((item, index) => (
                <ColumnListItem
                  key={index}
                  item={item}
                  dimensionKeys={getDimensionKey()}
                />
              ))}
            </tbody>
          </table>
        </div>
      </div>
      <div className="px-4 my-5">
        <Pagination
          page={filters.page || 1}
          pageSize={filters.pageSize || 50}
          isEnd={isEnd}
          totalPages={10}
          onChange={(page, pageSize) =>
            onChangeFilters({
              page,
              pageSize
            })
          }
        />
      </div>
    </div>
  );
}

export default ColumnList;
