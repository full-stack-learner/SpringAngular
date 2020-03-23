export const environment = {
  production: true,
  // the IP address of the Spring server including port
  apiUrl: 'http://your.domain.at:8443',
  // redirect URI back to the Angular application for oauth authorization code (no port needed because nginx defaults to 80 - see Dockerfile)
  redirectUri: 'http://your.domain.at'
};
