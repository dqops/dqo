import clsx from 'clsx';
import React, { useEffect, useMemo } from 'react';
import { Pagination } from '.';

interface IClientSidePaginationProps<T> {
  items: T[];
  onChangeItems: (items: T[]) => void;
  className?: string;
}

export default function ClientSidePagination<T>({
  items,
  onChangeItems,
  className
}: IClientSidePaginationProps<T>) {
  const [filters, setFilters] = React.useState({
    page: 1,
    pageSize: 1
  });

  const paginatedItems = useMemo(() => {
    const { page, pageSize } = filters;
    return items.slice((page - 1) * pageSize, page * pageSize);
  }, [items, filters]);

  useEffect(() => {
    onChangeItems(paginatedItems);
  }, [paginatedItems, onChangeItems]);

  const onChangeFilters = (newFilters: Partial<typeof filters>) => {
    setFilters((prevFilters) => ({
      ...prevFilters,
      ...newFilters
    }));
  };

  const totalPages = Math.ceil(items.length / filters.pageSize);

  return (
    <div className={clsx('px-4 py-4', className)}>
      <Pagination
        page={filters.page}
        pageSize={filters.pageSize}
        isEnd={filters.page >= totalPages}
        totalPages={totalPages}
        onChange={(page, pageSize) => {
          onChangeFilters({
            page,
            pageSize
          });
        }}
      />
    </div>
  );
}
