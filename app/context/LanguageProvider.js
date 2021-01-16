import React from 'react';
import PropTypes from 'prop-types';
import { IntlProvider } from 'react-intl';

export function LanguageProvider(props) {
  const defaultLocale = 'en';
  const locale = defaultLocale;

  return (
    <IntlProvider
      locale={locale}
      key={locale}
      messages={props.messages[locale]}
    >
      {React.Children.only(props.children)}
    </IntlProvider>
  );
}

LanguageProvider.propTypes = {
  messages: PropTypes.object,
  children: PropTypes.element.isRequired,
};

LanguageProvider.defaultProps = {
  messages: {},
};

export default LanguageProvider;
