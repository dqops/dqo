import React, { useEffect, useState } from 'react';
import {
  TableLineageSourceListModel,
  TableLineageSourceSpec
} from '../../../../api';
import { DataLineageApiClient } from '../../../../services/apiClient';
import { CheckTypes } from '../../../../shared/routes';
import { useDecodedParams } from '../../../../utils';
import Button from '../../../Button';
import SourceColumns from './SourceColumns';
import SourceTableSelectParameters from './SourceTableSelectParameters';

export default function SourceTableDetail({
  onBack,
  sourceTableEdit,
  create
}: {
  onBack: () => void;
  sourceTableEdit: {
    connection: string;
    schema: string;
    table: string;
  } | null;
  create: boolean;
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
  const [dataLineage, setDataLineage] =
    React.useState<TableLineageSourceListModel>({});
  const [dataLineageSpec, setDataLineageSpec] =
    useState<TableLineageSourceSpec>({});
  const [editConnectionSchemaTable, setEditConnectionSchemaTable] =
    React.useState(true);

  const onChangeEditConnectionSchemaTable = (open: boolean) => {
    setEditConnectionSchemaTable(open);
  };

  const onChangeParameters = (obj: Partial<TableLineageSourceListModel>) => {
    setDataLineage((prevState) => ({
      ...prevState,
      ...obj
    }));
  };

  const onChangeDataLineageSpec = (obj: Partial<TableLineageSourceSpec>) => {
    setDataLineageSpec((prevState) => ({
      ...prevState,
      ...obj
    }));
  };

  useEffect(() => {
    if (create || !sourceTableEdit) return;
    DataLineageApiClient.getTableSourceTable(
      connection,
      schema,
      table,
      sourceTableEdit.connection,
      sourceTableEdit.schema,
      sourceTableEdit.table
    ).then((res) => {
      console.log(res);
      setDataLineage(res.data);
    });
  }, [sourceTableEdit, create]);

  const handleSave = () => {
    if (create) {
      DataLineageApiClient.createTableSourceTable(
        connection,
        schema,
        table,
        dataLineage.source_connection ?? '',
        dataLineage.source_schema ?? '',
        dataLineage.source_table ?? '',
        {
          source_connection: dataLineage.source_connection,
          source_schema: dataLineage.source_schema,
          source_table: dataLineage.source_table,
          ...dataLineageSpec
        }
      );
    } else {
      DataLineageApiClient.updateTableSourceTable(
        connection,
        schema,
        table,
        dataLineage.source_connection ?? '',
        dataLineage.source_schema ?? '',
        dataLineage.source_table ?? '',
        {
          source_connection: dataLineage.source_connection,
          source_schema: dataLineage.source_schema,
          source_table: dataLineage.source_table,
          ...dataLineageSpec
        }
      );
    }
    onBack();
  };

  return (
    <div>
      <div className="flex space-x-4 items-center absolute right-2 top-4">
        <Button
          label="Save"
          color="primary"
          className="!w-30 !mr-5 !z-[99]"
          onClick={handleSave}
        />
      </div>
      <div className="mt-12">
        <SourceTableSelectParameters
          onChangeEditConnectionSchemaTable={onChangeEditConnectionSchemaTable}
          editConfigurationParameters={dataLineage}
          onChangeParameters={onChangeParameters}
          create={create}
        />
      </div>
      <div>
        <SourceColumns
          dataLineage={dataLineage}
          onChangeDataLineageSpec={setDataLineageSpec}
          dataLineageSpec={dataLineageSpec}
        />
      </div>
    </div>
  );
}
