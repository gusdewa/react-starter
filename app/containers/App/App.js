import React from 'react';
import { Router, Route, Switch } from 'react-router-dom';
import history from 'utils/history';
import GlobalStyle from 'styles/global-styles';
import NotFound from 'containers/NotFound';
import SampleCont from 'containers/SampleCont';
import Home from 'containers/Home';

const App = () => (
  <>
    <Router history={history}>
      <Switch>
        <Route exact path="/" component={Home} />
        <Route path="/sample" component={SampleCont} />
        <Route path="*" component={NotFound} />
      </Switch>
    </Router>
    <GlobalStyle />
  </>
);

export default App;
