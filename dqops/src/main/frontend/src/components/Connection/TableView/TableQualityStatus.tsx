import React, { useEffect, useState } from 'react'
import { CheckResultApi } from '../../../services/apiClient'
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../../shared/routes';
import { TableCurrentDataQualityStatusModel } from '../../../api';

export default function TableQualityStatus() {
    const {
        checkTypes,
        connection,
        schema,
        table
      }: {
        checkTypes: CheckTypes;
        connection: string;
        schema: string;
        table: string;
      } = useParams();
    const [tableDataQualityStatus, setTableDataQualityStatus] = useState<TableCurrentDataQualityStatusModel>({})

    const getTableDataQualityStatus = () => {   
        CheckResultApi.getTableDataQualityStatus(connection, schema, table)
        .then((res) =>  setTableDataQualityStatus(res.data))
    }

    useEffect(() => {
        getTableDataQualityStatus()
    }, [])
console.log(tableDataQualityStatus)
  return (
    <div>TableQualityStatus</div>
  )
}
