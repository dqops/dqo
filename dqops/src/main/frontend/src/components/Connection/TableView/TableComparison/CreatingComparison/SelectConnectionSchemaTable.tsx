import React, { useEffect, useState } from 'react';
import SectionWrapper from '../../../../Dashboard/SectionWrapper';
import { TParameters } from '../../../../../shared/constants';
import Select, { Option } from '../../../../Select';
import {
  ConnectionApiClient,
  SchemaApiClient,
  TableApiClient,
  TableComparisonsApi
} from '../../../../../services/apiClient';
import { table } from 'console';
import useConnectionSchemaTableExists from '../../../../../hooks/useConnectionSchemaTableExists';
import clsx from 'clsx';

type TSelectConnectionSchemaTable = {
  editConfigurationParameters: TParameters;
  onChangeEditConnectionSchemaTable: (open: boolean) => void;
  onChangeParameters: (obj: Partial<TParameters>) => void;
};

export default function SelectConnectionSchemaTable({
  editConfigurationParameters,
  onChangeEditConnectionSchemaTable,
  onChangeParameters
}: TSelectConnectionSchemaTable) {
  const [connectionOptions, setConnectionOptions] = useState<Option[]>([]);
  const [schemaOptions, setSchemaOptions] = useState<Option[]>([]);
  const [tableOptions, setTableOptions] = useState<Option[]>([]);

  // const { tableExist, schemaExist, connectionExist } =
  //   useConnectionSchemaTableExists(
  //     editConfigurationParameters.refConnection ?? '',
  //     editConfigurationParameters.refSchema ?? '',
  //     editConfigurationParameters.refTable ?? ''
  //   );
  useEffect(() => {
    ConnectionApiClient.getAllConnections().then((res) => {
      setConnectionOptions(
        res.data.map((item) => ({
          label: item.connection_name ?? '',
          value: item.connection_name ?? ''
        }))
      );
    });
  }, []);

  useEffect(() => {
    if (editConfigurationParameters?.refConnection) {
      SchemaApiClient.getSchemas(
        editConfigurationParameters.refConnection ?? ''
      ).then((res) => {
        if (res !== undefined) {
          setSchemaOptions(
            res.data.map((item) => ({
              label: item.schema_name ?? '',
              value: item.schema_name ?? ''
            }))
          );
        }
      });
    }
  }, [editConfigurationParameters.refConnection]);

  useEffect(() => {
    if (
      editConfigurationParameters.refConnection &&
      editConfigurationParameters.refSchema
    ) {
      TableApiClient.getTables(
        editConfigurationParameters.refConnection ?? '',
        editConfigurationParameters.refSchema ?? ''
      ).then((res) => {
        if (res !== undefined) {
          setTableOptions(
            res.data.map((item) => ({
              label: item.target?.table_name ?? '',
              value: item.target?.table_name ?? ''
            }))
          );
        }
      });
    }
  }, [
    editConfigurationParameters.refConnection,
    editConfigurationParameters.refSchema
  ]);

  return (
    <SectionWrapper
      title="Reference table (the source of truth)"
      className="py-8 mb-10 flex w-full items-center justify-between"
      svgIcon={true}
      onClick={() => onChangeEditConnectionSchemaTable(false)}
    >
      <div className="flex flex-col gap-2 w-1/3 mb-3 mr-4">
        <div>Connection</div>
        <Select
          className={clsx(
            'flex-1',
            editConfigurationParameters.refConnection &&
              editConfigurationParameters?.refConnection?.length > 0
              ? ''
              : 'border border-red-500'
          )}
          options={connectionOptions}
          value={editConfigurationParameters.refConnection}
          onChange={(selectedOption) => {
            onChangeParameters({
              refConnection: selectedOption
            });
          }}
          // triggerClassName={connectionExist ? '' : 'border border-red-500'}
        />
      </div>
      <div className="flex flex-col gap-2  w-1/3 mb-3 mr-4">
        <div> Schema</div>
        <Select
          className={clsx(
            'flex-1',
            editConfigurationParameters.refSchema &&
              editConfigurationParameters?.refSchema?.length > 0
              ? ''
              : 'border border-red-500'
          )}
          options={schemaOptions}
          value={editConfigurationParameters.refSchema}
          onChange={(selectedOption) =>
            onChangeParameters({
              refSchema: selectedOption
            })
          }
          // triggerClassName={schemaExist ? '' : 'border border-red-500'}
        />
      </div>
      <div className="flex flex-col gap-2 w-1/3 mb-3">
        <div>Table</div>
        <Select
          className={clsx(
            'flex-1',
            editConfigurationParameters.refTable &&
              editConfigurationParameters?.refTable?.length > 0
              ? ''
              : 'border border-red-500'
          )}
          options={tableOptions}
          value={editConfigurationParameters.refTable}
          onChange={(selectedOption) =>
            onChangeParameters({
              refTable: selectedOption
            })
          }
          // triggerClassName={tableExist ? '' : 'border border-red-500'}
        />
      </div>
    </SectionWrapper>
  );
}
