import {Component, OnInit} from '@angular/core';
import {User} from '../User';
import {DataService} from '../data.service';

@Component({
  selector: 'users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
  preserveWhitespaces: false
})
export class UsersComponent implements OnInit {

  users: User[];
  selectedUser: User;
  showManipulator: boolean = false;

  constructor(private dataService: DataService) {
  }

  getAllUsers() {
    this.dataService.getAllUsers().then(users => this.users = users);
  }

  ngOnInit(): void {
    this.getAllUsers();
  }

  onSelect(user: User): void {
    this.selectedUser = user;
    this.showManipulator = true;
  }

}
