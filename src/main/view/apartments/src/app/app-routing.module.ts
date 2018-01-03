import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UsersComponent} from "./users/users.component";
import {ApartmentsComponent} from "./apartments/apartments.component";
import {LogInFormComponent} from "./log-in-form/log-in-form.component";
import {RegistrationFormComponent} from "./registration-form/registration-form.component";

const routes: Routes = [
  {path: 'user', component: UsersComponent},
  {path: 'apartment', component: ApartmentsComponent},
  {path: 'login', component: LogInFormComponent},
  {path: 'register', component: RegistrationFormComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
