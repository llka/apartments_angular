import {Injectable} from '@angular/core';

@Injectable()
export class SliderService {

  private _value0: number;
  private _value1: number;

  constructor() {
    this._value0 = 50;
    this._value1 = 50;
  }

  get value0(): number {
    return this._value0;
  }

  set value0(value: number) {
    this._value0 = value;
  }

  get value1(): number {
    return this._value1;
  }

  set value1(value: number) {
    this._value1 = value;
  }
}
