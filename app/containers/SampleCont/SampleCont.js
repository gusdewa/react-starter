import React from 'react';
import { useTranslate, useGlobalData } from 'hooks';
import { SAMPLE_DATE_PROP, SAMPLE_OBJECT_PROP } from 'constants/globalDataKeys';
import SampleDeepComponentThatYouCantPassViaProp from 'components/SampleDeepComponentThatYouCantPassViaProp';
import { Wrapper } from './styles';

function SampleCont() {
  const trans = useTranslate('app.containers.SampleCont');
  const globalData = useGlobalData();

  const handleButtonClick = () => {
    // Pass an object here by calling set function
    globalData.set({
      [SAMPLE_DATE_PROP]: new Date().toUTCString(),
    });
  };

  const handleAnotherButtonClick = () => {
    // Pass an object here by calling set function
    globalData.set({
      [SAMPLE_OBJECT_PROP]: {
        one: 1,
        two: {
          twoPointOne: 2.1,
        },
      },
    });
  };

  return (
    <Wrapper>
      <span>Container {trans('Key', 'SampleCont')}</span>
      <button type="button" onClick={handleButtonClick}>
        Set global data date
      </button>
      <br />
      <button type="button" onClick={handleAnotherButtonClick}>
        Set global data object
      </button>
      <br />
      <SampleDeepComponentThatYouCantPassViaProp />
    </Wrapper>
  );
}

export default SampleCont;
