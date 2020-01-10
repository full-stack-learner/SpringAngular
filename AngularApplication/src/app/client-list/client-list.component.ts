import { Component, OnInit } from '@angular/core';
import { ClientService } from '../client.service';
import { Client } from '../client';
import { Observable } from 'rxjs';
import { DataSource } from '@angular/cdk/table';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {

  dataSource: ClientDataSource;
  displayedColumns = ['id', 'scope', 'secret_required', 'secret', 'delete'];
 
  constructor(private clientsService: ClientService) {
  }
 
  ngOnInit() {
    this.dataSource = new ClientDataSource(this.clientsService);
  }

  delete(id) {
    this.clientsService.delete(id).subscribe(
      data => window.location.reload(),
      err => alert('Error deleting ' + id)
    ); 
  }
}

export class ClientDataSource extends DataSource<any> {
  constructor(private clientsService: ClientService) {
    super();
  }

  connect(): Observable<Client[]> {
    return this.clientsService.findAll()
  }

  disconnect() {
  }
}
