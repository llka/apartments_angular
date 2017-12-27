import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {DataService} from '../data.service';

@Component({
  selector: 'log-in-form',
  templateUrl: './log-in-form.component.html',
  styleUrls: ['./log-in-form.component.css']
})
export class LogInFormComponent implements OnInit {

  loginForm: FormGroup;
  post: any;
  login: string;
  password: string;
  loginAlert = 'This field is required';
  passwordAlert = 'This field is required';

  constructor(private formBuilder: FormBuilder, private dataService: DataService) {
    this.loginForm = this.formBuilder.group({
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

    this.dataService.sendLoginParams(this.login, this.password);
  }
}
