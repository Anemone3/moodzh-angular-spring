export const environment = {
  production: true,
  apiUrl: process.env['API_URL'] || 'http://localhost:8080/api',
  apiTimeout: parseInt(process.env['API_TIMEOUT'] || '5000'),
  enableLogging: false,
};
