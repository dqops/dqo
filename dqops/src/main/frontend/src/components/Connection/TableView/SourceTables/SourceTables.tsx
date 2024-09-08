import React, { useEffect, useState } from 'react';
import { TableLineageSourceListModel } from '../../../../api';
import { DataLineageApiClient } from '../../../../services/apiClient';
import { CheckTypes } from '../../../../shared/routes';
import { useDecodedParams } from '../../../../utils';
import Button from '../../../Button';
import SvgIcon from '../../../SvgIcon';
import SourceTableDetail from './SourceTableDetail';
import SourceTablesTable from './SourceTablesTable';

export default function SourceTables() {
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
  const [addSourceTable, setAddSourceTable] = React.useState(false);
  const [sourceTableEdit, setSourceTableEdit] = React.useState('');
  const [tables, setTables] = useState<TableLineageSourceListModel[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    DataLineageApiClient.getTableSourceTables(connection, schema, table)
      .then((res) => {
        setTables(res.data);
      })
      .finally(() => setLoading(false));
  }, []);

  const onBack = () => {
    setAddSourceTable(false);
    setSourceTableEdit('');
  };

  return (
    <div className="p-2">
      {addSourceTable || sourceTableEdit ? (
        <div>
          <div className="flex space-x-4 items-center absolute right-10 top-30">
            <Button
              label="Back"
              color="primary"
              variant="text"
              className="px-0"
              leftIcon={
                <SvgIcon name="chevron-left" className="w-4 h-4 mr-2" />
              }
              onClick={onBack}
            />
          </div>
          <SourceTableDetail
            onBack={onBack}
            sourceTableEdit={sourceTableEdit}
          />
        </div>
      ) : (
        <>
          <div className="flex mb-4 "></div>
          <SourceTablesTable
            tables={tables}
            loading={loading}
            onChange={setTables}
          />
          <Button
            label="Add source table"
            color="primary"
            className="!w-50 !my-5"
            onClick={() => setAddSourceTable(true)}
          />
        </>
      )}
    </div>
  );
}
