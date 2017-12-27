import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UsersComponent} from "./users/users.component";
import {ApartmentsComponent} from "./apartments/apartments.component";
import {LogInFormComponent} from "./log-in-form/log-in-form.component";

const routes: Routes = [
  {path: 'user', component: UsersComponent},
  {path: 'apartment', component: ApartmentsComponent},
  {path: 'login', component: LogInFormComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
