import React, { useState } from 'react';
import Button from '../../Button';
import { TableComparisonModel } from '../../../api';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../SvgIcon';
import { TableComparisonsApi } from '../../../services/apiClient';
import { useParams } from 'react-router-dom';
import ConfirmDialog from '../../CustomTree/ConfirmDialog';

type ProfilingReferenceTableListProps = {
  references: TableComparisonModel[];
  onCreate: () => void;
  selectReference: (reference: TableComparisonModel) => void;
  onEdit: (reference: TableComparisonModel) => void;
  canUserCreateTableComparison?: boolean;
};

export const ProfilingReferenceTableList = ({
  references,
  onCreate,
  selectReference,
  canUserCreateTableComparison
}: ProfilingReferenceTableListProps) => {
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } = useParams();
  const [isOpen, setIsOpen] = useState(false);
  const [selectedComparison, setSelectedComparison] = useState('');
  const deleteComparison = async () => {
    await TableComparisonsApi.deleteTableComparisonConfiguration(
      connection,
      schema,
      table,
      selectedComparison
    );
  };

  return (
    <div className="px-8 py-4 text-sm">
      <table className="mb-4 w-full">
        <thead>
          <tr>
            <th className="text-left py-2">
              Table comparison configuration name
            </th>
            <th className="text-left px-2 py-2">Connection</th>
            <th className="text-left px-2 py-2">Schema</th>
            <th className="text-left px-2 py-2">Reference table name</th>
            <th className="text-left px-2 py-2" />
            <th className="text-left px-2 py-2" />
          </tr>
        </thead>
        <tbody>
          {references &&
            references.map((reference, index) => (
              <tr key={index}>
                <Button
                  variant="text"
                  color="primary"
                  className="text-sm px-0.5"
                  label={reference.table_comparison_configuration_name}
                  onClick={() => selectReference(reference)}
                />
                <td className="px-2">{reference.reference_connection}</td>
                <td className="px-2">
                  {reference.reference_table?.schema_name}
                </td>
                <td className="px-2">
                  {reference.reference_table?.table_name}
                </td>
                <td className="px-2">
                  {' '}
                  <IconButton
                    size="sm"
                    className="group bg-teal-500 ml-3"
                    onClick={() => {
                      setSelectedComparison(
                        reference.table_comparison_configuration_name ?? ''
                      ),
                        setIsOpen(true);
                    }}
                  >
                    <SvgIcon name="delete" className="w-4" />

                    <span className="hidden absolute right-0 bottom-6 p-1 normal-case bg-black text-white rounded-md group-hover:block whitespace-nowrap">
                      Delete table comparison
                    </span>
                  </IconButton>
                </td>
              </tr>
            ))}
        </tbody>
      </table>
      <Button
        color="primary"
        label="New table comparison configuration"
        className="text-sm"
        onClick={onCreate}
        disabled={canUserCreateTableComparison === false}
      />
      <ConfirmDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        onConfirm={deleteComparison}
        message={
          '  Are you sure you want to delete the table comparison ' +
          selectedComparison +
          '?'
        }
      />
    </div>
  );
};
