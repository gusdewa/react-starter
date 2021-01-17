import React, { useState, useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import { useTranslate } from 'hooks';
import { Wrapper } from './styles';

function Home() {
  const [yourAwesomeState, setyourAwesomeState] = useState('HOME');
  const trans = useTranslate('app.containers.Home');
  const history = useHistory();

  useEffect(() => {
    // Any logic during componentDidMount / state change
  }, []);

  const navigateToSamplePage = () => {
    history.push('/sample');
  };

  return (
    <Wrapper>
      <span>Container {trans('Label', 'Home')}</span>
      <span>State: {yourAwesomeState}</span>
      <button type="button" onClick={() => setyourAwesomeState('Change Me')}>
        Change Me
      </button>
      <button type="button" onClick={navigateToSamplePage}>
        {trans('GoToSamplePage', 'Go to Sample Page')}
      </button>
    </Wrapper>
  );
}

export default Home;
