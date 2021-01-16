import { useIntl } from 'react-intl';

export default (scopePrefix = '') => {
  const intl = useIntl();
  const translate = (id, values) =>
    intl.formatMessage(
      { id: `${scopePrefix}.${id}`, defaultMessage: values },
      values,
    );
  return translate;
};
