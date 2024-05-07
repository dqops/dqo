import React, { useEffect, useState } from 'react';
import { LabelModel, TableListModel } from '../../../api';
import { LabelsApiClient, SearchApiClient } from '../../../services/apiClient';
import { CheckTypes } from '../../../shared/routes';
import { useDecodedParams } from '../../../utils';
import Button from '../../Button';
import Input from '../../Input';
import TableList from '../../TableList';
type TLabel = LabelModel & { clicked: boolean };
type TTableWithSchema = TableListModel & { schema?: string };
export default function ConnectionTables() {
  const {
    checkTypes,
    connection
  }: {
    checkTypes: CheckTypes;
    connection: string;
  } = useDecodedParams();
  const [tables, setTables] = useState<TableListModel[]>([]);
  const [filters, setFilters] = useState<any>({
    page: 1,
    pageSize: 50,
    checkType:
      checkTypes == CheckTypes.SOURCES ? CheckTypes.MONITORING : checkTypes
  });
  const [table, setTable] = useState<string>();
  const [schema, setSchema] = useState<string>();
  const [labels, setLabels] = useState<TLabel[]>([]);

  const onChangeFilters = (obj: Partial<any>) => {
    setFilters((prev: any) => ({
      ...prev,
      ...obj
    }));
  };

  const onChangeLabels = (index: number) => {
    const arr = [...labels];
    arr[index] = { ...arr[index], clicked: !arr[index].clicked };

    const filteredlabels: string[] = arr
      .filter((x) => x.clicked && x.label)
      .map((x) => x.label)
      .filter((x): x is string => typeof x === 'string');

    getTables(filteredlabels);
    setLabels(arr);
  };

  const getTables = async (labels: string[] = []) => {
    const addPrefix = (str?: string) => {
      if (!str) return undefined;
      return str.includes('*') || str.length === 0 ? str : '*' + str + '*';
    };
    return SearchApiClient.findTables(
      connection,
      addPrefix(schema),
      addPrefix(table),
      labels,
      filters.page,
      filters.pageSize,
      filters.checkType
    ).then((res) => {
      const arr: TTableWithSchema[] = [];
      res.data.forEach((item) => {
        const jItem = { ...item, schema: item.target?.schema_name };
        arr.push(jItem);
      });
      setTables(arr);
      return arr;
    });
  };

  const refetchTables = (tables?: TableListModel[]) => {
    tables?.forEach((table) => {
      if (!table?.data_quality_status) {
        setTimeout(() => {
          getTables();
        }, 5000);
      }
    });
  };

  useEffect(() => {
    getTables().then((res) => {
      refetchTables(res);
    });
    const getLabels = () => {
      LabelsApiClient.getAllLabelsForTables().then((res) => {
        const array: TLabel[] = res.data.map((item) => {
          return { ...item, clicked: false };
        });
        setLabels(array);
      });
    };
    getLabels();
  }, [connection, filters]);

  return (
    <>
      <div className="flex items-center gap-x-4 mb-4 mt-2 px-4">
        <Input
          label="Schema name"
          value={schema}
          onChange={(e) => setSchema(e.target.value)}
        />
        <Input
          label="Table name"
          value={table}
          onChange={(e) => setTable(e.target.value)}
        />
        <Button
          label="Search"
          onClick={() =>
            getTables(
              labels
                .filter((x) => x.clicked && x.label)
                .map((x) => x.label)
                .filter((x): x is string => typeof x === 'string')
            )
          }
          color="primary"
          className="mt-5"
        />
      </div>
      <TableList
        tables={tables}
        setTables={setTables}
        filters={filters}
        onChangeFilters={onChangeFilters}
        labels={labels}
        onChangeLabels={onChangeLabels}
      />
    </>
  );
}
