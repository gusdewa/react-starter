import React from 'react';
import { useTranslate, useGlobalData } from 'hooks';
import { SAMPLE_DATE_PROP, SAMPLE_OBJECT_PROP } from 'constants/globalDataKeys';
import { Wrapper } from './styles';

function SampleDeepComponentThatYouCantPassViaProp() {
  const globalData = useGlobalData();

  const trans = useTranslate(
    'app.components.SampleDeepComponentThatYouCantPassViaProp',
  );

  return (
    <Wrapper>
      <span>Component SampleDeepComponentThatYouCantPassViaProp</span>
      {trans('Key', 'SampleDeepComponentThatYouCantPassViaProp anything')}
      <br />
      <br />
      <span>Date sample prop is: </span>
      <strong>
        {/* Retrive value of global data by passing the unique */
        // eslint-disable-next-line prettier/prettier
          globalData.get(SAMPLE_DATE_PROP)}
      </strong>
      <br />
      <span>Object sample prop is: </span>
      <strong>
        {/* Retrive value of global data by passing the unique */
        // eslint-disable-next-line prettier/prettier
          JSON.stringify(globalData.get(SAMPLE_OBJECT_PROP))}
      </strong>
    </Wrapper>
  );
}

SampleDeepComponentThatYouCantPassViaProp.propTypes = {
  // Put props validation here
};

export default SampleDeepComponentThatYouCantPassViaProp;
