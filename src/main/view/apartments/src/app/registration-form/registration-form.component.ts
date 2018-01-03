import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {DataService} from '../data.service';

@Component({
  selector: 'app-registration-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.css']
})
export class RegistrationFormComponent implements OnInit {

  registrationForm: FormGroup;
  post: any;
  login: string;
  password: string;
  loginAlert = 'This field is required';
  passwordAlert = 'This field is required';

  constructor(private formBuilder: FormBuilder, private dataService: DataService) {
    this.registrationForm = this.formBuilder.group({
        'login': [null, Validators.compose([Validators.required, Validators.minLength(4), Validators.maxLength(20)])],
        'password': [null, Validators.compose([Validators.required, Validators.minLength(6), Validators.maxLength(20)])]
      }
    );
  }

  ngOnInit() {
  }

  handleForm(post) {
    this.login = post.login;
    this.password = post.password;
    console.log(this.login);
    console.log(this.password);

    this.dataService.sendRegistrationParams(this.login, this.password);
    this.dataService.isAuthorized = true;
  }
}
