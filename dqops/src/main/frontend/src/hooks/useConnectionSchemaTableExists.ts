import { useEffect, useState } from 'react'
import { ConnectionApiClient, SchemaApiClient, TableApiClient } from '../services/apiClient';


 function useConnectionSchemaTableExists(connection : string, schema: string, table: string) {
   const [tableExist, setTableExist] = useState<boolean>();
   const [schemaExist, setSchemaExist] = useState<boolean>();
   const [connectionExist, setConnectionExist] = useState<boolean>();
 
   useEffect(() => {
     const fetchData = async () => {
       const validate404Status = (status: number): boolean => {
         return status === 200 || status === 404;
       };
       try {
         const tableResponse = await TableApiClient.getTable(
           connection,
           schema,
           table,
           { validateStatus: validate404Status }
         );
         setTableExist(tableResponse.status === 200);
 
         const schemaResponse = await SchemaApiClient.getSchemas(
           connection,
           { validateStatus: validate404Status }
         );
         setSchemaExist(schemaResponse.status === 200);
 
         const connectionResponse = await ConnectionApiClient.getConnection(
           connection,
           { validateStatus: validate404Status }
         );
         setConnectionExist(connectionResponse.status === 200);
       } catch (error) {
         setTableExist(false);
         setSchemaExist(false);
         setConnectionExist(false);
       }
     };
 
     fetchData();
   }, [connection, schema, table]);
    return {tableExist, schemaExist, connectionExist}
 }
export default useConnectionSchemaTableExists