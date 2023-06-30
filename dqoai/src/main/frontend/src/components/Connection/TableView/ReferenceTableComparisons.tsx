import React, { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router-dom";
import { TableComparisonsApi } from "../../../services/apiClient";
import { ReferenceTableModel } from "../../../api";
import Button from "../../Button";
import { useActionDispatch } from "../../../hooks/useActionDispatch";
import { addFirstLevelTab } from "../../../redux/actions/source.actions";
import { CheckTypes, ROUTES } from "../../../shared/routes";

export const ReferenceTableComparisons = () => {
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } = useParams();
  const [references, setReferences] = useState<ReferenceTableModel[]>([]);
  const dispatch = useActionDispatch();
  const history = useHistory();

  useEffect(() => {
    getReferenceComparisons();
  }, []);

  const getReferenceComparisons = () => {
    TableComparisonsApi.getReferenceTables(connection, schema, table).then((res) => {
      setReferences(res.data);
    });
  };

  const onCreateNewReference = () => {
    const url = `${ROUTES.TABLE_LEVEL_PAGE(CheckTypes.SOURCES, connection, schema, table, 'reference-tables')}?isEditing=true`;
    dispatch(addFirstLevelTab(CheckTypes.SOURCES, {
      url,
      value: ROUTES.TABLE_LEVEL_VALUE(CheckTypes.SOURCES, connection, schema, table),
      state: {},
      label: table
    }));

    history.push(url);
  };

  return (
    <div className="px-8 py-4 text-sm">
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
                className="text-sm"
                label="Configure comparison checks"
              />
            </td>
            <td className="px-2">
              <Button
                variant="text"
                color="primary"
                className="text-sm"
                label="Edit reference table"
              />
            </td>
          </tr>
        ))}
        </tbody>
      </table>

      <Button
        color="primary"
        label="New reference table for comparison"
        className="text-sm"
        onClick={onCreateNewReference}
      />
    </div>
  )
}