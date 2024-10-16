import moment from 'moment/moment';
import React, { useState } from 'react';
import { Bar } from 'react-chartjs-2';
import { HistogramDailyIssuesCount, IssueHistogramModel } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setIncidentsHistogram } from '../../redux/actions/incidents.actions';

type BarChartProps = {
  histograms: IssueHistogramModel;
  setHistograms?: (histograms: IssueHistogramModel) => void;
};

export const BarChart = ({ histograms, setHistograms }: BarChartProps) => {
  const [savedHistograms, setSavedHistograms] =
    useState<IssueHistogramModel | null>(null);
  const dispatch = useActionDispatch();
  const data = {
    labels: Object.keys(histograms?.days || {}).map((item) =>
      moment(item).format('MMM/DD')
    ),
    datasets: [
      {
        label: 'Warnings',
        data: Object.values(histograms?.days || {}).map(
          (item: HistogramDailyIssuesCount) => item.warnings
        ),
        backgroundColor: '#EBE51E',
        barPercentage: 0.9
      },
      {
        label: 'Errors',
        data: Object.values(histograms?.days || {}).map(
          (item: HistogramDailyIssuesCount) => item.errors
        ),
        backgroundColor: '#FF9900',
        barPercentage: 0.9
      },
      {
        label: 'Fatals',
        data: Object.values(histograms?.days || {}).map(
          (item: HistogramDailyIssuesCount) => item.fatals
        ),
        backgroundColor: '#E3170A',
        barPercentage: 0.9
      }
    ]
  };

  const options = {
    plugins: {
      title: {
        display: false
      },
      legend: {
        display: false
      }
    },
    scales: {
      x: {
        stacked: true,
        grid: {
          lineWidth: 0
        }
      },
      y: {
        stacked: true,
        grid: {
          lineWidth: 0,
          drawTicks: false
        },
        title: {
          display: false
        },
        ticks: {
          display: false
        }
      }
    },
    onClick: (event: any, elements: any) => {
      if (elements.length > 0) {
        const chartElement = elements[0];
        const dataIndex = chartElement.index;
        const label = data.labels[dataIndex];
        const year = new Date().getFullYear();
        const formattedDate = moment(`${label}/${year}`, 'MMM/DD/YYYY').format(
          'YYYY-MM-DD'
        );
        if (
          Object.keys(histograms?.days ?? {})?.length === 1 &&
          savedHistograms
        ) {
          if (setHistograms) {
            setHistograms(savedHistograms);
          } else {
            dispatch(setIncidentsHistogram(savedHistograms));
          }
          setSavedHistograms(null);
          return;
        }
        setSavedHistograms(histograms);
        const filteredHistorgrams = {
          ...histograms,
          days: { [formattedDate]: histograms?.days?.[formattedDate] ?? {} }
        };
        if (setHistograms) {
          setHistograms(filteredHistorgrams);
        } else {
          dispatch(setIncidentsHistogram(filteredHistorgrams));
        }
      }
    }
  };

  return (
    <div className="flex justify-center items-center">
      <Bar data={data} options={options} />
    </div>
  );
};
