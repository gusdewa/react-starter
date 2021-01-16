import request from 'utils/request';
//
import { SAMPLE_POST, SAMPLE_GET } from 'constants/serviceURL';

// Sample of POST request
export const verifyOtp = data =>
  request({
    url: SAMPLE_POST,
    options: {
      method: 'POST',
      body: JSON.stringify(data),
      headers: {
        'Content-Type': 'application/json',
      },
    },
  });

// Sample of GET request
export const getPostalCode = postalCode =>
  request({
    url: SAMPLE_GET,
    params: {
      postalCode,
    },
  });
