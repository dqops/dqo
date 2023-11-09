import React, { useEffect, useState } from 'react';
import { CheckResultApi } from '../../../services/apiClient';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../../shared/routes';
import { TableCurrentDataQualityStatusModel } from '../../../api';
import SectionWrapper from '../../Dashboard/SectionWrapper';

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
  const [tableDataQualityStatus, setTableDataQualityStatus] =
    useState<TableCurrentDataQualityStatusModel>({});
  const [firstLevelChecks, setFirstLevelChecks] = useState<string[]>([]);
  const getTableDataQualityStatus = () => {
    CheckResultApi.getTableDataQualityStatus(connection, schema, table).then(
      (res) => setTableDataQualityStatus(res.data)
    );
  };

  useEffect(() => {
    getTableDataQualityStatus();
  }, []);

  console.log(tableDataQualityStatus);
  return (
    <div className="p-4">
      <div className="flex gap-x-5">
        <SectionWrapper title="Current table status">
          <div className="flex">
            <div>Status:</div>
            <div>{tableDataQualityStatus.highest_severity_level}</div>
          </div>
          <div className="flex gap-x-2">
            <div>Last check executed at:</div>
            <div>{tableDataQualityStatus.last_check_executed_at}</div>
          </div>
        </SectionWrapper>
        <SectionWrapper title="Total checks executed">
          <div className="flex gap-x-2">
            <div>Total checks executed:</div>
            <div>{tableDataQualityStatus.executed_checks}</div>
          </div>
          <div className="flex gap-x-2">
            <div>Valid:</div>
            <div>{tableDataQualityStatus.valid_results}</div>
          </div>
          <div className="flex gap-x-2">
            <div>Warnings:</div>
            <div>{tableDataQualityStatus.warnings}</div>
          </div>
          <div className="flex gap-x-2">
            <div>Errors:</div>
            <div>{tableDataQualityStatus.errors}</div>
          </div>
          <div className="flex gap-x-2">
            <div>Fatals:</div>
            <div>{tableDataQualityStatus.fatals}</div>
          </div>
          <div className="flex gap-x-2">
            <div>Execution errors:</div>
            <div>{tableDataQualityStatus.execution_errors}</div>
          </div>
        </SectionWrapper>
      </div>
      <table className="border border-gray-150 mt-4 w-full">
        <thead>
          {Object.values(tableDataQualityStatus.checks ?? {}).map(
            (x, index) => (
              <th key={index} className="p-4">
                {x.category}
              </th>
            )
          )}
        </thead>
        <tbody></tbody>
      </table>
    </div>
  );
}
