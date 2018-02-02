import {Component, Input, OnInit} from '@angular/core';
import {SliderService} from '../slider.service';

@Component({
  selector: 'app-slider',
  templateUrl: './slider.component.html',
  styleUrls: ['./slider.component.css']
})
export class SliderComponent implements OnInit {

  @Input() type: number;
  @Input() minValue: number;
  @Input() maxValue: number;
  @Input() title: string;
  currentValue: number;

  constructor(private sliderService: SliderService) {
  }

  ngOnInit() {
    this.currentValue = (this.maxValue - this.minValue) / 2;
  }

  changed(value) {
    this.currentValue = value;
  }

  buttonUp(value) {
    this.currentValue = value;
    console.log("type = " + this.type);
    switch (this.type) {
      case 0:
        this.sliderService.value0 = this.currentValue;
        break;
      case 1:
        this.sliderService.value1 = this.currentValue;
        break;
      default:
        console.log("default");
    }
  }
}
