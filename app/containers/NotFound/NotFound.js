import React, { useState, useEffect } from 'react';
import { useTranslate } from 'hooks';
import { Wrapper } from './styles';

function NotFound() {
  const [yourAwesomeState, setyourAwesomeState] = useState('NOTFOUND');
  const trans = useTranslate('app.containers.NotFound');

  useEffect(() => {
    // Any logic during componentDidMount / state change
  }, []);

  return (
    <Wrapper>
      <span>Container {trans('Key', 'NotFound')}</span>
      <span>State: {yourAwesomeState}</span>
      <button type="button" onClick={() => setyourAwesomeState('Change Me')}>
        Change Me
      </button>
    </Wrapper>
  );
}

export default NotFound;
