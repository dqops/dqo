import clsx from 'clsx';
import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { IssueHistogramModel } from '../../api';
import SectionWrapper from '../../components/Dashboard/SectionWrapper';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getIncidentsHistograms,
  setIncidentsHistogramFilter
} from '../../redux/actions/incidents.actions';
import { IRootState } from '../../redux/reducers';
import {
  IncidentHistogramFilter,
  IncidentIssueFilter
} from '../../redux/reducers/incidents.reducer';
import { getFirstLevelIncidentsState } from '../../redux/selectors';
import { useDecodedParams } from '../../utils';
import { BarChart } from './BarChart';

type HistogramChartProps = {
  onChangeFilter: (obj: Partial<IncidentIssueFilter>) => void;
  days: number;
};
export const HistogramChart = ({
  onChangeFilter: changeIssueFilter,
  days
}: HistogramChartProps) => {
  const {
    connection,
    year: strYear,
    month: strMonth,
    id: incidentId
  }: {
    connection: string;
    year: string;
    month: string;
    id: string;
  } = useDecodedParams();
  const year = parseInt(strYear, 10);
  const month = parseInt(strMonth, 10);
  const {
    histograms,
    histogramFilter
  }: {
    histograms: IssueHistogramModel;
    histogramFilter: IncidentHistogramFilter;
  } = useSelector(getFirstLevelIncidentsState);
  const { activeTab } = useSelector((state: IRootState) => state.incidents);
  const dispatch = useActionDispatch();

  useEffect(() => {
    dispatch(
      setIncidentsHistogramFilter({
        connection,
        year,
        month,
        incidentId,
        days
      })
    );
  }, [connection, year, month, incidentId, days]);

  useEffect(() => {
    if (!histogramFilter) return;
    dispatch(getIncidentsHistograms(histogramFilter ?? {}));
  }, [histogramFilter, activeTab]);

  const onChangeFilter = (obj: Partial<IncidentHistogramFilter>) => {
    dispatch(
      setIncidentsHistogramFilter({
        ...histogramFilter,
        ...obj
      })
    );
    changeIssueFilter({
      ...obj,
      page: 1
    });
  };

  return (
    <div className="grid grid-cols-4 px-4 gap-4 my-6">
      <div className="col-span-2">
        <BarChart histograms={histograms} />
      </div>
      <SectionWrapper title="Filter by columns" className="text-sm">
        {Object.keys(histograms?.columns || {}).map((column, index) => (
          <div
            className={clsx(
              'flex gap-2 mb-2 cursor-pointer whitespace-normal break-all',
              {
                'font-bold text-gray-700': histogramFilter?.column === column,
                'text-gray-500':
                  histogramFilter?.column && histogramFilter?.column !== column
              }
            )}
            key={index}
            onClick={() =>
              onChangeFilter({
                column: histogramFilter?.column === column ? undefined : column
              })
            }
          >
            <span>
              {column.length === 0 ? '(no column name)' : ''}
              {column}
            </span>
            ({histograms?.columns?.[column]})
          </div>
        ))}
      </SectionWrapper>
      <SectionWrapper title="Filter by check name" className="text-sm">
        {Object.keys(histograms?.checks || {}).map((check, index) => (
          <div
            className={clsx(
              'flex gap-2 mb-2 cursor-pointer whitespace-normal break-all',
              {
                'font-bold text-gray-700': histogramFilter?.check === check,
                'text-gray-500':
                  histogramFilter?.check && histogramFilter?.check !== check
              }
            )}
            key={index}
            onClick={() =>
              onChangeFilter({
                check: histogramFilter?.check === check ? '' : check
              })
            }
          >
            <span>{check}</span>({histograms?.checks?.[check]})
          </div>
        ))}
      </SectionWrapper>
    </div>
  );
};
