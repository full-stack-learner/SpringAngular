import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ClientService } from '../client.service';
import { Client } from '../client';

@Component({
  selector: 'app-create-client',
  templateUrl: './create-client.component.html',
  styleUrls: ['./create-client.component.css']
})
export class CreateClientComponent implements OnInit {

  clientForm: FormGroup;
  invalidLogin: boolean = false;
  constructor(private formBuilder: FormBuilder, private router: Router, private clientService: ClientService) { }

  onSubmit() {
    if (this.clientForm.invalid) {
      return;
    }

    const secret = this.clientForm.controls.secret.value
    let client = new Client()
    client.secret = secret;
    client.scope = ['public'];
    client.secretRequired = false;
    
    this.clientService.create(client).subscribe(
      data => {
        alert('Client created')
        this.router.navigate(['clients'])
      }, err => alert('Error during client creation')
    ); 
  }

  ngOnInit() {
    this.clientForm = this.formBuilder.group({
      secret: ['', Validators.required],
      secretConfirmation: ['', Validators.required]
    }, { validator: this.checkSecrets });
  }

  private checkSecrets(group: FormGroup) {
    let secret = group.get('secret').value;
    let confirmSecret = group.get('secretConfirmation').value;

    if (secret === null) {
      return { notSame: true } 
    }

    return secret === confirmSecret ? null : { notSame: true }     
  }
}
