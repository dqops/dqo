import { Bar } from "react-chartjs-2";
import React from "react";
import moment from "moment/moment";
import { IncidentDailyIssuesCount, IncidentIssueHistogramModel } from "../../api";

type BarChartProps = {
  histograms: IncidentIssueHistogramModel
};

export const BarChart = ({ histograms }: BarChartProps) => {
  const data = {
    labels: (Object.keys(histograms?.days || {})).map(item => moment(item).format('MMM/DD')),
    datasets: [
      {
        label: 'Warnings',
        data: Object.values(histograms?.days || {}).map((item: IncidentDailyIssuesCount) => item.warnings || 1),
        backgroundColor: '#EBE51E',
        barThickness: 60,
      },
      {
        label: 'Errors',
        data: Object.values(histograms?.days || {}).map((item: IncidentDailyIssuesCount) => item.errors || 1),
        backgroundColor: '#FF9900',
        barThickness: 60,
      },
      {
        label: 'Fatals',
        data: Object.values(histograms?.days || {}).map((item: IncidentDailyIssuesCount) => item.fatals || 1),
        backgroundColor: '#E3170A',
        barThickness: 60,
      },
    ]
  };

  const options = {
    plugins: {
      title: {
        display: false,
      },
      legend: {
        display: false
      }
    },
    scales: {
      x: {
        stacked: true,
        grid: {
          lineWidth: 0,
        }
      },
      y: {
        stacked: true,
        grid: {
          lineWidth: 0,
          drawTicks: false,
        },
        title: {
          display: false,
        },
        ticks: {
          display: false
        }
      },
    }
  };

  return (
    <div className="flex justify-center items-center">
      <Bar data={data} options={options} />
    </div>
  );
};
