import React, { useEffect, useState } from 'react';
import { ColumnApiClient, ConnectionApiClient } from '../../services/apiClient';

import SelectInput from '../SelectInput';
import { useDecodedParams } from '../../utils';

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
  passedOptions?: Option[];
}

const ColumnSelect = ({
  label,
  value,
  tooltipText,
  onChange,
  disabled,
  triggerClassName,
  error,
  scope = 'column',
  placeholder,
  passedOptions
}: IColumnSelectProps) => {
  const [options, setOptions] = useState<Option[]>([]);
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } = useDecodedParams();

  useEffect(() => {
    const fetchData = async () => {
      if (connection && scope === 'connection') {
        try {
          const response = await ConnectionApiClient.getConnectionCommonColumns(
            connection
          );
          setOptions(
            response.data.map((item) => ({
              label: item.column_name || '',
              value: item.column_name || ''
            }))
          );
        } catch (error) {
          console.error('Cannot fetch the data:', error);
        }
      } else if (table) {
        try {
          const response = await ColumnApiClient.getColumns(
            connection,
            schema,
            table,
            false,
            undefined
          );
          setOptions(
            response.data.map((item) => ({
              label: item.column_name || '',
              value: item.column_name || ''
            }))
          );
        } catch (error) {
          console.error('Error:', error);
        }
      }
    };

    if (passedOptions === undefined) {
      fetchData();
    }
  }, [connection, schema, table, scope]);

  return (
    <div>
      <SelectInput
        disabled={disabled}
        label={label}
        value={value}
        tooltipText={tooltipText}
        options={passedOptions ?? options}
        triggerClassName={triggerClassName}
        onChange={onChange}
        error={error}
        placeholder={placeholder}
      />
    </div>
  );
};

export default ColumnSelect;
