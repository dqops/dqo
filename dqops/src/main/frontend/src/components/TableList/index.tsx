import clsx from 'clsx';
import React from 'react';
import { LabelModel, TableListModel } from '../../api';
import SchemaTableItem from '../../pages/Schema/SchemaTableItem';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import LabelsSectionWrapper from '../LabelsSectionWrapper/LabelsSectionWrapper';
import { Pagination } from '../Pagination';

type TButtonTabs = {
  label: string;
  value: string;
  sortable?: boolean;
  toRotate?: boolean | undefined;
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
  filters: any;
  onChangeFilters: (filters: any) => void;
  labels: TLabel[];
  onChangeLabels: (index: number) => void;
};

type TLabel = LabelModel & { clicked: boolean };

export default function index({
  tables,
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

  const renderItem = (label: string, key: string, toRotate?: boolean) => {
    return (
      <th className="px-4" key={key}>
        <div className="flex text-sm items-center relative">
          <span className={clsx(toRotate ? ' z-9' : '')}>{label}</span>
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

  const isEnd = tables.length < filters.pageSize;

  return (
    <div className="bg-white">
      <div className="flex">
        <div className="w-[280px]">
          <LabelsSectionWrapper
            labels={labels}
            onChangeLabels={onChangeLabels}
          />
        </div>
        <div className="overflow-x-auto">
          <table>
            <thead>
              <tr className="mb-2">
                {headerItems.map(
                  (item) =>
                    item?.label &&
                    item.value &&
                    renderItem(item.label, item.value, item.toRotate)
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
      <div className="px-4 my-5 pb-6">
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
