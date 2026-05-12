import { AuthConfig } from 'angular-oauth2-oidc';

export const authConfig: AuthConfig = {
  issuer: window.location.origin + '/realms/vidaplus',
  redirectUri: window.location.origin + '/login',
  clientId: 'vidaplus-client',
  responseType: 'code',
  scope: 'openid profile email',
  showDebugInformation: true,
  requireHttps: false,
  strictDiscoveryDocumentValidation: false,
  skipIssuerCheck: true
};
