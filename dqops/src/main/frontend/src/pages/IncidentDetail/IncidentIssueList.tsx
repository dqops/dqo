import { CheckResultEntryModel, IncidentModel } from "../../api";
import React, { useState } from "react";
import SvgIcon from "../../components/SvgIcon";
import CheckDetails from "../../components/DataQualityChecks/CheckDetails/CheckDetails";
import { SortableColumn } from "../IncidentConnection/SortableColumn";
import { IncidentIssueFilter } from "../../redux/reducers/incidents.reducer";
import moment from "moment";
import { CheckTypes, ROUTES } from "../../shared/routes";
import { addFirstLevelTab } from "../../redux/actions/source.actions";
import { useHistory } from "react-router-dom";
import { useDispatch } from "react-redux";

type IncidentIssueRowProps = {
  issue: CheckResultEntryModel;
  incidentDetail?: IncidentModel;
}

export const IncidentIssueRow = ({ issue, incidentDetail }: IncidentIssueRowProps) => {
  const [open, setOpen] = useState(false);
  const history = useHistory()
  const dispatch = useDispatch();

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

  const getSeverityClass = (row: CheckResultEntryModel) => {
    if (row.severity === 1) return 'bg-yellow-100';
    if (row.severity === 2) return 'bg-orange-100';
    if (row.severity === 3) return 'bg-red-100';

    return '';
  };

  const closeCheckDetails = () => {
    setOpen(false);
  };

  const toggleCheckDetails = () => {
    setOpen(prev => !prev);
  };

  const navigate = () => {
    const {
      checkType = 'profiling',
      connection = '',
      schema = '',
      table = '',
    } = incidentDetail || {};
    const columnName = issue.columnName
    const defaultCheckType = "profiling";
  
    let url, value, label;
    if (columnName && columnName.length > 0) {
      url = ROUTES.COLUMN_LEVEL_PAGE(checkType ?? defaultCheckType, connection , schema, table, columnName, 'detail');
      value = ROUTES.COLUMN_LEVEL_VALUE(checkType ?? defaultCheckType, connection, schema, table, columnName);
      label = columnName;
    } else {
      url = ROUTES.TABLE_LEVEL_PAGE(checkType ?? defaultCheckType, connection, schema, table, 'advanced');
      value = ROUTES.TABLE_LEVEL_VALUE(checkType ?? defaultCheckType, connection, schema, table);
      label = table;
    }
  
    const tabData = {
      url,
      value,
      state: {},
      label,
    };  
    dispatch(addFirstLevelTab(CheckTypes.PROFILING, tabData));
    history.push(url);
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
          <a className="text-blue-700 underline" onClick={navigate}>
            {issue.checkName}
          </a>
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
          {moment(issue.executedAt).format("YYYY-MM-DD HH:mm:ss.SSS")}
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
          {issue.timeGradient}
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
          {moment(issue.timePeriod).format("YYYY-MM-DD HH:mm:ss.SSS")}
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
          {issue.dataGroup}
        </td>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
          <span>{issue.id}</span>
        </td>
      </tr>
      {open && (
        <tr>
          <td colSpan={12}>
            <CheckDetails
              checkTypes={(issue?.checkType ?? CheckTypes.PROFILING) as CheckTypes}
              connection={incidentDetail?.connection ?? ''}
              schema={incidentDetail?.schema ?? ''}
              table={incidentDetail?.table ?? ''}
              checkName={issue.checkName}
              runCheckType={issue.checkType}
              onClose={closeCheckDetails}
              category={incidentDetail?.checkCategory}
              comparisonName={issue.tableComparison}
              column={issue.columnName}
            />
          </td>
        </tr>
      )}
    </>
  );
};

type IncidentIssueListProps = {
  issues: CheckResultEntryModel[];
  filters?: IncidentIssueFilter;
  onChangeFilter: (obj: Partial<IncidentIssueFilter>) => void;
  incidentDetail?: IncidentModel;
};

export const IncidentIssueList = ({ issues, filters, onChangeFilter, incidentDetail }: IncidentIssueListProps) => {
  const handleSortChange = (orderBy: string, direction?: 'asc' | 'desc') => {
    onChangeFilter({
      order: orderBy as any,
      ...filters?.order !== orderBy ? {
        direction: 'asc',
        page: 1 }: { direction }
    })
  };

  return (
    <div>
      <table className="mt-4 w-full">
        <thead>
        <tr>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
            <SortableColumn
              className="justify-end"
              label="Column Name"
              order="columnName"
              direction={filters?.order === 'columnName' ? filters.direction : undefined}
              onChange={handleSortChange}
            />
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
            <SortableColumn
              className="justify-end"
              label="Check Name"
              order="checkName"
              direction={filters?.order === 'checkName' ? filters.direction : undefined}
              onChange={handleSortChange}
            />
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
            <SortableColumn
              className="justify-end"
              label="Executed At"
              order="executedAt"
              direction={filters?.order === 'executedAt' ? filters.direction : undefined}
              onChange={handleSortChange}
            />
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
            <SortableColumn
              className="justify-end"
              label="Time Scale"
              order="timeGradient"
              direction={filters?.order === 'timeGradient' ? filters.direction : undefined}
              onChange={handleSortChange}
            />
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
            <SortableColumn
              className="justify-end"
              label="Time Period"
              order="timePeriod"
              direction={filters?.order === 'timePeriod' ? filters.direction : undefined}
              onChange={handleSortChange}
            />
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <SortableColumn
              className="justify-end"
              label="Actual Value"
              order="actualValue"
              direction={filters?.order === 'actualValue' ? filters.direction : undefined}
              onChange={handleSortChange}
            />
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <SortableColumn
              className="justify-end"
              label="Expected Value"
              order="expectedValue"
              direction={filters?.order === 'expectedValue' ? filters.direction : undefined}
              onChange={handleSortChange}
            />
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <SortableColumn
              className="justify-end"
              label="Issue Severity Level"
              order="severity"
              direction={filters?.order === 'severity' ? filters.direction : undefined}
              onChange={handleSortChange}
            />
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
            <SortableColumn
              className="justify-end"
              label="Data Group"
              order="dataGroup"
              direction={filters?.order === 'dataGroup' ? filters.direction : undefined}
              onChange={handleSortChange}
            />
          </th>
          <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
            Id
          </th>
        </tr>
        </thead>
        <tbody>
        {issues.map((issue) => (
          <IncidentIssueRow key={issue.id} issue={issue} incidentDetail={incidentDetail} />
        ))}
        </tbody>
      </table>
    </div>
  )
};

