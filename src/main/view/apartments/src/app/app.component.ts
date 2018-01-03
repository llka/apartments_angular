import {Component} from '@angular/core';
import {DataService} from './data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  authorized: boolean;

  constructor(private dataService: DataService) {
    this.authorized = dataService.isAuthorized;
  }

  logOut() {
    console.log('logout');
    this.dataService.logout();

    this.dataService.isAuthorized = false;
    this.authorized = false;

    console.log('logout - ok');
  }

  updateIsAuthorizedStatus() {
    this.authorized = this.dataService.isAuthorized;
  }
}
