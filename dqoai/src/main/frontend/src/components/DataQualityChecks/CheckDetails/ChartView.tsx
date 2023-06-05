import React, { useMemo, useRef } from 'react';
import * as Highcharts from 'highcharts';
import HighchartsReact from 'highcharts-react-official';
import moment from "moment/moment";
import { CheckResultDetailedSingleModel } from "../../../api";

type ChartViewProps = {
  data: CheckResultDetailedSingleModel[];
};

export const ChartView = ({ data }: ChartViewProps) => {
  const chartComponentRef = useRef<HighchartsReact.RefObject>(null);

  console.log('data', data);
  const options = useMemo(() => ({
    chart: {
      type: 'area',
      marginLeft: 10,
      marginRight: 10,
    },

    title: {
      text: '',
    },
    credits: {
      enabled: false,
    },
    xAxis: {
      categories: data.map((item) => moment(item.executedAt).format('YYYY-MM-DD')),
    },
    yAxis: {
      allowDecimals: false,
      min: 0,
      title: {
        text: ''
      }
    },
    plotOptions: {
      series: {
        pointStart: 2012
      },
      area: {
        stacking: 'normal',
        lineColor: '#666666',
        lineWidth: 1,
        marker: {
          lineWidth: 1,
          lineColor: '#666666'
        }
      }
    },    series: [{
      name: 'WARNING_LOWER_BOUND',
      color: '#EBE51E',
      opacity: 0.5,
      data: data.map((item) => item.warningLowerBound),
    }, {
      name: 'ERROR_LOWER_BOUND',
      color: '#FF9900',
      opacity: 0.5,
      data: data.map((item) => item.errorLowerBound),
    }, {
      name: 'FATAL_LOWER_BOUND',
      color: '#E3170A',
      opacity: 0.5,
      data: data.map((item) => item.fatalLowerBound),
    },{
      name: 'WARNING_UPPER_BOUND',
      color: '#EBE51E',
      opacity: 0.8,
      data: data.map((item) => item.warningUpperBound),
    }, {
      name: 'ERROR_UPPER_BOUND',
      opacity: 0.8,
      color: '#FF9900',
      data: data.map((item) => item.errorUpperBound),
    }, {
      name: 'FATAL_UPPER_BOUND',
      color: '#E3170A',
      opacity: 0.8,
      data: data.map((item) => item.fatalUpperBound),
    }]
  }), [data]);

  return (
    <div>
      <HighchartsReact
        highcharts={Highcharts}
        options={options}
        ref={chartComponentRef}
      />
    </div>
  )
}