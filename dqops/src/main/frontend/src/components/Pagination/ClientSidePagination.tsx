import React from 'react';
import { Pagination } from '.';

interface IClientSidePaginationProps<T> {
  items: T[];
  onChangeItems: (items: T[]) => void;
}

export default function ClientSidePagination<T>({
  items,
  onChangeItems
}: IClientSidePaginationProps<T>) {
  const [filteredItems, setFilteredItems] = React.useState<T[]>(items);
  const [isEnd, setIsEnd] = React.useState(false);
  const [filters, setFilters] = React.useState({
    page: 1,
    pageSize: 50
  });

  const onChangeFilters = (newFilters: any) => {
    setFilters(newFilters);
  };

  return (
    <div>
      {' '}
      <Pagination
        page={filters.page || 1}
        pageSize={filters.pageSize || 50}
        isEnd={isEnd}
        totalPages={10}
        onChange={(page, pageSize) => {
          onChangeItems(
            filteredItems.slice((page - 1) * pageSize, page * pageSize)
          );
          onChangeFilters({
            page,
            pageSize
          });
        }}
      />
    </div>
  );
}
