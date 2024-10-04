import moment from 'moment';

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

export const getColor = (status: number | undefined) => {
  switch (status) {
    case 0:
      return 'bg-teal-500';
    case 1:
      return 'bg-yellow-900';
    case 2:
      return 'bg-orange-900';
    case 3:
      return 'bg-red-900';
    case 4:
      return 'bg-black';
    default:
      return '';
  }
};
