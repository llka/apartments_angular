import {Component, Input, OnInit} from '@angular/core';
import {Apartment} from '../Apartment';

@Component({
  selector: 'app-apartment',
  templateUrl: './apartment.component.html',
  styleUrls: ['./apartment.component.css']
})
export class ApartmentComponent implements OnInit {
  @Input() apartment: Apartment;

  constructor() {
  }

  ngOnInit() {
  }

  timestampToDate(timestamp): string {
    const date = new Date(timestamp);
    const dateStr = '' + date;
    return dateStr.substring(0, 25);
  }

}
