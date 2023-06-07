import React from 'react';
import { CheckResultDetailedSingleModel } from "../../../api";
import { Line } from "react-chartjs-2";

type ChartViewProps = {
  data: CheckResultDetailedSingleModel[];
};

export const ChartView = ({ data }: ChartViewProps) => {
  const isFatalLowerExist = data.find(item => item.fatalLowerBound);
  const isErrorLowerExist = data.find(item => item.errorLowerBound);

  const isFatalUpperExist = data.find(item => item.fatalUpperBound);
  const isErrorUpperExist = data.find(item => item.errorUpperBound);

  const dataSource: any = {
    datasets: [
      {
        label: 'FATAL_LOWER_BOUND',
        data: data.map((item) => ({
          x: item.timePeriod,
          y: item.fatalLowerBound
        })),
        fill: 'start',
        borderColor: '#E3170A',
        backgroundColor: '#E3170A30',
      },
      {
        label: 'ERROR_LOWER_BOUND',
        data: data.map((item) => ({
          x: item.timePeriod,
          y: item.errorLowerBound
        })),
        fill: isFatalLowerExist ? '-1' : 'start',
        borderColor: '#FF9900',
        backgroundColor: '#FF990030',
      },
      {
        label: 'WARNING_LOWER_BOUND',
        data: data.map((item) => ({
          x: item.timePeriod,
          y: item.errorLowerBound
        })),
        fill: isFatalLowerExist || isErrorLowerExist ? '-1' : 'start',
        borderColor: '#EBE51E',
        backgroundColor: '#EBE51E30',
      },
      {
        label: 'ACTUAL VALUE',
        data: data.map((item) => ({
          x: item.timePeriod,
          y: item.actualValue
        })),
        fill: false,
        borderColor: 'rgb(54, 162, 235)',
        backgroundColor: 'rgb(54, 162, 235)',
      },
      {
        label: 'Expected VALUE',
        data: data.map((item) => ({
          x: item.timePeriod,
          y: item.expectedValue
        })),
        fill: false,
        borderColor: 'rgb(201, 203, 207)',
        backgroundColor: 'rgb(201, 203, 207)',
      },
      {
        label: 'WARNING_UPPER_BOUND',
        data: data.map((item) => ({
          x: item.timePeriod,
          y: item.warningUpperBound
        })),
        fill: isErrorUpperExist || isFatalUpperExist ? '+1' : 'end',
        borderColor: '#EBE51E',
        backgroundColor: '#EBE51E30',
      },
      {
        label: 'ERROR_UPPER_BOUND',
        data: data.map((item) => ({
          x: item.timePeriod,
          y: item.errorUpperBound
        })),
        fill: isFatalUpperExist ? '+1' : 'end',
        borderColor: '#FF9900',
        backgroundColor: '#FF990030',
      },
      {
        label: 'FATAL_UPPER_BOUND',
        data: data.map((item) => ({
          x: item.timePeriod,
          y: item.fatalUpperBound
        })),
        fill: 'end',
        borderColor: '#E3170A',
        backgroundColor: '#E3170A30',
      }
    ]
  };

  const options = {
    plugins: {
      title: {
        display: false,
      },
      filler: {
        propagate: false
      },
      'samples-filler-analyser': {
        target: 'chart-analyser'
      }
    },
    interaction: {
      intersect: false,
    },
    scales: {
      x: {
        type: 'time',
      },
      y: {
        stacked: true,
      },
    }
  };

  console.log('data', dataSource);
  return (
    <div className="my-8">
      <Line data={dataSource} options={options as any} />
    </div>
  );
}