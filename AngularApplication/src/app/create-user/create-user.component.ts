import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import { UserService } from '../user.service';
import { User } from '../user';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  signupForm: FormGroup;
  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService) { }

  onSubmit() {
    if (this.signupForm.invalid) {
      return;
    }

    const username = this.signupForm.controls.username.value
    const password = this.signupForm.controls.password.value
    const name = this.signupForm.controls.name.value
    let user = new User()
    user.username = username
    user.password = password
    user.name = name
    
    this.userService.create(user).subscribe(
      data => {
        alert('User created')
        if(this.userService.loggedIn()) {
          this.router.navigate(['users'])
        } else {
          this.router.navigate(['login'])
        }
      }, err => alert('Error during signup')
    ); 
  }

  ngOnInit() {
    this.signupForm = this.formBuilder.group({
      username: ['', Validators.compose([Validators.required])],
      password: ['', Validators.required],
      passwordConfirm: ['', Validators.required],
      name: ['']
    }, { validator: this.checkPasswords });
  }

  private checkPasswords(group: FormGroup) { // here we have the 'signup' group
    let pass = group.get('password').value;
    let confirmPass = group.get('passwordConfirm').value;

    if (pass === null) {
      return { notSame: true } 
    }

    return pass === confirmPass ? null : { notSame: true }     
  }
}
