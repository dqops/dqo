import clsx from 'clsx';
import React, { useEffect, useMemo, useState } from 'react';
import { TableLineageSourceListModel } from '../../../../api';
import {
  ConnectionApiClient,
  SchemaApiClient,
  TableApiClient
} from '../../../../services/apiClient';
import SectionWrapper from '../../../Dashboard/SectionWrapper';
import Select, { Option } from '../../../Select';

type TSelectConnectionSchemaTable = {
  editConfigurationParameters: TableLineageSourceListModel;
  onChangeParameters: (obj: Partial<TableLineageSourceListModel>) => void;
  create: boolean;
  setIsUpdated?: (value: boolean) => void;
};

export default function SourceTableSelectParameters({
  editConfigurationParameters,
  onChangeParameters,
  create,
  setIsUpdated
}: TSelectConnectionSchemaTable) {
  const [connectionOptions, setConnectionOptions] = useState<Option[]>([]);
  const [schemaOptions, setSchemaOptions] = useState<Option[]>([]);
  const [tableOptions, setTableOptions] = useState<Option[]>([]);

  useEffect(() => {
    if (!create) return;
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
    if (!create) return;
    if (editConfigurationParameters?.source_connection) {
      SchemaApiClient.getSchemas(
        editConfigurationParameters.source_connection ?? ''
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
  }, [editConfigurationParameters.source_connection]);

  useEffect(() => {
    if (!create) return;
    if (
      editConfigurationParameters.source_connection &&
      editConfigurationParameters.source_schema
    ) {
      TableApiClient.getTables(
        editConfigurationParameters.source_connection ?? '',
        editConfigurationParameters.source_schema ?? ''
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
    editConfigurationParameters.source_connection,
    editConfigurationParameters.source_schema
  ]);

  const isConnectionValid = useMemo(
    () =>
      connectionOptions.some(
        (x) => x.value === editConfigurationParameters.source_connection
      ) || !create,
    [connectionOptions, editConfigurationParameters.source_connection, create]
  );

  const isSchemaValid = useMemo(
    () =>
      schemaOptions.some(
        (x) => x.value === editConfigurationParameters.source_schema
      ) || !create,
    [schemaOptions, editConfigurationParameters.source_schema, create]
  );

  const isTableValid = useMemo(
    () =>
      tableOptions.some(
        (x) => x.value === editConfigurationParameters.source_table
      ) || !create,
    [tableOptions, editConfigurationParameters.source_table, create]
  );

  return (
    <SectionWrapper
      title="Source table"
      className="py-8 mb-10 flex w-full items-center justify-between"
    >
      <div className="flex flex-col gap-2 w-1/3 mb-3 mr-4">
        <div>Connection</div>
        <Select
          className={clsx(
            'flex-1',
            isConnectionValid ? '' : 'border border-red-500'
          )}
          options={
            create
              ? connectionOptions
              : [
                  {
                    label: editConfigurationParameters.source_connection,
                    value: editConfigurationParameters.source_connection
                  } as Option
                ]
          }
          value={editConfigurationParameters.source_connection}
          onChange={(selectedOption) => {
            setIsUpdated && setIsUpdated(true);
            onChangeParameters({
              source_connection: selectedOption
            });
          }}
          disabled={!create}
          disableIcon={!create}
        />
      </div>
      <div className="flex flex-col gap-2  w-1/3 mb-3 mr-4">
        <div> Schema</div>
        <Select
          className={clsx(
            'flex-1',
            isSchemaValid ? '' : 'border border-red-500'
          )}
          options={
            create
              ? schemaOptions
              : [
                  {
                    label: editConfigurationParameters.source_schema,
                    value: editConfigurationParameters.source_schema
                  } as Option
                ]
          }
          value={editConfigurationParameters.source_schema}
          onChange={(selectedOption) => {
            setIsUpdated && setIsUpdated(true);

            onChangeParameters({
              source_schema: selectedOption
            });
          }}
          disabled={!create}
          disableIcon={!create}
        />
      </div>
      <div className="flex flex-col gap-2 w-1/3 mb-3">
        <div>Table</div>
        <Select
          className={clsx(
            'flex-1',
            isTableValid ? '' : 'border border-red-500'
          )}
          options={
            create
              ? tableOptions
              : [
                  {
                    label: editConfigurationParameters.source_table,
                    value: editConfigurationParameters.source_table
                  } as Option
                ]
          }
          value={editConfigurationParameters.source_table}
          onChange={(selectedOption) => {
            setIsUpdated && setIsUpdated(true);
            onChangeParameters({
              source_table: selectedOption
            });
          }}
          triggerClassName="text-ellipsis"
          truncateText
          disabled={!create}
          disableIcon={!create}
        />
      </div>
    </SectionWrapper>
  );
}
