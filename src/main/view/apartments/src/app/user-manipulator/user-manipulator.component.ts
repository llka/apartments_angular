import {Component, OnInit, Input} from '@angular/core';
import {DataService} from "../data.service";
import {User} from '../User';
import {Apartment} from "../Apartment";

@Component({
  selector: 'user-manipulator',
  templateUrl: './user-manipulator.component.html',
  styleUrls: ['./user-manipulator.component.css'],
  providers: [DataService]
})
export class UserManipulatorComponent implements OnInit {

  @Input() user: User;
  userApartments: Apartment[];

  constructor(private dataService: DataService) {
  }

  delete(): void {
    this.dataService.deleteUser(this.user.id).then(() => this.goBack());
  }

  getUserApartments(): void {
    this.dataService.getUserApartments(this.user.id).then(apartments => this.userApartments = apartments);
  }

  goBack(): void {
    window.location.replace('');
  }

  ngOnInit() {
  }
}
