import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {DataService} from './data.service';
import {UsersComponent} from './users/users.component';
import {UserManipulatorComponent} from './user-manipulator/user-manipulator.component';
import {ApartmentsComponent} from './apartments/apartments.component';
import {AppRoutingModule} from './app-routing.module';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {LogInFormComponent} from './log-in-form/log-in-form.component';
import {RegistrationFormComponent} from './registration-form/registration-form.component';

@NgModule({
  declarations: [
    AppComponent,
    UsersComponent,
    UserManipulatorComponent,
    ApartmentsComponent,
    LogInFormComponent,
    RegistrationFormComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [DataService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
