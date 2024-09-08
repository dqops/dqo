import React, { useState } from 'react';
import {
  TableLineageSourceListModel,
  TableLineageSourceSpec
} from '../../../../api';
import { CheckTypes } from '../../../../shared/routes';
import { useDecodedParams } from '../../../../utils';
import Button from '../../../Button';
import SourceColumns from './SourceColumns';
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
          editConfigurationParameters={dataLineage}
          onChangeParameters={onChangeParameters}
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
