import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import {
  TableLineageSourceListModel,
  TableLineageSourceSpec
} from '../../../../api';
import { DataLineageApiClient } from '../../../../services/apiClient';
import { useDecodedParams } from '../../../../utils';
import Loader from '../../../Loader';
import SvgIcon from '../../../SvgIcon';
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
    React.useState<TableLineageSourceListModel>({});
  const [dataLineageSpec, setDataLineageSpec] =
    useState<TableLineageSourceSpec>({});
  const [editConnectionSchemaTable, setEditConnectionSchemaTable] =
    React.useState(create);
  const [loading, setLoading] = React.useState(false);
  const [isUpdated, setIsUpdated] = useState(false);
  const [isUpdating, setIsUpdating] = useState(false);

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
      ).finally(() => {
        onBack();
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

  // const dispatch = useActionDispatch();
  // const history = useHistory();

  // const goTable = () => {
  //   const url = ROUTES.TABLE_LEVEL_PAGE(
  //     CheckTypes.SOURCES,
  //     dataLineage.source_connection ?? '',
  //     dataLineage.source_schema ?? '',
  //     dataLineage.source_table ?? '',
  //     'detail'
  //   );
  //   const value = ROUTES.TABLE_LEVEL_VALUE(
  //     CheckTypes.SOURCES,
  //     dataLineage.source_connection ?? '',
  //     dataLineage.source_schema ?? '',
  //     dataLineage.source_table ?? ''
  //   );
  //   dispatch(
  //     addFirstLevelTab(CheckTypes.SOURCES, {
  //       url,
  //       value,
  //       label: dataLineage.source_table ?? '',
  //       state: {}
  //     })
  //   );

  //   history.push(url);
  // };

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
      <div className="mt-15">
        <div
          className={clsx(
            'flex items-center mb-4 px-4',
            !editConnectionSchemaTable ? 'block' : 'hidden'
          )}
        >
          <SvgIcon
            name="chevron-right"
            className="w-5 h-5 cursor-pointer"
            onClick={() => onChangeEditConnectionSchemaTable(true)}
          />
          <a
            className="font-bold"
            // onClick={goTable}
          >
            {dataLineage.source_connection}.{dataLineage.source_schema}.
            {dataLineage.source_table}
          </a>
        </div>

        {/* SourceTableSelectParameters component loaded once */}
        <div
          className={`${
            editConnectionSchemaTable ? 'block' : 'hidden'
          } transition-all duration-300 ease-in-out`}
        >
          <SourceTableSelectParameters
            onChangeEditConnectionSchemaTable={
              onChangeEditConnectionSchemaTable
            }
            editConfigurationParameters={dataLineage}
            onChangeParameters={onChangeParameters}
            create={create}
            setIsUpdated={setIsUpdated}
          />
        </div>
      </div>

      <div>
        <SourceColumns
          dataLineage={dataLineage}
          onChangeDataLineageSpec={setDataLineageSpec}
          dataLineageSpec={dataLineageSpec}
          create={create}
          setIsUpdated={setIsUpdated}
        />
      </div>
    </div>
  );
}
