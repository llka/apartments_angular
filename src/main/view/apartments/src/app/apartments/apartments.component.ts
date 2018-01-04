import {Component, OnInit, Input} from '@angular/core';
import {Apartment} from "../Apartment";

@Component({
  selector: 'apartments',
  templateUrl: './apartments.component.html',
  styleUrls: ['./apartments.component.css']
})
export class ApartmentsComponent implements OnInit {
  @Input() apartments: Apartment[];

  constructor() {
  }

  ngOnInit() {
  }

  timestampToDate(timestamp): string {
    const date = new Date(timestamp);
    const dateStr = '' + date;
    console.log(dateStr.substring(0, 25));
    return dateStr.substring(0, 25);
  }
}
