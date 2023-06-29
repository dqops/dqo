import React, { useState } from 'react';
import { TableComparisonsApi } from "../../../services/apiClient";
import { ReferenceTableModel } from "../../../api";
import Button from "../../Button";

type ReferenceTableListProps = {
  references: ReferenceTableModel[];
  onCreate: () => void;
  refetch: () => void;
  onEditReferenceTable: (reference: ReferenceTableModel) => void;
};

const ReferenceTableList = ({ references, onCreate, refetch, onEditReferenceTable }: ReferenceTableListProps) => {
  const [deletingReference, setDeletingReference] = useState<ReferenceTableModel>();
  const deleteReferenceTable = (reference: ReferenceTableModel) => {
    setDeletingReference(reference)
    TableComparisonsApi.deleteReferenceTable(
      reference.compared_connection ?? '',
      reference.compared_table?.schema_name ?? '',
      reference.compared_table?.table_name ?? '',
      reference.reference_table_configuration_name ?? '',
    ).then(() => {
      refetch();
    }).finally(() => {
      setDeletingReference(undefined);
    });
  };

  return (
    <div className="px-8 py-4">
      <table className="mb-4 w-full">
        <thead>
          <tr>
            <th className="text-left pr-2 py-2">Reference table configuration name</th>
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
            <td className="pr-2">
              {reference.reference_table_configuration_name}
            </td>
            <td className="px-2">
              {reference.reference_connection}
            </td>
            <td className="px-2">
              {reference.reference_table?.schema_name}
            </td>
            <td className="px-2">
              {reference.reference_table?.table_name}
            </td>
            <td className="px-2">
              {reference.compared_table_grouping_name}
            </td>
            <td className="px-2">
              {reference.reference_table_grouping_name}
            </td>
            <td className="px-2">
              <Button
                variant="text"
                color="primary"
                label="Edit"
                onClick={() => onEditReferenceTable(reference)}
              />
            </td>
            <td className="px-2">
              <Button
                variant="text"
                color="primary"
                label="Delete"
                loading={deletingReference?.reference_table_configuration_name === reference.reference_table_configuration_name}
                onClick={() => deleteReferenceTable(reference)}
              />
            </td>
          </tr>
        ))}
        </tbody>
      </table>

      <Button
        color="primary"
        label="New reference table for comparison"
        onClick={onCreate}
      />
    </div>
  );
};

export default ReferenceTableList;
