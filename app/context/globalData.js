/* eslint-disable react/prop-types */
import React, { useReducer, createContext } from 'react';
import { GLOBAL_DATA_SET } from 'constants/actions';

const initialPage = {};

const reducer = (state, action) => {
  switch (action.type) {
    case GLOBAL_DATA_SET:
      return { ...state, ...action.data };
    default:
      return state;
  }
};

const GlobalDataContext = createContext();
const GlobalDataProvider = ({ children }) => {
  const contextValue = useReducer(reducer, initialPage);
  return (
    <GlobalDataContext.Provider value={contextValue}>
      {children}
    </GlobalDataContext.Provider>
  );
};

export { GlobalDataContext as default, GlobalDataProvider };
