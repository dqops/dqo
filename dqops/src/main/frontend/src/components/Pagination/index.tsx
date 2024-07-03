import { IconButton } from '@material-tailwind/react';
import React from 'react';
import Select from '../Select';
import SvgIcon from '../SvgIcon';

const pageSizeOptions = [
  {
    label: 10,
    value: 10
  },
  {
    label: 20,
    value: 20
  },
  {
    label: 50,
    value: 50
  }
];

type PaginationProps = {
  page: number;
  pageSize: number;
  onChange: (page: number, pageSize: number) => void;
  totalPages?: number;
  isEnd?: boolean;
  selectMenuClassName?: string;
};

export const Pagination = ({
  page,
  pageSize,
  onChange,
  isEnd,
  selectMenuClassName
}: PaginationProps) => {
  const onChangePageSize = (value: number) => {
    onChange(1, value);
  };

  const onChangePage = (value: number) => {
    onChange(value, pageSize);
  };

  const onPrev = () => {
    if (page > 1) {
      onChangePage(page - 1);
    }
  };
  const onNext = () => {
    onChangePage(page + 1);
  };

  return (
    <div className="flex items-center gap-4 justify-between text-sm">
      <div className="flex items-center gap-4">
        <span>Rows per page</span>
        <Select
          options={pageSizeOptions}
          value={pageSize}
          onChange={onChangePageSize}
          menuClassName={'top-[-120px]'}
        />
      </div>
      <div className="flex items-center gap-2">
        <IconButton
          size="sm"
          className="w-10 h-10 !shadow-none"
          color="teal"
          onClick={onPrev}
          disabled={page === 1}
        >
          <SvgIcon name="chevron-left" className="w-4 text-white" />
        </IconButton>
        <IconButton
          size="sm"
          className="w-10 h-10 !shadow-none"
          color="teal"
          disabled={true}
        >
          {page}
        </IconButton>
        <IconButton
          size="sm"
          className="w-10 h-10 !shadow-none"
          color="teal"
          onClick={onNext}
          disabled={isEnd}
        >
          <SvgIcon name="chevron-right" className="w-4 text-white" />
        </IconButton>
      </div>
    </div>
  );
};
