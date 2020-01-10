import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service'

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  user: User;
 
  constructor(private service: UserService) {
  }
 
  ngOnInit() {
    this.service.me().subscribe(data => {
      this.user = data;
    });
  }
}
