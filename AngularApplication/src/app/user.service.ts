import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { User } from './user';
import { Observable, of } from 'rxjs';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class UserService extends BaseService {

  private usersUrl = this.baseUrl + '/api/user';
  private meUrl = this.baseUrl + '/me';
 
  constructor(private http: HttpClient) {
    super()
  }
 
  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.urlWithToken(this.usersUrl + "/all"));
  }

  public create(user) {
    // no token required here
    return this.http.post(this.usersUrl, user);
  }

  public delete(id) {
    return this.http.delete(this.urlWithToken(this.usersUrl + "/" + id));
  }

  public me(): Observable<User> {
    return this.http.get<User>(this.urlWithToken(this.meUrl));
  }
}
