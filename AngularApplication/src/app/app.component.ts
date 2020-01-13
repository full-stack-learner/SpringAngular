import { Component } from '@angular/core';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Angular Frontend | Spring Backend';

  constructor(public service: AuthService) {
    
  }
 
  ngOnInit(){
    let url = window.location.href
    let i = url.indexOf('code');
    if(!this.isLoggedIn() && i != -1) {
       let code = window.location.href.substring(i + 5)
       this.service.getToken(code, 'users');
    }
  }

  isLoggedIn() {
      return this.service.getAccessToken() != null
  }

  logout() {
    this.service.logout()
  }
}
