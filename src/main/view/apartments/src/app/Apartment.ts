import {Timestamp} from "rxjs";
export class Apartment{
  private _id: number;
  private _bookedFrom: Date;
  private _bookedTo: Date;
  private _cost: number;


  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get bookedFrom(): Date {
    return this._bookedFrom;
  }

  set bookedFrom(value: Date) {
    this._bookedFrom = value;
  }

  get bookedTo(): Date {
    return this._bookedTo;
  }

  set bookedTo(value: Date) {
    this._bookedTo = value;
  }

  get cost(): number {
    return this._cost;
  }

  set cost(value: number) {
    this._cost = value;
  }
}
