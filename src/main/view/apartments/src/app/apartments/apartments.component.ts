import {Component, OnInit, Input} from '@angular/core';
import {Apartment} from '../Apartment';
import {ApartmentsDataService} from '../apartments-data.service';
import {SliderService} from '../slider.service';

@Component({
  selector: 'apartments',
  templateUrl: './apartments.component.html',
  styleUrls: ['./apartments.component.css'],
  preserveWhitespaces: false
})
export class ApartmentsComponent implements OnInit {
  allApartments: Apartment[];
  maxCost: number;
  minCost: number;
  title: string;

  constructor(private dataService: ApartmentsDataService, private sliderService: SliderService) {
  }

  ngOnInit() {
    this.getAllApartments();
  }

  find() {
    this.maxCost = this.sliderService.value1;
    this.minCost = this.sliderService.value0;
    this.getApartmentsCostBetween(this.minCost, this.maxCost);
  }

  checkMaxCostSlider() {
    while (true) {
      const oldMaxCost = this.maxCost;
      const newMaxCost = this.sliderService.value1;
      if (newMaxCost !== oldMaxCost) {
        this.maxCost = newMaxCost;
        this.getApartmentsCostLessThen(newMaxCost);
      }
    }
  }

  getAllApartments() {
    this.title = 'All Apartments';
    this.dataService.getAllApartments().then(apartments => this.allApartments = apartments);
  }

  getApartmentsCostLessThen(maxCost: number) {
    this.title = `Apartments that cost less then ${maxCost}`;
    this.dataService.getAllApartmentsCostLessThen(maxCost).then(apartments => this.allApartments = apartments);
  }

  getApartmentsCostBetween(minCost: number, maxCost: number) {
    this.title = `Apartments that cost between ${this.minCost} and ${this.maxCost}`;
    this.dataService.getAllApartmentsCostBetween(minCost, maxCost).then(apartments => this.allApartments = apartments);
  }

  getAllApartmentsCostMoreThen(minCost: number) {
    this.title = `Apartments that cost between ${this.minCost} and ${this.maxCost}`;
    this.dataService.getAllApartmentsCostLessThen(minCost).then(apartments => this.allApartments = apartments);
  }
}
