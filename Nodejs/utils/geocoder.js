const NodeGeocoder = require('node-geocoder');

const options = {
  provider: "mapquest",
  httpAdapter: 'https',
  apiKey: "zN06dvpgHWec2wZfqcJY4xVbmjlGhuUb",
  formatter: null
};

const geocoder = NodeGeocoder(options);

module.exports = geocoder;
