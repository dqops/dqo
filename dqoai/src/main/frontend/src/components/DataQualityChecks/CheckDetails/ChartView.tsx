import React, { useMemo } from 'react';
import moment from "moment/moment";
import { CheckResultDetailedSingleModel } from "../../../api";
import { Line } from "react-chartjs-2";

type ChartViewProps = {
  data: CheckResultDetailedSingleModel[];
};

export const ChartView = ({ data }: ChartViewProps) => {
  const dataSource = {
    labels: data.map((item) => moment(item.executedAt).format('YYYY-MM-DD')),
    datasets: [
      {
        label: 'WARNING_LOWER_BOUND',
        data: data.map((item) => item.errorLowerBound),
        fill: true,
        borderColor: '#EBE51E',
        backgroundColor: '#EBE51E30',
      },
      {
        label: 'ERROR_LOWER_BOUND',
        data: data.map((item) => item.errorLowerBound),
        fill: true,
        borderColor: '#FF9900',
        backgroundColor: '#FF990030',
      },
      {
        label: 'FATAL_LOWER_BOUND',
        data: data.map((item) => item.fatalLowerBound),
        fill: true,
        borderColor: '#E3170A',
        backgroundColor: '#E3170A30',
      },
      {
        label: 'WARNING_UPPER_BOUND',
        data: data.map((item) => item.warningUpperBound),
        fill: true,
        borderColor: '#EBE51E',
        backgroundColor: '#EBE51E30',
      },
      {
        label: 'ERROR_UPPER_BOUND',
        data: data.map((item) => item.errorUpperBound),
        fill: true,
        borderColor: '#FF9900',
        backgroundColor: '#FF990030',
      },
      {
        label: 'FATAL_UPPER_BOUND',
        data: data.map((item) => item.fatalUpperBound),
        fill: true,
        borderColor: '#E3170A',
        backgroundColor: '#E3170A30',
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
    <div className="max-h-80 my-8">
      <Line data={dataSource} options={options} />
    </div>
  );
}