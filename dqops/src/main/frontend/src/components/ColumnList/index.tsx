import clsx from 'clsx';
import React from 'react';
import { ColumnListModel, LabelModel } from '../../api';
import { CheckTypes } from '../../shared/routes';
import { limitTextLength, useDecodedParams } from '../../utils';
import SectionWrapper from '../Dashboard/SectionWrapper';
import { Pagination } from '../Pagination';

import Loader from '../Loader';
import ColumnListItem from './ColumnListItem';
import renderItem from './renderItem';

type TButtonTabs = {
  label: string;
  value: string;
  toRotate?: boolean | undefined;
  className?: string;
};

type TColumnWithSchema = ColumnListModel & {
  schema?: string;
  table_name?: string;
  column_type?: string;
};

const constantHeaderItems: TButtonTabs[] = [
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
    label: 'Labels',
    value: 'labels'
  }
];

type TColumnListProps = {
  columns: TColumnWithSchema[];
  filters: any;
  onChangeFilters: (filters: any) => void;
  labels: TLabel[];
  onChangeLabels: (index: number) => void;
  loading: boolean;
};

type TLabel = LabelModel & { clicked: boolean };

function ColumnList({
  columns,
  filters,
  onChangeFilters,
  labels,
  onChangeLabels,
  loading
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

  const getDimensionKey = () => {
    if (loading || columns.length == 0) {
      return [];
    }
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
  const getBasicDimensions = () => {
    if (loading || columns.length == 0) {
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

    loading || columns.length == 0
      ? undefined
      : {
          label: 'Data Quality KPI',
          value: 'data-quality-kpi',
          toRotate: true,
          className: 'tracking-wider'
        },

    ...getBasicDimensions().map((x) => ({
      label: x,
      value: x,
      toRotate: true,
      className: 'tracking-wider font-normal'
    })),

    ...getDimensionKey().map((x) => ({
      label: x,
      value: x,
      toRotate: true,
      className: 'tracking-wider font-normal'
    })),
    {
      label: 'Actions',
      value: 'actions'
    }
  ];

  const isEnd = columns.length < filters.pageSize;

  return (
    <div className="bg-white py-2">
      <div className="flex">
        <div className="w-[200px] mr-3">
          <SectionWrapper
            title="Filter by labels"
            className="text-xs w-[200px] mx-4 mb-4 mt-2 "
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
                <span>{limitTextLength(label.label, 18)}</span>(
                {label.labels_count})
              </div>
            ))}
          </SectionWrapper>
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
                {columns.map((item, index) => (
                  <ColumnListItem
                    key={index}
                    item={item}
                    dimensionKeys={getDimensionKey()}
                  />
                ))}
              </tbody>
            )}
          </table>
          <div className="px-3 my-5 flex justify-end z-[100]">
            {columns.length >= 10 && (
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
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default ColumnList;
