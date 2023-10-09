import { useEffect, useState } from 'react';
import {
  ConnectionApiClient,
  SchemaApiClient,
  TableApiClient
} from '../services/apiClient';

function useConnectionSchemaTableExists(
  connection: string,
  schema: string,
  table: string
) {
  const [tableExist, setTableExist] = useState<boolean>();
  const [schemaExist, setSchemaExist] = useState<boolean>();
  const [connectionExist, setConnectionExist] = useState<boolean>();

  useEffect(() => {
    const fetchData = async () => {
      const validate404Status = (status: number): boolean => {
        return status === 200 || status === 404;
      };

      try {
        if (
          connection.length !== 0 &&
          schema.length !== 0 &&
          table.length !== 0
        ) {
          const tableResponse = await TableApiClient.getTable(
            connection,
            schema,
            table,
            { validateStatus: validate404Status }
          );
          setTableExist(tableResponse.status === 200);
        }
        if (connection.length !== 0) {
          const schemaResponse = await SchemaApiClient.getSchemas(connection, {
            validateStatus: validate404Status
          });
          if (schemaResponse.status === 200) {
            if (schemaResponse.data.find((x) => x.schema_name === schema)) {
              setSchemaExist(true);
            }else{
              setSchemaExist(false)
            }
          }
        }

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
    if(connection && connection.length!==0){
      fetchData();
    }
  }, [connection, schema, table]);
  return { tableExist, schemaExist, connectionExist };
}
export default useConnectionSchemaTableExists;
