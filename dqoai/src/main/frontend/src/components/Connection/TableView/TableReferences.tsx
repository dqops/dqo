import React, { useEffect, useState } from 'react';
import ReferenceTableList from './ReferenceTableList';
import { TableComparisonsApi } from '../../../services/apiClient';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import { ReferenceTableModel } from '../../../api';
import EditReferenceTable from './EditReferenceTable';
import qs from "query-string";

const TableReferences = () => {
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } = useParams();
  const [isEditing, setIsEditing] = useState(false);
  const [references, setReferences] = useState<ReferenceTableModel[]>([]);
  const [selectedReference, setSelectedReference] = useState<ReferenceTableModel>();
  const location = useLocation();
  const history = useHistory();

  useEffect(() => {
    const { isEditing: editing } = qs.parse(location.search);
    setIsEditing(editing === 'true');
  }, [location]);

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
    history.replace(`${location.pathname}?isEditing=false`);
    getReferences();
  };

  const onEditReferenceTable = (reference: ReferenceTableModel) => {
    setSelectedReference(reference);
    setIsEditing(true);
    history.replace(`${location.pathname}?isEditing=true`);
  };

  const onCreate = () => {
    setSelectedReference(undefined);
    setIsEditing(true);
    history.replace(`${location.pathname}?isEditing=true`);
  };

  return (
    <div className="my-1 text-sm">
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
