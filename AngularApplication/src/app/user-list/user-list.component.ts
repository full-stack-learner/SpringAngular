import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service'
import { Observable } from 'rxjs';
import { DataSource } from '@angular/cdk/table';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  dataSource: UserDataSource;
  displayedColumns = ['id', 'name', 'username', 'password', 'delete'];
 
  constructor(private userService: UserService) {
  }
 
  ngOnInit() {
    this.dataSource = new UserDataSource(this.userService);
  }

  delete(id) {
    this.userService.delete(id).subscribe(
      data => window.location.reload(),
      err => alert('Error deleting ' + id)
    ); 
  }
}

export class UserDataSource extends DataSource<any> {
  constructor(private userService: UserService) {
    super();
  }

  connect(): Observable<User[]> {
    return this.userService.findAll()
  }

  disconnect() {
  }
}
