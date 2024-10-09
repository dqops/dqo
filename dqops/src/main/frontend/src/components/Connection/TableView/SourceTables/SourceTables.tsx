import React from 'react';
import Button from '../../../Button';
import SvgIcon from '../../../SvgIcon';
import TableActionGroup from '../TableActionGroup';
import SourceTableDetail from './SourceTableDetail';
import SourceTablesSimilarTablesTable from './SourceTablesSimilarTablesTable';
import SourceTablesTable from './SourceTablesTable';

export default function SourceTables({ isTarget }: { isTarget?: boolean }) {
  const [addSourceTable, setAddSourceTable] = React.useState(false);
  const [sourceTableEdit, setSourceTableEdit] = React.useState<{
    connection: string;
    schema: string;
    table: string;
  } | null>(null);
  const [existingTables, setExistingTables] = React.useState<
    { connection: string; schema: string; table: string }[]
  >([]);

  const onBack = () => {
    setAddSourceTable(false);
    setSourceTableEdit(null);
  };
  const addSimilarTable = (
    obj: {
      connection: string;
      schema: string;
      table: string;
    } | null
  ) => {
    setSourceTableEdit(obj);
    setAddSourceTable(true);
  };

  return (
    <div className="p-2">
      {addSourceTable || sourceTableEdit ? (
        <div>
          <div className="flex space-x-4 items-center absolute right-10 top-40">
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
          <SourceTablesTable
            setSourceTableEdit={setSourceTableEdit}
            isTarget={isTarget}
            setExistingTables={setExistingTables}
          />
          {!isTarget && (
            <Button
              label={`Add source table`}
              color="primary"
              className="!w-50 !my-5 ml-4"
              onClick={() => setAddSourceTable(true)}
            />
          )}
          <div className="broder-t border-gray-100">
            <div className="font-bold text-lg ml-4 mb-4">
              Most similar tables
            </div>
            <SourceTablesSimilarTablesTable
              setSourceTableEdit={addSimilarTable}
              existingTables={existingTables}
            />
          </div>
        </>
      )}
    </div>
  );
}
