import React from 'react';
import { LabelModel, TableListModel } from '../../api';
import SchemaTableItem from '../../pages/Schema/SchemaTableItem';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import renderItem from '../ColumnList/renderItem';
import LabelsSectionWrapper from '../LabelsSectionWrapper/LabelsSectionWrapper';
import Loader from '../Loader';
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
  loading: boolean;
};

type TLabel = LabelModel & { clicked: boolean };

export default function index({
  tables,
  filters,
  onChangeFilters,
  labels,
  onChangeLabels,
  loading
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
        <div className="w-[200px]">
          <LabelsSectionWrapper
            labels={labels}
            onChangeLabels={onChangeLabels}
            className="text-xs w-[180px] mx-4 mb-4 mt-6 "
          />
        </div>
        <div className="overflow-x-auto">
          <table className="absolute top-25">
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
            {loading ? (
              <div className="ml-5 flex items-start justify-normal">
                <Loader
                  isFull={false}
                  className="w-8 h-8 fill-green-700 mt-5"
                />
              </div>
            ) : (
              <tbody>
                {tables.map((item, index) => (
                  <SchemaTableItem
                    key={index}
                    item={item}
                    dimensionKeys={getDimensionKey()}
                  />
                ))}
              </tbody>
            )}
            <div className="px-4 my-5 pb-6 flex justify-end">
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
          </table>
        </div>
      </div>
    </div>
  );
}
