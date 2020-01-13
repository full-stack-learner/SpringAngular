import { Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { HttpClient } from '@angular/common/http';
import { Client } from './client';
import { Observable } from 'rxjs';
import { environment } from './../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClientService extends BaseService{

  private clientsUrl = environment.apiUrl + '/api/client';

  constructor(private http: HttpClient) {
     super()
   }

   public findAll(): Observable<Client[]> {
    return this.http.get<Client[]>(this.urlWithToken(this.clientsUrl + "/all"));
  }

   public create(client) {
    return this.http.post(this.urlWithToken(this.clientsUrl), client);
  }

  public delete(id) {
    return this.http.delete(this.urlWithToken(this.clientsUrl + "/" + id));
  }
}
