import React, { useEffect, useMemo, useRef } from "react";
import { useSelector } from "react-redux";
import { getFirstLevelIncidentsState } from "../../redux/selectors";
import { getIncidentsHistograms, setIncidentsHistogramFilter } from "../../redux/actions/incidents.actions";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { useParams } from "react-router-dom";
import * as Highcharts from 'highcharts';
import HighchartsReact from 'highcharts-react-official';
import { IncidentDailyIssuesCount, IncidentIssueHistogramModel } from "../../api";
import moment from "moment";
import { IncidentHistogramFilter } from "../../redux/reducers/incidents.reducer";
import SectionWrapper from "../../components/Dashboard/SectionWrapper";
import clsx from "clsx";

export const HistogramChart = () => {
  const { connection, year: strYear, month: strMonth, id: incidentId }: { connection: string, year: string, month: string, id: string } = useParams();
  const year = parseInt(strYear, 10);
  const month = parseInt(strMonth, 10);
  const { histograms, histogramFilter }: { histograms: IncidentIssueHistogramModel, histogramFilter: IncidentHistogramFilter } = useSelector(getFirstLevelIncidentsState);
  const dispatch = useActionDispatch();
  const chartComponentRef = useRef<HighchartsReact.RefObject>(null);

  useEffect(() => {
    dispatch(
      setIncidentsHistogramFilter({
        connection,
        year,
        month,
        incidentId
      })
    );
  }, [])

  useEffect(() => {
    if (!histogramFilter) return;

    dispatch(getIncidentsHistograms(histogramFilter));
  }, [histogramFilter]);

  const options = useMemo(() => ({
    chart: {
      type: 'column',
      height: 200,
      marginLeft: 10,
      marginRight: 10,
      marginBottom: 30,
    },

    title: {
      text: '',
    },
    legend: {
      enabled: false,
    },
    credits: {
      enabled: false,
    },
    xAxis: {
      categories: (Object.keys(histograms?.days || {})).map(item => moment(item).format('MMM/DD')),
    },
    yAxis: {
      allowDecimals: false,
      min: 0,
      title: {
        text: ''
      }
    },
    plotOptions: {
      column: {
        stacking: 'normal',
      },
    },
    series: [{
      name: 'Warnings',
      color: '#EBE51E',
      data: Object.values(histograms?.days || {}).map((item: IncidentDailyIssuesCount) => item.warnings || 1),
    }, {
      name: 'Errors',
      color: '#FF9900',
      data: Object.values(histograms?.days || {}).map((item: IncidentDailyIssuesCount) => item.errors || 1),
    }, {
      name: 'Fatals',
      color: '#E3170A',
      data: Object.values(histograms?.days || {}).map((item: IncidentDailyIssuesCount) => item.fatals || 1),
    }]
  }), [histograms]);

  const onChangeFilter = (obj: Partial<IncidentHistogramFilter>) => {
    dispatch(
      setIncidentsHistogramFilter({
        ...histogramFilter,
        ...obj,
      })
    );
  };

  return (
    <div className="grid grid-cols-4 px-4 gap-4 my-6">
      <div className="col-span-2">
        <HighchartsReact
          highcharts={Highcharts}
          options={options}
          ref={chartComponentRef}
        />
      </div>
      <SectionWrapper title="Filter by columns">
        {Object.keys(histograms?.columns || {}).map((column, index) => (
          <div
            className={clsx("flex gap-2 mb-2 cursor-pointer", {
              'font-bold text-gray-700': histogramFilter.column === column,
              'text-gray-500': histogramFilter.column && histogramFilter.column !== column,
            })}
            key={index}
            onClick={() => onChangeFilter({ column: histogramFilter.column === column ? '' : column })}
          >
            <span>{column}</span>({histograms?.columns?.[column]})
          </div>
        ))}
      </SectionWrapper>
      <SectionWrapper title="Filter by check name">
        {Object.keys(histograms?.checks || {}).map((check, index) => (
          <div
            className={clsx("flex gap-2 mb-2 cursor-pointer", {
              'font-bold text-gray-700': histogramFilter.check === check,
              'text-gray-500': histogramFilter.check && histogramFilter.check !== check,
            })}
            key={index}
            onClick={() => onChangeFilter({ check: histogramFilter.check === check ? '' : check })}
          >
            <span>{check}</span>({histograms?.checks?.[check]})
          </div>
        ))}
      </SectionWrapper>
    </div>
  );
};
