import React from 'react';
import { CheckResultEntryModel } from '../../../api';
import { Line } from 'react-chartjs-2';
import { useTree } from '../../../contexts/treeContext';

type ChartViewProps = {
  data: CheckResultEntryModel[];
};

export const ChartView = ({ data }: ChartViewProps) => {
  const isFatalLowerExist = data.find((item) => item.fatalLowerBound);
  const isErrorLowerExist = data.find((item) => item.errorLowerBound);

  const isFatalUpperExist = data.find((item) => item.fatalUpperBound);
  const isErrorUpperExist = data.find((item) => item.errorUpperBound);
  const { sidebarWidth } = useTree();

  const dataSource: any = {
    datasets: [
      data.some((item) => item.fatalLowerBound !== undefined)
        ? {
            label: 'Fatal (lower bound)',
            data: data.map((item) => ({
              x: item.timePeriod,
              y: item.fatalLowerBound
            })),
            fill: 'start',
            borderColor: '#E3170A',
            backgroundColor: '#E3170A30'
          }
        : null,
      data.some((item) => item.errorLowerBound !== undefined)
        ? {
            label: 'Error (lower bound)',
            data: data.map((item) => ({
              x: item.timePeriod,
              y: item.errorLowerBound
            })),
            fill: isFatalLowerExist ? '-1' : 'start',
            borderColor: '#FF9900',
            backgroundColor: '#FF990030'
          }
        : null,
      data.some((item) => item.warningLowerBound !== undefined)
        ? {
            label: 'Warning (lower bound)',
            data: data.map((item) => ({
              x: item.timePeriod,
              y: item.warningLowerBound
            })),
            fill: isFatalLowerExist || isErrorLowerExist ? '-1' : 'start',
            borderColor: '#EBE51E',
            backgroundColor: '#EBE51E30'
          }
        : null,
      data.some((item) => item.actualValue !== undefined)
        ? {
            label: 'Actual value',
            data: data.map((item) => ({
              x: item.timePeriod,
              y: item.actualValue
            })),
            fill: false,
            borderColor: 'rgb(54, 162, 235)',
            backgroundColor: 'rgb(54, 162, 235)'
          }
        : null,
      data.some((item) => item.expectedValue !== undefined)
        ? {
            label: 'Expected value',
            data: data.map((item) => ({
              x: item.timePeriod,
              y: item.expectedValue
            })),
            fill: false,
            borderColor: 'rgb(201, 203, 207)',
            backgroundColor: 'rgb(201, 203, 207)'
          }
        : null,
      data.some((item) => item.warningUpperBound !== undefined)
        ? {
            label: 'Warning (upper bound)',
            data: data.map((item) => ({
              x: item.timePeriod,
              y: item.warningUpperBound
            })),
            fill: isErrorUpperExist || isFatalUpperExist ? '+1' : 'end',
            borderColor: '#EBE51E',
            backgroundColor: '#EBE51E30'
          }
        : null,
      data.some((item) => item.errorUpperBound !== undefined)
        ? {
            label: 'Error (upper bound)',
            data: data.map((item) => ({
              x: item.timePeriod,
              y: item.errorUpperBound
            })),
            fill: isFatalUpperExist ? '+1' : 'end',
            borderColor: '#FF9900',
            backgroundColor: '#FF990030'
          }
        : null,
      data.some((item) => item.fatalUpperBound !== undefined)
        ? {
            label: 'Fatal (upper bound)',
            data: data.map((item) => ({
              x: item.timePeriod,
              y: item.fatalUpperBound
            })),
            fill: 'end',
            borderColor: '#E3170A',
            backgroundColor: '#E3170A30'
          }
        : null
    ].filter((item) => item !== null)
  };
  const customWidth = `calc(100vw - ${sidebarWidth + 150}px)`;
  const customHeight = '370px';

  const aspectRatioValue = parseFloat(customWidth) / parseFloat(customHeight);

  const options = {
    aspectRatio: aspectRatioValue,
    plugins: {
      title: {
        display: false
      },
      filler: {
        propagate: false
      },
      'samples-filler-analyser': {
        target: 'chart-analyser'
      }
    },
    interaction: {
      intersect: false
    },
    scales: {
      x: {
        type: 'time',
        time: {
          unit: 'day'
        }
      },
      y: {
        stacked: false
      }
    }
  };

  return (
    <div className="my-8">
      <Line
        data={dataSource}
        options={options as any}
        width={customWidth}
        height={customHeight}
      />
    </div>
  );
};
