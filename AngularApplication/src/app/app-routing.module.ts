import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './user-list/user-list.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';
import { CreateUserComponent } from './create-user/create-user.component';
import { CreateClientComponent } from './create-client/create-client.component';
import { ClientListComponent } from './client-list/client-list.component';

const routes: Routes = [
  { path: 'users', component: UserListComponent },
  { path: 'clients', component: ClientListComponent },
  { path: 'me', component: UserComponent},
  { path: 'createuser', component: CreateUserComponent },
  { path: 'createclient', component: CreateClientComponent },
  { path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
