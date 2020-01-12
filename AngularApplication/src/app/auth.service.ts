import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import {Router} from "@angular/router";
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService extends BaseService {

  private redirectUri = 'http://localhost:4200';
  private tokenUri = this.baseUrl + '/oauth/token';
  private authUri = this.baseUrl + '/oauth/authorize';
  private clientId = '5e051ea44f64347c8530c268';
  
  constructor(private http: HttpClient, private router: Router) {
    super()
   }

  login(username, password, navigateTo) {

    const params = new HttpParams()
    .set('username', username)
    .set('password', password)
    .set('grant_type', 'password');

    const headers = {
      'Authorization': 'Basic ' + btoa(this.clientId + ':'),
      'Content-Type': 'application/x-www-form-urlencoded'
    }
    
    return this.http.post(this.tokenUri, params, { headers }).subscribe(
      data => { 
        this.saveToken(data)
        this.router.navigate([navigateTo]);
      }, err => alert('Invalid credentials')
    ); 
  }

  authorize() {
    window.location.href = this.authUri + '?response_type=code&client_id=' + this.clientId + '&redirect_uri='+ this.redirectUri;
  }

  getToken(code, navigateTo) {

    const params = new HttpParams()
    .set('code', code)
    .set('redirect_uri', this.redirectUri)
    .set('grant_type', 'authorization_code');

    const headers = {
      'Authorization': 'Basic ' + btoa(this.clientId + ':'),
      'Content-Type': 'application/x-www-form-urlencoded'
    }
    
    return this.http.post(this.tokenUri, params, { headers }).subscribe(
      data => {
        this.saveToken(data) 
        this.router.navigate([navigateTo]);
      }, err => alert('Unable to retrieve token via auth code')
    ); 
  }

  logout() {
    window.sessionStorage.removeItem(this.tokenKey);
    this.router.navigate(['login']);
    //window.location.reload();
  }

  private saveToken(token) {
    window.sessionStorage.setItem(this.tokenKey, JSON.stringify(token));
  }
}
