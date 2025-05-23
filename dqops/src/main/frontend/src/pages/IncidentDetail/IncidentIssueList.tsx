import moment from 'moment';
import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';
import {
  CheckResultEntryModel,
  CheckResultEntryModelTimeGradientEnum,
  IncidentModel
} from '../../api';
import SvgIcon from '../../components/SvgIcon';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { IncidentIssueFilter } from '../../redux/reducers/incidents.reducer';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { SortableColumn } from '../IncidentConnection/SortableColumn';
import IncidentCheckDetails from './IncidentCheckDetail';

type IncidentIssueRowProps = {
  issue: CheckResultEntryModel;
  incidentDetail?: IncidentModel;
  issues: CheckResultEntryModel[];
};

export const IncidentIssueRow = ({
  issue,
  incidentDetail,
  issues
}: IncidentIssueRowProps) => {
  const [open, setOpen] = useState(false);
  const history = useHistory();
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
  };

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
    setOpen((prev) => !prev);
  };

  const navigate = () => {
    const { connection = '', schema = '', table = '' } = incidentDetail || {};
    const {
      checkType = CheckTypes.PROFILING,
      columnName,
      timeGradient
    } = issue;

    let url, value, label;
    if (columnName && columnName.length > 0) {
      url = ROUTES.COLUMN_LEVEL_PAGE(
        checkType,
        connection,
        schema,
        table,
        columnName,
        checkType === CheckTypes.PROFILING
          ? 'advanced'
          : 'observability-status'
      );
      value = ROUTES.COLUMN_LEVEL_VALUE(
        checkType,
        connection,
        schema,
        table,
        columnName
      );
      label = columnName;
    } else {
      url = ROUTES.TABLE_LEVEL_PAGE(
        checkType,
        connection,
        schema,
        table,
        checkType === CheckTypes.PROFILING
          ? 'advanced'
          : 'observability-status'
      );
      value = ROUTES.TABLE_LEVEL_VALUE(checkType, connection, schema, table);
      label = table;
    }

    const tabData = {
      url,
      value,
      state: {},
      label
    };
    dispatch(addFirstLevelTab(checkType as CheckTypes, tabData));
    history.push(url);
  };
  const doesColumnHaveValues = (columnKey: keyof CheckResultEntryModel) => {
    return issues.some(
      (issue) =>
        issue[columnKey] !== undefined &&
        issue[columnKey] !== null &&
        issue[columnKey] !== ''
    );
  };
  return (
    <>
      <tr className={getSeverityClass(issue)}>
        <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
          {!open ? (
            <SvgIcon
              className="w-5"
              name="chevron-right"
              onClick={toggleCheckDetails}
            />
          ) : (
            <SvgIcon
              className="w-4"
              name="chevron-down"
              onClick={toggleCheckDetails}
            />
          )}
        </td>
        {doesColumnHaveValues('columnName') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
            {issue.columnName}
          </td>
        ) : null}
        {doesColumnHaveValues('checkName') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
            <a className="underline cursor-pointer" onClick={navigate}>
              {issue.checkName}
            </a>
          </td>
        ) : null}
        {doesColumnHaveValues('executedAt') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
            {formatDateTime(
              moment(issue.executedAt).format('YYYY-MM-DD HH:mm:ss.SSS')
            )}
          </td>
        ) : null}
        {doesColumnHaveValues('timeGradient') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
            {issue.timeGradient}
          </td>
        ) : null}
        {doesColumnHaveValues('timePeriod') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
            {formatDateTime(
              moment(issue.timePeriod).format('YYYY-MM-DD HH:mm:ss.SSS')
            )}
          </td>
        ) : null}
        {doesColumnHaveValues('actualValue') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <div>
              {typeof issue.actualValue === 'number' ? issue.actualValue : ''}
            </div>
          </td>
        ) : null}
        {doesColumnHaveValues('expectedValue') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <div>
              {typeof issue.expectedValue === 'number'
                ? issue.expectedValue
                : ''}
            </div>
          </td>
        ) : null}
        {doesColumnHaveValues('dataGroup') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
            {issue.dataGroup}
          </td>
        ) : null}
        {doesColumnHaveValues('severity') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
            {getIssueSeverityLevel(issue.severity)}
          </td>
        ) : null}
        {doesColumnHaveValues('warningLowerBound') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <div>
              {typeof issue.warningLowerBound === 'number'
                ? issue.warningLowerBound
                : ''}
            </div>
          </td>
        ) : null}
        {doesColumnHaveValues('warningUpperBound') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <div>
              {typeof issue.warningUpperBound === 'number'
                ? issue.warningUpperBound
                : ''}
            </div>
          </td>
        ) : null}
        {doesColumnHaveValues('errorLowerBound') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <div>
              {typeof issue.errorLowerBound === 'number'
                ? issue.errorLowerBound
                : ''}
            </div>
          </td>
        ) : null}
        {doesColumnHaveValues('errorUpperBound') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <div>
              {typeof issue.errorUpperBound === 'number'
                ? issue.errorUpperBound
                : ''}
            </div>
          </td>
        ) : null}
        {doesColumnHaveValues('fatalLowerBound') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <div>
              {typeof issue.fatalLowerBound === 'number'
                ? issue.fatalLowerBound
                : ''}
            </div>
          </td>
        ) : null}
        {doesColumnHaveValues('fatalUpperBound') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            <div>
              {typeof issue.fatalUpperBound === 'number'
                ? issue.fatalUpperBound
                : ''}
            </div>
          </td>
        ) : null}
        {doesColumnHaveValues('durationMs') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
            {issue.durationMs}
          </td>
        ) : null}
        {doesColumnHaveValues('id') ? (
          <td className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
            <span>{issue.id}</span>
          </td>
        ) : null}
      </tr>
      {open && (
        <tr>
          <td colSpan={12}>
            <IncidentCheckDetails
              checkTypes={
                (issue?.checkType ?? CheckTypes.PROFILING) as CheckTypes
              }
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

export const IncidentIssueList = ({
  issues,
  filters,
  onChangeFilter,
  incidentDetail
}: IncidentIssueListProps) => {
  const handleSortChange = (orderBy: string, direction?: 'asc' | 'desc') => {
    onChangeFilter({
      order: orderBy as any,
      ...(filters?.order !== orderBy
        ? {
            direction: 'asc',
            page: 1
          }
        : { direction })
    });
  };

  // Helper function to check if any issues have a value for a given column
  const doesColumnHaveValues = (columnKey: keyof CheckResultEntryModel) => {
    return issues.some(
      (issue) =>
        issue[columnKey] !== undefined &&
        issue[columnKey] !== null &&
        issue[columnKey] !== ''
    );
  };

  return (
    <div>
      <table className="mt-4 w-full">
        <thead>
          <tr>
            <th></th>
            {doesColumnHaveValues('columnName') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
                <SortableColumn
                  className="justify-start"
                  label="Column name"
                  order="columnName"
                  direction={
                    filters?.order === 'columnName'
                      ? filters.direction
                      : undefined
                  }
                  onChange={handleSortChange}
                />
              </th>
            )}
            {doesColumnHaveValues('checkName') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
                <SortableColumn
                  className="justify-start"
                  label="Check name"
                  order="checkName"
                  direction={
                    filters?.order === 'checkName'
                      ? filters.direction
                      : undefined
                  }
                  onChange={handleSortChange}
                />
              </th>
            )}
            {doesColumnHaveValues('executedAt') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
                <SortableColumn
                  className="justify-start"
                  label="Executed at"
                  order="executedAt"
                  direction={
                    filters?.order === 'executedAt'
                      ? filters.direction
                      : undefined
                  }
                  onChange={handleSortChange}
                />
              </th>
            )}
            {doesColumnHaveValues('timeGradient') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
                <SortableColumn
                  className="justify-start"
                  label="Time scale"
                  order="timeGradient"
                  direction={
                    filters?.order === 'timeGradient'
                      ? filters.direction
                      : undefined
                  }
                  onChange={handleSortChange}
                />
              </th>
            )}
            {doesColumnHaveValues('timePeriod') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
                <SortableColumn
                  className="justify-start"
                  label="Time period"
                  order="timePeriod"
                  direction={
                    filters?.order === 'timePeriod'
                      ? filters.direction
                      : undefined
                  }
                  onChange={handleSortChange}
                />
              </th>
            )}
            {doesColumnHaveValues('actualValue') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
                <SortableColumn
                  className="justify-end"
                  label="Actual value"
                  order="actualValue"
                  direction={
                    filters?.order === 'actualValue'
                      ? filters.direction
                      : undefined
                  }
                  onChange={handleSortChange}
                />
              </th>
            )}
            {doesColumnHaveValues('expectedValue') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
                <SortableColumn
                  className="justify-end"
                  label="Expected value"
                  order="expectedValue"
                  direction={
                    filters?.order === 'expectedValue'
                      ? filters.direction
                      : undefined
                  }
                  onChange={handleSortChange}
                />
              </th>
            )}
            {doesColumnHaveValues('dataGroup') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
                <SortableColumn
                  className="justify-start"
                  label="Data group"
                  order="dataGroup"
                  direction={
                    filters?.order === 'dataGroup'
                      ? filters.direction
                      : undefined
                  }
                  onChange={handleSortChange}
                />
              </th>
            )}
            {doesColumnHaveValues('severity') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
                <SortableColumn
                  className="justify-start"
                  label="Issue severity level"
                  order="severity"
                  direction={
                    filters?.order === 'severity'
                      ? filters.direction
                      : undefined
                  }
                  onChange={handleSortChange}
                />
              </th>
            )}
            {doesColumnHaveValues('warningLowerBound') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
                <span>
                  Warning
                  <br />
                  Lower Threshold
                </span>
              </th>
            )}
            {doesColumnHaveValues('warningUpperBound') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
                <span>
                  Warning
                  <br />
                  Upper Threshold
                </span>
              </th>
            )}
            {doesColumnHaveValues('errorLowerBound') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
                <span>
                  Error
                  <br />
                  Lower Threshold
                </span>
              </th>
            )}
            {doesColumnHaveValues('errorUpperBound') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
                <span>
                  Error
                  <br />
                  Upper Threshold
                </span>
              </th>
            )}
            {doesColumnHaveValues('fatalLowerBound') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
                <span>
                  Fatal
                  <br />
                  Lower Threshold
                </span>
              </th>
            )}
            {doesColumnHaveValues('fatalUpperBound') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-right">
                <span>
                  Fatal
                  <br />
                  Upper Threshold
                </span>
              </th>
            )}
            {doesColumnHaveValues('durationMs') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700">
                Duration Ms
              </th>
            )}
            {doesColumnHaveValues('id') && (
              <th className="text-sm px-4 !py-2 whitespace-nowrap text-gray-700 text-left">
                Id
              </th>
            )}
          </tr>
        </thead>
        <tbody>
          {issues.map((issue) => (
            <IncidentIssueRow
              key={issue.id}
              issue={issue}
              incidentDetail={incidentDetail}
              issues={issues}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};

function formatDateTime(dateTime: string): string {
  if (dateTime.endsWith('00:00:00.000')) {
    return dateTime.split(' ')[0];
  }

  return dateTime;
}
