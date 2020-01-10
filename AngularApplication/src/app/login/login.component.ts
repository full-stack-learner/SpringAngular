import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService) { }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }

    const username = this.loginForm.controls.username.value
    const password = this.loginForm.controls.password.value

    this.authService.login(username, password, 'users')
  }

  // oauth authorization code flow
  authorize() {
    this.authService.authorize()
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }
}
