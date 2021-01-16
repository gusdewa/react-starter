import { useContext } from 'react';
import { get } from 'lodash/fp';
import GlobalDataContext from 'context/globalData';
import { GLOBAL_DATA_SET } from 'constants/actions';

export default () => {
  const contextValue = useContext(GlobalDataContext);
  const [currentData, dispatch] = contextValue;

  return {
    get: path => get(path)(currentData),
    set: updatedData =>
      dispatch({
        type: GLOBAL_DATA_SET,
        data: updatedData,
      }),
  };
};
