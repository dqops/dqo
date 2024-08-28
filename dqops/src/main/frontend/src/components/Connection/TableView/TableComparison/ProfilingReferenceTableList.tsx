import { IconButton } from '@material-tailwind/react';
import React, { useState } from 'react';
import { TableComparisonModel } from '../../../../api';
import Button from '../../../Button';
import ConfirmDialog from '../../../CustomTree/ConfirmDialog';
import ClientSidePagination from '../../../Pagination/ClientSidePagination'; // Import pagination component
import SvgIcon from '../../../SvgIcon';

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
  const [displayedReferences, setDisplayedReferences] = useState<
    TableComparisonModel[]
  >([]); // State for displayed references

  return (
    <div className="px-8 py-4 text-sm">
      <table className="mb-4 w-full">
        <thead>
          {references.length !== 0 ? (
            <tr className="border-b border-gray-100">
              <th className="text-left py-2">
                Table comparison configuration name
              </th>
              <th className="text-left px-2 py-2">Reference connection</th>
              <th className="text-left px-2 py-2">Reference schema</th>
              <th className="text-left px-2 py-2">Reference table name</th>
              <th className="text-left px-2 py-2">
                <div className="flex items-center justify-start ml-6.5">
                  Action
                </div>
              </th>
            </tr>
          ) : null}
        </thead>
        <div className="w-full h-2"></div>
        <tbody>
          {displayedReferences.map((reference, index) => (
            <tr key={index}>
              <td className=" py-2 text-left block w-100">
                <Button
                  variant="text"
                  className="text-sm px-0.5 underline"
                  label={reference.table_comparison_configuration_name}
                  onClick={() => selectReference(reference)}
                />
              </td>
              <td className="px-2">{reference.reference_connection}</td>
              <td className="px-2">{reference.reference_table?.schema_name}</td>
              <td className="px-2 max-w-50 truncate">
                {reference.reference_table?.table_name}
              </td>
              <td className="px-2">
                {' '}
                <IconButton
                  size="sm"
                  ripple={false}
                  className="group bg-teal-500 ml-3 !shadow-none hover:!shadow-none hover:bg-[#028770]"
                  onClick={() => {
                    selectReference(reference);
                  }}
                  disabled={canUserCreateTableComparison === false}
                >
                  <SvgIcon name="edit" className="w-4" />
                  <span className="hidden absolute right-0 bottom-6 p-1 normal-case bg-black text-white rounded-md group-hover:block whitespace-nowrap">
                    Edit table comparison
                  </span>
                </IconButton>
                <IconButton
                  size="sm"
                  className="group bg-teal-500 ml-3 !shadow-none hover:!shadow-none hover:bg-[#028770]"
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
      <ClientSidePagination
        items={references}
        onChangeItems={setDisplayedReferences}
        className="pl-1"
      />
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
