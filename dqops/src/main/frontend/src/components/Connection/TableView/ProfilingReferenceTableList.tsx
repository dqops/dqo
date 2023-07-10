import React from 'react';
import Button from '../../Button';
import { ReferenceTableModel } from '../../../api';

type ProfilingReferenceTableListProps = {
  references: ReferenceTableModel[];
  onCreate: () => void;
  selectReference: (reference: ReferenceTableModel) => void;
  onEdit: (reference: ReferenceTableModel) => void;
};

export const ProfilingReferenceTableList = ({
  references,
  onCreate,
  selectReference
}: ProfilingReferenceTableListProps) => {
  return (
    <div className="px-8 py-4 text-sm">
      <table className="mb-4 w-full">
        <thead>
          <tr>
            <th className="text-left py-2">
              Reference table configuration name
            </th>
            <th className="text-left px-2 py-2">Connection</th>
            <th className="text-left px-2 py-2">Schema</th>
            <th className="text-left px-2 py-2">Reference table name</th>
            <th className="text-left px-2 py-2">Compared data grouping</th>
            <th className="text-left px-2 py-2">Reference data grouping</th>
            <th className="text-left px-2 py-2" />
            <th className="text-left px-2 py-2" />
          </tr>
        </thead>
        <tbody>
          {references.map((reference, index) => (
            <tr key={index}>
              <Button
                variant="text"
                color="primary"
                className="text-sm px-0.5"
                label={reference.reference_table_configuration_name}
                onClick={() => selectReference(reference)}
              />
              <td className="px-2">{reference.reference_connection}</td>
              <td className="px-2">{reference.reference_table?.schema_name}</td>
              <td className="px-2">{reference.reference_table?.table_name}</td>
              <td className="px-2">{reference.compared_table_grouping_name}</td>
              <td className="px-2">
                {reference.reference_table_grouping_name}
              </td>
              <td className="px-2"></td>
            </tr>
          ))}
        </tbody>
      </table>
      <Button
        color="primary"
        label="New reference table for comparison"
        className="text-sm"
        onClick={onCreate}
      />
    </div>
  );
};
