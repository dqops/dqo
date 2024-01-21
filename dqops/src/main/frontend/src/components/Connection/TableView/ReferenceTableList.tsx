import React, { useState } from 'react';
import { TableComparisonsApi } from '../../../services/apiClient';
import { TableComparisonModel } from '../../../api';
import Button from '../../Button';

type ReferenceTableListProps = {
  references: TableComparisonModel[];
  onCreate: () => void;
  refetch: () => void;
  onEditReferenceTable: (reference: TableComparisonModel) => void;
};

const ReferenceTableList = ({
  references,
  onCreate,
  refetch,
  onEditReferenceTable
}: ReferenceTableListProps) => {
  const [deletingReference, setDeletingReference] =
    useState<TableComparisonModel>();
  const deleteReferenceTable = (reference: TableComparisonModel) => {
    setDeletingReference(reference);
    TableComparisonsApi.deleteTableComparisonConfiguration(
      reference.compared_connection ?? '',
      reference.compared_table?.schema_name ?? '',
      reference.compared_table?.table_name ?? '',
      reference.table_comparison_configuration_name ?? ''
    )
      .then(() => {
        refetch();
      })
      .finally(() => {
        setDeletingReference(undefined);
      });
  };

  return (
    <div className="px-8 py-4">
      <table className="mb-4 w-full">
        <thead>
          <tr>
            <th className="text-left  py-2">
              Table comparison configuration name
            </th>
            <th className="text-left px-2 py-2">Reference connection</th>
            <th className="text-left px-2 py-2">Reference schema</th>
            <th className="text-left px-2 py-2">Reference table name</th>
            <th className="text-left px-2 py-2" />
            <th className="text-left px-2 py-2" />
          </tr>
        </thead>
        <tbody>
          {references.map((reference, index) => (
            <tr key={index}>
              <td className="pr-2">
                <Button
                  className="text-sm px-0"
                  variant="text"
                  color="primary"
                  label={reference.table_comparison_configuration_name}
                  onClick={() => onEditReferenceTable(reference)}
                />
              </td>
              <td className="px-2">{reference.reference_connection}</td>
              <td className="px-2">{reference.reference_table?.schema_name}</td>
              <td className="px-2">{reference.reference_table?.table_name}</td>

              <td className="px-2">
                <Button
                  className="text-sm"
                  variant="text"
                  color="primary"
                  label="Delete"
                  loading={
                    deletingReference?.table_comparison_configuration_name ===
                    reference.table_comparison_configuration_name
                  }
                  onClick={() => deleteReferenceTable(reference)}
                />
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <Button
        className="text-sm"
        color="primary"
        label="New table comparison configuration"
        onClick={onCreate}
      />
    </div>
  );
};

export default ReferenceTableList;
