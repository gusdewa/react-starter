import React, { useState, useEffect } from 'react';
import { useTranslate } from 'hooks';
import { Wrapper } from './styles';

function Home() {
  const [yourAwesomeState, setyourAwesomeState] = useState('HOME');
  const trans = useTranslate('app.containers.Home');

  useEffect(() => {
    // Any logic during componentDidMount / state change
  }, []);

  return (
    <Wrapper>
      <span>Container {trans('Key', 'Home')}</span>
      <span>State: {yourAwesomeState}</span>
      <button type="button" onClick={() => setyourAwesomeState('Change Me')}>
        Change Me
      </button>
    </Wrapper>
  );
}

export default Home;
