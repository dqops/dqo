import React, { useState } from 'react';
import { TableLineageSourceListModel } from '../../../../api';
import { CheckTypes } from '../../../../shared/routes';
import { useDecodedParams } from '../../../../utils';
import Button from '../../../Button';
import SourceTableSelectParameters from './SourceTableSelectParameters';

export default function SourceTableDetail({
  onBack,
  sourceTableEdit
}: {
  onBack: () => void;
  sourceTableEdit: string;
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
    React.useState<TableLineageSourceListModel | null>(null);
  const [editConnectionSchemaTable, setEditConnectionSchemaTable] =
    React.useState(true);
  const [editConfigurationParameters, setEditConfigurationParameters] =
    useState<TableLineageSourceListModel>({});

  const onChangeEditConnectionSchemaTable = (open: boolean) => {
    setEditConnectionSchemaTable(open);
  };

  //   React.useEffect(() => {
  //     DataLineageApiClient.getTableSourceTables(connection, schema, table)
  //       .then((res) => {
  //         setTables(res.data);
  //       })
  //       .finally(() => setLoading(false));
  //   }, [sourceTableEdit]);

  const onChangeParameters = (obj: Partial<TableLineageSourceListModel>) => {
    setEditConfigurationParameters((prevState) => ({
      ...prevState,
      ...obj
    }));
  };

  const handleSave = () => {
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
          editConfigurationParameters={editConfigurationParameters}
          onChangeParameters={onChangeParameters}
        />
      </div>
    </div>
  );
}
