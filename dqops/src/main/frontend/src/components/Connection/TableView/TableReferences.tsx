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
  const [selectedReference, setSelectedReference] = useState<string>();
  const location = useLocation();
  const history = useHistory();

  useEffect(() => {
    const { isEditing: editing, reference } = qs.parse(location.search);
    setIsEditing(editing === 'true');
    setSelectedReference(reference as string);
  }, [location]);

  useEffect(() => {
    getReferences();
  }, []);

  const getReferences = () => {
    TableComparisonsApi.getReferenceTables(connection, schema, table).then((res) => {
      setReferences(res.data);
    });
  };


  const onChangeEditing = (value: boolean, reference?: string) => {
    setIsEditing(value);
    const parsed = qs.parse(location.search);
    parsed.isEditing = value.toString();

    if (reference !== undefined) {
      parsed.reference = reference;
    }
    history.replace(`${location.pathname}?${qs.stringify(parsed)}`);
  };

  const onBack = () => {
    onChangeEditing(false);
    getReferences();
  };

  const onEditReferenceTable = (reference: ReferenceTableModel) => {
    setSelectedReference(reference.reference_table_configuration_name);
    onChangeEditing(true, reference.reference_table_configuration_name);
  };

  const onCreate = () => {
    setSelectedReference('');
    onChangeEditing(true, '');
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
