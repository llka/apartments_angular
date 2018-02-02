import {Component, Input, OnInit} from '@angular/core';
import {Apartment} from "../Apartment";

@Component({
  selector: 'app-apartments-list',
  templateUrl: './apartments-list.component.html',
  styleUrls: ['./apartments-list.component.css']
})
export class ApartmentsListComponent implements OnInit {
  @Input() apartmentsList: Apartment[];

  constructor() { }

  ngOnInit() {
  }

}
