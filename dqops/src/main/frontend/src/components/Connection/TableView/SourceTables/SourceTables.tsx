import React from 'react';
import Button from '../../../Button';
import SvgIcon from '../../../SvgIcon';
import TableActionGroup from '../TableActionGroup';
import SourceTableDetail from './SourceTableDetail';
import SourceTablesTable from './SourceTablesTable';

export default function SourceTables() {
  const [addSourceTable, setAddSourceTable] = React.useState(false);
  const [sourceTableEdit, setSourceTableEdit] = React.useState<{
    connection: string;
    schema: string;
    table: string;
  } | null>(null);

  const onBack = () => {
    setAddSourceTable(false);
    setSourceTableEdit(null);
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
            create={addSourceTable}
          />
        </div>
      ) : (
        <>
          <TableActionGroup onUpdate={onBack} isDisabled />
          <div className="flex mb-4 text-sm"></div>
          <SourceTablesTable setSourceTableEdit={setSourceTableEdit} />
          <Button
            label="Add source table"
            color="primary"
            className="!w-50 !my-5 ml-4"
            onClick={() => setAddSourceTable(true)}
          />
        </>
      )}
    </div>
  );
}
