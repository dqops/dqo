import { useEffect, useState } from 'react'
import { ConnectionApiClient, SchemaApiClient, TableApiClient } from '../services/apiClient';


 function useConnectionSchemaTableExists(connection : string, schema: string, table: string) {
    const [tableExist, setTableExist] = useState<boolean>()
    const [schemaExist, setSchemaExist] = useState<boolean>()
    const [connectionExist, setConnectionExist] = useState<boolean>()
    useEffect(() => {
    const fetchData = async () => {
         const validate404Status = (status : number) : boolean => {
            return status === 200 || status === 404;
        } 
    setSchemaExist(true)
    setTableExist(true)
    setConnectionExist(true)    
     TableApiClient.getTable(connection, schema, table,  {validateStatus: validate404Status})
        .then((res) => res.status === 404 && setTableExist(false))
        .catch(() => {setTableExist(false)});
   SchemaApiClient.getSchemas(connection, {validateStatus: validate404Status})
        .then((res) => res.status === 404 && (setTableExist(false), setSchemaExist(false)))
        .catch(() => { setTableExist(false), setSchemaExist(false)} );
     ConnectionApiClient.getConnection(connection, {validateStatus: validate404Status})
        .then((res) => res.status === 404 && (setTableExist(false), setSchemaExist(false), setConnectionExist(false)))
        .catch(() => { setTableExist(false), setSchemaExist(false), setConnectionExist(false) } );
    };

    fetchData();
  }, [connection, schema, table]);
    return {tableExist, schemaExist, connectionExist}
 }
export default useConnectionSchemaTableExists