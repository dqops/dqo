import React, { useEffect, useState } from 'react';
import {
  TableLineageTableListModel,
  TableLineageSourceSpec
} from '../../../../api';
import { DataLineageApiClient } from '../../../../services/apiClient';
import { useDecodedParams } from '../../../../utils';
import Loader from '../../../Loader';
import TableActionGroup from '../TableActionGroup';
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
    table
  }: {
    connection: string;
    schema: string;
    table: string;
  } = useDecodedParams();
  const [dataLineage, setDataLineage] =
    React.useState<TableLineageTableListModel>({});
  const [dataLineageSpec, setDataLineageSpec] =
    useState<TableLineageSourceSpec>({});

  const [loading, setLoading] = React.useState(false);
  const [isUpdated, setIsUpdated] = useState(false);
  const [isUpdating, setIsUpdating] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const onChangeParameters = (obj: Partial<TableLineageTableListModel>) => {
    setDataLineage((prevState) => ({
      ...prevState,
      ...obj
    }));
  };

  useEffect(() => {
    if (create || !sourceTableEdit) return;
    setLoading(true);
    DataLineageApiClient.getTableSourceTable(
      connection,
      schema,
      table,
      sourceTableEdit.connection,
      sourceTableEdit.schema,
      sourceTableEdit.table
    )
      .then((res) => {
        setDataLineageSpec(res.data);
        setDataLineage({
          source_connection: res.data.source_connection,
          source_schema: res.data.source_schema,
          source_table: res.data.source_table
        });
      })
      .finally(() => {
        setLoading(false);
      });
  }, [sourceTableEdit, create]);

  const handleSave = () => {
    setIsUpdating(true);
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
      )
        .then(() => {
          setError(null);
          onBack();
        })
        .catch((err) => {
          console.log(err);
          if (err.response.status === 409) {
            setError('Data lineage already exists');
          }
        })
        .finally(() => {
          setIsUpdating(false);
        });
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
      ).finally(() => {
        onBack();
        setIsUpdating(false);
      });
    }
  };

  if (loading) {
    return (
      <div className="w-full h-screen flex justify-center items-center">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }

  const isSaveDisabled =
    !dataLineage.source_connection ||
    !dataLineage.source_schema ||
    !dataLineage.source_table;

  return (
    <div className="text-sm">
      <TableActionGroup
        onUpdate={handleSave}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
        isDisabled={isSaveDisabled}
      />
      {error && <div className="text-red-500 ml-4 mt-4 font-bold">{error}</div>}
      <div className="block transition-all duration-300 ease-in-out mt-15">
        <SourceTableSelectParameters
          editConfigurationParameters={dataLineage}
          onChangeParameters={onChangeParameters}
          create={create}
          setIsUpdated={setIsUpdated}
        />
      </div>
      <SourceColumns
        dataLineage={dataLineage}
        onChangeDataLineageSpec={setDataLineageSpec}
        dataLineageSpec={dataLineageSpec}
        create={create}
        setIsUpdated={setIsUpdated}
      />
    </div>
  );
}
