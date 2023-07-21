import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { ColumnApiClient, ConnectionApiClient } from '../../services/apiClient';
import { AxiosResponse } from 'axios';
import { ColumnBasicModel, CommonColumnModel } from '../../api';
import SelectInput from '../SelectInput';

export interface Option {
  label: string;
  value: string;
}

interface IColumnSelectProps {
  label?: string;
  value?: string;
  tooltipText?: string;
  onChange: (value: string) => void;
  disabled?: boolean;
  scope?: string;
  error?: boolean;
  triggerClassName?: string;
  placeholder?: string;
  refConnection?: string;
  refSchema?: string;
  refTable?: string;
}

const ColumnSelect = ({
  label,
  value,
  tooltipText,
  onChange,
  disabled,
  scope = 'column',
  triggerClassName,
  error,
  placeholder,
  refConnection,
  refSchema,
  refTable
}: IColumnSelectProps) => {
  const [options, setOptions] = useState<Option[]>([]);
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } = useParams();

  const setColumns = (
    res: AxiosResponse<CommonColumnModel[] | ColumnBasicModel[]>
  ) => {
    setOptions(
      res.data.map((item) => ({
        label: item.column_name || '',
        value: item.column_name || ''
      }))
    );
  };

  useEffect(() => {
    if (refConnection && refSchema && refTable) {
      ColumnApiClient.getColumns(refConnection, refSchema, refTable).then(
        setColumns
      );
    } else if (connection && scope === 'connection') {
      ConnectionApiClient.getConnectionCommonColumns(connection).then(
        setColumns
      );
    } else if (table && !refTable) {
      ColumnApiClient.getColumns(connection, schema, table).then(setColumns);
    }
  }, [connection, schema, table, refConnection, refSchema, refTable]);

  return (
    <div>
      <SelectInput
        disabled={disabled}
        label={label}
        value={value}
        tooltipText={tooltipText}
        options={options}
        triggerClassName={triggerClassName}
        onChange={onChange}
        error={error}
        placeholder={placeholder}
      />
    </div>
  );
};

export default ColumnSelect;
