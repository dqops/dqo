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
  toRotate?: boolean | undefined;
  className?: string;
};

type TTableWithSchema = TableListModel & { schema?: string };

const constantHeaderItems: TButtonTabs[] = [
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
  }
];

type TTableListProps = {
  tables: TTableWithSchema[];
  filters: any;
  onChangeFilters: (filters: any) => void;
  labels: TLabel[];
  onChangeLabels: (index: number) => void;
  loading: boolean;
  showDimensions?: boolean;
};

type TLabel = LabelModel & { clicked: boolean };

export default function index({
  tables,
  filters,
  onChangeFilters,
  labels,
  onChangeLabels,
  loading,
  showDimensions
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
    if (loading || tables.length == 0) {
      return [];
    }
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

  const getBasicDimensions = () => {
    if (loading || tables.length == 0) {
      return [];
    }
    return basicDimensionTypes;
  };

  const headerItems: (TButtonTabs | undefined)[] = [
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

    ...constantHeaderItems,

    loading || tables.length == 0 || !showDimensions
      ? undefined
      : {
          label: 'Data quality KPI',
          value: 'data-quality-kpi',
          toRotate: true,
          className: 'tracking-wider'
        },
    ...(showDimensions
      ? getBasicDimensions().map((x) => ({
          label: x,
          value: x,
          toRotate: true,
          className: 'tracking-wider font-normal'
        }))
      : [
          {
            label: 'Rows',
            value: 'rows',
            toRotate: false,
            className: 'font-normal flex !items-start !text-left'
          },
          {
            label: 'Delays',
            value: 'delays',
            toRotate: false,
            className: ' font-normal  flex !items-start !text-left'
          }
        ]),
    ...(showDimensions
      ? getDimensionKey().map((x) => ({
          label: x,
          value: x,
          toRotate: true,
          className: 'tracking-wider font-normal'
        }))
      : []),
    {
      label: 'Actions',
      value: 'actions'
    }
  ];

  const isEnd = tables.length < filters.pageSize;

  const maxRowCount = Math.max(
    ...tables.map((x) => x.data_quality_status?.total_row_count ?? 0)
  );

  const maxDelay = Math.max(
    ...tables.map((x) => x.data_quality_status?.data_freshness_delay_days ?? 0)
  );

  console.log(maxDelay, maxRowCount);

  return (
    <div className="bg-white py-2">
      <div className="flex">
        <div className="w-[200px]">
          <LabelsSectionWrapper
            labels={labels}
            onChangeLabels={onChangeLabels}
            className="text-xs w-[180px] mx-4 mb-4 mt-2 "
          />
        </div>
        <div className="">
          <table className="">
            <thead>
              <tr className="mb-2">
                {headerItems.map(
                  (item) =>
                    item?.label &&
                    item.value &&
                    renderItem(
                      item.label,
                      item.value,
                      item.toRotate,
                      item.className
                    )
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
                    showDimensions={showDimensions}
                    maxRowCount={maxRowCount}
                    maxDelay={maxDelay}
                  />
                ))}
              </tbody>
            )}
          </table>
          <div className="px-3 my-5 flex justify-end">
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
      </div>
    </div>
  );
}
