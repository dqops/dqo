import clsx from 'clsx';
import React, { useState } from 'react';
import { LabelModel, TableListModel } from '../../api';
import SchemaTableItem from '../../pages/Schema/SchemaTableItem';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import SectionWrapper from '../Dashboard/SectionWrapper';
import { Pagination } from '../Pagination';
import SvgIcon from '../SvgIcon';

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
};

type TTableWithSchema = TableListModel & { schema?: string };

const headeritems: TButtonTabs[] = [
  {
    label: 'Table',
    value: 'target.table_name'
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

type TTableListProps = {
  tables: TTableWithSchema[];
  setTables: any;
  filters: any;
  onChangeFilters: (filters: any) => void;
  labels: TLabel[];
  onChangeLabels: (index: number) => void;
};

type TLabel = LabelModel & { clicked: boolean };

export default function index({
  tables,
  setTables,
  filters,
  onChangeFilters,
  labels,
  onChangeLabels
}: TTableListProps) {
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

  const renderItem = (label: string, key: string, sortable?: boolean) => {
    const sortTables = (key: string): void => {
      setTables((prev: any) => {
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
        className="px-4 text-left cursor-pointer"
        onClick={() => sortable !== false && sortTables(key)}
        key={key}
      >
        <div className="flex text-sm">
          {label}
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
    tables.forEach((table) => {
      Object.keys(table.data_quality_status?.dimensions ?? {}).forEach((x) => {
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
      sortable: false
    })),

    ...getDimensionKey().map((x) => ({
      label: x,
      value: x,
      sortable: false
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

  const isEnd = tables.length < filters.pageSize;

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
                    renderItem(item.label, item.value, item.sortable)
                )}
              </tr>
            </thead>
            <tbody>
              {tables.map((item, index) => (
                <SchemaTableItem
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
