import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import {
  TableLineageSourceListModel,
  TableLineageSourceSpec
} from '../../../../api';
import { useActionDispatch } from '../../../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../../../redux/actions/source.actions';
import { DataLineageApiClient } from '../../../../services/apiClient';
import { CheckTypes, ROUTES } from '../../../../shared/routes';
import { useDecodedParams } from '../../../../utils';
import Button from '../../../Button';
import Loader from '../../../Loader';
import SvgIcon from '../../../SvgIcon';
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
    React.useState(create);
  const [loading, setLoading] = React.useState(false);
  const onChangeEditConnectionSchemaTable = (open: boolean) => {
    setEditConnectionSchemaTable(open);
  };

  const onChangeParameters = (obj: Partial<TableLineageSourceListModel>) => {
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
        console.log(res);
        setDataLineageSpec(res.data);
      })
      .finally(() => {
        setLoading(false);
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
      ).finally(() => {
        onBack();
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
      });
    }
  };

  const dispatch = useActionDispatch();
  const history = useHistory();

  const goTable = () => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      dataLineage.source_connection ?? '',
      dataLineage.source_schema ?? '',
      dataLineage.source_table ?? '',
      'detail'
    );
    const value = ROUTES.TABLE_LEVEL_VALUE(
      CheckTypes.SOURCES,
      dataLineage.source_connection ?? '',
      dataLineage.source_schema ?? '',
      dataLineage.source_table ?? ''
    );
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value,
        label: dataLineage.source_table ?? '',
        state: {}
      })
    );

    history.push(url);
  };

  if (loading) {
    return (
      <div className="w-full h-screen flex justify-center items-center">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }

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
        {editConnectionSchemaTable ? (
          <SourceTableSelectParameters
            onChangeEditConnectionSchemaTable={
              onChangeEditConnectionSchemaTable
            }
            editConfigurationParameters={dataLineage}
            onChangeParameters={onChangeParameters}
            create={create}
          />
        ) : (
          <div className="flex items-center gap-4 mb-4">
            <SvgIcon
              name="chevron-right"
              className="w-5 h-5"
              onClick={() => onChangeEditConnectionSchemaTable(true)}
            />
            <a className="font-bold cursor-pointer" onClick={goTable}>
              {dataLineage.source_connection}.{dataLineage.source_schema}.
              {dataLineage.source_table}
            </a>
          </div>
        )}
      </div>
      <div>
        <SourceColumns
          dataLineage={dataLineage}
          onChangeDataLineageSpec={setDataLineageSpec}
          dataLineageSpec={dataLineageSpec}
          create={create}
        />
      </div>
    </div>
  );
}
