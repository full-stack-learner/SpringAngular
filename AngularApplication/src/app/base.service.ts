import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export abstract class BaseService {

  // the base url for our application (server IP address in production, localhost in development)
  // the url via which we can access Spring backend
  protected tokenKey = 'token';

  constructor() { }

  loggedIn() {
    return this.getAccessToken() != null
  }

  getAccessToken() {
    let token = JSON.parse(window.sessionStorage.getItem(this.tokenKey))
    if (token == null) {
      return null
    } else {
      return token.access_token
    }
  }

  protected urlWithToken(url) {
    let token = this.getAccessToken()
    if(token != null) {
      return url + '?access_token=' + token;
    } else {
      return url
    }
  }
}
