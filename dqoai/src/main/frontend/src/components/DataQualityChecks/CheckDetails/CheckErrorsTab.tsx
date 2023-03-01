import React, { useMemo } from "react";
import { ErrorsDetailedDataModel } from "../../../api";
import Select from "../../Select";
import { Table } from "../../Table";
import { useTree } from "../../../contexts/treeContext";
import moment from "moment/moment";
import ErrorText from "./ErrorText";

interface CheckErrorsTabProps {
  errors: ErrorsDetailedDataModel[];
  dataStreamName?: string;
  month?: string;
  onChangeMonth: (month: string) => void;
  onChangeDataStream: (name: string) => void;
}

const CheckErrorsTab = ({ errors, dataStreamName, onChangeDataStream, month, onChangeMonth }: CheckErrorsTabProps) => {
  const { sidebarWidth } = useTree();

  const columns = [
    {
      label: 'Check Name',
      value: 'checkName',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700 w-80',
    },
    {
      label: 'Executed At',
      value: 'executedAt',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700 w-60',
    },
    {
      label: 'Error Source',
      value: 'errorSource',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700 w-50',
    },
    {
      label: 'Error Message',
      value: 'errorMessage',
      className: 'text-sm !py-2 text-gray-700 w-120',
      render: (text: string) => <ErrorText text={text} />
    },
    {
      label: 'Readout Id',
      value: 'readoutId',
      className: 'text-sm !py-2 whitespace-nowrap text-gray-700 w-80 text-right',
    },
  ];

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
            options={(errors[0]?.dataStreamNames || []).map((item) => ({ label: item, value: item })) || []}
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
      {errors.length === 0 && (
        <div className="text-gray-700 mt-5">No Data</div>
      )}
      {errors.map((result, index) => (
        <div key={index} className="mb-4">
          <Table
            className="mt-4 w-full"
            columns={columns}
            data={(result.singleErrors || []).map((item) => ({ ...item, checkName: result.checkName }))}
            emptyMessage="No Data"
          />
        </div>
      ))}
    </div>
  );
};

export default CheckErrorsTab;
