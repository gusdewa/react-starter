export const loadFromLocalStorage = key => {
  try {
    return JSON.parse(window.localStorage.getItem(key));
  } catch {
    return undefined;
  }
};

export const saveToLocalStorage = (key, state) => {
  if (state === undefined) {
    window.localStorage.removeItem(key);
  } else {
    const serializedState = JSON.stringify(state);
    window.localStorage.setItem(key, serializedState);
  }
};
