import React, { useEffect, useState } from 'react';
import { TableListModel } from '../../api';
import TableList from '../../components/TableList';
import { TableApiClient } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';

export const SchemaTables = () => {
  const {
    checkTypes,
    connection,
    schema
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
  } = useDecodedParams();
  const [tables, setTables] = useState<TableListModel[]>([]);
  const [filters, setFilters] = useState<any>({ page: 1, limit: 50 });

  const onChangeFilters = (obj: Partial<any>) => {
    setFilters((prev: any) => ({
      ...prev,
      ...obj
    }));
  };

  const getTables = async () => {
    return TableApiClient.getTables(
      connection,
      schema,
      [],
      filters.page,
      filters.pageSize,
      filters.filter,
      checkTypes === CheckTypes.SOURCES ? CheckTypes.PROFILING : checkTypes
    ).then((res) => {
      setTables(res.data);
      return res;
    });
  };

  const refetchTables = (tables?: TableListModel[]) => {
    console.log(tables);
    tables?.forEach((table) => {
      if (!table.data_quality_status?.dimensions) {
        setTimeout(() => {
          getTables();
        }, 5000);
      }
    });
  };

  useEffect(() => {
    getTables().then((res) => {
      refetchTables(res.data);
    });
  }, [schema, connection, filters]);

  return (
    <>
      {/* <div className="flex items-center gap-x-4 mb-4 px-4">
        <Input
          label="Connection name"
          value={filters.connection}
          onChange={(e) => onChangeFilters({ connection: e.target.value })}
        />
        <Input
          label="Schema name"
          value={filters.schema}
          onChange={(e) => onChangeFilters({ schema: e.target.value })}
        />
        <Input
          label="Table name"
          value={filters.filter}
          onChange={(e) => onChangeFilters({ filter: e.target.value })}
        />
        <Button
          label="Search"
          onClick={getTables}
          color="primary"
          className="mt-"
        />
      </div> */}
      <TableList
        tables={tables}
        setTables={setTables}
        filters={filters}
        onChangeFilters={onChangeFilters}
      />
    </>
  );
};
