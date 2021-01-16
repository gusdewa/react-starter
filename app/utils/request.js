import { flow, split, join, reduce, keys, get } from 'lodash/fp';
//

/**
 * Parses the JSON returned by a network request
 *
 * @param  {object} response A response from a network request
 *
 * @return {object}          The parsed JSON from the request
 */
function parseJSON(response) {
  const status = get('status')(response);
  if (status === 204 || status === 205) {
    return null;
  }
  try {
    return response.json();
  } catch (error) {
    const errorDetails = { response, error };
    throw errorDetails;
  }
}

const parseResult = async response => {
  const result = await parseJSON(response);
  return get('data')(result);
};

/**
 * Checks if a network request came back fine, and throws an error if not
 *
 * @param  {object} response   A response from a network request
 *
 * @return {object|undefined} Returns either the response, or throws an error
 */
function checkStatus(response) {
  const status = get('status')(response);
  if (status >= 200 && status < 300) {
    return response;
  }
  const error = new Error(get('statusText')(response));
  error.response = response;
  throw error;
}

/**
 * Generate a full request URL with base parameter
 * @param {string} requestURL
 * @param {object} params
 */
const buildRequestURL = (requestURL, params) => {
  const relativeURL = reduce((acc, curKey) => {
    const replacedURL = flow(
      split(`:${curKey}`), // replace parameterized string
      join(params[curKey]),
    )(acc);
    return replacedURL;
  }, requestURL)(keys(params));
  // eslint-disable-next-line no-undef
  return `${SERVICE_URL}${relativeURL}`;
};

/**
 * allow request GET using query params
 * @param {object} qs
 */
export const querySearhParams = qs =>
  Object.keys(qs)
    .map(key => `${key}=${qs[key]}`)
    .join('&');

/**
 * Requests a URL, returning a promise
 *
 * @param  {string} url       The URL we want to request
 * @param  {object} [options] The options we want to pass to "fetch"
 *
 * @return {object}           The response data
 */
export default async function request({ url, params = null, options }) {
  let requestURL = buildRequestURL(url, params);
  if (get('qs')(options)) {
    requestURL = `${requestURL}?${querySearhParams(get('qs')(options))}`;
  }
  try {
    const response = await fetch(requestURL, options)
      .then(checkStatus)
      .then(parseResult);
    return response;
  } catch (error) {
    // LOG error here
    // send('Fetch API', requestURL, {
    //   apiResponse: response,
    //   apiError: error,
    // });
    return { error: true, message: get('message')(error) || error };
  }
}
