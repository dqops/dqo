import React, { useMemo } from "react";
import { CheckResultDetailedSingleModel, CheckResultsDetailedDataModel } from "../../../api";
import Select from "../../Select";
import { Table } from "../../Table";
import { useTree } from "../../../contexts/treeContext";
import moment from "moment";
import { IncidentIssueList } from "../../../pages/IncidentDetail/IncidentIssueList";

interface CheckResultsTabProps {
  results: CheckResultsDetailedDataModel[];
  dataStreamName?: string;
  month?: string;
  onChangeMonth: (month: string) => void;
  onChangeDataStream: (name: string) => void;
}

const CheckResultsTab = ({ results, dataStreamName, month, onChangeMonth, onChangeDataStream }: CheckResultsTabProps) => {
  const { sidebarWidth } = useTree();

  const getSeverityClass = (row: CheckResultDetailedSingleModel) => {
    if (row.severity === 1) return 'bg-yellow-100';
    if (row.severity === 2) return 'bg-orange-100';
    if (row.severity === 3) return 'bg-red-100';

    return '';
  };

  const monthOptions = useMemo(() => {
    return Array(24).fill('').map((item, index) => ({
      label: moment().subtract(index, 'months').format('MMMM YYYY'),
      value: moment().subtract(index, 'months').format('MMMM YYYY')
    }))
  }, []);

  return (
    <div className="py-3 overflow-auto" style={{ maxWidth: `calc(100vw - ${sidebarWidth + 100}px` }}>
      <div className="flex space-x-8 items-center">
        <div className="flex space-x-4 items-center">
          <div className="text-sm">Data stream</div>
          <Select
            value={dataStreamName}
            options={(results[0]?.dataStreamNames || []).map((item) => ({ label: item, value: item })) || []}
            onChange={onChangeDataStream}
          />
        </div>
        <div className="flex space-x-4 items-center">
          <div className="text-sm">Month</div>
          <Select
            value={month}
            options={monthOptions}
            onChange={onChangeMonth}
          />
        </div>
      </div>
      {results.length === 0 && (
        <div className="text-gray-700 mt-5">No Data</div>
      )}

      {results.map((result, index) => (
        <div key={index}>
          <IncidentIssueList
            issues={(result.singleCheckResults || []).map((item) => ({ ...item, checkName: result.checkName }))}
          />
        </div>
      ))}
    </div>
  );
};

export default CheckResultsTab;
