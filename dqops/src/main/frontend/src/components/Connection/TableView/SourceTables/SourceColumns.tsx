import React, { useEffect, useState } from 'react';
import {
  TableLineageSourceListModel,
  TableLineageSourceSpec
} from '../../../../api';
import { ColumnApiClient } from '../../../../services/apiClient';
import { CheckTypes } from '../../../../shared/routes';
import { useDecodedParams } from '../../../../utils';
import Select, { Option } from '../../../Select';

export default function SourceColumns({
  dataLineage,
  onChangeDataLineageSpec,
  dataLineageSpec
}: {
  dataLineage: TableLineageSourceListModel;
  onChangeDataLineageSpec: (dataLineage: TableLineageSourceSpec) => void;
  dataLineageSpec: TableLineageSourceSpec;
}) {
  const {
    connection,
    schema,
    table,
    checkTypes
  }: {
    connection: string;
    schema: string;
    table: string;
    checkTypes: CheckTypes;
  } = useDecodedParams();

  const [sourceColumns, setSourceColumns] = useState<Option[]>([]);
  const [targetColumns, setTargetColumns] = useState<string[]>([]);

  useEffect(() => {
    const fetchColumns = async () => {
      await ColumnApiClient.getColumns(connection, schema, table)
        .then((res) => {
          setTargetColumns(
            (res.data ?? []).map((column) => column.column_name ?? '')
          );
        })
        .catch((err) => console.error(err));
    };

    const fetchSourceColumns = async () => {
      await ColumnApiClient.getColumns(
        dataLineage.source_connection ?? '',
        dataLineage.source_schema ?? '',
        dataLineage.source_table ?? ''
      )
        .then((res) => {
          setSourceColumns([
            { label: '', value: '' },
            ...(res.data ?? []).map((column) => ({
              label: column.column_name ?? '',
              value: column.column_name ?? ''
            }))
          ]);
        })
        .catch((err) => console.error(err));
    };

    fetchColumns();
    if (
      dataLineage.source_connection &&
      dataLineage.source_schema &&
      dataLineage.source_table
    ) {
      fetchSourceColumns();
    }
  }, [
    connection,
    schema,
    table,
    dataLineage.source_connection,
    dataLineage.source_schema,
    dataLineage.source_table
  ]);

  const handleSelectChange = (
    column: string,
    newValue: string,
    sourceColumnIndex: number
  ) => {
    const newColumns = [
      ...(dataLineageSpec.columns?.[column]?.source_columns ?? [])
    ];
    if (newValue === '') {
      newColumns.splice(sourceColumnIndex, 1);
    } else {
      if (newColumns[sourceColumnIndex] !== undefined) {
        newColumns[sourceColumnIndex] = newValue;
      } else {
        newColumns.push(newValue);
      }
    }

    onChangeDataLineageSpec({
      ...dataLineageSpec,
      columns: {
        ...dataLineageSpec.columns,
        [column]: {
          ...dataLineageSpec.columns?.[column],
          source_columns: newColumns
        }
      }
    });
  };

  return (
    <div>
      <div className="px-4">Source Columns</div>
      <table>
        <tbody>
          {targetColumns.map((column, columnIndex) => (
            <tr key={columnIndex}>
              <td className="px-4 py-2">{column}</td>
              {[
                ...(dataLineageSpec.columns?.[column]?.source_columns ?? []),
                ''
              ].map((sourceColumn, sourceColumnIndex) => (
                <td className="px-4 py-2" key={sourceColumnIndex}>
                  <Select
                    value={sourceColumn}
                    options={sourceColumns}
                    onChange={(newValue) =>
                      handleSelectChange(column, newValue, sourceColumnIndex)
                    }
                  />
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
