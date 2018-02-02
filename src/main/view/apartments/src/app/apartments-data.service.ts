import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Apartment} from './Apartment';

@Injectable()
export class ApartmentsDataService {
  private apartmentUrl = '/apartment';

  constructor(private httpClient: HttpClient) {
  }

  private handleError(error: any): Promise<any> {
    console.error('Error', error);
    return Promise.reject(error.message || error);
  }

  getAllApartments(): Promise<Apartment[]> {
    return this.httpClient.get(this.apartmentUrl)
      .toPromise()
      .then(response => response as Apartment[])
      .catch(this.handleError);
  }

  getAllApartmentsCostLessThen(maxCost: number): Promise<Apartment[]> {
    let params = new HttpParams();
    params = params.append('maxCost', maxCost.toString());

    return this.httpClient.get(this.apartmentUrl, {params})
      .toPromise()
      .then(response => response as Apartment[])
      .catch(this.handleError);
  }

  getAllApartmentsCostMoreThen(minCost: number): Promise<Apartment[]> {
    let params = new HttpParams();
    params = params.append('minCost', minCost.toString());

    return this.httpClient.get(this.apartmentUrl, {params})
      .toPromise()
      .then(response => response as Apartment[])
      .catch(this.handleError);
  }

  getAllApartmentsCostBetween(minCost: number, maxCost: number): Promise<Apartment[]> {
    let params = new HttpParams();
    params = params.append('minCost', minCost.toString());
    params = params.append('maxCost', maxCost.toString());

    return this.httpClient.get(this.apartmentUrl, {params})
      .toPromise()
      .then(response => response as Apartment[])
      .catch(this.handleError);
  }
}
