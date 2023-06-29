import React, { useEffect, useState } from 'react';
import ReferenceTableList from "./ReferenceTableList";
import { TableComparisonsApi } from "../../../services/apiClient";
import { useParams } from "react-router-dom";
import { ReferenceTableModel } from "../../../api";
import EditReferenceTable from "./EditReferenceTable";

const TableReferences = () => {
  const { connection, schema, table }: { connection: string; schema: string; table: string } = useParams();
  const [isEditing, setIsEditing] = useState(false);
  const [references, setReferences] = useState<ReferenceTableModel[]>([]);
  const [selectedReference, setSelectedReference] = useState<ReferenceTableModel>();

  useEffect(() => {
    getReferences();
  }, []);

  const getReferences = () => {
    TableComparisonsApi.getReferenceTables(connection, schema, table).then((res) => {
      setReferences(res.data);
    });
  };

  const onBack = () => {
    setIsEditing(false);
    getReferences();
  };

  const onEditReferenceTable = (reference: ReferenceTableModel) => {
    setSelectedReference(reference);
    setIsEditing(true);
  };

  const onCreate = () => {
    setSelectedReference(undefined);
    setIsEditing(true);
  };

  return (
    <div className="my-1">
      {isEditing ? (
        <EditReferenceTable
          onBack={onBack}
          selectedReference={selectedReference}
        />
      ) : (
        <ReferenceTableList
          references={references}
          onCreate={onCreate}
          refetch={getReferences}
          onEditReferenceTable={onEditReferenceTable}
        />
      )}
    </div>
  );
};

export default TableReferences;
