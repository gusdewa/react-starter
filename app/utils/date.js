import moment from 'moment';

export const format = (date, customformat = 'DD MMM YYYY') => {
  const inputDate = new Date(date);
  return moment(inputDate).format(customformat);
};

export const difference = date => {
  const today = moment().startOf('day');
  const differenceFrom = moment(date).startOf('day');

  const differenceInDays = today.diff(differenceFrom, 'days');

  if (differenceInDays === 0) {
    return { key: 'today', days: differenceInDays };
  }
  if (differenceInDays > 0) {
    return { key: 'daysAgo', days: differenceInDays };
  }
  return { key: 'inDays', days: -differenceInDays };
};

export const getKeyDifference = dpd => {
  if (dpd > 0) return 'daysAgo';
  if (dpd < 0) return 'inDays';
  return 'today';
};

export const subtractNDaysFromNow = delta => {
  const deltaDate = moment().subtract(delta, 'days');
  return format(deltaDate);
};

export const addNDaysFromNow = delta => {
  const deltaDate = moment().add(delta, 'days');
  return format(deltaDate);
};

export const getCCDate = date => moment(date, 'YYYY-MM-DD').format('MM/YY');

export default {
  format,
  difference,
};
