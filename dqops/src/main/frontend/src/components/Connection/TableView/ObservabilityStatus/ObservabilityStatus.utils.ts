import moment from 'moment';
import { CheckResultsOverviewDataModelStatusesEnum } from '../../../../api';

export const calculateDateRange = (month: string) => {
  if (!month) return { startDate: '', endDate: '' };

  if (month === 'Last 3 months') {
    return {
      startDate: moment().add(-3, 'month').format('YYYY-MM-DD'),
      endDate: moment().format('YYYY-MM-DD')
    };
  }

  return {
    startDate: moment(month, 'MMMM YYYY').format('YYYY-MM-DD'),
    endDate: moment(month, 'MMMM YYYY').endOf('month').format('YYYY-MM-DD')
  };
};

export const getColor = (
  status?: CheckResultsOverviewDataModelStatusesEnum
) => {
  switch (status) {
    case 'valid':
      return 'bg-teal-500';
    case 'warning':
      return 'bg-yellow-900';
    case 'error':
      return 'bg-orange-900';
    case 'fatal':
      return 'bg-red-900';
    case 'execution_error':
      return 'bg-black';
    default:
      return 'bg-black';
  }
};
