import React, { useEffect, useState } from 'react'
import { ConnectionApiClient, SchemaApiClient, TableApiClient } from '../services/apiClient';


 function useConnectionSchemaTableExists(connection : string, schema: string, table: string) {
    const [tableExist, setTableExist] = useState(true)
    const [schemaExist, setSchemaExist] = useState(true)
    const [connectionExist, setConnectionExist] = useState(true)
    useEffect(() => {
    const fetchData = async () => {
        console.log("inside")
     await TableApiClient.getTable(connection, schema, table).then((res) => console.log(res.data) ).catch(() => {setTableExist(false)});
     await SchemaApiClient.getSchemas(connection).then((res) => console.log(res.data) ).catch(() => { setTableExist(false)} );
      await ConnectionApiClient.getConnection(connection).then((res) => console.log(res.data) ).catch(() => { setTableExist(false), setSchemaExist(false), setConnectionExist(false) } );
    };

    fetchData();
  }, [connection, schema, table]);
    return {tableExist, schemaExist, connectionExist}
 }
export default useConnectionSchemaTableExists