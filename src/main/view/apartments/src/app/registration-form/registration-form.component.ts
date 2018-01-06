import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {DataService} from '../data.service';

@Component({
  selector: 'app-registration-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.css'],
  preserveWhitespaces: false
})
export class RegistrationFormComponent implements OnInit {

  registrationForm: FormGroup;
  login: string;
  password: string;
  passwordRepeat: string;
  arePasswordsSame: number;
  loginAlert = 'This field is required';
  passwordAlert = 'This field is required';
  passwordMatchAlert = 'Passwords don\'t match';

  constructor(private formBuilder: FormBuilder, private dataService: DataService) {
    this.registrationForm = this.formBuilder.group({
        'login': [null, Validators.compose([Validators.required, Validators.minLength(4), Validators.maxLength(20)])],
        'password': [null, Validators.compose([Validators.required, Validators.minLength(6), Validators.maxLength(20)])],
        'passwordRepeat': [null, Validators.compose([Validators.required, Validators.minLength(6), Validators.maxLength(20)])],
      }
    );
  }

  ngOnInit() {
    this.arePasswordsSame = -1;
    console.log(this.arePasswordsSame);
    // this.registrationForm = new FormGroup({});
    // this.login = new FormControl(null, [
    //   Validators.required,
    //   Validators.minLength(4),
    //   Validators.maxLength(20)
    // ]);
    // this.password = new FormControl(null, [
    //   Validators.required,
    //   Validators.minLength(6),
    //   Validators.maxLength(20)
    // ]);
    // this.passwordRepeat = new FormControl(null, [
    //   Validators.required,
    //   Validators.minLength(6),
    //   Validators.maxLength(20)
    // ]);
    //
    // this.registrationForm = new FormGroup({
    //   login: this.login,
    //   password: this.password,
    //   passwordRepeat: this.passwordRepeat
    // });
  }

  handleForm(post) {
    this.login = post.login;
    this.password = post.password;
    this.passwordRepeat = post.passwordRepeat;
    if (this.password === post.passwordRepeat) {
      this.arePasswordsSame = 1;
      this.dataService.sendRegistrationParams(this.login, this.password);
      this.dataService.isAuthorized = true;
      console.log(this.arePasswordsSame);
    } else {
      console.log('passwords don\'t match');
      this.arePasswordsSame = 0;
      console.log(this.arePasswordsSame);
    }
  }
}
