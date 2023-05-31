import { CheckResultDetailedSingleModel, UICheckModel } from "../../api";
import React, { useEffect, useState } from "react";
import SvgIcon from "../../components/SvgIcon";
import CheckDetails from "../../components/DataQualityChecks/CheckDetails/CheckDetails";
import { ChecksApi } from "../../services/apiClient";

type IncidentIssueRowProps = {
  issue: CheckResultDetailedSingleModel;
}

export const IncidentIssueRow = ({ issue }: IncidentIssueRowProps) => {
  const [open, setOpen] = useState(false);
  const [check, setCheck] = useState<UICheckModel>();

  const getIssueSeverityLevel = (value?: number) => {
    let name = '';
    switch (value) {
      case 0:
        name = 'Success';
        break;
      case 1:
        name = 'Warning';
        break;
      case 2:
        name = 'Error';
        break;
      case 3:
        name = 'Fatal';
        break;
      default:
        break;
    }

    return name;
  }

  const getSeverityClass = (row: CheckResultDetailedSingleModel) => {
    if (row.severity === 1) return 'bg-yellow-100';
    if (row.severity === 2) return 'bg-orange-100';
    if (row.severity === 3) return 'bg-red-100';

    return '';
  };

  useEffect(() => {
    if (!issue?.checkName) return;

    ChecksApi.getCheck(issue?.checkName).then((res) => {
      console.log('res', res);
    });
  }, []);
  const closeCheckDetails = () => {
    setOpen(false);
  };

  const toggleCheckDetails = () => {
    setOpen(prev => !prev);
  };

  return (
    <>
      <tr className={getSeverityClass(issue)}>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
          <div className="flex space-x-1 items-center">
            {!open ? (
              <SvgIcon className="w-5" name="chevron-right" onClick={toggleCheckDetails} />
            ) : (
              <SvgIcon className="w-4" name="chevron-down" onClick={toggleCheckDetails} />
            )}
            <span>{issue.columnName}</span>
          </div>
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
          {issue.checkName}8
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
          {issue.executedAt}
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
          {issue.timeGradient}
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
          {issue.timePeriod}
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
          <div>{typeof issue.actualValue === 'number' ? issue.actualValue : ''}</div>
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
          <div>{typeof issue.expectedValue === 'number' ? issue.expectedValue : ''}</div>
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
          {getIssueSeverityLevel(issue.severity)}
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
          <div>{typeof issue.warningLowerBound === 'number' ? issue.warningLowerBound : ''}</div>
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
          <div>{typeof issue.warningUpperBound === 'number' ? issue.warningUpperBound : ''}</div>
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
          <div>{typeof issue.errorLowerBound === 'number' ? issue.errorLowerBound : ''}</div>
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
          <div>{typeof issue.errorUpperBound === 'number' ? issue.errorUpperBound : ''}</div>
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
          <div>{typeof issue.fatalLowerBound === 'number' ? issue.fatalLowerBound : ''}</div>
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
          <div>{typeof issue.fatalUpperBound === 'number' ? issue.fatalUpperBound : ''}</div>
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
          {issue.includeInKpi}
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
          {issue.includeInSla}
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
          {issue.durationMs}
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
          {issue.dataStream}
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
          <span>{issue.id}</span>
        </td>
      </tr>
      {open && (
        <tr>
          <td colSpan={12}>
            <CheckDetails
              onClose={closeCheckDetails}
            />
          </td>
        </tr>
      )}
    </>
  );
};

type IncidentIssueListProps = {
  issues: CheckResultDetailedSingleModel[];
};

export const IncidentIssueList = ({ issues }: IncidentIssueListProps) => {
  return (
    <div>
      <table className="mt-4 w-full">
        <thead>
        <tr>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
            Column Name
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
            Check Name
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
            Executed At
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
            Time Scale
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
            Time Period
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            Actual Value
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            Expected Value
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            Issue Severity Level
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <span>Warning<br/>Lower Threshold</span>
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <span>Warning<br/>Upper Threshold</span>
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <span>Error<br/>Lower Threshold</span>
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <span>Error<br/>Upper Threshold</span>
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <span>Fatal<br/>Lower Threshold</span>
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <span>Fatal<br/>Upper Threshold</span>
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            Include In Kpi
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
            Include In Sla
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
            Duration Ms
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
            Data Stream
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
            Id
          </th>
        </tr>
        </thead>
        <tbody>
        {issues.map((issue) => (
          <IncidentIssueRow key={issue.id} issue={issue} />
        ))}
        </tbody>
      </table>
    </div>
  )
};

