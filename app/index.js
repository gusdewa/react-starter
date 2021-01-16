/* eslint-disable no-undef */
import '@babel/polyfill';
import React from 'react';
import ReactDOM from 'react-dom';
import { ThemeProvider } from 'styled-components';
import 'sanitize.css/sanitize.css';
import { LanguageProvider, GlobalDataProvider } from 'context';
import App from 'containers/App';
import theme from 'styles/theme';
import { ELEMENT_ID } from 'constants/elementDOM';
import { translationMessages } from './global-i18n';

// Load the favicon and the .htaccess file
import '!file-loader?name=[name].[ext]!./favicon.ico';

const mountedNode = document.getElementById(ELEMENT_ID);
const render = messages => {
  ReactDOM.render(
    <LanguageProvider messages={messages}>
      <ThemeProvider theme={theme}>
        <GlobalDataProvider>
          <App />
        </GlobalDataProvider>
      </ThemeProvider>
    </LanguageProvider>,
    mountedNode,
  );
};

if (module.hot) {
  module.hot.accept(['./global-i18n', 'containers/App'], () => {
    ReactDOM.unmountComponentAtNode(mountedNode);
    render(translationMessages);
  });
}

// Chunked polyfill for browsers without Intl support
if (!window.Intl) {
  new Promise(resolve => {
    resolve(import('intl'));
  })
    .then(() => Promise.all([import('intl/locale-data/jsonp/en.js')]))
    .then(() => render(translationMessages))
    .catch(err => {
      throw err;
    });
} else {
  render(translationMessages);
}
