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
  const [ref, setRef] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      if (refConnection && refSchema && refTable) {
        try {
          const response = await ColumnApiClient.getColumns(
            refConnection,
            refSchema,
            refTable
          );
          setOptions(
            response.data.map((item) => ({
              label: item.column_name || '',
              value: item.column_name || ''
            }))
          );
          setRef(true);
        } catch (error) {
          console.error('Błąd pobierania danych:', error);
        }
      } else {
        if (connection && scope === 'connection' && !refTable && !ref) {
          try {
            const response =
              await ConnectionApiClient.getConnectionCommonColumns(connection);
            setOptions(
              response.data.map((item) => ({
                label: item.column_name || '',
                value: item.column_name || ''
              }))
            );
            console.log('first if');
          } catch (error) {
            console.error('Błąd pobierania danych:', error);
          }
        } else if (
          table &&
          !refTable &&
          !refConnection &&
          !refSchema &&
          ref === false
        ) {
          try {
            const response = await ColumnApiClient.getColumns(
              connection,
              schema,
              table
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
      }
    };

    fetchData();
  }, [
    connection,
    schema,
    table,
    refConnection,
    refSchema,
    refTable,
    ref,
    scope
  ]);

  console.log(ref);
  console.log(options);

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
