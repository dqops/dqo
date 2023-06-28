import React, { useEffect, useState } from 'react';
import ReferenceTableList from "./ReferenceTableList";
import { TableComparisonsApi } from "../../../services/apiClient";
import { useParams } from "react-router-dom";
import { ReferenceTableModel } from "../../../api";
import EditReferenceTable from "./EditReferenceTable";

const TableReferences = () => {
  const { connection, schema, table }: { connection: string; schema: string; table: string } = useParams();
  const [isEditing, setIsEditing] = useState(false);
  const [references, setReferences] = useState<ReferenceTableModel[]>([
    {
      reference_table_configuration_name: 'reference_tab_1',
      reference_connection: 'conn1',
      reference_table:  {
        schema_name: 'schema',
        table_name: 'table'
      },
      compared_table_grouping_name: 'by_country',
      reference_table_grouping_name: 'by_countries'
    }
  ]);

  useEffect(() => {
    TableComparisonsApi.getReferenceTables(connection, schema, table).then((res) => {
      // setReferences(res.data);
    });
  }, []);


  return (
    <div className="my-1">
      {isEditing ? (
        <EditReferenceTable
          onBack={() => setIsEditing(false)}
        />
      ) : (
        <ReferenceTableList
          references={references}
          setIsEditing={setIsEditing}
        />
      )}
    </div>
  );
};

export default TableReferences;
