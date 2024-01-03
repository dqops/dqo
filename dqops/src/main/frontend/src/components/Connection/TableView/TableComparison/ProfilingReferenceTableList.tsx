import React, { useState } from 'react';
import Button from '../../../Button';
import { TableComparisonModel } from '../../../../api';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../../SvgIcon';
import ConfirmDialog from '../../../CustomTree/ConfirmDialog';

type ProfilingReferenceTableListProps = {
  references: TableComparisonModel[];
  onCreate: () => void;
  selectReference: (reference: TableComparisonModel) => void;
  canUserCreateTableComparison?: boolean;
  deleteComparison: (tableComparisonConfigurationName: string) => Promise<void>;
};

export const ProfilingReferenceTableList = ({
  references,
  onCreate,
  selectReference,
  canUserCreateTableComparison,
  deleteComparison
}: ProfilingReferenceTableListProps) => {
  const [isOpen, setIsOpen] = useState(false);
  const [selectedComparison, setSelectedComparison] = useState('');
  return (
    <div className="px-8 py-4 text-sm">
      <table className="mb-4 w-full">
        <thead>
          {references.length !== 0 ? (
            <tr>
              <th className="text-left py-2">
                Table comparison configuration name
              </th>
              <th className="text-left px-2 py-2">Reference connection</th>
              <th className="text-left px-2 py-2">Reference schema</th>
              <th className="text-left px-2 py-2">Reference table name</th>
            </tr>
          ) : null}
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
                    disabled={canUserCreateTableComparison === false}
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
        onConfirm={() => deleteComparison(selectedComparison)}
        message={
          '  Are you sure you want to delete the table comparison ' +
          selectedComparison +
          '?'
        }
      />
    </div>
  );
};
